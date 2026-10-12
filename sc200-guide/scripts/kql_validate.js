// Validation syntaxique ET sémantique des requêtes KQL du guide avec Kusto.Language
// (bibliothèque officielle de Microsoft, version JavaScript @kusto/language-service-next).
// Entrée : JSON [{id, env, code}] ; env = "sentinel" | "xdr". Sortie : JSON des diagnostics.
global.Bridge = require('@kusto/language-service-next/bridge.js');
require('@kusto/language-service-next/Kusto.Language.Bridge.js');
const fs = require('fs');
const path = require('path');
const K = Kusto.Language, S = K.Symbols;

const schemas = JSON.parse(fs.readFileSync(path.join(__dirname, '..', 'content', 'schemas.json'), 'utf8'));
function table(name, cols) {
  return new S.TableSymbol.$ctor4(name, cols.map(([n, t]) => new S.ColumnSymbol(n, S.ScalarTypes.GetSymbol(t))));
}
const states = {};
for (const env of Object.keys(schemas)) {
  const tables = Object.entries(schemas[env]).map(([n, c]) => table(n, c));
  states[env] = K.GlobalState.Default.WithDatabase(new S.DatabaseSymbol.ctor('db', tables));
}
// Fonctions intégrées de Microsoft Sentinel simulées par un prélude `let` (signatures documentées).
const PRELUDE = {
  sentinel: [
    'let _GetWatchlist = (watchlistAlias:string) { datatable(SearchKey:string, IPAddress:string, UserPrincipalName:string, DeviceName:string, Comment:string, Tag:string)[] };',
    'let _Im_Authentication = (starttime:datetime=datetime(null), endtime:datetime=datetime(null)) { ASimAuthenticationEventLogs };',
  ].join('\n') + '\n',
  xdr: '',
};
const blocks = JSON.parse(fs.readFileSync(process.argv[2], 'utf8'));
const out = [];
for (const b of blocks) {
  const pre = PRELUDE[b.env] || '';
  const code = K.KustoCode.ParseAndAnalyze(pre + b.code, states[b.env]);
  const d = code.GetDiagnostics();
  const errs = [];
  for (let i = 0; i < d.Count; i++) {
    const x = d.getItem(i);
    if (x.Severity !== 'Error') continue;
    const start = x.Start - pre.length;
    const line = b.code.slice(0, Math.max(0, start)).split('\n').length;
    errs.push({ line, message: x.Message, near: b.code.substr(Math.max(0, start), 40) });
  }
  out.push({ id: b.id, env: b.env, errors: errs });
}
fs.writeFileSync(process.argv[3], JSON.stringify(out, null, 1));
const bad = out.filter(o => o.errors.length);
console.log(`${out.length} requêtes KQL analysées, ${bad.length} en erreur`);
for (const o of bad) console.log(' ', o.id, o.env, JSON.stringify(o.errors));

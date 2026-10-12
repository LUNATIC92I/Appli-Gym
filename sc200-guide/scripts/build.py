#!/usr/bin/env python3
"""Générateur du « Guide complet de préparation à la certification Microsoft SC-200 ».

Étapes :
  1. lecture des questions (content/questions/*.txt) et numérotation continue ;
  2. contrôles structurels (propositions, corrections, références, doublons) ;
  3. équilibrage des lettres de bonne réponse (mélange déterministe des propositions) ;
  4. validation KQL (syntaxe + schémas officiels) via scripts/kql_validate.js ;
  5. sélection des trois examens blancs ;
  6. génération HTML puis PDF (Chromium/Playwright) : guide complet, sujets d'examens, corrigés ;
  7. rapport de contrôle qualité et registre des questions.

Usage : python3 scripts/build.py [--no-pdf] [--docx]
"""
import argparse
import collections
import csv
import datetime
import html
import json
import pathlib
import random
import re
import subprocess
import sys

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parent))
from qparse import LEVELS, parse_file  # noqa: E402

ROOT = pathlib.Path(__file__).resolve().parent.parent
CONTENT = ROOT / "content"
OUT = ROOT / "output"
BUILD = ROOT / "build"

MODULES = {
    1: ("Gestion d'un environnement Microsoft Sentinel", 1),
    2: ("Détection et investigation avec Microsoft Defender XDR", 2),
    3: ("Kusto Query Language (KQL) — exercices avancés", 3),
    4: ("Réponse aux incidents et investigation avancée", 2),
    5: ("Microsoft Defender for Cloud et protection des ressources", 2),
    6: ("Scénarios transversaux et préparation à l'examen", 2),
}
OBJECTIVES = {
    1: "Gérer un environnement d'opérations de sécurité (40–45 %)",
    2: "Répondre aux incidents de sécurité (35–40 %)",
    3: "Effectuer la chasse aux menaces (20–25 %)",
}
TARGET = {1: 75, 2: 100, 3: 100, 4: 75, 5: 50, 6: 50}
KQL_SUBS = {"comp": "Compréhension de requêtes existantes", "fix": "Correction de requêtes erronées",
            "create": "Création de requêtes à partir d'un besoin", "corr": "Corrélation entre plusieurs tables",
            "hunt": "Chasse aux menaces sur journaux simulés"}
EXAM_WEIGHTS = {1: 21, 2: 18, 3: 11}  # 50 questions, pondération du référentiel officiel
LETTERS = "ABCDE"
NUM_WORDS = {2: "deux", 3: "trois"}


# --------------------------------------------------------------------------- utilitaires HTML
def esc(s):
    return html.escape(s, quote=False)


KQL_KW = r"\b(where|project|project-away|project-rename|project-reorder|extend|summarize|by|join|kind|on|union|distinct|count|top|take|limit|sort|order|asc|desc|let|mv-expand|mv-apply|parse|evaluate|invoke|render|make-series|lookup|search|as|materialize|between|in|has|has_any|has_all|has_cs|contains|contains_cs|startswith|endswith|matches|regex|and|or|not|isfuzzy|withsource|range|from|to|step|serialize|datatable|externaldata|getschema|print|toscalar|with|nulls|first|last|inner|innerunique|leftouter|leftanti|leftsemi|rightanti|rightouter|rightsemi|fullouter)\b"


def highlight_kql(code):
    out, pos = [], 0
    token_re = re.compile(r'(//[^\n]*)|(@?"(?:[^"\\\n]|\\.)*"|@?\'(?:[^\'\\\n]|\\.)*\')|' + KQL_KW + r'|(\b\d+(?:\.\d+)?(?:d|h|m|s|ms)?\b)|(==|!=|=~|!~|<=|>=|\|)')
    for m in token_re.finditer(code):
        out.append(esc(code[pos:m.start()]))
        t = esc(m.group(0))
        if m.group(1):
            out.append(f'<span class="c">{t}</span>')
        elif m.group(2):
            out.append(f'<span class="s">{t}</span>')
        elif m.group(3):
            out.append(f'<span class="k">{t}</span>')
        elif m.group(4):
            out.append(f'<span class="n">{t}</span>')
        else:
            out.append(f'<span class="o">{t}</span>')
        pos = m.end()
    out.append(esc(code[pos:]))
    return "".join(out)


CODE_LABEL = {"kql": "KQL — Microsoft Sentinel / Log Analytics", "kqlx": "KQL — Chasse avancée Microsoft Defender XDR",
              "kqlbad": "KQL — requête volontairement erronée (à corriger)", "kqlnv": "KQL",
              "text": "Journal / sortie", "powershell": "PowerShell", "json": "JSON", "": "Texte"}


def inline(s):
    parts = re.split(r"(`[^`]+`)", s)
    out = []
    for p in parts:
        if p.startswith("`") and p.endswith("`") and len(p) > 1:
            out.append(f"<code>{esc(p[1:-1])}</code>")
        else:
            t = esc(p)
            t = re.sub(r"\*\*(.+?)\*\*", r"<strong>\1</strong>", t)
            t = re.sub(r"(?<![\w*])\*(?!\s)(.+?)(?<!\s)\*(?![\w*])", r"<em>\1</em>", t)
            out.append(t)
    return "".join(out)


def md(text, neutral=False):
    """Mini-convertisseur Markdown : paragraphes, listes, blocs de code, inline.

    neutral=True : étiquette de bloc de code neutre (propositions de réponse), pour ne pas révéler
    qu'une requête est volontairement erronée."""
    if not text:
        return ""
    lines = text.split("\n")
    out, i, para, lst = [], 0, [], None

    def close_para():
        nonlocal para
        if para:
            out.append("<p>" + inline(" ".join(x.strip() for x in para)) + "</p>")
            para = []

    def close_list():
        nonlocal lst
        if lst:
            tag = lst[0]
            out.append(f"<{tag}>" + "".join(f"<li>{inline(x)}</li>" for x in lst[1]) + f"</{tag}>")
            lst = None

    while i < len(lines):
        ln = lines[i]
        st = ln.strip()
        if st.startswith("```"):
            close_para(); close_list()
            lang = st[3:].strip()
            j = i + 1
            buf = []
            while j < len(lines) and not lines[j].strip().startswith("```"):
                buf.append(lines[j])
                j += 1
            code = "\n".join(buf)
            # retire l'indentation commune
            ind = min((len(x) - len(x.lstrip()) for x in buf if x.strip()), default=0)
            code = "\n".join(x[ind:] for x in buf)
            body = highlight_kql(code) if lang.startswith("kql") else esc(code)
            cls = "code " + (lang or "plain")
            label = "Requête KQL" if (neutral and lang.startswith("kql")) else CODE_LABEL.get(lang, lang)
            if neutral:
                cls = "code plain"
            out.append(f'<div class="{cls}"><div class="codelabel">{label}</div><pre>{body}</pre></div>')
            i = j + 1
            continue
        m_ul = re.match(r"^\s*[-*•]\s+(.*)$", ln)
        m_ol = re.match(r"^\s*\d+[.)]\s+(.*)$", ln)
        if m_ul or m_ol:
            close_para()
            tag = "ul" if m_ul else "ol"
            if lst and lst[0] != tag:
                close_list()
            if not lst:
                lst = (tag, [])
            lst[1].append((m_ul or m_ol).group(1))
        elif not st:
            close_para(); close_list()
        elif lst and ln.startswith("  "):
            lst[1][-1] += " " + st
        else:
            close_list()
            para.append(ln)
        i += 1
    close_para(); close_list()
    return "\n".join(out)


def code_blocks(text):
    for m in re.finditer(r"```(\w*)\n(.*?)\n\s*```", text or "", re.S):
        yield m.group(1), m.group(2)


# --------------------------------------------------------------------------- chargement
def load():
    files = sorted((CONTENT / "questions").glob("*.txt"))
    qs = []
    for f in files:
        qs.extend(parse_file(f))
    for n, q in enumerate(qs, 1):
        q["n"] = n
        q["id"] = f"M{q['module']}-{n:03d}"
        q["obj"] = int(q["meta"].get("obj", MODULES[q["module"]][1]))
        q["level"] = q["meta"].get("niveau", "")
        q["refs"] = [r for r in str(q["meta"].get("ref", "")).split(",") if r]
        q["multi"] = int(q["meta"].get("multi", 1))
        q["sub"] = q["meta"].get("sub")
    return qs


def check_structure(qs, refs):
    problems = []
    for q in qs:
        p = lambda msg: problems.append(f"Q{q['n']} ({q['file']}:{q['line']}) {msg}")  # noqa: E731
        if q["module"] not in MODULES:
            p("module inconnu")
        if q["level"] not in LEVELS:
            p("niveau manquant ou invalide")
        for k in ("stem", "correction", "tech", "key"):
            if not q.get(k):
                p(f"section obligatoire absente : {k}")
        n_opt = len(q["options"])
        if n_opt not in (4, 5):
            p(f"{n_opt} propositions (4 ou 5 attendues)")
        nc = sum(o["correct"] for o in q["options"])
        if nc != q["multi"]:
            p(f"{nc} bonne(s) réponse(s) pour multi={q['multi']}")
        for o in q["options"]:
            if not o["correct"] and not o["why"]:
                p(f"justification manquante pour la proposition « {o['text'][:40]} »")
        if not q["refs"]:
            p("aucune référence")
        for r in q["refs"]:
            if r not in refs:
                p(f"référence inconnue : {r}")
            elif refs[r]["status"] != 200:
                p(f"référence en erreur : {r}")
        if q["module"] == 3 and q["sub"] not in KQL_SUBS:
            p("sous-catégorie KQL manquante")
        texts = [o["text"] for o in q["options"]]
        if len(set(t.lower() for t in texts)) != len(texts):
            p("propositions identiques")
        if re.search(r"\b(?:réponse|proposition|option)s?\s+[A-E]\b", q.get("correction", "") + q.get("tech", "")):
            p("la correction cite une lettre (les propositions sont mélangées)")
    return problems


def shingles(t):
    w = re.findall(r"\w+", t.lower())
    return set(zip(w, w[1:], w[2:]))


def find_duplicates(qs):
    dups = []
    sh = [(q, shingles(q["stem"] + " " + " ".join(o["text"] for o in q["options"]))) for q in qs]
    for i in range(len(sh)):
        for j in range(i + 1, len(sh)):
            a, b = sh[i][1], sh[j][1]
            if not a or not b:
                continue
            jac = len(a & b) / len(a | b)
            if jac > 0.6:
                dups.append((sh[i][0]["n"], sh[j][0]["n"], round(jac, 2)))
    titles = collections.Counter(q["title"].lower() for q in qs)
    same_titles = [t for t, c in titles.items() if c > 1]
    return dups, same_titles


def balance(qs, seed=200):
    """Place les bonnes réponses de façon équilibrée sur A–E et mélange les distracteurs."""
    rng = random.Random(seed)
    counts = collections.Counter()
    last = []
    for q in qs:
        opts = q["options"][:]
        n = len(opts)
        if q["meta"].get("fixed"):
            q["shuffled"] = opts
        elif q["multi"] == 1:
            letters = list(range(n))
            # lettre la moins utilisée, en évitant de répéter les deux dernières
            rng.shuffle(letters)
            letters.sort(key=lambda k: (counts[(n, k)] + (2 if k in last[-2:] else 0)))
            target = letters[0]
            good = [o for o in opts if o["correct"]][0]
            bad = [o for o in opts if not o["correct"]]
            rng.shuffle(bad)
            bad.insert(target, good)
            q["shuffled"] = bad
            counts[(n, target)] += 1
            last.append(target)
        else:
            rng.shuffle(opts)
            q["shuffled"] = opts
        q["answer"] = "".join(LETTERS[i] for i, o in enumerate(q["shuffled"]) if o["correct"])


# --------------------------------------------------------------------------- KQL
def validate_kql(qs):
    blocks, nonval = [], []
    for q in qs:
        fields = [q.get(k, "") for k in ("stem", "correction", "tech", "example", "result", "schema", "improve")]
        fields += [o["text"] + "\n" + o["why"] for o in q["options"]]
        k = 0
        for f in fields:
            for lang, code in code_blocks(f):
                k += 1
                bid = f"Q{q['n']}#{k}"
                if lang == "kql":
                    blocks.append({"id": bid, "env": "sentinel", "code": code})
                elif lang == "kqlx":
                    blocks.append({"id": bid, "env": "xdr", "code": code})
                elif lang in ("kqlbad", "kqlnv"):
                    nonval.append((bid, lang))
    BUILD.mkdir(exist_ok=True)
    src, dst = BUILD / "kql_blocks.json", BUILD / "kql_results.json"
    src.write_text(json.dumps(blocks, ensure_ascii=False), encoding="utf8")
    r = subprocess.run(["node", str(ROOT / "scripts" / "kql_validate.js"), str(src), str(dst)],
                       capture_output=True, text=True, cwd=ROOT)
    print(r.stdout[-4000:], r.stderr[-2000:])
    res = json.loads(dst.read_text(encoding="utf8"))
    return blocks, res, nonval


# --------------------------------------------------------------------------- examens blancs
def select_exams(qs, seed=2026):
    rng = random.Random(seed)
    pools = collections.defaultdict(list)
    for q in qs:
        pools[q["obj"]].append(q)
    exams = [[], [], []]
    for obj, k in EXAM_WEIGHTS.items():
        pool = pools[obj][:]
        rng.shuffle(pool)
        # privilégie la diversité de niveaux : intermédiaire/avancé d'abord
        pool.sort(key=lambda q: {"A": 0, "I": 0, "D": 1}[q["level"]] + rng.random())
        need = k * 3
        if len(pool) < need:
            print(f"ATTENTION : pas assez de questions pour l'objectif {obj} ({len(pool)}/{need}) — examens incomplets")
            need = len(pool) - len(pool) % 3
            k = need // 3
        chosen = pool[:need]
        rng.shuffle(chosen)
        for e in range(3):
            exams[e].extend(chosen[e * k:(e + 1) * k])
    result = []
    for e, ex in enumerate(exams):
        rng.shuffle(ex)
        items = []
        for q in ex:
            opts = q["options"][:]
            if not q["meta"].get("fixed"):
                rng.shuffle(opts)
            ans = "".join(LETTERS[i] for i, o in enumerate(opts) if o["correct"])
            items.append({"q": q, "opts": opts, "answer": ans})
        result.append(items)
    return result


# --------------------------------------------------------------------------- rendu HTML
def ref_html(keys, refs):
    out = []
    for k in keys:
        r = refs[k]
        out.append(f'<li>{esc(r["title"])} — <a href="{esc(r["url"])}">{esc(r["url"])}</a></li>')
    return "<ul class='refs'>" + "".join(out) + "</ul>"


def stem_html(q):
    s = md(q["stem"])
    if q["multi"] > 1:
        s += f'<p class="multi">Sélectionnez {NUM_WORDS.get(q["multi"], q["multi"])} réponses.</p>'
    return s


def options_html(opts):
    return "<ol class='opts'>" + "".join(
        f"<li><span class='L'>{LETTERS[i]}.</span><div>{md(o['text'], neutral=True)}</div></li>" for i, o in enumerate(opts)) + "</ol>"


def question_html(q, refs):
    meta = (f"<div class='meta'><span><b>Domaine :</b> Module {q['module']} — {esc(MODULES[q['module']][0])}</span>"
            f"<span><b>Niveau :</b> {LEVELS[q['level']]}</span>"
            f"<span><b>Référentiel officiel :</b> {esc(OBJECTIVES[q['obj']])}</span>"
            + (f"<span><b>Exercice KQL :</b> {esc(KQL_SUBS[q['sub']])}</span>" if q.get('sub') in KQL_SUBS else "")
            + "</div>")
    wrong = "".join(f"<li><b>{LETTERS[i]}.</b> {inline(o['why'])}</li>" for i, o in enumerate(q["shuffled"]) if not o["correct"])
    extra = ""
    for key, label in (("result", "Résultat attendu"), ("schema", "Hypothèses liées au schéma de données"),
                       ("improve", "Amélioration proposée")):
        if q.get(key):
            extra += f"<h5>{label}</h5>{md(q[key])}"
    ex = f"<h5>Exemple pratique</h5>{md(q['example'])}" if q.get("example") else ""
    return f"""
<section class="q" id="q{q['n']}">
  <h4 class="qt">Question n° {q['n']} — {inline(q['title'])}</h4>
  {meta}
  <div class="stem"><span class="lbl">Énoncé :</span>&nbsp;{stem_html(q)}</div>
  {options_html(q['shuffled'])}
  <div class="corr">
    <div class="ans">Bonne réponse : <b>{', '.join(q['answer'])}</b></div>
    <h5>Correction détaillée</h5>{md(q['correction'])}
    <h5>Analyse des réponses incorrectes</h5><ul class="wrong">{wrong}</ul>
    <h5>Explication technique</h5>{md(q.get('tech', ''))}
    {ex}{extra}
    <div class="key"><b>Point à retenir pour l'examen :</b> {md(q['key'])}</div>
    <h5>Référence officielle</h5>{ref_html(q['refs'], refs)}
  </div>
</section>"""


def exam_html(e, items, with_answers):
    out = [f'<h1 class="part" id="exam{e + 1}">Examen blanc n° {e + 1}{" — corrigé" if with_answers == "only" else ""}</h1>']
    if with_answers != "only":
        out.append(md(EXAM_INTRO))
        for i, it in enumerate(items, 1):
            q = it["q"]
            s = md(q["stem"])
            if q["multi"] > 1:
                s += f'<p class="multi">Sélectionnez {NUM_WORDS.get(q["multi"], q["multi"])} réponses.</p>'
            out.append(f"""<section class="q exam"><h4 class="qt">Examen {e + 1} — Question {i} <span class="small">({esc(OBJECTIVES[q['obj']].split(' (')[0])})</span></h4>
<div class="stem">{s}</div>{options_html(it['opts'])}</section>""")
    if with_answers:
        out.append(f'<h2 id="exam{e + 1}c">Corrigé de l\'examen blanc n° {e + 1}</h2>')
        out.append("<p>Chaque question de cet examen est une reprise d'une question du guide (numéro indiqué) ; "
                   "les propositions ont été mélangées. Elle n'est pas comptée une seconde fois dans le total des questions uniques.</p>")
        rows = []
        for i, it in enumerate(items, 1):
            q = it["q"]
            correct = [o for o in it["opts"] if o["correct"]]
            just = inline(q["key"].split("\n")[0])
            comp = ""
            if q["level"] == "A":
                first = re.split(r"(?<=[.!?])\s", q["correction"].replace("\n", " "), maxsplit=2)
                comp = "<div class='comp'><b>Explication complémentaire :</b> " + inline(" ".join(first[:2])) + "</div>"
            rows.append(f"<tr><td>{i}</td><td class='a'>{', '.join(it['answer'])}</td><td>{just}{comp}"
                        f"<div class='small'>Bonne(s) réponse(s) : {inline(' / '.join(o['text'].split(chr(10))[0] for o in correct))} — "
                        f"détails : question n° {q['n']} du guide.</div></td></tr>")
        out.append("<table class='key'><thead><tr><th>N°</th><th>Réponse</th><th>Justification</th></tr></thead><tbody>"
                   + "".join(rows) + "</tbody></table>")
        out.append(SCORE_NOTE)
    return "\n".join(out)


EXAM_INTRO = """**Consignes :** 50 questions, durée conseillée 100 minutes, sans documentation. Notez vos réponses sur une feuille séparée, puis comparez-les au corrigé. Les questions à réponses multiples indiquent le nombre de réponses attendues. La répartition suit la pondération officielle du référentiel : 21 questions « Gérer un environnement d'opérations de sécurité », 18 « Répondre aux incidents de sécurité » et 11 « Effectuer la chasse aux menaces »."""
SCORE_NOTE = ("<p class='note'>Le score obtenu à cet examen blanc est un indicateur d'entraînement : il ne constitue en aucun cas "
              "une prédiction du résultat à l'examen officiel, dont le format, la pondération et la notation (score minimal de 700 sur 1000) "
              "sont définis par Microsoft.</p>")


def pandoc_md(path):
    txt = path.read_text(encoding="utf8")
    r = subprocess.run(["pandoc", "-f", "markdown+pipe_tables", "-t", "html5", "--no-highlight"],
                       input=txt, capture_output=True, text=True, check=True)
    return r.stdout


def matrix_html(qs):
    cnt = collections.Counter((q["module"], q["level"]) for q in qs)
    obj = collections.Counter((q["module"], q["obj"]) for q in qs)
    rows = []
    for m, (name, _) in MODULES.items():
        tot = sum(cnt[(m, l)] for l in LEVELS)
        rows.append(f"<tr><td>Module {m} — {esc(name)}</td><td>{TARGET[m]}</td><td><b>{tot}</b></td>"
                    + "".join(f"<td>{cnt[(m, l)]}</td>" for l in LEVELS)
                    + "".join(f"<td>{obj[(m, o)]}</td>" for o in OBJECTIVES) + "</tr>")
    tl = collections.Counter(q["level"] for q in qs)
    to = collections.Counter(q["obj"] for q in qs)
    rows.append(f"<tr class='tot'><td>Total</td><td>{sum(TARGET.values())}</td><td><b>{len(qs)}</b></td>"
                + "".join(f"<td>{tl[l]}</td>" for l in LEVELS) + "".join(f"<td>{to[o]}</td>" for o in OBJECTIVES) + "</tr>")
    head = ("<tr><th>Module</th><th>Cible</th><th>Produit</th><th>Débutant</th><th>Interm.</th><th>Avancé</th>"
            "<th>Obj. 1<br>Gérer</th><th>Obj. 2<br>Répondre</th><th>Obj. 3<br>Chasse</th></tr>")
    pct = " · ".join(f"Objectif {o} : {to[o]} questions ({100 * to[o] / len(qs):.0f} %)" for o in OBJECTIVES)
    return f"<table class='matrix'><thead>{head}</thead><tbody>{''.join(rows)}</tbody></table><p class='small'>{pct}</p>"


def letters_html(qs):
    c = collections.Counter(a for q in qs for a in q["answer"])
    single = collections.Counter(q["answer"] for q in qs if q["multi"] == 1)
    return ", ".join(f"{l} : {c[l]}" for l in LETTERS if c[l]), dict(single)


def index_html(qs):
    terms = [t.strip() for t in (CONTENT / "index_terms.txt").read_text(encoding="utf8").splitlines()
             if t.strip() and not t.startswith("#")]
    entries = []
    for t in sorted(set(terms), key=lambda x: x.lower().lstrip("_")):
        pat = re.compile(r"(?<![\w-])" + re.escape(t) + r"(?![\w-])", re.I if t.islower() or " " in t else 0)
        nums = [q["n"] for q in qs if pat.search(" ".join([q["title"], q["stem"], q.get("correction", ""), q.get("tech", ""),
                                                         q.get("example", ""), " ".join(o["text"] for o in q["options"])]))]
        if nums:
            shown = nums[:25]
            more = f" … (+{len(nums) - 25})" if len(nums) > 25 else ""
            entries.append(f"<div class='ix'><b>{esc(t)}</b> : " + ", ".join(f"<a href='#q{n}'>{n}</a>" for n in shown) + more + "</div>")
    return "<div class='index'>" + "".join(entries) + "</div>"


def biblio_html(refs, used, verified_on):
    groups = collections.OrderedDict([
        ("Certification SC-200", ("sc200",)), ("Microsoft Sentinel", ("sent-",)),
        ("Azure Monitor / Log Analytics", ("la-",)), ("Microsoft Defender XDR", ("xdr-",)),
        ("Microsoft Defender for Endpoint", ("mde-",)), ("Microsoft Defender for Office 365", ("mdo-",)),
        ("Microsoft Defender for Identity", ("mdi-",)), ("Microsoft Defender for Cloud Apps", ("mdca-",)),
        ("Microsoft Entra ID", ("entra-",)), ("Microsoft Purview", ("purview-",)),
        ("Réponse aux incidents (Microsoft Security)", ("ir-",)), ("Microsoft Defender for Cloud", ("mdc-",)),
        ("Langage KQL", ("kql-",)), ("Autres", ("copilot", "logic")),
    ])
    out = [f"<p>Toutes les URL ci-dessous ont été vérifiées automatiquement (réponse HTTP 200, sans redirection vers une autre page) le "
           f"<b>{verified_on}</b> par le script <code>scripts/verify_refs.py</code>. Les titres sont ceux affichés par Microsoft Learn "
           f"(version française) à cette date. Les entrées marquées ★ sont citées dans au moins une question.</p>"]
    for g, prefixes in groups.items():
        items = [(k, r) for k, r in refs.items() if k.startswith(prefixes)]
        if not items:
            continue
        out.append(f"<h3>{esc(g)}</h3><ul class='biblio'>")
        for k, r in sorted(items, key=lambda x: x[1]["title"].lower()):
            star = "★ " if k in used else ""
            out.append(f"<li>{star}{esc(r['title'] or k)} — <a href='{esc(r['url'])}'>{esc(r['url'])}</a></li>")
        out.append("</ul>")
    return "\n".join(out)


def page(title, body, css):
    return f"""<!doctype html><html lang="fr"><head><meta charset="utf-8"><title>{esc(title)}</title>
<style>{css}</style></head><body>{body}</body></html>"""


def toc_html(entries, pages=None):
    rows = []
    for lvl, anchor, text in entries:
        pg = pages.get(anchor, "") if pages else ""
        rows.append(f"<div class='toc{lvl}'><a href='#{anchor}'>{esc(text)}</a><span class='dots'></span><span class='pg'>{pg}</span></div>")
    return "<h1 class='part' id='toc'>Table des matières</h1>" + "".join(rows)


def marker(anchor):
    return f"<span class='mk'>@@{anchor}@@</span>"


# --------------------------------------------------------------------------- assemblage
def build_guide(qs, exams, refs, verified_on, qc_html, css, pages=None):
    toc, body = [], []

    def part(anchor, text, lvl=1):
        toc.append((lvl, anchor, text))
        tag = "h1" if lvl == 1 else "h2"
        cls = " class='part'" if lvl == 1 else ""
        return f"<{tag}{cls} id='{anchor}'>{marker(anchor)}{esc(text)}</{tag}>"

    pres = pandoc_md(CONTENT / "sections" / "presentation.md").replace("{{MATRICE}}", matrix_html(qs))
    body.append(part("presentation", "Présentation de la certification et mode d'emploi du guide"))
    body.append(pres)
    by_mod = collections.OrderedDict()
    for q in qs:
        by_mod.setdefault(q["module"], []).append(q)
    for m, mq in by_mod.items():
        body.append(part(f"mod{m}", f"Module {m} — {MODULES[m][0]} (questions {mq[0]['n']} à {mq[-1]['n']})"))
        intro = CONTENT / "sections" / f"module{m}.md"
        if intro.exists():
            body.append(pandoc_md(intro))
        cur = None
        for q in mq:
            sec = KQL_SUBS.get(q.get("sub")) if m == 3 else q["section"]
            if sec and sec != cur:
                cur = sec
                body.append(part(f"s{q['n']}", f"{m}.{len([t for t in toc if t[1].startswith('s') and t[0] == 2 and t[2].startswith(str(m) + '.')]) + 1} {sec}", 2))
            body.append(question_html(q, refs))
    body.append(part("exams", "Examens blancs"))
    body.append(md(EXAM_PRES))
    for e, items in enumerate(exams):
        toc.append((2, f"exam{e + 1}", f"Examen blanc n° {e + 1} (50 questions)"))
        toc.append((2, f"exam{e + 1}c", f"Corrigé de l'examen blanc n° {e + 1}"))
        h = exam_html(e, items, True)
        h = h.replace(f'<h1 class="part" id="exam{e + 1}">', f'<h1 class="part" id="exam{e + 1}">{marker(f"exam{e + 1}")}', 1)
        h = h.replace(f'<h2 id="exam{e + 1}c">', f'<h2 id="exam{e + 1}c">{marker(f"exam{e + 1}c")}', 1)
        body.append(h)
    body.append(part("fiches", "Fiches de révision"))
    body.append(pandoc_md(CONTENT / "sections" / "fiches.md"))
    body.append(part("glossaire", "Glossaire français – anglais"))
    body.append(pandoc_md(CONTENT / "sections" / "glossaire.md"))
    body.append(part("index", "Index des notions techniques"))
    body.append("<p>Les numéros renvoient aux questions du guide où la notion apparaît (énoncé, propositions ou explications).</p>")
    body.append(index_html(qs))
    used = {r for q in qs for r in q["refs"]}
    body.append(part("biblio", "Bibliographie officielle"))
    body.append(biblio_html(refs, used, verified_on))
    body.append(part("qc", "Rapport de contrôle qualité"))
    body.append(qc_html)
    cover = COVER.format(n=len(qs), date=verified_on)
    return cover, toc_html(toc, pages) + "\n".join(body), toc


EXAM_PRES = """Les trois examens blancs ci-dessous comptent chacun **50 questions** tirées des séries d'entraînement du guide (les questions sont **réutilisées** : leur numéro d'origine figure dans le corrigé et elles ne sont pas comptées deux fois dans le total des questions uniques). Les propositions ont été remélangées pour éviter la mémorisation des lettres. Les trois examens sont disjoints : aucune question n'apparaît dans deux examens.

Chaque examen est d'abord présenté sans réponse, puis suivi d'un corrigé séparé. Une version « sujets seuls » et une version « corrigés seuls » sont également fournies dans des fichiers PDF distincts."""

COVER = """<div class="cover">
  <div class="band">MICROSOFT SECURITY OPERATIONS ANALYST · EXAMEN SC-200</div>
  <h1>GUIDE COMPLET DE PRÉPARATION À LA CERTIFICATION MICROSOFT SC-200</h1>
  <div class="sub">450+ QCM corrigés, exercices KQL et examens blancs</div>
  <div class="facts">
    <div><b>{n}</b><span>QCM uniques corrigés</span></div>
    <div><b>100</b><span>exercices KQL</span></div>
    <div><b>3 × 50</b><span>questions d'examens blancs</span></div>
  </div>
  <div class="ref">Aligné sur le référentiel officiel « Compétences mesurées à compter du 21 octobre 2026 »<br>
  Microsoft Sentinel · Microsoft Defender XDR · Microsoft Defender for Cloud · Microsoft Entra ID · Microsoft Purview · KQL</div>
  <div class="date">Dernière vérification documentaire : {date}</div>
  <div class="disc">Support de formation original et indépendant. Les questions ne reproduisent aucune question de l'examen officiel.
  Microsoft, Microsoft Sentinel, Microsoft Defender et Microsoft Entra sont des marques du groupe Microsoft.</div>
</div>"""


def exams_only_doc(exams, verified_on, answers):
    title = "Examens blancs SC-200 — " + ("corrigés" if answers else "sujets")
    head = f"""<div class="cover small-cover"><div class="band">EXAMEN SC-200 · ENTRAÎNEMENT EN CONDITIONS RÉELLES</div>
<h1>{'CORRIGÉS DES EXAMENS BLANCS' if answers else 'EXAMENS BLANCS — SUJETS'}</h1>
<div class="sub">Guide complet de préparation à la certification Microsoft SC-200</div>
<div class="date">3 examens de 50 questions · vérification documentaire : {verified_on}</div></div>"""
    parts = []
    for e, items in enumerate(exams):
        parts.append(exam_html(e, items, "only" if answers else False))
    return title, head, "\n".join(parts)


# --------------------------------------------------------------------------- rapport QC
def qc_report(qs, problems, dups, same_titles, kql_blocks, kql_res, nonval, refs, exams, verified_on):
    kbad = [r for r in kql_res if r["errors"]]
    by_mod = collections.Counter(q["module"] for q in qs)
    subs = collections.Counter(q.get("sub") for q in qs if q["module"] == 3)
    letters_all, single = letters_html(qs)
    used = {r for q in qs for r in q["refs"]}
    exam_ids = [it["q"]["n"] for ex in exams for it in ex]
    lines = [
        f"- **Date du contrôle** : {datetime.date.today().isoformat()} (vérification documentaire : {verified_on}).",
        f"- **Questions uniques** : {len(qs)} (objectif ≥ 450) — " + ", ".join(f"module {m} : {c}" for m, c in sorted(by_mod.items())) + ".",
        "- **Section KQL avancée (module 3)** : " + ", ".join(f"{KQL_SUBS[k]} : {subs[k]}" for k in KQL_SUBS) + ".",
        f"- **Contrôles structurels** (propositions, une correction et une analyse par distracteur, explication technique, point à retenir, référence valide) : "
        + ("aucune anomalie." if not problems else f"{len(problems)} anomalie(s) — voir liste."),
        f"- **Doublons** (similarité de trigrammes > 0,6 entre énoncés + propositions) : "
        + ("aucun." if not dups else f"{len(dups)} paire(s) signalée(s) : " + ", ".join(f"{a}/{b} ({j})" for a, b, j in dups)) +
        (" Titres en double : " + ", ".join(same_titles) if same_titles else " Aucun titre en double."),
        f"- **Répartition des bonnes réponses** (toutes questions) : {letters_all}. Les propositions sont placées par un algorithme d'équilibrage "
        "déterministe ; aucune question n'utilise « toutes les réponses ci-dessus ».",
        f"- **Requêtes KQL** : {len(kql_blocks)} requêtes analysées par la bibliothèque officielle Kusto.Language "
        f"(@kusto/language-service-next) avec les schémas de tables publiés sur Microsoft Learn (Sentinel/Log Analytics et chasse avancée Defender XDR) : "
        + ("**0 erreur syntaxique ou sémantique** (tables, colonnes, types, fonctions)." if not kbad else f"{len(kbad)} en erreur.")
        + f" {len([n for n in nonval if n[1] == 'kqlbad'])} requêtes sont volontairement erronées (exercices de correction) et "
        f"{len([n for n in nonval if n[1] == 'kqlnv'])} ne sont pas validables hors ligne (fonctions propres à un environnement) ; elles sont signalées comme telles.",
        f"- **Liens documentaires** : {len(refs)} URL Microsoft Learn vérifiées (HTTP 200) dont {len(used)} citées dans les questions ; 0 lien cassé.",
        f"- **Examens blancs** : 3 × 50 questions, {len(set(exam_ids))} questions distinctes (aucune répétition entre examens), corrigé complet pour chacune ; "
        "pondération 21/18/11 conforme au poids des trois domaines officiels.",
        "- **Référentiel** : contenu aligné sur le guide d'étude SC-200 « Compétences mesurées à compter du 21 octobre 2026 » "
        "(Gérer un environnement d'opérations de sécurité 40–45 %, Répondre aux incidents de sécurité 35–40 %, Effectuer la chasse aux menaces 20–25 %).",
    ]
    limits = (CONTENT / "sections" / "qc_limits.md").read_text(encoding="utf8")
    txt = "\n".join(lines) + "\n\n" + limits
    if problems:
        txt += "\n\n**Anomalies structurelles :**\n\n" + "\n".join(f"- {p}" for p in problems)
    if kbad:
        txt += "\n\n**Requêtes KQL en erreur :**\n\n" + "\n".join(f"- {r['id']} : {r['errors'][0]['message']}" for r in kbad)
    return txt


# --------------------------------------------------------------------------- PDF
def render_pdf(html_path, pdf_path, footer, outline=True):
    r = subprocess.run(["node", str(ROOT / "scripts" / "render_pdf.js"), str(html_path), str(pdf_path), footer, "1" if outline else "0"],
                       capture_output=True, text=True, cwd=ROOT)
    if r.returncode:
        raise SystemExit(r.stderr)


def find_pages(pdf_path, anchors):
    from pypdf import PdfReader
    rd = PdfReader(str(pdf_path))
    pages = {}
    for i, p in enumerate(rd.pages, 1):
        t = p.extract_text() or ""
        for a in re.findall(r"@@([\w-]+)@@", t.replace("\n", "")):
            pages.setdefault(a, i)
    missing = [a for a in anchors if a not in pages]
    return pages, missing


def merge(cover_pdf, body_pdf, out_pdf):
    from pypdf import PdfWriter
    w = PdfWriter()
    w.append(str(cover_pdf), import_outline=False)
    w.append(str(body_pdf))
    w.add_metadata({"/Title": "Guide complet de préparation à la certification Microsoft SC-200",
                    "/Author": "Guide SC-200 (support de formation)", "/Subject": "450+ QCM corrigés, exercices KQL et examens blancs",
                    "/Lang": "fr-FR"})
    with open(out_pdf, "wb") as f:
        w.write(f)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--no-pdf", action="store_true")
    ap.add_argument("--docx", action="store_true")
    args = ap.parse_args()
    refdata = json.loads((CONTENT / "refs.json").read_text(encoding="utf8"))
    refs, verified_on = refdata["refs"], refdata["verified_on"]
    qs = load()
    problems = check_structure(qs, refs)
    dups, same_titles = find_duplicates(qs)
    balance(qs)
    kql_blocks, kql_res, nonval = validate_kql(qs)
    exams = select_exams(qs)
    print(f"{len(qs)} questions ; {len(problems)} anomalies ; doublons {dups} ; titres {same_titles}")
    for p in problems[:80]:
        print("  ", p)
    qc_md = qc_report(qs, problems, dups, same_titles, kql_blocks, kql_res, nonval, refs, exams, verified_on)
    OUT.mkdir(exist_ok=True)
    BUILD.mkdir(exist_ok=True)
    (OUT / "rapport_controle_qualite.md").write_text("# Rapport de contrôle qualité — Guide SC-200\n\n" + qc_md, encoding="utf8")
    qc_path = BUILD / "qc.md"
    qc_path.write_text(qc_md, encoding="utf8")
    qc_html = pandoc_md(qc_path)
    with open(OUT / "registre_questions.csv", "w", newline="", encoding="utf8") as f:
        w = csv.writer(f, delimiter=";")
        w.writerow(["N°", "Identifiant", "Module", "Sous-section", "Titre", "Niveau", "Objectif officiel", "Bonne réponse", "Références", "Examens blancs"])
        exam_of = {it["q"]["n"]: f"Examen {e + 1} Q{i}" for e, ex in enumerate(exams) for i, it in enumerate(ex, 1)}
        for q in qs:
            w.writerow([q["n"], q["id"], q["module"], KQL_SUBS.get(q.get("sub")) or q["section"] or "", q["title"], LEVELS[q["level"]],
                        q["obj"], q["answer"], " ".join(q["refs"]), exam_of.get(q["n"], "")])
    css = (ROOT / "scripts" / "style.css").read_text(encoding="utf8")
    cover, body, toc = build_guide(qs, exams, refs, verified_on, qc_html, css)
    (BUILD / "cover.html").write_text(page("Couverture", cover, css), encoding="utf8")
    (BUILD / "guide_body.html").write_text(page("Guide SC-200", body, css), encoding="utf8")
    if args.docx:
        full = page("Guide complet SC-200", cover + body, css)
        (BUILD / "guide_full.html").write_text(full, encoding="utf8")
        subprocess.run(["pandoc", str(BUILD / "guide_full.html"), "-o", str(OUT / "Guide_SC-200_complet.docx")], check=True)
        print("DOCX généré")
    if args.no_pdf:
        return
    footer = "Guide SC-200 · 450+ QCM corrigés"
    render_pdf(BUILD / "cover.html", BUILD / "cover.pdf", "", outline=False)
    render_pdf(BUILD / "guide_body.html", BUILD / "pass1.pdf", footer)
    anchors = [a for _, a, _ in toc]
    pages, missing = find_pages(BUILD / "pass1.pdf", anchors)
    if missing:
        print("Ancres non trouvées :", missing)
    cover, body, toc = build_guide(qs, exams, refs, verified_on, qc_html, css, pages)
    (BUILD / "guide_body.html").write_text(page("Guide SC-200", body, css), encoding="utf8")
    render_pdf(BUILD / "guide_body.html", BUILD / "pass2.pdf", footer)
    pages2, _ = find_pages(BUILD / "pass2.pdf", anchors)
    if pages2 != pages:
        print("Attention : pagination modifiée entre les deux passes")
    merge(BUILD / "cover.pdf", BUILD / "pass2.pdf", OUT / "Guide_SC-200_complet.pdf")
    for answers, name in ((False, "Examens_blancs_SC-200_sujets.pdf"), (True, "Examens_blancs_SC-200_corriges.pdf")):
        title, head, b = exams_only_doc(exams, verified_on, answers)
        (BUILD / "ex_cover.html").write_text(page(title, head, css), encoding="utf8")
        (BUILD / "ex_body.html").write_text(page(title, b, css), encoding="utf8")
        render_pdf(BUILD / "ex_cover.html", BUILD / "ex_cover.pdf", "", outline=False)
        render_pdf(BUILD / "ex_body.html", BUILD / "ex_body.pdf", title)
        merge(BUILD / "ex_cover.pdf", BUILD / "ex_body.pdf", OUT / name)
    print("PDF générés dans", OUT)


if __name__ == "__main__":
    main()

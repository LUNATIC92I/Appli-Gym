// Exécute chaque exemple avec Bootstrap 5.3.3 (local) dans Chromium et en fait une capture d'écran.
// Signale toute erreur JavaScript ou ressource introuvable : un exemple doit fonctionner sans erreur.
const fs = require('fs');
let chromium;
try { ({ chromium } = require('playwright')); } catch (e) { ({ chromium } = require('/opt/node22/lib/node_modules/playwright')); }
(async () => {
  const jobs = JSON.parse(fs.readFileSync(process.argv[2], 'utf8'));
  const browser = await chromium.launch();
  let errors = 0;
  for (const j of jobs) {
    const page = await browser.newPage({ viewport: { width: j.width, height: 60 }, deviceScaleFactor: 1.6 });
    const pbs = [];
    page.on('pageerror', e => pbs.push('JS: ' + e.message));
    page.on('console', m => { if (m.type() === 'error') pbs.push('console: ' + m.text()); });
    page.on('requestfailed', r => pbs.push('ressource introuvable: ' + r.url()));
    await page.goto('file://' + j.html, { waitUntil: 'load' });
    await page.evaluate(() => document.fonts.ready);
    if (j.click) {
      for (const sel of j.click.split('|')) { await page.click(sel); await page.waitForTimeout(700); }
    }
    if (j.wait) await page.waitForTimeout(j.wait);
    const h = await page.evaluate(() => Math.ceil(document.body.getBoundingClientRect().bottom + parseFloat(getComputedStyle(document.body).marginBottom)));
    const modalOpen = await page.evaluate(() => !!document.querySelector('.modal.show, .offcanvas.show'));
    const height = modalOpen ? Math.max(520, Math.min(h, j.maxh)) : Math.max(40, Math.min(h, j.maxh));
    if (modalOpen) await page.setViewportSize({ width: j.width, height });
    await page.setViewportSize({ width: j.width, height });
    await page.waitForTimeout(150);
    await page.screenshot({ path: j.png, clip: { x: 0, y: 0, width: j.width, height } });
    if (pbs.length) { errors++; console.log('PROBLÈME', j.html, pbs.join(' | ')); }
    await page.close();
  }
  await browser.close();
  console.log(`${jobs.length} exemples exécutés, ${errors} avec erreurs`);
  if (errors) process.exit(2);
})().catch(e => { console.error(e); process.exit(1); });

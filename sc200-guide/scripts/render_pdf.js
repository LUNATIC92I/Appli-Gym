// Conversion HTML -> PDF avec Chromium (Playwright), format A4, pied de page numéroté.
// Usage : node render_pdf.js entree.html sortie.pdf "texte du pied de page" [outline 1|0]
const path = require('path');
let chromium;
try { ({ chromium } = require('playwright')); } catch (e) { ({ chromium } = require('/opt/node22/lib/node_modules/playwright')); }

(async () => {
  const [src, dst, footer, outline] = process.argv.slice(2);
  const browser = await chromium.launch();
  const page = await browser.newPage();
  await page.goto('file://' + path.resolve(src), { waitUntil: 'load', timeout: 0 });
  const withFooter = !!(footer && footer.length > 0);
  await page.pdf({
    path: dst,
    format: 'A4',
    printBackground: true,
    outline: outline === '1',
    tagged: true,
    displayHeaderFooter: withFooter,
    headerTemplate: '<span></span>',
    footerTemplate: withFooter
      ? `<div style="width:100%;font-family:Inter,'Liberation Sans',sans-serif;font-size:7.5px;color:#5b6b7f;padding:0 14mm;display:flex;justify-content:space-between;">
           <span>${footer}</span><span>Page <span class="pageNumber"></span> / <span class="totalPages"></span></span></div>`
      : '<span></span>',
    margin: withFooter ? { top: '14mm', bottom: '16mm', left: '14mm', right: '14mm' } : { top: '0', bottom: '0', left: '0', right: '0' },
    timeout: 0,
  });
  await browser.close();
})().catch(e => { console.error(e); process.exit(1); });

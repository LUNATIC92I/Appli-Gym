#!/usr/bin/env python3
"""Génère le cours « Bootstrap 5 pour les jeunes développeurs » au format PDF.

Étapes :
 1. lit les chapitres Markdown (chapitres/*.md) ;
 2. extrait chaque bloc de code marqué `.demo`, le valide (html-validate), l'exécute
    dans Chromium avec Bootstrap 5.3.3 local et en fait une capture d'écran insérée
    juste après le code (« Ce que tu vois dans le navigateur ») ;
 3. convertit le tout en HTML avec Pandoc, ajoute couverture et sommaire paginé ;
 4. imprime en PDF (Chromium / Playwright) en deux passes pour numéroter le sommaire.

Usage : python3 scripts/build.py [--no-pdf]
"""
import hashlib
import html
import json
import pathlib
import re
import subprocess
import sys

ROOT = pathlib.Path(__file__).resolve().parent.parent
CHAP = ROOT / "chapitres"
BUILD = ROOT / "build"
IMG = BUILD / "img"
DEMO = BUILD / "demo"
OUT = ROOT / "output"
NM = ROOT / "node_modules"
BS_CSS = (NM / "bootstrap/dist/css/bootstrap.min.css").as_uri()
BS_JS = (NM / "bootstrap/dist/js/bootstrap.bundle.min.js").as_uri()
BI_CSS = (NM / "bootstrap-icons/font/bootstrap-icons.min.css").as_uri()
CDN_CSS = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
CDN_JS = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
CDN_BI = "https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"

DEMO_RE = re.compile(r"^```\{([^}]*\.demo[^}]*)\}\n(.*?)\n```\s*$", re.S | re.M)


def attrs(s):
    a = dict(re.findall(r'(\w+)="([^"]*)"', s))
    a["classes"] = re.findall(r"\.(\w[\w-]*)", s)
    return a


PAGE_TMPL = """<!doctype html>
<html lang="fr">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Démo</title>
<link href="{css}" rel="stylesheet">
<link href="{bi}" rel="stylesheet">
</head>
<body class="{bodycls}">
{code}
<script src="{js}"></script>
</body>
</html>
"""


def local_page(code, fragment, bodycls="p-3"):
    if fragment:
        return PAGE_TMPL.format(css=BS_CSS, bi=BI_CSS, js=BS_JS, code=code, bodycls=bodycls)
    code = code.replace(CDN_CSS, BS_CSS).replace(CDN_JS, BS_JS).replace(CDN_BI, BI_CSS)
    # en local (file://), l'empreinte SRI et crossorigin empêcheraient le chargement : on les retire
    return re.sub(r'\s+(integrity="[^"]*"|crossorigin="anonymous")', "", code)


def validation_page(code, fragment):
    """Page complète avec les URL CDN, pour la validation HTML."""
    if fragment:
        return PAGE_TMPL.format(css=CDN_CSS, bi=CDN_BI, js=CDN_JS, code=code, bodycls="p-3")
    return code


def make_placeholder(name):
    """Crée une image d'exemple colorée pour images/NOM.jpg."""
    from PIL import Image, ImageDraw, ImageFont
    path = IMG / "images" / name
    if path.exists():
        return
    path.parent.mkdir(parents=True, exist_ok=True)
    h = int(hashlib.md5(name.encode()).hexdigest(), 16)
    palettes = [((79, 70, 229), (236, 72, 153)), ((14, 165, 233), (34, 197, 94)), ((249, 115, 22), (234, 179, 8)),
                ((168, 85, 247), (59, 130, 246)), ((16, 185, 129), (6, 182, 212)), ((239, 68, 68), (249, 115, 22))]
    c1, c2 = palettes[h % len(palettes)]
    w, hgt = (800, 500) if not name.startswith(("prof", "avatar", "eleve")) else (500, 500)
    if name.startswith(("logo", "icone")):
        w, hgt = 200, 200
    im = Image.new("RGB", (w, hgt))
    d = ImageDraw.Draw(im)
    for y in range(hgt):
        t = y / hgt
        d.line([(0, y), (w, y)], fill=tuple(int(c1[i] * (1 - t) + c2[i] * t) for i in range(3)))
    over = Image.new("RGBA", (w, hgt), (0, 0, 0, 0))
    do = ImageDraw.Draw(over)
    rnd = h
    for _ in range(7):
        rnd = (rnd * 1103515245 + 12345) % (2 ** 31)
        x, y, r = rnd % w, (rnd // w) % hgt, 20 + rnd % 90
        do.ellipse([x - r, y - r, x + r, y + r], fill=(255, 255, 255, 55))
    label = name.rsplit(".", 1)[0].replace("-", " ").replace("_", " ").title()
    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", max(18, w // 16))
    except OSError:
        font = ImageFont.load_default()
    tw = do.textlength(label, font=font)
    cy = hgt * 0.38
    do.rounded_rectangle([w / 2 - tw / 2 - 18, cy - w // 16, w / 2 + tw / 2 + 18, cy + w // 16], 18, fill=(0, 0, 0, 120))
    do.text((w / 2 - tw / 2, cy - w // 28), label, fill=(255, 255, 255, 255), font=font)
    im = Image.alpha_composite(im.convert("RGBA"), over)
    im.convert("RGB").save(path, quality=88)


def process_demos(md_text, chap_name, jobs, validations):
    counter = [0]

    def repl(m):
        a = attrs(m.group(1))
        code = m.group(2)
        counter[0] += 1
        key = f"{chap_name}-{counter[0]:02d}"
        fragment = "page" not in a["classes"]
        for img in re.findall(r'images/([\w.-]+\.(?:jpg|png))', code):
            make_placeholder(img)
        src = local_page(code, fragment, "" if "plein" in a["classes"] else "p-3")
        DEMO.mkdir(parents=True, exist_ok=True)
        (IMG / "images").mkdir(parents=True, exist_ok=True)
        if not (DEMO / "images").is_symlink():
            (DEMO / "images").symlink_to(IMG / "images", target_is_directory=True)
        page = DEMO / f"{key}.html"
        page.write_text(src, encoding="utf8")
        (BUILD / "valid").mkdir(exist_ok=True)
        vp = BUILD / "valid" / f"{key}.html"
        vp.write_text(validation_page(code, fragment), encoding="utf8")
        validations.append(str(vp))
        widths = [int(w) for w in str(a.get("largeur", 900)).split("|")]
        clics = a.get("clic", "").split(";") if len(widths) > 1 else [a.get("clic", "")]
        pngs = []
        for i, wdt in enumerate(widths):
            png = IMG / (f"{key}.png" if len(widths) == 1 else f"{key}-{i + 1}.png")
            pngs.append(png)
            jobs.append({"html": str(page), "png": str(png), "width": wdt,
                         "click": clics[i] if i < len(clics) else "", "wait": int(a.get("attente", 0)),
                         "maxh": int(a.get("hauteurmax", 1400))})
        lang_cls = " ".join("." + c for c in a["classes"] if c not in ("demo", "page", "plein"))
        if len(widths) == 1:
            legend = a.get("legende", "Ce que tu vois dans le navigateur")
            width_note = f" (écran de {widths[0]} pixels de large)" if "largeur" in a else ""
            imgs = f"![]({pngs[0].as_posix()}){{width={min(160, round(widths[0] * 0.11))}mm}}"
        else:
            legend = a.get("legende", "Le même code sur plusieurs écrans")
            width_note = " — " + ", ".join(
                f"{'à gauche' if i == 0 else ('à droite' if i == len(widths) - 1 else 'au milieu')} : {w} px"
                for i, w in enumerate(widths))
            k = min(0.11, 172 / sum(widths))
            imgs = " ".join(f"![]({pg.as_posix()}){{width={round(w * k)}mm .cote}}" for pg, w in zip(pngs, widths))
        return (f"```{{{lang_cls}}}\n{code}\n```\n\n::: rendu\n**🖥️ {legend}{width_note} :**\n\n"
                f"{imgs}\n:::\n")

    return DEMO_RE.sub(repl, md_text)


def render_all(jobs):
    (BUILD / "jobs.json").write_text(json.dumps(jobs), encoding="utf8")
    r = subprocess.run(["node", str(ROOT / "scripts" / "shoot.js"), str(BUILD / "jobs.json")], capture_output=True, text=True)
    print(r.stdout[-3000:], r.stderr[-3000:])
    if r.returncode:
        raise SystemExit("échec du rendu des démos")


def validate_html(files):
    cfg = ROOT / "scripts" / "htmlvalidate.json"
    r = subprocess.run(["npx", "html-validate", "-c", str(cfg), "-f", "text"] + files, capture_output=True, text=True, cwd=ROOT)
    out = (r.stdout + r.stderr).strip()
    return r.returncode == 0, out


def marker(anchor):
    return f'<span class="mk">@@{anchor}@@</span>'


def build_html(md_all, pages=None):
    tmpl = ROOT / "scripts" / "template.html"
    r = subprocess.run(["pandoc", "-f", "markdown+fenced_divs+pipe_tables+emoji", "-t", "html5", "-s",
                        "--template", str(tmpl), "--highlight-style", str(ROOT / "scripts" / "kids.theme"),
                        "--metadata", "title=Bootstrap 5"],
                       input=md_all, capture_output=True, text=True, check=True)
    body = r.stdout
    toc = []

    def add_marker(m):
        tag, ident, cls, inner = m.group(1), m.group(2), m.group(3) or "", m.group(4)
        if "nontoc" in cls:
            return m.group(0)
        toc.append((tag, ident, html.unescape(re.sub("<[^>]+>", "", inner))))
        mk = marker(ident) if pages is None else '<span class="mk"></span>'
        return f'<{tag} id="{ident}"{cls}>{mk}{inner}</{tag}>'

    body = re.sub(r'<(h1|h2) id="([^"]+)"( class="[^"]*")?>(.*?)</\1>', add_marker, body, flags=re.S)
    rows = []
    for tag, ident, text in toc:
        pg = pages.get(ident, "") if pages else ""
        rows.append(f'<div class="toc-{tag}"><a href="#{ident}">{html.escape(text)}</a><span class="dots"></span><span class="pg">{pg}</span></div>')
    toc_html = '<section class="toc"><h1 class="nontoc">📚 Sommaire</h1>' + "".join(rows) + "</section>"
    body = body.replace("<!--TOC-->", toc_html)
    return body, [t[1] for t in toc]


def find_pages(pdf, anchors):
    from pypdf import PdfReader
    pages = {}
    for i, p in enumerate(PdfReader(str(pdf)).pages, 1):
        t = (p.extract_text() or "").replace("\n", "")
        for a in re.findall(r"@@([\w-]+)@@", t):
            pages.setdefault(a, i)
    return pages, [a for a in anchors if a not in pages]


def render_pdf(src, dst, footer):
    r = subprocess.run(["node", str(ROOT / "scripts" / "render_pdf.js"), str(src), str(dst), footer], capture_output=True, text=True)
    if r.returncode:
        raise SystemExit(r.stderr)


def main():
    IMG.mkdir(parents=True, exist_ok=True)
    jobs, validations, parts = [], [], []
    only = [a.split("=", 1)[1] for a in sys.argv if a.startswith("--only=")]
    for f in sorted(CHAP.glob("*.md")):
        if only and not f.stem.startswith(only[0]):
            continue
        parts.append(process_demos(f.read_text(encoding="utf8"), f.stem, jobs, validations))
    md_all = "\n\n".join(parts)
    md_all = re.sub(r"^(\s+)- ([A-D])\) ", r"\1- **\2)** ", md_all, flags=re.M)
    ok, report = validate_html(validations)
    (BUILD / "validation.txt").write_text(report, encoding="utf8")
    print(f"Validation HTML de {len(validations)} exemples :", "OK" if ok else "ERREURS")
    if not ok:
        print(report[-6000:])
    render_all(jobs)
    if "--no-pdf" in sys.argv:
        return
    body, anchors = build_html(md_all)
    page = BUILD / "cours.html"
    page.write_text(body, encoding="utf8")
    render_pdf(page, BUILD / "pass1.pdf", "Bootstrap 5 — le cours des jeunes développeurs")
    pages, missing = find_pages(BUILD / "pass1.pdf", anchors)
    if missing:
        print("Ancres introuvables :", missing[:10])
    body, _ = build_html(md_all, pages)
    page.write_text(body, encoding="utf8")
    render_pdf(page, BUILD / "pass2.pdf", "Bootstrap 5 — le cours des jeunes développeurs")
    cover = ROOT / "scripts" / "cover.html"
    render_pdf(cover, BUILD / "cover.pdf", "")
    from pypdf import PdfReader, PdfWriter
    w = PdfWriter()
    w.append(str(BUILD / "cover.pdf"), import_outline=False)
    w.append(str(BUILD / "pass2.pdf"))
    w.add_metadata({"/Title": "Bootstrap 5 — Le cours des jeunes développeurs", "/Author": "Cours Bootstrap 5 (10-14 ans)",
                    "/Subject": "Apprendre Bootstrap 5.3 de zéro jusqu'au site complet", "/Lang": "fr-FR"})
    OUT.mkdir(exist_ok=True)
    with open(OUT / "Cours_Bootstrap5_Jeunes_Developpeurs.pdf", "wb") as fh:
        w.write(fh)
    n = len(PdfReader(str(OUT / "Cours_Bootstrap5_Jeunes_Developpeurs.pdf")).pages)
    print(f"PDF généré : {n} pages, {len(jobs)} captures d'exemples")


if __name__ == "__main__":
    main()

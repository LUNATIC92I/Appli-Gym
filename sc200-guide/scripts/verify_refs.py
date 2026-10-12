#!/usr/bin/env python3
"""Vérifie les URL Microsoft Learn de la bibliographie et produit content/refs.json.

Usage : python3 scripts/verify_refs.py
Chaque ligne de content/refs_sources.txt contient « clé chemin » ; le chemin est
relatif à https://learn.microsoft.com/fr-fr/. Le script télécharge chaque page,
relève le code HTTP, l'URL finale (après redirection) et le titre <h1> en français.
"""
import concurrent.futures
import datetime
import html
import json
import pathlib
import re
import urllib.request

ROOT = pathlib.Path(__file__).resolve().parent.parent
SRC = ROOT / "content" / "refs_sources.txt"
OUT = ROOT / "content" / "refs.json"
BASE = "https://learn.microsoft.com/fr-fr/"


def check(key, path):
    url = BASE + path
    # Les pages KQL existent en plusieurs « vues » : on fixe la vue Microsoft Sentinel.
    if path.startswith("kusto/") and "?" not in path:
        url += "?view=microsoft-sentinel"
    try:
        req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0 (SC-200 guide link checker)"})
        with urllib.request.urlopen(req, timeout=45) as r:
            body = r.read().decode("utf8", "replace")
            final = r.geturl()
            status = r.status
        h1 = re.search(r"<h1[^>]*>(.*?)</h1>", body, re.S)
        title = html.unescape(re.sub(r"<[^>]+>", "", h1.group(1))).strip() if h1 else ""
        title = re.sub(r"\s+", " ", title).replace("\xa0", " ")
        return key, {"url": url, "status": status, "final_url": final, "title": title}
    except Exception as e:  # noqa: BLE001
        return key, {"url": url, "status": getattr(e, "code", str(e)), "final_url": "", "title": ""}


def main():
    pairs = [l.split() for l in SRC.read_text(encoding="utf8").splitlines() if l.strip()]
    with concurrent.futures.ThreadPoolExecutor(12) as ex:
        res = dict(ex.map(lambda p: check(*p), pairs))
    bad = {k: v for k, v in res.items() if v["status"] != 200}
    out = {
        "verified_on": datetime.date.today().isoformat(),
        "refs": {k: res[k] for k, _ in pairs},
    }
    OUT.write_text(json.dumps(out, ensure_ascii=False, indent=1), encoding="utf8")
    print(f"{len(res)} URL vérifiées, {len(bad)} en erreur")
    for k, v in bad.items():
        print("  ERREUR", k, v["url"], v["status"])


if __name__ == "__main__":
    main()

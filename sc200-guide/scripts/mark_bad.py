#!/usr/bin/env python3
"""Outil d'édition : marque comme « kqlbad » (requête volontairement erronée) des blocs KQL.

Usage : python3 scripts/mark_bad.py Q237#2 Q238#3 ...
Hypothèse : dans la question, les blocs de code apparaissent dans l'ordre du fichier
(énoncé, puis propositions), ce qui correspond à la numérotation du validateur lorsque
les sections de correction ne contiennent pas de code avant les propositions.
"""
import pathlib
import re
import sys

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parent))
import build  # noqa: E402

qs = build.load()
by_n = {q["n"]: q for q in qs}
for arg in sys.argv[1:]:
    n, k = re.match(r"Q(\d+)#(\d+)", arg).groups()
    q = by_n[int(n)]
    path = build.CONTENT / "questions" / q["file"]
    lines = path.read_text(encoding="utf8").split("\n")
    start = q["line"] - 1
    end = next((i for i in range(start + 1, len(lines)) if lines[i].startswith("### ")), len(lines))
    count, done = 0, False
    in_code = False
    for i in range(start, end):
        m = re.search(r"```(kqlx?|kqlbad|kqlnv)?\b", lines[i])
        if "```" in lines[i]:
            opening = not in_code
            in_code = not in_code
            if opening and m and m.group(1) in ("kql", "kqlx"):
                count += 1
                if count == int(k):
                    lines[i] = lines[i].replace("```" + m.group(1), "```kqlbad", 1)
                    done = True
                    break
            elif opening:
                count += 1 if (m and m.group(1)) else 0
    print(arg, "OK" if done else "NON TROUVÉ", q["file"], q["title"])
    path.write_text("\n".join(lines), encoding="utf8")

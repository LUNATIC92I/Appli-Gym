"""Analyse des fichiers de questions (content/questions/*.txt).

Format d'une question :

    ### Titre de la question
    @ niveau=D|I|A ref=cle1,cle2 [obj=1|2|3] [multi=2] [fixed] [sub=...]
    ?: Énoncé (plusieurs lignes possibles, blocs ```kql / ```kqlx / ```kqlbad autorisés)
    +: Proposition correcte
    -: Proposition incorrecte || Justification de l'erreur
    =: Correction détaillée
    T: Explication technique
    X: Exemple pratique (facultatif)
    R: Résultat attendu (facultatif, questions KQL)
    S: Hypothèses liées au schéma (facultatif, questions KQL)
    M: Amélioration proposée (facultatif, questions KQL)
    !: Point à retenir pour l'examen

En-têtes de fichier : « @@ module=N » et « @@ section=Titre » (ouvre une sous-section).
Les lignes commençant par « # » hors bloc de code (et autres que « ### ») sont des commentaires.
"""
import pathlib
import re

LEVELS = {"D": "Débutant", "I": "Intermédiaire", "A": "Avancé"}
MARKERS = {"?": "stem", "=": "correction", "T": "tech", "X": "example", "R": "result",
           "S": "schema", "M": "improve", "!": "key"}


class ParseError(Exception):
    pass


def parse_file(path):
    path = pathlib.Path(path)
    lines = path.read_text(encoding="utf8").splitlines()
    module, section = None, None
    questions, cur, field, in_code = [], None, None, False

    def flush():
        if cur is not None:
            questions.append(cur)

    for no, raw in enumerate(lines, 1):
        line = raw.rstrip()
        if line.strip().startswith("```"):
            in_code = not in_code
        elif not in_code:
            if line.startswith("@@"):
                for k, v in re.findall(r"(\w+)=(.+?)(?=\s+\w+=|$)", line[2:].strip()):
                    if k == "module":
                        module = int(v)
                    elif k == "section":
                        section = v.strip()
                continue
            if line.startswith("### "):
                flush()
                cur = {"title": line[4:].strip(), "module": module, "section": section,
                       "file": path.name, "line": no, "options": [], "meta": {}}
                field = None
                continue
            if line.startswith("#"):
                continue
            if cur is None:
                if line.strip():
                    raise ParseError(f"{path.name}:{no}: texte hors question")
                continue
            if line.startswith("@ "):
                for tok in line[2:].split():
                    k, _, v = tok.partition("=")
                    cur["meta"][k] = v if v else True
                continue
            m = re.match(r"^([?=TXRSM!+\-]):\s?(.*)$", line)
            if m:
                mk, txt = m.groups()
                if txt.strip().startswith("```"):
                    in_code = True
                if mk in "+-":
                    cur["options"].append({"correct": mk == "+", "text": txt, "line": no})
                    field = ("opt", len(cur["options"]) - 1)
                else:
                    key = MARKERS[mk]
                    if key in cur:
                        raise ParseError(f"{path.name}:{no}: section {mk} en double")
                    cur[key] = txt
                    field = ("sec", key)
                continue
        # ligne de continuation (ou ligne de bloc de code)
        if cur is None or field is None:
            if line.strip():
                raise ParseError(f"{path.name}:{no}: ligne orpheline : {line[:60]}")
            continue
        if field[0] == "opt":
            o = cur["options"][field[1]]
            o["text"] += "\n" + raw.rstrip()
        else:
            cur[field[1]] += "\n" + raw.rstrip()
    flush()
    if in_code:
        raise ParseError(f"{path.name}: bloc de code non fermé")
    for q in questions:
        for o in q["options"]:
            txt, _, why = o["text"].partition("||")
            o["text"], o["why"] = txt.strip(), why.strip()
        for k in MARKERS.values():
            if k in q:
                q[k] = q[k].strip()
    return questions

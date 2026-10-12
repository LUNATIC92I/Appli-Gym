# Guide complet de préparation à la certification Microsoft SC-200

451 QCM originaux corrigés (dont 101 exercices KQL), 3 examens blancs de 50 questions, fiches de révision,
glossaire, index et bibliographie Microsoft Learn vérifiée — entièrement en français.

## Livrables (`output/`)

| Fichier | Contenu |
|---|---|
| `Guide_SC-200_complet.pdf` | Guide complet : couverture, sommaire paginé, présentation, 6 modules, examens blancs + corrigés, fiches, glossaire, index, bibliographie, rapport de contrôle qualité |
| `Examens_blancs_SC-200_sujets.pdf` | Les 3 examens blancs sans corrigé (entraînement en conditions réelles) |
| `Examens_blancs_SC-200_corriges.pdf` | Corrigés séparés des 3 examens blancs |
| `Guide_SC-200_complet.docx` | Version modifiable (conversion automatique, mise en page simplifiée) |
| `registre_questions.csv` | Registre de toutes les questions (numéro, module, niveau, objectif officiel, réponse, références, présence en examen blanc) |
| `rapport_controle_qualite.md` | Rapport de contrôle qualité généré automatiquement |

## Régénérer les documents

Prérequis : Python 3 (`pypdf`), Node.js, Playwright/Chromium, Pandoc.

```bash
npm install                        # bibliothèque Kusto.Language (validation KQL)
python3 scripts/verify_refs.py     # revérifie les URL Microsoft Learn -> content/refs.json
python3 scripts/fetch_schemas.py   # retélécharge les schémas de tables -> content/schemas.json
python3 scripts/build.py --docx    # contrôles + PDF (+ DOCX)
```

## Organisation des sources

- `content/questions/*.txt` : questions, au format décrit dans `scripts/qparse.py` (les propositions sont
  mélangées et équilibrées automatiquement ; la bonne réponse est marquée `+:`).
- `content/sections/*.md` : présentation, introductions des modules, fiches, glossaire, limites du contrôle qualité.
- `content/refs_sources.txt` / `content/refs.json` : bibliographie et résultat de la vérification des liens.
- `content/schemas.json` : schémas officiels des tables utilisés par le validateur KQL.
- `scripts/` : parseur, générateur, validateur KQL (`kql_validate.js`), rendu PDF (`render_pdf.js`), styles.

Support de formation original et indépendant ; il ne reproduit aucune question de l'examen officiel.

# Bootstrap 5 — le cours des jeunes développeurs

Cours complet en français sur Bootstrap 5.3 pour les 10–14 ans, débutants.

- **PDF final** : `output/Cours_Bootstrap5_Jeunes_Developpeurs.pdf` (148 pages)
- 18 chapitres + révisions : chaque chapitre contient des encadrés « À retenir » / « Astuce », 3 exercices corrigés (niveaux 1 à 3) et un quiz de 5 questions corrigé
- Projet final : le site complet de l'« École des Pins », expliqué étape par étape
- 145 captures d'écran générées en exécutant réellement chaque exemple (Bootstrap 5.3.3, Bootstrap Icons 1.11.3)

## Régénérer le PDF

Prérequis : Python 3 (pypdf, Pillow), Pandoc, Node.js, Chromium (Playwright).

```bash
npm install                      # bootstrap, bootstrap-icons, html-validate
python3 scripts/build.py         # validation HTML + captures + PDF
python3 scripts/build.py --no-pdf --only=13   # vérifier un seul chapitre
```

Le script valide chaque exemple avec html-validate, l'exécute dans Chromium (échec en cas d'erreur JavaScript ou de ressource manquante) et insère la capture sous le code.

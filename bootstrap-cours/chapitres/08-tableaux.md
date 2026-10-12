# Chapitre 8 — Les tableaux {#ch8}

::: chapitre-intro
Un tableau range des informations en **lignes** et en **colonnes** : un emploi du temps, un classement de tournoi, une liste de notes… Bootstrap rend les tableaux lisibles et jolis avec une seule classe.
:::

::: analogie
Un tableau, c'est comme une **grille de bataille navale** ou l'**emploi du temps** de ta classe : chaque case se trouve au croisement d'une ligne (un jour) et d'une colonne (une heure).
:::

## Les balises d'un tableau HTML

```{.ascii}
 <table>                          ← le tableau entier
 ├── <thead>                      ← l'en-tête (la ligne des titres)
 │    └── <tr>                    ← une ligne (table row)
 │         ├── <th>Nom</th>       ← une case de titre (table header)
 │         └── <th>Score</th>
 └── <tbody>                      ← le corps (les données)
      ├── <tr>
      │    ├── <td>Léo</td>       ← une case de donnée (table data)
      │    └── <td>120</td>
      └── <tr> ...
```

## Créer un tableau Bootstrap

La seule classe indispensable est `table`, à placer sur la balise `<table>`.

```{.html .demo largeur="700"}
<!-- class="table" : Bootstrap met en forme tout le tableau -->
<table class="table">
  <!-- Une légende qui décrit le tableau (utile pour l'accessibilité) -->
  <caption>Classement du tournoi de ping-pong</caption>
  <!-- L'en-tête du tableau -->
  <thead>
    <tr>
      <!-- scope="col" : cette case est le titre de toute la colonne -->
      <th scope="col">Place</th>
      <th scope="col">Joueur</th>
      <th scope="col">Points</th>
    </tr>
  </thead>
  <!-- Le corps du tableau : une ligne <tr> par joueur -->
  <tbody>
    <tr>
      <!-- scope="row" : cette case est le titre de la ligne -->
      <th scope="row">1</th>
      <td>Yanis</td>
      <td>42</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Chloé</td>
      <td>39</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td>Sami</td>
      <td>31</td>
    </tr>
  </tbody>
</table>
```

::: astuce
La balise `<caption>` affiche une petite légende sous le tableau. Elle aide tout le monde, et surtout les personnes qui utilisent un lecteur d'écran, à comprendre de quoi parle le tableau. Pour la mettre au-dessus, ajoute la classe `caption-top` sur la `<table>`.
:::

## Mettre en forme le tableau

On peut ajouter d'autres classes **à côté** de `table` :

| Classe | Effet |
|---|---|
| `table-striped` | Lignes « zébrées » (une sur deux est grisée) |
| `table-hover` | La ligne change de couleur sous la souris |
| `table-bordered` | Bordures autour de toutes les cases |
| `table-borderless` | Aucune bordure |
| `table-sm` | Tableau plus compact (cases moins hautes) |
| `table-dark` | Tableau sombre |
| `table-primary`, `table-success`… | Tableau ou ligne colorés |

```{.html .demo largeur="700"}
<!-- Zébré + bordures + compact -->
<table class="table table-striped table-bordered table-sm caption-top">
  <caption>Emploi du temps du lundi</caption>
  <!-- table-dark sur l'en-tête : une ligne de titres sombre -->
  <thead class="table-dark">
    <tr>
      <th scope="col">Heure</th>
      <th scope="col">Matière</th>
      <th scope="col">Salle</th>
    </tr>
  </thead>
  <tbody>
    <tr><th scope="row">8 h</th><td>Mathématiques</td><td>B12</td></tr>
    <tr><th scope="row">9 h</th><td>Français</td><td>A04</td></tr>
    <!-- table-success : cette ligne est verte -->
    <tr class="table-success"><th scope="row">10 h</th><td>Sport</td><td>Gymnase</td></tr>
    <tr><th scope="row">11 h</th><td>Anglais</td><td>A07</td></tr>
  </tbody>
</table>
```

On peut aussi colorier **une seule case** : `<td class="table-danger">`.

## Aligner le contenu

- `text-center` ou `text-end` sur une case pour centrer ou aligner à droite (pratique pour les nombres).
- `align-middle` sur la `<table>` pour centrer verticalement le contenu des cases.

## Les tableaux responsives

Un tableau avec beaucoup de colonnes **dépasse** de l'écran d'un téléphone. Solution : l'entourer d'une `div` avec la classe `table-responsive`. Le tableau peut alors **défiler horizontalement**, sans casser la page.

```{.ascii}
 📱 Sans table-responsive          📱 Avec table-responsive
 ┌──────────────┐                  ┌──────────────┐
 │Nom│Lun│Mar│Mer│Jeu│Ven  ← ça    │Nom│Lun│Mar│M ⇄ on fait
 │Léo│ ✓ │ ✓ │ ✗ │ ✓ │ ✓   dépasse │Léo│ ✓ │ ✓ │ ✗   défiler
 └──────────────┘                  └──────────────┘   avec le doigt
```

```{.html .demo largeur="380"}
<!-- La div table-responsive permet au tableau de défiler horizontalement -->
<div class="table-responsive">
  <table class="table table-bordered">
    <thead>
      <tr>
        <th scope="col">Élève</th>
        <th scope="col">Lundi</th>
        <th scope="col">Mardi</th>
        <th scope="col">Mercredi</th>
        <th scope="col">Jeudi</th>
        <th scope="col">Vendredi</th>
      </tr>
    </thead>
    <tbody>
      <tr><th scope="row">Léo</th><td>Présent</td><td>Présent</td><td>Absent</td><td>Présent</td><td>Présent</td></tr>
      <tr><th scope="row">Maya</th><td>Présente</td><td>Présente</td><td>Présente</td><td>Présente</td><td>Absente</td></tr>
    </tbody>
  </table>
</div>
```

Sur un écran de téléphone, on voit le début du tableau et on fait glisser pour voir la suite.

::: retenir
- Structure : `<table>` ➜ `<thead>` / `<tbody>` ➜ `<tr>` ➜ `<th>` (titre) ou `<td>` (donnée).
- Classe de base : `table`. Options : `table-striped`, `table-hover`, `table-bordered`, `table-sm`, `table-dark`, `table-success`…
- Pour les téléphones : une `div class="table-responsive"` autour du tableau.
- Accessibilité : `<caption>` et `scope="col"` / `scope="row"`.
:::

## Exercices du chapitre 8

:::: {.exercice .niveau1}
### Exercice 8.1 — Mes résultats en sport

**🎯 Objectif :** créer un tableau simple.

**📝 Consignes :** crée un tableau zébré de 3 lignes avec les colonnes « Épreuve » et « Résultat » (course : 9,2 s ; saut : 3,40 m ; lancer : 18 m).

**🔍 Indices :** `table table-striped`, un `<thead>` et un `<tbody>`.

::: solution
```{.html .demo largeur="500"}
<table class="table table-striped">
  <thead>
    <tr>
      <th scope="col">Épreuve</th>
      <th scope="col">Résultat</th>
    </tr>
  </thead>
  <tbody>
    <!-- Une ligne par épreuve -->
    <tr><td>Course de 50 m</td><td>9,2 s</td></tr>
    <tr><td>Saut en longueur</td><td>3,40 m</td></tr>
    <tr><td>Lancer de balle</td><td>18 m</td></tr>
  </tbody>
</table>
```

**Explication :** `table-striped` grise une ligne sur deux pour suivre les lignes facilement. Les titres sont dans `<th>` avec `scope="col"`, les données dans `<td>`.
:::
::::

:::: {.exercice .niveau2}
### Exercice 8.2 — Le classement coloré

**🎯 Objectif :** colorier des lignes selon leur signification.

**📝 Consignes :** crée le classement de 4 équipes (rang, équipe, points) avec un en-tête sombre, l'effet de survol, la 1re ligne en vert (`table-success`) et la dernière en rouge (`table-danger`). Les points sont alignés à droite.

**🔍 Indices :** `table table-hover`, `thead class="table-dark"`, `text-end` sur les cases des points.

::: solution
```{.html .demo largeur="600"}
<table class="table table-hover caption-top">
  <caption>Championnat de foot de l'école</caption>
  <thead class="table-dark">
    <tr>
      <th scope="col">Rang</th>
      <th scope="col">Équipe</th>
      <!-- text-end : aligné à droite, comme les nombres -->
      <th scope="col" class="text-end">Points</th>
    </tr>
  </thead>
  <tbody>
    <!-- Le premier en vert -->
    <tr class="table-success"><th scope="row">1</th><td>Les Lions</td><td class="text-end">21</td></tr>
    <tr><th scope="row">2</th><td>Les Faucons</td><td class="text-end">17</td></tr>
    <tr><th scope="row">3</th><td>Les Dauphins</td><td class="text-end">12</td></tr>
    <!-- Le dernier en rouge -->
    <tr class="table-danger"><th scope="row">4</th><td>Les Tortues</td><td class="text-end">5</td></tr>
  </tbody>
</table>
```

**Explication :** les classes `table-success` et `table-danger` se placent sur une ligne `<tr>`. `text-end` aligne les nombres à droite pour pouvoir les comparer plus facilement. `table-hover` colore la ligne survolée par la souris.
:::
::::

:::: {.exercice .niveau3}
### Exercice 8.3 — Le carnet de lecture responsive

**🎯 Objectif :** créer un tableau large qui reste utilisable sur téléphone.

**📝 Consignes :** crée un tableau de 5 colonnes (titre, auteur, genre, pages, note) avec 3 livres. Il doit être bordé, compact, centré verticalement, défiler sur téléphone et avoir une légende au-dessus. La note est un badge coloré.

**🔍 Indices :** `div.table-responsive` ➜ `table table-bordered table-sm align-middle caption-top`. Badge : `<span class="badge text-bg-success">5/5</span>`.

::: solution
```{.html .demo largeur="400"}
<!-- Défilement horizontal sur petit écran -->
<div class="table-responsive">
  <!-- Bordé, compact, contenu centré verticalement, légende en haut -->
  <table class="table table-bordered table-sm align-middle caption-top">
    <caption>📖 Mon carnet de lecture</caption>
    <thead class="table-light">
      <tr>
        <th scope="col">Titre</th>
        <th scope="col">Auteur</th>
        <th scope="col">Genre</th>
        <th scope="col" class="text-end">Pages</th>
        <th scope="col">Note</th>
      </tr>
    </thead>
    <tbody>
      <tr><td>Le dragon du collège</td><td>C. Rivière</td><td>Fantastique</td><td class="text-end">212</td><td><span class="badge text-bg-success">5/5</span></td></tr>
      <tr><td>Mission Lune</td><td>H. Benali</td><td>Science-fiction</td><td class="text-end">180</td><td><span class="badge text-bg-primary">4/5</span></td></tr>
      <tr><td>Le mystère du grenier</td><td>Z. Lambert</td><td>Enquête</td><td class="text-end">156</td><td><span class="badge text-bg-warning">3/5</span></td></tr>
    </tbody>
  </table>
</div>
```

**Explication :** `table-responsive` évite que le tableau dépasse de l'écran : sur téléphone, on fait défiler. `align-middle` centre verticalement le contenu des cases (utile quand certaines cases ont deux lignes). `table-light` donne un en-tête gris clair. Les badges colorés rendent la note visible d'un coup d'œil.
:::
::::

## Quiz du chapitre 8

::: quiz
1. Quelle balise crée une **ligne** de tableau ?
    - A) `<th>`
    - B) `<tr>`
    - C) `<line>`
    - D) `<td>`
2. Quelle classe est indispensable pour un tableau Bootstrap ?
    - A) `tableau`
    - B) `grid`
    - C) `table-bs`
    - D) `table`
3. Que fait `table-striped` ?
    - A) Le tableau est barré
    - B) Une ligne sur deux est grisée
    - C) Les cases sont plus grandes
    - D) Le tableau devient sombre
4. Comment éviter qu'un grand tableau dépasse de l'écran d'un téléphone ?
    - A) L'entourer d'une `div class="table-responsive"`
    - B) Ajouter `table-small`
    - C) Ajouter `overflow-phone`
    - D) Supprimer des colonnes au hasard
5. Quelle balise utilise-t-on pour une case de **titre** ?
    - A) `<title>`
    - B) `<td>`
    - C) `<th>`
    - D) `<caption>`
:::

::: reponses
1. **B** — `<tr>` = *table row* (ligne). `<td>` et `<th>` sont des cases.
2. **D** — `table` est la classe de base ; les autres classes s'ajoutent à côté.
3. **B** — Les lignes sont « zébrées » pour mieux les suivre.
4. **A** — `table-responsive` permet le défilement horizontal.
5. **C** — `<th>` = *table header* (case de titre) ; `<caption>` est la légende du tableau entier.
:::

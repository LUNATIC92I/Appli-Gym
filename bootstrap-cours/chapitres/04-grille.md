# Chapitre 4 — Le système de grille {#ch4}

::: chapitre-intro
C'est **LE** super-pouvoir de Bootstrap ! La grille permet de placer des éléments côte à côte, en colonnes, et de les réorganiser automatiquement selon la taille de l'écran. Prends ton temps sur ce chapitre : tout le reste du cours l'utilise.
:::

## L'idée : une page découpée en 12 colonnes

Bootstrap découpe chaque ligne de ta page en **12 parts égales** appelées **colonnes**. Tu choisis combien de parts chaque élément occupe.

::: analogie
Imagine une **tablette de chocolat de 12 carrés** posée sur une ligne. Tu peux la partager :

- en 2 morceaux de 6 carrés (6 + 6 = 12) ;
- en 3 morceaux de 4 carrés (4 + 4 + 4 = 12) ;
- en un gros morceau de 8 et un petit de 4 (8 + 4 = 12).

Le total doit faire **12** pour remplir toute la ligne !
:::

```{.ascii}
 Les 12 colonnes d'une ligne (row)
 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 │1 │2 │3 │4 │5 │6 │7 │8 │9 │10│11│12│
 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
 ┌─────────────────┬─────────────────┐
 │     col-6       │     col-6       │   6 + 6 = 12
 └─────────────────┴─────────────────┘
 ┌───────────┬───────────┬───────────┐
 │   col-4   │   col-4   │   col-4   │   4 + 4 + 4 = 12
 └───────────┴───────────┴───────────┘
 ┌───────────────────────┬───────────┐
 │         col-8         │   col-4   │   8 + 4 = 12
 └───────────────────────┴───────────┘
```

## Les trois pièces de la grille : container, row, col

La grille utilise toujours trois niveaux emboîtés, comme des poupées russes :

1. un **container** (la boîte qui centre, chapitre 3) ;
2. une **row** (*ligne* en anglais) : elle contient des colonnes ;
3. des **col** (*colonnes*) : c'est **dans** les colonnes que l'on met le contenu.

```{.ascii}
 container ┌───────────────────────────────────────────┐
           │ row ┌───────────────────────────────────┐ │
           │     │ col ┌─────────┐ col ┌───────────┐ │ │
           │     │     │contenu  │     │ contenu   │ │ │
           │     │     └─────────┘     └───────────┘ │ │
           │     └───────────────────────────────────┘ │
           └───────────────────────────────────────────┘
```

::: retenir
**Règle d'or de la grille :** `container` ➜ `row` ➜ `col` ➜ ton contenu. Les colonnes doivent être les **enfants directs** d'une `row`.
:::

## Premières colonnes : des colonnes automatiques

Avec la classe `col` toute seule, Bootstrap partage la ligne **en parts égales** entre toutes les colonnes. Pour bien voir les colonnes dans les exemples, on leur donne une bordure et un fond.

```{.html .demo largeur="900"}
<div class="container">
  <!-- Une ligne avec 3 colonnes automatiques : chacune prend 1/3 -->
  <div class="row">
    <div class="col border bg-light p-2">Colonne A</div>
    <div class="col border bg-light p-2">Colonne B</div>
    <div class="col border bg-light p-2">Colonne C</div>
  </div>
</div>
```

Ajoute une quatrième `col` et chacune prendra automatiquement un quart. Pratique !

## Choisir la largeur : col-1 à col-12

Pour choisir précisément la largeur, on écrit le **nombre de parts** après `col-` : de `col-1` (très étroit) à `col-12` (toute la ligne).

```{.html .demo largeur="900"}
<div class="container">
  <!-- 8 + 4 = 12 : une grande colonne et une petite -->
  <div class="row mb-2">
    <div class="col-8 border bg-info-subtle p-2">col-8 : le contenu principal</div>
    <div class="col-4 border bg-warning-subtle p-2">col-4 : à côté</div>
  </div>
  <!-- 3 + 6 + 3 = 12 : trois colonnes, celle du milieu est la plus large -->
  <div class="row">
    <div class="col-3 border bg-success-subtle p-2">col-3</div>
    <div class="col-6 border bg-danger-subtle p-2">col-6</div>
    <div class="col-3 border bg-success-subtle p-2">col-3</div>
  </div>
</div>
```

::: attention
Si le total dépasse 12, les colonnes en trop **passent à la ligne suivante**. Par exemple `col-8` + `col-6` = 14 : la `col-6` descend en dessous. Ce n'est pas une erreur, mais souvent ce n'est pas ce que l'on voulait !
:::

## La grille responsive : changer de disposition selon l'écran

Voici la vraie magie ! On peut dire : « sur téléphone, prends toute la largeur ; sur tablette, prends la moitié ; sur ordinateur, prends un tiers ». On ajoute pour cela le **nom du palier** dans la classe : `col-md-6` veut dire « 6 parts **à partir** du palier `md` (768 px) ».

| Classe | Signification |
|---|---|
| `col-12` | 12 parts sur **tous** les écrans |
| `col-sm-6` | 6 parts à partir de 576 px (grands téléphones) |
| `col-md-4` | 4 parts à partir de 768 px (tablettes) |
| `col-lg-3` | 3 parts à partir de 992 px (ordinateurs) |
| `col-xl-2` | 2 parts à partir de 1200 px (grands écrans) |

On peut **combiner** plusieurs classes sur la même colonne : `class="col-12 col-md-6 col-lg-4"`.

```{.ascii}
 class="col-12 col-md-6 col-lg-4"  sur 3 cartes

 📱 Téléphone (< 768 px)   📟 Tablette (≥ 768 px)     💻 Ordinateur (≥ 992 px)
 ┌──────────────┐          ┌──────────┬──────────┐    ┌───────┬───────┬───────┐
 │   Carte 1    │          │ Carte 1  │ Carte 2  │    │Carte 1│Carte 2│Carte 3│
 ├──────────────┤          ├──────────┼──────────┘    └───────┴───────┴───────┘
 │   Carte 2    │          │ Carte 3  │
 ├──────────────┤          └──────────┘
 │   Carte 3    │
 └──────────────┘
```

Voici le même code affiché sur un **ordinateur** (1000 px), puis sur un **téléphone** (380 px) :

```{.html .demo largeur="1000|380"}
<div class="container">
  <div class="row">
    <!-- Téléphone : 12 parts (toute la ligne) / tablette : 6 / ordinateur : 4 -->
    <div class="col-12 col-md-6 col-lg-4 border bg-primary-subtle p-3">🐱 Carte 1</div>
    <div class="col-12 col-md-6 col-lg-4 border bg-success-subtle p-3">🐶 Carte 2</div>
    <div class="col-12 col-md-6 col-lg-4 border bg-warning-subtle p-3">🐰 Carte 3</div>
  </div>
</div>
```

::: astuce
Pour tester le responsive sans téléphone : sur ton ordinateur, **rétrécis la fenêtre du navigateur** en tirant son bord, ou appuie sur **F12** puis sur l'icône de téléphone 📱 (le « mode appareil »). Tu verras tes colonnes se réorganiser en direct !
:::

## Lire une classe de colonne

Une classe de colonne se lit toujours de la même façon :

```{.ascii}
     col  -  md  -  6
      │      │      │
      │      │      └── combien de parts sur 12 ?   (6 = la moitié)
      │      └───────── à partir de quel écran ?    (md = tablette, 768 px)
      └──────────────── c'est une colonne
```

Bootstrap est conçu « **mobile first** » (le téléphone d'abord) : une classe sans palier (`col-12`) vaut pour tous les écrans, et une classe avec palier (`col-md-6`) **remplace** la précédente à partir de ce palier.

## Les gouttières : l'espace entre les colonnes

L'espace entre les colonnes s'appelle la **gouttière** (*gutter*). On le règle sur la `row` avec les classes `g-0` (aucun espace) à `g-5` (grand espace). `gx-` règle l'espace horizontal, `gy-` l'espace vertical.

```{.html .demo largeur="900"}
<div class="container">
  <!-- g-4 : grand espace entre les colonnes, horizontalement ET verticalement -->
  <div class="row g-4">
    <!-- Le contenu est dans une div intérieure : c'est elle qui porte la couleur -->
    <div class="col-6"><div class="p-3 bg-primary text-white rounded">Bloc 1</div></div>
    <div class="col-6"><div class="p-3 bg-primary text-white rounded">Bloc 2</div></div>
    <div class="col-6"><div class="p-3 bg-primary text-white rounded">Bloc 3</div></div>
    <div class="col-6"><div class="p-3 bg-primary text-white rounded">Bloc 4</div></div>
  </div>
</div>
```

::: attention
La gouttière est un espace **à l'intérieur** des colonnes. Pour la voir, il faut mettre le fond coloré sur un élément **dans** la colonne (comme ci-dessus), pas sur la colonne elle-même.
:::

## Décaler une colonne : offset

La classe `offset-*` laisse des parts vides **avant** une colonne. Par exemple, `col-6 offset-3` : 3 parts vides, puis 6 parts de contenu (et 3 vides après) → la colonne est **centrée** !

```{.html .demo largeur="900"}
<div class="container">
  <div class="row">
    <!-- 3 parts vides, puis 6 parts : la colonne est au centre -->
    <div class="col-6 offset-3 border bg-light p-3 text-center">Je suis centrée grâce à offset-3</div>
  </div>
</div>
```

## Des grilles dans des grilles

On peut mettre une nouvelle `row` **à l'intérieur** d'une colonne. Elle est à nouveau découpée en 12 parts !

```{.html .demo largeur="900"}
<div class="container">
  <div class="row">
    <!-- Colonne de gauche : 8 parts -->
    <div class="col-8 border p-2">
      Partie principale (col-8)
      <!-- Nouvelle ligne DANS la colonne : à nouveau 12 parts -->
      <div class="row mt-2">
        <div class="col-6"><div class="bg-info-subtle p-2">Sous-colonne 1</div></div>
        <div class="col-6"><div class="bg-info-subtle p-2">Sous-colonne 2</div></div>
      </div>
    </div>
    <!-- Colonne de droite : 4 parts -->
    <div class="col-4 border p-2 bg-warning-subtle">Barre latérale (col-4)</div>
  </div>
</div>
```

## Le raccourci row-cols

Quand toutes les colonnes ont la même taille, on peut dire directement à la `row` combien de colonnes afficher par ligne : `row-cols-1`, `row-cols-md-3`, etc. Très pratique pour une galerie !

```{.html .demo largeur="900"}
<div class="container">
  <!-- 2 colonnes par ligne sur téléphone, 4 à partir de la tablette ; g-2 = petit espace -->
  <div class="row row-cols-2 row-cols-md-4 g-2">
    <div class="col"><div class="p-3 bg-success-subtle text-center">🍎</div></div>
    <div class="col"><div class="p-3 bg-success-subtle text-center">🍌</div></div>
    <div class="col"><div class="p-3 bg-success-subtle text-center">🍇</div></div>
    <div class="col"><div class="p-3 bg-success-subtle text-center">🍓</div></div>
  </div>
</div>
```

::: retenir
- La grille = `container` ➜ `row` ➜ `col`.
- Une ligne contient **12 parts** : `col-4` = 4 parts sur 12 = un tiers.
- `col` seul = parts égales automatiques.
- `col-md-6` = 6 parts **à partir** des tablettes (768 px) ; on combine : `col-12 col-md-6 col-lg-4`.
- `g-3` règle l'espace entre colonnes ; `offset-3` décale ; `row-cols-3` fixe le nombre de colonnes par ligne.
:::

## Exercices du chapitre 4

:::: {.exercice .niveau1}
### Exercice 4.1 — Deux colonnes égales

**🎯 Objectif :** créer une première grille.

**📝 Consignes :** crée une ligne avec deux colonnes de même largeur : à gauche « 🍕 Pizza », à droite « 🍔 Burger ». Ajoute une bordure à chaque colonne pour les voir.

**🔍 Indices :** container ➜ row ➜ deux `col-6` (ou deux `col`). Bordure : `border`. Espace intérieur : `p-3`.

::: solution
```{.html .demo largeur="800"}
<div class="container">
  <div class="row">
    <!-- 6 + 6 = 12 : deux moitiés -->
    <div class="col-6 border p-3">🍕 Pizza</div>
    <div class="col-6 border p-3">🍔 Burger</div>
  </div>
</div>
```

**Explication :** chaque colonne occupe 6 parts sur 12, donc la moitié de la ligne. Avec `col` sans nombre, on obtiendrait exactement le même résultat, car Bootstrap partagerait la ligne en deux parts égales.
:::
::::

:::: {.exercice .niveau2}
### Exercice 4.2 — Article et barre latérale

**🎯 Objectif :** faire une mise en page responsive classique.

**📝 Consignes :** crée une page avec un article (à gauche) et une barre latérale « Mes liens » (à droite). Sur **ordinateur** (à partir de `lg`), l'article prend 9 parts et la barre 3 parts. Sur **téléphone et tablette**, les deux prennent toute la largeur, l'une sous l'autre.

**🔍 Indices :** combine `col-12` (par défaut) et `col-lg-9` / `col-lg-3`. Ajoute `g-3` à la row.

::: solution
```{.html .demo largeur="1000"}
<div class="container">
  <!-- g-3 : espace entre l'article et la barre -->
  <div class="row g-3">
    <!-- Toute la largeur sur petit écran, 9 parts sur ordinateur -->
    <div class="col-12 col-lg-9">
      <div class="p-3 border rounded">
        <h2 class="h4">Mon voyage à la mer</h2>
        <p class="mb-0">Cet été, j'ai vu des dauphins et construit le plus grand château de sable de la plage !</p>
      </div>
    </div>
    <!-- Toute la largeur sur petit écran, 3 parts sur ordinateur -->
    <div class="col-12 col-lg-3">
      <div class="p-3 bg-body-tertiary rounded">
        <h3 class="h6">Mes liens</h3>
        <p class="mb-0 small">Photos · Vidéos · Contact</p>
      </div>
    </div>
  </div>
</div>
```

**Explication :** `col-12` s'applique à tous les écrans ; à partir de 992 px (`lg`), `col-lg-9` et `col-lg-3` prennent le relais : 9 + 3 = 12. Sur une tablette, les deux blocs s'empilent car seule `col-12` s'applique.
:::
::::

:::: {.exercice .niveau3}
### Exercice 4.3 — La galerie qui s'adapte

**🎯 Objectif :** maîtriser les colonnes responsives sur plusieurs paliers.

**📝 Consignes :** crée une galerie de 6 vignettes d'animaux (emoji + nom) : 1 par ligne sur petit téléphone, 2 par ligne à partir de `sm`, 3 à partir de `md` et 6 à partir de `xl`. Les vignettes doivent être séparées par un espace.

**🔍 Indices :** utilise `row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-xl-6` sur la `row`, et `g-3`. Chaque vignette : `<div class="col">` avec une `<div>` intérieure colorée.

::: solution
```{.html .demo largeur="700"}
<div class="container">
  <!-- 1 / 2 / 3 / 6 colonnes selon la taille d'écran ; g-3 = espace -->
  <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-xl-6 g-3">
    <!-- Chaque vignette : une colonne contenant une boîte centrée -->
    <div class="col"><div class="p-3 bg-primary-subtle rounded text-center">🦁<br>Lion</div></div>
    <div class="col"><div class="p-3 bg-success-subtle rounded text-center">🐸<br>Grenouille</div></div>
    <div class="col"><div class="p-3 bg-warning-subtle rounded text-center">🐝<br>Abeille</div></div>
    <div class="col"><div class="p-3 bg-danger-subtle rounded text-center">🦊<br>Renard</div></div>
    <div class="col"><div class="p-3 bg-info-subtle rounded text-center">🐬<br>Dauphin</div></div>
    <div class="col"><div class="p-3 bg-secondary-subtle rounded text-center">🐘<br>Éléphant</div></div>
  </div>
</div>
```

**Explication :** `row-cols-*` est un raccourci : plutôt que d'écrire des classes sur chaque colonne, on dit à la ligne combien de colonnes elle doit afficher. L'image montre un écran de 700 px (palier `sm`) : 2 vignettes par ligne. Sur 800 px, il y en aurait 3, et sur un grand écran (≥ 1200 px) les 6 seraient alignées. `<br>` est un retour à la ligne.
:::
::::

## Quiz du chapitre 4

::: quiz
1. En combien de parts Bootstrap découpe-t-il une ligne ?
    - A) 16
    - B) 12
    - C) 10
    - D) 24
2. Quel est le bon ordre d'emboîtement ?
    - A) `container` ➜ `row` ➜ `col`
    - B) `row` ➜ `container` ➜ `col`
    - C) `container` ➜ `col` ➜ `row`
    - D) `col` ➜ `row` ➜ `container`
3. Que signifie `col-md-4` ?
    - A) 4 parts uniquement sur les téléphones
    - B) 4 pixels de marge
    - C) 4 colonnes sur tous les écrans
    - D) 4 parts sur 12 à partir des tablettes (768 px)
4. Que se passe-t-il avec `col-8` + `col-6` dans la même ligne ?
    - A) La page disparaît
    - B) Bootstrap affiche une erreur
    - C) La `col-6` passe à la ligne suivante
    - D) Les colonnes deviennent plus petites
5. Quelle classe ajoute un espace entre les colonnes ?
    - A) `space-3` sur le container
    - B) `col-gap`
    - C) `g-3` sur la `row`
    - D) `offset-3`
:::

::: reponses
1. **B** — 12, car 12 se divise facilement par 2, 3, 4 et 6.
2. **A** — Container, puis ligne, puis colonnes : le contenu va dans les colonnes.
3. **D** — `md` = à partir de 768 px ; `4` = 4 parts sur 12 (un tiers).
4. **C** — 8 + 6 = 14 > 12 : la colonne qui ne rentre pas descend à la ligne suivante.
5. **C** — Les classes `g-*` (gouttières) se mettent sur la `row`. `offset-3` décale une colonne mais ne crée pas d'espace entre colonnes.
:::

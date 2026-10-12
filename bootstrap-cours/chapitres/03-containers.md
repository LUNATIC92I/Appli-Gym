# Chapitre 3 — Les containers : des boîtes pour ranger ta page {#ch3}

::: chapitre-intro
Une page bien rangée est plus agréable à lire. Bootstrap propose des **containers** (« conteneurs ») : des boîtes invisibles qui gardent ton contenu bien aligné, avec de jolies marges sur les côtés.
:::

## Pourquoi des containers ?

Sur un grand écran d'ordinateur, une ligne de texte qui va d'un bord à l'autre de l'écran est **très difficile à lire** : les yeux se perdent ! Le container limite la largeur du contenu et le **centre** dans la page.

::: analogie
Imagine un **plateau de cantine** : toutes tes assiettes sont posées dessus, bien rangées au milieu. Sans plateau, elles seraient éparpillées sur toute la table ! Le container, c'est le plateau de ta page.
:::

## Le `container` classique

La classe `container` crée une boîte **centrée** dont la largeur maximale change selon la taille de l'écran (on dit qu'elle est *responsive*). Sur un téléphone, elle prend toute la largeur (avec un petit espace sur les côtés). Sur un ordinateur, elle s'arrête à une largeur maximale et laisse des marges blanches.

```{.html .demo .plein largeur="1000"}
<!-- La page entière a un fond gris clair pour bien voir le container -->
<div class="bg-body-secondary py-3">
  <!-- container : boîte centrée de largeur limitée -->
  <!-- bg-primary text-white p-3 : fond bleu, texte blanc, espace intérieur -->
  <div class="container bg-primary text-white p-3">
    Je suis dans un <strong>container</strong> : regarde les marges à gauche et à droite !
  </div>
</div>
```

## Le `container-fluid` : toute la largeur

La classe `container-fluid` (*fluide*) crée une boîte qui prend **toujours 100 % de la largeur** de l'écran. Utile pour une grande bannière ou une barre de couleur.

```{.html .demo .plein largeur="1000"}
<div class="bg-body-secondary py-3">
  <!-- container-fluid : la boîte s'étend d'un bord à l'autre -->
  <div class="container-fluid bg-success text-white p-3">
    Je suis un <strong>container-fluid</strong> : je prends toute la largeur !
  </div>
</div>
```

## Comparaison en image

```{.ascii}
 Écran d'ordinateur (large)
 ┌──────────────────────────────────────────────────────────────┐
 │        ┌──────────────── container ─────────────────┐        │
 │ marge  │  Contenu centré, largeur maximale limitée  │  marge │
 │        └────────────────────────────────────────────┘        │
 │┌──────────────────────── container-fluid ───────────────────┐│
 ││  Contenu qui prend 100 % de la largeur, bord à bord        ││
 │└────────────────────────────────────────────────────────────┘│
 └──────────────────────────────────────────────────────────────┘

 Écran de téléphone (étroit)
 ┌────────────────────┐
 │┌── container ─────┐│   ← sur petit écran, les deux
 ││ presque toute la ││     prennent (presque) toute
 ││ largeur          ││     la largeur
 │└──────────────────┘│
 └────────────────────┘
```

## Les largeurs du container

Bootstrap définit des **paliers** de taille d'écran, appelés *breakpoints* (« points de rupture »). Pour chaque palier, le `container` a une largeur maximale :

| Taille d'écran | Exemple | Nom du palier | Largeur max du `container` |
|---|---|---|---|
| moins de 576 px | petit téléphone | (aucun, « xs ») | 100 % |
| 576 px et plus | grand téléphone | `sm` | 540 px |
| 768 px et plus | tablette | `md` | 720 px |
| 992 px et plus | petit ordinateur | `lg` | 960 px |
| 1200 px et plus | ordinateur | `xl` | 1140 px |
| 1400 px et plus | grand écran | `xxl` | 1320 px |

::: astuce
Un **pixel** (px) est un tout petit point de l'écran. Un téléphone fait environ 360 à 430 pixels de large, un ordinateur portable environ 1 300 à 1 900. Tu retrouveras ces paliers `sm`, `md`, `lg`, `xl`, `xxl` partout dans Bootstrap, surtout dans la grille (chapitre 4) et le responsive (chapitre 16).
:::

## Les containers « à palier »

Il existe aussi des containers qui sont **fluides jusqu'à un certain palier**, puis deviennent classiques : `container-sm`, `container-md`, `container-lg`, `container-xl`, `container-xxl`.

Par exemple, `container-lg` prend 100 % de la largeur sur téléphone et tablette, puis se comporte comme un `container` normal à partir de 992 px.

```{.html .demo largeur="800"}
<!-- Sur un écran de 800 px (une tablette), container-lg est encore fluide -->
<div class="container-lg bg-warning p-3 mb-2">container-lg : 100 % de large jusqu'à 992 px</div>
<!-- container-md est déjà limité à 720 px car l'écran fait plus de 768 px -->
<div class="container-md bg-info p-3">container-md : limité à 720 px dès 768 px</div>
```

## Cas pratiques : quel container choisir ?

| Situation | Container conseillé | Pourquoi |
|---|---|---|
| Le texte d'un article ou d'une page | `container` | Lignes pas trop longues, agréables à lire |
| Une bannière colorée en haut de page | `container-fluid` | La couleur doit toucher les bords |
| Un tableau de scores très large | `container-fluid` | Il a besoin de place |
| Un formulaire d'inscription | `container` | Centré et lisible |

Une astuce très utilisée : mettre un `container` **à l'intérieur** d'une bande de couleur pleine largeur. La couleur touche les bords, mais le texte reste bien centré.

```{.html .demo .plein largeur="1000"}
<!-- La bande (section) a un fond violet qui prend toute la largeur -->
<section class="bg-dark text-white py-4">
  <!-- Le contenu, lui, reste centré grâce au container -->
  <div class="container">
    <h2>Bienvenue au club de robotique</h2>
    <p class="mb-0">La couleur va d'un bord à l'autre, mais le texte reste bien aligné.</p>
  </div>
</section>
```

::: retenir
- `container` : boîte **centrée** avec une largeur maximale qui dépend de l'écran.
- `container-fluid` : boîte qui prend **toute la largeur**.
- `container-md`, `container-lg`… : fluides jusqu'au palier indiqué.
- Astuce pro : une bande pleine largeur avec un `container` dedans.
:::

::: attention
Ne mets pas un `container` dans un autre `container` : cela ajoute des marges inutiles. Un seul niveau de container suffit (sauf pour le cas de la bande colorée, où la bande n'est pas un container).
:::

## Exercices du chapitre 3

:::: {.exercice .niveau1}
### Exercice 3.1 — Container ou container-fluid ?

**🎯 Objectif :** utiliser les deux types de containers.

**📝 Consignes :** crée deux boîtes l'une sous l'autre : la première, bleue, dans un `container` avec le texte « Je suis centrée » ; la seconde, rouge, dans un `container-fluid` avec le texte « Je touche les bords ».

**🔍 Indices :** utilise `bg-primary` et `bg-danger` pour les couleurs, `text-white` pour le texte, `p-3` pour l'espace intérieur, et `mb-3` pour séparer les deux boîtes.

::: solution
```{.html .demo .plein largeur="1000"}
<!-- Boîte 1 : centrée et limitée en largeur -->
<div class="container bg-primary text-white p-3 mb-3">Je suis centrée</div>
<!-- Boîte 2 : pleine largeur -->
<div class="container-fluid bg-danger text-white p-3">Je touche les bords</div>
```

**Explication :** sur un écran large, la boîte bleue a des marges sur les côtés alors que la rouge s'étend d'un bord à l'autre. `mb-3` (*margin bottom*) ajoute un espace sous la première boîte.
:::
::::

:::: {.exercice .niveau2}
### Exercice 3.2 — La bande d'annonce

**🎯 Objectif :** combiner une bande pleine largeur et un container.

**📝 Consignes :** crée une bande jaune (`bg-warning`) pleine largeur qui annonce « 🎉 Fête de l'école samedi à 14 h ! ». Le texte doit rester aligné avec le reste de la page (dans un container) et être centré.

**🔍 Indices :** la bande est une `<div>` ou une `<section>` avec la couleur ; à l'intérieur, une `<div class="container">`. Pour centrer, `text-center`.

::: solution
```{.html .demo .plein largeur="1000"}
<!-- La bande jaune pleine largeur, avec de l'espace vertical (py-3) -->
<div class="bg-warning py-3">
  <!-- Le container garde le texte aligné ; text-center le centre -->
  <div class="container text-center fw-bold">
    🎉 Fête de l'école samedi à 14 h !
  </div>
</div>
```

**Explication :** `py-3` ajoute un espace en haut **et** en bas (*y* = vertical). `fw-bold` (*font weight bold*) met le texte en gras. La bande jaune touche les bords de l'écran, mais le texte est dans le container.
:::
::::

:::: {.exercice .niveau3}
### Exercice 3.3 — Une page en trois bandes

**🎯 Objectif :** organiser une page complète avec des bandes et des containers.

**📝 Consignes :** construis une page avec :

1. une bande sombre en haut (`bg-dark text-white`) avec le titre « Mon blog de dessins » ;
2. une partie centrale normale avec un paragraphe de bienvenue ;
3. une bande grise en bas (`bg-body-tertiary`) avec « © 2026 — Mon blog ».

Chaque partie doit avoir son contenu dans un `container`.

**🔍 Indices :** utilise trois balises : `<header>` (en-tête), `<main>` (contenu principal) et `<footer>` (pied de page). Ce sont des balises HTML « sémantiques » : elles disent à quoi sert chaque partie.

::: solution
```{.html .demo .plein largeur="1000"}
<!-- En-tête sombre pleine largeur -->
<header class="bg-dark text-white py-4">
  <div class="container">
    <h1 class="h3 mb-0">✏️ Mon blog de dessins</h1>
  </div>
</header>
<!-- Contenu principal avec de l'espace vertical -->
<main class="container py-4">
  <p class="lead">Bienvenue ! Ici, je partage mes dessins de dragons, de robots et de paysages.</p>
  <p>Reviens chaque semaine pour découvrir une nouvelle création.</p>
</main>
<!-- Pied de page gris clair -->
<footer class="bg-body-tertiary py-3">
  <div class="container text-muted small">© 2026 — Mon blog</div>
</footer>
```

**Explication ligne par ligne :**

- `<header>` contient le haut de la page ; la classe `h3` donne au `<h1>` la taille d'un titre de niveau 3 (plus petit) tout en restant le titre principal ; `mb-0` enlève la marge sous le titre.
- `<main class="container py-4">` : le `main` est lui-même le container, avec de l'espace en haut et en bas.
- `<footer>` : la bande grise ; `small` rend le texte plus petit et `text-muted` le rend gris.
:::
::::

## Quiz du chapitre 3

::: quiz
1. Que fait la classe `container` ?
    - A) Elle crée un bouton
    - B) Elle cache le contenu
    - C) Elle colore le fond en bleu
    - D) Elle crée une boîte centrée avec une largeur maximale
2. Que fait `container-fluid` ?
    - A) Elle prend toujours toute la largeur de l'écran
    - B) Elle centre le texte
    - C) Elle rend le texte liquide
    - D) Elle crée une animation
3. Sur un téléphone de 375 px de large, quelle est la largeur d'un `container` ?
    - A) Presque toute la largeur (100 %)
    - B) La moitié de l'écran
    - C) 1140 px
    - D) 540 px
4. Comment faire une bande de couleur qui touche les bords, avec un texte bien aligné ?
    - A) Mettre deux `container` l'un dans l'autre
    - B) C'est impossible avec Bootstrap
    - C) Mettre la couleur sur une `<div>` pleine largeur et un `container` à l'intérieur
    - D) Mettre la couleur sur un `container`
5. À partir de quelle largeur d'écran le palier `lg` commence-t-il ?
    - A) 768 px
    - B) 992 px
    - C) 576 px
    - D) 1400 px
:::

::: reponses
1. **D** — `container` centre le contenu et limite sa largeur selon l'écran.
2. **A** — *fluid* veut dire fluide : la boîte s'étire sur 100 % de la largeur.
3. **A** — En dessous de 576 px, le `container` prend toute la largeur (avec un petit espace intérieur).
4. **C** — C'est l'astuce pro : la couleur sur la bande, le `container` dedans pour aligner le texte.
5. **B** — `lg` (*large*) commence à 992 px. `sm` = 576, `md` = 768, `xl` = 1200, `xxl` = 1400.
:::

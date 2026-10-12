# Chapitre 12 — Les badges {#ch12}

::: chapitre-intro
Un **badge** est une petite étiquette colorée : « Nouveau », « 3 messages », « -50 % »… Il sert à compter, à signaler une nouveauté ou à mettre en avant une information courte.
:::

::: analogie
Le badge, c'est comme la **petite bulle rouge** qui apparaît sur l'icône d'une application de ton téléphone pour dire « 3 nouveaux messages ». Ou comme l'**autocollant** « Nouveau ! » sur un paquet de céréales.
:::

## Créer un badge

Un badge est un `<span>` avec la classe `badge` et une couleur, de préférence avec `text-bg-*` pour un texte toujours lisible. Il prend automatiquement la taille du texte qui l'entoure.

```{.html .demo largeur="700"}
<!-- Le badge prend la taille du titre dans lequel il se trouve -->
<h3>Mes messages <span class="badge text-bg-primary">4</span></h3>
<h5>Le jeu du mois <span class="badge text-bg-success">Nouveau</span></h5>
<!-- Toutes les couleurs, côte à côte -->
<span class="badge text-bg-primary">Primary</span>
<span class="badge text-bg-secondary">Secondary</span>
<span class="badge text-bg-success">Success</span>
<span class="badge text-bg-danger">Danger</span>
<span class="badge text-bg-warning">Warning</span>
<span class="badge text-bg-info">Info</span>
<span class="badge text-bg-light">Light</span>
<span class="badge text-bg-dark">Dark</span>
```

## Les badges « pilule »

Avec `rounded-pill`, le badge a des bords complètement arrondis, comme une gélule.

```{.html .demo largeur="700"}
<!-- rounded-pill : forme de pilule -->
<span class="badge rounded-pill text-bg-danger">-50 %</span>
<span class="badge rounded-pill text-bg-warning">Bientôt</span>
<span class="badge rounded-pill text-bg-info">Gratuit</span>
```

## Compteurs dans des boutons

Un badge dans un bouton permet d'afficher un **compteur**.

```{.html .demo largeur="700"}
<!-- Le badge est placé dans le bouton, après le texte -->
<button type="button" class="btn btn-primary">
  Messages <span class="badge text-bg-light">7</span>
</button>
<button type="button" class="btn btn-outline-danger">
  <i class="bi bi-heart-fill" aria-hidden="true"></i> J'aime <span class="badge text-bg-danger">128</span>
</button>
```

## Les notifications : un badge posé sur le coin

Pour faire la petite bulle de notification **sur le coin** d'un bouton, on utilise les classes de position (chapitre 15) : `position-relative` sur le bouton et `position-absolute top-0 start-100 translate-middle` sur le badge.

```{.html .demo largeur="600"}
<!-- position-relative : le bouton sert de repère pour placer le badge -->
<button type="button" class="btn btn-dark position-relative mt-3 ms-2">
  <i class="bi bi-bell-fill" aria-hidden="true"></i> Notifications
  <!-- position-absolute top-0 start-100 : en haut à droite du bouton -->
  <!-- translate-middle : on centre la bulle sur le coin -->
  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill text-bg-danger">
    12
    <!-- visually-hidden : texte invisible, mais lu par les lecteurs d'écran -->
    <span class="visually-hidden">notifications non lues</span>
  </span>
</button>
```

::: astuce
La classe `visually-hidden` cache un texte à l'écran tout en le laissant lisible par les lecteurs d'écran. Ici, une personne aveugle entendra « 12 notifications non lues » au lieu de juste « 12 ».
:::

## Badges dans une liste

Les badges vont très bien dans un `list-group` pour afficher des compteurs alignés à droite.

```{.html .demo largeur="600"}
<!-- d-flex justify-content-between : le texte à gauche, le badge à droite -->
<ul class="list-group">
  <li class="list-group-item d-flex justify-content-between align-items-center">
    Livres lus ce mois-ci <span class="badge text-bg-primary rounded-pill">6</span>
  </li>
  <li class="list-group-item d-flex justify-content-between align-items-center">
    Devoirs à rendre <span class="badge text-bg-warning rounded-pill">2</span>
  </li>
  <li class="list-group-item d-flex justify-content-between align-items-center">
    Retards <span class="badge text-bg-success rounded-pill">0</span>
  </li>
</ul>
```

::: retenir
- Badge = `<span class="badge text-bg-couleur">`.
- `rounded-pill` = forme de pilule.
- Compteur dans un bouton : badge placé dans le `<button>`.
- Bulle de notification : `position-relative` sur le parent + `position-absolute top-0 start-100 translate-middle` sur le badge.
- `visually-hidden` : texte réservé aux lecteurs d'écran.
:::

## Exercices du chapitre 12

:::: {.exercice .niveau1}
### Exercice 12.1 — Les nouveautés

**🎯 Objectif :** ajouter des badges dans des titres.

**📝 Consignes :** crée trois titres `h4` de jeux : le premier avec un badge vert « Nouveau », le deuxième avec un badge rouge « -30 % », le troisième avec un badge gris « Épuisé ».

**🔍 Indices :** `badge text-bg-success`, `text-bg-danger`, `text-bg-secondary`.

::: solution
```{.html .demo largeur="600"}
<h4>Course de kart <span class="badge text-bg-success">Nouveau</span></h4>
<h4>Puzzle des planètes <span class="badge text-bg-danger">-30 %</span></h4>
<h4>Jeu de l'oie géant <span class="badge text-bg-secondary">Épuisé</span></h4>
```

**Explication :** le badge est placé **dans** le titre : il prend donc la taille du titre. La couleur aide à comprendre l'information (vert = nouveau, rouge = promotion, gris = indisponible).
:::
::::

:::: {.exercice .niveau2}
### Exercice 12.2 — La boîte de réception

**🎯 Objectif :** utiliser des badges comme compteurs dans une liste.

**📝 Consignes :** crée une liste de 3 dossiers (Reçus, Envoyés, Importants) avec un compteur en pilule à droite de chacun.

**🔍 Indices :** `list-group-item d-flex justify-content-between align-items-center` et `badge rounded-pill`.

::: solution
```{.html .demo largeur="500"}
<ul class="list-group">
  <!-- Le texte à gauche, le compteur à droite -->
  <li class="list-group-item d-flex justify-content-between align-items-center">
    📥 Reçus <span class="badge text-bg-primary rounded-pill">14</span>
  </li>
  <li class="list-group-item d-flex justify-content-between align-items-center">
    📤 Envoyés <span class="badge text-bg-secondary rounded-pill">3</span>
  </li>
  <li class="list-group-item d-flex justify-content-between align-items-center">
    ⭐ Importants <span class="badge text-bg-danger rounded-pill">1</span>
  </li>
</ul>
```

**Explication :** `justify-content-between` écarte le texte et le badge aux deux extrémités de la ligne ; `align-items-center` les aligne verticalement.
:::
::::

:::: {.exercice .niveau3}
### Exercice 12.3 — Les icônes de notification

**🎯 Objectif :** placer des bulles de notification sur des boutons-icônes.

**📝 Consignes :** crée deux boutons ronds contenant seulement une icône (enveloppe et panier), chacun avec une bulle rouge de notification sur le coin (5 et 2). Le tout doit rester accessible.

**🔍 Indices :** bouton `position-relative` + `aria-label` ; badge `position-absolute top-0 start-100 translate-middle` + texte `visually-hidden`. `rounded-circle` rend le bouton rond.

::: solution
```{.html .demo largeur="500"}
<!-- Bouton rond avec une enveloppe ; aria-label décrit le bouton -->
<button type="button" class="btn btn-outline-primary rounded-circle position-relative mt-3 me-4" aria-label="Messages">
  <i class="bi bi-envelope-fill" aria-hidden="true"></i>
  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill text-bg-danger">5<span class="visually-hidden"> messages non lus</span></span>
</button>
<!-- Bouton rond avec un panier -->
<button type="button" class="btn btn-outline-success rounded-circle position-relative mt-3" aria-label="Panier">
  <i class="bi bi-cart-fill" aria-hidden="true"></i>
  <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill text-bg-danger">2<span class="visually-hidden"> articles dans le panier</span></span>
</button>
```

**Explication :** le bouton est le repère (`position-relative`) ; le badge est placé dans son coin supérieur droit (`top-0 start-100`) puis recentré sur ce coin (`translate-middle`). `mt-3` laisse de la place au-dessus pour que la bulle ne soit pas coupée. Les textes `visually-hidden` complètent les nombres pour les lecteurs d'écran.
:::
::::

## Quiz du chapitre 12

::: quiz
1. Quelle balise utilise-t-on généralement pour un badge ?
    - A) `<div>`
    - B) `<form>`
    - C) `<table>`
    - D) `<span>`
2. Quelle classe donne une forme de pilule ?
    - A) `pill`
    - B) `circle`
    - C) `badge-round`
    - D) `rounded-pill`
3. Pourquoi un badge dans un `<h2>` est-il plus grand que dans un paragraphe ?
    - A) C'est une erreur de Bootstrap
    - B) Parce que les titres agrandissent toujours les images
    - C) Parce qu'il prend la taille du texte qui l'entoure
    - D) Il faut ajouter `badge-lg`
4. Que fait `visually-hidden` ?
    - A) Supprime le texte de la page
    - B) Cache le texte à l'écran mais le laisse lisible par les lecteurs d'écran
    - C) Met le texte en blanc
    - D) Rend le texte flou
5. Quelle classe faut-il sur le bouton pour placer une bulle sur son coin ?
    - A) `corner`
    - B) `float-end`
    - C) `position-relative`
    - D) `position-absolute`
:::

::: reponses
1. **D** — `<span>` est une balise « en ligne », qui s'insère dans le texte.
2. **D** — `rounded-pill` arrondit complètement les bords.
3. **C** — La taille du badge s'adapte au texte qui l'entoure.
4. **B** — Le texte reste accessible aux personnes aveugles sans encombrer l'écran.
5. **C** — Le parent est `position-relative` ; le badge est `position-absolute`.
:::

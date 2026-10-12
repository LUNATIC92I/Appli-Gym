# Chapitre 17 — Les icônes Bootstrap Icons {#ch17}

::: chapitre-intro
**Bootstrap Icons** est une bibliothèque gratuite de plus de **2 000 petits dessins** : maison, cœur, étoile, poubelle, flèches… On les a déjà croisées dans les chapitres précédents ; voyons maintenant comment bien les utiliser.
:::

::: analogie
Les icônes sont comme les **panneaux** du code de la route : un simple dessin suffit pour comprendre « danger » ou « parking », même sans lire. Sur un site, une loupe veut dire « rechercher » et une enveloppe « message ».
:::

## Installer Bootstrap Icons

Les icônes sont un fichier CSS **séparé** de Bootstrap. On l'ajoute dans le `<head>`, juste après celui de Bootstrap :

```html
<!-- Le CSS de Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<!-- Le CSS de Bootstrap Icons (version 1.11.3) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet"
      integrity="sha384-XGjxtQfXaH2tnPFa9x+ruJTuLE3Aa6LhHSWRr1XeTyhezb4abCG4ccI5AkVDxqC+" crossorigin="anonymous">
```

## Afficher une icône

Une icône s'écrit avec une balise `<i>` qui porte deux classes : `bi` (« c'est une Bootstrap Icon ») et `bi-nom-de-l-icone`.

```{.html .demo largeur="700"}
<!-- bi : c'est une icône ; bi-house : laquelle -->
<p class="fs-3">
  <i class="bi bi-house" aria-hidden="true"></i>
  <i class="bi bi-house-fill" aria-hidden="true"></i>
  <i class="bi bi-heart" aria-hidden="true"></i>
  <i class="bi bi-heart-fill" aria-hidden="true"></i>
  <i class="bi bi-star" aria-hidden="true"></i>
  <i class="bi bi-star-fill" aria-hidden="true"></i>
</p>
```

::: retenir
Beaucoup d'icônes existent en deux versions : **contour** (`bi-heart`) et **remplie** (`-fill` : `bi-heart-fill`).
:::

## Taille et couleur

Une icône se comporte comme une **lettre** : on change sa taille avec `fs-1` … `fs-6` (ou `display-*`) et sa couleur avec `text-*`.

```{.html .demo largeur="700"}
<!-- fs-1 : très grande ; text-danger : rouge -->
<i class="bi bi-heart-fill fs-1 text-danger" aria-hidden="true"></i>
<!-- fs-2 : grande ; text-warning : jaune -->
<i class="bi bi-star-fill fs-2 text-warning" aria-hidden="true"></i>
<!-- fs-3 : moyenne ; text-success : verte -->
<i class="bi bi-tree-fill fs-3 text-success" aria-hidden="true"></i>
<!-- fs-4 ; text-primary : bleue -->
<i class="bi bi-droplet-fill fs-4 text-primary" aria-hidden="true"></i>
<!-- display-4 : géante ; text-info -->
<i class="bi bi-cloud-sun-fill display-4 text-info" aria-hidden="true"></i>
```

## Les icônes les plus utiles

```{.html .demo largeur="800" legende="Quelques icônes populaires et leur nom"}
<div class="row row-cols-3 row-cols-md-6 g-2 text-center small">
  <div class="col"><i class="bi bi-house-fill fs-3" aria-hidden="true"></i><br>bi-house-fill</div>
  <div class="col"><i class="bi bi-search fs-3" aria-hidden="true"></i><br>bi-search</div>
  <div class="col"><i class="bi bi-envelope-fill fs-3" aria-hidden="true"></i><br>bi-envelope-fill</div>
  <div class="col"><i class="bi bi-telephone-fill fs-3" aria-hidden="true"></i><br>bi-telephone-fill</div>
  <div class="col"><i class="bi bi-person-circle fs-3" aria-hidden="true"></i><br>bi-person-circle</div>
  <div class="col"><i class="bi bi-gear-fill fs-3" aria-hidden="true"></i><br>bi-gear-fill</div>
  <div class="col"><i class="bi bi-cart-fill fs-3" aria-hidden="true"></i><br>bi-cart-fill</div>
  <div class="col"><i class="bi bi-trash fs-3" aria-hidden="true"></i><br>bi-trash</div>
  <div class="col"><i class="bi bi-pencil-fill fs-3" aria-hidden="true"></i><br>bi-pencil-fill</div>
  <div class="col"><i class="bi bi-check-circle-fill fs-3" aria-hidden="true"></i><br>bi-check-circle-fill</div>
  <div class="col"><i class="bi bi-x-circle-fill fs-3" aria-hidden="true"></i><br>bi-x-circle-fill</div>
  <div class="col"><i class="bi bi-exclamation-triangle-fill fs-3" aria-hidden="true"></i><br>bi-exclamation-triangle-fill</div>
  <div class="col"><i class="bi bi-arrow-right fs-3" aria-hidden="true"></i><br>bi-arrow-right</div>
  <div class="col"><i class="bi bi-calendar-event fs-3" aria-hidden="true"></i><br>bi-calendar-event</div>
  <div class="col"><i class="bi bi-geo-alt-fill fs-3" aria-hidden="true"></i><br>bi-geo-alt-fill</div>
  <div class="col"><i class="bi bi-camera-fill fs-3" aria-hidden="true"></i><br>bi-camera-fill</div>
  <div class="col"><i class="bi bi-music-note-beamed fs-3" aria-hidden="true"></i><br>bi-music-note-beamed</div>
  <div class="col"><i class="bi bi-list fs-3" aria-hidden="true"></i><br>bi-list</div>
</div>
```

::: astuce
Pour trouver une icône, va sur le site officiel **icons.getbootstrap.com** et tape un mot **en anglais** dans la recherche : *cat*, *book*, *sun*, *rocket*… Clique sur l'icône pour copier son code.
:::

## Icônes et accessibilité

Un lecteur d'écran ne sait pas « lire » un dessin. Deux cas :

1. **L'icône décore un texte** (« 🏠 Accueil ») : on ajoute `aria-hidden="true"` pour que l'icône soit ignorée ; le texte suffit.
2. **L'icône est seule dans un bouton ou un lien** : on garde `aria-hidden="true"` sur l'icône et on ajoute `aria-label="…"` sur le bouton pour dire à quoi il sert.

```{.html .demo largeur="700"}
<!-- Cas 1 : icône + texte → l'icône est cachée aux lecteurs d'écran -->
<a href="index.html" class="btn btn-primary"><i class="bi bi-house-fill me-1" aria-hidden="true"></i> Accueil</a>
<!-- Cas 2 : icône seule → aria-label décrit le bouton -->
<button type="button" class="btn btn-outline-danger" aria-label="Supprimer">
  <i class="bi bi-trash" aria-hidden="true"></i>
</button>
<button type="button" class="btn btn-outline-secondary" aria-label="Paramètres">
  <i class="bi bi-gear-fill" aria-hidden="true"></i>
</button>
```

::: attention
N'écris pas `<i class="bi-heart">` sans la classe `bi` : l'icône ne s'affichera pas correctement. Il faut **toujours les deux** : `bi bi-heart`.
:::

## Exercices du chapitre 17

:::: {.exercice .niveau1}
### Exercice 17.1 — Ma météo de la semaine

**🎯 Objectif :** afficher des icônes colorées de différentes tailles.

**📝 Consignes :** affiche 3 jours avec, pour chacun, une grande icône météo colorée : soleil (jaune), nuage de pluie (bleu), neige (bleu clair).

**🔍 Indices :** `bi-sun-fill`, `bi-cloud-rain-fill`, `bi-snow` ; `fs-1` ; `text-warning`, `text-primary`, `text-info`.

::: solution
```{.html .demo largeur="600"}
<!-- d-flex + justify-content-around : 3 jours bien répartis -->
<div class="d-flex justify-content-around text-center">
  <div><i class="bi bi-sun-fill fs-1 text-warning" aria-hidden="true"></i><br>Lundi : soleil</div>
  <div><i class="bi bi-cloud-rain-fill fs-1 text-primary" aria-hidden="true"></i><br>Mardi : pluie</div>
  <div><i class="bi bi-snow fs-1 text-info" aria-hidden="true"></i><br>Mercredi : neige</div>
</div>
```

**Explication :** `fs-1` agrandit l'icône comme un texte, et la couleur `text-*` lui donne sa teinte. Le texte sous chaque icône (après `<br>`) donne l'information aux lecteurs d'écran : l'icône peut donc être `aria-hidden`.
:::
::::

:::: {.exercice .niveau2}
### Exercice 17.2 — La barre d'outils

**🎯 Objectif :** créer des boutons-icônes accessibles.

**📝 Consignes :** crée un groupe de 4 boutons avec seulement une icône : gras, italique, souligné, effacer. Chaque bouton doit être compréhensible par un lecteur d'écran.

**🔍 Indices :** `btn-group` avec `role="group"` et `aria-label` ; icônes `bi-type-bold`, `bi-type-italic`, `bi-type-underline`, `bi-eraser-fill`.

::: solution
```{.html .demo largeur="600"}
<!-- btn-group : boutons collés ; aria-label décrit le groupe -->
<div class="btn-group" role="group" aria-label="Mise en forme du texte">
  <button type="button" class="btn btn-outline-dark" aria-label="Gras"><i class="bi bi-type-bold" aria-hidden="true"></i></button>
  <button type="button" class="btn btn-outline-dark" aria-label="Italique"><i class="bi bi-type-italic" aria-hidden="true"></i></button>
  <button type="button" class="btn btn-outline-dark" aria-label="Souligné"><i class="bi bi-type-underline" aria-hidden="true"></i></button>
  <button type="button" class="btn btn-outline-danger" aria-label="Effacer"><i class="bi bi-eraser-fill" aria-hidden="true"></i></button>
</div>
```

**Explication :** chaque bouton n'a pas de texte visible, donc son `aria-label` est obligatoire. Les icônes sont `aria-hidden` pour ne pas être lues deux fois.
:::
::::

:::: {.exercice .niveau3}
### Exercice 17.3 — Les cartes « contact »

**🎯 Objectif :** utiliser les icônes dans une mise en page complète.

**📝 Consignes :** crée 3 cartes côte à côte (empilées sur téléphone) : Adresse, Téléphone, E-mail. Chaque carte a une grande icône dans un rond coloré, un titre et une information.

**🔍 Indices :** `row row-cols-1 row-cols-md-3 g-3` ; pour le rond : une `div` avec `rounded-circle`, une couleur de fond, une largeur et une hauteur fixes, et `d-inline-flex align-items-center justify-content-center`.

::: solution
```{.html .demo largeur="900"}
<div class="row row-cols-1 row-cols-md-3 g-3 text-center">
  <div class="col">
    <div class="card h-100 shadow-sm"><div class="card-body">
      <!-- Le rond : 64px de côté, icône centrée grâce à flexbox -->
      <div class="rounded-circle text-bg-primary d-inline-flex align-items-center justify-content-center mb-2" style="width: 64px; height: 64px;">
        <i class="bi bi-geo-alt-fill fs-3" aria-hidden="true"></i>
      </div>
      <h2 class="fs-5">Adresse</h2>
      <p class="card-text mb-0">12 rue des Pins, 75000 Paris</p>
    </div></div>
  </div>
  <div class="col">
    <div class="card h-100 shadow-sm"><div class="card-body">
      <div class="rounded-circle text-bg-success d-inline-flex align-items-center justify-content-center mb-2" style="width: 64px; height: 64px;">
        <i class="bi bi-telephone-fill fs-3" aria-hidden="true"></i>
      </div>
      <h2 class="fs-5">Téléphone</h2>
      <p class="card-text mb-0">01 23 45 67 89</p>
    </div></div>
  </div>
  <div class="col">
    <div class="card h-100 shadow-sm"><div class="card-body">
      <div class="rounded-circle text-bg-danger d-inline-flex align-items-center justify-content-center mb-2" style="width: 64px; height: 64px;">
        <i class="bi bi-envelope-fill fs-3" aria-hidden="true"></i>
      </div>
      <h2 class="fs-5">E-mail</h2>
      <p class="card-text mb-0">contact@ecole-des-pins.fr</p>
    </div></div>
  </div>
</div>
```

**Explication :** le rond est une boîte carrée (64 × 64 px) arrondie en cercle par `rounded-circle`. `d-inline-flex align-items-center justify-content-center` place l'icône pile au milieu, et `text-center` sur la grille centre le rond dans la carte. Ce bloc resservira dans le projet final !
:::
::::

## Quiz du chapitre 17

::: quiz
1. Quelles classes faut-il pour afficher l'icône « étoile » ?
    - A) `btn btn-star`
    - B) `icon star`
    - C) `bi-star` seulement
    - D) `bi bi-star`
2. Comment agrandir une icône ?
    - A) Ce n'est pas possible
    - B) Avec `fs-1`, `fs-2`… comme un texte
    - C) Avec `btn-lg`
    - D) Avec `width="200"`
3. Que signifie le suffixe `-fill` ?
    - A) L'icône est animée
    - B) L'icône est plus grande
    - C) L'icône est remplie (pleine)
    - D) L'icône est en couleur
4. Un bouton ne contient qu'une icône. Que faut-il ajouter sur le bouton ?
    - A) `alt`
    - B) `aria-label` qui décrit son action
    - C) `title="icône"`
    - D) Rien
5. Où ajoute-t-on le lien vers Bootstrap Icons ?
    - A) Dans chaque balise `<i>`
    - B) Dans le fichier JavaScript de Bootstrap
    - C) Dans le `<head>`, avec une balise `<link>`
    - D) À la fin du `<body>`, avec `<script>`
:::

::: reponses
1. **D** — Il faut la classe de base `bi` et la classe de l'icône `bi-star`.
2. **B** — Une icône se comporte comme une lettre : elle suit la taille du texte.
3. **C** — `bi-heart` est un contour, `bi-heart-fill` un cœur plein.
4. **B** — `aria-label` donne un nom au bouton pour les lecteurs d'écran.
5. **C** — C'est une feuille de style : `<link>` dans le `<head>`.
:::

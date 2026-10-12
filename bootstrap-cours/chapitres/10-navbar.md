# Chapitre 10 — La barre de navigation (navbar) {#ch10}

::: chapitre-intro
La **barre de navigation** est le menu en haut d'un site : le logo à gauche, les liens vers les pages (Accueil, Galerie, Contact…). Avec Bootstrap, elle se transforme toute seule en **menu « hamburger » ☰** sur les téléphones !
:::

::: analogie
La navbar est comme le **panneau d'orientation d'un parc d'attractions** : il t'indique où aller (« Montagnes russes → », « Restaurant ← »). Sur un petit plan de poche (le téléphone), on replie le panneau et on le déplie quand on en a besoin : c'est le bouton ☰.
:::

## La structure d'une navbar

```{.ascii}
 nav.navbar.navbar-expand-lg
 ┌──────────────────────────────────────────────────────────────┐
 │ div.container-fluid                                          │
 │ ┌──────────────┐                ┌───────────────────────────┐│
 │ │.navbar-brand │  [☰] ← toggler │ div.collapse.navbar-collapse│
 │ │  🏫 Mon site  │   (téléphone)  │  ul.navbar-nav            ││
 │ └──────────────┘                │   li.nav-item > a.nav-link ││
 │                                 │   Accueil  Galerie  Contact ││
 │                                 └───────────────────────────┘│
 └──────────────────────────────────────────────────────────────┘
```

- `navbar` : la barre.
- `navbar-expand-lg` : le menu est **déplié** à partir du palier `lg` (992 px) ; en dessous, il est replié derrière le bouton ☰.
- `navbar-brand` : le nom ou le logo du site.
- `navbar-toggler` : le bouton ☰ qui apparaît sur petit écran.
- `collapse navbar-collapse` : la zone qui se replie.
- `navbar-nav`, `nav-item`, `nav-link` : la liste des liens.

## Une navbar complète et responsive

Pour que le bouton ☰ ouvre le menu, le **JavaScript de Bootstrap** doit être chargé (le `<script>` du modèle de page, chapitre 2).

```{.html .demo largeur="1000|380|380" clic=";;.navbar-toggler" legende="Ordinateur, téléphone, puis téléphone après un appui sur ☰"}
<!-- La barre : navbar-expand-lg (dépliée dès 992 px), fond clair -->
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <!-- Le nom du site, cliquable, qui ramène à l'accueil -->
    <a class="navbar-brand" href="index.html">🏫 École des Pins</a>
    <!-- Le bouton ☰ : il ouvre/ferme la zone #menuPrincipal -->
    <!-- data-bs-toggle="collapse" : Bootstrap sait qu'il doit replier/déplier -->
    <!-- data-bs-target="#menuPrincipal" : la zone à replier (son id) -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuPrincipal" aria-controls="menuPrincipal" aria-expanded="false" aria-label="Ouvrir le menu">
      <!-- L'icône ☰ dessinée par Bootstrap -->
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- La zone qui se replie sur petit écran -->
    <div class="collapse navbar-collapse" id="menuPrincipal">
      <!-- La liste des liens ; me-auto pousse ce qui suit vers la droite -->
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <!-- active + aria-current="page" : la page où l'on se trouve -->
          <a class="nav-link active" aria-current="page" href="index.html">Accueil</a>
        </li>
        <li class="nav-item"><a class="nav-link" href="galerie.html">Galerie</a></li>
        <li class="nav-item"><a class="nav-link" href="activites.html">Activités</a></li>
        <li class="nav-item"><a class="nav-link" href="contact.html">Contact</a></li>
      </ul>
      <!-- Un bouton à droite de la barre -->
      <a class="btn btn-primary" href="inscription.html">S'inscrire</a>
    </div>
  </div>
</nav>
```

Sur un **téléphone**, le menu est replié : on ne voit que le nom du site et le bouton ☰ (image du milieu). Après un appui sur ☰, le menu se déplie vers le bas (image de droite).


::: attention
Le `data-bs-target` du bouton ☰ doit contenir un `#` suivi **exactement** de l'`id` de la zone à replier : `data-bs-target="#menuPrincipal"` ↔ `id="menuPrincipal"`. Une faute, et le bouton ne fait plus rien !
:::

## Les couleurs de la navbar

Pour une barre sombre, on met un fond sombre (`bg-dark` ou `bg-primary`) et on ajoute `data-bs-theme="dark"` : Bootstrap passe alors le texte et l'icône ☰ en clair.

```{.html .demo largeur="1000"}
<!-- data-bs-theme="dark" : textes et icônes clairs, adaptés au fond sombre -->
<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
  <div class="container">
    <a class="navbar-brand fw-bold" href="index.html">🚀 Club Espace</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuEspace" aria-controls="menuEspace" aria-expanded="false" aria-label="Ouvrir le menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="menuEspace">
      <!-- ms-auto : la liste est poussée tout à droite -->
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link active" aria-current="page" href="index.html">Accueil</a></li>
        <li class="nav-item"><a class="nav-link" href="planetes.html">Planètes</a></li>
        <li class="nav-item"><a class="nav-link" href="fusees.html">Fusées</a></li>
      </ul>
    </div>
  </div>
</nav>
```

::: astuce
`me-auto` (*margin end auto*) sur la liste la colle à gauche et pousse ce qui suit à droite ; `ms-auto` (*margin start auto*) pousse la liste elle-même à droite. Ces classes viennent de Flexbox (chapitre 15).
:::

## Un menu déroulant (dropdown)

Un lien de la navbar peut ouvrir un **sous-menu**. Il faut la classe `dropdown` sur le `li`, `dropdown-toggle` et `data-bs-toggle="dropdown"` sur le lien, puis une liste `dropdown-menu`.

```{.html .demo largeur="800" clic="#menuActivites" hauteurmax="320"}
<nav class="navbar navbar-expand bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.html">🎨 Atelier</a>
    <ul class="navbar-nav">
      <li class="nav-item"><a class="nav-link" href="index.html">Accueil</a></li>
      <!-- dropdown : ce lien contient un sous-menu -->
      <li class="nav-item dropdown">
        <!-- Le lien qui ouvre le sous-menu -->
        <a class="nav-link dropdown-toggle" href="#" id="menuActivites" role="button" data-bs-toggle="dropdown" aria-expanded="false">Activités</a>
        <!-- Le sous-menu -->
        <ul class="dropdown-menu">
          <li><a class="dropdown-item" href="peinture.html">🖌️ Peinture</a></li>
          <li><a class="dropdown-item" href="poterie.html">🏺 Poterie</a></li>
          <!-- Une ligne de séparation -->
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" href="agenda.html">📅 Agenda</a></li>
        </ul>
      </li>
    </ul>
  </div>
</nav>
```

::: retenir
- `nav.navbar.navbar-expand-lg` ➜ `container` ➜ `navbar-brand` + `navbar-toggler` + `div.collapse.navbar-collapse#id` ➜ `ul.navbar-nav` ➜ `li.nav-item` ➜ `a.nav-link`.
- `navbar-expand-lg` : menu déplié à partir de 992 px, replié derrière ☰ en dessous.
- Le bouton ☰ : `data-bs-toggle="collapse"` + `data-bs-target="#id-de-la-zone"`.
- Barre sombre : fond sombre + `data-bs-theme="dark"`.
- Le JavaScript de Bootstrap est nécessaire pour ☰ et les sous-menus.
:::

## Projet guidé : la navbar de mon club

**Étape 1 — Le squelette.** On écrit `nav.navbar`, le container et le nom du club.
**Étape 2 — Les liens.** On ajoute la liste `navbar-nav` avec 4 liens.
**Étape 3 — Le responsive.** On ajoute `navbar-expand-md`, le bouton ☰ et la zone `collapse`.
**Étape 4 — La couleur.** On choisit un fond vert sombre et `data-bs-theme="dark"`.
**Étape 5 — Le bouton d'action.** On ajoute un bouton « Rejoindre » avec une icône.

Voici le résultat final :

```{.html .demo largeur="1000"}
<!-- Étapes 1 + 3 + 4 : barre verte foncée, dépliée dès 768 px (md) -->
<nav class="navbar navbar-expand-md bg-success" data-bs-theme="dark">
  <div class="container">
    <!-- Étape 1 : le nom du club avec une icône -->
    <a class="navbar-brand fw-bold" href="index.html"><i class="bi bi-dribbble" aria-hidden="true"></i> Basket Club</a>
    <!-- Étape 3 : le bouton ☰ -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuBasket" aria-controls="menuBasket" aria-expanded="false" aria-label="Ouvrir le menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- Étape 3 : la zone repliable -->
    <div class="collapse navbar-collapse" id="menuBasket">
      <!-- Étape 2 : les liens -->
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link active" aria-current="page" href="index.html">Accueil</a></li>
        <li class="nav-item"><a class="nav-link" href="equipes.html">Équipes</a></li>
        <li class="nav-item"><a class="nav-link" href="matchs.html">Matchs</a></li>
        <li class="nav-item"><a class="nav-link" href="photos.html">Photos</a></li>
      </ul>
      <!-- Étape 5 : bouton clair avec une icône -->
      <a class="btn btn-light" href="rejoindre.html"><i class="bi bi-person-plus" aria-hidden="true"></i> Rejoindre</a>
    </div>
  </div>
</nav>
```

## Exercices du chapitre 10

:::: {.exercice .niveau1}
### Exercice 10.1 — Ma première barre

**🎯 Objectif :** créer une navbar simple, sans menu repliable.

**📝 Consignes :** crée une barre claire avec le nom « Mon blog » et 3 liens : Accueil (actif), Articles, Contact. Le menu doit rester toujours déplié.

**🔍 Indices :** utilise `navbar-expand` **sans** palier : la barre est dépliée sur tous les écrans, pas besoin de bouton ☰.

::: solution
```{.html .demo largeur="700"}
<!-- navbar-expand sans palier : toujours dépliée -->
<nav class="navbar navbar-expand bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.html">Mon blog</a>
    <ul class="navbar-nav">
      <!-- Le lien de la page actuelle est actif -->
      <li class="nav-item"><a class="nav-link active" aria-current="page" href="index.html">Accueil</a></li>
      <li class="nav-item"><a class="nav-link" href="articles.html">Articles</a></li>
      <li class="nav-item"><a class="nav-link" href="contact.html">Contact</a></li>
    </ul>
  </div>
</nav>
```

**Explication :** avec `navbar-expand` (sans `-lg` ni `-md`), les liens sont toujours visibles. C'est pratique pour un petit menu de 2 ou 3 liens qui tient même sur téléphone.
:::
::::

:::: {.exercice .niveau2}
### Exercice 10.2 — La barre sombre responsive

**🎯 Objectif :** créer une barre sombre qui se replie sur téléphone.

**📝 Consignes :** crée une navbar sombre (`bg-dark`) « Ciné-club » repliée sous 992 px, avec les liens Films, Horaires, Tarifs alignés à droite.

**🔍 Indices :** `navbar-expand-lg`, `data-bs-theme="dark"`, `ms-auto` sur la liste. Vérifie que `data-bs-target` et `id` correspondent.

::: solution
```{.html .demo largeur="1000"}
<nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
  <div class="container">
    <a class="navbar-brand" href="index.html">🎬 Ciné-club</a>
    <!-- Le bouton ☰ cible #menuCine -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuCine" aria-controls="menuCine" aria-expanded="false" aria-label="Ouvrir le menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- La zone repliable porte l'id menuCine -->
    <div class="collapse navbar-collapse" id="menuCine">
      <!-- ms-auto : liste poussée à droite -->
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="films.html">Films</a></li>
        <li class="nav-item"><a class="nav-link" href="horaires.html">Horaires</a></li>
        <li class="nav-item"><a class="nav-link" href="tarifs.html">Tarifs</a></li>
      </ul>
    </div>
  </div>
</nav>
```

**Explication :** `bg-dark` + `data-bs-theme="dark"` rendent la barre sombre avec des textes clairs. `ms-auto` pousse les liens à droite. Sous 992 px, seuls le nom et le bouton ☰ restent visibles.
:::
::::

:::: {.exercice .niveau3}
### Exercice 10.3 — La barre avec sous-menu et recherche

**🎯 Objectif :** combiner sous-menu et petit formulaire de recherche dans une navbar.

**📝 Consignes :** crée une navbar « Ludothèque » repliée sous `lg`, avec : un lien Accueil, un sous-menu « Jeux » (Plateau, Cartes, Vidéo) et, à droite, un champ de recherche avec un bouton loupe.

**🔍 Indices :** sous-menu : `nav-item dropdown` + `dropdown-toggle` + `dropdown-menu`. Recherche : `<form class="d-flex" role="search">` avec un `input` `form-control me-2` et un bouton. Le champ a besoin d'un `aria-label`.

::: solution
```{.html .demo largeur="1000"}
<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="index.html">🎲 Ludothèque</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuLudo" aria-controls="menuLudo" aria-expanded="false" aria-label="Ouvrir le menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="menuLudo">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link active" aria-current="page" href="index.html">Accueil</a></li>
        <!-- Le sous-menu « Jeux » -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Jeux</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="plateau.html">Jeux de plateau</a></li>
            <li><a class="dropdown-item" href="cartes.html">Jeux de cartes</a></li>
            <li><a class="dropdown-item" href="video.html">Jeux vidéo</a></li>
          </ul>
        </li>
      </ul>
      <!-- Formulaire de recherche ; d-flex aligne le champ et le bouton -->
      <form class="d-flex" role="search">
        <!-- aria-label : nom du champ pour les lecteurs d'écran (pas de label visible) -->
        <input class="form-control me-2" type="search" placeholder="Chercher un jeu" aria-label="Chercher un jeu">
        <button class="btn btn-outline-success" type="submit" aria-label="Lancer la recherche"><i class="bi bi-search" aria-hidden="true"></i></button>
      </form>
    </div>
  </div>
</nav>
```

**Explication :** le sous-menu fonctionne grâce à `data-bs-toggle="dropdown"` et au JavaScript de Bootstrap. Le formulaire de recherche est mis en ligne avec `d-flex` ; `me-2` sépare le champ du bouton. Comme il n'y a pas d'étiquette visible, `aria-label` décrit le champ et le bouton.
:::
::::

## Quiz du chapitre 10

::: quiz
1. Que signifie `navbar-expand-lg` ?
    - A) La barre est toujours repliée
    - B) Le texte est en grand
    - C) Le menu est déplié à partir de 992 px et replié en dessous
    - D) La barre est très grande
2. Quelle classe porte le nom ou le logo du site ?
    - A) `brand-name`
    - B) `navbar-logo`
    - C) `navbar-brand`
    - D) `nav-title`
3. Que faut-il pour que le bouton ☰ fonctionne ?
    - A) Le JavaScript de Bootstrap et un `data-bs-target` qui correspond à l'`id` de la zone
    - B) Rien de spécial
    - C) Une image de hamburger
    - D) La classe `burger`
4. Comment indiquer la page où l'on se trouve ?
    - A) `class="nav-link here"`
    - B) `class="nav-link active"` et `aria-current="page"`
    - C) `class="nav-link bold"`
    - D) Ce n'est pas possible
5. Comment obtenir des textes clairs sur une navbar à fond sombre ?
    - A) `text-white` sur chaque lettre
    - B) `navbar-light`
    - C) `color="white"`
    - D) `data-bs-theme="dark"` sur la `nav`
:::

::: reponses
1. **C** — *expand* = déplier ; `lg` = à partir de 992 px.
2. **C** — `navbar-brand` (la « marque » du site).
3. **A** — Le bouton utilise le JavaScript de Bootstrap et doit viser la bonne zone (`#id`).
4. **B** — `active` change l'apparence ; `aria-current="page"` l'annonce aux lecteurs d'écran.
5. **D** — `data-bs-theme="dark"` adapte les couleurs des textes et de l'icône ☰ à un fond sombre.
:::

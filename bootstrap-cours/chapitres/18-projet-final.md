# Chapitre 18 — Projet final : le site de l'École des Pins {#ch18}

::: chapitre-intro
Bravo, tu connais maintenant tous les outils importants de Bootstrap ! Il est temps de les réunir pour construire un **vrai site complet** : celui d'une école imaginaire, l'**École des Pins**. Nous allons travailler comme de vrais développeurs : comprendre le besoin, dessiner une maquette, coder section par section, rendre le site responsive et enfin l'optimiser.
:::

::: analogie
Construire un site, c'est comme construire une **maison** : on écoute d'abord ce que veut la famille (le besoin), l'architecte dessine un plan (la maquette), puis on bâtit pièce par pièce (les sections), on vérifie que tout tient debout (les tests) et on fait les finitions (l'optimisation).
:::

```{.ascii}
  ① Analyse     ② Maquette     ③ Développement   ④ Responsive    ⑤ Optimisation
  du besoin  →  (le plan)   →  section par    →  (tous les    →  (finitions,
  (quoi ?)                     section            écrans)          vérifications)
```

## Étape 1 — L'analyse du besoin

La directrice de l'École des Pins, Mme Durand, nous a envoyé ce message :

> « Bonjour ! Nous aimerions un site pour présenter notre école aux familles. Il faudrait montrer qui nous sommes, quelques photos de nos activités, présenter l'équipe des enseignants et permettre aux parents de nous écrire. Beaucoup de parents regardent sur leur téléphone. Merci ! »

On transforme ce message en **cahier des charges**, c'est-à-dire la liste de ce que le site doit contenir :

| Besoin de la directrice | Ce qu'on va construire | Composant Bootstrap (chapitre) |
|---|---|---|
| Naviguer facilement | Une barre de navigation en haut | Navbar (10) |
| « Montrer qui nous sommes » | Une grande bannière d'accueil | Grille, couleurs, boutons (4, 5, 6) |
| Présenter l'école | 3 points forts avec icônes | Cartes, icônes (7, 17) |
| « Quelques photos » | Une galerie d'images | Grille, images (4, 16) |
| « Présenter l'équipe » | Des cartes enseignants | Cartes, badges (7, 12) |
| « Permettre de nous écrire » | Un formulaire de contact | Formulaires (9) |
| Infos pratiques | Un pied de page | Grille, utilitaires (4, 15) |
| « Sur leur téléphone » | Un site responsive | Responsive (16) |

::: retenir
Avant de coder, on se pose toujours trois questions : **Pour qui** est le site ? (des familles) **Pour quoi faire** ? (découvrir l'école et la contacter) **Sur quel écran** ? (surtout des téléphones).
:::

## Étape 2 — Le maquettage

Une **maquette** (*wireframe* en anglais) est un dessin simple du site, sans couleurs ni détails. On la fait sur papier ou avec des caractères comme ici. Voici la version **ordinateur** :

```{.ascii}
┌──────────────────────────────────────────────────────────────────┐
│ 🌲 École des Pins          Accueil  L'école  Galerie  Équipe  Contact │ ← navbar
├──────────────────────────────────────────────────────────────────┤
│  Bienvenue à l'École des Pins !          ┌─────────────────────┐ │
│  Une école où l'on apprend en s'amusant  │       PHOTO         │ │ ← bannière
│  [Découvrir l'école] [Nous contacter]    └─────────────────────┘ │
├──────────────────────────────────────────────────────────────────┤
│        ( 📖 )              ( 🎨 )              ( 🌱 )              │
│      Apprendre             Créer              Grandir             │ ← présentation
├──────────────────────────────────────────────────────────────────┤
│  [photo] [photo] [photo] [photo]                                 │ ← galerie
│  [photo] [photo] [photo] [photo]                                 │
├──────────────────────────────────────────────────────────────────┤
│  ┌──────┐  ┌──────┐  ┌──────┐  ┌──────┐                          │ ← équipe
│  │ prof │  │ prof │  │ prof │  │ prof │                          │
│  └──────┘  └──────┘  └──────┘  └──────┘                          │
├──────────────────────────────────────────────────────────────────┤
│  Infos (adresse, tél.)      │  Formulaire : nom, e-mail, message │ ← contact
├──────────────────────────────────────────────────────────────────┤
│  À propos    |    Liens utiles    |    Suivez-nous   © 2026      │ ← footer
└──────────────────────────────────────────────────────────────────┘
```

Et la version **téléphone** : tout s'empile en une seule colonne et le menu se replie derrière le bouton ☰.

```{.ascii}
┌──────────────────────┐
│ 🌲 École des Pins  ☰ │
├──────────────────────┤
│ Bienvenue à l'École  │
│ des Pins !           │
│ [Découvrir l'école]  │
│ [Nous contacter]     │
│      PHOTO           │
├──────────────────────┤
│ ( 📖 ) Apprendre     │
│ ( 🎨 ) Créer         │
│ ( 🌱 ) Grandir       │
├──────────────────────┤
│ [photo]  [photo]     │
│ [photo]  [photo] ... │
├──────────────────────┤
│ ┌──────┐  ┌──────┐   │
│ │ prof │  │ prof │   │
│ └──────┘  └──────┘   │
├──────────────────────┤
│ Infos                │
│ Formulaire           │
├──────────────────────┤
│ Footer               │
└──────────────────────┘
```

On choisit aussi une **charte graphique** (les couleurs et le style) : le **vert** (`success`) rappelle les pins, le **jaune** (`warning`) apporte de la joie pour les boutons, et le reste est blanc et gris clair pour que ce soit lisible.

## Étape 3 — Préparer le projet

Crée un dossier `ecole-des-pins` avec cette organisation :

```{.ascii}
ecole-des-pins/
├── index.html          ← la page du site
└── images/             ← toutes les photos
    ├── ecole.jpg
    ├── galerie-1.jpg … galerie-8.jpg
    └── prof-durand.jpg, prof-martin.jpg, prof-nguyen.jpg, prof-diallo.jpg
```

Dans `index.html`, on part du modèle de base du chapitre 2, auquel on ajoute Bootstrap Icons. Toutes les sections des étapes suivantes se placeront **dans le `<body>`, avant le `<script>`**.

```html
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- La description apparaît dans les résultats des moteurs de recherche -->
  <meta name="description" content="L'École des Pins : une école primaire où l'on apprend en s'amusant.">
  <title>École des Pins — Apprendre en s'amusant</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet"
        integrity="sha384-XGjxtQfXaH2tnPFa9x+ruJTuLE3Aa6LhHSWRr1XeTyhezb4abCG4ccI5AkVDxqC+" crossorigin="anonymous">
</head>
<body>

  <!-- Ici viendront : navbar, bannière, présentation, galerie, équipe, contact, footer -->

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
```

::: astuce
Avance **une section à la fois** et regarde le résultat dans ton navigateur après chaque étape. Si quelque chose ne marche pas, tu sauras tout de suite où chercher !
:::

## Étape 4 — La barre de navigation

```{.html .demo .plein largeur="1100|380" clic=";.navbar-toggler" legende="Sur ordinateur, puis sur téléphone après un clic sur ☰"}
<!-- navbar-expand-lg : menu déplié à partir de lg ; sticky-top : reste collée en haut quand on fait défiler -->
<nav class="navbar navbar-expand-lg bg-white shadow-sm sticky-top">
  <div class="container">
    <!-- Le logo : une icône de sapin verte + le nom de l'école -->
    <a class="navbar-brand fw-bold text-success" href="#accueil">
      <i class="bi bi-tree-fill" aria-hidden="true"></i> École des Pins
    </a>
    <!-- Le bouton ☰ visible sur petit écran -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuEcole"
            aria-controls="menuEcole" aria-expanded="false" aria-label="Ouvrir le menu">
      <span class="navbar-toggler-icon"></span>
    </button>
    <!-- Les liens, repliables -->
    <div class="collapse navbar-collapse" id="menuEcole">
      <!-- ms-auto : les liens sont poussés à droite -->
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link active" aria-current="page" href="#accueil">Accueil</a></li>
        <li class="nav-item"><a class="nav-link" href="#ecole">L'école</a></li>
        <li class="nav-item"><a class="nav-link" href="#galerie">Galerie</a></li>
        <li class="nav-item"><a class="nav-link" href="#equipe">Équipe</a></li>
        <!-- Le dernier lien est un bouton jaune pour attirer l'œil -->
        <li class="nav-item ms-lg-2"><a class="btn btn-warning" href="#contact">Contact</a></li>
      </ul>
    </div>
  </div>
</nav>
```

**Ligne par ligne :**

- `navbar navbar-expand-lg` : une barre de navigation qui se déplie à partir des écrans `lg` (992 px).
- `bg-white shadow-sm` : fond blanc et légère ombre pour la détacher de la page.
- `sticky-top` : la barre reste **collée en haut** quand on fait défiler la page. Pratique sur un long site !
- `container` : le contenu de la barre est aligné avec le reste du site.
- `navbar-brand … text-success` : le nom de l'école, en vert, avec une icône `bi-tree-fill`. Le lien `#accueil` ramène en haut.
- `navbar-toggler` + `data-bs-target="#menuEcole"` : le bouton ☰ ouvre ou ferme la `div` dont l'`id` est `menuEcole`.
- `ms-auto` : marge automatique à gauche → les liens se placent à droite.
- Les liens `href="#ecole"`, `#galerie`… mènent aux **sections de la même page** grâce à leur `id` (on les créera ensuite).
- `ms-lg-2` : un petit espace avant le bouton « Contact », seulement sur grand écran.


## Étape 5 — La bannière d'accueil

La bannière (on dit aussi *hero*, « héros ») est la première chose que l'on voit. Elle doit dire **en une phrase** de quoi parle le site.

```{.html .demo .plein largeur="1100"}
<!-- id="accueil" : la cible du lien « Accueil » -->
<!-- bg-success bg-gradient : fond vert avec un léger dégradé ; text-white : texte blanc ; py-5 : grand espace en haut et en bas -->
<header id="accueil" class="bg-success bg-gradient text-white py-5">
  <div class="container">
    <!-- align-items-center : texte et image centrés en hauteur ; g-4 : espace entre colonnes -->
    <div class="row align-items-center g-4">
      <!-- Colonne texte : toute la largeur sur téléphone, la moitié à partir de lg -->
      <div class="col-lg-6 text-center text-lg-start">
        <!-- Un badge pour l'actualité -->
        <span class="badge text-bg-warning mb-3">🎉 Inscriptions ouvertes pour la rentrée</span>
        <!-- display-5 : un très grand titre ; fw-bold : gras -->
        <h1 class="display-5 fw-bold">Bienvenue à l'École des Pins !</h1>
        <!-- lead : un paragraphe d'introduction un peu plus grand -->
        <p class="lead">Une école où l'on apprend, crée et grandit ensemble, au milieu des arbres.</p>
        <!-- Les deux boutons : d-grid sur téléphone (empilés), côte à côte à partir de sm -->
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center justify-content-lg-start">
          <a href="#ecole" class="btn btn-warning btn-lg">Découvrir l'école</a>
          <a href="#contact" class="btn btn-outline-light btn-lg">Nous contacter</a>
        </div>
      </div>
      <!-- Colonne image -->
      <div class="col-lg-6">
        <!-- img-fluid : l'image s'adapte ; rounded-4 : coins bien arrondis ; shadow : ombre -->
        <img src="images/ecole.jpg" class="img-fluid rounded-4 shadow" alt="La cour de l'École des Pins entourée d'arbres">
      </div>
    </div>
  </div>
</header>
```

**Ligne par ligne :**

- `<header>` : une balise qui veut dire « en-tête de la page » ; elle aide les moteurs de recherche et les lecteurs d'écran à comprendre la page.
- `bg-success bg-gradient` : notre vert, avec un léger effet de lumière.
- `row` + deux `col-lg-6` : sur ordinateur, le texte à gauche et l'image à droite ; sur téléphone, l'un sous l'autre.
- `text-center text-lg-start` : texte centré sur téléphone, aligné à gauche sur ordinateur.
- `display-5` : un titre plus grand qu'un `h1` normal. `lead` : un paragraphe d'accroche.
- `d-grid gap-2 d-sm-flex` : boutons empilés sur petit téléphone, côte à côte ensuite (exercice 16.2 !).
- Il n'y a **qu'un seul `<h1>`** dans toute la page : c'est le titre principal. Les sections suivantes utiliseront des `<h2>`.

## Étape 6 — La section de présentation

```{.html .demo .plein largeur="1100"}
<!-- id="ecole" : cible du lien « L'école » ; py-5 : espace vertical -->
<section id="ecole" class="py-5">
  <div class="container">
    <!-- Le titre de la section, centré -->
    <h2 class="text-center fw-bold mb-2">Notre école</h2>
    <!-- col-lg-8 mx-auto : un texte pas trop large, centré -->
    <p class="text-center text-body-secondary col-lg-8 mx-auto mb-5">
      Depuis 1985, l'École des Pins accueille 180 élèves du CP au CM2. Notre devise : chaque enfant est unique !
    </p>
    <!-- 1 bloc par ligne sur téléphone, 3 à partir de md -->
    <div class="row row-cols-1 row-cols-md-3 g-4 text-center">
      <div class="col">
        <!-- Le rond coloré avec l'icône (exercice 17.3) -->
        <div class="rounded-circle text-bg-primary d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;">
          <i class="bi bi-book-half fs-2" aria-hidden="true"></i>
        </div>
        <h3 class="fs-4">Apprendre</h3>
        <p>Lire, compter, découvrir le monde… avec des projets concrets et une bibliothèque de 3 000 livres.</p>
      </div>
      <div class="col">
        <div class="rounded-circle text-bg-danger d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;">
          <i class="bi bi-palette-fill fs-2" aria-hidden="true"></i>
        </div>
        <h3 class="fs-4">Créer</h3>
        <p>Peinture, théâtre, musique et atelier de code : chacun trouve sa façon de s'exprimer.</p>
      </div>
      <div class="col">
        <div class="rounded-circle text-bg-success d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;">
          <i class="bi bi-flower1 fs-2" aria-hidden="true"></i>
        </div>
        <h3 class="fs-4">Grandir</h3>
        <p>Un potager, des sorties en forêt et un conseil des élèves pour apprendre à vivre ensemble.</p>
      </div>
    </div>
  </div>
</section>
```

**Ligne par ligne :**

- `<section id="ecole">` : une partie de la page avec un thème ; son `id` permet au menu d'y mener.
- `py-5` : beaucoup d'espace en haut et en bas, pour que les sections « respirent ».
- `text-body-secondary` : texte gris, moins important que le titre.
- `col-lg-8 mx-auto` : le paragraphe n'occupe que 8/12 de la largeur sur grand écran et il est centré ; une ligne de texte trop longue est fatigante à lire.
- `row-cols-1 row-cols-md-3` : 1 bloc par ligne sur téléphone, 3 sur tablette et ordinateur.
- Les ronds colorés reprennent la technique de l'exercice 17.3 : un carré de 72 px arrondi en cercle, icône centrée par flexbox.
- Les titres des blocs sont des `<h3>` (avec `fs-4` pour la taille) : ils sont « sous » le `<h2>` de la section. On respecte l'ordre h1 → h2 → h3.

## Étape 7 — La galerie photos

```{.html .demo .plein largeur="1100"}
<!-- bg-body-tertiary : fond gris très clair pour alterner avec la section précédente -->
<section id="galerie" class="py-5 bg-body-tertiary">
  <div class="container">
    <h2 class="text-center fw-bold mb-4">La vie à l'école</h2>
    <!-- 2 photos par ligne sur téléphone, 4 à partir de md ; g-3 : espace entre les photos -->
    <div class="row row-cols-2 row-cols-md-4 g-3">
      <!-- figure : une image avec sa légende -->
      <figure class="col mb-0">
        <!-- loading="lazy" : l'image n'est chargée que quand on s'en approche (page plus rapide) -->
        <img src="images/galerie-1.jpg" class="img-fluid rounded shadow-sm" alt="Des élèves plantent des tomates au potager" loading="lazy">
        <figcaption class="small text-center mt-1">Le potager</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-2.jpg" class="img-fluid rounded shadow-sm" alt="Spectacle de théâtre de fin d'année" loading="lazy">
        <figcaption class="small text-center mt-1">Le spectacle</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-3.jpg" class="img-fluid rounded shadow-sm" alt="Classe en sortie dans la forêt" loading="lazy">
        <figcaption class="small text-center mt-1">Sortie en forêt</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-4.jpg" class="img-fluid rounded shadow-sm" alt="Atelier de programmation de robots" loading="lazy">
        <figcaption class="small text-center mt-1">Atelier robots</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-5.jpg" class="img-fluid rounded shadow-sm" alt="Match de basket dans le gymnase" loading="lazy">
        <figcaption class="small text-center mt-1">Le sport</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-6.jpg" class="img-fluid rounded shadow-sm" alt="Élèves qui lisent à la bibliothèque" loading="lazy">
        <figcaption class="small text-center mt-1">La bibliothèque</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-7.jpg" class="img-fluid rounded shadow-sm" alt="Peintures accrochées pour l'exposition" loading="lazy">
        <figcaption class="small text-center mt-1">L'exposition</figcaption>
      </figure>
      <figure class="col mb-0">
        <img src="images/galerie-8.jpg" class="img-fluid rounded shadow-sm" alt="La chorale de l'école chante" loading="lazy">
        <figcaption class="small text-center mt-1">La chorale</figcaption>
      </figure>
    </div>
  </div>
</section>
```

**Ligne par ligne :**

- `bg-body-tertiary` : un gris très clair ; en **alternant** blanc et gris, on voit bien où commence chaque section.
- `row-cols-2 row-cols-md-4` : 2 photos par ligne sur téléphone, 4 sur les écrans plus grands → 2 lignes de 4 sur ordinateur.
- `<figure>` et `<figcaption>` : les balises HTML faites pour « une image + sa légende ». La `figure` est aussi la colonne (`col`) : pas besoin d'une `div` en plus.
- `alt="…"` : chaque photo a une description précise, utile aux personnes aveugles et si l'image ne se charge pas.
- `loading="lazy"` : le navigateur attend que l'on fasse défiler la page jusqu'à la galerie pour télécharger les photos. La page s'affiche plus vite !

## Étape 8 — Les cartes de l'équipe

```{.html .demo .plein largeur="1100"}
<section id="equipe" class="py-5">
  <div class="container">
    <h2 class="text-center fw-bold mb-4">L'équipe enseignante</h2>
    <!-- 1 carte par ligne sur petit téléphone, 2 à partir de sm, 4 à partir de lg -->
    <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
      <div class="col">
        <!-- h-100 : toutes les cartes ont la même hauteur ; border-0 shadow-sm : sans bordure, avec ombre -->
        <div class="card h-100 border-0 shadow-sm text-center">
          <img src="images/prof-durand.jpg" class="card-img-top" alt="Portrait de Mme Durand">
          <div class="card-body">
            <h3 class="card-title fs-5 mb-1">Mme Durand</h3>
            <!-- Le badge indique le rôle -->
            <span class="badge text-bg-success mb-2">Directrice · CM2</span>
            <p class="card-text small">Passionnée d'astronomie, elle organise la nuit des étoiles.</p>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100 border-0 shadow-sm text-center">
          <img src="images/prof-martin.jpg" class="card-img-top" alt="Portrait de M. Martin">
          <div class="card-body">
            <h3 class="card-title fs-5 mb-1">M. Martin</h3>
            <span class="badge text-bg-primary mb-2">CE2 · CM1</span>
            <p class="card-text small">Il anime l'atelier robots et le club d'échecs.</p>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100 border-0 shadow-sm text-center">
          <img src="images/prof-nguyen.jpg" class="card-img-top" alt="Portrait de Mme Nguyen">
          <div class="card-body">
            <h3 class="card-title fs-5 mb-1">Mme Nguyen</h3>
            <span class="badge text-bg-warning mb-2">CP · CE1</span>
            <p class="card-text small">Elle adore les contes et fait vivre la bibliothèque.</p>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100 border-0 shadow-sm text-center">
          <img src="images/prof-diallo.jpg" class="card-img-top" alt="Portrait de M. Diallo">
          <div class="card-body">
            <h3 class="card-title fs-5 mb-1">M. Diallo</h3>
            <span class="badge text-bg-danger mb-2">Sport · Musique</span>
            <p class="card-text small">Basket, chorale, percussions : il ne s'arrête jamais !</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
```

**Ligne par ligne :**

- `row-cols-1 row-cols-sm-2 row-cols-lg-4` : trois comportements → 1, puis 2, puis 4 cartes par ligne.
- `card h-100` : chaque carte remplit la hauteur de sa colonne ; ainsi, toutes les cartes d'une ligne ont **la même hauteur**, même si les textes sont de longueurs différentes.
- `border-0 shadow-sm` : on remplace la bordure par une ombre douce, plus moderne.
- `card-img-top` : la photo en haut de la carte, avec les coins du haut arrondis.
- Le `badge` donne le rôle de chaque enseignant, avec une couleur différente pour chacun.

## Étape 9 — Le formulaire de contact

```{.html .demo .plein largeur="1100"}
<section id="contact" class="py-5 bg-body-tertiary">
  <div class="container">
    <h2 class="text-center fw-bold mb-4">Nous contacter</h2>
    <div class="row g-4">
      <!-- Colonne des infos pratiques : 5/12 sur ordinateur -->
      <div class="col-lg-5">
        <!-- list-unstyled : liste sans puces -->
        <ul class="list-unstyled fs-5">
          <li class="mb-3"><i class="bi bi-geo-alt-fill text-success me-2" aria-hidden="true"></i>12 rue des Pins, 75000 Paris</li>
          <li class="mb-3"><i class="bi bi-telephone-fill text-success me-2" aria-hidden="true"></i>01 23 45 67 89</li>
          <li class="mb-3"><i class="bi bi-envelope-fill text-success me-2" aria-hidden="true"></i>contact@ecole-des-pins.fr</li>
          <li><i class="bi bi-clock-fill text-success me-2" aria-hidden="true"></i>Lundi – vendredi : 8 h 20 – 16 h 30</li>
        </ul>
      </div>
      <!-- Colonne du formulaire : 7/12 sur ordinateur -->
      <div class="col-lg-7">
        <!-- p-4 bg-white rounded-3 shadow-sm : le formulaire dans une « feuille » blanche -->
        <form class="row g-3 p-4 bg-white rounded-3 shadow-sm" action="merci.html" method="post">
          <!-- Nom et e-mail côte à côte à partir de md -->
          <div class="col-md-6">
            <label for="nom" class="form-label">Nom du parent</label>
            <input type="text" class="form-control" id="nom" name="nom" autocomplete="name" required>
          </div>
          <div class="col-md-6">
            <label for="email" class="form-label">E-mail</label>
            <input type="email" class="form-control" id="email" name="email" autocomplete="email" placeholder="nom@exemple.fr" required>
          </div>
          <!-- La liste déroulante pour la classe -->
          <div class="col-12">
            <label for="classe" class="form-label">Classe concernée</label>
            <select class="form-select" id="classe" name="classe">
              <option value="">Choisir une classe…</option>
              <option>CP</option>
              <option>CE1</option>
              <option>CE2</option>
              <option>CM1</option>
              <option>CM2</option>
            </select>
          </div>
          <div class="col-12">
            <label for="message" class="form-label">Votre message</label>
            <textarea class="form-control" id="message" name="message" rows="4" required></textarea>
          </div>
          <!-- La case à cocher obligatoire -->
          <div class="col-12">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="accord" name="accord" required>
              <label class="form-check-label" for="accord">J'accepte que l'école me réponde par e-mail.</label>
            </div>
          </div>
          <!-- Le bouton d'envoi -->
          <div class="col-12">
            <button type="submit" class="btn btn-success btn-lg">
              <i class="bi bi-send-fill me-1" aria-hidden="true"></i> Envoyer le message
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</section>
```

**Ligne par ligne :**

- Deux colonnes `col-lg-5` et `col-lg-7` (5 + 7 = 12) : les infos à gauche, le formulaire à droite sur ordinateur.
- `list-unstyled` : enlève les puces de la liste ; les icônes vertes les remplacent joliment.
- `<form class="row g-3 …">` : le formulaire est lui-même une ligne de grille, ce qui permet de placer les champs en colonnes.
- `action="merci.html" method="post"` : où et comment envoyer les données (il faudra un serveur pour les recevoir vraiment).
- Chaque `<label for="…">` correspond à l'`id` de son champ : cliquer sur le texte place le curseur dans le champ.
- `autocomplete="name"` / `"email"` : le navigateur peut proposer de remplir automatiquement.
- `required` : le navigateur refuse l'envoi si le champ est vide (ou si l'e-mail est mal écrit).

## Étape 10 — Le pied de page

```{.html .demo .plein largeur="1100"}
<!-- text-bg-dark : fond sombre et texte clair ; pt-5 pb-3 : espace en haut et en bas -->
<footer class="text-bg-dark pt-5 pb-3">
  <div class="container">
    <div class="row g-4">
      <!-- Colonne 1 : à propos -->
      <div class="col-md-5">
        <h2 class="fs-5"><i class="bi bi-tree-fill text-success" aria-hidden="true"></i> École des Pins</h2>
        <p class="text-white-50">École primaire publique. Apprendre, créer et grandir ensemble depuis 1985.</p>
      </div>
      <!-- Colonne 2 : liens utiles -->
      <div class="col-6 col-md-3">
        <h2 class="fs-6 text-uppercase">Liens utiles</h2>
        <ul class="list-unstyled">
          <li><a href="menus.html" class="link-light text-decoration-none">Menus de la cantine</a></li>
          <li><a href="calendrier.html" class="link-light text-decoration-none">Calendrier</a></li>
          <li><a href="inscriptions.html" class="link-light text-decoration-none">Inscriptions</a></li>
        </ul>
      </div>
      <!-- Colonne 3 : réseaux -->
      <div class="col-6 col-md-4">
        <h2 class="fs-6 text-uppercase">Suivez-nous</h2>
        <!-- Liens avec icône seule : aria-label obligatoire -->
        <a href="https://www.youtube.com" class="link-light fs-3 me-3" aria-label="Notre chaîne YouTube"><i class="bi bi-youtube" aria-hidden="true"></i></a>
        <a href="https://www.instagram.com" class="link-light fs-3 me-3" aria-label="Notre compte Instagram"><i class="bi bi-instagram" aria-hidden="true"></i></a>
        <a href="mailto:contact@ecole-des-pins.fr" class="link-light fs-3" aria-label="Nous écrire un e-mail"><i class="bi bi-envelope-fill" aria-hidden="true"></i></a>
      </div>
    </div>
    <!-- Une ligne de séparation puis le copyright, centré et en petit -->
    <hr class="border-secondary">
    <p class="text-center small text-white-50 mb-0">© 2026 École des Pins — Site réalisé avec Bootstrap 5</p>
  </div>
</footer>
```

**Ligne par ligne :**

- `<footer>` : la balise du pied de page.
- `text-bg-dark` : fond presque noir et texte blanc, pour bien marquer la fin du site.
- `col-md-5` / `col-6 col-md-3` / `col-6 col-md-4` : sur téléphone, « À propos » prend toute la ligne et les deux autres colonnes se partagent la ligne suivante ; sur tablette et plus, les trois sont côte à côte (5 + 3 + 4 = 12).
- `text-white-50` : blanc à 50 % → un gris clair, moins important que les titres.
- `link-light text-decoration-none` : des liens clairs, sans soulignement.
- Les icônes de réseaux sont seules dans leur lien : chaque lien a donc un `aria-label`.

## Étape 11 — La mise en page : assembler le tout

On place les sept morceaux **dans l'ordre** dans le `<body>`. Entre la bannière et le pied de page, on entoure les sections d'une balise `<main>` (« contenu principal ») : c'est une bonne pratique d'accessibilité.

```{.ascii}
<body>
  <nav>     … navbar …         </nav>       ← Étape 4
  <header>  … bannière …       </header>    ← Étape 5
  <main>
    <section id="ecole">   …   </section>   ← Étape 6
    <section id="galerie"> …   </section>   ← Étape 7
    <section id="equipe">  …   </section>   ← Étape 8
    <section id="contact"> …   </section>   ← Étape 9
  </main>
  <footer>  … pied de page …   </footer>    ← Étape 10
  <script src="…bootstrap.bundle.min.js"></script>
</body>
```

Quelques règles de mise en page appliquées partout :

- **Le même `container`** dans chaque section : tout le contenu est aligné sur les mêmes bords.
- **Le même espacement** `py-5` pour chaque section : le site a un rythme régulier.
- **L'alternance des fonds** blanc / gris clair (`bg-body-tertiary`) : on distingue les sections sans traits.
- **Des titres cohérents** : `h2.text-center.fw-bold` pour chaque section.

::: astuce
Un dernier réglage : avec une navbar `sticky-top`, quand on clique sur « Galerie », le titre de la section se retrouve **caché sous la barre**. Une ligne de CSS dans le `<head>` règle le problème : `<style> section, header { scroll-margin-top: 70px; } html { scroll-behavior: smooth; } </style>`. Elle laisse 70 px au-dessus de la section, et `smooth` fait défiler la page en douceur.
:::

## Étape 12 — Le responsive : vérifier sur tous les écrans

Grâce aux classes choisies, le site s'adapte déjà. Voici le récapitulatif de ce qui change :

| Section | Téléphone (< 576 px) | Tablette (md, ≥ 768 px) | Ordinateur (lg, ≥ 992 px) |
|---|---|---|---|
| Navbar | menu replié ☰ | menu replié ☰ | menu déplié |
| Bannière | texte centré, image dessous, boutons empilés | idem, boutons côte à côte | texte à gauche, image à droite |
| Présentation | 1 bloc par ligne | 3 blocs par ligne | 3 blocs par ligne |
| Galerie | 2 photos par ligne | 4 photos par ligne | 4 photos par ligne |
| Équipe | 1 carte par ligne | 2 cartes par ligne | 4 cartes par ligne |
| Contact | infos puis formulaire | infos puis formulaire (nom/e-mail côte à côte) | infos à gauche, formulaire à droite |
| Footer | 1 + 2 colonnes | 3 colonnes | 3 colonnes |

Comment vérifier ? Ouvre ton site, appuie sur **F12**, active le **mode appareil** 📱, puis teste au moins trois tailles : un téléphone (environ 375 px), une tablette (768 px) et un ordinateur (1200 px et plus). À chaque fois, vérifie : rien ne dépasse sur le côté, tout est lisible, les boutons sont faciles à toucher.

## Étape 13 — L'optimisation : les finitions de pro

| Vérification | Pourquoi | Comment |
|---|---|---|
| Un `<title>` et une `meta description` clairs | Bien apparaître dans les moteurs de recherche | Dans le `<head>` |
| `lang="fr"` sur `<html>` | Les lecteurs d'écran prononcent en français | `<html lang="fr">` |
| Un texte `alt` sur chaque image | Accessibilité, et texte de secours | `alt="…"` précis |
| Un seul `<h1>`, puis h2, h3 dans l'ordre | La structure est claire | Contrôler les titres |
| Des images légères | Le site se charge vite, même en 4G | Images de moins de 200 ko, format JPG ou WebP |
| `loading="lazy"` | On ne charge les photos que si on les voit | Sur les images en bas de page |
| Liens CDN avec `integrity` | Sécurité : le fichier n'a pas été modifié | Code copié de la documentation officielle |
| Script Bootstrap en bas du `<body>` | La page s'affiche avant le chargement du JavaScript | Juste avant `</body>` |
| Code validé | Pas d'erreur cachée | Le validateur du W3C : validator.w3.org |
| Contraste suffisant | Lisible par tous | `text-bg-*` choisit la bonne couleur de texte |

::: retenir
Un bon site n'est pas seulement **joli** : il est **rapide**, **lisible par tous** (accessible), **bien rangé** et **fonctionne sur tous les écrans**.
:::

## Le code complet du site

Voici le fichier `index.html` final, avec toutes les étapes assemblées. Les commentaires repèrent chaque partie.

```{.html .demo .page largeur="1200" hauteurmax="1500" legende="Le site terminé sur un ordinateur (haut de la page)"}
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="L'École des Pins : une école primaire où l'on apprend en s'amusant.">
  <title>École des Pins — Apprendre en s'amusant</title>
  <!-- Bootstrap 5.3.3 et Bootstrap Icons 1.11.3 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet"
        integrity="sha384-XGjxtQfXaH2tnPFa9x+ruJTuLE3Aa6LhHSWRr1XeTyhezb4abCG4ccI5AkVDxqC+" crossorigin="anonymous">
  <!-- Le seul CSS personnel : place pour la navbar collée et défilement doux -->
  <style>
    section, header { scroll-margin-top: 70px; }
    html { scroll-behavior: smooth; }
  </style>
</head>
<body>

  <!-- ===== 1. NAVBAR ===== -->
  <nav class="navbar navbar-expand-lg bg-white shadow-sm sticky-top">
    <div class="container">
      <a class="navbar-brand fw-bold text-success" href="#accueil"><i class="bi bi-tree-fill" aria-hidden="true"></i> École des Pins</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuEcole"
              aria-controls="menuEcole" aria-expanded="false" aria-label="Ouvrir le menu">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="menuEcole">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item"><a class="nav-link active" aria-current="page" href="#accueil">Accueil</a></li>
          <li class="nav-item"><a class="nav-link" href="#ecole">L'école</a></li>
          <li class="nav-item"><a class="nav-link" href="#galerie">Galerie</a></li>
          <li class="nav-item"><a class="nav-link" href="#equipe">Équipe</a></li>
          <li class="nav-item ms-lg-2"><a class="btn btn-warning" href="#contact">Contact</a></li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- ===== 2. BANNIÈRE ===== -->
  <header id="accueil" class="bg-success bg-gradient text-white py-5">
    <div class="container">
      <div class="row align-items-center g-4">
        <div class="col-lg-6 text-center text-lg-start">
          <span class="badge text-bg-warning mb-3">🎉 Inscriptions ouvertes pour la rentrée</span>
          <h1 class="display-5 fw-bold">Bienvenue à l'École des Pins !</h1>
          <p class="lead">Une école où l'on apprend, crée et grandit ensemble, au milieu des arbres.</p>
          <div class="d-grid gap-2 d-sm-flex justify-content-sm-center justify-content-lg-start">
            <a href="#ecole" class="btn btn-warning btn-lg">Découvrir l'école</a>
            <a href="#contact" class="btn btn-outline-light btn-lg">Nous contacter</a>
          </div>
        </div>
        <div class="col-lg-6">
          <img src="images/ecole.jpg" class="img-fluid rounded-4 shadow" alt="La cour de l'École des Pins entourée d'arbres">
        </div>
      </div>
    </div>
  </header>

  <main>
    <!-- ===== 3. PRÉSENTATION ===== -->
    <section id="ecole" class="py-5">
      <div class="container">
        <h2 class="text-center fw-bold mb-2">Notre école</h2>
        <p class="text-center text-body-secondary col-lg-8 mx-auto mb-5">Depuis 1985, l'École des Pins accueille 180 élèves du CP au CM2. Notre devise : chaque enfant est unique !</p>
        <div class="row row-cols-1 row-cols-md-3 g-4 text-center">
          <div class="col">
            <div class="rounded-circle text-bg-primary d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;"><i class="bi bi-book-half fs-2" aria-hidden="true"></i></div>
            <h3 class="fs-4">Apprendre</h3>
            <p>Lire, compter, découvrir le monde… avec des projets concrets et une bibliothèque de 3 000 livres.</p>
          </div>
          <div class="col">
            <div class="rounded-circle text-bg-danger d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;"><i class="bi bi-palette-fill fs-2" aria-hidden="true"></i></div>
            <h3 class="fs-4">Créer</h3>
            <p>Peinture, théâtre, musique et atelier de code : chacun trouve sa façon de s'exprimer.</p>
          </div>
          <div class="col">
            <div class="rounded-circle text-bg-success d-inline-flex align-items-center justify-content-center mb-3" style="width: 72px; height: 72px;"><i class="bi bi-flower1 fs-2" aria-hidden="true"></i></div>
            <h3 class="fs-4">Grandir</h3>
            <p>Un potager, des sorties en forêt et un conseil des élèves pour apprendre à vivre ensemble.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ===== 4. GALERIE ===== -->
    <section id="galerie" class="py-5 bg-body-tertiary">
      <div class="container">
        <h2 class="text-center fw-bold mb-4">La vie à l'école</h2>
        <div class="row row-cols-2 row-cols-md-4 g-3">
          <figure class="col mb-0"><img src="images/galerie-1.jpg" class="img-fluid rounded shadow-sm" alt="Des élèves plantent des tomates au potager" loading="lazy"><figcaption class="small text-center mt-1">Le potager</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-2.jpg" class="img-fluid rounded shadow-sm" alt="Spectacle de théâtre de fin d'année" loading="lazy"><figcaption class="small text-center mt-1">Le spectacle</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-3.jpg" class="img-fluid rounded shadow-sm" alt="Classe en sortie dans la forêt" loading="lazy"><figcaption class="small text-center mt-1">Sortie en forêt</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-4.jpg" class="img-fluid rounded shadow-sm" alt="Atelier de programmation de robots" loading="lazy"><figcaption class="small text-center mt-1">Atelier robots</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-5.jpg" class="img-fluid rounded shadow-sm" alt="Match de basket dans le gymnase" loading="lazy"><figcaption class="small text-center mt-1">Le sport</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-6.jpg" class="img-fluid rounded shadow-sm" alt="Élèves qui lisent à la bibliothèque" loading="lazy"><figcaption class="small text-center mt-1">La bibliothèque</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-7.jpg" class="img-fluid rounded shadow-sm" alt="Peintures accrochées pour l'exposition" loading="lazy"><figcaption class="small text-center mt-1">L'exposition</figcaption></figure>
          <figure class="col mb-0"><img src="images/galerie-8.jpg" class="img-fluid rounded shadow-sm" alt="La chorale de l'école chante" loading="lazy"><figcaption class="small text-center mt-1">La chorale</figcaption></figure>
        </div>
      </div>
    </section>

    <!-- ===== 5. ÉQUIPE ===== -->
    <section id="equipe" class="py-5">
      <div class="container">
        <h2 class="text-center fw-bold mb-4">L'équipe enseignante</h2>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
          <div class="col"><div class="card h-100 border-0 shadow-sm text-center">
            <img src="images/prof-durand.jpg" class="card-img-top" alt="Portrait de Mme Durand">
            <div class="card-body"><h3 class="card-title fs-5 mb-1">Mme Durand</h3><span class="badge text-bg-success mb-2">Directrice · CM2</span>
              <p class="card-text small">Passionnée d'astronomie, elle organise la nuit des étoiles.</p></div>
          </div></div>
          <div class="col"><div class="card h-100 border-0 shadow-sm text-center">
            <img src="images/prof-martin.jpg" class="card-img-top" alt="Portrait de M. Martin">
            <div class="card-body"><h3 class="card-title fs-5 mb-1">M. Martin</h3><span class="badge text-bg-primary mb-2">CE2 · CM1</span>
              <p class="card-text small">Il anime l'atelier robots et le club d'échecs.</p></div>
          </div></div>
          <div class="col"><div class="card h-100 border-0 shadow-sm text-center">
            <img src="images/prof-nguyen.jpg" class="card-img-top" alt="Portrait de Mme Nguyen">
            <div class="card-body"><h3 class="card-title fs-5 mb-1">Mme Nguyen</h3><span class="badge text-bg-warning mb-2">CP · CE1</span>
              <p class="card-text small">Elle adore les contes et fait vivre la bibliothèque.</p></div>
          </div></div>
          <div class="col"><div class="card h-100 border-0 shadow-sm text-center">
            <img src="images/prof-diallo.jpg" class="card-img-top" alt="Portrait de M. Diallo">
            <div class="card-body"><h3 class="card-title fs-5 mb-1">M. Diallo</h3><span class="badge text-bg-danger mb-2">Sport · Musique</span>
              <p class="card-text small">Basket, chorale, percussions : il ne s'arrête jamais !</p></div>
          </div></div>
        </div>
      </div>
    </section>

    <!-- ===== 6. CONTACT ===== -->
    <section id="contact" class="py-5 bg-body-tertiary">
      <div class="container">
        <h2 class="text-center fw-bold mb-4">Nous contacter</h2>
        <div class="row g-4">
          <div class="col-lg-5">
            <ul class="list-unstyled fs-5">
              <li class="mb-3"><i class="bi bi-geo-alt-fill text-success me-2" aria-hidden="true"></i>12 rue des Pins, 75000 Paris</li>
              <li class="mb-3"><i class="bi bi-telephone-fill text-success me-2" aria-hidden="true"></i>01 23 45 67 89</li>
              <li class="mb-3"><i class="bi bi-envelope-fill text-success me-2" aria-hidden="true"></i>contact@ecole-des-pins.fr</li>
              <li><i class="bi bi-clock-fill text-success me-2" aria-hidden="true"></i>Lundi – vendredi : 8 h 20 – 16 h 30</li>
            </ul>
          </div>
          <div class="col-lg-7">
            <form class="row g-3 p-4 bg-white rounded-3 shadow-sm" action="merci.html" method="post">
              <div class="col-md-6"><label for="nom" class="form-label">Nom du parent</label>
                <input type="text" class="form-control" id="nom" name="nom" autocomplete="name" required></div>
              <div class="col-md-6"><label for="email" class="form-label">E-mail</label>
                <input type="email" class="form-control" id="email" name="email" autocomplete="email" placeholder="nom@exemple.fr" required></div>
              <div class="col-12"><label for="classe" class="form-label">Classe concernée</label>
                <select class="form-select" id="classe" name="classe">
                  <option value="">Choisir une classe…</option><option>CP</option><option>CE1</option><option>CE2</option><option>CM1</option><option>CM2</option>
                </select></div>
              <div class="col-12"><label for="message" class="form-label">Votre message</label>
                <textarea class="form-control" id="message" name="message" rows="4" required></textarea></div>
              <div class="col-12"><div class="form-check">
                <input class="form-check-input" type="checkbox" id="accord" name="accord" required>
                <label class="form-check-label" for="accord">J'accepte que l'école me réponde par e-mail.</label></div></div>
              <div class="col-12"><button type="submit" class="btn btn-success btn-lg"><i class="bi bi-send-fill me-1" aria-hidden="true"></i> Envoyer le message</button></div>
            </form>
          </div>
        </div>
      </div>
    </section>
  </main>

  <!-- ===== 7. PIED DE PAGE ===== -->
  <footer class="text-bg-dark pt-5 pb-3">
    <div class="container">
      <div class="row g-4">
        <div class="col-md-5">
          <h2 class="fs-5"><i class="bi bi-tree-fill text-success" aria-hidden="true"></i> École des Pins</h2>
          <p class="text-white-50">École primaire publique. Apprendre, créer et grandir ensemble depuis 1985.</p>
        </div>
        <div class="col-6 col-md-3">
          <h2 class="fs-6 text-uppercase">Liens utiles</h2>
          <ul class="list-unstyled">
            <li><a href="menus.html" class="link-light text-decoration-none">Menus de la cantine</a></li>
            <li><a href="calendrier.html" class="link-light text-decoration-none">Calendrier</a></li>
            <li><a href="inscriptions.html" class="link-light text-decoration-none">Inscriptions</a></li>
          </ul>
        </div>
        <div class="col-6 col-md-4">
          <h2 class="fs-6 text-uppercase">Suivez-nous</h2>
          <a href="https://www.youtube.com" class="link-light fs-3 me-3" aria-label="Notre chaîne YouTube"><i class="bi bi-youtube" aria-hidden="true"></i></a>
          <a href="https://www.instagram.com" class="link-light fs-3 me-3" aria-label="Notre compte Instagram"><i class="bi bi-instagram" aria-hidden="true"></i></a>
          <a href="mailto:contact@ecole-des-pins.fr" class="link-light fs-3" aria-label="Nous écrire un e-mail"><i class="bi bi-envelope-fill" aria-hidden="true"></i></a>
        </div>
      </div>
      <hr class="border-secondary">
      <p class="text-center small text-white-50 mb-0">© 2026 École des Pins — Site réalisé avec Bootstrap 5</p>
    </div>
  </footer>

  <!-- Le JavaScript de Bootstrap, à la toute fin -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
```

Et voici le haut du site (navbar, bannière, début de l'équipe) tel qu'il s'affiche sur un téléphone :

```{.html .demo .plein largeur="390" hauteurmax="1500" legende="Le site terminé sur un téléphone (haut de la page)"}
<nav class="navbar navbar-expand-lg bg-white shadow-sm sticky-top">
  <div class="container">
    <a class="navbar-brand fw-bold text-success" href="#accueil"><i class="bi bi-tree-fill" aria-hidden="true"></i> École des Pins</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuEcole"
            aria-controls="menuEcole" aria-expanded="false" aria-label="Ouvrir le menu"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="menuEcole"><ul class="navbar-nav ms-auto"><li class="nav-item"><a class="nav-link" href="#ecole">L'école</a></li></ul></div>
  </div>
</nav>
<header id="accueil" class="bg-success bg-gradient text-white py-5">
  <div class="container">
    <div class="row align-items-center g-4">
      <div class="col-lg-6 text-center text-lg-start">
        <span class="badge text-bg-warning mb-3">🎉 Inscriptions ouvertes pour la rentrée</span>
        <h1 class="display-5 fw-bold">Bienvenue à l'École des Pins !</h1>
        <p class="lead">Une école où l'on apprend, crée et grandit ensemble, au milieu des arbres.</p>
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center justify-content-lg-start">
          <a href="#ecole" class="btn btn-warning btn-lg">Découvrir l'école</a>
          <a href="#contact" class="btn btn-outline-light btn-lg">Nous contacter</a>
        </div>
      </div>
      <div class="col-lg-6"><img src="images/ecole.jpg" class="img-fluid rounded-4 shadow" alt="La cour de l'École des Pins"></div>
    </div>
  </div>
</header>
<section id="equipe" class="py-5">
  <div class="container">
    <h2 class="text-center fw-bold mb-4">L'équipe enseignante</h2>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-4 g-4">
      <div class="col"><div class="card h-100 border-0 shadow-sm text-center">
        <img src="images/prof-durand.jpg" class="card-img-top" alt="Portrait de Mme Durand">
        <div class="card-body"><h3 class="card-title fs-5 mb-1">Mme Durand</h3><span class="badge text-bg-success mb-2">Directrice · CM2</span></div>
      </div></div>
    </div>
  </div>
</section>
```

::: defi
Ton site est terminé ! Remplace maintenant les textes et les photos par ceux de **ta propre école**, de ton club de sport ou de ta famille. Change la couleur principale (`success` → `primary`, `danger`…) et observe comme tout le site change d'ambiance.
:::

## Exercices du projet final

:::: {.exercice .niveau1}
### Exercice 18.1 — L'annonce des portes ouvertes

**🎯 Objectif :** ajouter un message important en haut du site.

**📝 Consignes :** juste sous la navbar, ajoute une alerte jaune **fermable** : « 📅 Portes ouvertes samedi 14 mars de 9 h à 12 h ! », avec un lien « En savoir plus ».

**🔍 Indices :** `alert alert-warning alert-dismissible fade show`, `rounded-0 mb-0` pour qu'elle touche les bords, `alert-link`, et le bouton `btn-close`.

::: solution
```{.html .demo .plein largeur="1000"}
<!-- rounded-0 : pas de coins arrondis ; mb-0 : collée à la bannière ; text-center : centrée -->
<div class="alert alert-warning alert-dismissible fade show rounded-0 mb-0 text-center" role="alert">
  📅 <strong>Portes ouvertes</strong> samedi 14 mars de 9 h à 12 h !
  <a href="portes-ouvertes.html" class="alert-link">En savoir plus</a>
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
</div>
```

**Explication :** c'est une alerte du chapitre 11 ; `rounded-0` et `mb-0` la transforment en bandeau pleine largeur, collé entre la navbar et la bannière. La croix permet aux familles de la fermer une fois lue.
:::
::::

:::: {.exercice .niveau2}
### Exercice 18.2 — La section « Actualités »

**🎯 Objectif :** créer une nouvelle section complète et l'ajouter au menu.

**📝 Consignes :** ajoute une section `id="actus"` avec 3 cartes d'actualités (image, date en badge, titre, texte, bouton « Lire »). 1 carte par ligne sur téléphone, 3 sur ordinateur. Ajoute le lien « Actus » dans la navbar.

**🔍 Indices :** même structure que la section équipe : `section.py-5 > .container > h2 + .row.row-cols-1.row-cols-md-3.g-4` ; dans la navbar : `<li class="nav-item"><a class="nav-link" href="#actus">Actus</a></li>`.

::: solution
```{.html .demo .plein largeur="1000"}
<section id="actus" class="py-5">
  <div class="container">
    <h2 class="text-center fw-bold mb-4">Les actualités</h2>
    <div class="row row-cols-1 row-cols-md-3 g-4">
      <div class="col">
        <div class="card h-100 shadow-sm">
          <img src="images/actu-carnaval.jpg" class="card-img-top" alt="Enfants déguisés pour le carnaval">
          <div class="card-body">
            <!-- La date dans un badge discret -->
            <span class="badge text-bg-light border mb-2">3 février</span>
            <h3 class="card-title fs-5">Le carnaval de l'école</h3>
            <p class="card-text">Thème de cette année : les animaux de la forêt !</p>
            <a href="carnaval.html" class="btn btn-outline-success btn-sm">Lire</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100 shadow-sm">
          <img src="images/actu-musee.jpg" class="card-img-top" alt="Une classe au musée des sciences">
          <div class="card-body">
            <span class="badge text-bg-light border mb-2">20 février</span>
            <h3 class="card-title fs-5">Sortie au musée</h3>
            <p class="card-text">Les CM1 ont découvert les dinosaures.</p>
            <a href="musee.html" class="btn btn-outline-success btn-sm">Lire</a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100 shadow-sm">
          <img src="images/actu-concours.jpg" class="card-img-top" alt="Coupe du concours de lecture">
          <div class="card-body">
            <span class="badge text-bg-light border mb-2">1er mars</span>
            <h3 class="card-title fs-5">Concours de lecture</h3>
            <p class="card-text">Bravo à Yanis, champion de la lecture à voix haute !</p>
            <a href="concours.html" class="btn btn-outline-success btn-sm">Lire</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
```

**Explication :** on réutilise les mêmes « briques » que dans le reste du site (`section.py-5`, `container`, titre centré, grille `row-cols`, cartes `h-100`) : le site reste cohérent. Le lien du menu `href="#actus"` mène à la section grâce à son `id`.
:::
::::

:::: {.exercice .niveau3}
### Exercice 18.3 — Agrandir les photos de la galerie

**🎯 Objectif :** combiner galerie et modale.

**📝 Consignes :** quand on clique sur la photo du potager, elle s'ouvre en grand dans une modale centrée, avec sa légende en titre. La photo doit rester un vrai bouton accessible.

**🔍 Indices :** entoure l'image d'un `<button>` sans style (`btn p-0 border-0`) avec `data-bs-toggle="modal"` ; modale `modal-lg modal-dialog-centered` ; image `img-fluid` dans le `modal-body`.

::: solution
```{.html .demo largeur="800" clic="#photoPotager" legende="Après un clic sur la photo du potager"}
<div class="row row-cols-2 row-cols-md-4 g-3">
  <figure class="col mb-0">
    <!-- Le bouton entoure l'image : btn p-0 border-0 enlève l'apparence de bouton -->
    <button type="button" class="btn p-0 border-0" id="photoPotager"
            data-bs-toggle="modal" data-bs-target="#modalePotager">
      <img src="images/galerie-1.jpg" class="img-fluid rounded shadow-sm" alt="Agrandir : des élèves plantent des tomates au potager">
    </button>
    <figcaption class="small text-center mt-1">Le potager</figcaption>
  </figure>
</div>

<!-- La modale qui montre la photo en grand -->
<div class="modal fade" id="modalePotager" tabindex="-1" aria-labelledby="titrePotager">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title fs-5" id="titrePotager">Le potager</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
      </div>
      <!-- p-0 : la photo touche les bords de la modale -->
      <div class="modal-body p-0">
        <img src="images/galerie-1.jpg" class="img-fluid w-100 rounded-bottom" alt="Des élèves plantent des tomates au potager">
      </div>
    </div>
  </div>
</div>
```

**Explication :** un `<button>` est utilisable au clavier (touche Tab puis Entrée), contrairement à une simple image : c'est donc lui qui ouvre la modale. Le texte `alt` de la petite image commence par « Agrandir » pour annoncer l'action. Pour toute la galerie, il suffit de répéter le principe avec une modale par photo (ou d'utiliser un carrousel dans une seule modale !).
:::
::::

## Quiz du projet final

::: quiz
1. Quelle est la toute première étape d'un projet de site ?
    - A) Écrire le footer
    - B) Ajouter des icônes
    - C) Analyser le besoin : pour qui, pour quoi, sur quel écran
    - D) Choisir les couleurs
2. Comment le lien `href="#galerie"` sait-il où aller ?
    - A) Grâce au titre « Galerie »
    - B) Grâce à l'`id="galerie"` de la section
    - C) Grâce à la classe `galerie`
    - D) Il va toujours en bas de la page
3. Que fait `sticky-top` sur la navbar ?
    - A) Elle passe en bas de la page
    - B) Elle disparaît quand on descend
    - C) Elle devient transparente
    - D) Elle reste collée en haut de l'écran pendant le défilement
4. Pourquoi mettre `h-100` sur les cartes de l'équipe ?
    - A) Pour arrondir les coins
    - B) Pour qu'elles fassent 100 pixels
    - C) Pour les cacher
    - D) Pour qu'elles aient toutes la même hauteur sur une ligne
5. À quoi sert `loading="lazy"` sur les images de la galerie ?
    - A) À charger les images seulement quand on s'en approche, pour accélérer la page
    - B) À empêcher le téléchargement
    - C) À flouter les images
    - D) À les afficher en noir et blanc
:::

::: reponses
1. **C** — On comprend d'abord ce que veut le client, avant de dessiner et de coder.
2. **B** — Le `#` désigne un `id` sur la même page.
3. **D** — La barre reste visible : on peut changer de section à tout moment.
4. **D** — Les cartes remplissent leur colonne : fini les cartes de hauteurs différentes.
5. **A** — Les images éloignées ne sont téléchargées qu'au moment utile.
:::

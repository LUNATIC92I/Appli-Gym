# Chapitre 14 — Le carrousel (diaporama) {#ch14}

::: chapitre-intro
Un **carrousel** fait défiler des images (ou des contenus) les unes après les autres, comme un diaporama. On le voit souvent en haut des sites pour présenter les nouveautés.
:::

::: analogie
Le carrousel ressemble à une **visionneuse de photos** : une seule photo est visible à la fois, et on passe à la suivante avec les flèches… ou elle avance toute seule, comme un manège qui tourne.
:::

## Comment est construit un carrousel

```{.ascii}
 div.carousel.slide  (id="monCarrousel")
 ├── div.carousel-indicators   ── les petits traits ▬ ▬ ▬ en bas
 ├── div.carousel-inner        ── la « pellicule »
 │   ├── div.carousel-item.active  ── diapo 1 (visible au départ)
 │   ├── div.carousel-item         ── diapo 2
 │   └── div.carousel-item         ── diapo 3
 ├── button.carousel-control-prev  ── flèche ‹ (précédente)
 └── button.carousel-control-next  ── flèche › (suivante)
```

::: retenir
Une seule diapo doit avoir la classe **`active`** : c'est celle qui s'affiche en premier. Sans elle, le carrousel reste vide !
:::

## Un carrousel complet

```{.html .demo largeur="600"}
<!-- carousel slide : un carrousel qui glisse ; l'id sert aux boutons -->
<div id="vacances" class="carousel slide">
  <!-- Les indicateurs : un bouton par diapo -->
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#vacances" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Diapo 1"></button>
    <button type="button" data-bs-target="#vacances" data-bs-slide-to="1" aria-label="Diapo 2"></button>
    <button type="button" data-bs-target="#vacances" data-bs-slide-to="2" aria-label="Diapo 3"></button>
  </div>
  <!-- La pellicule avec les diapos -->
  <div class="carousel-inner rounded">
    <!-- active : la première diapo visible -->
    <div class="carousel-item active">
      <!-- d-block w-100 : l'image prend toute la largeur -->
      <img src="images/plage.jpg" class="d-block w-100" alt="La plage au coucher du soleil">
    </div>
    <div class="carousel-item">
      <img src="images/montagne.jpg" class="d-block w-100" alt="Des montagnes enneigées">
    </div>
    <div class="carousel-item">
      <img src="images/foret.jpg" class="d-block w-100" alt="Une forêt en automne">
    </div>
  </div>
  <!-- Flèche « précédente » -->
  <button class="carousel-control-prev" type="button" data-bs-target="#vacances" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Précédente</span>
  </button>
  <!-- Flèche « suivante » -->
  <button class="carousel-control-next" type="button" data-bs-target="#vacances" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Suivante</span>
  </button>
</div>
```

**Ligne par ligne :**

- `carousel slide` : le carrousel, avec une animation de glissement.
- `data-bs-target="#vacances"` : chaque bouton dit **quel** carrousel il commande.
- `data-bs-slide-to="1"` : l'indicateur va directement à la diapo n°1 (on compte à partir de **0** !).
- `data-bs-slide="prev"` / `"next"` : reculer ou avancer d'une diapo.
- `visually-hidden` : le mot « Précédente » n'est pas affiché mais il est lu par les lecteurs d'écran.

Après un clic sur la flèche « suivante », la deuxième image apparaît :

```{.html .demo largeur="600" clic=".carousel-control-next" attente="300" legende="Après un clic sur la flèche › : la diapo 2 est affichée"}
<div id="vacances2" class="carousel slide">
  <div class="carousel-inner rounded">
    <div class="carousel-item active"><img src="images/plage.jpg" class="d-block w-100" alt="La plage"></div>
    <div class="carousel-item"><img src="images/montagne.jpg" class="d-block w-100" alt="La montagne"></div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#vacances2" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Précédente</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#vacances2" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Suivante</span>
  </button>
</div>
```

## Les options utiles

| Ajout | Effet |
|---|---|
| `data-bs-ride="carousel"` sur le carrousel | défile **tout seul** dès le chargement |
| `data-bs-interval="3000"` sur une diapo | cette diapo reste 3 secondes (3000 millisecondes) |
| `carousel-fade` à côté de `slide` | fondu enchaîné au lieu du glissement |
| `data-bs-theme="dark"` | flèches et indicateurs foncés (pour des images claires) |
| `div.carousel-caption` dans une diapo | une légende posée sur l'image |

## Ajouter des légendes

```{.html .demo largeur="600"}
<div id="planetes" class="carousel slide carousel-fade" data-bs-ride="carousel">
  <div class="carousel-inner rounded">
    <!-- data-bs-interval : la diapo reste 4 secondes -->
    <div class="carousel-item active" data-bs-interval="4000">
      <img src="images/mars.jpg" class="d-block w-100" alt="La planète Mars">
      <!-- La légende ; d-none d-md-block : cachée sur les petits écrans -->
      <div class="carousel-caption d-none d-md-block">
        <h2 class="fs-4">Mars, la planète rouge</h2>
        <p>Sa couleur vient de la rouille présente dans son sol.</p>
      </div>
    </div>
    <div class="carousel-item" data-bs-interval="4000">
      <img src="images/saturne.jpg" class="d-block w-100" alt="La planète Saturne">
      <div class="carousel-caption d-none d-md-block">
        <h2 class="fs-4">Saturne et ses anneaux</h2>
        <p>Ses anneaux sont faits de glace et de cailloux.</p>
      </div>
    </div>
  </div>
</div>
```

::: astuce
Utilise des images de **même taille** (même largeur et même hauteur) : sinon la hauteur du carrousel change à chaque diapo et la page « saute ».
:::

::: attention
Un carrousel qui défile tout seul peut gêner certaines personnes. Laisse toujours les flèches pour qu'on puisse avancer à son rythme ; Bootstrap met d'ailleurs le défilement en pause quand la souris est dessus.
:::

## Exercices du chapitre 14

:::: {.exercice .niveau1}
### Exercice 14.1 — Mes animaux préférés

**🎯 Objectif :** construire un carrousel simple avec des flèches.

**📝 Consignes :** crée un carrousel de 3 images (chat, chien, lapin) avec les flèches précédente / suivante.

**🔍 Indices :** n'oublie ni la classe `active` sur la première diapo, ni l'`id` utilisé par les `data-bs-target`.

::: solution
```{.html .demo largeur="560"}
<div id="animaux" class="carousel slide">
  <div class="carousel-inner rounded">
    <div class="carousel-item active"><img src="images/chat.jpg" class="d-block w-100" alt="Un chat roux"></div>
    <div class="carousel-item"><img src="images/chien.jpg" class="d-block w-100" alt="Un chien qui court"></div>
    <div class="carousel-item"><img src="images/lapin.jpg" class="d-block w-100" alt="Un lapin blanc"></div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#animaux" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Précédente</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#animaux" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Suivante</span>
  </button>
</div>
```

**Explication :** les trois diapos sont dans `carousel-inner` ; seule la première est `active`. Les deux boutons visent `#animaux`, l'`id` du carrousel.
:::
::::

:::: {.exercice .niveau2}
### Exercice 14.2 — Le diaporama automatique

**🎯 Objectif :** faire défiler le carrousel tout seul avec des indicateurs.

**📝 Consignes :** reprends l'exercice 14.1 en ajoutant les indicateurs, un défilement automatique et un temps de 2 secondes par diapo.

**🔍 Indices :** `data-bs-ride="carousel"`, `data-bs-interval="2000"`, `carousel-indicators` avec `data-bs-slide-to`.

::: solution
```{.html .demo largeur="560"}
<!-- data-bs-ride="carousel" : défilement automatique -->
<div id="animauxAuto" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#animauxAuto" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Chat"></button>
    <button type="button" data-bs-target="#animauxAuto" data-bs-slide-to="1" aria-label="Chien"></button>
    <button type="button" data-bs-target="#animauxAuto" data-bs-slide-to="2" aria-label="Lapin"></button>
  </div>
  <div class="carousel-inner rounded">
    <!-- data-bs-interval="2000" : 2 secondes par diapo -->
    <div class="carousel-item active" data-bs-interval="2000"><img src="images/chat.jpg" class="d-block w-100" alt="Un chat roux"></div>
    <div class="carousel-item" data-bs-interval="2000"><img src="images/chien.jpg" class="d-block w-100" alt="Un chien qui court"></div>
    <div class="carousel-item" data-bs-interval="2000"><img src="images/lapin.jpg" class="d-block w-100" alt="Un lapin blanc"></div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#animauxAuto" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Précédente</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#animauxAuto" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Suivante</span>
  </button>
</div>
```

**Explication :** l'indicateur n°0 est `active` comme la première diapo. Les numéros `data-bs-slide-to` commencent à 0. Chaque diapo reste 2000 ms grâce à `data-bs-interval`.
:::
::::

:::: {.exercice .niveau3}
### Exercice 14.3 — Projet : la vitrine du club photo

**🎯 Objectif :** combiner carrousel, légendes, fondu et carte.

**📝 Consignes :** dans une carte intitulée « 📸 Concours photo », place un carrousel en fondu avec 3 photos gagnantes. Chaque diapo a une légende (nom de la photo et de l'élève). Sous le carrousel, un bouton « Voter » occupe toute la largeur.

**🔍 Indices :** `card`, `card-header` ; `carousel slide carousel-fade` ; `carousel-caption` ; `d-grid` pour le bouton.

::: solution
```{.html .demo largeur="560"}
<div class="card shadow">
  <div class="card-header text-bg-dark fw-bold">📸 Concours photo de l'école</div>
  <!-- carousel-fade : les photos se remplacent en fondu -->
  <div id="concours" class="carousel slide carousel-fade">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="images/photo-lac.jpg" class="d-block w-100" alt="Un lac au lever du jour">
        <div class="carousel-caption">
          <h2 class="fs-5">1er prix : « Le lac endormi »</h2>
          <p class="mb-0">par Inès, 6e B</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="images/photo-abeille.jpg" class="d-block w-100" alt="Une abeille sur une fleur">
        <div class="carousel-caption">
          <h2 class="fs-5">2e prix : « Au travail ! »</h2>
          <p class="mb-0">par Lucas, 5e A</p>
        </div>
      </div>
      <div class="carousel-item">
        <img src="images/photo-ville.jpg" class="d-block w-100" alt="La ville la nuit">
        <div class="carousel-caption">
          <h2 class="fs-5">3e prix : « Lumières »</h2>
          <p class="mb-0">par Sami, 4e C</p>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#concours" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span><span class="visually-hidden">Précédente</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#concours" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span><span class="visually-hidden">Suivante</span>
    </button>
  </div>
  <!-- d-grid : le bouton prend toute la largeur de la carte -->
  <div class="card-body d-grid">
    <button type="button" class="btn btn-success">Voter pour ma photo préférée</button>
  </div>
</div>
```

**Explication :** le carrousel est directement dans la carte, entre l'en-tête et le corps. `carousel-fade` (toujours avec `slide`) remplace le glissement par un fondu. Les légendes `carousel-caption` s'affichent ici à toutes les tailles d'écran, car on n'a pas mis `d-none d-md-block`.
:::
::::

## Quiz du chapitre 14

::: quiz
1. Quelle classe doit avoir la diapo affichée en premier ?
    - A) `start`
    - B) `first`
    - C) `show`
    - D) `active`
2. Quel attribut fait défiler le carrousel tout seul ?
    - A) `data-bs-ride="carousel"`
    - B) `data-bs-slide="auto"`
    - C) `autoplay`
    - D) `data-auto="true"`
3. Que vaut `data-bs-slide-to` pour aller à la **deuxième** diapo ?
    - A) `2`
    - B) `0`
    - C) `1`
    - D) `second`
4. Quelle classe ajoute une légende sur une image ?
    - A) `carousel-text`
    - B) `carousel-caption`
    - C) `figure-caption`
    - D) `card-text`
5. Pourquoi utiliser des images de même taille ?
    - A) Pour que le carrousel ne change pas de hauteur
    - B) Parce que Bootstrap refuse les autres
    - C) Ce n'est pas utile
    - D) Pour que les images soient en couleur
:::

::: reponses
1. **D** — La diapo `active` est visible au départ.
2. **A** — `data-bs-ride="carousel"` lance le défilement automatique.
3. **C** — On compte à partir de 0 : la deuxième diapo porte le numéro 1.
4. **B** — `carousel-caption` place un texte centré en bas de l'image.
5. **A** — Sinon la page « saute » à chaque changement de diapo.
:::

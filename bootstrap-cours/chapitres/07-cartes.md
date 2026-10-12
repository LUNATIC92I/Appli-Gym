# Chapitre 7 — Les cartes (cards) {#ch7}

::: chapitre-intro
Les **cartes** sont des petites boîtes qui présentent une information complète : une image, un titre, un texte et un bouton. On les voit partout : fiches de produits, articles, profils de joueurs… C'est l'un des composants préférés des développeurs !
:::

::: analogie
Une carte Bootstrap ressemble à une **carte à collectionner** (Pokémon, footballeurs…) : une image en haut, un nom, une description et parfois des statistiques en bas. Chaque carte est indépendante, et on peut en ranger plein côte à côte dans un classeur… comme dans une grille !
:::

## La structure d'une carte

```{.ascii}
 ┌─────────────────────────────┐  ← .card          (la carte entière)
 │                             │
 │        🖼️  IMAGE             │  ← .card-img-top  (l'image du haut)
 │                             │
 ├─────────────────────────────┤
 │  .card-body                 │  ← le « corps » avec de l'espace intérieur
 │   Titre      .card-title    │
 │   Texte...   .card-text     │
 │  [ Bouton ]  .btn           │
 └─────────────────────────────┘
```

## Une première carte

Les images de ce chapitre sont rangées dans le dossier `images` de ton projet (par exemple `images/panda.jpg`). Utilise tes propres photos ou des images libres de droits.

```{.html .demo largeur="500"}
<!-- .card : la carte ; style="width: 18rem" fixe sa largeur (18 × la taille du texte) -->
<div class="card" style="width: 18rem;">
  <!-- L'image du haut ; alt décrit l'image pour ceux qui ne la voient pas -->
  <img src="images/panda.jpg" class="card-img-top" alt="Un panda qui mange du bambou">
  <!-- Le corps de la carte -->
  <div class="card-body">
    <!-- Le titre de la carte -->
    <h5 class="card-title">Le panda géant</h5>
    <!-- Le texte de la carte -->
    <p class="card-text">Il passe jusqu'à 14 heures par jour à manger du bambou !</p>
    <!-- Un bouton-lien vers une autre page -->
    <a href="panda.html" class="btn btn-primary">En savoir plus</a>
  </div>
</div>
```

::: attention
L'attribut `alt` de l'image est **obligatoire** : il décrit l'image pour les personnes aveugles et s'affiche si l'image ne se charge pas. Écris une vraie description, pas juste « image ».
:::

## En-tête, pied et liste

Une carte peut aussi avoir un **en-tête** (`card-header`), un **pied** (`card-footer`), un **sous-titre** (`card-subtitle`) et une **liste** (`list-group list-group-flush`).

```{.html .demo largeur="500"}
<!-- Une carte sans image, de 20rem de large -->
<div class="card" style="width: 20rem;">
  <!-- En-tête de la carte -->
  <div class="card-header">⚽ Fiche joueur</div>
  <div class="card-body">
    <h5 class="card-title">Inès Martin</h5>
    <!-- Sous-titre gris (text-body-secondary) ; mb-2 = petite marge dessous -->
    <h6 class="card-subtitle mb-2 text-body-secondary">Attaquante · 12 ans</h6>
    <p class="card-text">Capitaine de l'équipe des Cigognes, reine des penaltys.</p>
  </div>
  <!-- Une liste collée à la carte (flush = sans bordures extérieures) -->
  <ul class="list-group list-group-flush">
    <li class="list-group-item">Buts : 18</li>
    <li class="list-group-item">Passes décisives : 9</li>
  </ul>
  <!-- Pied de carte, texte gris -->
  <div class="card-footer text-body-secondary">Mis à jour lundi</div>
</div>
```

## Cartes colorées

On peut colorier une carte entière avec `text-bg-*` ou seulement sa bordure avec `border-*`.

```{.html .demo largeur="800"}
<div class="row g-3">
  <!-- Carte entièrement verte -->
  <div class="col-6">
    <div class="card text-bg-success">
      <div class="card-body">
        <h5 class="card-title">Défi réussi ✅</h5>
        <p class="card-text">Tu as lu 10 livres ce mois-ci !</p>
      </div>
    </div>
  </div>
  <!-- Carte avec une bordure rouge et un titre rouge -->
  <div class="col-6">
    <div class="card border-danger">
      <div class="card-body text-danger">
        <h5 class="card-title">Défi en cours 🔥</h5>
        <p class="card-text">Encore 3 jours pour finir ton exposé.</p>
      </div>
    </div>
  </div>
</div>
```

## Plusieurs cartes dans une grille

Le vrai pouvoir des cartes, c'est de les **aligner dans une grille** (chapitre 4). Avec `row-cols-*`, elles se réorganisent selon l'écran. La classe `h-100` donne à toutes les cartes d'une ligne la **même hauteur**.

```{.html .demo largeur="1000"}
<div class="container">
  <!-- 1 carte par ligne sur téléphone, 3 à partir de la tablette ; g-4 = espace -->
  <div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col">
      <!-- h-100 : toutes les cartes ont la même hauteur -->
      <div class="card h-100">
        <img src="images/koala.jpg" class="card-img-top" alt="Un koala accroché à un arbre">
        <div class="card-body">
          <h5 class="card-title">Le koala</h5>
          <p class="card-text">Il dort environ 20 heures par jour.</p>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <img src="images/manchot.jpg" class="card-img-top" alt="Un manchot sur la banquise">
        <div class="card-body">
          <h5 class="card-title">Le manchot</h5>
          <p class="card-text">Il ne vole pas, mais il nage jusqu'à 35 km/h ! C'est un champion de la plongée.</p>
        </div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <img src="images/renard.jpg" class="card-img-top" alt="Un renard roux dans la neige">
        <div class="card-body">
          <h5 class="card-title">Le renard</h5>
          <p class="card-text">Il entend une souris sous la neige.</p>
        </div>
      </div>
    </div>
  </div>
</div>
```

::: astuce
Sans `h-100`, la carte du manchot (au texte plus long) serait plus haute que les autres. Avec `h-100`, elles ont toutes la même hauteur : c'est beaucoup plus joli !
:::

## Image en fond : card-img-overlay

Avec `card-img-overlay`, le texte s'affiche **par-dessus** l'image, comme une affiche de cinéma.

```{.html .demo largeur="600"}
<!-- text-bg-dark : texte blanc ; l'image remplit la carte (card-img) -->
<div class="card text-bg-dark" style="max-width: 26rem;">
  <img src="images/foret.jpg" class="card-img" alt="Une forêt magique au coucher du soleil">
  <!-- Le texte posé sur l'image -->
  <div class="card-img-overlay">
    <h5 class="card-title">La forêt enchantée</h5>
    <p class="card-text">Une histoire écrite par la classe de 6e B.</p>
  </div>
</div>
```

::: retenir
- Une carte : `card` ➜ `card-img-top` (image) ➜ `card-body` ➜ `card-title`, `card-text`, bouton.
- Options : `card-header`, `card-footer`, `card-subtitle`, `list-group-flush`, `card-img-overlay`.
- Couleurs : `text-bg-*` ou `border-*`.
- Dans une grille : `row row-cols-1 row-cols-md-3 g-4` + `h-100` sur les cartes.
- Toujours un `alt` qui décrit l'image.
:::

## Projet pratique : le catalogue de la bibliothèque

On crée une page de bibliothèque avec 4 livres présentés en cartes : 1 par ligne sur téléphone, 2 sur tablette, 4 sur ordinateur. Chaque carte a une image, un titre, l'auteur, un badge d'âge et un bouton « Réserver ».

```{.html .demo largeur="1100"}
<div class="container">
  <!-- Titre de la page avec une petite marge dessous -->
  <h2 class="mb-4">📚 Les nouveautés de la bibliothèque</h2>
  <!-- La grille : 1 / 2 / 4 cartes par ligne -->
  <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
    <!-- Livre 1 -->
    <div class="col">
      <div class="card h-100 shadow-sm">
        <img src="images/livre-dragon.jpg" class="card-img-top" alt="Couverture : un dragon vert au-dessus d'un château">
        <div class="card-body">
          <h5 class="card-title">Le dragon du collège</h5>
          <p class="card-text text-body-secondary">de Camille Rivière</p>
        </div>
        <!-- Pied de carte : âge conseillé et bouton -->
        <div class="card-footer bg-transparent d-flex justify-content-between align-items-center">
          <span class="badge text-bg-info">9-12 ans</span>
          <button type="button" class="btn btn-sm btn-primary">Réserver</button>
        </div>
      </div>
    </div>
    <!-- Livre 2 -->
    <div class="col">
      <div class="card h-100 shadow-sm">
        <img src="images/livre-espace.jpg" class="card-img-top" alt="Couverture : une fusée qui décolle vers la Lune">
        <div class="card-body">
          <h5 class="card-title">Mission Lune</h5>
          <p class="card-text text-body-secondary">de Hugo Benali</p>
        </div>
        <div class="card-footer bg-transparent d-flex justify-content-between align-items-center">
          <span class="badge text-bg-info">10-14 ans</span>
          <button type="button" class="btn btn-sm btn-primary">Réserver</button>
        </div>
      </div>
    </div>
    <!-- Livre 3 : déjà emprunté, le bouton est désactivé -->
    <div class="col">
      <div class="card h-100 shadow-sm">
        <img src="images/livre-mystere.jpg" class="card-img-top" alt="Couverture : une loupe au-dessus d'une carte au trésor">
        <div class="card-body">
          <h5 class="card-title">Le mystère du grenier</h5>
          <p class="card-text text-body-secondary">de Zoé Lambert</p>
        </div>
        <div class="card-footer bg-transparent d-flex justify-content-between align-items-center">
          <span class="badge text-bg-info">8-11 ans</span>
          <button type="button" class="btn btn-sm btn-secondary" disabled>Emprunté</button>
        </div>
      </div>
    </div>
    <!-- Livre 4 -->
    <div class="col">
      <div class="card h-100 shadow-sm">
        <img src="images/livre-robot.jpg" class="card-img-top" alt="Couverture : un petit robot qui arrose une fleur">
        <div class="card-body">
          <h5 class="card-title">Bip, le robot jardinier</h5>
          <p class="card-text text-body-secondary">de Malik Haddad</p>
        </div>
        <div class="card-footer bg-transparent d-flex justify-content-between align-items-center">
          <span class="badge text-bg-info">10-13 ans</span>
          <button type="button" class="btn btn-sm btn-primary">Réserver</button>
        </div>
      </div>
    </div>
  </div>
</div>
```

**Explication des nouveautés :**

- `shadow-sm` ajoute une petite **ombre** sous chaque carte : elles semblent posées sur la page.
- `card-footer bg-transparent` : un pied de carte sans fond gris.
- `d-flex justify-content-between align-items-center` place le badge à gauche et le bouton à droite, alignés verticalement (on verra ces classes « flexbox » au chapitre 15).
- `badge text-bg-info` : une petite étiquette (chapitre 12).
- Le livre emprunté a un bouton gris **désactivé** : l'utilisateur comprend qu'il n'est pas disponible.

## Exercices du chapitre 7

:::: {.exercice .niveau1}
### Exercice 7.1 — Ma carte d'animal

**🎯 Objectif :** construire une carte complète.

**📝 Consignes :** crée une carte de 18rem de large sur ton animal préféré, avec une image, un titre, une phrase et un bouton vert « J'adore ».

**🔍 Indices :** reprends la structure `card` ➜ `img.card-img-top` ➜ `card-body` ➜ `card-title` / `card-text` / bouton.

::: solution
```{.html .demo largeur="500"}
<!-- La carte, de 18rem de large -->
<div class="card" style="width: 18rem;">
  <!-- L'image avec une description dans alt -->
  <img src="images/tortue.jpg" class="card-img-top" alt="Une tortue de mer qui nage">
  <div class="card-body">
    <h5 class="card-title">La tortue de mer</h5>
    <p class="card-text">Elle peut vivre plus de 80 ans et parcourir des milliers de kilomètres.</p>
    <!-- Bouton vert -->
    <button type="button" class="btn btn-success">J'adore</button>
  </div>
</div>
```

**Explication :** chaque partie a sa classe : la carte (`card`), l'image (`card-img-top`), le corps (`card-body`) avec titre et texte. Le bouton est un `<button>` car il ne mène pas vers une autre page.
:::
::::

:::: {.exercice .niveau2}
### Exercice 7.2 — La fiche de personnage

**🎯 Objectif :** utiliser en-tête, sous-titre, liste et pied de carte.

**📝 Consignes :** crée la fiche d'un personnage de jeu : en-tête « 🛡️ Personnage », titre (nom), sous-titre (classe et niveau), une phrase, une liste de 3 statistiques, et un pied « Dernière partie : hier ».

**🔍 Indices :** `card-header`, `card-subtitle mb-2 text-body-secondary`, `list-group list-group-flush`, `card-footer`.

::: solution
```{.html .demo largeur="500"}
<div class="card" style="width: 20rem;">
  <!-- En-tête -->
  <div class="card-header">🛡️ Personnage</div>
  <div class="card-body">
    <h5 class="card-title">Lyra la Brave</h5>
    <!-- Sous-titre gris -->
    <h6 class="card-subtitle mb-2 text-body-secondary">Archère · niveau 27</h6>
    <p class="card-text">Elle protège la forêt d'Émeraude depuis trois saisons.</p>
  </div>
  <!-- Liste des statistiques, collée aux bords de la carte -->
  <ul class="list-group list-group-flush">
    <li class="list-group-item">❤️ Vie : 320</li>
    <li class="list-group-item">⚔️ Attaque : 85</li>
    <li class="list-group-item">🛡️ Défense : 60</li>
  </ul>
  <!-- Pied de carte -->
  <div class="card-footer text-body-secondary">Dernière partie : hier</div>
</div>
```

**Explication :** `list-group-flush` supprime les bordures extérieures de la liste pour qu'elle se fonde dans la carte. L'en-tête et le pied ont un léger fond gris qui sépare bien les parties.
:::
::::

:::: {.exercice .niveau3}
### Exercice 7.3 — La galerie de cartes responsive

**🎯 Objectif :** aligner plusieurs cartes dans une grille responsive de même hauteur.

**📝 Consignes :** crée 3 cartes « sorties scolaires » (musée, ferme, planétarium) avec image, titre, texte de longueur différente et un pied de carte affichant la date. 1 carte par ligne sur téléphone, 3 sur tablette et plus. Les cartes doivent avoir la même hauteur.

**🔍 Indices :** `row row-cols-1 row-cols-md-3 g-4`, `card h-100`, `card-footer`.

::: solution
```{.html .demo largeur="1000"}
<div class="container">
  <div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col">
      <div class="card h-100">
        <img src="images/musee.jpg" class="card-img-top" alt="La grande salle d'un musée avec un squelette de dinosaure">
        <div class="card-body">
          <h5 class="card-title">🦖 Le musée</h5>
          <p class="card-text">Visite de la galerie des dinosaures.</p>
        </div>
        <!-- Le pied reste en bas grâce à h-100 sur la carte -->
        <div class="card-footer text-body-secondary">12 mars</div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <img src="images/ferme.jpg" class="card-img-top" alt="Des vaches dans un pré devant une ferme">
        <div class="card-body">
          <h5 class="card-title">🐄 La ferme</h5>
          <p class="card-text">Traite des vaches, fabrication de fromage et balade en tracteur dans les champs.</p>
        </div>
        <div class="card-footer text-body-secondary">4 avril</div>
      </div>
    </div>
    <div class="col">
      <div class="card h-100">
        <img src="images/planetarium.jpg" class="card-img-top" alt="Un dôme étoilé de planétarium">
        <div class="card-body">
          <h5 class="card-title">🪐 Le planétarium</h5>
          <p class="card-text">Voyage au milieu des planètes.</p>
        </div>
        <div class="card-footer text-body-secondary">22 mai</div>
      </div>
    </div>
  </div>
</div>
```

**Explication :** grâce à `h-100`, les trois cartes ont la même hauteur, et comme le `card-footer` est la dernière partie de la carte, les dates sont alignées en bas, même si les textes ont des longueurs différentes.
:::
::::

## Quiz du chapitre 7

::: quiz
1. Quelle classe contient le titre et le texte d'une carte ?
    - A) `card-img-top`
    - B) `card-group`
    - C) `card-header`
    - D) `card-body`
2. À quoi sert l'attribut `alt` d'une image ?
    - A) À décrire l'image pour ceux qui ne la voient pas
    - B) À rendre l'image cliquable
    - C) À changer la taille de l'image
    - D) À ajouter un filtre de couleur
3. Comment donner la même hauteur à des cartes placées côte à côte ?
    - A) `same-height` sur la row
    - B) `h-100` sur chaque carte
    - C) `card-equal`
    - D) C'est impossible
4. Quelle classe permet d'écrire du texte par-dessus une image ?
    - A) `card-on-image`
    - B) `img-text`
    - C) `card-text-top`
    - D) `card-img-overlay`
5. Quelle classe ajoute une petite ombre à une carte ?
    - A) `dark-sm`
    - B) `bg-shadow`
    - C) `shadow-sm`
    - D) `card-shadow`
:::

::: reponses
1. **D** — `card-body` est le « corps » de la carte, avec un espace intérieur.
2. **A** — `alt` décrit l'image (lecteurs d'écran, image qui ne se charge pas).
3. **B** — `h-100` donne 100 % de la hauteur de la colonne à chaque carte.
4. **D** — `card-img-overlay` superpose le contenu sur l'image.
5. **C** — `shadow-sm` (petite ombre) ; il existe aussi `shadow` et `shadow-lg`.
:::

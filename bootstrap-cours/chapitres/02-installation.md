# Chapitre 2 — Installer Bootstrap et créer son premier projet {#ch2}

::: chapitre-intro
Pour utiliser des Lego, il faut d'abord ouvrir la boîte ! Dans ce chapitre, tu vas apprendre à « brancher » Bootstrap sur ta page grâce à un **CDN**, à créer ton premier projet et à comprendre chaque ligne d'une page Bootstrap.
:::

## Le CDN : Bootstrap livré par Internet

Bootstrap est rangé dans deux fichiers :

- un fichier **CSS** (`bootstrap.min.css`) : tous les styles (couleurs, boutons, grilles…) ;
- un fichier **JavaScript** (`bootstrap.bundle.min.js`) : ce qui fait **bouger** les choses (menus qui s'ouvrent, fenêtres qui apparaissent, diaporamas…).

Ces fichiers sont stockés sur des serveurs spéciaux appelés **CDN** (*Content Delivery Network*, « réseau de livraison de contenu »). Ta page n'a qu'à dire : « Va chercher Bootstrap à cette adresse » et le navigateur le télécharge en un éclair.

::: analogie
Le CDN, c'est comme une **bibliothèque municipale** : au lieu d'acheter tous les livres, tu les empruntes quand tu en as besoin. Ta page « emprunte » Bootstrap sur Internet au moment où elle s'affiche.
:::

```{.ascii}
   Ton ordinateur                                  Internet
 ┌─────────────────┐   1. « Donne-moi Bootstrap » ┌──────────────────┐
 │  index.html     │ ───────────────────────────► │  CDN jsDelivr    │
 │  <link ...>     │                              │  bootstrap.css   │
 │  <script ...>   │ ◄─────────────────────────── │  bootstrap.js    │
 └─────────────────┘   2. « Voilà les fichiers ! »└──────────────────┘
          │
          ▼ 3. Le navigateur affiche ta page avec le style Bootstrap ✨
```

::: attention
Le CDN fonctionne seulement si tu es **connecté à Internet**. Sans connexion, ta page s'affichera sans style (comme l'exemple « Sans Bootstrap » du chapitre 1).
:::

## Créer ton premier projet, étape par étape

**Étape 1 — Créer un dossier.** Sur ton bureau, crée un dossier nommé `mon-site`. Tous les fichiers de ton projet iront dedans.

**Étape 2 — Ouvrir le dossier dans VS Code.** Lance Visual Studio Code, clique sur *Fichier* → *Ouvrir le dossier…* et choisis `mon-site`.

**Étape 3 — Créer le fichier `index.html`.** Clique sur l'icône « Nouveau fichier » et nomme-le `index.html`. Le nom `index` est spécial : c'est la page d'accueil d'un site.

**Étape 4 — Créer un dossier `images`.** Dans `mon-site`, crée un dossier `images` où tu rangeras tes photos.

```{.ascii}
 mon-site/                 ← le dossier de ton projet
 ├── index.html            ← ta page d'accueil
 └── images/               ← tes images (photo.jpg, logo.png...)
```

::: attention
Évite les espaces, les accents et les majuscules dans les noms de fichiers : écris `ma-page.html` et pas `Ma Page Été.html`. Sur Internet, ces caractères posent souvent des problèmes.
:::

## Le modèle de page Bootstrap (à garder précieusement !)

Voici le **modèle officiel** d'une page Bootstrap 5.3. Tu vas le réutiliser pour **toutes** tes pages. Chaque ligne est expliquée par un commentaire `<!-- ... -->` : les commentaires sont des notes pour les humains, le navigateur ne les affiche pas.

```{.html .demo .page largeur="700"}
<!-- 1. On annonce que c'est une page web moderne (HTML5) -->
<!doctype html>
<!-- 2. Début de la page ; lang="fr" indique qu'elle est en français -->
<html lang="fr">
  <!-- 3. La « tête » : des informations pour le navigateur, invisibles dans la page -->
  <head>
    <!-- 4. Encodage UTF-8 : pour que les accents (é, è, à, ç) s'affichent bien -->
    <meta charset="utf-8">
    <!-- 5. Indispensable pour que la page s'adapte aux téléphones -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 6. Le texte affiché dans l'onglet du navigateur -->
    <title>Ma première page Bootstrap</title>
    <!-- 7. On charge le CSS de Bootstrap depuis le CDN -->
    <!--    integrity = une empreinte qui vérifie que le fichier n'a pas été modifié -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  </head>
  <!-- 8. Le « corps » : tout ce qui s'affiche dans la page -->
  <body>
    <!-- 9. Un titre de premier niveau -->
    <h1>Bonjour le monde ! 👋</h1>
    <!-- 10. Le JavaScript de Bootstrap, placé tout à la fin du body -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
<!-- 11. Fin de la page -->
</html>
```

**Pour voir ta page :** enregistre le fichier (Ctrl + S, ou Cmd + S sur Mac), puis double-clique sur `index.html` dans ton dossier : il s'ouvre dans ton navigateur. Chaque fois que tu modifies le code, enregistre et appuie sur **F5** dans le navigateur pour rafraîchir.

::: astuce
Dans VS Code, tape simplement `!` puis appuie sur la touche **Tab** : un squelette HTML apparaît tout seul ! Il ne te reste qu'à ajouter les lignes de Bootstrap (`<link>` et `<script>`).
:::

## La structure d'une page, en image

```{.ascii}
 <!doctype html>
 <html lang="fr">
 ┌──────────────────────────── <head> ─────────────────────────────┐
 │  meta charset   →  les accents                                  │
 │  meta viewport  →  les téléphones                               │
 │  title          →  le nom de l'onglet                           │
 │  link Bootstrap →  LES STYLES (le CSS)            ★ en haut      │
 └─────────────────────────────────────────────────────────────────┘
 ┌──────────────────────────── <body> ─────────────────────────────┐
 │  Ton contenu : titres, textes, images, boutons, cartes...       │
 │                                                                 │
 │  script Bootstrap → LE MOUVEMENT (le JavaScript)  ★ tout en bas │
 └─────────────────────────────────────────────────────────────────┘
 </html>
```

::: analogie
La page est comme une **lettre** : le `<head>`, c'est l'enveloppe (adresse, timbre — des infos utiles mais pas le message), et le `<body>`, c'est la lettre elle-même que l'on lit.
:::

Pourquoi le `<script>` est-il **à la fin** ? Parce que le navigateur lit la page de haut en bas. En mettant le JavaScript à la fin, ton contenu s'affiche d'abord, très vite, et les petites animations arrivent juste après.

::: retenir
- Le **CSS** de Bootstrap se charge avec `<link>` dans le `<head>`.
- Le **JavaScript** de Bootstrap se charge avec `<script>` juste avant `</body>`.
- La ligne `<meta name="viewport" ...>` est indispensable pour les téléphones.
- Garde ce modèle : c'est le point de départ de **toutes** tes pages.
:::

## Ajouter du contenu Bootstrap

Remplaçons le simple titre par un peu de contenu stylé. On ne montre que la partie `<body>` : le reste du modèle ne change pas.

```{.html .demo largeur="700"}
<!-- Une boîte centrée (container) avec de l'espace au-dessus (mt-4) -->
<div class="container mt-4">
  <!-- display-4 : un très grand titre « vitrine » -->
  <h1 class="display-4">Mon super site</h1>
  <!-- text-muted : texte gris, plus discret -->
  <p class="text-muted">Créé par Léa, 12 ans, avec Bootstrap 5.3.</p>
  <!-- Un bouton principal bleu -->
  <button type="button" class="btn btn-primary">Découvrir</button>
</div>
```

## Exercices du chapitre 2

:::: {.exercice .niveau1}
### Exercice 2.1 — Mon fichier de départ

**🎯 Objectif :** créer et ouvrir une page Bootstrap qui fonctionne.

**📝 Consignes :**

1. Crée un dossier `exercices-bootstrap` et, dedans, un fichier `ex1.html`.
2. Recopie le modèle de page Bootstrap.
3. Change le titre de l'onglet en « Mon exercice 1 » et le `<h1>` en « Je sais installer Bootstrap ! ».
4. Ouvre la page dans ton navigateur.

**🔍 Indices :** le titre de l'onglet est dans `<title>`. Si la police du titre n'a pas changé par rapport à une page sans Bootstrap, vérifie la ligne `<link>`.

::: solution
```{.html .demo .page largeur="600"}
<!doctype html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Le nouveau titre de l'onglet -->
    <title>Mon exercice 1</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  </head>
  <body>
    <!-- Le nouveau titre de la page -->
    <h1>Je sais installer Bootstrap !</h1>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
</html>
```

**Explication :** seules deux lignes changent par rapport au modèle : `<title>` (l'onglet) et `<h1>` (la page). On reconnaît que Bootstrap est chargé grâce à la police : sans Bootstrap, le titre s'afficherait avec une police à empattements (comme dans un livre).
:::
::::

:::: {.exercice .niveau2}
### Exercice 2.2 — Trouver l'erreur

**🎯 Objectif :** savoir repérer pourquoi Bootstrap ne fonctionne pas.

**📝 Consignes :** Tom a écrit cette page mais son bouton reste gris. Trouve les **deux** erreurs.

```{.html}
<!doctype html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <title>Page de Tom</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  </head>
  <body>
    <button type="button" class="btn-primary">Valider</button>
  </body>
</html>
```

**🔍 Indices :** regarde les attributs de la balise `<link>`, puis compare les classes du bouton avec celles du chapitre 1. Il manque aussi une ligne importante pour les téléphones !

::: solution
1. Il manque `rel="stylesheet"` dans `<link>` : sans lui, le navigateur ne sait pas que ce fichier est une feuille de style et l'ignore.
2. Le bouton a seulement `btn-primary` : il faut **aussi** la classe de base `btn`.
3. Bonus : la ligne `<meta name="viewport" ...>` manque ; la page fonctionnera, mais sera mal adaptée aux téléphones.

```{.html .demo .page largeur="600"}
<!doctype html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <!-- Ajout de la ligne pour les téléphones -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Page de Tom</title>
    <!-- Ajout de rel="stylesheet" (et de l'empreinte de sécurité) -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  </head>
  <body class="p-3">
    <!-- Ajout de la classe de base "btn" -->
    <button type="button" class="btn btn-primary">Valider</button>
  </body>
</html>
```

**Explication :** avec Bootstrap, beaucoup de composants ont une **classe de base** (`btn`, `card`, `alert`…) et des **classes de variation** (`btn-primary`, `btn-lg`…). La classe de base est obligatoire.
:::
::::

:::: {.exercice .niveau3}
### Exercice 2.3 — Ma carte de visite

**🎯 Objectif :** combiner le modèle et des classes pour faire une petite page de présentation.

**📝 Consignes :** crée une page `moi.html` qui contient, dans une `<div class="container mt-4">` :

1. un grand titre avec ton prénom (`display-5`) ;
2. une phrase de présentation grise (`text-muted`) ;
3. deux boutons : « Mes jeux préférés » (bleu, `btn-primary`) et « Me contacter » (bordure bleue, `btn-outline-primary`).

**🔍 Indices :** deux boutons placés l'un après l'autre se mettent côte à côte. Pour les séparer un peu, ajoute `me-2` (marge à droite) au premier.

::: solution
```{.html .demo largeur="700"}
<!-- Boîte centrée avec une marge en haut -->
<div class="container mt-4">
  <!-- display-5 : titre vitrine, un peu moins grand que display-4 -->
  <h1 class="display-5">Salut, moi c'est Nolan !</h1>
  <!-- Phrase de présentation en gris -->
  <p class="text-muted">J'ai 11 ans, j'adore le basket, les jeux de construction et le code.</p>
  <!-- Bouton plein bleu ; me-2 = marge à droite pour l'espacer du suivant -->
  <button type="button" class="btn btn-primary me-2">Mes jeux préférés</button>
  <!-- Bouton avec seulement une bordure bleue -->
  <button type="button" class="btn btn-outline-primary">Me contacter</button>
</div>
```

**Explication :** `display-5` est une taille de titre spéciale de Bootstrap, plus grande que `h1`. `btn-outline-primary` donne un bouton « contour » (on le verra au chapitre 6). `me-2` signifie *margin end* (marge à la fin, c'est-à-dire à droite) de niveau 2 : on reverra ces petites classes d'espacement au chapitre 15.
:::
::::

## Quiz du chapitre 2

::: quiz
1. Que veut dire CDN ?
    - A) Un éditeur de code
    - B) Un type de bouton
    - C) Un réseau qui livre des fichiers sur Internet
    - D) Une couleur de Bootstrap
2. Où place-t-on la balise `<link>` qui charge le CSS de Bootstrap ?
    - A) N'importe où, ça ne change rien
    - B) Dans le `<title>`
    - C) Tout à la fin du `<body>`
    - D) Dans le `<head>`
3. À quoi sert `<meta name="viewport" ...>` ?
    - A) À adapter la page aux écrans des téléphones
    - B) À charger le JavaScript
    - C) À afficher les accents
    - D) À changer la couleur de la page
4. Où place-t-on le `<script>` de Bootstrap ?
    - A) Juste avant `</body>`
    - B) Dans un autre fichier obligatoirement
    - C) Dans le `<title>`
    - D) Avant `<!doctype html>`
5. Pourquoi le bouton `<button class="btn-primary">` reste-t-il gris ?
    - A) Parce que Bootstrap n'a pas de bouton bleu
    - B) Parce qu'il manque la classe de base `btn`
    - C) Parce que le bouton est trop petit
    - D) Parce qu'il faut écrire `class='primary'`
:::

::: reponses
1. **C** — CDN signifie *Content Delivery Network* : des serveurs qui livrent rapidement des fichiers comme Bootstrap.
2. **D** — Le CSS se charge dans le `<head>` pour que la page soit stylée dès son affichage.
3. **A** — La balise viewport permet à la page de s'adapter à la largeur des téléphones. Les accents, c'est `charset="utf-8"`.
4. **A** — Le JavaScript se met à la fin du `<body>` pour que le contenu s'affiche d'abord.
5. **B** — Il faut toujours la classe de base `btn` en plus de la couleur `btn-primary`.
:::

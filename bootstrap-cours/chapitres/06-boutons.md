# Chapitre 6 — Les boutons {#ch6}

::: chapitre-intro
Les boutons sont partout : « Jouer », « Envoyer », « J'aime », « Télécharger »… Avec Bootstrap, tu peux créer des boutons magnifiques de toutes les couleurs, de toutes les tailles et même avec des petites icônes.
:::

## Le bouton de base

Un bouton Bootstrap a besoin de **deux classes** : la classe de base `btn` et une classe de couleur `btn-...`.

```{.html .demo largeur="800"}
<!-- type="button" : un bouton simple (qui n'envoie pas de formulaire) -->
<button type="button" class="btn btn-primary">Primary</button>
<button type="button" class="btn btn-secondary">Secondary</button>
<button type="button" class="btn btn-success">Success</button>
<button type="button" class="btn btn-danger">Danger</button>
<button type="button" class="btn btn-warning">Warning</button>
<button type="button" class="btn btn-info">Info</button>
<button type="button" class="btn btn-light">Light</button>
<button type="button" class="btn btn-dark">Dark</button>
<!-- btn-link : un bouton qui ressemble à un lien -->
<button type="button" class="btn btn-link">Link</button>
```

::: analogie
`btn`, c'est la **forme** du gâteau (un rond avec des bords arrondis) ; `btn-success`, c'est le **glaçage** (vert). Sans la forme, pas de gâteau ; sans glaçage, un gâteau tout simple !
:::

## Les trois balises qui peuvent devenir des boutons

| Balise | Quand l'utiliser ? |
|---|---|
| `<button type="button">` | Une action dans la page (ouvrir une fenêtre, afficher un message…) |
| `<button type="submit">` | Envoyer un formulaire (chapitre 9) |
| `<a href="..." class="btn ...">` | Aller vers une autre page : c'est un **lien** déguisé en bouton |

```{.html .demo largeur="700"}
<!-- Un vrai bouton pour une action -->
<button type="button" class="btn btn-primary">Lancer la partie</button>
<!-- Un lien qui ressemble à un bouton : il mène à une autre page -->
<!-- role="button" aide les lecteurs d'écran à comprendre que c'est un bouton -->
<a href="regles.html" class="btn btn-outline-primary" role="button">Lire les règles</a>
```

::: astuce
Écris toujours `type="button"` sur un bouton qui n'envoie pas de formulaire. Sinon, s'il est placé dans un formulaire, il l'enverra par erreur !
:::

## Les boutons « contour » (outline)

Remplace `btn-primary` par `btn-outline-primary` : le bouton n'a plus qu'une **bordure** colorée. Quand on passe la souris dessus, il se remplit. Parfait pour une action moins importante.

```{.html .demo largeur="800"}
<!-- btn-outline-* : seulement la bordure et le texte sont colorés -->
<button type="button" class="btn btn-outline-primary">Primary</button>
<button type="button" class="btn btn-outline-success">Success</button>
<button type="button" class="btn btn-outline-danger">Danger</button>
<button type="button" class="btn btn-outline-warning">Warning</button>
<button type="button" class="btn btn-outline-dark">Dark</button>
```

## Les tailles

Ajoute `btn-lg` (*large*, grand) ou `btn-sm` (*small*, petit). Sans classe de taille, le bouton a une taille normale.

```{.html .demo largeur="700"}
<!-- Grand, normal et petit -->
<button type="button" class="btn btn-primary btn-lg">Grand bouton</button>
<button type="button" class="btn btn-primary">Bouton normal</button>
<button type="button" class="btn btn-primary btn-sm">Petit bouton</button>
```

### Un bouton sur toute la largeur

Pour un bouton qui prend toute la largeur (très pratique sur téléphone), on l'entoure d'une `div` avec `d-grid` (*display grid*). `gap-2` met un espace entre plusieurs boutons empilés.

```{.html .demo largeur="500"}
<!-- d-grid : les boutons prennent toute la largeur ; gap-2 : espace entre eux -->
<div class="d-grid gap-2">
  <button type="button" class="btn btn-success btn-lg">Jouer</button>
  <button type="button" class="btn btn-outline-secondary">Options</button>
</div>
```

## Les états : actif et désactivé

- `active` : le bouton semble **enfoncé** (sélectionné).
- l'attribut `disabled` : le bouton est **désactivé**, on ne peut plus cliquer (il devient pâle).

```{.html .demo largeur="700"}
<!-- active : le bouton paraît sélectionné ; aria-pressed le dit aux lecteurs d'écran -->
<button type="button" class="btn btn-primary active" aria-pressed="true">Sélectionné</button>
<!-- disabled : on ne peut plus cliquer dessus -->
<button type="button" class="btn btn-primary" disabled>Désactivé</button>
```

## Les groupes de boutons

Pour coller plusieurs boutons ensemble (comme une télécommande), on les met dans une `div` avec la classe `btn-group`.

```{.html .demo largeur="700"}
<!-- role="group" et aria-label décrivent le groupe pour l'accessibilité -->
<div class="btn-group" role="group" aria-label="Choix de la difficulté">
  <button type="button" class="btn btn-outline-primary">Facile</button>
  <button type="button" class="btn btn-outline-primary active" aria-pressed="true">Moyen</button>
  <button type="button" class="btn btn-outline-primary">Difficile</button>
</div>
```

## Les boutons avec icônes

Les **icônes** rendent les boutons plus clairs. Bootstrap a sa propre collection d'icônes, **Bootstrap Icons** (on la verra en détail au chapitre 17). Pour l'utiliser, ajoute cette ligne dans le `<head>` de ta page, sous celle de Bootstrap :

```{.html}
<!-- Chargement de la collection d'icônes Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" integrity="sha384-XGjxtQfXaH2tnPFa9x+ruJTuLE3Aa6LhHSWRr1XeTyhezb4abCG4ccI5AkVDxqC+" crossorigin="anonymous">
```

Une icône s'écrit avec une balise `<i>` et deux classes : `bi` et `bi-nom-de-l-icone`.

```{.html .demo largeur="800"}
<!-- L'icône est une balise <i> placée dans le bouton, avant le texte -->
<!-- aria-hidden="true" : les lecteurs d'écran ignorent l'icône (le texte suffit) -->
<button type="button" class="btn btn-danger"><i class="bi bi-heart-fill" aria-hidden="true"></i> J'aime</button>
<button type="button" class="btn btn-success"><i class="bi bi-download" aria-hidden="true"></i> Télécharger</button>
<button type="button" class="btn btn-primary"><i class="bi bi-send" aria-hidden="true"></i> Envoyer</button>
<!-- Un bouton avec SEULEMENT une icône : aria-label explique ce qu'il fait -->
<button type="button" class="btn btn-outline-dark" aria-label="Paramètres"><i class="bi bi-gear" aria-hidden="true"></i></button>
```

::: attention
Un bouton qui contient **seulement une icône** n'a pas de texte. Ajoute toujours `aria-label="..."` pour expliquer ce qu'il fait : les personnes aveugles utilisent un **lecteur d'écran** qui lit ce texte à voix haute.
:::

::: retenir
- Bouton = `btn` + une couleur (`btn-primary`, `btn-outline-success`…).
- Tailles : `btn-lg`, `btn-sm` ; pleine largeur : une `div class="d-grid"`.
- États : `active`, `disabled`.
- Groupe : `btn-group`.
- Icônes : `<i class="bi bi-..."></i>` avec la ligne Bootstrap Icons dans le `<head>`.
- Un lien vers une autre page : `<a href="..." class="btn ...">`.
:::

## Exercices du chapitre 6

:::: {.exercice .niveau1}
### Exercice 6.1 — Le menu de jeu

**🎯 Objectif :** créer des boutons colorés de différentes tailles.

**📝 Consignes :** crée un grand bouton vert « Nouvelle partie », un bouton bleu normal « Continuer » et un petit bouton gris « Quitter ».

**🔍 Indices :** `btn-success btn-lg`, `btn-primary`, `btn-secondary btn-sm`.

::: solution
```{.html .demo largeur="700"}
<!-- Le bouton principal est le plus grand -->
<button type="button" class="btn btn-success btn-lg">Nouvelle partie</button>
<!-- Taille normale -->
<button type="button" class="btn btn-primary">Continuer</button>
<!-- Petit bouton gris, moins important -->
<button type="button" class="btn btn-secondary btn-sm">Quitter</button>
```

**Explication :** la taille et la couleur montrent l'importance de chaque action : on voit tout de suite le bouton principal. Toujours `btn` + couleur (+ taille éventuelle).
:::
::::

:::: {.exercice .niveau2}
### Exercice 6.2 — Le menu pour téléphone

**🎯 Objectif :** empiler des boutons sur toute la largeur.

**📝 Consignes :** crée un menu de 3 boutons pleine largeur, espacés : « Jouer » (vert, grand), « Classement » (contour bleu) et « Boutique » (désactivé, avec la mention « bientôt »).

**🔍 Indices :** `d-grid gap-2` sur une `div` qui entoure les boutons ; l'attribut `disabled` pour le dernier.

::: solution
```{.html .demo largeur="420"}
<!-- d-grid : boutons pleine largeur ; gap-2 : espace entre eux -->
<div class="d-grid gap-2">
  <!-- Grand bouton vert : l'action principale -->
  <button type="button" class="btn btn-success btn-lg">Jouer</button>
  <!-- Bouton contour bleu -->
  <button type="button" class="btn btn-outline-primary">Classement</button>
  <!-- Bouton désactivé : on ne peut pas encore cliquer -->
  <button type="button" class="btn btn-outline-secondary" disabled>Boutique (bientôt)</button>
</div>
```

**Explication :** `d-grid` transforme la `div` en grille d'une seule colonne : chaque bouton s'étire sur toute la largeur. `disabled` grise le bouton et empêche le clic.
:::
::::

:::: {.exercice .niveau3}
### Exercice 6.3 — La barre d'outils du lecteur de musique

**🎯 Objectif :** combiner groupes de boutons et icônes accessibles.

**📝 Consignes :** crée un lecteur de musique avec un groupe de 3 boutons sombres contenant **uniquement des icônes** : précédent, lecture, suivant. Ajoute à côté un bouton rouge « ❤ J'aime » avec icône et texte.

**🔍 Indices :** icônes `bi-skip-backward-fill`, `bi-play-fill`, `bi-skip-forward-fill`, `bi-heart-fill`. N'oublie pas les `aria-label` sur les boutons sans texte. `ms-2` ajoute une marge à gauche.

::: solution
```{.html .demo largeur="700"}
<!-- Groupe de 3 boutons collés ; aria-label décrit le groupe -->
<div class="btn-group" role="group" aria-label="Commandes de lecture">
  <!-- Chaque bouton n'a qu'une icône : aria-label donne son nom -->
  <button type="button" class="btn btn-dark" aria-label="Morceau précédent"><i class="bi bi-skip-backward-fill" aria-hidden="true"></i></button>
  <button type="button" class="btn btn-dark btn-lg" aria-label="Lecture"><i class="bi bi-play-fill" aria-hidden="true"></i></button>
  <button type="button" class="btn btn-dark" aria-label="Morceau suivant"><i class="bi bi-skip-forward-fill" aria-hidden="true"></i></button>
</div>
<!-- Bouton J'aime avec icône ET texte ; ms-2 = marge à gauche -->
<button type="button" class="btn btn-danger ms-2"><i class="bi bi-heart-fill" aria-hidden="true"></i> J'aime</button>
```

**Explication :** `btn-group` colle les boutons ; le bouton « Lecture » est plus grand (`btn-lg`) car c'est le plus important. Les `aria-label` rendent le lecteur utilisable par tous, et `aria-hidden="true"` évite que l'icône soit lue en double.
:::
::::

## Quiz du chapitre 6

::: quiz
1. Quelles classes faut-il pour un bouton vert ?
    - A) `button success`
    - B) `btn btn-success`
    - C) `bg-success`
    - D) `btn-green`
2. Que fait `btn-outline-danger` ?
    - A) Un bouton désactivé
    - B) Un bouton qui disparaît
    - C) Un bouton rouge plein
    - D) Un bouton avec seulement une bordure rouge
3. Comment rendre un bouton impossible à cliquer ?
    - A) Attribut `hidden-click`
    - B) Classe `btn-stop`
    - C) Classe `active`
    - D) Attribut `disabled`
4. Pour aller vers une autre page avec un bouton, on utilise plutôt :
    - A) `<a href="page.html" class="btn btn-primary">`
    - B) `<button href="page.html">`
    - C) `<p class="btn">`
    - D) `<link class="btn">`
5. Pourquoi ajouter `aria-label` à un bouton qui ne contient qu'une icône ?
    - A) Pour que les lecteurs d'écran puissent dire ce que fait le bouton
    - B) Pour changer sa couleur
    - C) C'est obligatoire pour que l'icône s'affiche
    - D) Pour agrandir l'icône
:::

::: reponses
1. **B** — Toujours la classe de base `btn` + la couleur `btn-success`.
2. **D** — *outline* = contour : seule la bordure (et le texte) est colorée.
3. **D** — L'attribut `disabled` désactive le bouton.
4. **A** — Un lien `<a>` sert à changer de page ; Bootstrap peut lui donner l'apparence d'un bouton.
5. **A** — `aria-label` donne un nom au bouton pour les personnes qui utilisent un lecteur d'écran.
:::

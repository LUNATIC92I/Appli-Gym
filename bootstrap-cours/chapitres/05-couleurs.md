# Chapitre 5 — Les couleurs Bootstrap {#ch5}

::: chapitre-intro
Bootstrap possède une **palette de couleurs** avec des noms qui ont un sens : bleu pour l'important, vert pour la réussite, rouge pour le danger… Dans ce chapitre, tu apprends à colorier tes textes et tes fonds en un clin d'œil.
:::

## Les huit couleurs « thème »

Au lieu de parler de « bleu » ou de « rouge », Bootstrap donne aux couleurs un **rôle**. C'est comme un **code de la route** des couleurs : chacun comprend tout de suite le message.

| Nom | Couleur | Rôle | Exemple d'utilisation |
|---|---|---|---|
| `primary` | bleu | principal, le plus important | le bouton « Envoyer » |
| `secondary` | gris | secondaire, moins important | le bouton « Annuler » |
| `success` | vert | réussite, tout va bien | « Bravo, inscription réussie ! » |
| `danger` | rouge | danger, erreur, suppression | « Mot de passe incorrect » |
| `warning` | jaune | attention | « Plus que 2 places ! » |
| `info` | bleu clair | information | « Le site sera en maintenance demain » |
| `light` | blanc cassé | fond clair | une zone discrète |
| `dark` | presque noir | fond sombre | une barre de menu |

::: analogie
Les couleurs Bootstrap fonctionnent comme les **feux de circulation** : vert, tout va bien ; jaune (orange), attention ; rouge, stop, danger ! Tout le monde comprend sans lire.
:::

## Colorier le texte : `text-*`

Pour changer la couleur d'un texte, on ajoute `text-` suivi du nom de la couleur.

```{.html .demo largeur="600"}
<!-- Chaque paragraphe utilise une couleur de texte différente -->
<p class="text-primary">text-primary : le texte important</p>
<p class="text-secondary">text-secondary : un texte secondaire</p>
<p class="text-success">text-success : bravo, c'est réussi !</p>
<p class="text-danger">text-danger : oups, une erreur</p>
<p class="text-warning">text-warning : attention (peu lisible sur fond blanc !)</p>
<p class="text-info">text-info : une information</p>
<!-- text-light sur fond blanc est invisible : on lui met un fond sombre -->
<p class="text-light bg-dark">text-light : texte clair sur fond sombre</p>
<p class="text-dark">text-dark : texte très foncé</p>
```

## Colorier le fond : `bg-*`

Pour changer la couleur de **fond**, on utilise `bg-` (pour *background*).

```{.html .demo largeur="600"}
<!-- p-2 ajoute un espace intérieur ; mb-1 un petit espace en dessous -->
<div class="bg-primary text-white p-2 mb-1">bg-primary</div>
<div class="bg-secondary text-white p-2 mb-1">bg-secondary</div>
<div class="bg-success text-white p-2 mb-1">bg-success</div>
<div class="bg-danger text-white p-2 mb-1">bg-danger</div>
<div class="bg-warning text-dark p-2 mb-1">bg-warning</div>
<div class="bg-info text-dark p-2 mb-1">bg-info</div>
<div class="bg-light text-dark p-2 mb-1 border">bg-light</div>
<div class="bg-dark text-white p-2">bg-dark</div>
```

::: attention
Pense toujours au **contraste** : un texte doit être facile à lire sur son fond. Blanc sur bleu, oui ; jaune sur blanc, non ! Les personnes qui voient mal ont besoin d'un bon contraste.
:::

## La classe magique `text-bg-*`

Bootstrap 5.3 propose `text-bg-primary`, `text-bg-warning`, etc. : cette classe met la couleur de **fond** ET choisit automatiquement une couleur de texte **lisible** (blanc ou noir). Plus besoin de réfléchir !

```{.html .demo largeur="600"}
<!-- text-bg-* : fond coloré + texte lisible choisi automatiquement -->
<div class="text-bg-primary p-2 mb-1">text-bg-primary : texte blanc automatique</div>
<div class="text-bg-warning p-2 mb-1">text-bg-warning : texte noir automatique</div>
<div class="text-bg-info p-2">text-bg-info : texte noir automatique</div>
```

## Les couleurs douces : `-subtle` et `-emphasis`

Pour des fonds **pastel** (plus doux), Bootstrap 5.3 propose `bg-primary-subtle`, `bg-success-subtle`… Pour un texte foncé assorti : `text-primary-emphasis`.

```{.html .demo largeur="600"}
<!-- Fond pastel + texte foncé assorti + bordure assortie -->
<div class="bg-success-subtle text-success-emphasis border border-success-subtle rounded p-3 mb-2">
  🌱 Fond vert pastel : idéal pour un message doux.
</div>
<div class="bg-danger-subtle text-danger-emphasis border border-danger-subtle rounded p-3">
  🍓 Fond rouge pastel : pour attirer l'attention en douceur.
</div>
```

## Bordures et opacité

On peut aussi colorier une **bordure** : `border border-primary` (la classe `border` dessine la bordure, `border-primary` la colore). Et rendre une couleur plus transparente avec `bg-opacity-50` ou `text-opacity-50` (50 % de transparence).

```{.html .demo largeur="600"}
<!-- Bordure épaisse (border-3) de couleur danger, coins arrondis -->
<div class="border border-3 border-danger rounded p-2 mb-2">Bordure rouge épaisse</div>
<!-- Fond bleu à 25 % d'opacité : couleur très légère -->
<div class="bg-primary bg-opacity-25 p-2">Fond bleu à 25 % d'opacité</div>
```

::: retenir
- 8 couleurs thème : `primary`, `secondary`, `success`, `danger`, `warning`, `info`, `light`, `dark`.
- `text-*` colorie le **texte**, `bg-*` le **fond**, `border-*` la **bordure**.
- `text-bg-*` = fond coloré + texte lisible automatique.
- `-subtle` = version pastel, `-emphasis` = texte foncé assorti.
- Pense au **contraste** pour que tout le monde puisse lire.
:::

## Mini-projet : le tableau d'humeur de la classe

On construit une petite page qui affiche l'humeur du jour avec des cartes de couleur, en utilisant la grille du chapitre 4.

```{.html .demo largeur="900"}
<div class="container">
  <!-- Titre de la page -->
  <h2 class="mb-3">🌈 L'humeur de la classe aujourd'hui</h2>
  <!-- 2 colonnes sur téléphone, 4 sur tablette et ordinateur ; g-3 = espace -->
  <div class="row row-cols-2 row-cols-md-4 g-3">
    <!-- Chaque humeur : une boîte colorée avec un emoji et un texte -->
    <div class="col"><div class="text-bg-success rounded p-3 text-center">😀<br>Joyeux<br><strong>12</strong></div></div>
    <div class="col"><div class="text-bg-info rounded p-3 text-center">😴<br>Fatigués<br><strong>5</strong></div></div>
    <div class="col"><div class="text-bg-warning rounded p-3 text-center">😬<br>Stressés<br><strong>3</strong></div></div>
    <div class="col"><div class="text-bg-danger rounded p-3 text-center">😡<br>Grognons<br><strong>1</strong></div></div>
  </div>
  <!-- Une note en bas, dans un cadre pastel -->
  <p class="bg-primary-subtle text-primary-emphasis rounded p-2 mt-3 mb-0">💬 Sondage réalisé ce matin auprès de 21 élèves.</p>
</div>
```

**Explication :** on réutilise la grille (`row-cols-2 row-cols-md-4`), puis `text-bg-*` pour colorier chaque humeur avec un texte lisible, `rounded` pour arrondir les coins et `text-center` pour centrer. Le rôle des couleurs aide à comprendre : vert = joyeux, rouge = grognon.

## Exercices du chapitre 5

:::: {.exercice .niveau1}
### Exercice 5.1 — Le feu tricolore

**🎯 Objectif :** utiliser les couleurs de fond.

**📝 Consignes :** dessine un feu tricolore avec trois boîtes l'une sous l'autre : rouge « STOP », jaune « ATTENTION », vert « PASSE ».

**🔍 Indices :** `text-bg-danger`, `text-bg-warning`, `text-bg-success`, avec `p-3`, `mb-2` et `text-center`.

::: solution
```{.html .demo largeur="400"}
<!-- Trois boîtes de couleur, du haut vers le bas -->
<div class="text-bg-danger p-3 mb-2 text-center fw-bold">🔴 STOP</div>
<div class="text-bg-warning p-3 mb-2 text-center fw-bold">🟡 ATTENTION</div>
<div class="text-bg-success p-3 text-center fw-bold">🟢 PASSE</div>
```

**Explication :** `text-bg-*` choisit automatiquement un texte blanc sur rouge et vert, et noir sur jaune. `fw-bold` met le texte en gras.
:::
::::

:::: {.exercice .niveau2}
### Exercice 5.2 — Les messages du jeu

**🎯 Objectif :** choisir la bonne couleur selon le sens du message.

**📝 Consignes :** affiche quatre messages pastel avec la couleur adaptée : « Niveau terminé ! », « Il te reste 1 vie », « Partie perdue… », « Nouveau niveau disponible ».

**🔍 Indices :** réussite = success, attention = warning, échec = danger, information = info. Utilise `bg-*-subtle` + `text-*-emphasis` + `rounded p-2 mb-2`.

::: solution
```{.html .demo largeur="600"}
<!-- Réussite : vert -->
<p class="bg-success-subtle text-success-emphasis rounded p-2 mb-2">🏆 Niveau terminé !</p>
<!-- Attention : jaune -->
<p class="bg-warning-subtle text-warning-emphasis rounded p-2 mb-2">❤️ Il te reste 1 vie</p>
<!-- Échec : rouge -->
<p class="bg-danger-subtle text-danger-emphasis rounded p-2 mb-2">💀 Partie perdue…</p>
<!-- Information : bleu clair -->
<p class="bg-info-subtle text-info-emphasis rounded p-2 mb-0">📢 Nouveau niveau disponible</p>
```

**Explication :** chaque message a une couleur qui correspond à son **sens**. Le joueur comprend avant même de lire. Les versions `-subtle` sont plus douces que les couleurs pleines.
:::
::::

:::: {.exercice .niveau3}
### Exercice 5.3 — La palette de mes jeux préférés

**🎯 Objectif :** combiner grille, couleurs et bordures.

**📝 Consignes :** crée une grille de 3 cartes (1 par ligne sur téléphone, 3 sur tablette) présentant tes jeux préférés. Chaque carte a une **bordure épaisse colorée**, un titre coloré de la même couleur et une phrase.

**🔍 Indices :** colonne `col-12 col-md-4`, boîte intérieure `border border-3 border-primary rounded p-3 h-100` ; titre `text-primary`. `h-100` donne à toutes les boîtes la même hauteur.

::: solution
```{.html .demo largeur="900"}
<div class="container">
  <div class="row g-3">
    <!-- Carte 1 : bleue -->
    <div class="col-12 col-md-4">
      <div class="border border-3 border-primary rounded p-3 h-100">
        <h3 class="h5 text-primary">⛏️ Jeu de construction</h3>
        <p class="mb-0">Je construis des châteaux géants avec mes amis.</p>
      </div>
    </div>
    <!-- Carte 2 : verte -->
    <div class="col-12 col-md-4">
      <div class="border border-3 border-success rounded p-3 h-100">
        <h3 class="h5 text-success">⚽ Jeu de foot</h3>
        <p class="mb-0">Je gagne la coupe avec mon équipe préférée.</p>
      </div>
    </div>
    <!-- Carte 3 : rouge -->
    <div class="col-12 col-md-4">
      <div class="border border-3 border-danger rounded p-3 h-100">
        <h3 class="h5 text-danger">🏎️ Jeu de course</h3>
        <p class="mb-0">Je fais des dérapages dans les virages.</p>
      </div>
    </div>
  </div>
</div>
```

**Explication :** `border` dessine la bordure, `border-3` l'épaissit, `border-primary` la colore. `h-100` (*height 100 %*) fait que toutes les boîtes ont la hauteur de la plus grande, pour un alignement parfait. `h5` donne au titre `<h3>` la taille d'un petit titre.
:::
::::

## Quiz du chapitre 5

::: quiz
1. Quelle couleur Bootstrap utiliser pour un message d'erreur ?
    - A) `danger`
    - B) `light`
    - C) `success`
    - D) `info`
2. Quelle classe colore le **fond** en vert ?
    - A) `border-success`
    - B) `green`
    - C) `text-success`
    - D) `bg-success`
3. Que fait `text-bg-warning` ?
    - A) Un texte jaune sur fond blanc
    - B) Un texte qui clignote
    - C) Un fond jaune avec un texte lisible choisi automatiquement
    - D) Rien, cette classe n'existe pas
4. Pourquoi faut-il faire attention au contraste ?
    - A) Pour que le texte soit lisible par tout le monde
    - B) Ce n'est pas important
    - C) Pour que les couleurs soient plus vives
    - D) Pour que la page charge plus vite
5. Quelle classe donne un fond bleu pastel ?
    - A) `bg-blue-light`
    - B) `text-primary`
    - C) `bg-primary-subtle`
    - D) `bg-primary-pastel`
:::

::: reponses
1. **A** — `danger` (rouge) signale une erreur ou un danger.
2. **D** — `bg-` concerne le fond (*background*) ; `text-success` colorerait le texte.
3. **C** — `text-bg-*` combine la couleur de fond et un texte lisible automatiquement.
4. **A** — Un bon contraste permet à tous, y compris aux personnes malvoyantes, de lire facilement.
5. **C** — Les versions pastel se terminent par `-subtle`.
:::

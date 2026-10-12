# Chapitre 15 — Les classes utilitaires {#ch15}

::: chapitre-intro
Les **classes utilitaires** sont de petites classes qui font **une seule chose** : ajouter de l'espace, centrer, cacher, aligner… En les combinant, on règle la mise en page sans écrire une ligne de CSS.
:::

::: analogie
Les utilitaires sont comme une **boîte à outils** : un tournevis (marge), un marteau (padding), une règle (alignement)… Chaque outil est simple, mais ensemble ils permettent de tout construire.
:::

## Marges et padding : le code secret

```{.ascii}
   ┌──────────── marge (margin) : l'espace AUTOUR de la boîte ──────┐
   │   ┌──────── bordure ────────────────────────────────────────┐  │
   │   │   padding : l'espace À L'INTÉRIEUR, autour du texte     │  │
   │   │        ┌──────────────────────┐                         │  │
   │   │        │   le contenu (texte) │                         │  │
   │   │        └──────────────────────┘                         │  │
   │   └─────────────────────────────────────────────────────────┘  │
   └────────────────────────────────────────────────────────────────┘
```

Le nom d'une classe d'espacement se lit comme un code en 3 morceaux : **quoi** + **côté** + **taille**.

| Quoi | Côté | Taille |
|---|---|---|
| `m` = marge | `t` = haut (*top*) | `0` = rien |
| `p` = padding | `b` = bas (*bottom*) | `1` = tout petit |
| | `s` = début / gauche (*start*) | `2` = petit |
| | `e` = fin / droite (*end*) | `3` = moyen |
| | `x` = gauche + droite | `4` = grand |
| | `y` = haut + bas | `5` = très grand |
| | (rien) = les 4 côtés | `auto` = automatique (marges) |

Exemples : `mt-3` = marge en haut moyenne ; `px-4` = padding gauche et droite grand ; `p-0` = aucun padding ; `mx-auto` = centre une boîte qui a une largeur.

```{.html .demo largeur="700"}
<!-- p-1, p-3, p-5 : de plus en plus d'espace à l'intérieur -->
<div class="d-flex gap-2 align-items-start">
  <div class="p-1 bg-primary-subtle border border-primary">p-1</div>
  <div class="p-3 bg-primary-subtle border border-primary">p-3</div>
  <div class="p-5 bg-primary-subtle border border-primary">p-5</div>
</div>
<!-- mt-4 : marge en haut ; ms-5 : marge à gauche -->
<div class="mt-4 ms-5 p-2 bg-warning-subtle border border-warning">mt-4 ms-5 : je suis poussé vers le bas et vers la droite</div>
<!-- mx-auto + w-50 : boîte centrée -->
<div class="mx-auto w-50 mt-3 p-2 text-center bg-success-subtle border border-success">w-50 mx-auto : centrée</div>
```

## Largeur, hauteur et bordures

- `w-25`, `w-50`, `w-75`, `w-100` : largeur en % du parent ; `h-100` : toute la hauteur.
- `border`, `border-top`, `border-0`, `border-3` (épaisseur), `border-danger` (couleur).
- `rounded`, `rounded-3`, `rounded-circle`, `rounded-pill` : coins arrondis.
- `shadow-sm`, `shadow`, `shadow-lg` : ombres.

## Display : afficher ou cacher

`d-*` change la façon dont un élément s'affiche : `d-none` (caché), `d-block` (bloc, prend toute la ligne), `d-inline` (dans le texte), `d-flex` (boîte flexible). Avec un point de rupture, on cache ou montre selon l'écran : `d-none d-md-block` = caché sur téléphone, visible à partir des tablettes (on y revient au chapitre 16).

## Flexbox : ranger des éléments en ligne

`d-flex` transforme un élément en **boîte flexible** : ses enfants se rangent côte à côte et on choisit comment les aligner.

| Classe | Effet |
|---|---|
| `justify-content-start / center / end` | aligne sur l'axe horizontal (gauche / centre / droite) |
| `justify-content-between` | écarte au maximum (un à chaque bout) |
| `justify-content-around` | répartit avec de l'espace autour |
| `align-items-start / center / end` | aligne verticalement |
| `flex-column` | range en colonne au lieu d'en ligne |
| `flex-wrap` | passe à la ligne s'il n'y a plus de place |
| `gap-2`, `gap-3` | espace entre les enfants |

```{.html .demo largeur="700"}
<!-- justify-content-between : un élément à chaque bout -->
<div class="d-flex justify-content-between p-2 mb-2 border rounded">
  <span class="badge text-bg-primary">A</span><span class="badge text-bg-primary">B</span><span class="badge text-bg-primary">C</span>
</div>
<!-- justify-content-center + gap-3 : au centre, espacés -->
<div class="d-flex justify-content-center gap-3 p-2 mb-2 border rounded">
  <span class="badge text-bg-success">A</span><span class="badge text-bg-success">B</span><span class="badge text-bg-success">C</span>
</div>
<!-- align-items-center : centré verticalement dans une boîte haute -->
<div class="d-flex align-items-center justify-content-end gap-2 p-2 border rounded" style="height: 90px;">
  <span class="badge text-bg-danger">en bas ?</span><span class="badge text-bg-danger">non : au milieu, à droite !</span>
</div>
```

## Le texte

- Alignement : `text-start`, `text-center`, `text-end`.
- Graisse et style : `fw-bold`, `fw-light`, `fst-italic`.
- Taille : `fs-1` (très grand) à `fs-6` (petit).
- Transformations : `text-uppercase` (MAJUSCULES), `text-decoration-none` (enlève le soulignement d'un lien).

## Position

`position-relative` / `position-absolute` / `position-fixed` / `sticky-top`, avec les placements `top-0`, `bottom-0`, `start-0`, `end-0`, `start-50`, `top-50` et `translate-middle` pour se centrer sur un point. On l'a utilisé pour les bulles de notification (chapitre 12).

```{.html .demo largeur="600"}
<!-- La boîte grise est le repère (position-relative) -->
<div class="position-relative bg-light border rounded" style="height: 140px;">
  <!-- Chaque pastille est placée dans un coin ou au centre -->
  <span class="position-absolute top-0 start-0 badge text-bg-primary m-2">haut gauche</span>
  <span class="position-absolute top-0 end-0 badge text-bg-success m-2">haut droite</span>
  <span class="position-absolute top-50 start-50 translate-middle badge text-bg-dark">centre</span>
  <span class="position-absolute bottom-0 start-0 badge text-bg-warning m-2">bas gauche</span>
  <span class="position-absolute bottom-0 end-0 badge text-bg-danger m-2">bas droite</span>
</div>
```

::: retenir
- Espacement : `m`/`p` + côté (`t b s e x y`) + taille (`0` à `5`, `auto`).
- `d-none` cache, `d-flex` range en ligne ; `justify-content-*` (horizontal), `align-items-*` (vertical), `gap-*`.
- `text-center`, `fw-bold`, `fs-3`, `w-50`, `rounded`, `shadow`…
- `position-relative` sur le parent, `position-absolute` + `top/bottom/start/end` sur l'enfant.
:::

::: astuce
Avant d'écrire du CSS, demande-toi : « Existe-t-il une classe utilitaire pour ça ? ». Neuf fois sur dix, la réponse est oui !
:::

## Exercices du chapitre 15

:::: {.exercice .niveau1}
### Exercice 15.1 — La carte d'identité du héros

**🎯 Objectif :** utiliser marges, padding, bordures et texte.

**📝 Consignes :** crée une boîte de largeur 50 %, centrée, avec une bordure bleue épaisse, des coins arrondis, une ombre et un grand padding. À l'intérieur : un titre centré en gras « Super Zoé » et une phrase en italique.

**🔍 Indices :** `w-50 mx-auto`, `border border-3 border-primary`, `rounded-3 shadow p-4`, `text-center fw-bold`, `fst-italic`.

::: solution
```{.html .demo largeur="700"}
<!-- w-50 mx-auto : moitié de largeur, centrée -->
<!-- border-3 border-primary : bordure épaisse bleue ; rounded-3 : bien arrondie -->
<div class="w-50 mx-auto border border-3 border-primary rounded-3 shadow p-4">
  <!-- text-center : centré ; fw-bold : gras ; mb-2 : petite marge dessous -->
  <h2 class="text-center fw-bold fs-4 mb-2">🦸 Super Zoé</h2>
  <!-- fst-italic : italique ; mb-0 : pas de marge dessous -->
  <p class="fst-italic text-center mb-0">Super-pouvoir : finir ses devoirs en 5 minutes.</p>
</div>
```

**Explication :** chaque classe fait une seule chose et on les additionne. `mx-auto` ne centre que parce que la boîte a une largeur (`w-50`). `p-4` éloigne le texte de la bordure.
:::
::::

:::: {.exercice .niveau2}
### Exercice 15.2 — La barre du lecteur de musique

**🎯 Objectif :** maîtriser `d-flex`, `justify-content` et `align-items`.

**📝 Consignes :** crée une barre sombre avec : à gauche le titre de la chanson, au centre trois boutons (précédent, lecture, suivant) et à droite le temps « 1:23 ». Tout est centré verticalement.

**🔍 Indices :** `d-flex justify-content-between align-items-center` sur la barre, et un `div` avec `d-flex gap-2` pour les boutons. Icônes : `bi-skip-backward-fill`, `bi-play-fill`, `bi-skip-forward-fill`.

::: solution
```{.html .demo largeur="700"}
<!-- La barre : 3 groupes répartis (between) et centrés en hauteur -->
<div class="d-flex justify-content-between align-items-center text-bg-dark rounded-pill px-4 py-2">
  <!-- Groupe 1 : le titre -->
  <span class="fw-bold">🎵 La danse des pingouins</span>
  <!-- Groupe 2 : les boutons, espacés par gap-2 -->
  <div class="d-flex gap-2">
    <button type="button" class="btn btn-outline-light btn-sm" aria-label="Précédent"><i class="bi bi-skip-backward-fill" aria-hidden="true"></i></button>
    <button type="button" class="btn btn-light btn-sm" aria-label="Lecture"><i class="bi bi-play-fill" aria-hidden="true"></i></button>
    <button type="button" class="btn btn-outline-light btn-sm" aria-label="Suivant"><i class="bi bi-skip-forward-fill" aria-hidden="true"></i></button>
  </div>
  <!-- Groupe 3 : le temps -->
  <span class="small">1:23</span>
</div>
```

**Explication :** la barre contient 3 enfants ; `justify-content-between` en met un à gauche, un au centre, un à droite. `align-items-center` les aligne en hauteur. Le groupe de boutons est lui-même un `d-flex` : on peut emboîter les boîtes flexibles.
:::
::::

:::: {.exercice .niveau3}
### Exercice 15.3 — La vignette de jeu vidéo

**🎯 Objectif :** combiner position, flexbox et espacements.

**📝 Consignes :** crée une vignette (largeur 18rem) avec une image. Sur l'image, en haut à droite, un badge « -40 % ». Sous l'image, une ligne avec le nom du jeu à gauche et le prix à droite, puis deux boutons côte à côte qui se partagent la largeur.

**🔍 Indices :** conteneur `position-relative` ; badge `position-absolute top-0 end-0 m-2` ; ligne `d-flex justify-content-between` ; boutons dans `d-flex gap-2` avec `flex-fill` sur chacun.

::: solution
```{.html .demo largeur="500"}
<!-- La vignette : bordure, coins arrondis, ombre ; overflow-hidden coupe ce qui dépasse -->
<div class="border rounded-3 shadow-sm overflow-hidden" style="width: 18rem;">
  <!-- Le repère pour placer le badge sur l'image -->
  <div class="position-relative">
    <img src="images/jeu-dragon.jpg" class="w-100 d-block" alt="Affiche du jeu Dragon Quest Kids">
    <span class="position-absolute top-0 end-0 m-2 badge text-bg-danger fs-6">-40 %</span>
  </div>
  <!-- p-3 : espace intérieur pour le texte et les boutons -->
  <div class="p-3">
    <!-- Nom à gauche, prix à droite ; mb-3 : espace avant les boutons -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <span class="fw-bold">Dragon Kids</span>
      <span class="text-success fw-bold">11,99 €</span>
    </div>
    <!-- flex-fill : chaque bouton prend la même part de largeur -->
    <div class="d-flex gap-2">
      <button type="button" class="btn btn-primary flex-fill">Acheter</button>
      <button type="button" class="btn btn-outline-secondary flex-fill">Détails</button>
    </div>
  </div>
</div>
```

**Explication :** la `div.position-relative` entoure seulement l'image : le badge se place donc par rapport à l'image, pas à toute la vignette. `overflow-hidden` arrondit aussi les coins de l'image. `flex-fill` répartit l'espace entre les boutons.
:::
::::

## Quiz du chapitre 15

::: quiz
1. Que signifie la classe `pt-3` ?
    - A) Padding en haut, taille 3
    - B) Marge en haut, taille 3
    - C) Police taille 3
    - D) Padding sur 3 côtés
2. Quelle classe centre horizontalement une boîte qui a une largeur ?
    - A) `align-center`
    - B) `center`
    - C) `text-center`
    - D) `mx-auto`
3. Quelle classe cache un élément ?
    - A) `opacity-100`
    - B) `d-hide`
    - C) `hidden-all`
    - D) `d-none`
4. Dans un `d-flex`, quelle classe aligne les enfants **verticalement** au centre ?
    - A) `vertical-center`
    - B) `text-center`
    - C) `align-items-center`
    - D) `justify-content-center`
5. Que fait `justify-content-between` ?
    - A) Supprime les espaces
    - B) Met tout au centre
    - C) Écarte les éléments : le premier au début, le dernier à la fin
    - D) Met les éléments en colonne
:::

::: reponses
1. **A** — `p` = padding, `t` = top (haut), `3` = taille moyenne.
2. **D** — Les marges automatiques à gauche et à droite centrent la boîte.
3. **D** — `d-none` = *display: none*.
4. **C** — `align-items` agit sur l'axe vertical (pour une rangée), `justify-content` sur l'axe horizontal.
5. **C** — L'espace libre est réparti **entre** les éléments.
:::

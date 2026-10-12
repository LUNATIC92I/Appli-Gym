# Chapitre 16 — Le responsive design {#ch16}

::: chapitre-intro
Un site **responsive** s'adapte à **tous les écrans** : petit téléphone, tablette, grand ordinateur. C'est indispensable : aujourd'hui, la plupart des visites se font sur téléphone !
:::

::: analogie
Pense à l'**eau** : versée dans un verre, elle prend la forme du verre ; dans une bouteille, celle de la bouteille. Une page responsive fait pareil : elle prend la forme de l'écran.
:::

## Mobile first : on commence par le petit écran

Bootstrap est **« mobile first »** (*le mobile d'abord*) : une classe **sans** point de rupture s'applique à **tous** les écrans, à commencer par les plus petits. Une classe **avec** point de rupture (`md`, `lg`…) s'applique **à partir de** cette taille, et au-dessus.

```{.ascii}
 largeur de l'écran →
 0px         576px        768px        992px        1200px       1400px
 │── (aucun) ──│──── sm ────│──── md ────│──── lg ────│──── xl ────│── xxl ──→
   téléphone    grand tél.    tablette     portable     ordinateur   grand écran

 col-12 col-md-6 :  ████████████ (12/12)  jusqu'à 767px
                    ██████ ██████ (6/12)   à partir de 768px (md) et au-dessus
```

| Point de rupture | Abréviation | À partir de | Appareil typique |
|---|---|---|---|
| (aucun) | — | 0 px | petit téléphone |
| Small | `sm` | 576 px | grand téléphone |
| Medium | `md` | 768 px | tablette |
| Large | `lg` | 992 px | ordinateur portable |
| Extra large | `xl` | 1200 px | ordinateur de bureau |
| Extra extra large | `xxl` | 1400 px | très grand écran |

::: retenir
On lit `col-md-6` ainsi : « **à partir** de la taille md, prends 6 colonnes ». En dessous, c'est la règle précédente (ici celle sans point de rupture) qui s'applique.
:::

::: attention
N'oublie jamais la ligne `<meta name="viewport" content="width=device-width, initial-scale=1">` dans le `<head>` : sans elle, le téléphone affiche la page « en miniature » et le responsive ne fonctionne pas !
:::

## Exemple 1 : une grille qui s'adapte

La même page, vue sur trois écrans différents :

```{.html .demo largeur="1100|800|380"}
<!-- 1 carte par ligne sur téléphone, 2 sur tablette, 4 sur ordinateur -->
<div class="row g-3">
  <div class="col-12 col-md-6 col-lg-3"><div class="p-3 text-bg-primary rounded">🍎 Pomme</div></div>
  <div class="col-12 col-md-6 col-lg-3"><div class="p-3 text-bg-success rounded">🥝 Kiwi</div></div>
  <div class="col-12 col-md-6 col-lg-3"><div class="p-3 text-bg-warning rounded">🍌 Banane</div></div>
  <div class="col-12 col-md-6 col-lg-3"><div class="p-3 text-bg-danger rounded">🍓 Fraise</div></div>
</div>
```

## Exemple 2 : cacher ou montrer selon l'écran

| Classes | Téléphone | Tablette (md) | Ordinateur (lg) |
|---|---|---|---|
| `d-none d-md-block` | caché | visible | visible |
| `d-block d-md-none` | visible | caché | caché |
| `d-none d-lg-block` | caché | caché | visible |

```{.html .demo largeur="900|380"}
<!-- Visible seulement à partir de md -->
<div class="d-none d-md-block alert alert-primary">💻 Bienvenue sur la version grand écran !</div>
<!-- Visible seulement en dessous de md -->
<div class="d-block d-md-none alert alert-warning">📱 Bienvenue sur la version téléphone !</div>
```

## Exemple 3 : changer l'ordre et l'alignement

Beaucoup de classes utilitaires acceptent un point de rupture : `text-md-start`, `flex-md-row`, `order-md-2`, `p-lg-5`, `d-md-flex`, `justify-content-lg-center`… Mais pas toutes : `w-50` ou `fs-3`, par exemple, n'existent pas en version `-md-`. En cas de doute, vérifie dans la documentation officielle !

```{.html .demo largeur="900|380"}
<!-- flex-column : en colonne sur téléphone ; flex-md-row : en ligne à partir de md -->
<div class="d-flex flex-column flex-md-row align-items-center gap-3 p-3 border rounded">
  <!-- order-md-2 : sur grand écran, l'image passe en 2e position (à droite) -->
  <img src="images/fusee.jpg" class="rounded order-md-2" style="width: 220px;" alt="Une fusée qui décolle">
  <!-- text-center sur téléphone, text-md-start (à gauche) sur grand écran -->
  <div class="text-center text-md-start">
    <h2 class="fs-4">Atelier « Mini-fusées »</h2>
    <p class="mb-0">Construis ta fusée à eau et fais-la décoller dans la cour !</p>
  </div>
</div>
```

## Les images et les textes responsives

- `img-fluid` : l'image ne dépasse jamais de son parent et rétrécit avec l'écran.
- `ratio ratio-16x9` : un cadre aux bonnes proportions, idéal pour une vidéo.
- `display-1` à `display-6` : des titres géants ; la taille des titres Bootstrap diminue automatiquement sur petit écran.
- `table-responsive` (chapitre 8) et `navbar-expand-lg` (chapitre 10) sont aussi du responsive !

::: astuce
Pour tester : dans Chrome ou Firefox, appuie sur **F12** puis sur l'icône 📱 (« mode appareil ») et choisis un téléphone ou une tablette. Tu peux aussi simplement rétrécir la fenêtre de ton navigateur avec la souris.
:::

## Exercices du chapitre 16

:::: {.exercice .niveau1}
### Exercice 16.1 — La galerie adaptable

**🎯 Objectif :** utiliser les points de rupture de la grille.

**📝 Consignes :** crée une galerie de 6 images : 2 par ligne sur téléphone, 3 sur tablette, 6 sur grand ordinateur (xl).

**🔍 Indices :** 12 ÷ 2 = 6 ; 12 ÷ 3 = 4 ; 12 ÷ 6 = 2 → `col-6 col-md-4 col-xl-2`.

::: solution
```{.html .demo largeur="800" legende="À 800 px (tablette) : 3 images par ligne"}
<div class="row g-2">
  <!-- col-6 : 2 par ligne ; col-md-4 : 3 par ligne ; col-xl-2 : 6 par ligne -->
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-1.jpg" class="img-fluid rounded" alt="Photo 1"></div>
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-2.jpg" class="img-fluid rounded" alt="Photo 2"></div>
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-3.jpg" class="img-fluid rounded" alt="Photo 3"></div>
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-4.jpg" class="img-fluid rounded" alt="Photo 4"></div>
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-5.jpg" class="img-fluid rounded" alt="Photo 5"></div>
  <div class="col-6 col-md-4 col-xl-2"><img src="images/galerie-6.jpg" class="img-fluid rounded" alt="Photo 6"></div>
</div>
```

**Explication :** on part du plus petit écran (`col-6`) puis on ajoute les changements pour les écrans plus grands. `img-fluid` empêche les images de déborder de leur colonne.
:::
::::

:::: {.exercice .niveau2}
### Exercice 16.2 — Le bouton qui s'adapte

**🎯 Objectif :** utiliser des utilitaires avec point de rupture.

**📝 Consignes :** crée un bouton « Je m'inscris » qui prend **toute la largeur** sur téléphone, mais qui a une **largeur normale** et se place **à droite** à partir de md.

**🔍 Indices :** une `div` avec `d-grid` sur téléphone devient `d-md-flex justify-content-md-end`.

::: solution
```{.html .demo largeur="800|380"}
<!-- d-grid : le bouton s'étire ; d-md-flex : à partir de md, il reprend sa taille -->
<!-- justify-content-md-end : à partir de md, il se place à droite -->
<div class="d-grid d-md-flex justify-content-md-end">
  <button type="button" class="btn btn-primary btn-lg">Je m'inscris</button>
</div>
```

**Explication :** sur téléphone, un gros bouton pleine largeur est facile à toucher avec le pouce. Sur grand écran, il serait trop long : on le remet à sa taille et on l'aligne à droite.
:::
::::

:::: {.exercice .niveau3}
### Exercice 16.3 — La page profil responsive

**🎯 Objectif :** construire une mise en page complète qui change de structure.

**📝 Consignes :** sur grand écran (lg), une colonne de gauche (4/12) contient l'avatar et le nom, une colonne de droite (8/12) contient trois statistiques côte à côte. Sur téléphone, tout s'empile, et les statistiques passent l'une sous l'autre. L'avatar est rond et centré.

**🔍 Indices :** `col-lg-4` / `col-lg-8` ; pour les statistiques `row row-cols-1 row-cols-sm-3` ; `rounded-circle`, `text-center`.

::: solution
```{.html .demo largeur="1000|380"}
<div class="row g-3 align-items-center">
  <!-- Colonne profil : toute la largeur sur téléphone, 4/12 sur ordinateur -->
  <div class="col-12 col-lg-4 text-center">
    <img src="images/avatar-leo.png" class="rounded-circle border border-4 border-info mb-2" style="width: 110px;" alt="Avatar de Léo">
    <h2 class="fs-4 mb-0">Léo, 12 ans</h2>
    <p class="text-body-secondary">Apprenti développeur</p>
  </div>
  <!-- Colonne statistiques : 8/12 sur ordinateur -->
  <div class="col-12 col-lg-8">
    <!-- row-cols-1 : 1 par ligne ; row-cols-sm-3 : 3 par ligne à partir de sm -->
    <div class="row row-cols-1 row-cols-sm-3 g-2 text-center">
      <div class="col"><div class="p-3 rounded text-bg-primary"><div class="fs-3 fw-bold">18</div>chapitres lus</div></div>
      <div class="col"><div class="p-3 rounded text-bg-success"><div class="fs-3 fw-bold">54</div>exercices</div></div>
      <div class="col"><div class="p-3 rounded text-bg-warning"><div class="fs-3 fw-bold">1</div>site créé</div></div>
    </div>
  </div>
</div>
```

**Explication :** deux niveaux de responsive se combinent : la grande grille (`col-lg-4` / `col-lg-8`) et la petite grille des statistiques (`row-cols-sm-3`). On écrit le code **une seule fois** ; ce sont les classes qui décident de l'affichage selon la largeur de l'écran.
:::
::::

## Quiz du chapitre 16

::: quiz
1. Que veut dire « mobile first » ?
    - A) On conçoit d'abord pour le petit écran, puis on ajoute les règles pour les grands
    - B) Le mobile charge la page en premier
    - C) On commence par le design de l'ordinateur
    - D) Le site ne marche que sur mobile
2. À partir de quelle largeur s'applique `md` ?
    - A) 992 px
    - B) 768 px
    - C) 1200 px
    - D) 576 px
3. Que fait `d-none d-lg-block` ?
    - A) Visible partout
    - B) Visible sur petits écrans, caché à partir de lg
    - C) Caché partout
    - D) Caché sur petits écrans, visible à partir de lg
4. Avec `col-12 col-md-4`, combien de colonnes par ligne sur une tablette ?
    - A) 2
    - B) 4
    - C) 3
    - D) 1
5. Quelle balise est indispensable pour que le responsive fonctionne sur téléphone ?
    - A) `<meta name="viewport" …>`
    - B) `<meta charset="utf-8">`
    - C) `<title>`
    - D) `<link rel="icon">`
:::

::: reponses
1. **A** — On part du petit écran et on ajoute des classes avec point de rupture pour les écrans plus grands.
2. **B** — `md` commence à 768 px (taille tablette).
3. **D** — `d-none` cache d'abord, `d-lg-block` réaffiche à partir de 992 px.
4. **C** — 12 ÷ 4 = 3 éléments par ligne à partir de md.
5. **A** — La balise viewport demande au téléphone d'utiliser sa vraie largeur.
:::

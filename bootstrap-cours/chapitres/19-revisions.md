# Révisions — La fiche mémo Bootstrap {#revisions}

::: chapitre-intro
Voici l'essentiel du cours sur quelques pages. Garde-les près de ton ordinateur quand tu crées un site : c'est ton **anti-sèche officielle** !
:::

## Le modèle de page à toujours utiliser

```html
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Mon site</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet"
        integrity="sha384-XGjxtQfXaH2tnPFa9x+ruJTuLE3Aa6LhHSWRr1XeTyhezb4abCG4ccI5AkVDxqC+" crossorigin="anonymous">
</head>
<body>
  <!-- Ton contenu ici -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
```

## Les classes les plus importantes

| Thème | Classes essentielles | Chapitre |
|---|---|---|
| Containers | `container`, `container-fluid`, `container-md` | 3 |
| Grille | `row`, `col`, `col-6`, `col-md-4`, `row-cols-md-3`, `g-3`, `offset-md-2` | 4 |
| Couleurs | `text-primary`, `bg-success`, `text-bg-warning`, `bg-danger-subtle`, `border-info` | 5 |
| Boutons | `btn btn-primary`, `btn-outline-*`, `btn-lg`, `btn-sm`, `btn-group`, `d-grid` | 6 |
| Cartes | `card`, `card-body`, `card-title`, `card-text`, `card-img-top`, `card-header`, `h-100` | 7 |
| Tableaux | `table`, `table-striped`, `table-hover`, `table-bordered`, `table-responsive` | 8 |
| Formulaires | `form-label`, `form-control`, `form-select`, `form-check`, `form-check-input` | 9 |
| Navbar | `navbar navbar-expand-lg`, `navbar-brand`, `navbar-toggler`, `collapse navbar-collapse`, `navbar-nav`, `nav-link` | 10 |
| Alertes | `alert alert-success`, `alert-dismissible fade show`, `btn-close`, `alert-link` | 11 |
| Badges | `badge text-bg-*`, `rounded-pill` | 12 |
| Modales | `modal fade`, `modal-dialog`, `modal-content`, `modal-header/body/footer`, `modal-dialog-centered` | 13 |
| Carrousel | `carousel slide`, `carousel-inner`, `carousel-item active`, `carousel-control-prev/next`, `carousel-caption` | 14 |
| Espacement | `m-*`, `p-*`, `mt-3`, `px-4`, `mx-auto`, `gap-2` | 15 |
| Flexbox | `d-flex`, `justify-content-between`, `align-items-center`, `flex-column`, `flex-md-row` | 15 |
| Affichage | `d-none`, `d-block`, `d-md-none`, `d-none d-lg-block` | 15, 16 |
| Texte | `text-center`, `fw-bold`, `fs-3`, `display-4`, `lead`, `text-uppercase` | 5, 15 |
| Images | `img-fluid`, `rounded`, `rounded-circle`, `shadow` | 7, 16 |
| Icônes | `bi bi-nom`, `bi-…-fill` | 17 |

## Les attributs magiques du JavaScript Bootstrap

| Attribut | Rôle | Exemple |
|---|---|---|
| `data-bs-toggle` | quel composant ouvrir | `"collapse"`, `"modal"`, `"dropdown"` |
| `data-bs-target` | quel élément viser (avec `#id`) | `"#menu"`, `"#regles"` |
| `data-bs-dismiss` | fermer le composant parent | `"alert"`, `"modal"` |
| `data-bs-slide` / `data-bs-slide-to` | piloter un carrousel | `"next"`, `"prev"`, `"2"` |
| `data-bs-ride` | lancer le défilement automatique | `"carousel"` |
| `data-bs-theme` | thème clair ou sombre | `"dark"` |

## Les points de rupture

```{.ascii}
 (aucun) 0px │ sm 576px │ md 768px │ lg 992px │ xl 1200px │ xxl 1400px
 téléphone   │ grand tél.│ tablette │ portable │ ordinateur│ grand écran
 → une classe avec un point de rupture s'applique À PARTIR de cette taille
```

## Les 10 erreurs les plus fréquentes

| ❌ Erreur | ✅ Solution |
|---|---|
| Oublier la balise `<meta name="viewport">` : le site est minuscule sur téléphone | Toujours partir du modèle de page complet |
| Oublier le `<script>` Bootstrap : le menu ☰, les modales, le carrousel ne marchent pas | Ajouter `bootstrap.bundle.min.js` juste avant `</body>` |
| Mettre des `col` sans `row` autour (ou une `row` sans `container`) | Toujours : `container` → `row` → `col` |
| Dépasser 12 colonnes sur une ligne en pensant rester sur une ligne | Faire le calcul : 4 + 4 + 4 = 12 ✔️ |
| Écrire `btn-primary` sans `btn` (ou `bi-heart` sans `bi`) | Toujours la classe de base + la variante |
| Un `data-bs-target="menu"` sans `#`, ou un `id` mal orthographié | `data-bs-target="#menu"` et `id="menu"` identiques |
| Oublier la classe `active` sur la première diapo du carrousel | Une seule diapo `active` au départ |
| Une image sans `alt` | Décrire l'image : `alt="Un chat roux qui dort"` |
| Un bouton qui contient seulement une icône, sans `aria-label` | `<button aria-label="Supprimer">` |
| Faire une faute de frappe dans une classe (`text-centre`, `colmd-6`) : rien ne se passe, sans message d'erreur ! | Relire attentivement et utiliser l'autocomplétion de l'éditeur |

## Les bonnes pratiques

::: retenir
1. **Mobile first** : pense d'abord au téléphone, puis ajoute `md`, `lg`…
2. **Les classes avant le CSS** : utilise les utilitaires Bootstrap avant d'écrire ton propre style.
3. **Accessibilité** : `alt` sur les images, `label` pour chaque champ, `aria-label` sur les boutons-icônes, un seul `<h1>` et des titres dans l'ordre.
4. **Des balises qui ont du sens** : `<nav>`, `<header>`, `<main>`, `<section>`, `<footer>`.
5. **Commente ton code** : `<!-- Bannière -->` aide à s'y retrouver.
6. **Indente ton code** : décale les balises enfants pour voir qui est dans quoi.
7. **Teste sur plusieurs tailles d'écran** avec le mode appareil (F12).
8. **Avance petit à petit** : une section, puis on vérifie, puis la suivante.
9. **Valide ton HTML** sur validator.w3.org.
10. **La documentation officielle** (getbootstrap.com) est ta meilleure amie : tous les exemples y sont.
:::

## Petit glossaire

| Mot | Définition simple |
|---|---|
| Balise | Un mot entre chevrons `< >` qui indique un élément HTML |
| Classe | Une étiquette (`class="…"`) qui donne un style à un élément |
| CDN | Un serveur sur Internet qui fournit les fichiers de Bootstrap |
| Composant | Un morceau tout prêt : bouton, carte, navbar, modale… |
| Utilitaire | Une petite classe qui fait une seule chose (`mt-3`, `text-center`) |
| Responsive | Qui s'adapte à toutes les tailles d'écran |
| Point de rupture | Largeur d'écran à partir de laquelle la mise en page change |
| Accessibilité | Faire un site utilisable par tout le monde, y compris les personnes handicapées |
| `id` | Un nom unique donné à un élément, utilisé par `#` |

::: defi
**Le grand défi final** : sans regarder le cours, crée une page « Mon animal préféré » avec une navbar, une carte de présentation, un carrousel de 3 photos, une alerte « Le savais-tu ? » et un petit formulaire « Vote pour ton animal préféré ». Ensuite, vérifie-la sur un téléphone. Si tu y arrives, tu es officiellement un **jeune développeur Bootstrap** ! 🎓🏆
:::

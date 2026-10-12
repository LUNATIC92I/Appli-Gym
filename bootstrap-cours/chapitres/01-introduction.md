# Chapitre 1 — Découvrir Bootstrap {#ch1}

::: chapitre-intro
Dans ce chapitre, tu vas découvrir ce qu'est Bootstrap, pourquoi il a été inventé et comment il transforme une page toute simple en une page moderne, en ajoutant seulement quelques mots dans ton code.
:::

## Qu'est-ce que Bootstrap ?

**Bootstrap** est une **boîte à outils gratuite** qui contient des milliers de styles déjà prêts pour embellir tes pages web : des boutons colorés, des menus, des cartes, des formulaires, des grilles pour ranger ton contenu…

Il a été créé en 2011 par deux développeurs de l'entreprise Twitter, Mark Otto et Jacob Thornton. Aujourd'hui, c'est l'un des outils les plus utilisés au monde pour fabriquer des sites web. Dans ce cours, on utilise la version **5.3**.

::: analogie
Imagine que tu veux construire un château. Tu pourrais fabriquer toi-même chaque brique en argile… ça prendrait des mois ! Ou alors tu peux ouvrir une **boîte de Lego** : les briques sont déjà prêtes, de toutes les formes et de toutes les couleurs. Tu n'as plus qu'à les assembler.

**Bootstrap, c'est ta boîte de Lego pour le web.** Les « briques » s'appellent des **classes** : tu les colles sur tes éléments et hop, ils deviennent beaux.
:::

## Pourquoi Bootstrap existe-t-il ?

Avant Bootstrap, pour faire un joli bouton, il fallait écrire soi-même beaucoup de règles de style : la couleur, les coins arrondis, la taille du texte, l'effet quand on passe la souris dessus… Et il fallait tout recommencer pour chaque site !

Bootstrap règle trois problèmes :

1. **Gagner du temps** : les styles sont déjà écrits et testés.
2. **Avoir un résultat joli et cohérent** : tous les boutons, menus et textes vont bien ensemble.
3. **S'adapter à tous les écrans** : une page Bootstrap s'affiche correctement sur un téléphone comme sur un grand écran. Aujourd'hui, la majorité des visites de sites web se font depuis un téléphone !

## HTML, CSS et Bootstrap : qui fait quoi ?

Pour fabriquer une page web, on utilise trois ingrédients principaux. Bootstrap vient s'ajouter par-dessus.

```{.ascii}
   🧱 HTML            🎨 CSS               🎁 BOOTSTRAP
 ┌────────────┐     ┌────────────┐       ┌──────────────────────┐
 │ Le SQUELETTE│     │ Les HABITS │       │ Une GARDE-ROBE        │
 │ de la page │     │ de la page │       │ déjà remplie d'habits │
 │            │     │            │       │ (écrits en CSS)       │
 │ titres,    │     │ couleurs,  │       │ tu choisis juste      │
 │ textes,    │     │ tailles,   │       │ avec des CLASSES :    │
 │ images,    │     │ marges,    │       │ btn, card, navbar...  │
 │ boutons... │     │ positions..│       │                      │
 └────────────┘     └────────────┘       └──────────────────────┘
```

- **HTML** dit **ce qu'il y a** dans la page : « ici un titre, là un paragraphe, là une image ».
- **CSS** dit **à quoi ça ressemble** : « ce titre est violet, ce paragraphe a une grande marge ».
- **Bootstrap** est un énorme fichier **CSS déjà écrit** (plus un peu de JavaScript pour les éléments qui bougent, comme les menus qui s'ouvrent). Tu n'écris presque pas de CSS : tu utilises ses **classes**.

::: analogie
Pense à un bonhomme : **HTML**, c'est son squelette (la tête, les bras, les jambes). **CSS**, ce sont ses vêtements, sa coupe de cheveux, ses couleurs. **Bootstrap**, c'est un magasin de vêtements où tout est déjà cousu : tu dis juste « je veux le t-shirt bleu taille M » et c'est fait !
:::

### Une classe, comment ça marche ?

Une classe est une **étiquette** qu'on ajoute à une balise HTML grâce à l'attribut `class`. Bootstrap a préparé le style correspondant à chaque étiquette.

```{.html}
<!-- Un bouton tout simple, sans classe : il est gris et petit -->
<button>Clique-moi</button>

<!-- Le même bouton avec deux étiquettes Bootstrap : "btn" et "btn-primary" -->
<button class="btn btn-primary">Clique-moi</button>
```

- `btn` veut dire « je suis un **bouton** Bootstrap » (coins arrondis, espace autour du texte…).
- `btn-primary` veut dire « je suis le bouton **principal** » (couleur bleue).
- On peut mettre **plusieurs classes** dans le même `class="..."`, il suffit de les **séparer par un espace**.

## Avant / après Bootstrap

Voici exactement le même contenu HTML : un titre, un paragraphe, un bouton et une liste. Regarde d'abord le résultat **sans** Bootstrap (le navigateur affiche ses styles par défaut, un peu tristes).

```{.html .demo .page largeur="700" legende="Sans Bootstrap"}
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <title>Sans Bootstrap</title>
</head>
<body>
  <h1>Le club des jeunes codeurs</h1>
  <p>Rejoins-nous tous les mercredis pour créer des jeux et des sites web !</p>
  <button type="button">Je m'inscris</button>
  <ul>
    <li>Atelier jeux vidéo</li>
    <li>Atelier sites web</li>
    <li>Atelier robots</li>
  </ul>
</body>
</html>
```

Et maintenant **avec** Bootstrap. On a seulement ajouté la ligne qui charge Bootstrap et quelques classes. Compare les deux images !

```{.html .demo .page largeur="700" legende="Avec Bootstrap"}
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Avec Bootstrap</title>
  <!-- On charge la boîte de Lego : le fichier CSS de Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
  <!-- "container" range le contenu au centre, "py-4" ajoute de l'espace en haut et en bas -->
  <div class="container py-4">
    <!-- "text-primary" colorie le titre en bleu -->
    <h1 class="text-primary">Le club des jeunes codeurs</h1>
    <!-- "lead" rend le paragraphe plus grand : c'est une introduction -->
    <p class="lead">Rejoins-nous tous les mercredis pour créer des jeux et des sites web !</p>
    <!-- "btn btn-success btn-lg" : un grand bouton vert -->
    <button type="button" class="btn btn-success btn-lg">Je m'inscris</button>
    <!-- "list-group" transforme la liste en jolies cases ; "mt-4" = marge en haut -->
    <ul class="list-group mt-4">
      <li class="list-group-item">Atelier jeux vidéo</li>
      <li class="list-group-item">Atelier sites web</li>
      <li class="list-group-item">Atelier robots</li>
    </ul>
  </div>
</body>
</html>
```

Incroyable, non ? Avec **seulement quelques classes**, la page a l'air professionnelle. Et nous n'avons pas écrit une seule ligne de CSS !

::: retenir
- **HTML** = le contenu (le squelette).
- **CSS** = l'apparence (les habits).
- **Bootstrap** = des milliers de styles CSS déjà prêts, que l'on utilise avec des **classes** : `class="btn btn-primary"`.
- Plusieurs classes se séparent par un **espace**.
:::

## Premiers pas : lire une classe Bootstrap

Les classes Bootstrap ont des noms logiques. Une fois que tu as compris le principe, tu peux souvent **deviner** une classe !

| Classe | Se lit… | Effet |
|---|---|---|
| `text-primary` | texte + principal | texte bleu |
| `text-danger` | texte + danger | texte rouge |
| `bg-warning` | *background* (fond) + attention | fond jaune |
| `btn-success` | bouton + succès | bouton vert |
| `mt-3` | *margin top* (marge en haut) niveau 3 | espace au-dessus |
| `text-center` | texte + centre | texte centré |

::: astuce
Beaucoup de mots viennent de l'anglais : *primary* = principal, *success* = succès, *danger* = danger, *warning* = attention, *background* = arrière-plan, *center* = centre. Apprendre ces quelques mots t'aidera énormément !
:::

::: attention
Les classes doivent être écrites **exactement** comme Bootstrap les a prévues : `btn-primary` fonctionne, mais `btn-primery`, `Btn-Primary` ou `btn primary` ne fonctionnent pas. Une seule faute de frappe et le style disparaît !
:::

## Exercices du chapitre 1

:::: {.exercice .niveau1}
### Exercice 1.1 — Reconnaître les ingrédients

**🎯 Objectif :** savoir distinguer HTML, CSS et Bootstrap.

**📝 Consignes :** pour chaque phrase, indique s'il s'agit de **HTML**, de **CSS** ou de **Bootstrap**.

1. « Je dis qu'il y a un titre et une image dans la page. »
2. « J'écris que le titre doit être violet et en taille 40. »
3. « J'ajoute `class="btn btn-danger"` à mon bouton pour qu'il devienne rouge. »
4. « J'écris la balise `<p>` pour faire un paragraphe. »

**🔍 Indices :** HTML = contenu, CSS = style écrit par toi, Bootstrap = style déjà prêt utilisé avec des classes.

::: solution
1. **HTML** : on décrit le contenu (titre, image).
2. **CSS** : on écrit soi-même une règle d'apparence.
3. **Bootstrap** : on utilise des classes déjà prêtes (`btn`, `btn-danger`).
4. **HTML** : la balise `<p>` décrit un paragraphe.

**Explication :** retiens la question magique : « Est-ce que je dis **ce qu'il y a** (HTML), **à quoi ça ressemble en l'écrivant moi-même** (CSS) ou est-ce que **j'utilise une classe toute prête** (Bootstrap) ? »
:::
::::

:::: {.exercice .niveau2}
### Exercice 1.2 — Deviner les classes

**🎯 Objectif :** apprendre à lire et deviner les noms de classes Bootstrap.

**📝 Consignes :** à partir du tableau du chapitre, devine la classe qui permet :

1. d'écrire un texte en vert (*success*) ;
2. de mettre un fond rouge (*danger*) ;
3. de centrer un texte ;
4. de créer un bouton jaune (*warning*).

**🔍 Indices :** combine les petits mots `text-`, `bg-`, `btn-` avec les noms de couleur `success`, `danger`, `warning`.

::: solution
Voici les classes, utilisées dans une petite page de test :

```{.html .demo largeur="600"}
<!-- 1. text-success : texte vert -->
<p class="text-success">Bravo, tu as réussi !</p>
<!-- 2. bg-danger : fond rouge ; text-white : texte blanc pour bien lire -->
<p class="bg-danger text-white">Attention, zone dangereuse !</p>
<!-- 3. text-center : texte centré -->
<p class="text-center">Je suis au milieu.</p>
<!-- 4. btn + btn-warning : bouton jaune (type="button" = bouton simple) -->
<button type="button" class="btn btn-warning">Bouton jaune</button>
```

**Explication :** Bootstrap suit toujours la même logique : **quoi** (`text`, `bg`, `btn`) + **tiret** + **comment** (`success`, `danger`, `warning`, `center`). Sur la deuxième ligne, on a ajouté `text-white`, sinon le texte noir serait difficile à lire sur le rouge.
:::
::::

:::: {.exercice .niveau3}
### Exercice 1.3 — Ma première transformation

**🎯 Objectif :** transformer une page « triste » en page Bootstrap.

**📝 Consignes :** reprends l'exemple « Sans Bootstrap » de la page précédente, mais pour **ton propre club** (football, dessin, échecs…). Ajoute Bootstrap et :

1. un titre de couleur ;
2. un paragraphe d'introduction en grand (`lead`) ;
3. un grand bouton coloré ;
4. une liste en cases (`list-group`).

**🔍 Indices :** n'oublie pas la ligne `<link ...>` qui charge Bootstrap dans la partie `<head>`, et entoure ton contenu d'une `<div class="container">`.

::: solution
```{.html .demo .page largeur="700"}
<!doctype html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Club d'échecs</title>
  <!-- Chargement de Bootstrap : sans cette ligne, aucune classe ne fonctionne -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
  <!-- Une boîte centrée avec de l'espace en haut et en bas -->
  <div class="container py-4">
    <!-- Titre en vert grâce à text-success -->
    <h1 class="text-success">♟️ Le club d'échecs du collège</h1>
    <!-- Paragraphe d'introduction mis en avant -->
    <p class="lead">Viens apprendre les ouvertures, les pièges et les finales le vendredi midi.</p>
    <!-- Grand bouton bleu -->
    <button type="button" class="btn btn-primary btn-lg">Je veux jouer !</button>
    <!-- Liste en cases, avec une marge en haut -->
    <ul class="list-group mt-4">
      <li class="list-group-item">Tournoi chaque mois</li>
      <li class="list-group-item">Cours pour débutants</li>
      <li class="list-group-item">Parties contre les professeurs</li>
    </ul>
  </div>
</body>
</html>
```

**Explication ligne par ligne :**

- `<!doctype html>` annonce au navigateur que c'est une page web moderne (HTML5).
- `<html lang="fr">` ouvre la page et précise qu'elle est en français (utile pour les lecteurs d'écran des personnes malvoyantes).
- Dans `<head>`, la balise `<meta charset="utf-8">` permet d'afficher les accents ; la balise `viewport` adapte la page aux téléphones ; `<title>` donne le nom de l'onglet ; `<link>` charge Bootstrap.
- `<div class="container py-4">` est une boîte qui centre le contenu (chapitre 3) ; `py-4` ajoute de l'espace en haut et en bas (chapitre 15).
- Le reste reprend les classes vues dans ce chapitre : `text-success`, `lead`, `btn btn-primary btn-lg`, `list-group`, `list-group-item`, `mt-4`.
:::
::::

## Quiz du chapitre 1

::: quiz
1. Qu'est-ce que Bootstrap ?
    - A) Une boîte à outils de styles prêts à l'emploi pour le web
    - B) Un langage de programmation pour les jeux vidéo
    - C) Un navigateur web
    - D) Un logiciel de dessin
2. Quel langage décrit le **contenu** d'une page (titres, textes, images) ?
    - A) CSS
    - B) Python
    - C) Bootstrap
    - D) HTML
3. Comment ajoute-t-on deux classes à un bouton ?
    - A) `class="btn btn-primary"`
    - B) `class="btn,btn-primary"`
    - C) `class="btn" class="btn-primary"`
    - D) `class="btn+btn-primary"`
4. Que fait la classe `text-center` ?
    - A) Elle colorie le texte en rouge
    - B) Elle centre le texte
    - C) Elle agrandit le texte
    - D) Elle met le texte en gras
5. Pourquoi Bootstrap est-il utile ?
    - A) Il remplace complètement le HTML
    - B) Il ne fonctionne que sur ordinateur
    - C) Il fait gagner du temps et rend les pages jolies et adaptées à tous les écrans
    - D) Il sert uniquement à faire des animations
:::

::: reponses
1. **A** — Bootstrap est une boîte à outils (on dit aussi un *framework*) de styles et de composants déjà prêts. Ce n'est ni un navigateur, ni un langage.
2. **D** — HTML décrit le contenu. CSS décrit l'apparence ; Bootstrap fournit du CSS tout prêt.
3. **A** — On met toutes les classes dans un seul attribut `class`, séparées par des **espaces**.
4. **B** — `text-center` signifie « texte au centre ».
5. **C** — Bootstrap fait gagner du temps, donne un résultat cohérent et s'adapte aux téléphones comme aux ordinateurs. Il ne remplace pas le HTML : il s'utilise **avec** lui.
:::

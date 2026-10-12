# Chapitre 9 — Les formulaires {#ch9}

::: chapitre-intro
Un formulaire permet au visiteur de **donner des informations** : s'inscrire à un club, envoyer un message, répondre à un sondage… Bootstrap rend les champs de saisie beaux, alignés et faciles à utiliser.
:::

::: analogie
Un formulaire web, c'est comme la **fiche d'inscription papier** de ton club de sport : des cases pour ton nom, ton âge, des cases à cocher pour les activités… La différence ? Ici, la fiche est envoyée par Internet !
:::

## Le principe : une étiquette + un champ

Chaque champ a besoin d'une **étiquette** (`<label>`) qui dit ce qu'il faut écrire, reliée au champ (`<input>`) par un identifiant :

```{.ascii}
   <label for="prenom">Prénom</label>      ← l'étiquette
                 │
                 └──── for="prenom" pointe vers ──┐
                                                  ▼
   <input type="text" id="prenom" ...>     ← le champ (id="prenom")
```

Grâce à ce lien, cliquer sur l'étiquette place le curseur dans le champ, et les lecteurs d'écran savent lire le nom du champ.

Dans Bootstrap :

- `form-label` met en forme l'étiquette ;
- `form-control` met en forme le champ (pleine largeur, coins arrondis, effet bleu quand on clique) ;
- `mb-3` sur une `div` qui entoure le duo étiquette + champ ajoute de l'espace entre les lignes.

## Texte, e-mail et mot de passe

```{.html .demo largeur="600"}
<!-- La balise form entoure tout le formulaire -->
<form>
  <!-- Un duo étiquette + champ, avec un espace dessous (mb-3) -->
  <div class="mb-3">
    <label for="prenom" class="form-label">Prénom</label>
    <!-- type="text" : du texte libre ; placeholder : un exemple en gris -->
    <input type="text" class="form-control" id="prenom" placeholder="Ex. : Lina">
  </div>
  <div class="mb-3">
    <label for="email" class="form-label">Adresse e-mail</label>
    <!-- type="email" : le navigateur vérifie qu'il y a bien un @ -->
    <!-- aria-describedby relie le champ au petit texte d'aide en dessous -->
    <input type="email" class="form-control" id="email" placeholder="nom@exemple.fr" aria-describedby="aideEmail">
    <!-- form-text : petit texte d'aide gris -->
    <div id="aideEmail" class="form-text">Demande à un parent si tu n'as pas d'adresse e-mail.</div>
  </div>
  <div class="mb-3">
    <label for="motdepasse" class="form-label">Mot de passe</label>
    <!-- type="password" : les caractères sont cachés par des points -->
    <input type="password" class="form-control" id="motdepasse">
  </div>
  <!-- type="submit" : ce bouton envoie le formulaire -->
  <button type="submit" class="btn btn-primary">S'inscrire</button>
</form>
```

::: attention
Un `<label>` doit avoir un `for` **identique** à l'`id` de son champ. Et chaque `id` doit être **unique** dans la page : deux champs ne peuvent pas avoir le même `id`.
:::

Les principaux types de champs `<input>` :

| `type=` | Pour… | Particularité |
|---|---|---|
| `text` | du texte | clavier normal |
| `email` | une adresse e-mail | vérifie le `@`, clavier avec `@` sur téléphone |
| `password` | un mot de passe | caractères cachés |
| `number` | un nombre | petites flèches pour augmenter/diminuer |
| `date` | une date | un calendrier s'ouvre |
| `tel` | un numéro de téléphone | clavier numérique sur téléphone |

Pour un **long texte** (un message), on utilise `<textarea>` avec la même classe `form-control` et l'attribut `rows` (nombre de lignes visibles).

## Les cases à cocher (checkbox)

Une **case à cocher** permet de choisir **plusieurs** options. Dans Bootstrap, chaque case est dans une `div class="form-check"`, avec `form-check-input` sur la case et `form-check-label` sur l'étiquette.

```{.html .demo largeur="600"}
<p class="mb-1">Quelles activités t'intéressent ?</p>
<!-- Une case à cocher = une div form-check -->
<div class="form-check">
  <!-- value : la valeur envoyée si la case est cochée -->
  <input class="form-check-input" type="checkbox" value="dessin" id="actDessin">
  <label class="form-check-label" for="actDessin">Dessin</label>
</div>
<div class="form-check">
  <!-- checked : cette case est déjà cochée au départ -->
  <input class="form-check-input" type="checkbox" value="code" id="actCode" checked>
  <label class="form-check-label" for="actCode">Programmation</label>
</div>
<div class="form-check">
  <input class="form-check-input" type="checkbox" value="theatre" id="actTheatre">
  <label class="form-check-label" for="actTheatre">Théâtre</label>
</div>
<!-- Un interrupteur : une case à cocher avec form-switch -->
<div class="form-check form-switch mt-2">
  <input class="form-check-input" type="checkbox" role="switch" id="newsletter">
  <label class="form-check-label" for="newsletter">Recevoir la lettre du club</label>
</div>
```

## Les boutons radio

Un **bouton radio** permet de choisir **une seule** option parmi plusieurs. Tous les boutons d'un même choix doivent avoir le **même `name`** : c'est ce qui les relie (cocher l'un décoche les autres).

```{.html .demo largeur="600"}
<p class="mb-1">Ton niveau en natation :</p>
<!-- Les trois radios ont name="niveau" : on ne peut en choisir qu'un -->
<div class="form-check">
  <input class="form-check-input" type="radio" name="niveau" id="debutant" value="debutant">
  <label class="form-check-label" for="debutant">Débutant</label>
</div>
<div class="form-check">
  <input class="form-check-input" type="radio" name="niveau" id="moyen" value="moyen" checked>
  <label class="form-check-label" for="moyen">Moyen</label>
</div>
<div class="form-check">
  <input class="form-check-input" type="radio" name="niveau" id="expert" value="expert">
  <label class="form-check-label" for="expert">Expert</label>
</div>
```

::: analogie
Les boutons radio portent ce nom à cause des **vieux postes de radio** : quand on appuyait sur le bouton d'une station, le bouton de l'ancienne station ressortait tout seul. Une seule station à la fois !
:::

## Les listes déroulantes (select)

Une **liste déroulante** permet de choisir une option dans un menu. La balise `<select>` reçoit la classe `form-select`, et chaque choix est une balise `<option>`.

```{.html .demo largeur="600"}
<div class="mb-3">
  <label for="classe" class="form-label">Ta classe</label>
  <!-- form-select : une liste déroulante stylée par Bootstrap -->
  <select class="form-select" id="classe">
    <!-- selected disabled : texte affiché au départ, qu'on ne peut pas choisir -->
    <option selected disabled value="">Choisis ta classe…</option>
    <option value="cm2">CM2</option>
    <option value="6e">6e</option>
    <option value="5e">5e</option>
    <option value="4e">4e</option>
  </select>
</div>
```

## Les tailles et la mise en page

- `form-control-lg` / `form-control-sm` (et `form-select-lg` / `form-select-sm`) changent la taille des champs.
- On peut ranger les champs **côte à côte** avec la **grille** du chapitre 4.

```{.html .demo largeur="800"}
<form>
  <!-- g-3 : espace entre les champs ; les colonnes s'empilent sur téléphone -->
  <div class="row g-3">
    <div class="col-md-6">
      <label for="nom" class="form-label">Nom</label>
      <input type="text" class="form-control" id="nom">
    </div>
    <div class="col-md-6">
      <label for="age" class="form-label">Âge</label>
      <!-- type="number" avec un minimum et un maximum -->
      <input type="number" class="form-control" id="age" min="8" max="16">
    </div>
    <div class="col-12">
      <label for="message" class="form-label">Pourquoi veux-tu t'inscrire ?</label>
      <!-- textarea : un champ de plusieurs lignes -->
      <textarea class="form-control" id="message" rows="3"></textarea>
    </div>
    <div class="col-12">
      <button type="submit" class="btn btn-primary">Envoyer</button>
    </div>
  </div>
</form>
```

## Champs obligatoires et validation

L'attribut `required` rend un champ **obligatoire** : le navigateur refuse d'envoyer le formulaire s'il est vide. Bootstrap peut aussi afficher un champ en vert (`is-valid`) ou en rouge (`is-invalid`) avec un message (`valid-feedback` / `invalid-feedback`).

```{.html .demo largeur="600"}
<div class="mb-3">
  <label for="pseudo" class="form-label">Pseudo</label>
  <!-- is-invalid : bordure rouge et icône d'erreur -->
  <input type="text" class="form-control is-invalid" id="pseudo" value="a" required aria-describedby="pseudoErreur">
  <!-- Ce message rouge s'affiche car le champ a la classe is-invalid -->
  <div id="pseudoErreur" class="invalid-feedback">Ton pseudo doit contenir au moins 3 lettres.</div>
</div>
<div class="mb-3">
  <label for="ville" class="form-label">Ville</label>
  <!-- is-valid : bordure verte -->
  <input type="text" class="form-control is-valid" id="ville" value="Lyon" required>
  <div class="valid-feedback">Parfait !</div>
</div>
```

::: attention
**Ne demande jamais de mot de passe ou d'informations personnelles** (adresse, téléphone) sans l'accord d'un adulte. Dans tes projets d'entraînement, les formulaires ne sont pas reliés à un vrai serveur : ils servent à apprendre la mise en page.
:::

::: retenir
- Duo de base : `<label class="form-label" for="x">` + `<input class="form-control" id="x">`.
- Types : `text`, `email`, `password`, `number`, `date`… ; long texte : `<textarea>`.
- Cases à cocher (plusieurs choix) et radios (un seul choix, même `name`) : `form-check`, `form-check-input`, `form-check-label`.
- Liste déroulante : `<select class="form-select">` + `<option>`.
- `required` = obligatoire ; `is-valid` / `is-invalid` = affichage vert / rouge.
- Bouton d'envoi : `<button type="submit">`.
:::

## Exercices du chapitre 9

:::: {.exercice .niveau1}
### Exercice 9.1 — La connexion

**🎯 Objectif :** créer un formulaire avec e-mail et mot de passe.

**📝 Consignes :** crée un formulaire de connexion avec un champ e-mail, un champ mot de passe et un bouton bleu « Se connecter ». Les deux champs sont obligatoires.

**🔍 Indices :** `type="email"`, `type="password"`, l'attribut `required`, et `for`/`id` identiques.

::: solution
```{.html .demo largeur="500"}
<form>
  <div class="mb-3">
    <label for="connexionEmail" class="form-label">E-mail</label>
    <!-- required : obligatoire -->
    <input type="email" class="form-control" id="connexionEmail" required>
  </div>
  <div class="mb-3">
    <label for="connexionMdp" class="form-label">Mot de passe</label>
    <input type="password" class="form-control" id="connexionMdp" required>
  </div>
  <!-- Bouton d'envoi du formulaire -->
  <button type="submit" class="btn btn-primary">Se connecter</button>
</form>
```

**Explication :** chaque `label` est relié à son champ (`for` = `id`). `type="password"` cache les caractères. Si on clique sur « Se connecter » avec un champ vide, le navigateur affiche un message grâce à `required`.
:::
::::

:::: {.exercice .niveau2}
### Exercice 9.2 — Le sondage de la cantine

**🎯 Objectif :** utiliser radios, cases à cocher et select.

**📝 Consignes :** crée un sondage avec : une liste déroulante « Jour » (lundi à vendredi), des radios « As-tu aimé le repas ? » (oui / bof / non), des cases à cocher « Ce que tu as mangé » (entrée, plat, dessert) et un bouton vert « Envoyer mon avis ».

**🔍 Indices :** les trois radios partagent `name="avis"`. Pour mettre les radios sur une seule ligne, ajoute `form-check-inline` à chaque `div.form-check`.

::: solution
```{.html .demo largeur="600"}
<form>
  <!-- Liste déroulante -->
  <div class="mb-3">
    <label for="jour" class="form-label">Jour</label>
    <select class="form-select" id="jour">
      <option value="lundi">Lundi</option>
      <option value="mardi">Mardi</option>
      <option value="jeudi">Jeudi</option>
      <option value="vendredi">Vendredi</option>
    </select>
  </div>
  <!-- Radios sur une ligne (form-check-inline) ; même name="avis" -->
  <p class="mb-1">As-tu aimé le repas ?</p>
  <div class="mb-3">
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="avis" id="avisOui" value="oui">
      <label class="form-check-label" for="avisOui">😋 Oui</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="avis" id="avisBof" value="bof">
      <label class="form-check-label" for="avisBof">😐 Bof</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="radio" name="avis" id="avisNon" value="non">
      <label class="form-check-label" for="avisNon">🤢 Non</label>
    </div>
  </div>
  <!-- Cases à cocher : plusieurs choix possibles -->
  <p class="mb-1">Ce que tu as mangé :</p>
  <div class="mb-3">
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" id="entree" value="entree">
      <label class="form-check-label" for="entree">Entrée</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" id="plat" value="plat">
      <label class="form-check-label" for="plat">Plat</label>
    </div>
    <div class="form-check form-check-inline">
      <input class="form-check-input" type="checkbox" id="dessert" value="dessert">
      <label class="form-check-label" for="dessert">Dessert</label>
    </div>
  </div>
  <button type="submit" class="btn btn-success">Envoyer mon avis</button>
</form>
```

**Explication :** les radios partagent `name="avis"` : on ne peut en cocher qu'un. Les cases à cocher ont chacune leur `id` : on peut en cocher plusieurs. `form-check-inline` aligne les choix sur une ligne.
:::
::::

:::: {.exercice .niveau3}
### Exercice 9.3 — L'inscription au club de théâtre

**🎯 Objectif :** construire un formulaire complet sur deux colonnes, avec validation visuelle.

**📝 Consignes :** crée un formulaire avec prénom et nom côte à côte (sur ordinateur), e-mail, âge (8 à 15 ans), un rôle préféré (select), un message (textarea), une case « J'accepte le règlement » obligatoire et un grand bouton pleine largeur. Affiche le champ e-mail en erreur avec le message « Il manque le @ ».

**🔍 Indices :** grille `row g-3` avec `col-md-6` et `col-12` ; `is-invalid` + `invalid-feedback` ; `d-grid` pour le bouton.

::: solution
```{.html .demo largeur="800"}
<form>
  <div class="row g-3">
    <!-- Prénom et nom : côte à côte à partir des tablettes -->
    <div class="col-md-6">
      <label for="thPrenom" class="form-label">Prénom</label>
      <input type="text" class="form-control" id="thPrenom" required>
    </div>
    <div class="col-md-6">
      <label for="thNom" class="form-label">Nom</label>
      <input type="text" class="form-control" id="thNom" required>
    </div>
    <!-- E-mail affiché en erreur -->
    <div class="col-md-8">
      <label for="thEmail" class="form-label">E-mail d'un parent</label>
      <input type="email" class="form-control is-invalid" id="thEmail" value="maman.exemple.fr" required aria-describedby="thEmailErreur">
      <div id="thEmailErreur" class="invalid-feedback">Il manque le @.</div>
    </div>
    <!-- Âge entre 8 et 15 ans -->
    <div class="col-md-4">
      <label for="thAge" class="form-label">Âge</label>
      <input type="number" class="form-control" id="thAge" min="8" max="15">
    </div>
    <!-- Liste déroulante -->
    <div class="col-12">
      <label for="thRole" class="form-label">Rôle préféré</label>
      <select class="form-select" id="thRole">
        <option value="heros">Le héros ou l'héroïne</option>
        <option value="mechant">Le méchant</option>
        <option value="comique">Le personnage comique</option>
        <option value="decor">Les décors et la technique</option>
      </select>
    </div>
    <!-- Message sur plusieurs lignes -->
    <div class="col-12">
      <label for="thMessage" class="form-label">Pourquoi veux-tu faire du théâtre ?</label>
      <textarea class="form-control" id="thMessage" rows="3"></textarea>
    </div>
    <!-- Case obligatoire -->
    <div class="col-12">
      <div class="form-check">
        <input class="form-check-input" type="checkbox" id="thReglement" required>
        <label class="form-check-label" for="thReglement">J'accepte le règlement du club</label>
      </div>
    </div>
    <!-- Bouton pleine largeur -->
    <div class="col-12 d-grid">
      <button type="submit" class="btn btn-primary btn-lg">Je m'inscris 🎭</button>
    </div>
  </div>
</form>
```

**Explication :** la grille (`row g-3`, `col-md-6`, `col-12`) organise les champs : côte à côte sur un grand écran, empilés sur un téléphone. Le champ e-mail montre comment signaler une erreur (`is-invalid` + `invalid-feedback` relié par `aria-describedby`). La case du règlement est `required` : le formulaire ne part pas tant qu'elle n'est pas cochée.
:::
::::

## Quiz du chapitre 9

::: quiz
1. Quelle classe donne le style Bootstrap à un champ de texte ?
    - A) `input-style`
    - B) `form-check`
    - C) `form-label`
    - D) `form-control`
2. Comment relier un `<label>` à son champ ?
    - A) Ce n'est pas possible
    - B) Avec la classe `link`
    - C) Avec `for` sur le label égal à l'`id` du champ
    - D) En les mettant sur la même ligne
3. Quelle est la différence entre radio et case à cocher ?
    - A) Les radios ne fonctionnent que sur téléphone
    - B) Aucune
    - C) Radio = un seul choix ; case à cocher = plusieurs choix possibles
    - D) Radio = plusieurs choix ; case à cocher = un seul choix
4. Quel type d'`input` cache les caractères tapés ?
    - A) `password`
    - B) `email`
    - C) `secret`
    - D) `hidden`
5. Quelle classe met en forme une liste déroulante `<select>` ?
    - A) `dropdown`
    - B) `form-select`
    - C) `select-box`
    - D) `form-control-list`
:::

::: reponses
1. **D** — `form-control` ; `form-label` est pour l'étiquette, `form-check` pour les cases à cocher et radios.
2. **C** — L'attribut `for` du label doit être identique à l'`id` du champ.
3. **C** — Les radios d'un même groupe (même `name`) ne permettent qu'un choix.
4. **A** — `type="password"` masque le texte (attention, `hidden` cache le champ entier !).
5. **B** — `<select class="form-select">`.
:::

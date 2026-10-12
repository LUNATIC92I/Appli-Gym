# Chapitre 13 — Les fenêtres modales {#ch13}

::: chapitre-intro
Une **modale** est une fenêtre qui s'ouvre **par-dessus** la page, le reste devenant grisé. On s'en sert pour demander une confirmation (« Veux-tu vraiment supprimer ? »), afficher un détail ou un petit formulaire, sans changer de page.
:::

::: analogie
La modale, c'est comme quand le professeur **arrête la classe** pour lire une annonce importante : tout le monde s'arrête, écoute, puis on reprend. Tant que la modale est ouverte, on ne peut rien faire d'autre sur la page.
:::

## Les trois morceaux d'une modale

```{.ascii}
 ┌────────────── div.modal (le fond grisé + la fenêtre) ─────────┐
 │   ┌──────────── div.modal-dialog (la position) ───────────┐   │
 │   │ ┌────────── div.modal-content (la boîte blanche) ───┐ │   │
 │   │ │  modal-header : titre ..................... [X]   │ │   │
 │   │ │  modal-body   : le contenu (texte, image…)        │ │   │
 │   │ │  modal-footer : les boutons (Fermer, Valider)     │ │   │
 │   │ └───────────────────────────────────────────────────┘ │   │
 │   └───────────────────────────────────────────────────────┘   │
 └───────────────────────────────────────────────────────────────┘
```

Pour l'**ouvrir**, un bouton porte deux attributs : `data-bs-toggle="modal"` (« ouvre une modale ») et `data-bs-target="#id"` (« laquelle »). Pour la **fermer**, un bouton porte `data-bs-dismiss="modal"`. Le JavaScript de Bootstrap (le fichier *bundle*) fait tout le travail : pas besoin d'écrire de JavaScript !

## Créer et ouvrir une modale

```{.html .demo largeur="700" clic="#btnRegles" legende="Après un clic sur le bouton « Lire les règles »"}
<!-- 1. Le bouton qui ouvre la modale dont l'id est "regles" -->
<button type="button" class="btn btn-primary" id="btnRegles"
        data-bs-toggle="modal" data-bs-target="#regles">
  Lire les règles du jeu
</button>

<!-- 2. La modale : cachée tant qu'on n'a pas cliqué -->
<!-- fade : apparition en fondu ; tabindex="-1" : gestion du clavier ;
     aria-labelledby : le titre qui décrit la modale -->
<div class="modal fade" id="regles" tabindex="-1" aria-labelledby="titreRegles">
  <div class="modal-dialog">
    <div class="modal-content">
      <!-- En-tête : le titre et la croix de fermeture -->
      <div class="modal-header">
        <h2 class="modal-title fs-5" id="titreRegles">📜 Règles du jeu</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
      </div>
      <!-- Corps : le contenu -->
      <div class="modal-body">
        <p>1. Lance le dé.</p>
        <p>2. Avance ton pion.</p>
        <p class="mb-0">3. Le premier arrivé gagne !</p>
      </div>
      <!-- Pied : les boutons d'action -->
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
        <button type="button" class="btn btn-success" data-bs-dismiss="modal">J'ai compris</button>
      </div>
    </div>
  </div>
</div>
```

**Ligne par ligne :**

- `data-bs-toggle="modal" data-bs-target="#regles"` : le bouton ouvre la modale dont l'`id` est `regles` (attention au `#` !).
- `class="modal fade"` : c'est une modale, qui apparaît en fondu.
- `tabindex="-1"` : réglage pour le clavier (Bootstrap gère ensuite tout seul les attributs d'accessibilité quand la modale s'ouvre ou se ferme).
- `aria-labelledby="titreRegles"` : le titre de la modale sert à la décrire.
- `data-bs-dismiss="modal"` : n'importe quel bouton avec cet attribut ferme la modale.

::: retenir
On peut aussi fermer une modale en cliquant **sur le fond grisé** ou en appuyant sur la touche **Échap**.
:::

## Fermer : bloquer le fond

Pour une modale importante (par exemple un formulaire à moitié rempli), on peut empêcher la fermeture par un clic sur le fond avec `data-bs-backdrop="static"` et `data-bs-keyboard="false"` sur la `div.modal`. Seuls les boutons de fermeture fonctionnent alors.

## Tailles et position

| Classe sur `modal-dialog` | Effet |
|---|---|
| `modal-sm` | petite modale |
| `modal-lg` | grande modale |
| `modal-xl` | très grande modale |
| `modal-fullscreen` | prend tout l'écran |
| `modal-dialog-centered` | centrée verticalement |
| `modal-dialog-scrollable` | le corps défile si le texte est long |

## Scénario : la confirmation

Scénario classique : avant de supprimer quelque chose, on demande « Es-tu sûr ? ».

```{.html .demo largeur="600" clic="#btnSuppr" legende="La modale de confirmation, centrée"}
<button type="button" class="btn btn-outline-danger" id="btnSuppr"
        data-bs-toggle="modal" data-bs-target="#confirmer">
  <i class="bi bi-trash" aria-hidden="true"></i> Supprimer mon dessin
</button>

<!-- modal-dialog-centered : la fenêtre est centrée ; modal-sm : petite taille -->
<div class="modal fade" id="confirmer" tabindex="-1" aria-labelledby="titreConfirmer">
  <div class="modal-dialog modal-dialog-centered modal-sm">
    <div class="modal-content">
      <div class="modal-body text-center">
        <h2 class="fs-5" id="titreConfirmer">Supprimer le dessin ?</h2>
        <p class="mb-0">Tu ne pourras pas le récupérer.</p>
      </div>
      <!-- justify-content-center : les boutons au milieu -->
      <div class="modal-footer justify-content-center">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
        <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Supprimer</button>
      </div>
    </div>
  </div>
</div>
```

::: attention
Chaque modale doit avoir un `id` **unique**, et le `data-bs-target` du bouton doit l'écrire **exactement pareil**, précédé d'un `#`. Une seule lettre de différence et le bouton ne fait plus rien !
:::

::: astuce
Place tes modales **à la fin du `<body>`**, juste avant le `<script>` de Bootstrap : ton code reste rangé et la modale s'affiche toujours bien au-dessus du reste.
:::

## Exercices du chapitre 13

:::: {.exercice .niveau1}
### Exercice 13.1 — La blague du jour

**🎯 Objectif :** créer une modale simple.

**📝 Consignes :** un bouton jaune « Blague du jour 😂 » ouvre une modale avec le titre « Blague du jour », une blague dans le corps et un bouton « Fermer ».

**🔍 Indices :** `data-bs-toggle="modal"`, `data-bs-target="#blague"`, `modal-header`, `modal-body`, `modal-footer`.

::: solution
```{.html .demo largeur="600" clic="#btnBlague"}
<button type="button" class="btn btn-warning" id="btnBlague" data-bs-toggle="modal" data-bs-target="#blague">
  Blague du jour 😂
</button>
<div class="modal fade" id="blague" tabindex="-1" aria-labelledby="titreBlague">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title fs-5" id="titreBlague">Blague du jour</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
      </div>
      <div class="modal-body">
        Pourquoi les poissons détestent l'ordinateur ? Parce qu'ils ont peur du <strong>net</strong> !
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Fermer</button>
      </div>
    </div>
  </div>
</div>
```

**Explication :** le bouton cible la modale `#blague`. Les trois parties (en-tête, corps, pied) organisent la fenêtre ; la croix et le bouton « Fermer » portent `data-bs-dismiss="modal"`.
:::
::::

:::: {.exercice .niveau2}
### Exercice 13.2 — La fiche d'un animal

**🎯 Objectif :** utiliser une grande modale centrée contenant une image.

**📝 Consignes :** une carte « Le panda » contient un bouton « En savoir plus » qui ouvre une modale **grande** et **centrée** avec une image et deux phrases.

**🔍 Indices :** `modal-dialog modal-lg modal-dialog-centered`, une image `img-fluid rounded`.

::: solution
```{.html .demo largeur="900" clic="#btnPanda"}
<div class="card" style="width: 16rem;">
  <div class="card-body">
    <h2 class="card-title fs-5">🐼 Le panda</h2>
    <p class="card-text">Un ours noir et blanc très gourmand.</p>
    <button type="button" class="btn btn-primary" id="btnPanda" data-bs-toggle="modal" data-bs-target="#panda">En savoir plus</button>
  </div>
</div>
<!-- La modale : grande (modal-lg) et centrée (modal-dialog-centered) -->
<div class="modal fade" id="panda" tabindex="-1" aria-labelledby="titrePanda">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="modal-title fs-5" id="titrePanda">Tout sur le panda</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
      </div>
      <div class="modal-body">
        <!-- row/col : l'image à gauche, le texte à droite -->
        <div class="row align-items-center">
          <div class="col-md-5"><img src="images/panda.jpg" class="img-fluid rounded" alt="Un panda mange du bambou"></div>
          <div class="col-md-7">
            <p>Le panda géant vit dans les montagnes de Chine.</p>
            <p class="mb-0">Il mange jusqu'à 12 kg de bambou par jour !</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
```

**Explication :** `modal-lg` agrandit la fenêtre, `modal-dialog-centered` la centre en hauteur. Dans le corps, on réutilise la grille du chapitre 4 : une modale peut contenir n'importe quel code Bootstrap.
:::
::::

:::: {.exercice .niveau3}
### Exercice 13.3 — L'inscription au tournoi

**🎯 Objectif :** mettre un formulaire dans une modale qui ne se ferme pas par erreur.

**📝 Consignes :** un bouton « S'inscrire au tournoi » ouvre une modale contenant un formulaire (pseudo + choix du jeu). La modale ne doit **pas** se fermer si l'on clique sur le fond. Le bouton « Valider » est le bouton d'envoi du formulaire.

**🔍 Indices :** `data-bs-backdrop="static"`, `data-bs-keyboard="false"` ; le `<form>` peut entourer le corps et le pied de la modale.

::: solution
```{.html .demo largeur="700" clic="#btnTournoi"}
<button type="button" class="btn btn-success" id="btnTournoi" data-bs-toggle="modal" data-bs-target="#tournoi">
  🏆 S'inscrire au tournoi
</button>
<!-- data-bs-backdrop="static" : un clic sur le fond ne ferme pas ;
     data-bs-keyboard="false" : la touche Échap non plus -->
<div class="modal fade" id="tournoi" data-bs-backdrop="static" data-bs-keyboard="false"
     tabindex="-1" aria-labelledby="titreTournoi">
  <div class="modal-dialog modal-dialog-centered">
    <!-- Le formulaire entoure le corps ET le pied -->
    <form class="modal-content" action="inscription.html">
      <div class="modal-header">
        <h2 class="modal-title fs-5" id="titreTournoi">Inscription au tournoi</h2>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
      </div>
      <div class="modal-body">
        <label for="pseudo" class="form-label">Ton pseudo</label>
        <input type="text" class="form-control mb-3" id="pseudo" name="pseudo" required>
        <label for="jeu" class="form-label">Ton jeu</label>
        <select class="form-select" id="jeu" name="jeu">
          <option>Échecs</option>
          <option>Puissance 4</option>
          <option>Dames</option>
        </select>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
        <!-- type="submit" : envoie le formulaire -->
        <button type="submit" class="btn btn-success">Valider</button>
      </div>
    </form>
  </div>
</div>
```

**Explication :** le `<form>` porte directement la classe `modal-content` : ainsi, le bouton « Valider » du pied fait bien partie du formulaire. Avec `static`, seuls la croix et « Annuler » ferment la fenêtre : impossible de perdre son inscription par un clic maladroit.
:::
::::

## Quiz du chapitre 13

::: quiz
1. Quel attribut un bouton doit-il avoir pour ouvrir une modale ?
    - A) `data-bs-toggle="modal"`
    - B) `href="modal"`
    - C) `data-open="modal"`
    - D) `class="modal-open"`
2. Que doit contenir `data-bs-target` ?
    - A) Le nom du fichier
    - B) La couleur de la modale
    - C) L'`id` de la modale précédé de `#`
    - D) Le titre de la modale
3. Quel attribut ferme la modale ?
    - A) `class="close"`
    - B) `data-bs-close`
    - C) `onclose`
    - D) `data-bs-dismiss="modal"`
4. Quelle classe centre la modale verticalement ?
    - A) `modal-center`
    - B) `modal-dialog-centered`
    - C) `align-middle`
    - D) `text-center`
5. Que fait `data-bs-backdrop="static"` ?
    - A) Empêche la fermeture par un clic sur le fond
    - B) Rend le fond blanc
    - C) Agrandit la modale
    - D) Supprime le fondu
:::

::: reponses
1. **A** — `data-bs-toggle="modal"` indique qu'il faut ouvrir une modale.
2. **C** — Par exemple `data-bs-target="#regles"` pour la modale `id="regles"`.
3. **D** — Tout bouton avec `data-bs-dismiss="modal"` ferme la modale qui le contient.
4. **B** — `modal-dialog-centered` se place sur la `div.modal-dialog`.
5. **A** — Le fond devient « statique » : cliquer dessus ne ferme plus la fenêtre.
:::

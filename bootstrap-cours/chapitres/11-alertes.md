# Chapitre 11 — Les alertes {#ch11}

::: chapitre-intro
Une **alerte** est un message important affiché dans une boîte colorée : « Ton message a bien été envoyé ! », « Attention, il manque ton prénom », « Erreur de connexion »… Elle attire l'œil sans gêner le reste de la page.
:::

::: analogie
Les alertes sont comme les **post-it de couleur** que l'on colle sur le frigo : vert pour « Bravo ! », jaune pour « N'oublie pas ! », rouge pour « Urgent ! ». On comprend le message rien qu'à la couleur.
:::

## Créer une alerte

Une alerte est une `div` avec la classe de base `alert`, une classe de couleur `alert-*` et l'attribut `role="alert"` (qui prévient les lecteurs d'écran qu'un message important est apparu).

```{.html .demo largeur="700"}
<!-- alert-success : message de réussite (vert) -->
<div class="alert alert-success" role="alert">✅ Ton inscription au club de chant est confirmée !</div>
<!-- alert-danger : message d'erreur (rouge) -->
<div class="alert alert-danger" role="alert">❌ Le mot de passe est incorrect. Réessaie.</div>
<!-- alert-warning : message d'attention (jaune) -->
<div class="alert alert-warning" role="alert">⚠️ Il ne reste que 2 places pour la sortie au zoo.</div>
<!-- alert-info : simple information (bleu clair) -->
<div class="alert alert-info" role="alert">ℹ️ La cantine sera fermée vendredi.</div>
```

Les huit couleurs existent : `alert-primary`, `alert-secondary`, `alert-success`, `alert-danger`, `alert-warning`, `alert-info`, `alert-light`, `alert-dark`.

## Une alerte plus riche

Une alerte peut contenir un **titre** (`alert-heading`), des paragraphes, une ligne `<hr>` et des **liens** colorés assortis (`alert-link`).

```{.html .demo largeur="700"}
<div class="alert alert-success" role="alert">
  <!-- Le titre de l'alerte -->
  <h4 class="alert-heading">Bravo, champion ! 🏆</h4>
  <p>Tu as terminé tous les exercices du chapitre 10 sans aucune erreur.</p>
  <!-- Une ligne de séparation -->
  <hr>
  <!-- alert-link : un lien dont la couleur va avec l'alerte ; mb-0 : pas de marge dessous -->
  <p class="mb-0">Prêt pour la suite ? <a href="chapitre11.html" class="alert-link">Passe au chapitre 11</a>.</p>
</div>
```

## Une alerte avec une icône

En utilisant `d-flex` et `align-items-center` (chapitre 15), on aligne une icône Bootstrap Icons et le texte.

```{.html .demo largeur="700"}
<!-- d-flex : icône et texte côte à côte ; align-items-center : centrés verticalement -->
<div class="alert alert-warning d-flex align-items-center" role="alert">
  <!-- fs-4 : grande icône ; me-2 : espace à droite -->
  <i class="bi bi-exclamation-triangle-fill fs-4 me-2" aria-hidden="true"></i>
  <div>N'oublie pas ton maillot de bain pour la piscine demain !</div>
</div>
```

## Une alerte qu'on peut fermer

Avec le JavaScript de Bootstrap, on ajoute une **croix** pour fermer l'alerte :

- les classes `alert-dismissible fade show` sur l'alerte ;
- un bouton `btn-close` avec `data-bs-dismiss="alert"`.

```{.html .demo largeur="700"}
<!-- alert-dismissible : place pour la croix ; fade show : disparition en fondu -->
<div class="alert alert-info alert-dismissible fade show" role="alert">
  📢 Nouveau : le club de robotique ouvre le mercredi !
  <!-- La croix : data-bs-dismiss="alert" ferme l'alerte qui la contient -->
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
</div>
```

Quand on clique sur la croix, l'alerte disparaît en douceur :

```{.html .demo largeur="700" clic=".btn-close" attente="400" legende="Après un clic sur la croix (l'alerte a disparu)"}
<div class="alert alert-info alert-dismissible fade show" role="alert">
  📢 Nouveau : le club de robotique ouvre le mercredi !
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
</div>
<p class="mb-0">La suite de la page remonte à la place de l'alerte.</p>
```

::: astuce
Utilise les alertes avec modération : si tout est affiché en alerte, plus rien n'attire l'attention ! Réserve-les aux messages vraiment importants.
:::

::: retenir
- Alerte = `div.alert.alert-couleur` + `role="alert"`.
- Couleurs : `alert-success` (réussite), `alert-danger` (erreur), `alert-warning` (attention), `alert-info` (information)…
- Contenu : `alert-heading`, `alert-link`, `<hr>`.
- Fermable : `alert-dismissible fade show` + `<button class="btn-close" data-bs-dismiss="alert" aria-label="Fermer">`.
:::

## Exercices du chapitre 11

:::: {.exercice .niveau1}
### Exercice 11.1 — Trois messages

**🎯 Objectif :** choisir la bonne couleur d'alerte.

**📝 Consignes :** affiche trois alertes : « Photo enregistrée » (réussite), « Fichier trop lourd » (erreur), « Batterie faible » (attention).

**🔍 Indices :** `alert-success`, `alert-danger`, `alert-warning`, sans oublier `role="alert"`.

::: solution
```{.html .demo largeur="600"}
<div class="alert alert-success" role="alert">📷 Photo enregistrée.</div>
<div class="alert alert-danger" role="alert">📁 Fichier trop lourd (5 Mo maximum).</div>
<div class="alert alert-warning" role="alert">🔋 Batterie faible : branche ton appareil.</div>
```

**Explication :** la couleur correspond au sens du message : vert = réussi, rouge = erreur, jaune = attention. `role="alert"` fait annoncer le message par les lecteurs d'écran.
:::
::::

:::: {.exercice .niveau2}
### Exercice 11.2 — Le bulletin météo de la sortie

**🎯 Objectif :** créer une alerte riche avec titre, séparation et lien.

**📝 Consignes :** crée une alerte bleue (`alert-primary`) avec le titre « Sortie au parc maintenue ☀️ », un paragraphe, une ligne de séparation et un lien « Voir la liste des affaires ».

**🔍 Indices :** `alert-heading`, `<hr>`, `alert-link`, `mb-0` sur le dernier paragraphe.

::: solution
```{.html .demo largeur="700"}
<div class="alert alert-primary" role="alert">
  <h4 class="alert-heading">Sortie au parc maintenue ☀️</h4>
  <p>Il fera 22 °C demain. Rendez-vous à 9 h devant le portail de l'école.</p>
  <hr>
  <p class="mb-0"><a href="affaires.html" class="alert-link">Voir la liste des affaires</a> à emporter.</p>
</div>
```

**Explication :** `alert-heading` donne au titre la couleur de l'alerte, `<hr>` sépare les parties, `alert-link` rend le lien bien visible et assorti. `mb-0` enlève l'espace inutile en bas de l'alerte.
:::
::::

:::: {.exercice .niveau3}
### Exercice 11.3 — Les alertes fermables avec icônes

**🎯 Objectif :** combiner icône, flexbox et fermeture.

**📝 Consignes :** crée deux alertes fermables : une verte avec l'icône `bi-check-circle-fill` (« Devoir rendu à temps ») et une rouge avec l'icône `bi-x-octagon-fill` (« Tu as oublié ton exposé »).

**🔍 Indices :** `alert-dismissible fade show d-flex align-items-center`, une icône `<i>` avec `me-2`, et la croix `btn-close`.

::: solution
```{.html .demo largeur="700"}
<!-- Alerte verte fermable avec icône -->
<div class="alert alert-success alert-dismissible fade show d-flex align-items-center" role="alert">
  <i class="bi bi-check-circle-fill fs-5 me-2" aria-hidden="true"></i>
  <div>Devoir de maths rendu à temps. Bien joué !</div>
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
</div>
<!-- Alerte rouge fermable avec icône -->
<div class="alert alert-danger alert-dismissible fade show d-flex align-items-center" role="alert">
  <i class="bi bi-x-octagon-fill fs-5 me-2" aria-hidden="true"></i>
  <div>Tu as oublié ton exposé sur les volcans.</div>
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
</div>
```

**Explication :** `d-flex align-items-center` aligne l'icône et le texte. `alert-dismissible` réserve la place de la croix à droite. Chaque croix ferme uniquement l'alerte dans laquelle elle se trouve, grâce à `data-bs-dismiss="alert"`.
:::
::::

## Quiz du chapitre 11

::: quiz
1. Quelle classe utiliser pour un message de réussite ?
    - A) `alert-good`
    - B) `alert-success`
    - C) `alert-danger`
    - D) `alert-green`
2. À quoi sert `role="alert"` ?
    - A) À prévenir les lecteurs d'écran qu'un message important est affiché
    - B) À colorer l'alerte
    - C) À fermer l'alerte
    - D) À faire clignoter l'alerte
3. Quel attribut permet à un bouton de fermer l'alerte ?
    - A) `data-close="true"`
    - B) `onclick="hide"`
    - C) `class="close-alert"`
    - D) `data-bs-dismiss="alert"`
4. Quelle classe colore un lien assorti à l'alerte ?
    - A) `text-link`
    - B) `btn-link`
    - C) `alert-link`
    - D) `link-alert`
5. Pourquoi ne pas mettre tous les messages en alerte ?
    - A) Parce que Bootstrap en limite le nombre
    - B) Parce que les alertes sont payantes
    - C) Parce que plus rien n'attirerait l'attention
    - D) Parce qu'elles ralentissent l'ordinateur
:::

::: reponses
1. **B** — `alert-success` = vert, pour la réussite.
2. **A** — `role="alert"` annonce le message aux technologies d'assistance.
3. **D** — `data-bs-dismiss="alert"` sur le bouton `btn-close` (avec le JavaScript de Bootstrap).
4. **C** — `alert-link` donne au lien une couleur assortie et un style gras.
5. **C** — Trop d'alertes = plus aucune ne ressort. On les garde pour l'essentiel.
:::

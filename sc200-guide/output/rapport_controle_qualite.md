# Rapport de contrôle qualité — Guide SC-200

- **Date du contrôle** : 2026-10-12 (vérification documentaire : 2026-10-12).
- **Questions uniques** : 347 (objectif ≥ 450) — module 1 : 75, module 2 : 100, module 3 : 101, module 4 : 71.
- **Section KQL avancée (module 3)** : Compréhension de requêtes existantes : 20, Correction de requêtes erronées : 21, Création de requêtes à partir d'un besoin : 20, Corrélation entre plusieurs tables : 20, Chasse aux menaces sur journaux simulés : 20.
- **Contrôles structurels** (propositions, une correction et une analyse par distracteur, explication technique, point à retenir, référence valide) : aucune anomalie.
- **Doublons** (similarité de trigrammes > 0,6 entre énoncés + propositions) : aucun. Aucun titre en double.
- **Répartition des bonnes réponses** (toutes questions) : A : 93, B : 89, C : 87, D : 86, E : 8. Les propositions sont placées par un algorithme d'équilibrage déterministe ; aucune question n'utilise « toutes les réponses ci-dessus ».
- **Requêtes KQL** : 302 requêtes analysées par la bibliothèque officielle Kusto.Language (@kusto/language-service-next) avec les schémas de tables publiés sur Microsoft Learn (Sentinel/Log Analytics et chasse avancée Defender XDR) : **0 erreur syntaxique ou sémantique** (tables, colonnes, types, fonctions). 33 requêtes sont volontairement erronées (exercices de correction) et 2 ne sont pas validables hors ligne (fonctions propres à un environnement) ; elles sont signalées comme telles.
- **Liens documentaires** : 376 URL Microsoft Learn vérifiées (HTTP 200) dont 266 citées dans les questions ; 0 lien cassé.
- **Examens blancs** : 3 × 50 questions, 150 questions distinctes (aucune répétition entre examens), corrigé complet pour chacune ; pondération 21/18/11 conforme au poids des trois domaines officiels.
- **Référentiel** : contenu aligné sur le guide d'étude SC-200 « Compétences mesurées à compter du 21 octobre 2026 » (Gérer un environnement d'opérations de sécurité 40–45 %, Répondre aux incidents de sécurité 35–40 %, Effectuer la chasse aux menaces 20–25 %).

À rédiger {{MATRICE}}

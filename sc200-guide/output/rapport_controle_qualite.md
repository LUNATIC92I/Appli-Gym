# Rapport de contrôle qualité — Guide SC-200

- **Date du contrôle** : 2026-10-12 (vérification documentaire : 2026-10-12).
- **Questions uniques** : 451 (objectif ≥ 450) — module 1 : 75, module 2 : 100, module 3 : 101, module 4 : 75, module 5 : 50, module 6 : 50.
- **Section KQL avancée (module 3)** : Compréhension de requêtes existantes : 20, Correction de requêtes erronées : 21, Création de requêtes à partir d'un besoin : 20, Corrélation entre plusieurs tables : 20, Chasse aux menaces sur journaux simulés : 20.
- **Contrôles structurels** (propositions, une correction et une analyse par distracteur, explication technique, point à retenir, référence valide) : aucune anomalie.
- **Doublons** (similarité de trigrammes > 0,6 entre énoncés + propositions) : aucun. Aucun titre en double.
- **Répartition des bonnes réponses** (toutes questions) : A : 119, B : 115, C : 116, D : 116, E : 14. Les propositions sont placées par un algorithme d'équilibrage déterministe ; aucune question n'utilise « toutes les réponses ci-dessus ».
- **Requêtes KQL** : 359 requêtes analysées par la bibliothèque officielle Kusto.Language (@kusto/language-service-next) avec les schémas de tables publiés sur Microsoft Learn (Sentinel/Log Analytics et chasse avancée Defender XDR) : **0 erreur syntaxique ou sémantique** (tables, colonnes, types, fonctions). 33 requêtes sont volontairement erronées (exercices de correction) et 2 ne sont pas validables hors ligne (fonctions propres à un environnement) ; elles sont signalées comme telles.
- **Liens documentaires** : 376 URL Microsoft Learn vérifiées (HTTP 200) dont 298 citées dans les questions ; 0 lien cassé.
- **Examens blancs** : 3 × 50 questions, 150 questions distinctes (aucune répétition entre examens), corrigé complet pour chacune ; pondération 21/18/11 conforme au poids des trois domaines officiels.
- **Référentiel** : contenu aligné sur le guide d'étude SC-200 « Compétences mesurées à compter du 21 octobre 2026 » (Gérer un environnement d'opérations de sécurité 40–45 %, Répondre aux incidents de sécurité 35–40 %, Effectuer la chasse aux menaces 20–25 %).

### Méthode de contrôle

Les contrôles ci-dessus sont produits **automatiquement** à chaque génération par `scripts/build.py` ; ils ne sont pas déclaratifs. Les vérifications réalisées :

1. **Structure** : chaque question possède un énoncé, 4 ou 5 propositions, le bon nombre de réponses correctes, une justification pour chaque proposition incorrecte, une correction détaillée, une explication technique, un point à retenir et au moins une référence connue et valide. Les corrections ne citent jamais de lettre, car les propositions sont mélangées par un algorithme d'équilibrage déterministe.
2. **KQL** : les requêtes sont analysées (syntaxe **et** sémantique : tables, colonnes, types, fonctions) par la bibliothèque officielle Kusto.Language (paquet npm `@kusto/language-service-next`), avec les schémas de 82 tables téléchargés depuis les pages de référence Microsoft Learn (`scripts/fetch_schemas.py`). Les requêtes des propositions incorrectes qui sont volontairement invalides sont marquées et exclues de la validation.
3. **Liens** : chaque URL Microsoft Learn (version fr-fr) est téléchargée ; le code HTTP, l'absence de redirection vers une autre page et le titre réel de la page sont enregistrés (`scripts/verify_refs.py`, fichier `content/refs.json`).
4. **Doublons** : comparaison de similarité (trigrammes de mots) entre toutes les paires de questions et contrôle des titres en double.
5. **Relecture technique** : les faits sensibles et récents (data lake, gestion des cas, collecte personnalisée MDE, détections personnalisées, plans Defender for Servers, prérequis de connexion au portail Defender, référentiel SC-200 du 21 octobre 2026) ont été vérifiés sur les pages Microsoft Learn correspondantes pendant la rédaction ; les résultats attendus des 20 exercices de chasse sur journaux simulés ont été recalculés manuellement (une erreur de jour de semaine a ainsi été corrigée).

### Limites connues

- **Exécution des requêtes** : l'analyse Kusto.Language valide la syntaxe, les noms et les types, mais **n'exécute pas** les requêtes. Les résultats annoncés pour les journaux simulés ont été vérifiés à la main ; le comportement exact de certaines fonctions (par exemple `series_decompose_anomalies`) peut varier légèrement selon la version du moteur.
- **Hypothèses de schéma** : quelques requêtes s'appuient sur des propriétés de colonnes dynamiques (par exemple `Properties_d.resourceLocation`, `RawEventData.SiteUrl`, `AdditionalFields`) ou sur des valeurs de colonnes (libellés d'`ActionType`, de `ProductName`, de `SentinelResourceType`) qui dépendent des sources et des versions ; elles sont signalées dans la rubrique « Hypothèses liées au schéma ». Les fonctions propres à un environnement (`workspace()`, `FileProfile()`) sont marquées comme non validables hors ligne.
- **Évolution des produits** : les fonctionnalités Microsoft Security évoluent très vite (renommages, préversions, migrations vers le portail Defender). Les informations sont exactes à la date de vérification indiquée ; plusieurs fonctionnalités citées sont en **préversion** (cas d'incident, collecte personnalisée MDE, certaines capacités du data lake et du graphe Sentinel) et peuvent changer.
- **Pondération** : la répartition par module imposée par le cahier des charges ne reproduit pas exactement la pondération officielle (voir la matrice de la présentation) ; les examens blancs, eux, la respectent.
- **Langue de l'examen** : la version française de l'examen peut être mise à jour environ huit semaines après la version anglaise ; le guide suit le référentiel du 21 octobre 2026.
- **Questions officielles** : aucune question réelle de l'examen n'a été consultée ni reproduite ; le style des questions est indicatif.
- **Version DOCX** : si elle est fournie, elle est convertie automatiquement depuis le HTML par Pandoc ; sa mise en page est plus simple que celle du PDF, qui reste la version de référence.

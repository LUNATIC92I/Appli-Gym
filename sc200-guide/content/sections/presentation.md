## La certification Microsoft SC-200

La certification **Microsoft Certified: Security Operations Analyst Associate** est obtenue en réussissant l'examen **SC-200 : Microsoft Security Operations Analyst**. Elle s'adresse aux analystes SOC qui réduisent le risque de l'organisation en **triant** les alertes, en **répondant** aux incidents, en **chassant** les menaces et en **concevant des détections**, à l'aide de Microsoft Defender XDR, Microsoft Sentinel, Microsoft Entra ID, Microsoft Purview et Microsoft Defender for Cloud.

Informations publiées par Microsoft Learn (page « Guide d'étude de l'examen SC-200 », consultée le 12 octobre 2026) :

- **Score de réussite : 700** (sur une échelle de 1 à 1 000).
- Les certifications Associate **expirent chaque année** et se renouvellent gratuitement par une évaluation en ligne sur Microsoft Learn.
- La **version anglaise** de l'examen est mise à jour en premier ; les versions localisées le sont **environ huit semaines plus tard**. Si votre langue n'est pas disponible, un temps supplémentaire de 30 minutes peut être demandé.
- La plupart des questions portent sur des fonctionnalités en **disponibilité générale** ; des fonctionnalités en préversion couramment utilisées peuvent apparaître.
- Un **bac à sable d'examen** et une **évaluation d'entraînement gratuite** sont proposés par Microsoft.

> **Référentiel utilisé par ce guide.** Le guide est aligné sur les **compétences mesurées à compter du 21 octobre 2026** (le journal des modifications publié par Microsoft indique une modification « mineure » de l'objectif d'ingestion des données par rapport à la version précédente). Si vous passez l'examen dans une langue autre que l'anglais peu après cette date, vérifiez sur Microsoft Learn quelle version des compétences s'applique à votre session.

### Les trois domaines officiels

| Domaine officiel | Poids | Principaux objectifs |
|------------------|-------|------------------------------------------------------------|
| **1. Gérer un environnement d'opérations de sécurité** | 40–45 % | Automatisation Defender XDR et Sentinel (notifications, ajustement des alertes, fonctionnalités avancées et stratégies MDE dont ASR, AIR, interruption automatique d'attaque, groupes d'appareils, règles d'automatisation, playbooks) ; configuration de la plateforme Sentinel (rôles, rétention Analytics / data lake / XDR, classeurs, optimisations SOC) ; ingestion (connecteurs, Windows Security Events via AMA, WEF, Syslog et CEF via AMA, journaux Azure par paramètres de diagnostic et Azure Policy, indicateurs de menace, tables personnalisées) ; détections (règles de détection personnalisées Defender XDR, règles d'analyse planifiées, NRT, TI et ML, couverture MITRE ATT&CK, anomalies). |
| **2. Répondre aux incidents de sécurité** | 35–40 % | Alertes et incidents Defender XDR (Defender for Office 365, Purview, Defender for Cloud, Defender for Cloud Apps, Entra ID, Defender for Identity, Sentinel), IA agentique et Security Copilot, attaques multi-étapes et mouvement latéral, gestion des cas ; réponse avec Defender for Endpoint (chronologie, réponse dynamique, package d'investigation, entités et preuves, interruption d'attaque) ; activités Microsoft 365 (Purview Audit, recherche de contenu eDiscovery, journaux d'activité Microsoft Graph). |
| **3. Effectuer la chasse aux menaces** | 20–25 % | Chasse avec Defender XDR (choix des tables, KQL, requêtes de chasse avancée, analyse des menaces, graphes de chasse et rayon d'impact, Sentinel Graph) ; chasse avec la plateforme Sentinel (requêtes de chasse, jobs KQL du data lake, règles de synthèse, notebooks et serveur MCP de Sentinel). |

## Organisation du guide

Le guide suit la répartition pédagogique demandée en six modules, chacun rattaché aux domaines officiels question par question (mention « Référentiel officiel » de chaque question). La matrice ci-dessous résulte du décompte automatique réalisé par le script de génération.

{{MATRICE}}

**Remarque sur la pondération.** La répartition en modules imposée par le cahier des charges (100 questions Defender XDR, 75 questions de réponse aux incidents) donne plus de poids au domaine 2 que l'examen officiel. Pour compenser, les **examens blancs** sont construits selon la pondération officielle (21 questions du domaine 1, 18 du domaine 2, 11 du domaine 3 sur 50) et les fiches de révision insistent sur les objectifs de configuration du domaine 1.

### Format de chaque question

Chaque question comporte : un titre, le domaine (module du guide), le niveau (Débutant, Intermédiaire, Avancé), le rattachement au référentiel officiel, l'énoncé, quatre propositions (cinq pour les questions à réponses multiples, qui indiquent le nombre de réponses attendues), puis un **encadré de correction** : bonne réponse, correction détaillée, analyse de chaque proposition incorrecte, explication technique, exemple pratique éventuel, point à retenir et références Microsoft Learn vérifiées.

Les exercices du **module 3 (KQL)** ajoutent, lorsque c'est utile, le **résultat attendu**, les **hypothèses liées au schéma de données** et une **amélioration proposée**. Les blocs de code sont étiquetés :

- **KQL — Microsoft Sentinel / Log Analytics** : requête validée avec le schéma des tables Log Analytics (colonne temporelle `TimeGenerated`) ;
- **KQL — Chasse avancée Microsoft Defender XDR** : requête validée avec le schéma de la chasse avancée (colonne temporelle `Timestamp`) ;
- **KQL — requête volontairement erronée** : requête à corriger dans les exercices de correction ;
- **Requête KQL** (dans les propositions) : étiquette neutre, pour ne pas révéler la bonne réponse.

Toutes les requêtes marquées Sentinel ou Defender XDR ont été analysées par la bibliothèque officielle **Kusto.Language** de Microsoft, avec les schémas de tables publiés sur Microsoft Learn (voir le rapport de contrôle qualité en fin de document).

### Conseils d'utilisation

1. Lisez la présentation de chaque module, puis traitez les questions **sans regarder la correction** (masquez l'encadré vert).
2. Notez vos réponses, comparez, et relisez systématiquement l'analyse des propositions incorrectes : c'est là que se trouvent les pièges classiques.
3. Reproduisez les requêtes KQL dans un espace de démonstration (Log Analytics Demo, chasse avancée d'un locataire d'essai) : la pratique est indispensable.
4. Passez un examen blanc en conditions réelles (100 minutes, sans documentation) après les modules 1 à 3, puis les deux autres en fin de préparation.
5. Utilisez les fiches de révision et le glossaire dans les derniers jours.

> **Avertissement.** Ce guide est un support de formation original et indépendant. Il ne reproduit aucune question de l'examen officiel et ne constitue pas une garantie de réussite. Les fonctionnalités Microsoft évoluent rapidement : en cas de doute, la documentation Microsoft Learn fait foi. Certaines fonctionnalités dépendent de licences (par exemple Defender for Office 365 Plan 2, Microsoft Entra ID P2, Defender for Servers Plan 2, Defender CSPM, Security Copilot) ou sont en préversion ; cela est signalé dans les explications.

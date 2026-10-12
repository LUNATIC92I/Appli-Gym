## Fiche 1 — Principales tables KQL

### Microsoft Defender XDR (chasse avancée — colonne temporelle `Timestamp`, 30 jours)

| Table | Contenu | Colonnes clés |
|----------------------|----------------------------|------------------------------------|
| DeviceProcessEvents | Créations de processus | FileName, FolderPath, ProcessCommandLine, SHA256, AccountName, InitiatingProcessFileName/CommandLine, InitiatingProcessParentFileName |
| DeviceNetworkEvents | Connexions réseau | RemoteIP, RemotePort, RemoteUrl, LocalIP, ActionType (ConnectionSuccess…), InitiatingProcess* |
| DeviceFileEvents | Créations, modifications, renommages, suppressions de fichiers | ActionType (FileCreated, FileRenamed…), FileName, FolderPath, SHA256, PreviousFileName, RequestProtocol, RequestSourceIP, ShareName |
| DeviceLogonEvents | Ouvertures de session sur les appareils | ActionType (LogonSuccess/LogonFailed), LogonType (chaîne : Interactive, RemoteInteractive, Network…), AccountName, RemoteIP, LogonId, IsLocalAdmin |
| DeviceRegistryEvents | Modifications du registre | ActionType (RegistryValueSet…), RegistryKey, RegistryValueName, RegistryValueData |
| DeviceEvents | Événements divers (ASR, antivirus, services, PowerShell…) | ActionType (AsrOfficeChildProcessBlocked, ServiceInstalled, AntivirusDetection…), AdditionalFields |
| DeviceInfo / DeviceTvmSoftwareVulnerabilities | Inventaire, vulnérabilités | DeviceId, OSPlatform ; CveId, VulnerabilitySeverityLevel (instantané, sans Timestamp) |
| EmailEvents | Messages (remise, menaces) | NetworkMessageId, SenderFromAddress, RecipientEmailAddress, Subject, EmailDirection, DeliveryAction, DeliveryLocation, LatestDeliveryLocation, ThreatTypes, AuthenticationDetails |
| EmailAttachmentInfo / EmailUrlInfo | Pièces jointes / URL des messages | NetworkMessageId, RecipientEmailAddress, FileName, FileType, SHA256 ; Url, UrlDomain |
| EmailPostDeliveryEvents | Actions après remise (ZAP, remédiation) | NetworkMessageId, ActionType (Phish ZAP…), ActionResult |
| UrlClickEvents | Clics Safe Links | Url, AccountUpn, ActionType (ClickAllowed/ClickBlocked), IsClickedThrough, NetworkMessageId |
| IdentityLogonEvents / IdentityQueryEvents / IdentityDirectoryEvents | Authentifications, requêtes de reconnaissance, changements d'annuaire (MDI) | AccountUpn, IPAddress, ActionType, Protocol ; QueryType, QueryTarget ; AdditionalFields |
| CloudAppEvents | Activités Microsoft 365 et applications connectées | ActionType, Application, AccountObjectId, IPAddress, RawEventData |
| AlertInfo / AlertEvidence | Alertes de tous les produits / entités des alertes | AlertId, Title, Severity, ServiceSource ; EntityType, EvidenceRole |

### Microsoft Sentinel / Log Analytics (colonne temporelle `TimeGenerated`)

| Table | Contenu | À retenir |
|----------------------|----------------------------|------------------------------------|
| SigninLogs / AADNonInteractiveUserSignInLogs | Connexions Entra ID interactives / non interactives | ResultType est une **chaîne** (« 0 » = succès, 50126, 500121…) ; LocationDetails et DeviceDetail sont **dynamiques** |
| AADServicePrincipalSignInLogs | Connexions des principaux de service | Compromission d'applications |
| AuditLogs | Opérations d'annuaire Entra ID | OperationName, InitiatedBy et TargetResources dynamiques |
| AADUserRiskEvents / AADRiskyUsers | Détections et utilisateurs à risque (ID Protection) | RiskEventType, RiskLevel |
| SecurityEvent | Journal Sécurité Windows (AMA) | EventID numérique, LogonType numérique (10 = RDP) |
| WindowsEvent / Event | Événements transférés (WEF) / autres journaux Windows | Canal, EventData |
| Syslog / CommonSecurityLog | Linux et appliances / CEF | Facility, ProcessName, SyslogMessage ; DeviceVendor, SourceIP, DestinationIP, DeviceAction |
| AzureActivity | Plan de contrôle Azure | OperationNameValue, Caller, CallerIpAddress, ActivityStatusValue |
| OfficeActivity | Audit Microsoft 365 | Operation, UserId, ClientIP, OfficeWorkload, Parameters |
| MicrosoftGraphActivityLogs | Appels à l'API Graph | RequestUri, AppId, UserId, IPAddress |
| SecurityAlert / SecurityIncident | Alertes / incidents (historisés) | Dédupliquer avec `arg_max` ; AlertIds tableau dynamique |
| ThreatIntelIndicators | Indicateurs STIX | ObservableKey, ObservableValue, IsActive, ValidUntil, Confidence |
| BehaviorAnalytics / IdentityInfo / Anomalies | UEBA et anomalies | InvestigationPriority (0–10), ActivityInsights ; AccountUPN, AssignedRoles |
| SentinelHealth / SentinelAudit / Usage | Santé, audit, volumes | Échecs de connecteurs/automatisations ; volumes par DataType (Mo) |

## Fiche 2 — Opérateurs et fonctions KQL essentiels

| Besoin | Opérateur / fonction | Exemple |
|----------------------|----------------------------|------------------------------------|
| Filtrer | `where`, `==`, `=~`, `!=`, `in`, `in~`, `has`, `has_any`, `contains`, `startswith`, `matches regex`, `between (a .. b)` | `where FileName in~ ("net.exe","whoami.exe")` |
| Fenêtre temporelle | `ago()`, `now()`, `datetime()`, `bin()`, `startofday()` | `where TimeGenerated > ago(1d)` |
| Colonnes | `project`, `project-away`, `project-rename`, `extend` | `extend Pays = tostring(LocationDetails.countryOrRegion)` |
| Agréger | `summarize` + `count()`, `dcount()`, `countif()`, `make_set()`, `make_list()`, `arg_max()`, `min()`, `max()`, `avg()`, `percentile()` | `summarize arg_max(TimeGenerated, *) by IncidentNumber` |
| Classer | `top N by`, `sort by` / `order by` (décroissant par défaut), `take` | `top 10 by Echecs` |
| Combiner | `join kind=inner/leftouter/leftanti/innerunique (défaut)`, `lookup`, `union withsource=` | `join kind=inner T on $left.A == $right.B` |
| Dynamique | `parse_json()`, `tostring()`, `mv-expand`, `bag_unpack`, `set_has_element()` | `mv-expand AlertId = AlertIds to typeof(string)` |
| Texte | `parse`, `extract()`, `split()`, `strcat()`, `tolower()`, `base64_decode_tostring()`, chaînes verbatim `@"..."` | `extract(@"from (\d+\.\d+\.\d+\.\d+)", 1, SyslogMessage)` |
| Variables | `let x = ...;`, `toscalar()`, `materialize()`, `datatable()`, `_GetWatchlist()` | `let fenetre = 1h;` |
| Séries | `make-series`, `series_decompose_anomalies()`, `prev()` (après `sort`) | `extend (A, S, B) = series_decompose_anomalies(Serie, 2.5)` |
| IP / géographie | `ipv4_is_private()`, `ipv4_is_in_range()`, `geo_info_from_ip_address()` | `where not(ipv4_is_private(DestinationIP))` |
| Visualiser | `render timechart`, `render barchart` | `summarize count() by bin(TimeGenerated, 1h) | render timechart` |

**Performance :** filtre temporel en premier ; `has` plutôt que `contains` ; filtrer et projeter avant les jointures ; petite table à gauche ; éviter `search *` ; préciser `kind` dans les jointures ; nommer les agrégats.

## Fiche 3 — Microsoft Sentinel en un coup d'œil

| Domaine | Points essentiels |
|---|---|
| Plateforme | SIEM + SOAR sur un espace Log Analytics (une instance par espace). Transition vers le portail Defender : plus de prise en charge dans le portail Azure après le **31 mars 2027**. Espace principal + secondaires dans le portail Defender. |
| Rôles | Reader / Responder (incidents) / Contributor (ressources, solutions) / Playbook Operator (exécuter) / Automation Contributor (compte de service Sentinel) ; Logic App Contributor pour éditer les playbooks ; RBAC Azure pour le SIEM, RBAC Entra pour le data lake ; RBAC au niveau table et de contexte de ressource. |
| Ingestion | AMA + DCR (XPath, transformations), Windows Security Events via AMA (SecurityEvent), Windows Forwarded Events (WEF → WindowsEvent), Syslog/CEF via AMA (redirecteur Linux), journaux Azure par paramètres de diagnostic + Azure Policy, TI (TAXII, API d'upload, MDTI), Logs Ingestion API (tables _CL), connecteur Defender XDR. |
| Données | Niveau Analytics (détection, 90 jours inclus) ; data lake (jusqu'à 12 ans, KQL, jobs KQL, notebooks) ; tables XDR 30 jours par défaut, rétention extensible ; jobs de recherche (_SRCH), restauration (_RST), règles de synthèse (20 min–24 h). |
| Détection | Planifiées (5 min–14 j, lookback ≤ 14 j, 150 alertes max par exécution), NRT (chaque minute, nombre limité), Fusion (ML multi-étapes, activé par défaut), Microsoft Threat Intelligence Analytics, anomalies (Flighting/Production), mappage d'entités (10 max), détails personnalisés, remplacement des détails d'alerte, regroupement d'alertes (≤ 7 jours, 150 alertes/incident), suppression (≤ 24 h), « AUTO DISABLED ». |
| Automatisation | Règles d'automatisation (incident créé/mis à jour, alerte créée ; ordre ; expiration ; tâches) ; playbooks Logic Apps (déclencheurs incident, alerte, entité ; identité managée). |
| Chasse | Requêtes de chasse, signets, Hunts, notebooks (MSTICPy, data lake), serveur MCP, graphe Sentinel, UEBA (BehaviorAnalytics, IdentityInfo), watchlists, ASIM (`_Im_*`). |
| Pilotage | Classeurs (paramètres), page MITRE ATT&CK, optimisations SOC, SentinelHealth/SentinelAudit, Repositories (CI/CD), hub de contenu. |

## Fiche 4 — Capacités de Microsoft Defender XDR

| Produit | Capacités clés pour le SOC |
|---|---|
| Portail Defender | Incidents corrélés multi-produits, alertes, analyse des menaces, chasse avancée (30 j), détections personnalisées (Continuous/NRT, 1 h, 3 h, 12 h, 24 h, Custom pour Sentinel), Centre de notifications, gestion des cas, Copilot, RBAC unifié, notifications (incidents, alertes, actions de réponse, analyse des menaces), ajustement des alertes. |
| Defender for Endpoint | EDR, ASR (Audit → Bloquer/Avertir), protection réseau, contrôle des dossiers, anti-falsification, mode bloc EDR, indicateurs (fichiers : AV actif ; IP/URL : protection réseau + « Custom network indicators »), groupes d'appareils (rang), niveaux d'automatisation (Full recommandé), Live Response (basique/avancé), isolation (complète/sélective), restriction d'exécution, package d'investigation, contenir appareil/utilisateur, découverte des appareils, collecte personnalisée. |
| AIR / interruption d'attaque | Enquêtes automatiques avec verdicts et remédiations (actions en attente : 7 jours) ; interruption automatique (contenir appareils, désactiver/contenir utilisateurs) pour rançongiciels, BEC, AiTM ; exclusions possibles. |
| Defender for Office 365 | P1 : Safe Attachments, Safe Links, anti-hameçonnage avancé, détections en temps réel ; P2 : Threat Explorer, AIR, campagnes, simulation d'attaques ; ZAP, soumissions, liste d'autorisation/blocage, stratégies prédéfinies. |
| Defender for Identity | Capteurs sur DC, AD FS, AD CS, Entra Connect (v3 via MDE) ; détections (reconnaissance SAMR/LDAP, Kerberoasting, DCSync, pass-the-hash/ticket, Golden Ticket, force brute) ; honeytokens ; actions (désactiver, réinitialiser) via compte d'action. |
| Defender for Cloud Apps | Cloud Discovery (MDE, collecteur), applications non approuvées bloquées via MDE, connecteurs API, contrôle de session (accès conditionnel), anomalies (voyage impossible, téléchargement massif, IP anonyme), stratégies de fichiers, gouvernance des applications OAuth, « confirmer l'utilisateur compromis ». |
| Entra ID Protection | Risque utilisateur (identifiants divulgués…) vs risque de connexion (IP anonyme, propriétés inhabituelles, voyage atypique…) ; stratégies d'accès conditionnel basées sur le risque ; confirmer/ignorer le risque. |
| Purview | Audit (Standard 180 jours, Premium 1 an+), MailItemsAccessed, eDiscovery (conservation, recherche, purge), alertes DLP et Insider Risk dans Defender XDR. |

## Fiche 5 — Étapes d'investigation d'un incident

1. **Triage** : lire l'incident (histoire de l'attaque, alertes, ressources), qualifier (vrai/faux positif), évaluer gravité et urgence, assigner.
2. **Délimiter** : entités impliquées (comptes, appareils, boîtes, applications, IP), fenêtre temporelle, patient zéro.
3. **Collecter** : chronologie des appareils, arborescence de processus, chasse avancée sur les IOC, journaux d'identité et d'audit, package d'investigation, preuves volatiles d'abord.
4. **Analyser** : reconstruire la chaîne ATT&CK (accès initial → exécution → persistance → élévation → accès aux identifiants → découverte → mouvement latéral → collecte → exfiltration/impact), mesurer l'impact sur les données.
5. **Pivoter** : rechercher chaque nouvel indicateur dans tout le parc (hachages, IP, domaines, comptes, techniques).
6. **Documenter** : chronologie, décisions, preuves (chaîne de custode), conclusions, classification.

## Fiche 6 — Procédures de réponse

| Situation | Confinement | Éradication / restauration |
|----------------------|----------------------------|------------------------------------|
| Poste compromis | Isoler (MDE), restreindre l'exécution, contenir les appareils non gérés | Supprimer persistances, réimager si accès privilégié prolongé, libérer après vérification |
| Compte cloud compromis | Désactiver/réinitialiser, révoquer les sessions, marquer compromis | Supprimer règles de boîte, méthodes MFA, consentements, appareils ajoutés ; stratégies de risque, MFA résistante |
| Compte AD / hybride | Désactiver dans AD (source) et cloud, contenir l'utilisateur | Réinitialiser les comptes exposés ; krbtgt deux fois si DCSync/Golden Ticket |
| Hameçonnage | Purger les messages (Explorer / Take action / eDiscovery), bloquer expéditeur/URL | Traiter les cliqueurs, soumettre à Microsoft, durcir (ASR, Safe Links/Attachments) |
| Rançongiciel | Isoler, désactiver les comptes de déploiement, neutraliser GPO/tâches, protéger les sauvegardes | Restaurer depuis des sauvegardes vérifiées antérieures à l'intrusion, surveillance renforcée |
| Application OAuth malveillante | Désactiver le principal de service, révoquer les octrois | Restreindre le consentement, flux d'approbation administrateur |
| Ressource cloud | NSG/JIT, révoquer clés et SAS, retirer les rôles | Faire tourner les secrets, Azure Policy, moindre privilège, identités managées |

## Fiche 7 — Différences entre produits Microsoft Security

| Produit | Nature | Ne pas confondre avec |
|----------------------|----------------------------|------------------------------------|
| Microsoft Sentinel | SIEM + SOAR (journaux de toutes sources, règles, automatisation, chasse) | Defender XDR (détection native sur les charges Microsoft) |
| Microsoft Defender XDR | XDR : corrélation et réponse sur terminaux, identités, messagerie, SaaS | Defender for Cloud (ressources cloud) |
| Microsoft Defender for Cloud | CNAPP : CSPM + protection des charges cloud (Azure, AWS, GCP, local via Arc) | Defender for Cloud Apps (CASB pour applications SaaS) |
| Microsoft Defender for Cloud Apps | CASB : Shadow IT, applications SaaS, sessions, OAuth | Defender for Cloud |
| Microsoft Entra ID / ID Protection | Identité, accès conditionnel, risques d'identité | Defender for Identity (Active Directory local) |
| Microsoft Defender for Identity | Détection des attaques sur AD local via capteurs | Entra ID Protection (identités cloud) |
| Microsoft Purview | Conformité et protection des données (audit, eDiscovery, DLP, risques internes) | Defender for Office 365 (menaces de messagerie) |
| Secure Score Microsoft vs Secure Score Defender for Cloud | Posture Microsoft 365/XDR vs posture des ressources cloud | — |

## Fiche 8 — Erreurs courantes des candidats

- Comparer `ResultType` de SigninLogs à un entier : c'est une **chaîne** (« 0 »).
- Utiliser `Timestamp` dans Sentinel/Log Analytics (ou `TimeGenerated` en pensant à la chasse avancée native) sans vérifier l'environnement.
- Oublier que `join` sans `kind` est **innerunique** ; que les clés de jointure sont sensibles à la casse.
- Grouper par une colonne **dynamique** sans `tostring()`.
- Confondre **règle d'automatisation** (Sentinel, incidents) et **playbook** (Logic App), ou **Playbook Operator** et **Automation Contributor**.
- Penser qu'une règle NRT accepte une fenêtre de recherche paramétrable, ou qu'une règle planifiée peut remonter au-delà de 14 jours.
- Croire que filtrer dans une requête réduit le coût d'ingestion (il faut une DCR / transformation).
- Confondre **suppression** d'une règle (pause après alerte) et **regroupement** d'alertes en incidents.
- Oublier la **révocation des sessions** après une réinitialisation de mot de passe ; oublier les consentements OAuth.
- Éteindre un poste compromis au lieu de l'**isoler** ; restaurer avant d'avoir éradiqué.
- Confondre **Defender for Cloud** et **Defender for Cloud Apps**, **Entra ID Protection** et **Defender for Identity**.
- Penser que le mode **Audit** des règles ASR protège.
- Croire que la chasse avancée conserve plus de **30 jours** de données XDR par défaut.

## Fiche 9 — Bonnes pratiques d'investigation SOC

- Commencer par la **portée** (qui, quoi, depuis quand) avant les actions destructrices ; préserver les preuves.
- Penser **entités** : un IOC trouvé = un pivot sur tout le parc et toutes les sources.
- Privilégier les **identifiants stables** (DeviceId, AccountObjectId, SID, NetworkMessageId) aux noms.
- Normaliser la casse et les types avant les jointures ; documenter les hypothèses de schéma.
- Distinguer **faux positif** et **vrai positif bénin** ; corriger les règles à la source (watchlists, exclusions ciblées).
- Automatiser ce qui est fiable et répétitif ; réserver les actions automatiques destructrices aux détections à très haute confiance.
- Appliquer le **moindre privilège** à l'équipe SOC elle-même (rôles Sentinel, URBAC, PIM).
- Communiquer hors bande lorsqu'une compromission de l'identité ou de la messagerie est possible.
- Clore avec une **classification**, une documentation complète et des actions d'amélioration.

## Fiche 10 — Points à réviser en priorité

1. Rôles Sentinel, autorisations des playbooks, RBAC unifié Defender.
2. Connecteurs : AMA/DCR, Windows Security Events, WEF, Syslog/CEF, diagnostic + Azure Policy, TI, Logs Ingestion API.
3. Niveaux de données et rétention : Analytics, data lake, XDR ; jobs KQL, règles de synthèse, jobs de recherche.
4. Règles d'analyse : planifiées vs NRT, paramètres (lookback, regroupement, suppression, entités), délai d'ingestion, anomalies, Fusion, TI, MITRE.
5. Règles d'automatisation et playbooks (déclencheurs, ordre, expiration, identité managée).
6. MDE : fonctionnalités avancées, ASR, groupes d'appareils, niveaux d'automatisation, indicateurs, actions de réponse, Live Response, collecte personnalisée, détections personnalisées (colonnes requises).
7. MDO, MDI, MDCA, Entra ID Protection, Purview : principales alertes et actions de remédiation.
8. Interruption automatique d'attaque : prérequis, comportement, exclusions.
9. KQL : `where`, `summarize`, `join` (saveurs), `mv-expand`, `arg_max`, `make_set`, `parse`/`extract`, types dynamiques, `bin`, `let`.
10. Defender for Cloud : plans, recommandations vs alertes, JIT, intégration XDR et Sentinel.

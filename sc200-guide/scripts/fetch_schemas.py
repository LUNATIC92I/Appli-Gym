#!/usr/bin/env python3
"""Télécharge les schémas officiels des tables (Microsoft Learn) utilisés pour valider le KQL.

Produit content/schemas.json :
  {"sentinel": {Table: [[colonne, type], ...]}, "xdr": {...}}
- « sentinel » : référence des tables Azure Monitor / Log Analytics (Microsoft Sentinel).
- « xdr »      : schéma de la chasse avancée Microsoft Defender XDR.
"""
import concurrent.futures
import html
import json
import pathlib
import re
import urllib.request

ROOT = pathlib.Path(__file__).resolve().parent.parent
OUT = ROOT / "content" / "schemas.json"

XDR = """DeviceProcessEvents DeviceNetworkEvents DeviceFileEvents DeviceLogonEvents DeviceRegistryEvents
DeviceEvents DeviceInfo DeviceImageLoadEvents DeviceNetworkInfo DeviceFileCertificateInfo
DeviceTvmSoftwareVulnerabilities EmailEvents EmailAttachmentInfo EmailUrlInfo EmailPostDeliveryEvents
UrlClickEvents IdentityLogonEvents IdentityQueryEvents IdentityDirectoryEvents IdentityInfo
CloudAppEvents AlertInfo AlertEvidence EntraIdSignInEvents GraphApiAuditEvents BehaviorInfo BehaviorEntities""".split()

LA = """SigninLogs AADNonInteractiveUserSignInLogs AADServicePrincipalSignInLogs AADManagedIdentitySignInLogs AuditLogs
SecurityEvent Syslog CommonSecurityLog AzureActivity OfficeActivity SecurityAlert SecurityIncident
BehaviorAnalytics ThreatIntelIndicators ThreatIntelligenceIndicator AADUserRiskEvents AADRiskyUsers
MicrosoftGraphActivityLogs Event Heartbeat StorageBlobLogs AzureDiagnostics Usage WindowsEvent
SentinelHealth Anomalies IdentityInfo DnsEvents AZFWNetworkRule AZFWApplicationRule LAQueryLogs SentinelAudit
Watchlist SecurityRecommendation DeviceProcessEvents DeviceNetworkEvents DeviceFileEvents DeviceLogonEvents
DeviceRegistryEvents DeviceEvents DeviceInfo EmailEvents EmailAttachmentInfo EmailUrlInfo UrlClickEvents
IdentityLogonEvents IdentityQueryEvents IdentityDirectoryEvents CloudAppEvents AlertInfo AlertEvidence
KubeAuditLogs AzureMetrics AWSCloudTrail ASimAuthenticationEventLogs SecurityCaseEvent""".split()

TYPES = {"string": "string", "datetime": "datetime", "int": "int", "long": "long", "bool": "bool",
         "boolean": "bool", "dynamic": "dynamic", "real": "real", "double": "real", "guid": "guid",
         "timespan": "timespan", "decimal": "decimal", "int64": "long", "int32": "int"}


def get(url):
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
    with urllib.request.urlopen(req, timeout=45) as r:
        return r.read().decode("utf8", "replace")


def parse(body):
    cols = []
    for tbl in re.findall(r"<table.*?</table>", body, re.S):
        rows = re.findall(r"<tr.*?</tr>", tbl, re.S)
        if not rows:
            continue
        head = [html.unescape(re.sub("<[^>]+>", "", c)).strip().lower() for c in re.findall(r"<t[hd][^>]*>(.*?)</t[hd]>", rows[0], re.S)]
        if not head or not any("type" in h for h in head) or not any(h.startswith("column") for h in head):
            continue
        ci = next(i for i, h in enumerate(head) if h.startswith("column"))
        ti = next(i for i, h in enumerate(head) if "type" in h)
        for r in rows[1:]:
            cells = [html.unescape(re.sub("<[^>]+>", "", c)).strip() for c in re.findall(r"<td[^>]*>(.*?)</td>", r, re.S)]
            if len(cells) <= max(ci, ti):
                continue
            name = cells[ci].strip("`* ")
            typ = TYPES.get(cells[ti].strip("` ").lower())
            if name and typ and re.match(r"^[A-Za-z_][A-Za-z0-9_]*$", name):
                cols.append([name, typ])
    seen, out = set(), []
    for n, t in cols:
        if n not in seen:
            seen.add(n)
            out.append([n, t])
    return out


def xdr(t):
    try:
        return "xdr", t, parse(get(f"https://learn.microsoft.com/en-us/defender-xdr/advanced-hunting-{t.lower()}-table"))
    except Exception as e:  # noqa: BLE001
        return "xdr", t, str(e)


def la(t):
    try:
        return "sentinel", t, parse(get(f"https://learn.microsoft.com/en-us/azure/azure-monitor/reference/tables/{t.lower()}"))
    except Exception as e:  # noqa: BLE001
        return "sentinel", t, str(e)


def main():
    jobs = [(xdr, t) for t in XDR] + [(la, t) for t in LA]
    out = {"sentinel": {}, "xdr": {}}
    with concurrent.futures.ThreadPoolExecutor(12) as ex:
        for env, t, cols in ex.map(lambda j: j[0](j[1]), jobs):
            if isinstance(cols, str) or not cols:
                print("ÉCHEC", env, t, cols if isinstance(cols, str) else "aucune colonne")
                continue
            out[env][t] = cols
    OUT.write_text(json.dumps(out, indent=0), encoding="utf8")
    print({k: len(v) for k, v in out.items()})


if __name__ == "__main__":
    main()

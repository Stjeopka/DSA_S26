# DSA – Datenstrukturen und Algorithmen

Workspace für die Übungsaufgaben in DSA (SS 2026, Uni Stuttgart).

## Voraussetzungen

- [VS Code](https://code.visualstudio.com/) mit der Extension **Dev Containers** (`ms-vscode-remote.remote-containers`)
- Docker (lokal oder auf einem Remote-Server)

## Einrichtung

1. Repository klonen:
   ```bash
   git clone <repo-url> && cd dsa
   ```
2. In VS Code öffnen und bei der Aufforderung **"Reopen in Container"** klicken (oder `Ctrl+Shift+P` → *Dev Containers: Reopen in Container*).
3. Der Container installiert automatisch Java 17, Maven, fzf und yq.

## Teammitglieder eintragen

Öffne `co-authers.yaml` und trage alle Gruppenmitglieder ein:

```yaml
members:
  - matrikelnummer: "1234567"
    firstname: "Max"
    lastname: "Mustermann"
  - matrikelnummer: "7654321"
    firstname: "Erika"
    lastname: "Musterfrau"
```

Die Nachnamen werden automatisch für den ZIP-Dateinamen bei der Abgabe verwendet.

## Projektstruktur

Jede Übung liegt in einem eigenen Ordner:

```
Ex00_LastNamesOfMembers_project/
    pom.xml
    src/main/java/...    ← Implementierung
    src/test/java/...    ← Tests
Ex01_..._project/
    ...
```

## Kompilieren & Testen

### Im Terminal

```bash
cd Ex00_LastNamesOfMembers_project
mvn compile    # Kompilieren
mvn test       # Tests ausführen
```

### Über VS Code Tasks

`Ctrl+Shift+P` → **"Run Task"** → eine der folgenden Optionen wählen:

| Task | Beschreibung |
|------|-------------|
| **Maven: Compile (select project)** | Projekt aus Liste wählen, dann kompilieren |
| **Maven: Test (select project)** | Projekt aus Liste wählen, dann testen |
| **Maven: Compile Current Project** | Kompiliert das Projekt der aktuell geöffneten Datei |
| **Submit: Test & Zip (select project)** | Kompilieren + Testen + ZIP erstellen (siehe Abgabe) |

### Statusleiste (unten)

Die drei wichtigsten Tasks sind auch als Buttons in der **Statusleiste** am unteren Rand von VS Code verfügbar:

| Button | Funktion |
|--------|----------|
| **Compile** | Projekt wählen und kompilieren |
| **Test** | Projekt wählen und testen |
| **Submit** | Kompilieren + Testen + ZIP erstellen |

Einfach anklicken – kein Shortcut nötig.

### Tests in der Sidebar

Die grünen Play-Buttons neben `@Test`-Methoden und der Beaker-Icon in der linken Sidebar funktionieren, sobald der Java Language Server geladen ist (Status unten links: *Java: Ready*).

## Abgabe

Das Skript `submit.sh` erledigt alles in einem Schritt:

1. **Kompiliert** das Projekt
2. **Führt alle Tests** aus
3. Nur bei Erfolg: erstellt eine **ZIP-Datei** im Format `Ex00_MustermanMusterfrau_project.zip`

### Ausführen

**Option A** – VS Code Task:

`Ctrl+Shift+P` → "Run Task" → **"Submit: Test & Zip (select project)"**

**Option B** – Terminal:

```bash
# Interaktiv (Projekt wählen)
./submit.sh

# Direkt
./submit.sh Ex00_LastNamesOfMembers_project
```

### Ergebnis

Die ZIP-Datei wird im Ordner `submissions/` abgelegt (wird nicht mit Git hochgeladen):

```
submissions/
└── Ex00_MustermanMusterfrau_project.zip
    └── Ex00_MustermanMusterfrau_project/
        ├── pom.xml
        └── src/
```

Der Prof kann die ZIP entpacken und sofort `mvn compile && mvn test` ausführen.

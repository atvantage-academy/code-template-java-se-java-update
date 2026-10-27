# Übungsprojekt · Java-Update

Dieses Repository ist das Übungsprojekt der Schulung **„Java-Update: Neuerungen von
Java 9 bis 25“** der ATVANTAGE Academy. Die Übungen selbst stehen in den
[Lernenden-Unterlagen](https://atvantage-academy.github.io/training-material-java-se/java-update/).

Auf `main` liegt der **Startstand**: je Übung ein eigenes Modul mit eigener `pom.xml`,
geschrieben wie mit Java 8. Jedes Modul startet auf Java 8; in der Übung stellst Du sein
Release hoch – unabhängig von allen anderen. Was mehrere Übungen brauchen, liegt in jeder
davon noch einmal: Die Bestellabwicklung steckt in den Schwerpunkten Virtual Threads und
JVM, damit eine Übung die andere nicht verändert.

## Was hier liegt

| Ordner | Inhalt |
|--------|--------|
| [`ueberblick/`](ueberblick) | `plattform-und-release-modell/` startet ein erstes Programm direkt aus dem Quelltext (`Versionsinfo.java`). `werkzeuge/` lässt `jdeps` und `jdeprscan` über alle Module der Inkompatibilitäten laufen – und über eigene Klassen mit der internen API `sun.misc.Unsafe` und mit abgekündigten APIs (`new Integer(…)`, `finalize()`, `Thread.stop()`). |
| [`inkompatibilitaeten/`](inkompatibilitaeten) | **Je Bruchstelle ein Modul**, getrennt nach [`bauzeit/`](inkompatibilitaeten/bauzeit) – der Compiler meldet den Bruch, sobald Du das Release hochstellst – und [`laufzeit/`](inkompatibilitaeten/laufzeit) – der Bruch zeigt sich erst auf dem neuen JDK. Jedes Modul startet auf Java 8 und läuft dort. Dazu `laufzeit/toolchain`: prüft, ob Deine Toolchain für JDK 8 stimmt. |
| [`neuerungen/`](neuerungen) | **Je Neuerung ein Modul**, getrennt nach [`syntax/`](neuerungen/syntax) und [`api/`](neuerungen/api). Darin eine Klasse im Stil von Java 8 mit ihrem Test. Kommentare `// Umbauen: …` markieren die Stellen, die Du mit der Neuerung umbaust, und sagen, warum. |
| [`schwerpunkte/`](schwerpunkte) | **Je Schwerpunktthema ein Modul.** `modulsystem/` ist ein Export von Bestellungen in drei Teilen – Modell, CSV-Format und Anwendung –, die Du zu Java-Modulen machst. `virtual-threads/` ist die **Bestellabwicklung** auf Java 8: Domäne, eine REST-Schnittstelle in Schichten und ein Batch-Import. `jvm-fuer-entwickler/` bringt dieselbe REST-Schnittstelle als startbares JAR mit und zwei Programme für den Garbage Collector und das Pinning. `valhalla/` sammelt Stellen, die auf die Identität von `Integer` und Co. setzen. |
| [`.run/`](.run), [`eclipse/`](eclipse), [`.vscode/`](.vscode) | Fertige Run Configurations für IntelliJ IDEA, Eclipse und VS Code. |

## Voraussetzungen

- **JDK 21 oder JDK 25** (25 empfohlen).
- **Maven 3.9 oder neuer**, installiert und im Pfad (`mvn -v`). Das Projekt bringt
  keinen Maven Wrapper mit.
- Eine IDE, die Maven-Projekte importiert – einfach die `pom.xml` im Wurzelordner
  öffnen.
- Optional ein **JDK 8**, um den Stand vor dem Umstieg selbst zu sehen (siehe unten).

## Loslegen

Baue das ganze Projekt auf einmal, ohne bei Testfehlern abzubrechen:

```shell
mvn verify -fae -Dmaven.test.failure.ignore=true
```

Auf JDK 21 oder 25 schlagen dabei Tests in `inkompatibilitaeten/` fehl. **Das ist
Absicht** – um diese Brüche geht es in den Übungen.

## In der IDE

Für jedes Übungsmodul gibt es eine Run Configuration, die es baut und testet
(`clean verify`), benannt wie das Modul: vorn die Gruppe (`bauzeit`, `laufzeit`,
`syntax`, `api`), dahinter der Ordner – etwa `laufzeit-jaxb` für
`inkompatibilitaeten/laufzeit/jaxb`. Die
Run Configuration `java-update` baut das ganze Projekt, ohne bei Testfehlern
abzubrechen. `ueberblick-werkzeuge-jdeps` und `ueberblick-werkzeuge-jdeprscan` lassen die beiden
Werkzeuge des JDK über die übersetzten Klassen aller Module der Inkompatibilitäten laufen.
Zu den Brüchen zur Laufzeit gibt es eine zweite mit `-jdk8` am Ende – etwa
`laufzeit-reflection-jdk8` –, die die Tests auf JDK 8 laufen lässt (siehe
[Der Stand vorher, mit JDK 8](#der-stand-vorher-mit-jdk-8)). Wo eine Übung einen
Kompatibilitätsschalter ausprobiert, gibt es eine Run Configuration mit dem Schalter am
Ende – `laufzeit-datumsformat-compat`, `laufzeit-reflection-add-opens`,
`laufzeit-security-manager-allow`, `laufzeit-zeichensatz-compat`.
`syntax-markdown-javadoc-doku` erzeugt zusätzlich die Dokumentation des Moduls,
`syntax-kompakte-quelldateien-start` startet das Werkzeug `Lagerliste`. Zum Modulsystem
bauen `schwerpunkte-modulsystem-…` die drei Teile und führen danach je einen Befehl der
Übung aus: `-klassenpfad` und `-modulpfad-automatisch` starten den `Exportlauf` auf dem
Klassen- bzw. Modulpfad, solange die JARs noch keine `module-info` haben,
`-beschreiben` beschreibt das Modul der Anwendung, `-modulpfad` startet sie unter
ihrem eigenen Modulnamen, `-image` und `-image-mit-csv` bauen mit `jlink` ein eigenes
Laufzeit-Image; gestartet wird es über sein Startskript auf der Kommandozeile.
Zu Virtual Threads startet `schwerpunkte-virtual-threads-rest-starten` die
REST-Schnittstelle. Gemessen wird je JDK: `schwerpunkte-virtual-threads-batch-messen-jdk8`,
`-jdk21` und `-jdk25` bauen den Batch-Import und starten ihn mit Zeitmessung,
`schwerpunkte-virtual-threads-rest-messen-jdk8`, `-jdk21` und `-jdk25` ebenso den
Lasttest gegen die REST-Schnittstelle (siehe [Messen mit einem bestimmten
JDK](#messen-mit-einem-bestimmten-jdk)).

**Programme, die direkt aus dem Quelltext starten** (`java Datei.java`), liegen im
Ordner ihres Moduls, nicht unter `src/` – Maven übersetzt sie nicht, weil manche ein
neueres JDK brauchen als Java 8. Für jeden Befehl, den eine Übung damit ausführt, gibt es
ein Maven-Profil im Modul und eine Run Configuration mit dem Profil am Ende, etwa
`ueberblick-plattform-und-release-modell-versionsinfo`, `schwerpunkte-valhalla-diagnose`
oder `schwerpunkte-jvm-fuer-entwickler-gc-z`. Sie laufen im Ordner des Moduls, mit dem
JDK, mit dem Maven läuft.

- **IntelliJ IDEA** liest die Dateien in [`.run/`](.run) beim Öffnen des Projekts;
  Du findest sie oben rechts in der Auswahl neben dem Startknopf.
- **Eclipse** findet die Dateien in [`eclipse/`](eclipse), sobald Du das Projekt über
  *File → Import → Existing Maven Projects* importiert hast – samt dem Wurzelprojekt
  `java-update`. Du startest sie über *Run → Run Configurations…*.
- **VS Code** braucht das *Extension Pack for Java*; öffne den Wurzelordner über
  *File → Open Folder…*. Dieselben Run Configurations liegen in [`.vscode/`](.vscode):
  Alles, was Maven aufruft, ist ein Task in `tasks.json` – Du startest ihn über
  *Terminal → Run Task…*. Die Programme, die VS Code direkt startet
  (`schwerpunkte-virtual-threads-…`), stehen in `launch.json` unter *Run and Debug*.
  Maven nimmt dabei das JDK, das `mvn -v` im Terminal nennt.

## Das Release umstellen

Für welche Java-Version übersetzt wird, steht in der Standard-Property
**`maven.compiler.release`** – im Wurzel-`pom.xml` für alle und in jedem Übungsmodul
noch einmal für dieses Modul (für die Bestellabwicklung einmal in
`schwerpunkte/virtual-threads/pom.xml`). Zum
Hochstellen änderst Du den Wert in der `pom.xml` des Moduls, so wie bei einer echten
Migration.

Baue danach das Modul neu, zum Beispiel das Modul `inkompatibilitaeten/laufzeit/jaxb`:

```shell
mvn clean verify -pl inkompatibilitaeten/laufzeit/jaxb -am
```

**Immer mit `clean`.** Ändert sich nur das Release, hält Maven die übersetzten
Klassen für aktuell und übersetzt nicht neu – der Bruch bliebe unsichtbar.

Ein JDK übersetzt nur für Releases bis zu seiner eigenen Version: Mit JDK 21 geht
`--release 25` nicht.

## Der Stand vorher, mit JDK 8

`--release 8` legt nur fest, gegen welche Sprache und API übersetzt wird. Laufen
tut der Code auf Deinem JDK – deshalb treten die Brüche zur Laufzeit schon auf, bevor
Du etwas umstellst. Wer sie vorher grün sehen will, braucht ein JDK 8. Wie die Tests
dort ausgehen, steht auch in der Übung; das JDK 8 ist kein Muss.

Mit JDK 8 trägst Du es in `~/.m2/toolchains.xml` ein (siehe
[Maven: Toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html)):

```xml
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>1.8</version>
    </provides>
    <configuration>
      <jdkHome>/pfad/zum/jdk8</jdkHome>
    </configuration>
  </toolchain>
</toolchains>
```

Ob die Einrichtung stimmt, prüft das Modul `inkompatibilitaeten/laufzeit/toolchain`: Sein Test ist
nur grün, wenn er wirklich auf JDK 8 läuft (Run Configuration `laufzeit-toolchain`):

```shell
mvn clean verify -pl inkompatibilitaeten/laufzeit/toolchain -am -Pjdk8
```

Baue dann mit dem Profil `jdk8` – Maven übersetzt weiter mit Deinem JDK, die Tests
laufen auf JDK 8:

```shell
mvn clean verify -pl inkompatibilitaeten/laufzeit/jaxb -am -Pjdk8
```

In der IDE startest Du dafür die Run Configuration mit `-jdk8` am Ende, etwa
`laufzeit-jaxb-jdk8`. IntelliJ IDEA und Eclipse lesen `~/.m2/toolchains.xml`
wie Maven auf der Kommandozeile.

## Messen mit einem bestimmten JDK

Für die Übung zu Virtual Threads misst Du denselben Code auf JDK 8, 21 und – optional –
25. Trag dafür, wie oben für JDK 8, auch JDK 21 und 25 in `~/.m2/toolchains.xml` ein
(`<version>21</version>` bzw. `<version>25</version>`). Die Profile `jdk21` und `jdk25`
lassen dann Übersetzen, Tests und Programme auf diesem JDK laufen statt auf dem, mit
dem Maven läuft. Das Profil `messen` startet nach dem Bauen die Messung des Moduls:

```shell
mvn clean package -pl schwerpunkte/virtual-threads/bestellung-batch -am -Pjdk21,messen
```

`-am` baut die Domäne mit, ein `mvn install` vorher ist nicht nötig. Für die
REST-Schnittstelle steht `bestellung-rest` statt `bestellung-batch`. Steht das Release
höher als das JDK – etwa 25 auf JDK 21 –, überschreibst Du es für diesen Aufruf mit
`-Dmaven.compiler.release=21`.

## Die Lösungen

Zu jeder Übung gibt es einen Branch `loesung/«übung»`. Er zweigt von `main` ab und
trägt den Stand **nach** dieser Übung; sein Pull Request zeigt im Diff genau, was die
Übung verändert. Die Übungen hängen nicht voneinander ab – jede startet auf `main`.

➜ [Alle Lösungen ansehen](https://atvantage-academy.github.io/code-template-java-se-java-update/) –
auf einer Seite zum Durchklicken: Musterlösung wählen, geänderte Dateien ansehen, je
Datei die Änderung oder den kompletten Stand. Dieselben Lösungen als Pull Requests:
[auf GitHub](https://github.com/atvantage-academy/code-template-java-se-java-update/pulls).

**Sieh erst nach, wenn Du es selbst versucht hast.** Der Wert einer Übung liegt im
Suchen, nicht im Ergebnis.

## Die Pipeline

[`build.yml`](.github/workflows/build.yml) prüft auf `main` dreierlei:

- **Startstand auf Java 8** – das ganze Projekt auf JDK 8. Muss grün sein: So lief
  der Code, bevor wir ihn angefasst haben.
- **Stand vorher über Toolchains** – dasselbe mit dem Profil `jdk8`: Maven auf
  JDK 21, die Tests auf JDK 8.
- **Bestandsaufnahme auf JDK 21 und 25** – derselbe Code auf den neuen JDKs.
  Fehlschlagende Tests brechen den Lauf nicht ab; welche es sind, steht in der
  Zusammenfassung des Laufs.

Auf einem Lösungsbranch `loesung/«modul»` laufen diese drei nicht. Dort prüft
stattdessen **Lösung auf JDK 21 und 25** genau das Modul der Übung – streng, jeder
Testfehler ist ein Fehler.

[`pages.yml`](.github/workflows/pages.yml) erzeugt die Seite mit den Lösungen und
veröffentlicht sie auf GitHub Pages – bei jedem Push auf `main` und bei jeder Änderung
an einem Pull Request. Wie man sie lokal erzeugt, steht in
[`.github/pages/README-local.md`](.github/pages/README-local.md).

## Gut zu wissen

**Eine Warnung zu `sun.misc.Unsafe` beim Start von Maven** auf JDK 24 oder neuer
kommt nicht aus diesem Projekt, sondern aus einer Bibliothek in Maven selbst – bei
Maven bis 3.9.11. Sie ist ein Beispiel für das, worum es in diesem Kurs geht: Das JDK
kündigt an, dass ein interner Zugriff bald nicht mehr erlaubt ist (JEP 498).

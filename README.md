# Übungsprojekt · Java-Update

Dieses Repository ist das Übungsprojekt der Schulung **„Java-Update: Neuerungen von
Java 9 bis 25“** der ATVANTAGE Academy. Die Übungen selbst stehen in den
[Lernenden-Unterlagen](https://atvantage-academy.github.io/training-material-java-se/java-update/).

Auf `main` liegt der **Startstand**: eine Anwendung, wie sie mit Java 8 geschrieben
wurde und jahrelang lief, und je Übung ein eigenes Modul. Jedes Modul startet auf
Java 8; in der Übung stellst Du sein Release hoch – unabhängig von allen anderen.

## Was hier liegt

| Ordner | Inhalt |
|--------|--------|
| [`altbestand/`](altbestand) | Die **Bestellabwicklung** auf Java 8: Domäne, eine REST-Schnittstelle in Schichten und ein Batch-Import. Daran probierst Du Virtual Threads und die Laufzeit der JVM aus. |
| [`kompatibilitaet/`](kompatibilitaet) | **Je Bruchstelle ein Modul.** Jedes startet auf Java 8 und läuft dort. Was beim Umstieg bricht, siehst Du, wenn Du das Release hochstellst oder den Code auf einem neuen JDK laufen lässt. |
| [`neuerungen/`](neuerungen) | **Je Neuerung in Syntax und API ein Modul**, mit einem kleinen Beispiel und seinem Test. Auch diese Module starten auf Java 8. |
| [`spielwiese/`](spielwiese) | Einzelne Programme zum Ausprobieren, ohne Build. |
| [`.run/`](.run), [`eclipse/`](eclipse) | Fertige Run Configurations für IntelliJ IDEA und Eclipse. |

## Voraussetzungen

- **JDK 21 oder JDK 25** (25 empfohlen).
- **Maven 3.9 oder neuer**, installiert und im Pfad (`mvn -v`). Das Projekt bringt
  keinen Maven Wrapper mit.
- Eine IDE, die Maven-Projekte importiert – einfach die `pom.xml` im Wurzelordner
  öffnen.
- Optional ein **JDK 8**, um den Stand vor dem Umstieg selbst zu sehen (siehe unten).

## Loslegen

Baue und teste den Altbestand:

```shell
mvn verify -f altbestand/pom.xml
```

Der Altbestand übersetzt und seine Tests laufen – auch auf Deinem neuen JDK.
Übersetzt wird er dabei für Java 8 (`--release 8`).

Baue das ganze Projekt auf einmal, ohne bei Testfehlern abzubrechen:

```shell
mvn verify -fae -Dmaven.test.failure.ignore=true
```

Auf JDK 21 oder 25 schlagen dabei Tests in `kompatibilitaet/` fehl. **Das ist
Absicht** – um diese Brüche geht es in den Übungen.

## In der IDE

Für jedes Übungsmodul gibt es eine Run Configuration, die es baut und testet
(`clean verify`), benannt wie das Modul – etwa `kompatibilitaet-jaxb`. Die
Run Configuration `java-update` baut das ganze Projekt, ohne bei Testfehlern
abzubrechen. Dazu kommen `RestAnwendung`, `BatchAnwendung` und `Lasttest` zum Starten.

- **IntelliJ IDEA** liest die Dateien in [`.run/`](.run) beim Öffnen des Projekts;
  Du findest sie oben rechts in der Auswahl neben dem Startknopf.
- **Eclipse** findet die Dateien in [`eclipse/`](eclipse), sobald Du das Projekt über
  *File → Import → Existing Maven Projects* importiert hast – samt dem Wurzelprojekt
  `java-update`. Du startest sie über *Run → Run Configurations…*.

## Das Release umstellen

Für welche Java-Version übersetzt wird, steht in der Standard-Property
**`maven.compiler.release`** – im Wurzel-`pom.xml` für alle und in jedem Übungsmodul
noch einmal für dieses Modul (für den Altbestand in `altbestand/pom.xml`). Zum
Hochstellen änderst Du den Wert in der `pom.xml` des Moduls, so wie bei einer echten
Migration.

Baue danach das Modul neu, zum Beispiel das Modul `kompatibilitaet/jaxb`:

```shell
mvn clean verify -pl kompatibilitaet/jaxb -am
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

Baue dann mit dem Profil `jdk8` – Maven übersetzt weiter mit Deinem JDK, die Tests
laufen auf JDK 8:

```shell
mvn clean verify -pl kompatibilitaet/jaxb -am -Pjdk8
```

## Die Lösungen

Zu jeder Übung gibt es einen Branch `loesung/«übung»`. Er zweigt von `main` ab und
trägt den Stand **nach** dieser Übung; sein Pull Request zeigt im Diff genau, was die
Übung verändert. Die Übungen hängen nicht voneinander ab – jede startet auf `main`.

➜ [Alle Lösungen ansehen](https://github.com/atvantage-academy/code-template-java-se-java-update/pulls)

**Sieh erst nach, wenn Du es selbst versucht hast.** Der Wert einer Übung liegt im
Suchen, nicht im Ergebnis.

## Die Pipeline

[`build.yml`](.github/workflows/build.yml) prüft auf `main` dreierlei:

- **Altbestand auf Java 8** – das ganze Projekt auf JDK 8. Muss grün sein: So lief
  der Code, bevor wir ihn angefasst haben.
- **Stand vorher über Toolchains** – dasselbe mit dem Profil `jdk8`: Maven auf
  JDK 21, die Tests auf JDK 8.
- **Bestandsaufnahme auf JDK 21 und 25** – derselbe Code auf den neuen JDKs.
  Fehlschlagende Tests brechen den Lauf nicht ab; welche es sind, steht in der
  Zusammenfassung des Laufs.

Auf einem Lösungsbranch `loesung/«modul»` laufen diese drei nicht. Dort prüft
stattdessen **Lösung auf JDK 21 und 25** genau das Modul der Übung – streng, jeder
Testfehler ist ein Fehler.

## Gut zu wissen

**Eine Warnung zu `sun.misc.Unsafe` beim Start von Maven** auf JDK 24 oder neuer
kommt nicht aus diesem Projekt, sondern aus einer Bibliothek in Maven selbst – bei
Maven bis 3.9.11. Sie ist ein Beispiel für das, worum es in diesem Kurs geht: Das JDK
kündigt an, dass ein interner Zugriff bald nicht mehr erlaubt ist (JEP 498).

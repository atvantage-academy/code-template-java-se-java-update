# Übungsprojekt · Java-Update

Dieses Repository ist das Übungsprojekt der Schulung **„Java-Update: Neuerungen von
Java 9 bis 25“** der ATVANTAGE Academy. Die Übungen selbst stehen in den
[Lernenden-Unterlagen](https://atvantage-academy.github.io/training-material-java-se/java-update/).

Auf `main` liegt der **Startstand**: eine Anwendung, wie sie mit Java 8 geschrieben
wurde und jahrelang lief. Im Kurs hebst Du sie Schritt für Schritt auf ein aktuelles
Release.

## Was hier liegt

| Ordner | Inhalt |
|--------|--------|
| [`altbestand/`](altbestand) | Die **Bestellabwicklung** auf Java 8: Domäne, eine REST-Schnittstelle in Schichten und ein Batch-Import. Daran probierst Du die neuen Sprachmittel, APIs und Virtual Threads aus. |
| [`kompatibilitaet/`](kompatibilitaet) | **Je Bruchstelle ein Modul.** Jedes startet auf Java 8 und läuft dort. Was beim Umstieg bricht, siehst Du, wenn Du das Release hochstellst oder den Code auf einem neuen JDK laufen lässt. |
| [`vorlagen/`](vorlagen) | Das Gerüst für die Übung **„Bau Dein eigenes `STR`“**: Vorlagen, wie sie die String Templates aus Java 21 kannten, und zwei Prozessoren, die Du selbst baust. Die Tests sind bis dahin abgeschaltet. |
| [`spielwiese/`](spielwiese) | Einzelne Programme zum Ausprobieren, ohne Build. |

## Voraussetzungen

- **JDK 21 oder JDK 25** (25 empfohlen). Maven brauchst Du nicht: Der Maven Wrapper
  (`./mvnw`, unter Windows `mvnw.cmd`) lädt die passende Version beim ersten Aufruf.
- Eine IDE, die Maven-Projekte importiert – einfach die `pom.xml` im Wurzelordner
  öffnen.

## Loslegen

```shell
./mvnw verify -f altbestand/pom.xml
```

Der Altbestand übersetzt und seine Tests laufen – auch auf Deinem neuen JDK.
Übersetzt wird er dabei für Java 8 (`--release 8`).

Das ganze Projekt auf einmal:

```shell
./mvnw verify -fae -Dmaven.test.failure.ignore=true
```

Auf JDK 21 oder 25 schlagen dabei Tests in `kompatibilitaet/` fehl. **Das ist
Absicht** – um diese Brüche geht es in der Übung.

## Das Release umstellen

Für welche Java-Version übersetzt wird, steht in der Standard-Property
**`maven.compiler.release`**: im Wurzel-`pom.xml` für alle, in jedem
Kompatibilitätsmodul noch einmal für dieses Modul. Zum Hochstellen änderst Du den
Wert in der `pom.xml` des Moduls. Für einen schnellen Versuch geht es auch auf der
Kommandozeile:

```shell
./mvnw clean verify -pl kompatibilitaet/jaxb -Dmaven.compiler.release=11
```

**Immer mit `clean`.** Ändert sich nur das Release, hält Maven die übersetzten
Klassen für aktuell und übersetzt nicht neu – der Bruch bliebe unsichtbar.

Ein JDK übersetzt nur für Releases bis zu seiner eigenen Version: Mit JDK 21 geht
`--release 25` nicht.

## Die Lösungen

Zu jeder Übung gibt es einen Branch `loesung/«übung»` mit dem Stand **nach** dieser
Übung, und einen Pull Request, dessen Diff genau zeigt, was die Übung verändert.

➜ [Alle Lösungen ansehen](https://github.com/atvantage-academy/code-template-java-se-java-update/pulls)

**Sieh erst nach, wenn Du es selbst versucht hast.** Der Wert einer Übung liegt im
Suchen, nicht im Ergebnis.

## Die Pipeline

[`build.yml`](.github/workflows/build.yml) prüft zweierlei:

- **Altbestand auf Java 8** – das ganze Projekt auf JDK 8. Muss grün sein: So lief
  der Code, bevor wir ihn angefasst haben.
- **Bestandsaufnahme auf JDK 21 und 25** – derselbe Code auf den neuen JDKs.
  Fehlschlagende Tests brechen den Lauf nicht ab; welche es sind, steht in der
  Zusammenfassung des Laufs.

## Gut zu wissen

**Die Warnung zu `sun.misc.Unsafe` beim Start von Maven** auf JDK 24 oder neuer
kommt nicht aus diesem Projekt, sondern aus einer Bibliothek in Maven selbst. Sie
ist ein Beispiel für das, worum es in diesem Kurs geht: Das JDK kündigt an, dass
ein interner Zugriff bald nicht mehr erlaubt ist (JEP 498).

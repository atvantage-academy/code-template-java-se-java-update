# Kompatibilität · die Brüche und ihre Behebung

Musterlösung zur Übung „Kompatibilität“. Jedes Modul lief auf Java 8 und bricht genau
einmal: beim Übersetzen, wenn Du das Release hochstellst, oder zur Laufzeit auf einem
neuen JDK. In diesem Stand sind alle Module auf `--release 21` gehoben und auf JDK 21
und 25 grün.

Die Meldungen unten sind auf Temurin 21 und 25 gemessen.

## Auf einen Blick

| Modul | Bricht | ab | Ursache | Behebung in diesem Stand |
|-------|--------|----|---------|--------------------------|
| [`unterstrich`](unterstrich) | beim Übersetzen | 9 | `_` ist ein Schlüsselwort | umbenannt |
| [`eingeschraenkte-bezeichner`](eingeschraenkte-bezeichner) | beim Übersetzen | 14 | `yield` gehört zur Switch-Expression | Methode umbenannt |
| [`jaxb`](jaxb) | beim Übersetzen und zur Laufzeit | 11 | JAXB ist nicht mehr im JDK (JEP 320) | Jakarta XML Binding als Abhängigkeit |
| [`reflection`](reflection) | zur Laufzeit | 16 | starke Kapselung der JDK-Interna (JEP 396/403) | **Notlösung** `--add-opens` |
| [`zeichensatz`](zeichensatz) | zur Laufzeit | 18 | Standard-Zeichensatz ist UTF-8 (JEP 400) | Zeichensatz ausdrücklich angegeben |
| [`datumsformat`](datumsformat) | zur Laufzeit | 9 | Locale-Daten aus dem CLDR (JEP 252) | Format im eigenen Code festgelegt |
| [`zeitstempel`](zeitstempel) | zur Laufzeit | 15 | `Instant.now()` ist genauer | Uhr mit Millisekunden-Takt |
| [`sequenced`](sequenced) | beim Übersetzen | 21 | `List` hat jetzt `getFirst()`/`getLast()` (JEP 431) | Komposition statt Vererbung |
| [`security-manager`](security-manager) | zur Laufzeit | 18 (24 endgültig) | Security Manager abgeschaltet (JEP 411/486) | Plugin in eigenem Prozess |

## Die Werkzeuge vorab

Zwei der Laufzeitbrüche hätte ein Werkzeug gefunden, bevor ein Test läuft:

```shell
jdeprscan --release 21 --for-removal kompatibilitaet/security-manager/target/classes
jdeps -verbose:package kompatibilitaet/jaxb/target/classes | grep "not found"
```

- **`jdeprscan`** meldet alles, was zur Entfernung vorgesehen ist – hier den
  `SecurityManager`.
- **`jdeps`** meldet Pakete, die es im Ziel-JDK nicht gibt – hier `javax.xml.bind`.
- **`javac` mit `-Xlint:all`** kündigt an, was als Nächstes verschwindet. Beim
  Hochstellen des Altbestands auf 21 meldete es `new URL(String)` als veraltet (seit
  Java 20); dieser Stand nutzt `URI.create(…).toURL()`.

Den Zugriff per Reflection findet **keines** dieser Werkzeuge: `jdeps --jdk-internals`
sieht nur, was im Bytecode steht, nicht den Feldnamen in einem String. Solche Brüche
zeigen erst die Tests.

## Die Brüche im Einzelnen

### `unterstrich`

- **Symptom:** `as of release 9, '_' is a keyword, and may not be used as an identifier`;
  beim `catch (NumberFormatException _)` unter JDK 21
  `unnamed variables are a preview feature and are disabled by default`.
- **Ursache:** `_` ist seit Java 9 reserviert. Seit Java 22 (LTS 25) ist es die
  **unbenannte Variable** (JEP 456): Ein `catch (… _)` ist dann wieder gültig, ein
  **Zugriff** auf `_` nie mehr.
- **Behebung:** umbenennen.
- **Notlösung:** keine – außer beim alten Release zu bleiben.

### `eingeschraenkte-bezeichner`

- **Symptom:** `yield outside of switch expression` – beim **Aufruf** der eigenen
  Methode `yield(…)`, nicht bei ihrer Deklaration.
- **Ursache:** Seit Java 14 liefert `yield` den Wert einer Switch-Expression. Ein
  unqualifizierter Aufruf `yield(x)` liest sich für den Compiler als diese Anweisung.
- **Behebung:** Methode in `liefere(…)` umbenannt. Kleiner, aber unschöner:
  `this.yield(…)` qualifiziert den Aufruf.
- **Kein Bruch:** `var`, `record` und `yield` als **Variablennamen** in `Protokoll`
  übersetzen weiterhin. Eingeschränkte Bezeichner sind keine Schlüsselwörter; nur als
  **Typname** sind `var` und `record` verboten.

### `jaxb`

- **Symptom:** beim Übersetzen `package javax.xml.bind does not exist`; zur Laufzeit
  mit altem Release `NoClassDefFoundError: javax/xml/bind/JAXB`.
- **Ursache:** Java 11 hat die Java-EE- und CORBA-Module entfernt (JEP 320): JAXB,
  JAX-WS, JAF, Common Annotations.
- **Behebung:** Jakarta XML Binding 4 als Abhängigkeit (API plus Implementierung zur
  Laufzeit), Importe auf `jakarta.xml.bind`.
- **Gut zu wissen:** Der Wechsel von `javax` auf `jakarta` ist **keine Änderung des
  JDK**, sondern der Umzug der Spezifikation zur Eclipse Foundation. Wer beim alten
  Namensraum bleiben muss, nimmt `javax.xml.bind:jaxb-api` 2.3 – eine Sackgasse. Auf
  einem Jakarta-EE-Server und in Quarkus bringt die Plattform die Implementierung mit.

### `reflection`

- **Symptom:** `InaccessibleObjectException: Unable to make field transient
  java.lang.Object[] java.util.ArrayList.elementData accessible: module java.base does
  not "opens java.util" to unnamed module`.
- **Ursache:** Seit Java 16 sind die Interna des JDK standardmäßig gekapselt (JEP 396),
  seit 17 gibt es keinen Schalter mehr, der alles auf einmal öffnet (JEP 403).
- **Notlösung in diesem Stand:** `--add-opens java.base/java.util=ALL-UNNAMED` im
  Surefire-`argLine` und als `Add-Opens` im Manifest. Sie muss **überall** stehen, wo
  der Code läuft – Test, Startskript, Container.
- **Behebung an der Ursache:** Es gibt keine öffentliche API für die Kapazität einer
  `ArrayList`. Die Frage „wie viel Speicher ist reserviert?“ beantworten ein
  Heap-Histogramm (`jcmd <pid> GC.class_histogram`) oder Java Flight Recorder – nicht
  der Anwendungscode. In echten Projekten steckt dieser Bruch fast immer in einer
  **Bibliothek** (alte Versionen von Lombok, Mockito, ByteBuddy, Hibernate, XStream);
  dort heißt die Behebung: aktualisieren.

### `zeichensatz`

- **Symptom:** `expected: <Jana Müller> but was: <Jana M�ller>`.
- **Ursache:** Bis Java 17 kam der Standard-Zeichensatz vom Betriebssystem – auf einem
  deutschen Windows `windows-1252`. Seit Java 18 ist er überall UTF-8 (JEP 400). Unter
  Linux und macOS war er schon vorher UTF-8; deshalb stellt ein Maven-Profil im Modul das
  Windows-Altsystem bis JDK 17 nach.
- **Behebung:** Der Zeichensatz der Datei steht im Code – `new FileReader(datei,
  charset)` gibt es seit Java 11. Das Profil darf bleiben: Der Test ist jetzt mit jedem
  Standard-Zeichensatz grün.
- **Notlösung:** `-Dfile.encoding=COMPAT` stellt das alte Verhalten wieder her.

### `datumsformat`

- **Symptom:** `expected: <… vom Mo, 14. Sep 2026 über 1.234,50 €> but was:
  <… vom Mo., 14. Sept. 2026 über 1.234,50 €>`.
- **Ursache:** Seit Java 9 stammen die Locale-Daten aus dem Unicode CLDR (JEP 252).
  Abkürzungen haben jetzt einen Punkt, „Sept.“ hat vier Buchstaben – und **vor dem €
  steht ein geschütztes Leerzeichen** (U+00A0) statt eines normalen. Das sieht man in
  der Meldung nicht: Wer nur das Datum repariert, hat noch einen roten Test.
- **Behebung:** Die Kopfzeile wird vom Lagersystem eingelesen, ihre Form ist ein
  Vertrag. Deshalb stehen Abkürzungen und Betragsformat im Code
  (`DateTimeFormatterBuilder.appendText(…, Map)`, `DecimalFormat`). Ist ein Text nur
  für Menschen gedacht, ist die andere Antwort genauso richtig: das neue Format
  übernehmen und den Test anpassen.
- **Notlösung:** `-Djava.locale.providers=COMPAT` – **nur bis Java 22**, seit 23 gibt es
  die alten Daten nicht mehr.

### `zeitstempel`

- **Symptom:** `expected: <BESTELLT@2026-10-14T13:29:57.120836761Z> but was:
  <BESTELLT@2026-10-14T13:29:57.120Z>`.
- **Ursache:** Seit Java 15 liefert `Instant.now()` je nach Betriebssystem Mikro- oder
  sogar Nanosekunden statt Millisekunden. Gespeichert werden Millisekunden – nach dem
  Laden ist das Ereignis ein anderes.
- **Behebung:** Die Anwendung legt die Genauigkeit fest, nicht das JDK:
  `Instant.now(Clock.tickMillis(ZoneOffset.UTC))`. `Clock.tickMillis` gibt es seit
  Java 9. Dieselbe Falle steckt in jeder `TIMESTAMP(3)`-Spalte einer Datenbank.

### `sequenced`

- **Symptom:** `getFirst() in kompatibilitaet.sequenced.Warteschlange cannot implement
  getFirst() in java.util.List`.
- **Ursache:** Seit Java 21 erben alle Listen `getFirst()`, `getLast()`, `reversed()`
  und weitere aus `SequencedCollection` (JEP 431). Die eigenen Methoden gleichen Namens
  liefern `Optional` – ein anderer Rückgabetyp, also kein gültiges Überschreiben.
- **Behebung:** Die Warteschlange **hat** eine Liste, statt eine zu **sein**. Die
  Aufrufer bleiben unverändert. Wer von einer JDK-Klasse erbt, erbt auch alles, was das
  JDK ihr künftig hinzufügt.

### `security-manager`

- **Symptom:** `UnsupportedOperationException: The Security Manager is deprecated and
  will be removed in a future release`.
- **Ursache:** Seit Java 17 zur Entfernung vorgesehen (JEP 411), seit Java 18 nur noch
  mit `-Djava.security.manager=allow` einschaltbar, seit Java 24 dauerhaft abgeschaltet
  (JEP 486). **Einen Ersatz im JDK gibt es nicht.**
- **Behebung:** Das Plugin läuft in einer eigenen JVM (`ProcessBuilder`). Ein
  `System.exit` beendet nur diese; der Status kommt als Exit-Code zurück. Die Abgrenzung
  übernimmt das Betriebssystem – oder ein Container.
- **Notlösung:** `-Djava.security.manager=allow` – **nur bis Java 23**.

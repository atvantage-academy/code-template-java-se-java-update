# Spielwiese

Hier probierst Du Neuerungen aus, ohne ein Projekt anzulegen. Jede Datei ist ein
eigenständiges Programm, das Java direkt aus dem Quelltext startet – ohne Maven und
ohne vorheriges `javac`:

```shell
java spielwiese/Versionsinfo.java
```

Das funktioniert seit Java 11 (JEP 330); seit Java 22 darf ein solches Programm auch
weitere Quelldateien im selben Ordner nutzen (JEP 458).

Lege für jeden Versuch eine neue Datei an. Was hier liegt, wird von keinem Build
übersetzt und von keinem Test geprüft.

`Leerzeile` zeigt den Unterschied zwischen `--release 8` und `-source 8 -target 8`;
wie sie übersetzt wird, steht im Kommentar am Anfang der Datei.

`Pakete` verteilt Artikel mit einem Stream Gatherer (JEP 485) auf Pakete und läuft
nur auf JDK 25.

`Speicherlast` gibt dem Garbage Collector Arbeit, damit sich die Collectoren
vergleichen lassen, und zeigt mit JDK 25 den Effekt der kompakten Objekt-Header.
`Angeheftet` lässt virtuelle Threads in einem `synchronized`-Block warten – unter
JDK 21 hält der Flight Recorder das Pinning fest, unter JDK 25 nicht mehr.

`Wertbasiert` sammelt vier Stellen, an denen Code auf die Identität von `Integer`,
`LocalDate` und Co. setzt – der Compiler warnt schon heute, und mit Project Valhalla
ändert sich ihr Verhalten. `Wertklassen` vergleicht Records mit und ohne Identität
und braucht einen Early-Access-Build von JDK 28 mit `--enable-preview`.

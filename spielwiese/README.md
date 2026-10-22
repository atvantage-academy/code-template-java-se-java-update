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

Einige Dateien zeigen, was nach Java 25 kommt (`FinalesFeld`, `PrimitiveMuster`,
`Gleichzeitig`). Sie brauchen ein neueres JDK oder `--enable-preview`; wie sie
starten, steht jeweils im Kommentar am Anfang der Datei.

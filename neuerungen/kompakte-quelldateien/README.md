# Musterlösung · LTS 25: Kompakte Quelldateien

**JDK:** 25 (JEP 458, Java 22; JEP 512, Java 25; LTS 25). Das Release des Moduls
bleibt bei 8: Die Features betreffen den Start mit `java`, nicht den Build.

`skript/Lagerliste.java` ist eine kompakte Quelldatei: Klassendeklaration, `static`,
`String[] args` und die Importe aus `java.util` sind weg, `IO.println` ersetzt
`System.out.println`. Dazu `var`, `List.of` und `Map.merge` – die Ausgabe bleibt
zeichengleich, `LagerlisteTest` ist unverändert grün.

**Grenzen:** Die implizit deklarierte Klasse hat keinen Namen, auf den sich anderer Code
beziehen kann, und kein Paket. Braucht das Werkzeug eine Bibliothek außerhalb des JDK,
ist ein Maven-Projekt wieder der richtige Ort.

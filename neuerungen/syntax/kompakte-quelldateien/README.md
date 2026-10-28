# Musterlösung · LTS 25: Kompakte Quelldateien

**Release:** 25 (JEP 512, Java 25; LTS 25) – braucht ein JDK 25. Maven übersetzt das
Werkzeug mit; eine kompakte Quelldatei verlangt dafür Release 25.

`src/main/java/Lagerliste.java` ist eine kompakte Quelldatei: Klassendeklaration,
`static`, `String[] args` und die Importe aus `java.util` sind weg, `IO.println` ersetzt
`System.out.println`. Dazu `var`, `List.of` und `Map.merge` – die Ausgabe bleibt
zeichengleich. Starten: Run Configuration `syntax-kompakte-quelldateien-start` oder
`mvn clean compile -pl neuerungen/syntax/kompakte-quelldateien -am -Pstart`.

**Grenzen:** Die implizit deklarierte Klasse hat keinen Namen, auf den sich anderer Code
beziehen kann, und kein Paket. Braucht das Werkzeug eine Bibliothek außerhalb des JDK,
ist ein Maven-Projekt mit gewöhnlichen Klassen wieder der richtige Ort.

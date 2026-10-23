/**
 * Ein kleines Werkzeug: fasst Lagerbewegungen je Artikel zusammen. Es gehört zu keinem
 * Build, Java startet es direkt aus dem Quelltext:
 *
 *     java neuerungen/kompakte-quelldateien/skript/Lagerliste.java
 *
 * Als kompakte Quelldatei (JEP 512, Java 25): keine Klasse, kein static, keine Importe
 * aus java.base, IO statt System.out.
 */

void main() {
    var bewegungen = List.of(
            "A-100;Kaffeebecher;12",
            "A-200;Teekanne;3",
            "A-100;Kaffeebecher;8",
            "A-300;Untersetzer;40");

    var namen = new TreeMap<String, String>();
    var bestand = new TreeMap<String, Integer>();
    for (var bewegung : bewegungen) {
        var felder = bewegung.split(";");
        namen.put(felder[0], felder[1]);
        bestand.merge(felder[0], Integer.parseInt(felder[2]), Integer::sum);
    }

    bestand.forEach((artikel, menge) -> IO.println(artikel + " " + namen.get(artikel) + ": " + menge));
}

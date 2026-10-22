import java.util.List;
import java.util.stream.Gatherers;

/**
 * Verteilt Artikel auf Pakete zu je drei Stück – mit einem Stream Gatherer.
 *
 * Gatherers gibt es seit Java 24 (JEP 485), im Kurs also ab LTS 25. Läuft nur auf JDK 25:
 *
 *     java spielwiese/Pakete.java
 */
public class Pakete {

    public static void main(String[] args) {
        var artikel = List.of("Kaffeebohnen", "Milchkännchen", "Siebträgermaschine",
                "Entkalker", "Kaffeemühle", "Tamper", "Filterpapier");

        var pakete = artikel.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();

        for (var i = 0; i < pakete.size(); i++) {
            System.out.println("Paket " + (i + 1) + ": " + String.join(", ", pakete.get(i)));
        }
    }
}

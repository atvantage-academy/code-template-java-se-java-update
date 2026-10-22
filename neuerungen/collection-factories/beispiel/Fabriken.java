import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Collection Factories (JEP 269, Java 9) und Sequenced Collections (JEP 431, Java 21).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 21:
 *
 *     java neuerungen/collection-factories/beispiel/Fabriken.java
 *
 * Ändere die Werte und starte noch einmal. Welche Versuche unten scheitern, und warum?
 */
public class Fabriken {

    public static void main(String[] args) {
        List<String> zahlarten = List.of("Rechnung", "Lastschrift", "Kreditkarte", "PayPal");
        System.out.println("Zahlarten: " + zahlarten);
        System.out.println("Erste: " + zahlarten.getFirst() + ", letzte: " + zahlarten.getLast());
        System.out.println("Rückwärts: " + zahlarten.reversed());

        Set<String> länder = Set.of("DE", "AT", "CH");
        System.out.println("Länder: " + länder + " – in welcher Reihenfolge?");

        Map<String, Integer> ziele = Map.of("Rechnung", 14, "Lastschrift", 3);
        System.out.println("Zahlungsziele: " + ziele);

        versuche("ein Element hinzufügen", () -> zahlarten.add("Bar"));
        versuche("null in List.of", () -> List.of("Rechnung", null));
        versuche("ein Land doppelt in Set.of", () -> Set.of("DE", "DE"));
        versuche("nach null fragen", () -> zahlarten.contains(null));
    }

    private static void versuche(String was, Runnable versuch) {
        try {
            versuch.run();
            System.out.println(was + ": geht");
        } catch (RuntimeException fehler) {
            System.out.println(was + ": " + fehler.getClass().getSimpleName());
        }
    }
}

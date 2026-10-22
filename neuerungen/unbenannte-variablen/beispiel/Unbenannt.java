import java.util.List;
import java.util.Map;

/**
 * Unbenannte Variablen und Muster (JEP 456, Java 22): „_“ für alles, was der Code nie
 * liest.
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 22:
 *
 *     java neuerungen/unbenannte-variablen/beispiel/Unbenannt.java
 *
 * Nimm danach die Kommentarzeichen vor der markierten Zeile in main weg und starte
 * noch einmal. Was meldet der Compiler?
 */
public class Unbenannt {

    record Position(String artikelnummer, int menge) {
    }

    public static void main(String[] args) {
        // In einem catch-Block, der die Ausnahme nicht braucht
        int zahlen = 0;
        for (String text : List.of("12", "zwölf", "7")) {
            try {
                Integer.parseInt(text);
                zahlen++;
            } catch (NumberFormatException _) {
                // keine Zahl
            }
        }
        System.out.println("Zahlen: " + zahlen);

        // In einer Schleife, die nur zählt
        int zeilen = 0;
        for (String _ : List.of("a", "b", "c")) {
            zeilen++;
        }
        System.out.println("Zeilen: " + zeilen);

        // In einem Lambda – auch mehrfach im selben
        Map.of("A-100", 2, "A-200", 5).forEach((_, _) -> System.out.println("ein Artikel"));

        // In einem Record-Muster
        Object objekt = new Position("A-100", 3);
        if (objekt instanceof Position(var artikelnummer, _)) {
            System.out.println("Artikel: " + artikelnummer);
        }

        // System.out.println(_);
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Records (JEP 395, Java 16).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 16:
 *
 *     java neuerungen/records/beispiel/Wertobjekte.java
 *
 * Nimm danach die Kommentarzeichen vor einer der markierten Zeilen weg und starte
 * noch einmal. Was meldet der Compiler? Probiere jede Zeile einzeln.
 */
public class Wertobjekte {

    record Geldbetrag(long cent, String währung) {

        // Kompakter Konstruktor: ohne Parameterliste, die Zuweisung macht der Compiler.
        Geldbetrag {
            if (cent < 0) {
                throw new IllegalArgumentException("Kein negativer Betrag: " + cent);
            }
        }

        Geldbetrag plus(Geldbetrag anderer) {
            return new Geldbetrag(cent + anderer.cent, währung);
        }

        // String rundung;

        // Geldbetrag(long cent) { this.cent = cent; this.währung = "EUR"; }
    }

    record Lieferung(List<String> artikel) {
    }

    record SichereLieferung(List<String> artikel) {

        SichereLieferung {
            artikel = List.copyOf(artikel);
        }
    }

    public static void main(String[] args) {
        var betrag = new Geldbetrag(1890, "EUR");
        System.out.println("toString: " + betrag);
        System.out.println("Zugriff:  " + betrag.cent() + " " + betrag.währung());
        System.out.println("equals:   " + betrag.equals(new Geldbetrag(1890, "EUR")));
        System.out.println("plus:     " + betrag.plus(new Geldbetrag(249, "EUR")));

        // betrag.cent = 0;

        try {
            new Geldbetrag(-1, "EUR");
        } catch (IllegalArgumentException e) {
            System.out.println("Abgelehnt: " + e.getMessage());
        }

        var artikel = new ArrayList<String>();
        artikel.add("Kaffeebohnen");
        var lieferung = new Lieferung(artikel);
        var sicher = new SichereLieferung(artikel);
        artikel.add("Filtertüten");
        System.out.println("Lieferung:        " + lieferung);
        System.out.println("SichereLieferung: " + sicher);
    }
}

/**
 * Flexible Konstruktoren (JEP 513, Java 25): Anweisungen vor super(…) – zum Prüfen der
 * Argumente und zum Belegen eigener Felder, bevor die Oberklasse läuft.
 *
 * Starten im Wurzelordner des Übungsprojekts, mit JDK 25:
 *
 *     java neuerungen/flexible-konstruktoren/beispiel/VorSuper.java
 *
 * Nimm danach die Kommentarzeichen vor der markierten Zeile im Konstruktor von
 * Lieferschein weg und starte noch einmal. Was meldet der Compiler?
 */
public class VorSuper {

    static class Beleg {
        Beleg() {
            // Die Oberklasse ruft eine Methode, die die Unterklasse überschreibt
            System.out.println("Beleg angelegt: " + beschreibung());
        }

        String beschreibung() {
            return "Beleg";
        }
    }

    // Klassisch: Das Feld ist belegt, NACHDEM die Oberklasse gelaufen ist
    static class Rechnung extends Beleg {
        private final String nummer;

        Rechnung(String nummer) {
            super();
            this.nummer = nummer;
        }

        @Override
        String beschreibung() {
            return "Rechnung " + nummer;
        }
    }

    // Seit Java 25: prüfen und das Feld belegen, BEVOR die Oberklasse läuft
    static class Lieferschein extends Beleg {
        private final String nummer;

        Lieferschein(String nummer) {
            if (nummer.isBlank()) {
                throw new IllegalArgumentException("Lieferscheinnummer fehlt");
            }
            this.nummer = nummer.strip();
            // System.out.println(beschreibung());
            super();
        }

        @Override
        String beschreibung() {
            return "Lieferschein " + nummer;
        }
    }

    public static void main(String[] args) {
        new Rechnung("R-17");
        new Lieferschein(" L-4 ");
        try {
            new Lieferschein(" ");
        } catch (IllegalArgumentException fehler) {
            System.out.println("Abgelehnt: " + fehler.getMessage());
        }
    }
}

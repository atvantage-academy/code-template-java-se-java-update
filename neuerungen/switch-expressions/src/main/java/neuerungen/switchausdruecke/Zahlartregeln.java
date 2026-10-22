package neuerungen.switchausdruecke;

/**
 * Regeln je Zahlart, wie sie Import und Oberfläche brauchen. Jede Methode wählt mit
 * einem switch-Ausdruck (JEP 361).
 */
public final class Zahlartregeln {

    private Zahlartregeln() {
    }

    /** Liest die Zahlart aus dem Kürzel einer Importdatei, etwa „RE“ oder „pp“. */
    public static Zahlart ausKürzel(String kürzel) {
        return switch (kürzel.trim().toUpperCase()) {
            case "RE" -> Zahlart.RECHNUNG;
            case "LS", "SEPA" -> Zahlart.LASTSCHRIFT;
            case "KK" -> Zahlart.KREDITKARTE;
            case "PP" -> Zahlart.PAYPAL;
            default -> throw new IllegalArgumentException("Unbekanntes Kürzel: " + kürzel);
        };
    }

    /** Nach wie vielen Tagen der Betrag spätestens eingeht. */
    public static int zahlungszielTage(Zahlart zahlart) {
        // Ohne default: Kommt eine Zahlart dazu, meldet es der Compiler.
        return switch (zahlart) {
            case RECHNUNG -> 14;
            case LASTSCHRIFT -> 3;
            case KREDITKARTE, PAYPAL -> 0;
        };
    }

    /** Ein Hinweis für die Bestellbestätigung. */
    public static String hinweis(Zahlart zahlart) {
        return switch (zahlart) {
            case RECHNUNG -> {
                int tage = zahlungszielTage(zahlart);
                yield "Bitte zahle innerhalb von " + tage + " Tagen.";
            }
            case LASTSCHRIFT -> "Wir buchen den Betrag von Deinem Konto ab.";
            case KREDITKARTE, PAYPAL -> "Der Betrag ist bereits bezahlt.";
        };
    }
}

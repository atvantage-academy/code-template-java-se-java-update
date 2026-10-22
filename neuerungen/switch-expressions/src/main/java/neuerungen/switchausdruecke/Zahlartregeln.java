package neuerungen.switchausdruecke;

/**
 * Regeln je Zahlart, wie sie Import und Oberfläche brauchen. Jede Methode wählt mit
 * einem switch – so, wie man das mit Java 8 schreibt.
 */
public final class Zahlartregeln {

    private Zahlartregeln() {
    }

    /** Liest die Zahlart aus dem Kürzel einer Importdatei, etwa „RE“ oder „pp“. */
    public static Zahlart ausKürzel(String kürzel) {
        Zahlart zahlart;
        switch (kürzel.trim().toUpperCase()) {
            case "RE":
                zahlart = Zahlart.RECHNUNG;
                break;
            case "LS":
            case "SEPA":
                zahlart = Zahlart.LASTSCHRIFT;
                break;
            case "KK":
                zahlart = Zahlart.KREDITKARTE;
                break;
            case "PP":
                zahlart = Zahlart.PAYPAL;
                break;
            default:
                throw new IllegalArgumentException("Unbekanntes Kürzel: " + kürzel);
        }
        return zahlart;
    }

    /** Nach wie vielen Tagen der Betrag spätestens eingeht. */
    public static int zahlungszielTage(Zahlart zahlart) {
        switch (zahlart) {
            case RECHNUNG:
                return 14;
            case LASTSCHRIFT:
                return 3;
            case KREDITKARTE:
            case PAYPAL:
                return 0;
            default:
                throw new IllegalStateException("Zahlart ohne Zahlungsziel: " + zahlart);
        }
    }

    /** Ein Hinweis für die Bestellbestätigung. */
    public static String hinweis(Zahlart zahlart) {
        String hinweis;
        switch (zahlart) {
            case RECHNUNG:
                int tage = zahlungszielTage(zahlart);
                hinweis = "Bitte zahle innerhalb von " + tage + " Tagen.";
                break;
            case LASTSCHRIFT:
                hinweis = "Wir buchen den Betrag von Deinem Konto ab.";
                break;
            default:
                hinweis = "Der Betrag ist bereits bezahlt.";
        }
        return hinweis;
    }
}

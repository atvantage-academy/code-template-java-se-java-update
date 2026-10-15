package bestellung.domaene;

import java.time.format.DateTimeFormatter;

/**
 * Beschreibt eine Zahlart für Kundinnen und Kunden – mit Record-Patterns, die jede
 * Zahlart gleich in ihre Bestandteile zerlegen.
 */
public final class ZahlartBeschreibung {

    private static final DateTimeFormatter MONAT_JAHR = DateTimeFormatter.ofPattern("MM/yyyy");

    private ZahlartBeschreibung() {
    }

    public static String beschreibe(Zahlart zahlart) {
        return switch (zahlart) {
            case Rechnung(var tage) when tage == 0 -> "Rechnung, sofort zahlbar";
            case Rechnung(var tage) -> "Rechnung, zahlbar innerhalb von " + tage + " Tagen";
            case Lastschrift(var iban) -> "Lastschrift von " + maskiere(iban);
            case Kreditkarte(var nummer, var gültigBis) ->
                    "Kreditkarte " + maskiere(nummer) + ", gültig bis " + gültigBis.format(MONAT_JAHR);
            case PayPal(var konto) -> "PayPal (" + konto + ")";
        };
    }

    private static String maskiere(String nummer) {
        var ohneLeerzeichen = nummer.replace(" ", "");
        var sichtbar = ohneLeerzeichen.length() - 4;
        return "*".repeat(sichtbar) + ohneLeerzeichen.substring(sichtbar);
    }
}

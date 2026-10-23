package neuerungen.muster;

import java.time.format.DateTimeFormatter;

/**
 * Beschreibt eine Zahlart für Kundinnen und Kunden – mit einem {@code switch} über
 * Typ-Muster. Die Hierarchie ist offen, deshalb braucht er einen {@code default}.
 */
public final class ZahlartBeschreibung {

    private static final DateTimeFormatter MONAT_JAHR = DateTimeFormatter.ofPattern("MM/yyyy");

    private ZahlartBeschreibung() {
    }

    public static String beschreibe(Zahlart zahlart) {
        return switch (zahlart) {
            case null -> "keine Zahlart angegeben";
            case Rechnung rechnung when rechnung.getZahlungszielTage() == 0 -> "Rechnung, sofort zahlbar";
            case Rechnung rechnung ->
                    "Rechnung, zahlbar innerhalb von " + rechnung.getZahlungszielTage() + " Tagen";
            case Lastschrift lastschrift -> "Lastschrift von " + maskiere(lastschrift.getIban());
            case Kreditkarte kreditkarte -> "Kreditkarte " + maskiere(kreditkarte.getKartennummer())
                    + ", gültig bis " + kreditkarte.getGültigBis().format(MONAT_JAHR);
            case PayPal payPal -> "PayPal (" + payPal.getKonto() + ")";
            default -> throw new IllegalArgumentException("Unbekannte Zahlart: " + zahlart);
        };
    }

    private static String maskiere(String nummer) {
        String ohneLeerzeichen = nummer.replace(" ", "");
        StringBuilder maskiert = new StringBuilder();
        for (int i = 0; i < ohneLeerzeichen.length() - 4; i++) {
            maskiert.append('*');
        }
        return maskiert.append(ohneLeerzeichen.substring(ohneLeerzeichen.length() - 4)).toString();
    }
}

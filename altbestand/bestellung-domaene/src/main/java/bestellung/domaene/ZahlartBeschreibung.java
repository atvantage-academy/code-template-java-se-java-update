package bestellung.domaene;

import java.time.format.DateTimeFormatter;

/**
 * Beschreibt eine Zahlart für Kundinnen und Kunden – ohne Visitor, mit einer
 * Kette aus {@code instanceof} und Casts, wie man sie in gewachsenem Code findet.
 */
public final class ZahlartBeschreibung {

    private static final DateTimeFormatter MONAT_JAHR = DateTimeFormatter.ofPattern("MM/yyyy");

    private ZahlartBeschreibung() {
    }

    public static String beschreibe(Zahlart zahlart) {
        if (zahlart instanceof Rechnung) {
            Rechnung rechnung = (Rechnung) zahlart;
            return "Rechnung, zahlbar innerhalb von " + rechnung.zahlungszielTage() + " Tagen";
        } else if (zahlart instanceof Lastschrift) {
            Lastschrift lastschrift = (Lastschrift) zahlart;
            return "Lastschrift von " + maskiere(lastschrift.iban());
        } else if (zahlart instanceof Kreditkarte) {
            Kreditkarte kreditkarte = (Kreditkarte) zahlart;
            return "Kreditkarte " + maskiere(kreditkarte.kartennummer())
                    + ", gültig bis " + kreditkarte.gültigBis().format(MONAT_JAHR);
        } else if (zahlart instanceof PayPal) {
            PayPal payPal = (PayPal) zahlart;
            return "PayPal (" + payPal.konto() + ")";
        } else {
            throw new IllegalArgumentException("Unbekannte Zahlart: " + zahlart);
        }
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

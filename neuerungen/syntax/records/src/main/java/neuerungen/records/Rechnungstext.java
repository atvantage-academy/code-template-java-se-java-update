package neuerungen.records;

import java.util.ArrayList;
import java.util.List;

/**
 * Schreibt den Text einer Rechnung. Hier hängen die Aufrufer der Zugriffsmethoden.
 */
public final class Rechnungstext {

    private Rechnungstext() {
    }

    public static List<String> zeilen(Bestellung bestellung) {
        List<String> zeilen = new ArrayList<String>();
        Kunde kunde = bestellung.getKunde();
        zeilen.add("Rechnung " + bestellung.getNummer() + " für " + kunde.getName()
                + " (" + kunde.getKundennummer() + ")");
        for (Position position : bestellung.getPositionen()) {
            zeilen.add(position.getMenge() + " × " + position.getBezeichnung()
                    + " [" + position.getArtikelnummer() + "] à " + position.getEinzelpreis()
                    + " EUR = " + position.getSumme() + " EUR");
        }
        zeilen.add("Gesamt: " + bestellung.getGesamtsumme() + " EUR");
        return zeilen;
    }

    public static String zahlungshinweis(Kreditkarte karte) {
        String nummer = karte.getKartennummer();
        return "Abgebucht von Karte ****" + nummer.substring(nummer.length() - 4)
                + ", gültig bis " + karte.getGültigBis();
    }
}

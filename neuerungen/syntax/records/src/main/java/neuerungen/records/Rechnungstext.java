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
        zeilen.add("Rechnung " + bestellung.getNummer() + " für " + kunde.name()
                + " (" + kunde.kundennummer() + ")");
        for (Position position : bestellung.getPositionen()) {
            zeilen.add(position.menge() + " × " + position.bezeichnung()
                    + " [" + position.artikelnummer() + "] à " + position.einzelpreis()
                    + " EUR = " + position.summe() + " EUR");
        }
        zeilen.add("Gesamt: " + bestellung.getGesamtsumme() + " EUR");
        return zeilen;
    }

    public static String zahlungshinweis(Kreditkarte karte) {
        String nummer = karte.kartennummer();
        return "Abgebucht von Karte ****" + nummer.substring(nummer.length() - 4)
                + ", gültig bis " + karte.gültigBis();
    }
}

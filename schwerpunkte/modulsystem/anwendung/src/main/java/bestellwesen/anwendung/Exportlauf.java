package bestellwesen.anwendung;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

import bestellwesen.export.Exportformat;
import bestellwesen.modell.Betrag;
import bestellwesen.modell.Bestellung;
import bestellwesen.modell.Position;

/** Exportiert einen kleinen Bestand an Bestellungen in jedes Format, das zur Laufzeit da ist. */
public final class Exportlauf {

    private Exportlauf() {
    }

    public static void main(String[] args) {
        List<Bestellung> bestand = beispielbestand();
        Map<String, String> exporte = exportiereAlle(bestand);
        if (exporte.isEmpty()) {
            System.out.println("Kein Exportformat gefunden.");
        }
        for (Map.Entry<String, String> export : exporte.entrySet()) {
            System.out.println("== " + export.getKey() + " ==");
            System.out.print(export.getValue());
        }
        long gesamt = 0;
        for (Bestellung bestellung : bestand) {
            gesamt += bestellung.summeCent();
        }
        System.out.println("Gesamt: " + Betrag.alsEuro(gesamt));
    }

    /** Je gefundenem Format sein Name und der Export, nach Namen sortiert. */
    public static Map<String, String> exportiereAlle(List<Bestellung> bestellungen) {
        Map<String, String> exporte = new TreeMap<String, String>();
        for (Exportformat format : ServiceLoader.load(Exportformat.class)) {
            exporte.put(format.name(), format.exportiere(bestellungen));
        }
        return exporte;
    }

    public static List<Bestellung> beispielbestand() {
        return Arrays.asList(
                new Bestellung("B-1001", "Muster GmbH", Arrays.asList(
                        new Position("Tasse", 4, 1250), new Position("Kanne", 1, 3990))),
                new Bestellung("B-1002", "Beispiel AG", Arrays.asList(
                        new Position("Teller", 120, 990))));
    }
}

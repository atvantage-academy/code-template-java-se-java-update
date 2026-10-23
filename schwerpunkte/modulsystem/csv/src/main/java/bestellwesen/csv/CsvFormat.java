package bestellwesen.csv;

import java.util.List;

import bestellwesen.export.Exportformat;
import bestellwesen.modell.Betrag;
import bestellwesen.modell.Bestellung;

/** Exportiert Bestellungen als CSV mit Semikolon, je Bestellung eine Zeile. */
public final class CsvFormat implements Exportformat {

    @Override
    public String name() {
        return "csv";
    }

    @Override
    public String exportiere(List<Bestellung> bestellungen) {
        StringBuilder csv = new StringBuilder("nummer;kunde;positionen;summe\n");
        for (Bestellung bestellung : bestellungen) {
            csv.append(bestellung.getNummer()).append(';')
                    .append(bestellung.getKunde()).append(';')
                    .append(bestellung.getPositionen().size()).append(';')
                    .append(Betrag.alsEuro(bestellung.summeCent())).append('\n');
        }
        return csv.toString();
    }
}

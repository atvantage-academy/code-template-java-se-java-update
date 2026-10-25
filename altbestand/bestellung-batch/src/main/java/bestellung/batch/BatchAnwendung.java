package bestellung.batch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import bestellung.domaene.BestellService;
import bestellung.domaene.Bestellung;
import bestellung.domaene.InMemoryBestellRepository;
import bestellung.extern.Bonitätsprüfung;

/**
 * Der nächtliche Import: liest eine CSV-Datei und legt die geprüften Bestellungen an.
 *
 * <pre>
 * mvn -pl altbestand/bestellung-batch -am install -DskipTests
 * mvn -pl altbestand/bestellung-batch exec:java              # mitgelieferte Beispieldatei
 * mvn -pl altbestand/bestellung-batch exec:java -Dexec.args=pfad/zur/datei.csv
 * </pre>
 */
public class BatchAnwendung {

    public static final long AUSKUNFT_LATENZ_MILLIS = 50;

    public static void main(String[] args) throws IOException, InterruptedException {
        InputStream ein = args.length > 0
                ? new FileInputStream(args[0])
                : BatchAnwendung.class.getResourceAsStream("/bestellungen.csv");
        List<Bestellung> bestellungen = new CsvLeser().lies(ein);

        BestellService service = new BestellService(new InMemoryBestellRepository());
        BestellImport bestellImport = new BestellImport(service,
                new Bonitätsprüfung(AUSKUNFT_LATENZ_MILLIS));
        ImportErgebnis ergebnis = bestellImport.importiere(bestellungen);

        System.out.printf("%d Bestellungen gelesen: %s (Auskunft-Latenz %d ms)%n",
                bestellungen.size(), ergebnis, AUSKUNFT_LATENZ_MILLIS);
        System.out.println();
        System.out.println(service.bericht());
    }
}

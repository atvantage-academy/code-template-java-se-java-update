package bestellung.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import bestellung.domaene.BestellService;
import bestellung.domaene.Bestellung;
import bestellung.extern.Bonitätsprüfung;

/**
 * Importiert Bestellungen: Jede wird bei der Auskunftei geprüft und nur angelegt,
 * wenn der Kunde kreditwürdig ist. Jede Prüfung bekommt ihren eigenen virtuellen
 * Thread – die Frage, wie groß ein Pool sein darf, stellt sich nicht mehr.
 */
public class BestellImport {

    private final BestellService service;
    private final Bonitätsprüfung bonitätsprüfung;

    public BestellImport(BestellService service, Bonitätsprüfung bonitätsprüfung) {
        this.service = service;
        this.bonitätsprüfung = bonitätsprüfung;
    }

    public ImportErgebnis importiere(List<Bestellung> bestellungen) throws InterruptedException {
        var start = System.nanoTime();
        // Ein ExecutorService ist seit Java 19 AutoCloseable: close() wartet, bis alle
        // Aufgaben fertig sind.
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var prüfungen = bestellungen.stream()
                    .map(bestellung -> executor.submit(
                            () -> bonitätsprüfung.istKreditwürdig(bestellung.getKunde().kundennummer())))
                    .toList();
            var angenommen = 0;
            var abgelehnt = new ArrayList<String>();
            for (var i = 0; i < bestellungen.size(); i++) {
                var bestellung = bestellungen.get(i);
                if (ergebnis(prüfungen.get(i))) {
                    service.anlegen(bestellung);
                    angenommen++;
                } else {
                    abgelehnt.add(bestellung.getKunde().kundennummer());
                }
            }
            return new ImportErgebnis(angenommen, abgelehnt, (System.nanoTime() - start) / 1_000_000);
        }
    }

    private static boolean ergebnis(Future<Boolean> prüfung) throws InterruptedException {
        try {
            return prüfung.get();
        } catch (ExecutionException e) {
            throw new IllegalStateException("Bonitätsprüfung fehlgeschlagen", e.getCause());
        }
    }
}

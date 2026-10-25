package bestellung.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import bestellung.domaene.BestellService;
import bestellung.domaene.Bestellung;
import bestellung.extern.Bonitätsprüfung;

/**
 * Importiert Bestellungen: Jede wird bei der Auskunftei geprüft und nur angelegt,
 * wenn der Kunde kreditwürdig ist. Die Prüfungen laufen parallel in einem festen
 * Thread-Pool mit acht Threads – wie groß er sein darf, ist die alte Streitfrage.
 */
public class BestellImport {

    private final BestellService service;
    private final Bonitätsprüfung bonitätsprüfung;

    public BestellImport(BestellService service, Bonitätsprüfung bonitätsprüfung) {
        this.service = service;
        this.bonitätsprüfung = bonitätsprüfung;
    }

    public ImportErgebnis importiere(List<Bestellung> bestellungen) throws InterruptedException {
        long start = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Future<Boolean>> prüfungen = new ArrayList<Future<Boolean>>();
            for (final Bestellung bestellung : bestellungen) {
                prüfungen.add(pool.submit(new Callable<Boolean>() {
                    @Override
                    public Boolean call() {
                        return bonitätsprüfung.istKreditwürdig(bestellung.getKunde().getKundennummer());
                    }
                }));
            }
            int angenommen = 0;
            List<String> abgelehnt = new ArrayList<String>();
            for (int i = 0; i < bestellungen.size(); i++) {
                Bestellung bestellung = bestellungen.get(i);
                if (ergebnis(prüfungen.get(i))) {
                    service.anlegen(bestellung);
                    angenommen++;
                } else {
                    abgelehnt.add(bestellung.getKunde().getKundennummer());
                }
            }
            return new ImportErgebnis(angenommen, abgelehnt, (System.nanoTime() - start) / 1_000_000);
        } finally {
            pool.shutdown();
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

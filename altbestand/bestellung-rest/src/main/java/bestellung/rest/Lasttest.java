package bestellung.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Schickt viele Bestellungen gleichzeitig an die REST-Schnittstelle und misst,
 * wie lange das dauert. Startet den Server selbst.
 *
 * <pre>
 * mvn -pl altbestand/bestellung-rest exec:java -Dexec.mainClass=bestellung.rest.Lasttest
 * </pre>
 */
public class Lasttest {

    static final String BESTELLUNG = "{\"kunde\": {\"kundennummer\": \"K-2001\", \"name\": \"Ada Lovelace\"},"
            + " \"zahlart\": {\"art\": \"RECHNUNG\", \"zahlungszielTage\": 14},"
            + " \"positionen\": [{\"artikelnummer\": \"A-100\", \"bezeichnung\": \"Kaffeebohnen, 1 kg\","
            + " \"menge\": 2, \"einzelpreis\": 18.90}]}";

    /**
     * Wie viele Bestellungen höchstens gleichzeitig unterwegs sind. macOS nimmt nicht
     * mehr als 128 Verbindungen in die Warteschlange des Servers auf
     * ({@code kern.ipc.somaxconn}) und setzt jede weitere zurück – mit mehr
     * gleichzeitigen Anfragen bräche der Lasttest dort ab.
     */
    static final int GLEICHZEITIG = 100;

    public static void main(String[] args) throws Exception {
        int anzahl = args.length > 0 ? Integer.parseInt(args[0]) : 200;
        RestAnwendung anwendung = new RestAnwendung(0);
        anwendung.starte();
        try {
            long dauer = miss(anwendung.getPort(), anzahl);
            System.out.printf("%d Bestellungen in %d ms (Server: %d Threads, Lager-Latenz %d ms)%n",
                    anzahl, dauer, RestAnwendung.THREADS, RestAnwendung.LAGER_LATENZ_MILLIS);
        } finally {
            anwendung.stoppe();
        }
    }

    static long miss(final int port, int anzahl) throws Exception {
        ExecutorService clients = Executors.newFixedThreadPool(Math.min(anzahl, GLEICHZEITIG));
        try {
            List<Future<Integer>> antworten = new ArrayList<Future<Integer>>();
            long start = System.nanoTime();
            for (int i = 0; i < anzahl; i++) {
                antworten.add(clients.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws IOException {
                        return sendeBestellung(port);
                    }
                }));
            }
            for (Future<Integer> antwort : antworten) {
                if (antwort.get() != 201) {
                    throw new IllegalStateException("Unerwarteter Status: " + antwort.get());
                }
            }
            return (System.nanoTime() - start) / 1_000_000;
        } finally {
            clients.shutdown();
        }
    }

    static int sendeBestellung(int port) throws IOException {
        HttpURLConnection verbindung = (HttpURLConnection) new URL("http://localhost:" + port + "/bestellungen")
                .openConnection();
        verbindung.setRequestMethod("POST");
        verbindung.setDoOutput(true);
        verbindung.setRequestProperty("Content-Type", "application/json");
        OutputStream aus = verbindung.getOutputStream();
        try {
            aus.write(BESTELLUNG.getBytes(StandardCharsets.UTF_8));
        } finally {
            aus.close();
        }
        int status = verbindung.getResponseCode();
        verbindung.disconnect();
        return status;
    }
}

package bestellung.rest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;

import bestellung.domaene.Beispieldaten;
import bestellung.domaene.BestellService;
import bestellung.domaene.InMemoryBestellRepository;
import bestellung.extern.Lagerdienst;

/**
 * Startet die REST-Schnittstelle der Bestellabwicklung.
 *
 * <p>Ein virtueller Thread je Anfrage. Die alte Antwort auf „Wie viele Anfragen
 * schafft der Server gleichzeitig?“ – so viele, wie Threads im Pool sind – gilt
 * nicht mehr: Wer auf das Lager wartet, belegt keinen Thread des Betriebssystems.
 *
 * <pre>
 * ./mvnw -pl altbestand/bestellung-rest -am install -DskipTests
 * ./mvnw -pl altbestand/bestellung-rest exec:java
 * </pre>
 */
public class RestAnwendung {

    public static final long LAGER_LATENZ_MILLIS = 100;
    static final int BACKLOG = 1000;

    private final HttpServer server;
    private final ExecutorService executor;

    public RestAnwendung(int port) throws IOException {
        BestellService service = new BestellService(new InMemoryBestellRepository());
        Beispieldaten.befülle(service);

        executor = Executors.newVirtualThreadPerTaskExecutor();
        // Die Warteschlange für neue Verbindungen. 0 hieß „Vorgabe des Systems“ (oft 50);
        // mit festem Pool fiel das nie auf. Mit virtuellen Threads ist der Pool kein
        // Engpass mehr – und die zu kurze Warteschlange wird der nächste.
        server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
        server.createContext("/bestellungen",
                new BestellHandler(service, new Lagerdienst(LAGER_LATENZ_MILLIS), new BestellMapper()));
        server.setExecutor(executor);
    }

    public void starte() {
        server.start();
    }

    public void stoppe() {
        server.stop(0);
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getPort() {
        return server.getAddress().getPort();
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        RestAnwendung anwendung = new RestAnwendung(port);
        anwendung.starte();
        System.out.println("Bestellabwicklung läuft auf http://localhost:" + anwendung.getPort() + "/bestellungen");
    }
}

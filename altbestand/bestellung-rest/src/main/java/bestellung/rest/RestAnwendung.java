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
 * <p>Ein Thread je Anfrage aus einem festen Pool mit zehn Threads – die klassische
 * Antwort auf „Wie viele Anfragen schafft der Server gleichzeitig?“: so viele, wie
 * Threads im Pool sind.
 *
 * <pre>
 * mvn -pl altbestand/bestellung-rest -am install -DskipTests
 * mvn -pl altbestand/bestellung-rest exec:java
 * </pre>
 */
public class RestAnwendung {

    public static final long LAGER_LATENZ_MILLIS = 100;
    /**
     * Wie viele neue Verbindungen auf ihre Annahme warten dürfen. 0 hieße „Vorgabe des
     * JDK“, also 50 – der Lasttest schickt aber bis zu 100 auf einmal. Linux nimmt die
     * überzähligen eine Sekunde später an; macOS setzt sie zurück („Connection reset“
     * bzw. „SocketException: Invalid argument“), und der Lasttest bricht ab. macOS
     * kappt den Wert außerdem bei {@code kern.ipc.somaxconn} (128).
     */
    static final int BACKLOG = 1000;

    private final HttpServer server;
    private final ExecutorService executor;

    public RestAnwendung(int port) throws IOException {
        BestellService service = new BestellService(new InMemoryBestellRepository());
        Beispieldaten.befülle(service);

        executor = Executors.newVirtualThreadPerTaskExecutor();
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

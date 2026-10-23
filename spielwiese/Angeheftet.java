import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Startet viele virtuelle Threads, die in einem synchronized-Block warten – wie die
 * Fremdsysteme im Altbestand. Unter JDK 21 heftet das jeden virtuellen Thread an
 * seinen Trägerthread („Pinning“), unter JDK 25 nicht mehr (JEP 491). Nachweisen
 * lässt es sich mit dem Flight Recorder:
 *
 *     java -XX:StartFlightRecording=filename=angeheftet.jfr spielwiese/Angeheftet.java
 *     jfr print --events jdk.VirtualThreadPinned angeheftet.jfr
 *
 * Läuft ab Java 21.
 */
public class Angeheftet {

    static final int AUFGABEN = 200;
    static final long WARTEZEIT_MILLIS = 50;

    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        try (ExecutorService ausführer = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < AUFGABEN; i++) {
                ausführer.submit(Angeheftet::frageFremdsystem);
            }
        }
        System.out.printf("%d Aufgaben zu je %d ms in %d ms (%d Prozessoren)%n", AUFGABEN, WARTEZEIT_MILLIS,
                (System.nanoTime() - start) / 1_000_000, Runtime.getRuntime().availableProcessors());
    }

    static Void frageFremdsystem() throws InterruptedException {
        Object verbindung = new Object();
        synchronized (verbindung) {
            Thread.sleep(WARTEZEIT_MILLIS);
        }
        return null;
    }
}

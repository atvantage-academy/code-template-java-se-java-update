package bestellung.extern;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Eine Verbindung zu einem entfernten System – hier nur nachgestellt: Jede Anfrage
 * blockiert für die eingestellte Latenz, als wartete sie auf das Netz.
 *
 * <p>Der Zustand ist mit einem {@link ReentrantLock} gesichert, nicht mehr mit
 * {@code synchronized}. Bis Java 23 hält ein virtueller Thread, der in einem
 * {@code synchronized}-Block wartet, seinen Trägerthread fest („Pinning“) – mit
 * einem Lock gibt er ihn frei. Ab Java 24 (JEP 491, im Kurs LTS 25) pinnt auch
 * {@code synchronized} nicht mehr; dort wäre der Umbau nicht nötig.
 */
public class Verbindung {

    private final long latenzMillis;
    private final ReentrantLock sperre = new ReentrantLock();
    private int anfragen;

    public Verbindung(long latenzMillis) {
        this.latenzMillis = latenzMillis;
    }

    public String sende(String anfrage) {
        sperre.lock();
        try {
            anfragen++;
            TimeUnit.MILLISECONDS.sleep(latenzMillis);
            return "OK:" + anfrage;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Anfrage unterbrochen: " + anfrage, e);
        } finally {
            sperre.unlock();
        }
    }

    public int getAnfragen() {
        sperre.lock();
        try {
            return anfragen;
        } finally {
            sperre.unlock();
        }
    }
}

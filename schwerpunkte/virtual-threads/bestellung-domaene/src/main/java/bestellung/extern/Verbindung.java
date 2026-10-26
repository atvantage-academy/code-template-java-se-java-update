package bestellung.extern;

import java.util.concurrent.TimeUnit;

/**
 * Eine Verbindung zu einem entfernten System – hier nur nachgestellt: Jede Anfrage
 * blockiert für die eingestellte Latenz, als wartete sie auf das Netz.
 *
 * <p>Wie viele Client-Bibliotheken aus der Java-8-Zeit sichert die Klasse ihren
 * Zustand mit {@code synchronized}. Jeder Aufruf bekommt eine eigene Verbindung;
 * der Monitor ist also nie umkämpft – er wird nur gehalten, während gewartet wird.
 */
public class Verbindung {

    private final long latenzMillis;
    private int anfragen;

    public Verbindung(long latenzMillis) {
        this.latenzMillis = latenzMillis;
    }

    public synchronized String sende(String anfrage) {
        anfragen++;
        try {
            TimeUnit.MILLISECONDS.sleep(latenzMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Anfrage unterbrochen: " + anfrage, e);
        }
        return "OK:" + anfrage;
    }

    public synchronized int getAnfragen() {
        return anfragen;
    }
}

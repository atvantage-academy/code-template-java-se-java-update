import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Hält einen festen Bestand an Bestellpositionen im Speicher und erzeugt daneben
 * viele kurzlebige Objekte – genug Arbeit für den Garbage Collector, um ihn zu
 * vergleichen. Läuft ab Java 11; den GC wählst Du beim Start:
 *
 *     java -XX:+UseSerialGC -Xmx1g -Xlog:gc spielwiese/Speicherlast.java
 *     java -XX:+UseParallelGC -Xmx1g -Xlog:gc spielwiese/Speicherlast.java
 *     java -XX:+UseG1GC -Xmx1g -Xlog:gc spielwiese/Speicherlast.java
 *     java -XX:+UseZGC -Xmx1g -Xlog:gc spielwiese/Speicherlast.java
 *
 * Am Ende steht, wie viel Speicher der Bestand belegt. Mit JDK 25 lässt sich so auch
 * der Effekt der kompakten Objekt-Header zeigen (JEP 519):
 *
 *     java -XX:+UseCompactObjectHeaders -Xmx1g spielwiese/Speicherlast.java
 */
public class Speicherlast {

    static final class Position {
        final String artikelnummer;
        final int menge;
        final long centJeStück;

        Position(String artikelnummer, int menge, long centJeStück) {
            this.artikelnummer = artikelnummer;
            this.menge = menge;
            this.centJeStück = centJeStück;
        }

        long summe() {
            return menge * centJeStück;
        }
    }

    static final int BESTAND = 3_000_000;
    static final int RUNDEN = 150_000_000;

    public static void main(String[] args) {
        Random zufall = new Random(42);
        String[] artikel = new String[1000];
        for (int i = 0; i < artikel.length; i++) {
            artikel[i] = "A-" + i;
        }
        List<Position> bestand = new ArrayList<>(BESTAND);
        for (int i = 0; i < BESTAND; i++) {
            bestand.add(new Position(artikel[i % artikel.length], 1 + i % 5, 100 + i % 900));
        }

        long start = System.nanoTime();
        long umsatz = 0;
        for (int i = 0; i < RUNDEN; i++) {
            Position neu = new Position(artikel[zufall.nextInt(artikel.length)], 1 + zufall.nextInt(5),
                    100 + zufall.nextInt(900));
            umsatz += neu.summe();
            if (i % 20 == 0) {
                bestand.set(zufall.nextInt(BESTAND), neu);
            }
        }
        long dauer = (System.nanoTime() - start) / 1_000_000;

        System.gc();
        Runtime laufzeit = Runtime.getRuntime();
        long belegt = (laufzeit.totalMemory() - laufzeit.freeMemory()) / (1024 * 1024);
        System.out.printf("%d Runden in %d ms, Umsatz %d, %d Positionen im Bestand belegen etwa %d MB%n",
                RUNDEN, dauer, umsatz, bestand.size(), belegt);
    }
}

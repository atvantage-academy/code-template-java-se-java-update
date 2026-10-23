import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * Wertobjekte ohne Identität (Project Valhalla, JEP 401): Preview in Java 28. Läuft nur
 * mit einem Early-Access-Build von JDK 28 (jdk.java.net/28) und --enable-preview:
 *
 *     java --enable-preview spielwiese/Wertklassen.java
 *
 * Vergleicht fünf Millionen Lagerplätze als Record mit Identität und als Wert-Record:
 * wie viel Speicher das Array belegt und wie lange ein Durchlauf dauert, wenn die
 * Elemente in gemischter Reihenfolge stehen. Ein Wert passt nur dann flach ins Array,
 * wenn er samt Markierung für null in 64 Bit passt – deshalb gibt es den Platz zweimal.
 */

record Platz(int regal, short fach) {}

value record WertPlatz(int regal, short fach) {}

value record GroßerWertPlatz(int regal, int fach) {}

static final int ANZAHL = 5_000_000;
static final Random ZUFALL = new Random(1);

void main() {
    var a = new WertPlatz(12, (short) 3);
    var b = new WertPlatz(12, (short) 3);
    IO.println("a == b: " + (a == b) + ", hat Identität: " + Objects.hasIdentity(a));
    IO.println();

    long vorher = belegt();
    Platz[] plätze = new Platz[ANZAHL];
    for (int i = 0; i < ANZAHL; i++) plätze[i] = new Platz(i % 1000, (short) i);
    Collections.shuffle(Arrays.asList(plätze), ZUFALL);
    long speicher = belegt() - vorher;
    long beste = Long.MAX_VALUE;
    for (int runde = 0; runde < 10; runde++) {
        long start = System.nanoTime();
        long summe = 0;
        for (Platz p : plätze) summe += p.regal();
        beste = Math.min(beste, System.nanoTime() - start + (summe < 0 ? 1 : 0));
    }
    zeige("Platz (mit Identität)", speicher, beste);
    plätze = null;

    vorher = belegt();
    WertPlatz[] wertplätze = new WertPlatz[ANZAHL];
    for (int i = 0; i < ANZAHL; i++) wertplätze[i] = new WertPlatz(i % 1000, (short) i);
    Collections.shuffle(Arrays.asList(wertplätze), ZUFALL);
    speicher = belegt() - vorher;
    beste = Long.MAX_VALUE;
    for (int runde = 0; runde < 10; runde++) {
        long start = System.nanoTime();
        long summe = 0;
        for (WertPlatz p : wertplätze) summe += p.regal();
        beste = Math.min(beste, System.nanoTime() - start + (summe < 0 ? 1 : 0));
    }
    zeige("WertPlatz (int, short)", speicher, beste);
    wertplätze = null;

    vorher = belegt();
    GroßerWertPlatz[] großePlätze = new GroßerWertPlatz[ANZAHL];
    for (int i = 0; i < ANZAHL; i++) großePlätze[i] = new GroßerWertPlatz(i % 1000, i);
    Collections.shuffle(Arrays.asList(großePlätze), ZUFALL);
    speicher = belegt() - vorher;
    beste = Long.MAX_VALUE;
    for (int runde = 0; runde < 10; runde++) {
        long start = System.nanoTime();
        long summe = 0;
        for (GroßerWertPlatz p : großePlätze) summe += p.regal();
        beste = Math.min(beste, System.nanoTime() - start + (summe < 0 ? 1 : 0));
    }
    zeige("GroßerWertPlatz (2 × int)", speicher, beste);
}

void zeige(String name, long speicher, long nanos) {
    IO.println(String.format("%-27s %4d MB, Durchlauf %3d ms", name, speicher / 1_000_000, nanos / 1_000_000));
}

long belegt() {
    System.gc();
    System.gc();
    Runtime laufzeit = Runtime.getRuntime();
    return laufzeit.totalMemory() - laufzeit.freeMemory();
}

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Vier Stellen, an denen Code stillschweigend auf die Identität einer wertbasierten
 * Klasse setzt – Integer, LocalDate und Co. (JEP 390, seit Java 16). Mit Project
 * Valhalla werden diese Klassen zu Wertklassen ohne Identität (JEP 401, Preview in
 * Java 28). Läuft ab Java 11:
 *
 *     java Wertbasiert.java
 *
 * Der Compiler warnt dabei schon heute. Wo zur Laufzeit auf einer wertbasierten Klasse
 * synchronisiert wird, meldet die JVM mit:
 *
 *     java -XX:+UnlockDiagnosticVMOptions -XX:DiagnoseSyncOnValueBasedClasses=2 Wertbasiert.java
 *
 * Mit einem Early-Access-Build von JDK 28 zeigt sich, was Valhalla daraus macht:
 *
 *     java --enable-preview Wertbasiert.java
 */
public class Wertbasiert {

    // 1 · Ein Lagerbestand, der auf seinem eigenen Zähler sperrt
    static class Lager {
        private Integer bestand = 0;

        void zubuchen(int menge) {
            synchronized (bestand) {
                bestand = bestand + menge;
            }
        }

        int bestand() {
            return bestand;
        }
    }

    // 2 · Ein Cache für Tageskurse, der Einträge vergessen darf
    static final Map<LocalDate, BigDecimal> KURSE = new WeakHashMap<>();

    // 3 · Ein Vergleich zweier Mengen
    static boolean gleicheMenge(Integer a, Integer b) {
        return a == b;
    }

    // 4 · Ein Zähler je Artikelnummer, der Objekte statt Werte zählt
    static final Map<Integer, Integer> AUFRUFE = new IdentityHashMap<>();

    public static void main(String[] args) throws Exception {
        prüfe("1 · Lager", Wertbasiert::lager);
        prüfe("2 · Kurse", Wertbasiert::kurse);
        prüfe("3 · Mengen", () -> "100 == 100: " + gleicheMenge(100, 100)
                + ", 1000 == 1000: " + gleicheMenge(1000, 1000));
        prüfe("4 · Aufrufe", Wertbasiert::aufrufe);
    }

    static String lager() throws InterruptedException {
        Lager lager = new Lager();
        ExecutorService ausführer = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1_000; i++) {
            ausführer.submit(() -> lager.zubuchen(1));
        }
        ausführer.shutdown();
        ausführer.awaitTermination(1, TimeUnit.MINUTES);
        return "1000 Stück zugebucht, Bestand " + lager.bestand();
    }

    static String kurse() {
        KURSE.put(LocalDate.of(2026, 10, 23), new BigDecimal("1.0842"));
        return "Kurse im Cache: " + KURSE.size();
    }

    static String aufrufe() {
        for (int i = 0; i < 3; i++) {
            AUFRUFE.merge(Integer.valueOf(4711), 1, Integer::sum);
        }
        return "3 Aufrufe für Artikel 4711, Einträge: " + AUFRUFE.size();
    }

    interface Prüfung {
        String ergebnis() throws Exception;
    }

    static void prüfe(String name, Prüfung prüfung) {
        try {
            System.out.println(name + ": " + prüfung.ergebnis());
        } catch (Throwable fehler) {
            System.out.println(name + ": " + fehler);
        }
    }
}

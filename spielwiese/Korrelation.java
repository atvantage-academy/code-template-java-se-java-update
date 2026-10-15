import java.util.concurrent.Executors;

/**
 * Gibt jeder Anfrage eine Korrelations-ID mit, ohne sie durch jede Methode zu reichen –
 * mit einem Scoped Value statt eines ThreadLocal.
 *
 * Scoped Values sind seit Java 25 final (JEP 506), im Kurs also LTS 25. Läuft nur auf JDK 25:
 *
 *     java spielwiese/Korrelation.java
 */
public class Korrelation {

    static final ScopedValue<String> KORRELATIONS_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (var nummer = 1; nummer <= 3; nummer++) {
                var id = "anfrage-" + nummer;
                executor.submit(() -> ScopedValue.where(KORRELATIONS_ID, id).run(Korrelation::bearbeite));
            }
        }
        System.out.println("Außerhalb gebunden? " + KORRELATIONS_ID.isBound());
    }

    static void bearbeite() {
        // Tief im Aufruf, ohne Parameter: Der Wert gilt nur innerhalb von run(…)
        // und nur in diesem Thread – niemand kann ihn ändern oder vergessen aufzuräumen.
        System.out.println(KORRELATIONS_ID.get() + " frage das Lager an");
    }
}

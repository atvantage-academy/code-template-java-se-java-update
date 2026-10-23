import java.util.concurrent.Executors;

/**
 * Scoped Values (JEP 506, Java 25): ein Wert, der für die Dauer eines Aufrufs gilt –
 * gebunden mit where(…).run(…), danach wieder ungebunden.
 *
 * Starten im Wurzelordner des Übungsprojekts, mit JDK 25:
 *
 *     java neuerungen/scoped-values/beispiel/Bereiche.java
 *
 * Nimm danach die Kommentarzeichen vor der markierten Zeile in main weg und starte
 * noch einmal. Was meldet der Compiler?
 */
public class Bereiche {

    static final ScopedValue<String> ANFRAGE = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        System.out.println("Vorher gebunden? " + ANFRAGE.isBound());

        ScopedValue.where(ANFRAGE, "A-1").run(() -> {
            schreibe("Bestellung angelegt");

            // Neu binden gilt nur für den inneren Aufruf
            ScopedValue.where(ANFRAGE, "A-2").run(() -> schreibe("innere Anfrage"));
            schreibe("wieder außen");
        });

        System.out.println("Nachher gebunden? " + ANFRAGE.isBound());
        System.out.println("Ohne Bindung: " + ANFRAGE.orElse("–"));

        // Viele virtuelle Threads, jeder mit seiner eigenen Bindung
        try (var ausführer = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= 3; i++) {
                String anfrage = "V-" + i;
                ausführer.submit(() -> ScopedValue.where(ANFRAGE, anfrage).run(() -> schreibe("auf virtuellem Thread")));
            }
        }

        // ANFRAGE.set("A-3");
    }

    static void schreibe(String text) {
        System.out.println("[" + ANFRAGE.get() + "] " + text);
    }
}

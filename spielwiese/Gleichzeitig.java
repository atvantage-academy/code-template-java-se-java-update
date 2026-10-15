import java.util.concurrent.StructuredTaskScope;

/**
 * Fragt Lager und Auskunftei gleichzeitig an – als eine Arbeitseinheit: Scheitert
 * eine Anfrage, wird die andere abgebrochen, und kein Thread bleibt zurück.
 *
 * Structured Concurrency ist in Java 25 bis 27 Preview und soll mit Java 28 final
 * werden (JEP 543) – also mit LTS 29. Ausprobieren (mit JDK 26 oder 27 die Zahl anpassen):
 *
 *     java --enable-preview --source 25 spielwiese/Gleichzeitig.java
 *
 * Preview heißt: Die API darf sich noch ändern. Mit JDK 25 und 26 genügte bei main
 * ein „throws InterruptedException“; seit JDK 27 wirft join() zusätzlich eine
 * ExecutionException (JEP 533), und derselbe Quelltext ließ sich nicht mehr übersetzen.
 */
public class Gleichzeitig {

    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();
        try (var scope = StructuredTaskScope.open()) {
            var bestand = scope.fork(() -> frage("Lager", 100, 7));
            var bonität = scope.fork(() -> frage("Auskunftei", 50, 1));
            scope.join();
            System.out.printf("Bestand %d, Bonität %d – nach %d ms%n",
                    bestand.get(), bonität.get(), (System.nanoTime() - start) / 1_000_000);
        }
    }

    static int frage(String system, long millis, int antwort) throws InterruptedException {
        Thread.sleep(millis);
        System.out.println(system + " antwortet in " + Thread.currentThread());
        return antwort;
    }
}

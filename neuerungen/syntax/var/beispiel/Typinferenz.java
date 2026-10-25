import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Lokale Typinferenz mit var (JEP 286, Java 10) und var in Lambda-Parametern
 * (JEP 323, Java 11).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 11:
 *
 *     java neuerungen/syntax/var/beispiel/Typinferenz.java
 *
 * Nimm danach die Kommentarzeichen vor einer der markierten Zeilen weg und starte
 * noch einmal. Was meldet der Compiler? Probiere jede Zeile einzeln.
 */
public class Typinferenz {

    // var feld = "Felder brauchen einen Typ";

    public static void main(String[] args) {
        var zahlarten = new ArrayList<String>();
        zahlarten.add("Rechnung");
        System.out.println("zahlarten ist eine " + zahlarten.getClass().getSimpleName());

        var ohneTyp = new ArrayList<>();
        ohneTyp.add(42);
        ohneTyp.add("und ein Text");
        System.out.println("Was steckt in ohneTyp? " + ohneTyp);

        var menge = 3;
        var preis = 18.90;
        System.out.println("menge mal preis: " + (menge * preis));

        for (var zahlart : List.of("Rechnung", "PayPal")) {
            System.out.println("Zahlart: " + zahlart);
        }

        BiFunction<String, Integer, String> zeile = (var artikel, var anzahl) -> anzahl + " × " + artikel;
        System.out.println(zeile.apply("Kaffeebohnen", 2));

        int var = 7;
        System.out.println("Eine Variable namens var: " + var);

        // var nichts = null;
        // var zahlen = {1, 2, 3};
        // var lambda = () -> "Hallo";
        // var a = 1, b = 2;
    }
}

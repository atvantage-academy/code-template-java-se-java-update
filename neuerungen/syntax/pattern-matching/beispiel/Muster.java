import java.util.List;

/**
 * Pattern Matching: instanceof mit Muster (JEP 394, Java 16), Pattern Matching für
 * switch (JEP 441, Java 21) und Record-Patterns (JEP 440, Java 21).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 21:
 *
 *     java neuerungen/syntax/pattern-matching/beispiel/Muster.java
 *
 * Nimm danach die Kommentarzeichen vor einer der markierten Zeilen weg und starte
 * noch einmal. Was meldet der Compiler? Probiere jede Zeile einzeln.
 */
public class Muster {

    record Punkt(int x, int y) {
    }

    record Strecke(Punkt von, Punkt bis) {
    }

    static String mitInstanceof(Object o) {
        if (o instanceof String text && !text.isEmpty()) {
            return "ein Text, " + text.length() + " Zeichen lang";
        }
        if (o instanceof Punkt p) {
            return "ein Punkt bei " + p.x() + "/" + p.y();
        }
        return "etwas anderes";
    }

    static String mitSwitch(Object o) {
        return switch (o) {
            case null -> "nichts";
            // case Integer i -> "irgendeine Zahl";
            case Integer i when i < 0 -> "eine negative Zahl";
            case Integer i -> "die Zahl " + i;
            case Punkt(var x, var y) when x == 0 && y == 0 -> "der Ursprung";
            case Punkt(var x, var y) -> "ein Punkt bei " + x + "/" + y;
            case Strecke(Punkt(var x1, var y1), Punkt(var x2, var y2)) ->
                    "eine Strecke der Länge " + Math.hypot(x2 - x1, y2 - y1);
            case String text -> "der Text „" + text + "“";
            default -> "etwas anderes: " + o.getClass().getSimpleName();
        };
    }

    // static String ohneDefault(Object o) {
    //     return switch (o) {
    //         case String text -> text;
    //     };
    // }

    public static void main(String[] args) {
        System.out.println(mitInstanceof("Kaffee"));
        System.out.println(mitInstanceof(new Punkt(3, 4)));
        System.out.println(mitInstanceof(42));

        for (Object o : List.of(-7, 42, new Punkt(0, 0), new Punkt(3, 4),
                new Strecke(new Punkt(0, 0), new Punkt(3, 4)), "Kaffee", 3.14)) {
            System.out.println(mitSwitch(o));
        }
        System.out.println(mitSwitch(null));
    }
}

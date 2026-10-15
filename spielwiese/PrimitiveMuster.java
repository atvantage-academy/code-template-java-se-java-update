/**
 * Muster mit primitiven Typen in switch und instanceof.
 *
 * Preview seit Java 23 (JEP 455), in Java 27 die fünfte Preview (JEP 532) – noch
 * nicht final. Läuft deshalb nur mit --enable-preview und dem eigenen JDK-Release:
 *
 *     java --enable-preview --source 25 spielwiese/PrimitiveMuster.java
 */
public class PrimitiveMuster {

    public static void main(String[] args) {
        for (int menge : new int[] {0, 1, 12, 500}) {
            System.out.println(menge + " → " + beschreibe(menge));
        }
        for (double betrag : new double[] {42.0, 42.5, 1e10}) {
            System.out.println(betrag + " → " + alsGanzzahl(betrag));
        }
    }

    static String beschreibe(int menge) {
        return switch (menge) {
            case 0 -> "leer";
            case 1 -> "ein Stück";
            case int m when m > 100 -> "Großbestellung über " + m;
            case int m -> m + " Stück";
        };
    }

    static String alsGanzzahl(double betrag) {
        // Das Muster passt nur, wenn die Umwandlung nichts verliert.
        if (betrag instanceof int ganz) {
            return "passt verlustfrei in int: " + ganz;
        }
        return "passt nicht in int";
    }
}

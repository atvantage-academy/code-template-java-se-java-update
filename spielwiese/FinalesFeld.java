import java.lang.reflect.Field;

/**
 * Ändert ein final-Feld per Reflection – so, wie es manche Bibliotheken für
 * Deserialisierung oder Dependency Injection tun.
 *
 * Bis Java 25 klappt das stillschweigend. Seit Java 26 warnt die JVM (JEP 500,
 * „Prepare to Make Final Mean Final“); in einem künftigen Release wird es verboten.
 * Ausprobieren, mit dem Verbot von morgen:
 *
 *     java spielwiese/FinalesFeld.java
 *     java --illegal-final-field-mutation=deny spielwiese/FinalesFeld.java
 */
public class FinalesFeld {

    static class Bestellung {
        private final String nummer;

        Bestellung(String nummer) {
            this.nummer = nummer;
        }
    }

    public static void main(String[] args) throws Exception {
        var bestellung = new Bestellung("B-1001");

        Field feld = Bestellung.class.getDeclaredField("nummer");
        feld.setAccessible(true);
        feld.set(bestellung, "B-9999");

        System.out.println("Nummer nach der Änderung: " + bestellung.nummer);
    }
}

package kompatibilitaet.unterstrich;

/**
 * Liest Zahlen aus Benutzereingaben.
 */
public final class Eingabe {

    private Eingabe() {
    }

    public static int zahlOderStandard(String text, int standard) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            // Ab Java 22 (LTS 25) wieder als „catch (NumberFormatException _)“ möglich –
            // dann als unbenannte Variable, die sich nicht mehr lesen lässt (JEP 456).
            return standard;
        }
    }

    public static String ersterBuchstabe(String text) {
        String bereinigt = text.trim();
        return bereinigt.isEmpty() ? "" : bereinigt.substring(0, 1).toUpperCase();
    }
}

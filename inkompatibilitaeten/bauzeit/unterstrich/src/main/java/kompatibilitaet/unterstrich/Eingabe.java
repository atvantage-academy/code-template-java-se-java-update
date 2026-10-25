package kompatibilitaet.unterstrich;

/**
 * Liest Zahlen aus Benutzereingaben. Der Unterstrich steht für „brauche ich nicht“ –
 * mit Java 8 ein erlaubter Bezeichner.
 */
public final class Eingabe {

    private Eingabe() {
    }

    public static int zahlOderStandard(String text, int standard) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException _) {
            return standard;
        }
    }

    public static String ersterBuchstabe(String text) {
        String _ = text.trim();
        return _.isEmpty() ? "" : _.substring(0, 1).toUpperCase();
    }
}

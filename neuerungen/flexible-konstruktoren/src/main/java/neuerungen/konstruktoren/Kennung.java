package neuerungen.konstruktoren;

/**
 * Eine fachliche Kennung mit Präfix, etwa „B-1042“ für eine Bestellung. Der Konstruktor
 * erwartet den Wert schon geprüft und normalisiert.
 */
public abstract class Kennung {

    private final String wert;

    protected Kennung(String präfix, String wert) {
        if (!wert.startsWith(präfix + "-")) {
            throw new IllegalArgumentException("Kennung muss mit " + präfix + "- beginnen: " + wert);
        }
        this.wert = wert;
    }

    public final String wert() {
        return wert;
    }

    @Override
    public String toString() {
        return wert;
    }
}

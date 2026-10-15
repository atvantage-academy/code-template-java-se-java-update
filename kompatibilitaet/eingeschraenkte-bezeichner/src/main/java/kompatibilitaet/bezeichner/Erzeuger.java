package kompatibilitaet.bezeichner;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein Erzeuger liefert Werte nacheinander ab – Unterklassen rufen dazu {@link #liefere(Object)}.
 * Bis Java 13 hieß die Methode {@code yield} wie in anderen Sprachen; seit Java 14 ist
 * {@code yield} ein eingeschränkter Bezeichner der Switch-Expressions.
 */
public abstract class Erzeuger<T> {

    private final List<T> werte = new ArrayList<T>();

    protected abstract void erzeuge();

    protected void liefere(T wert) {
        werte.add(wert);
    }

    public List<T> alle() {
        werte.clear();
        erzeuge();
        return new ArrayList<T>(werte);
    }
}

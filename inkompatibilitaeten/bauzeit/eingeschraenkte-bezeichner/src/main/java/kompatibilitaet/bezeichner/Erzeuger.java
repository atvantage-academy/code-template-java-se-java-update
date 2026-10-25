package kompatibilitaet.bezeichner;

import java.util.ArrayList;
import java.util.List;

/**
 * Ein Erzeuger liefert Werte nacheinander ab – Unterklassen rufen dazu {@link #yield(Object)}.
 * Der Name stammt aus anderen Sprachen, in denen Generatoren so arbeiten.
 */
public abstract class Erzeuger<T> {

    private final List<T> werte = new ArrayList<T>();

    protected abstract void erzeuge();

    protected void yield(T wert) {
        werte.add(wert);
    }

    public List<T> alle() {
        werte.clear();
        erzeuge();
        return new ArrayList<T>(werte);
    }
}

package kompatibilitaet.sequenced;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Eine Liste von Aufträgen, die der Reihe nach abgearbeitet werden. Erster und
 * letzter Auftrag lassen sich abfragen, ohne dass eine leere Warteschlange eine
 * Ausnahme wirft.
 */
public class Warteschlange<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;

    public Optional<E> getFirst() {
        return isEmpty() ? Optional.<E>empty() : Optional.of(get(0));
    }

    public Optional<E> getLast() {
        return isEmpty() ? Optional.<E>empty() : Optional.of(get(size() - 1));
    }
}

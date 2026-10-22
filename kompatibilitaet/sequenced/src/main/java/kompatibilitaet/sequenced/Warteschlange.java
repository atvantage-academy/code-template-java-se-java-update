package kompatibilitaet.sequenced;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Eine Liste von Aufträgen, die der Reihe nach abgearbeitet werden. Erster und
 * letzter Auftrag lassen sich abfragen, ohne dass eine leere Warteschlange eine
 * Ausnahme wirft.
 * <p>
 * Die Warteschlange <em>hat</em> eine Liste, statt eine zu <em>sein</em>: Seit Java 21
 * erbt jede {@link List} {@code getFirst()} und {@code getLast()} (JEP 431) – mit
 * anderem Rückgabetyp als hier. Wer von einer JDK-Klasse erbt, erbt auch alles, was das
 * JDK ihr künftig hinzufügt.
 */
public class Warteschlange<E> implements Iterable<E> {

    private final List<E> aufträge = new ArrayList<E>();

    public boolean add(E auftrag) {
        return aufträge.add(auftrag);
    }

    public int size() {
        return aufträge.size();
    }

    public boolean isEmpty() {
        return aufträge.isEmpty();
    }

    public Optional<E> getFirst() {
        return isEmpty() ? Optional.<E>empty() : Optional.of(aufträge.get(0));
    }

    public Optional<E> getLast() {
        return isEmpty() ? Optional.<E>empty() : Optional.of(aufträge.get(aufträge.size() - 1));
    }

    @Override
    public Iterator<E> iterator() {
        return aufträge.iterator();
    }
}

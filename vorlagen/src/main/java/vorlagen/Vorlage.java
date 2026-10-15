package vorlagen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Eine Vorlage: feste Textstücke und die Werte dazwischen – getrennt gehalten, bis ein
 * {@link Vorlagenprozessor} entscheidet, wie sie zusammenkommen.
 *
 * <p>Die Platzhalter schreibst Du als {@code {}}:
 *
 * <pre>
 * Vorlage.aus("Bestellung {} für {}", "B-00001", "Jana Müller")
 *   fragmente() → ["Bestellung ", " für ", ""]
 *   werte()     → ["B-00001", "Jana Müller"]
 * </pre>
 *
 * <p>Es gibt immer genau ein Fragment mehr als Werte – auch dann, wenn ein Fragment leer
 * ist. So sah auch {@code StringTemplate} aus, das Java 21 und 22 als Preview hatten.
 */
public final class Vorlage {

    private static final String PLATZHALTER = "{}";

    private final List<String> fragmente;
    private final List<Object> werte;

    private Vorlage(List<String> fragmente, List<Object> werte) {
        this.fragmente = Collections.unmodifiableList(fragmente);
        this.werte = Collections.unmodifiableList(werte);
    }

    /**
     * Zerlegt das Muster an jedem {@code {}} und ordnet die Werte der Reihe nach zu.
     *
     * @throws IllegalArgumentException wenn die Zahl der Werte nicht zur Zahl der
     *                                  Platzhalter passt
     */
    public static Vorlage aus(String muster, Object... werte) {
        List<String> fragmente = new ArrayList<String>();
        int start = 0;
        int treffer;
        while ((treffer = muster.indexOf(PLATZHALTER, start)) >= 0) {
            fragmente.add(muster.substring(start, treffer));
            start = treffer + PLATZHALTER.length();
        }
        fragmente.add(muster.substring(start));
        if (fragmente.size() != werte.length + 1) {
            throw new IllegalArgumentException((fragmente.size() - 1) + " Platzhalter, aber "
                    + werte.length + " Werte: " + muster);
        }
        return new Vorlage(fragmente, new ArrayList<Object>(Arrays.asList(werte)));
    }

    /** Die festen Textstücke, eines mehr als {@link #werte()}. */
    public List<String> fragmente() {
        return fragmente;
    }

    /** Die Werte in der Reihenfolge ihrer Platzhalter. Ein Wert darf {@code null} sein. */
    public List<Object> werte() {
        return werte;
    }
}

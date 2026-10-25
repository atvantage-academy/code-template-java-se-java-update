package neuerungen.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Feste Stammdaten des Shops. Alle Sammlungen sind unveränderlich – so, wie man das
 * mit Java 8 schreibt.
 */
public final class Stammdaten {

    /** Die Zahlarten in der Reihenfolge, in der der Shop sie anbietet. */
    // Umbauen: List.of liefert die unveränderliche Liste direkt.
    public static final List<String> ZAHLARTEN = Collections.unmodifiableList(
            Arrays.asList("Rechnung", "Lastschrift", "Kreditkarte", "PayPal"));

    /** Die Länder, in die der Shop liefert. */
    // Umbauen: Set.of statt Umweg über Arrays.asList und HashSet.
    public static final Set<String> LIEFERLÄNDER = Collections.unmodifiableSet(
            new HashSet<String>(Arrays.asList("DE", "AT", "CH")));

    /** Das Zahlungsziel in Tagen je Zahlart, die eines hat. */
    // Umbauen: Map.of – der statische Block unten entfällt.
    public static final Map<String, Integer> ZAHLUNGSZIEL_TAGE;

    static {
        Map<String, Integer> ziele = new HashMap<String, Integer>();
        ziele.put("Rechnung", 14);
        ziele.put("Lastschrift", 3);
        ZAHLUNGSZIEL_TAGE = Collections.unmodifiableMap(ziele);
    }

    private Stammdaten() {
    }

    /** Die Zahlart, die der Shop zuerst anbietet. */
    public static String bevorzugteZahlart() {
        // Umbauen: getFirst() aus SequencedCollection (Java 21).
        return ZAHLARTEN.get(0);
    }

    /** Die Zahlart, die der Shop zuletzt anbietet. */
    public static String letzteZahlart() {
        // Umbauen: getLast().
        return ZAHLARTEN.get(ZAHLARTEN.size() - 1);
    }

    /** Die Zahlarten in umgekehrter Reihenfolge. */
    public static List<String> zahlartenRückwärts() {
        // Umbauen: reversed() liefert eine Ansicht – keine Kopie nötig.
        List<String> kopie = new ArrayList<String>(ZAHLARTEN);
        Collections.reverse(kopie);
        return kopie;
    }
}

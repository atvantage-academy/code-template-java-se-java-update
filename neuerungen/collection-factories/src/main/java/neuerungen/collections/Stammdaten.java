package neuerungen.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Feste Stammdaten des Shops. Alle Sammlungen sind unveränderlich – angelegt mit den
 * Fabrikmethoden aus Java 9 (JEP 269).
 */
public final class Stammdaten {

    /** Die Zahlarten in der Reihenfolge, in der der Shop sie anbietet. */
    public static final List<String> ZAHLARTEN = List.of("Rechnung", "Lastschrift", "Kreditkarte", "PayPal");

    /** Die Länder, in die der Shop liefert. */
    public static final Set<String> LIEFERLÄNDER = Set.of("DE", "AT", "CH");

    /** Das Zahlungsziel in Tagen je Zahlart, die eines hat. */
    public static final Map<String, Integer> ZAHLUNGSZIEL_TAGE = Map.of("Rechnung", 14, "Lastschrift", 3);

    private Stammdaten() {
    }

    /** Die Zahlart, die der Shop zuerst anbietet. */
    public static String bevorzugteZahlart() {
        return ZAHLARTEN.getFirst();
    }

    /** Die Zahlart, die der Shop zuletzt anbietet. */
    public static String letzteZahlart() {
        return ZAHLARTEN.getLast();
    }

    /** Die Zahlarten in umgekehrter Reihenfolge – eine Ansicht, keine Kopie (JEP 431). */
    public static List<String> zahlartenRückwärts() {
        return ZAHLARTEN.reversed();
    }
}

package neuerungen.typinferenz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Wertet Umsätze je Kunde aus, aus Zeilen der Form „K-2001;18.90“. Lokale Variablen
 * nennen ihren Typ nur, wo er nicht schon dasteht (JEP 286, JEP 323).
 */
public final class Umsatzauswertung {

    private Umsatzauswertung() {
    }

    /** Alle Beträge je Kundennummer, sortiert nach Kundennummer. */
    public static Map<String, List<BigDecimal>> beträgeJeKunde(List<String> zeilen) {
        var ergebnis = new TreeMap<String, List<BigDecimal>>();
        for (var zeile : zeilen) {
            var felder = zeile.split(";");
            var beträge = ergebnis.get(felder[0]);
            if (beträge == null) {
                beträge = new ArrayList<>();
                ergebnis.put(felder[0], beträge);
            }
            beträge.add(new BigDecimal(felder[1]));
        }
        return ergebnis;
    }

    /** Der Umsatz je Kundennummer. */
    public static Map<String, BigDecimal> umsatzJeKunde(List<String> zeilen) {
        var umsätze = new TreeMap<String, BigDecimal>();
        for (var eintrag : beträgeJeKunde(zeilen).entrySet()) {
            var summe = BigDecimal.ZERO;
            for (var betrag : eintrag.getValue()) {
                summe = summe.add(betrag);
            }
            umsätze.put(eintrag.getKey(), summe);
        }
        return umsätze;
    }

    /** Eine Berichtszeile je Kunde, etwa „K-2001: 37.80 EUR“. */
    public static List<String> berichtszeilen(List<String> zeilen) {
        BiFunction<String, BigDecimal, String> zeile =
                (var kundennummer, var umsatz) -> kundennummer + ": " + umsatz + " EUR";
        var bericht = new ArrayList<String>();
        for (var eintrag : umsatzJeKunde(zeilen).entrySet()) {
            bericht.add(zeile.apply(eintrag.getKey(), eintrag.getValue()));
        }
        return bericht;
    }
}

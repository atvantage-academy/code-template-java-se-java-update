package neuerungen.typinferenz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Wertet Umsätze je Kunde aus, aus Zeilen der Form „K-2001;18.90“. Jeder Typ steht
 * ausgeschrieben da – auch dort, wo er rechts vom Gleichheitszeichen schon steht.
 */
public final class Umsatzauswertung {

    private Umsatzauswertung() {
    }

    /** Alle Beträge je Kundennummer, sortiert nach Kundennummer. */
    public static Map<String, List<BigDecimal>> beträgeJeKunde(List<String> zeilen) {
        Map<String, List<BigDecimal>> ergebnis = new TreeMap<String, List<BigDecimal>>();
        for (String zeile : zeilen) {
            String[] felder = zeile.split(";");
            List<BigDecimal> beträge = ergebnis.get(felder[0]);
            if (beträge == null) {
                beträge = new ArrayList<BigDecimal>();
                ergebnis.put(felder[0], beträge);
            }
            beträge.add(new BigDecimal(felder[1]));
        }
        return ergebnis;
    }

    /** Der Umsatz je Kundennummer. */
    public static Map<String, BigDecimal> umsatzJeKunde(List<String> zeilen) {
        Map<String, BigDecimal> umsätze = new TreeMap<String, BigDecimal>();
        for (Map.Entry<String, List<BigDecimal>> eintrag : beträgeJeKunde(zeilen).entrySet()) {
            BigDecimal summe = BigDecimal.ZERO;
            for (BigDecimal betrag : eintrag.getValue()) {
                summe = summe.add(betrag);
            }
            umsätze.put(eintrag.getKey(), summe);
        }
        return umsätze;
    }

    /** Eine Berichtszeile je Kunde, etwa „K-2001: 37.80 EUR“. */
    public static List<String> berichtszeilen(List<String> zeilen) {
        BiFunction<String, BigDecimal, String> zeile =
                (String kundennummer, BigDecimal umsatz) -> kundennummer + ": " + umsatz + " EUR";
        List<String> bericht = new ArrayList<String>();
        for (Map.Entry<String, BigDecimal> eintrag : umsatzJeKunde(zeilen).entrySet()) {
            bericht.add(zeile.apply(eintrag.getKey(), eintrag.getValue()));
        }
        return bericht;
    }
}

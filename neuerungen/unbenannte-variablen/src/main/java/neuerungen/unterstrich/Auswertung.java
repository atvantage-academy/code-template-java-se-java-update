package neuerungen.unterstrich;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Wertet Zeilen aus einer Importdatei aus. An mehreren Stellen braucht der Code eine
 * Variable, die er nie liest – mit Java 8 bekommt sie trotzdem einen Namen.
 */
public final class Auswertung {

    private Auswertung() {
    }

    /** Wie viele der Texte ganze Zahlen sind. */
    public static int anzahlZahlen(List<String> texte) {
        int anzahl = 0;
        for (String text : texte) {
            try {
                Integer.parseInt(text.trim());
                anzahl++;
            } catch (NumberFormatException ignoriert) {
                // keine Zahl – zählt nicht
            }
        }
        return anzahl;
    }

    /** Wie viele Zeilen die Datei hat. */
    public static int anzahlZeilen(Iterable<String> zeilen) {
        int anzahl = 0;
        for (String zeile : zeilen) {
            anzahl++;
        }
        return anzahl;
    }

    /** Die Artikelnummern je Kundennummer, aus Zeilen der Form „K-2001;A-100“. */
    public static Map<String, List<String>> artikelJeKunde(List<String> zeilen) {
        Map<String, List<String>> ergebnis = new TreeMap<String, List<String>>();
        for (String zeile : zeilen) {
            String[] felder = zeile.split(";");
            ergebnis.computeIfAbsent(felder[0], kundennummer -> new ArrayList<String>()).add(felder[1]);
        }
        return ergebnis;
    }

    /** Die Summe aller Mengen, gleich welcher Artikel. */
    public static int gesamtmenge(Map<String, Integer> mengeJeArtikel) {
        int[] summe = {0};
        mengeJeArtikel.forEach((artikelnummer, menge) -> summe[0] += menge);
        return summe[0];
    }
}

package kompatibilitaet.werkzeuge;

import java.util.HashMap;
import java.util.Map;

/**
 * Führt den Bestand je Artikel. Die Zahlen werden von Hand eingepackt – mit den
 * Konstruktoren von {@code Integer} und {@code Double}, wie man es vor Java 5 lernte.
 */
public final class Lagerbestand {

    private final Map<String, Integer> mengen = new HashMap<String, Integer>();

    public void zugang(String artikel, int menge) {
        Integer bisher = mengen.get(artikel);
        mengen.put(artikel, new Integer(bisher == null ? menge : bisher.intValue() + menge));
    }

    public int menge(String artikel) {
        Integer menge = mengen.get(artikel);
        return menge == null ? 0 : menge.intValue();
    }

    public static Double preis(String text) {
        return new Double(text.replace(',', '.'));
    }
}

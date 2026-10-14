package kompatibilitaet.zeitstempel;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Speichert Ereignisse so, wie sie in der Datenbank landen: der Zeitpunkt als
 * Millisekunden seit 1970 in einer {@code BIGINT}-Spalte.
 */
public class EreignisSpeicher {

    private final Map<Long, String> tabelle = new LinkedHashMap<Long, String>();

    public long speichere(Ereignis ereignis) {
        long schlüssel = ereignis.getZeitpunkt().toEpochMilli();
        tabelle.put(schlüssel, ereignis.getArt());
        return schlüssel;
    }

    public Ereignis lade(long schlüssel) {
        return new Ereignis(tabelle.get(schlüssel), Instant.ofEpochMilli(schlüssel));
    }
}

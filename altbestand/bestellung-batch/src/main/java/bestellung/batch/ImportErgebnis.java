package bestellung.batch;

import java.util.Collections;
import java.util.List;

/**
 * Was ein Import gebracht hat.
 */
public final class ImportErgebnis {

    private final int angenommen;
    private final List<String> abgelehnt;
    private final long dauerMillis;

    public ImportErgebnis(int angenommen, List<String> abgelehnt, long dauerMillis) {
        this.angenommen = angenommen;
        this.abgelehnt = Collections.unmodifiableList(abgelehnt);
        this.dauerMillis = dauerMillis;
    }

    public int getAngenommen() {
        return angenommen;
    }

    public List<String> getAbgelehnt() {
        return abgelehnt;
    }

    public long getDauerMillis() {
        return dauerMillis;
    }

    @Override
    public String toString() {
        return angenommen + " angenommen, " + abgelehnt.size() + " abgelehnt, " + dauerMillis + " ms";
    }
}

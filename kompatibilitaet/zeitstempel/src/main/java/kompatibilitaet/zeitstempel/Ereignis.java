package kompatibilitaet.zeitstempel;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

/**
 * Etwas, das zu einem Zeitpunkt passiert ist – auf die Millisekunde genau.
 */
public final class Ereignis {

    /**
     * Seit Java 15 liefert {@link Instant#now()} je nach Betriebssystem mehr als Millisekunden.
     * Gespeichert werden aber Millisekunden – also legt die Uhr die Genauigkeit fest,
     * nicht das JDK. {@link Clock#tickMillis} gibt es seit Java 9.
     */
    private static final Clock UHR = Clock.tickMillis(ZoneOffset.UTC);

    private final String art;
    private final Instant zeitpunkt;

    public Ereignis(String art, Instant zeitpunkt) {
        this.art = art;
        this.zeitpunkt = zeitpunkt;
    }

    public static Ereignis jetzt(String art) {
        return new Ereignis(art, Instant.now(UHR));
    }

    public String getArt() {
        return art;
    }

    public Instant getZeitpunkt() {
        return zeitpunkt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ereignis)) {
            return false;
        }
        Ereignis that = (Ereignis) o;
        return art.equals(that.art) && zeitpunkt.equals(that.zeitpunkt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(art, zeitpunkt);
    }

    @Override
    public String toString() {
        return art + "@" + zeitpunkt;
    }
}

package kompatibilitaet.zeitstempel;

import java.time.Instant;
import java.util.Objects;

/**
 * Etwas, das zu einem Zeitpunkt passiert ist.
 */
public final class Ereignis {

    private final String art;
    private final Instant zeitpunkt;

    public Ereignis(String art, Instant zeitpunkt) {
        this.art = art;
        this.zeitpunkt = zeitpunkt;
    }

    public static Ereignis jetzt(String art) {
        return new Ereignis(art, Instant.now());
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

package bestellwesen.modell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Eine Bestellung eines Kunden mit ihren Positionen. */
public final class Bestellung {

    private final String nummer;
    private final String kunde;
    private final List<Position> positionen;

    public Bestellung(String nummer, String kunde, List<Position> positionen) {
        this.nummer = nummer;
        this.kunde = kunde;
        this.positionen = Collections.unmodifiableList(new ArrayList<Position>(positionen));
    }

    public String getNummer() {
        return nummer;
    }

    public String getKunde() {
        return kunde;
    }

    public List<Position> getPositionen() {
        return positionen;
    }

    /** Die Summe aller Positionen, in Cent. */
    public long summeCent() {
        long summe = 0;
        for (Position position : positionen) {
            summe += position.betragCent();
        }
        return summe;
    }
}

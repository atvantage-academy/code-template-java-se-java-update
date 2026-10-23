package bestellwesen.modell;

import bestellwesen.modell.intern.Betragsformat;

/** Beträge für die Anzeige – der Teil von {@link Betragsformat}, der zur API gehört. */
public final class Betrag {

    private Betrag() {
    }

    /** Ein Betrag in Cent als Euro, etwa „1.234,50 €“. */
    public static String alsEuro(long cent) {
        return Betragsformat.alsEuro(cent);
    }
}

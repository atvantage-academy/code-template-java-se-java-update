package bestellwesen.modell;

/** Eine Zeile einer Bestellung: Artikel, Menge und Preis je Stück in Cent. */
public final class Position {

    private final String artikel;
    private final int menge;
    private final long einzelpreisCent;

    public Position(String artikel, int menge, long einzelpreisCent) {
        this.artikel = artikel;
        this.menge = menge;
        this.einzelpreisCent = einzelpreisCent;
    }

    public String getArtikel() {
        return artikel;
    }

    public int getMenge() {
        return menge;
    }

    public long getEinzelpreisCent() {
        return einzelpreisCent;
    }

    /** Menge mal Preis je Stück, in Cent. */
    public long betragCent() {
        return menge * einzelpreisCent;
    }
}

package bestellung.extern;

/**
 * Fragt beim Lager nach, ob ein Artikel vorrätig ist.
 */
public class Lagerdienst {

    private final long latenzMillis;

    public Lagerdienst(long latenzMillis) {
        this.latenzMillis = latenzMillis;
    }

    public boolean istVorrätig(String artikelnummer) {
        String antwort = new Verbindung(latenzMillis).sende("lager/" + artikelnummer);
        return antwort.startsWith("OK") && !artikelnummer.endsWith("-999");
    }
}

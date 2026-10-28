package bestellung.extern;

/**
 * Fragt bei einer Auskunftei nach, ob ein Kunde kreditwürdig ist.
 */
public class Bonitaetspruefung {

    private final long latenzMillis;

    public Bonitaetspruefung(long latenzMillis) {
        this.latenzMillis = latenzMillis;
    }

    /**
     * Im Beispiel gilt: Kundennummern, die auf 7 enden, sind nicht kreditwürdig.
     */
    public boolean istKreditwürdig(String kundennummer) {
        new Verbindung(latenzMillis).sende("auskunft/" + kundennummer);
        return !kundennummer.endsWith("7");
    }
}

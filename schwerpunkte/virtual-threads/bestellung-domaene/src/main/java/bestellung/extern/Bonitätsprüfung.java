package bestellung.extern;

/**
 * Fragt bei einer Auskunftei nach, ob ein Kunde kreditwürdig ist.
 */
public class Bonitätsprüfung {

    private final long latenzMillis;

    public Bonitätsprüfung(long latenzMillis) {
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

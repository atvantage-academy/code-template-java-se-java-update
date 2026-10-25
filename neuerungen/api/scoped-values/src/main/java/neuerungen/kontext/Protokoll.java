package neuerungen.kontext;

/** Baut Protokollzeilen, die die laufende Anfrage nennen. */
public final class Protokoll {

    private Protokoll() {
    }

    /** Eine Zeile der Form „[A-1] Bestellung angelegt“. */
    public static String zeile(String text) {
        return "[" + Anfragekontext.aktuelleAnfrage() + "] " + text;
    }
}

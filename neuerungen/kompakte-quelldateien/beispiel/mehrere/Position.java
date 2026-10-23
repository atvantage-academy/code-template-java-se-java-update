/** Eine Bestellposition – liegt in einer eigenen Datei neben Bericht.java. */
public record Position(String artikel, int menge, int einzelpreisInCent) {

    public int gesamtInCent() {
        return menge * einzelpreisInCent;
    }
}

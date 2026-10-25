/** Formatiert Beträge und Zeilen des Berichts – liegt in einer eigenen Datei. */
public final class Format {

    private Format() {
    }

    public static String euro(int cent) {
        return String.format("%d,%02d €", cent / 100, cent % 100);
    }

    public static String zeile(Position position) {
        return position.menge() + " × " + position.artikel() + ": " + euro(position.gesamtInCent());
    }
}

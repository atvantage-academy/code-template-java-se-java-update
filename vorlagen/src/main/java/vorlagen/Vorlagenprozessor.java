package vorlagen;

/**
 * Macht aus einer {@link Vorlage} ein Ergebnis. Welches, entscheidet der Prozessor:
 * einen Text, eine SQL-Abfrage, ein JSON-Dokument …
 *
 * <p>Das ist die Idee hinter den String Templates (JEP 430/459): Die Sprache hält Text
 * und Werte getrennt, und erst ein Prozessor wie {@code STR} oder ein eigener für SQL
 * setzt sie zusammen – jeder auf seine Art.
 *
 * @param <R> was der Prozessor liefert
 */
public interface Vorlagenprozessor<R> {

    R verarbeite(Vorlage vorlage);
}

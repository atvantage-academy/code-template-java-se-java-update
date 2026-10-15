package vorlagen;

/**
 * Macht aus einer Vorlage eine {@link SqlAbfrage}: Jeder Wert wird zu einem Parameter
 * {@code ?}, statt in den SQL-Text zu geraten.
 */
public class SqlProzessor implements Vorlagenprozessor<SqlAbfrage> {

    @Override
    public SqlAbfrage verarbeite(Vorlage vorlage) {
        // TODO Übung „Bau Dein eigenes STR“
        throw new UnsupportedOperationException("Noch nicht gebaut – das ist Deine Übung.");
    }
}

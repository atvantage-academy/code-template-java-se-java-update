package vorlagen;

/**
 * Macht aus einer Vorlage eine {@link SqlAbfrage}: Jeder Wert wird zu einem Parameter
 * {@code ?}, statt in den SQL-Text zu geraten.
 */
public class SqlProzessor implements Vorlagenprozessor<SqlAbfrage> {

    @Override
    public SqlAbfrage verarbeite(Vorlage vorlage) {
        // Die Werte kommen nie in den Text: Zwischen die Fragmente tritt nur ein „?“.
        return new SqlAbfrage(String.join("?", vorlage.fragmente()), vorlage.werte());
    }
}

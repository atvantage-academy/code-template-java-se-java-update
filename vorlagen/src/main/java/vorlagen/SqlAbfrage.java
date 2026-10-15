package vorlagen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Eine SQL-Abfrage mit Parametern: der Text mit {@code ?} an jeder Stelle eines Werts,
 * die Werte getrennt davon.
 */
public final class SqlAbfrage {

    private final String sql;
    private final List<Object> parameter;

    public SqlAbfrage(String sql, List<?> parameter) {
        this.sql = sql;
        this.parameter = Collections.unmodifiableList(new ArrayList<Object>(parameter));
    }

    public String sql() {
        return sql;
    }

    public List<Object> parameter() {
        return parameter;
    }

    /**
     * Bereitet die Abfrage auf der Verbindung vor und setzt die Parameter. Die Datenbank
     * bekommt den SQL-Text und die Werte getrennt – ein Wert kann so nie zu SQL werden.
     */
    public PreparedStatement vorbereiten(Connection verbindung) throws SQLException {
        PreparedStatement anweisung = verbindung.prepareStatement(sql);
        for (int i = 0; i < parameter.size(); i++) {
            anweisung.setObject(i + 1, parameter.get(i));
        }
        return anweisung;
    }

    @Override
    public String toString() {
        return sql + " " + parameter;
    }
}

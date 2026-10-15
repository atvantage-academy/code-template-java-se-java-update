package vorlagen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Der SqlProzessor gegen eine echte Datenbank (H2 im Speicher) – und zum Vergleich,
 * was passiert, wenn man SQL einfach als Text zusammensetzt.
 */
@Disabled("Übung „Bau Dein eigenes STR“ – entferne diese Zeile, wenn Du den SqlProzessor baust")
class SqlProzessorTest {

    /** Eine Eingabe, wie sie ein Angreifer in ein Suchfeld tippt. */
    private static final String ANGRIFF = "x' OR '1'='1";

    private final Vorlagenprozessor<SqlAbfrage> SQL = new SqlProzessor();

    private Connection datenbank;

    @BeforeEach
    void datenbankAnlegen() throws SQLException {
        datenbank = DriverManager.getConnection("jdbc:h2:mem:" + UUID.randomUUID());
        Statement anweisung = datenbank.createStatement();
        anweisung.execute("CREATE TABLE bestellung (id VARCHAR(10), kunde VARCHAR(50))");
        anweisung.execute("INSERT INTO bestellung VALUES "
                + "('B-00001', 'Jana Müller'), ('B-00002', 'Ömer Yılmaz'), "
                + "('B-00003', 'Grete Weiß'), ('B-00004', 'Jana Müller')");
        anweisung.close();
    }

    @AfterEach
    void datenbankSchließen() throws SQLException {
        datenbank.close();
    }

    @Test
    void ersetztJedenWertDurchEinFragezeichen() {
        SqlAbfrage abfrage = SQL.verarbeite(Vorlage.aus(
                "SELECT id FROM bestellung WHERE kunde = {} AND id > {}", "Jana Müller", "B-00001"));
        assertEquals("SELECT id FROM bestellung WHERE kunde = ? AND id > ?", abfrage.sql());
        assertEquals(Arrays.<Object>asList("Jana Müller", "B-00001"), abfrage.parameter());
    }

    @Test
    void findetDieBestellungenEinerKundin() throws SQLException {
        SqlAbfrage abfrage = SQL.verarbeite(Vorlage.aus(
                "SELECT id FROM bestellung WHERE kunde = {} ORDER BY id", "Jana Müller"));
        assertEquals(Arrays.asList("B-00001", "B-00004"), ids(abfrage.vorbereiten(datenbank)));
    }

    @Test
    void eingeschleustesSqlBleibtEinWert() throws SQLException {
        SqlAbfrage abfrage = SQL.verarbeite(Vorlage.aus(
                "SELECT id FROM bestellung WHERE kunde = {} ORDER BY id", ANGRIFF));
        assertEquals(new ArrayList<String>(), ids(abfrage.vorbereiten(datenbank)));
    }

    /**
     * Zum Vergleich: dieselbe Abfrage als Text zusammengesetzt. Dieser Test ist GRÜN –
     * und genau das ist das Problem.
     */
    @Test
    void alsTextZusammengesetztLiefertDerAngriffAlleBestellungen() throws SQLException {
        String sql = new TextProzessor().verarbeite(Vorlage.aus(
                "SELECT id FROM bestellung WHERE kunde = '{}' ORDER BY id", ANGRIFF));
        Statement anweisung = datenbank.createStatement();
        List<String> ids = ids(anweisung.executeQuery(sql));
        anweisung.close();
        assertEquals(Arrays.asList("B-00001", "B-00002", "B-00003", "B-00004"), ids);
    }

    private static List<String> ids(PreparedStatement anweisung) throws SQLException {
        try {
            return ids(anweisung.executeQuery());
        } finally {
            anweisung.close();
        }
    }

    private static List<String> ids(ResultSet ergebnis) throws SQLException {
        List<String> ids = new ArrayList<String>();
        while (ergebnis.next()) {
            ids.add(ergebnis.getString("id"));
        }
        return ids;
    }
}

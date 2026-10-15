package vorlagen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Übung „Bau Dein eigenes STR“ – entferne diese Zeile, wenn Du den TextProzessor baust")
class TextProzessorTest {

    private final Vorlagenprozessor<String> STR = new TextProzessor();

    @Test
    void setztDieWerteZwischenDieFragmente() {
        assertEquals("Bestellung B-00001 für Jana Müller",
                STR.verarbeite(Vorlage.aus("Bestellung {} für {}", "B-00001", "Jana Müller")));
    }

    @Test
    void lässtEinenTextOhneWerteUnverändert() {
        assertEquals("Guten Tag", STR.verarbeite(Vorlage.aus("Guten Tag")));
    }

    @Test
    void kommtMitWertenAmAnfangUndAmEndeZurecht() {
        assertEquals("3 × 18.90", STR.verarbeite(Vorlage.aus("{} × {}", 3, new BigDecimal("18.90"))));
    }

    @Test
    void schreibtNullAlsNull() {
        assertEquals("E-Mail: null", STR.verarbeite(Vorlage.aus("E-Mail: {}", (Object) null)));
    }
}

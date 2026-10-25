package neuerungen.kontext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AnfragekontextTest {

    @Test
    void nenntDieLaufendeAnfrage() throws Exception {
        String zeile = Anfragekontext.bearbeite("A-1", () -> Protokoll.zeile("Bestellung angelegt"));
        assertEquals("[A-1] Bestellung angelegt", zeile);
    }

    @Test
    void außerhalbEinerAnfrageStehtEinStrich() {
        assertEquals("[–] Leerlauf", Protokoll.zeile("Leerlauf"));
    }

    @Test
    void nachDerAnfrageIstDerKontextLeer() throws Exception {
        Anfragekontext.bearbeite("A-2", () -> "fertig");
        assertEquals("–", Anfragekontext.aktuelleAnfrage());
    }

    @Test
    void eineInnereAnfrageÜberdecktDieÄußereNurZeitweise() throws Exception {
        String zeilen = Anfragekontext.bearbeite("A-3", () -> {
            String innen = Anfragekontext.bearbeite("A-4", () -> Protokoll.zeile("innen"));
            return innen + " | " + Protokoll.zeile("außen");
        });
        assertEquals("[A-4] innen | [A-3] außen", zeilen);
    }

    @Test
    void auchNachEinemFehlerIstDerKontextLeer() {
        assertThrows(IllegalStateException.class, () -> Anfragekontext.bearbeite("A-5", () -> {
            throw new IllegalStateException("Fremdsystem nicht erreichbar");
        }));
        assertEquals("–", Anfragekontext.aktuelleAnfrage());
    }
}

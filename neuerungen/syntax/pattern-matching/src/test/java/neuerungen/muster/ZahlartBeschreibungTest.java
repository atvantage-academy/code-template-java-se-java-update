package neuerungen.muster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

class ZahlartBeschreibungTest {

    @Test
    void beschreibtEineRechnungMitZahlungsziel() {
        assertEquals("Rechnung, zahlbar innerhalb von 14 Tagen",
                ZahlartBeschreibung.beschreibe(new Rechnung(14)));
    }

    @Test
    void beschreibtEineRechnungOhneZahlungsziel() {
        assertEquals("Rechnung, sofort zahlbar", ZahlartBeschreibung.beschreibe(new Rechnung(0)));
    }

    @Test
    void beschreibtEineLastschriftMaskiert() {
        assertEquals("Lastschrift von ******************3000",
                ZahlartBeschreibung.beschreibe(new Lastschrift("DE89 3704 0044 0532 0130 00")));
    }

    @Test
    void beschreibtEineKreditkarteMaskiert() {
        assertEquals("Kreditkarte ************1111, gültig bis 03/2028",
                ZahlartBeschreibung.beschreibe(new Kreditkarte("4111 1111 1111 1111", YearMonth.of(2028, 3))));
    }

    @Test
    void beschreibtPayPal() {
        assertEquals("PayPal (ada@example.org)", ZahlartBeschreibung.beschreibe(new PayPal("ada@example.org")));
    }

    @Test
    void beschreibtEineFehlendeZahlart() {
        assertEquals("keine Zahlart angegeben", ZahlartBeschreibung.beschreibe(null));
    }

    @Test
    void lehntEineUnbekannteZahlartAb() {
        Zahlart gutschein = new Zahlart() {
        };
        assertThrows(IllegalArgumentException.class, () -> ZahlartBeschreibung.beschreibe(gutschein));
    }
}

package bestellung.domaene;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;

class ZahlartTest {

    private static final BigDecimal HUNDERT = new BigDecimal("100.00");

    @Test
    void gebührenJeZahlart() {
        assertEquals(new BigDecimal("1.50"), GebührenRechner.gebühr(new Rechnung(14), HUNDERT));
        assertEquals(new BigDecimal("0.00"), GebührenRechner.gebühr(new Lastschrift("DE02"), HUNDERT));
        assertEquals(new BigDecimal("1.90"),
                GebührenRechner.gebühr(new Kreditkarte("4111111111111111", YearMonth.of(2028, 3)), HUNDERT));
        assertEquals(new BigDecimal("2.84"), GebührenRechner.gebühr(new PayPal("a@example.com"), HUNDERT));
    }

    @Test
    void beschreibungMaskiertNummern() {
        assertEquals("Lastschrift von ******************2051",
                ZahlartBeschreibung.beschreibe(new Lastschrift("DE02 1203 0000 0000 2020 51")));
        assertEquals("Kreditkarte ************1111, gültig bis 03/2028",
                ZahlartBeschreibung.beschreibe(new Kreditkarte("4111 1111 1111 1111", YearMonth.of(2028, 3))));
    }
}

package neuerungen.versiegelt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class GebührenRechnerTest {

    private static final BigDecimal BETRAG = new BigDecimal("100.00");

    @Test
    void eineRechnungKostetEineFesteGebühr() {
        assertEquals(new BigDecimal("1.50"), GebührenRechner.gebühr(new Rechnung(14), BETRAG));
    }

    @Test
    void eineLastschriftKostetNichts() {
        assertEquals(new BigDecimal("0.00"),
                GebührenRechner.gebühr(new Lastschrift("DE89 3704 0044 0532 0130 00"), BETRAG));
    }

    @Test
    void eineKreditkarteKostetEinenAnteil() {
        assertEquals(new BigDecimal("1.90"),
                GebührenRechner.gebühr(new Kreditkarte("4111 1111 1111 1111"), BETRAG));
    }

    @Test
    void payPalKostetFixUndAnteil() {
        assertEquals(new BigDecimal("2.84"), GebührenRechner.gebühr(new PayPal("ada@example.org"), BETRAG));
    }
}

package neuerungen.doku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class RabattrechnerTest {

    @Test
    void unterDerErstenStufeGibtEsKeinenRabatt() {
        assertEquals(new BigDecimal("0.00"), Rabattrechner.rabatt(new BigDecimal("99.99")));
    }

    @Test
    void abHundertEuroGibtEsFünfProzent() {
        assertEquals(new BigDecimal("12.50"), Rabattrechner.rabatt(new BigDecimal("250.00")));
    }

    @Test
    void abFünfhundertEuroGibtEsZehnProzent() {
        assertEquals(new BigDecimal("50.00"), Rabattrechner.rabatt(new BigDecimal("500.00")));
    }

    @Test
    void lehntEinenNegativenWarenwertAb() {
        assertThrows(IllegalArgumentException.class, () -> Rabattrechner.rabatt(new BigDecimal("-1")));
    }
}

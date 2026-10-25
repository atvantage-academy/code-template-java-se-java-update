package neuerungen.interfaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PreisregelTest {

    private final Preisregel regel = new Regelsteuersatz();

    @Test
    void rechnetBruttoAufCentGenau() {
        assertEquals(new BigDecimal("11.90"), regel.brutto(new BigDecimal("10.00")));
        assertEquals(new BigDecimal("22.48"), regel.brutto(new BigDecimal("18.89")));
    }

    @Test
    void ziehtDenRabattVorDerSteuerAb() {
        assertEquals(new BigDecimal("10.71"), regel.bruttoMitRabatt(new BigDecimal("10.00"), 10));
        assertEquals(new BigDecimal("11.90"), regel.bruttoMitRabatt(new BigDecimal("10.00"), 0));
    }

    @Test
    void jedeRegelNenntNurIhrenSteuersatz() {
        Preisregel ermäßigt = () -> new BigDecimal("0.07");
        assertEquals(new BigDecimal("10.70"), ermäßigt.brutto(new BigDecimal("10.00")));
    }
}

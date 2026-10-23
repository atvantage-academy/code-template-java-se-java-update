package bestellung.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LasttestTest {

    @Test
    void ohneAngabeSindHundertGleichzeitigUnterwegs() {
        assertEquals(100, Lasttest.gleichzeitig(null));
        assertEquals(100, Lasttest.gleichzeitig(" "));
    }

    @Test
    void dieAngabeGilt() {
        assertEquals(20, Lasttest.gleichzeitig("20"));
    }

    @Test
    void wenigerAlsEinsIstUngültig() {
        assertThrows(IllegalArgumentException.class, () -> Lasttest.gleichzeitig("0"));
    }
}

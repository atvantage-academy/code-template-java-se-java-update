package neuerungen.konstruktoren;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BestellnummerTest {

    @Test
    void normalisiertDieEingabe() {
        assertEquals("B-1042", new Bestellnummer("  b-1042 ").wert());
    }

    @Test
    void lehntEineLeereEingabeAb() {
        IllegalArgumentException fehler = assertThrows(IllegalArgumentException.class, () -> new Bestellnummer("  "));
        assertEquals("Bestellnummer fehlt", fehler.getMessage());
    }

    @Test
    void lehntEineFehlendeEingabeAb() {
        IllegalArgumentException fehler = assertThrows(IllegalArgumentException.class, () -> new Bestellnummer(null));
        assertEquals("Bestellnummer fehlt", fehler.getMessage());
    }

    @Test
    void lehntEinFalschesPräfixAb() {
        assertThrows(IllegalArgumentException.class, () -> new Bestellnummer("L-7"));
    }
}

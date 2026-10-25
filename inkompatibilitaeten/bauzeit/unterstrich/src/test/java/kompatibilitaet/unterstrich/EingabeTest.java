package kompatibilitaet.unterstrich;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EingabeTest {

    @Test
    void liestZahlen() {
        assertEquals(42, Eingabe.zahlOderStandard(" 42 ", 0));
        assertEquals(7, Eingabe.zahlOderStandard("sieben", 7));
    }

    @Test
    void liefertDenErstenBuchstaben() {
        assertEquals("J", Eingabe.ersterBuchstabe("  java"));
        assertEquals("", Eingabe.ersterBuchstabe("   "));
    }
}

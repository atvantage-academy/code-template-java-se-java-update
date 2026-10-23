package bestellwesen.modell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import bestellwesen.modell.intern.Betragsformat;

class BestellungTest {

    @Test
    void summiertAllePositionen() {
        Bestellung bestellung = new Bestellung("B-1", "Muster GmbH", Arrays.asList(
                new Position("Tasse", 4, 1250), new Position("Kanne", 1, 3990)));
        assertEquals(8990, bestellung.summeCent());
    }

    @Test
    void schreibtBeträgeAlsEuro() {
        assertEquals("1.234,05 €", Betragsformat.alsEuro(123405));
        assertEquals("0,99 €", Betragsformat.alsEuro(99));
    }
}

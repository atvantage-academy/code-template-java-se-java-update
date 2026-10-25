package kompatibilitaet.bezeichner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BezeichnerTest {

    @Test
    void erzeugtQuadratzahlen() {
        assertEquals(Arrays.asList(1, 4, 9, 16), new Quadratzahlen(4).alle());
    }

    @Test
    void protokolliertMitNummer() {
        Protokoll protokoll = new Protokoll();
        protokoll.notiere(" Start ");
        protokoll.notiere("Ende");
        assertEquals(Arrays.asList("1: Start", "2: Ende"), protokoll.getEinträge());
    }
}

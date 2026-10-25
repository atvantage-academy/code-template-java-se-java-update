package kompatibilitaet.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class KapazitätsmonitorTest {

    @Test
    void misstDieReservierteKapazität() {
        ArrayList<String> liste = new ArrayList<String>(100);
        liste.add("eins");
        liste.add("zwei");

        assertEquals(100, Kapazitätsmonitor.kapazität(liste));
        assertEquals(0.02, Kapazitätsmonitor.auslastung(liste), 0.0001);
    }
}

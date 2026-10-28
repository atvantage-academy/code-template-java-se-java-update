package kompatibilitaet.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class KapazitaetsmonitorTest {

    @Test
    void misstDieReservierteKapazität() {
        ArrayList<String> liste = new ArrayList<String>(100);
        liste.add("eins");
        liste.add("zwei");

        assertEquals(100, Kapazitaetsmonitor.kapazität(liste));
        assertEquals(0.02, Kapazitaetsmonitor.auslastung(liste), 0.0001);
    }
}

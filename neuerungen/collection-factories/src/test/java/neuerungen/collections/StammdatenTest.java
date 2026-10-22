package neuerungen.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class StammdatenTest {

    @Test
    void zahlartenStehenInDerAngebotenenReihenfolge() {
        assertEquals(Arrays.asList("Rechnung", "Lastschrift", "Kreditkarte", "PayPal"), Stammdaten.ZAHLARTEN);
        assertEquals("Rechnung", Stammdaten.bevorzugteZahlart());
        assertEquals("PayPal", Stammdaten.letzteZahlart());
        assertEquals(Arrays.asList("PayPal", "Kreditkarte", "Lastschrift", "Rechnung"),
                Stammdaten.zahlartenRückwärts());
    }

    @Test
    void lieferländerUndZahlungsziele() {
        assertEquals(new HashSet<String>(Arrays.asList("DE", "AT", "CH")), Stammdaten.LIEFERLÄNDER);
        assertEquals(Integer.valueOf(14), Stammdaten.ZAHLUNGSZIEL_TAGE.get("Rechnung"));
        assertEquals(Integer.valueOf(3), Stammdaten.ZAHLUNGSZIEL_TAGE.get("Lastschrift"));
        assertNull(Stammdaten.ZAHLUNGSZIEL_TAGE.get("PayPal"));
    }

    @Test
    void stammdatenSindUnveränderlich() {
        assertThrows(UnsupportedOperationException.class, () -> Stammdaten.ZAHLARTEN.add("Bar"));
        assertThrows(UnsupportedOperationException.class, () -> Stammdaten.LIEFERLÄNDER.add("FR"));
        assertThrows(UnsupportedOperationException.class, () -> Stammdaten.ZAHLUNGSZIEL_TAGE.put("PayPal", 0));
    }

    @Test
    void umgekehrteReiheLässtDieStammdatenUnberührt() {
        Stammdaten.zahlartenRückwärts();
        assertEquals("Rechnung", Stammdaten.ZAHLARTEN.get(0));
    }
}

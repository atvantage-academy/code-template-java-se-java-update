package bestellung.domaene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;

/**
 * Was das Modell zusagt – vor und nach dem Umbau auf Records und versiegelte Typen.
 */
class DomänenmodellTest {

    @Test
    void positionenMitGleichenWertenSindGleich() {
        var a = new Position("A-100", "Kaffeebohnen, 1 kg", 2, new BigDecimal("18.90"));
        var b = new Position("A-100", "Kaffeebohnen, 1 kg", 2, new BigDecimal("18.90"));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void einePositionOhneMengeGibtEsNicht() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position("A-100", "Kaffeebohnen, 1 kg", 0, new BigDecimal("18.90")));
    }

    @Test
    void einenKundenOhneNamenGibtEsNicht() {
        assertThrows(NullPointerException.class, () -> new Kunde("K-1001", null, null));
    }

    @Test
    void dieKreditkarteVerrätIhreNummerNicht() {
        var karte = new Kreditkarte("4111 1111 1111 1111", YearMonth.of(2028, 3));
        assertFalse(karte.toString().contains("4111 1111"), karte.toString());
    }

    @Test
    void esGibtGenauVierZahlarten() {
        assertTrue(Zahlart.class.isSealed());
        assertEquals(4, Zahlart.class.getPermittedSubclasses().length);
    }
}

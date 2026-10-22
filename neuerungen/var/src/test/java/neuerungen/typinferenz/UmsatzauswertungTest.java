package neuerungen.typinferenz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class UmsatzauswertungTest {

    private static final List<String> ZEILEN = Arrays.asList(
            "K-2002;5.00", "K-2001;18.90", "K-2001;18.90", "K-2003;0.99");

    @Test
    void sammeltBeträgeJeKunde() {
        Map<String, List<BigDecimal>> beträge = Umsatzauswertung.beträgeJeKunde(ZEILEN);
        assertEquals(Arrays.asList("K-2001", "K-2002", "K-2003"), Arrays.asList(beträge.keySet().toArray()));
        assertEquals(Arrays.asList(new BigDecimal("18.90"), new BigDecimal("18.90")), beträge.get("K-2001"));
    }

    @Test
    void summiertDenUmsatzJeKunde() {
        assertEquals(new BigDecimal("37.80"), Umsatzauswertung.umsatzJeKunde(ZEILEN).get("K-2001"));
    }

    @Test
    void schreibtEineZeileJeKunde() {
        assertEquals(Arrays.asList("K-2001: 37.80 EUR", "K-2002: 5.00 EUR", "K-2003: 0.99 EUR"),
                Umsatzauswertung.berichtszeilen(ZEILEN));
    }
}

package kompatibilitaet.datumsformat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class LieferscheinTest {

    @Test
    void schreibtDieKopfzeile() {
        assertEquals("Lieferschein B-00001 vom Mo, 14. Sep 2026 über 1.234,50 €",
                Lieferschein.kopfzeile("B-00001", LocalDate.of(2026, 9, 14), new BigDecimal("1234.50")));
    }
}

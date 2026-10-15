package vorlagen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class VorlageTest {

    @Test
    void zerlegtDasMusterAnDenPlatzhaltern() {
        Vorlage vorlage = Vorlage.aus("Bestellung {} für {}", "B-00001", "Jana Müller");
        assertEquals(Arrays.asList("Bestellung ", " für ", ""), vorlage.fragmente());
        assertEquals(Arrays.<Object>asList("B-00001", "Jana Müller"), vorlage.werte());
    }

    @Test
    void ohnePlatzhalterGibtEsEinFragmentUndKeinenWert() {
        Vorlage vorlage = Vorlage.aus("Guten Tag");
        assertEquals(Collections.singletonList("Guten Tag"), vorlage.fragmente());
        assertEquals(Collections.emptyList(), vorlage.werte());
    }

    @Test
    void lehntFalscheZahlVonWertenAb() {
        assertThrows(IllegalArgumentException.class, () -> Vorlage.aus("{} und {}", "eins"));
    }
}

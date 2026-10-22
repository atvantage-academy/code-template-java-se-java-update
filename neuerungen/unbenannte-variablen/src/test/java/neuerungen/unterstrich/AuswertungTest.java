package neuerungen.unterstrich;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AuswertungTest {

    @Test
    void zähltNurGanzeZahlen() {
        assertEquals(2, Auswertung.anzahlZahlen(Arrays.asList("12", " 7 ", "zwölf", "1.5")));
    }

    @Test
    void zähltZeilen() {
        assertEquals(3, Auswertung.anzahlZeilen(Arrays.asList("a", "b", "c")));
    }

    @Test
    void sammeltArtikelJeKunde() {
        Map<String, List<String>> ergebnis = Auswertung.artikelJeKunde(
                Arrays.asList("K-2001;A-100", "K-2002;A-200", "K-2001;A-300"));
        assertEquals(Arrays.asList("A-100", "A-300"), ergebnis.get("K-2001"));
        assertEquals(Arrays.asList("A-200"), ergebnis.get("K-2002"));
    }

    @Test
    void summiertAlleMengen() {
        Map<String, Integer> mengen = new LinkedHashMap<String, Integer>();
        mengen.put("A-100", 2);
        mengen.put("A-200", 5);
        assertEquals(7, Auswertung.gesamtmenge(mengen));
    }
}

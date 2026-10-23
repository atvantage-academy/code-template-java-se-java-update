package neuerungen.records;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class WertklassenTest {

    private static final Kunde KUNDE = new Kunde("K-1001", "Ada Lovelace", null);
    private static final Position KAFFEE =
            new Position("A-100", "Kaffeebohnen, 1 kg", 2, new BigDecimal("18.90"));
    private static final Position FILTER =
            new Position("A-200", "Filtertüten", 1, new BigDecimal("2.49"));

    @Test
    void positionenMitGleichenWertenSindGleich() {
        Position nochEinmal = new Position("A-100", "Kaffeebohnen, 1 kg", 2, new BigDecimal("18.90"));
        assertEquals(KAFFEE, nochEinmal);
        assertEquals(KAFFEE.hashCode(), nochEinmal.hashCode());
        assertNotEquals(KAFFEE, FILTER);
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
    void kundenOhneEmailSindGleich() {
        assertEquals(KUNDE, new Kunde("K-1001", "Ada Lovelace", null));
    }

    @Test
    void dieKreditkarteVerrätIhreNummerNicht() {
        Kreditkarte karte = new Kreditkarte("4111 1111 1111 1111", YearMonth.of(2028, 3));
        assertFalse(karte.toString().contains("4111 1111"), karte.toString());
        assertEquals("Abgebucht von Karte ****1111, gültig bis 2028-03", Rechnungstext.zahlungshinweis(karte));
    }

    @Test
    void eineBestellungBleibtDieselbeAuchWennSieStorniertWird() {
        Bestellung bestellung = new Bestellung("B-1", KUNDE, Arrays.asList(KAFFEE));
        Bestellung gleicheNummer = new Bestellung("B-1", KUNDE, Arrays.asList(KAFFEE));
        bestellung.stornieren();
        assertEquals(Status.STORNIERT, bestellung.getStatus());
        assertEquals(gleicheNummer, bestellung);
    }

    @Test
    void derRechnungstextNenntJedePositionUndDieSumme() {
        Bestellung bestellung = new Bestellung("B-2", KUNDE, Arrays.asList(KAFFEE, FILTER));
        assertEquals(Arrays.asList(
                "Rechnung B-2 für Ada Lovelace (K-1001)",
                "2 × Kaffeebohnen, 1 kg [A-100] à 18.90 EUR = 37.80 EUR",
                "1 × Filtertüten [A-200] à 2.49 EUR = 2.49 EUR",
                "Gesamt: 40.29 EUR"), Rechnungstext.zeilen(bestellung));
    }
}

package bestellung.domaene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BestellServiceTest {

    private BestellService service;

    @BeforeEach
    void beispieldatenAnlegen() {
        service = new BestellService(new InMemoryBestellRepository());
        Beispieldaten.befülle(service);
    }

    @Test
    void vergibtFortlaufendeIds() {
        List<Bestellung> alle = service.alleNeuesteZuerst();
        assertEquals(4, alle.size());
        assertEquals("B-00004", alle.get(0).getId());
        assertEquals("B-00001", alle.get(3).getId());
    }

    @Test
    void sortiertBeiGleichemDatumNachId() {
        List<Bestellung> alle = service.alleNeuesteZuerst();
        assertEquals("B-00002", alle.get(1).getId());
        assertEquals("B-00003", alle.get(2).getId());
    }

    @Test
    void gesamtbetragEnthältDieGebührDerZahlart() {
        Bestellung aufRechnung = service.finde("B-00001").get();
        assertEquals(new BigDecimal("51.80"), service.gesamtbetrag(aufRechnung));
    }

    @Test
    void stornierteBestellungenZählenNichtZumUmsatz() {
        service.stornieren("B-00002");
        Map<String, BigDecimal> umsatz = service.umsatzJeZahlart();
        assertEquals(3, umsatz.size());
        assertTrue(!umsatz.containsKey("Kreditkarte"));
    }

    @Test
    void versendeteBestellungLässtSichNichtStornieren() {
        service.finde("B-00001").get().setStatus(Status.VERSENDET);
        assertThrows(IllegalStateException.class, () -> service.stornieren("B-00001"));
    }

    @Test
    void bestellungOhnePositionenWirdAbgelehnt() {
        Bestellung leer = new Bestellung(Beispieldaten.KUNDEN.get(0), Collections.<Position>emptyList(),
                new Rechnung(14), LocalDate.of(2026, 9, 10));
        assertThrows(IllegalArgumentException.class, () -> service.anlegen(leer));
    }

    @Test
    void berichtNenntJedeBestellung() {
        String bericht = service.bericht();
        for (int i = 1; i <= 4; i++) {
            assertTrue(bericht.contains("B-0000" + i), "fehlt: B-0000" + i);
        }
    }

    /**
     * Hält den Bericht zeichengenau fest: Wer ihn umbaut, sieht hier sofort, ob sich am
     * Ergebnis etwas geändert hat. Verglichen wird Zeile für Zeile, damit das
     * Zeilenende des Betriebssystems keine Rolle spielt.
     */
    @Test
    void berichtBleibtZeichengenauGleich() {
        String[] erwartet = {
                "Bestellbericht",
                "==============",
                "",
                "B-00004  2026-09-07 NEU               91.57 EUR",
                "B-00002  2026-09-03 NEU              661.33 EUR",
                "B-00003  2026-09-03 NEU              118.47 EUR",
                "B-00001  2026-09-01 NEU               51.80 EUR",
                "",
                "Umsatz je Zahlart:",
                "  Kreditkarte      661.33 EUR",
                "  Lastschrift      118.47 EUR",
                "  PayPal            91.57 EUR",
                "  Rechnung          51.80 EUR"
        };
        assertEquals(Arrays.asList(erwartet), Arrays.asList(service.bericht().split("\\R")));
    }
}

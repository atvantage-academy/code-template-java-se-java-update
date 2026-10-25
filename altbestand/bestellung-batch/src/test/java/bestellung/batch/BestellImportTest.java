package bestellung.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import bestellung.domaene.BestellService;
import bestellung.domaene.Bestellung;
import bestellung.domaene.InMemoryBestellRepository;
import bestellung.extern.Bonitätsprüfung;

class BestellImportTest {

    @Test
    void liestDieBeispieldatei() throws IOException {
        List<Bestellung> bestellungen = new CsvLeser().lies(getClass().getResourceAsStream("/bestellungen.csv"));
        assertEquals(200, bestellungen.size());
        assertEquals("K-3000", bestellungen.get(0).getKunde().getKundennummer());
    }

    @Test
    void lehntNichtKreditwürdigeKundenAb() throws Exception {
        List<Bestellung> bestellungen = new CsvLeser().lies(getClass().getResourceAsStream("/bestellungen.csv"));
        BestellService service = new BestellService(new InMemoryBestellRepository());

        ImportErgebnis ergebnis = new BestellImport(service, new Bonitätsprüfung(1)).importiere(bestellungen);

        assertEquals(180, ergebnis.getAngenommen());
        assertEquals(20, ergebnis.getAbgelehnt().size());
        assertTrue(ergebnis.getAbgelehnt().contains("K-3007"));
        assertEquals(180, service.alleNeuesteZuerst().size());
    }
}

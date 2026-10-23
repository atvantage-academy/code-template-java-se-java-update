package bestellwesen.anwendung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ExportlaufTest {

    @Test
    void findetDasCsvFormatZurLaufzeit() {
        Map<String, String> exporte = Exportlauf.exportiereAlle(Exportlauf.beispielbestand());
        assertTrue(exporte.containsKey("csv"), "gefundene Formate: " + exporte.keySet());
    }

    @Test
    void exportiertJedeBestellungAlsZeile() {
        String csv = Exportlauf.exportiereAlle(Exportlauf.beispielbestand()).get("csv");
        assertEquals("nummer;kunde;positionen;summe\n"
                + "B-1001;Muster GmbH;2;89,90 €\n"
                + "B-1002;Beispiel AG;1;1.188,00 €\n", csv);
    }
}

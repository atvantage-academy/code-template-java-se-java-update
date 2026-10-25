package kompatibilitaet.jaxb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class XmlExportTest {

    @Test
    void schreibtUndLiestXml() {
        String xml = XmlExport.alsXml(new BestellungXml("B-00001", "K-1001", new BigDecimal("51.80")));

        assertTrue(xml.contains("<bestellung id=\"B-00001\">"), xml);
        assertTrue(xml.contains("<kundennummer>K-1001</kundennummer>"), xml);

        BestellungXml gelesen = XmlExport.ausXml(xml);
        assertEquals("K-1001", gelesen.kundennummer);
        assertEquals(new BigDecimal("51.80"), gelesen.betrag);
    }
}

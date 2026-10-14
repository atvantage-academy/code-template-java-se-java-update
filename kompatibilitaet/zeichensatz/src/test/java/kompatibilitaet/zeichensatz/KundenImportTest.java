package kompatibilitaet.zeichensatz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.jupiter.api.Test;

class KundenImportTest {

    @Test
    void liestUmlauteAusDemAltsystem() throws Exception {
        Map<String, String> kunden = KundenImport.lies(datei("/kunden-altsystem.csv"));

        assertEquals("Jana Müller", kunden.get("K-1001"));
        assertEquals("Grete Weiß", kunden.get("K-1003"));
    }

    private File datei(String name) throws URISyntaxException {
        return new File(getClass().getResource(name).toURI());
    }
}

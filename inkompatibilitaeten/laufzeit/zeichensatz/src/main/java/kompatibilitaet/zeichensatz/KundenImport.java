package kompatibilitaet.zeichensatz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Liest die Kundenliste, die das Altsystem jede Nacht als CSV ablegt.
 * Der {@link FileReader} nimmt dafür den Standard-Zeichensatz der JVM.
 */
public final class KundenImport {

    private KundenImport() {
    }

    /**
     * @return Kundennummer → Name
     */
    public static Map<String, String> lies(File datei) throws IOException {
        Map<String, String> kunden = new LinkedHashMap<String, String>();
        BufferedReader leser = new BufferedReader(new FileReader(datei));
        try {
            String zeile = leser.readLine();
            while ((zeile = leser.readLine()) != null) {
                String[] felder = zeile.split(";");
                kunden.put(felder[0], felder[1]);
            }
        } finally {
            leser.close();
        }
        return kunden;
    }
}

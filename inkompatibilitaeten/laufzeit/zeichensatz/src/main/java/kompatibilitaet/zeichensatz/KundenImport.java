package kompatibilitaet.zeichensatz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Liest die Kundenliste, die das Altsystem jede Nacht als CSV ablegt.
 * Das Altsystem schreibt windows-1252 – das steht jetzt im Code, statt sich auf den
 * Standard-Zeichensatz der JVM zu verlassen, der seit Java 18 überall UTF-8 ist.
 */
public final class KundenImport {

    private static final Charset ALTSYSTEM = Charset.forName("windows-1252");

    private KundenImport() {
    }

    /**
     * @return Kundennummer → Name
     */
    public static Map<String, String> lies(File datei) throws IOException {
        Map<String, String> kunden = new LinkedHashMap<String, String>();
        BufferedReader leser = new BufferedReader(new FileReader(datei, ALTSYSTEM));
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

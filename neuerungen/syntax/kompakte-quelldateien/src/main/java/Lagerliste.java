// Umbauen: Eine kompakte Quelldatei importiert das Modul java.base von selbst.
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Ein kleines Werkzeug: fasst Lagerbewegungen je Artikel zusammen. Es liegt ohne Paket
 * in src/main/java, so wie später die kompakte Quelldatei, die kein Paket kennt.
 *
 * Geschrieben im Stil von Java 8 – mit allem, was eine Klasse dafür braucht.
 */
// Umbauen: Die Klasse entfällt, aus main wird „void main()“.
public class Lagerliste {

    public static void main(String[] args) {
        List<String> bewegungen = Arrays.asList(
                "A-100;Kaffeebecher;12",
                "A-200;Teekanne;3",
                "A-100;Kaffeebecher;8",
                "A-300;Untersetzer;40");

        Map<String, String> namen = new TreeMap<String, String>();
        Map<String, Integer> bestand = new TreeMap<String, Integer>();
        for (String bewegung : bewegungen) {
            String[] felder = bewegung.split(";");
            namen.put(felder[0], felder[1]);
            Integer bisher = bestand.get(felder[0]);
            bestand.put(felder[0], (bisher == null ? 0 : bisher) + Integer.parseInt(felder[2]));
        }

        for (Map.Entry<String, Integer> eintrag : bestand.entrySet()) {
            // Umbauen: IO.println statt System.out.println.
            System.out.println(eintrag.getKey() + " " + namen.get(eintrag.getKey()) + ": " + eintrag.getValue());
        }
    }
}

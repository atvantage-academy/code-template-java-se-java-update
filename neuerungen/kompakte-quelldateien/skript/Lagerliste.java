import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Ein kleines Werkzeug: fasst Lagerbewegungen je Artikel zusammen. Es gehört zu keinem
 * Build, Java startet es direkt aus dem Quelltext (JEP 330, ab Java 11):
 *
 *     java neuerungen/kompakte-quelldateien/skript/Lagerliste.java
 *
 * Geschrieben im Stil von Java 8 – mit allem, was eine Klasse dafür braucht.
 */
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
            System.out.println(eintrag.getKey() + " " + namen.get(eintrag.getKey()) + ": " + eintrag.getValue());
        }
    }
}

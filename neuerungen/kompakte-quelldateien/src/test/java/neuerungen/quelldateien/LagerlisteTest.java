package neuerungen.quelldateien;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * Startet das Werkzeug skript/Lagerliste.java so, wie man es von Hand startet – mit dem
 * Programm „java“ des JDK, das die Tests ausführt – und prüft seine Ausgabe.
 */
class LagerlisteTest {

    private static final Path SKRIPT = Paths.get("skript", "Lagerliste.java");

    @Test
    void fasstDieBewegungenJeArtikelZusammen() throws Exception {
        int jdk = jdkVersion();
        assumeTrue(jdk >= 11, "Quelldateien direkt starten geht erst ab JDK 11");
        String quelltext = new String(Files.readAllBytes(SKRIPT), StandardCharsets.UTF_8);
        boolean kompakt = !quelltext.contains("class Lagerliste");
        assumeTrue(!kompakt || jdk >= 25, "Die kompakte Fassung startet erst ab JDK 25");

        String ausgabe = starte(SKRIPT);

        assertEquals("A-100 Kaffeebecher: 20\nA-200 Teekanne: 3\nA-300 Untersetzer: 40\n", ausgabe);
    }

    private static String starte(Path skript) throws Exception {
        String java = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        Process prozess = new ProcessBuilder(java, "-Dstdout.encoding=UTF-8", skript.toString())
                .redirectErrorStream(true)
                .start();
        String ausgabe = lies(prozess.getInputStream());
        prozess.waitFor(60, TimeUnit.SECONDS);
        assertEquals(0, prozess.exitValue(), ausgabe);
        return ausgabe.replace("\r\n", "\n");
    }

    private static String lies(InputStream strom) throws Exception {
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        byte[] block = new byte[4096];
        int gelesen;
        while ((gelesen = strom.read(block)) != -1) {
            puffer.write(block, 0, gelesen);
        }
        return new String(puffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private static int jdkVersion() {
        String version = System.getProperty("java.specification.version");
        return version.startsWith("1.") ? Integer.parseInt(version.substring(2)) : Integer.parseInt(version);
    }
}

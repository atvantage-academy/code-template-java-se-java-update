package kompatibilitaet.securitymanager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Führt Plugins von Drittanbietern aus. Ruft ein Plugin {@code System.exit(…)},
 * soll nicht die ganze Anwendung enden.
 * <p>
 * Früher hat das ein Security Manager verhindert. Den gibt es nicht mehr: seit Java 17
 * zur Entfernung vorgesehen (JEP 411), seit Java 24 dauerhaft abgeschaltet (JEP 486) –
 * ohne Ersatz im JDK. Die Abgrenzung übernimmt jetzt das Betriebssystem: Jedes Plugin
 * läuft in einer eigenen JVM. Ein {@code System.exit} beendet nur diese.
 */
public final class PluginAusfuehrer {

    private static final long ZEITLIMIT_SEKUNDEN = 30;

    private PluginAusfuehrer() {
    }

    /**
     * @return was das Plugin ausgegeben hat
     * @throws PluginBeendetException wenn das Plugin mit einem Status ungleich 0 endet
     */
    public static String führeAus(Class<? extends Runnable> plugin) throws IOException, InterruptedException {
        Process prozess = new ProcessBuilder(
                Path.of(System.getProperty("java.home"), "bin", "java").toString(),
                "-cp", System.getProperty("java.class.path"),
                PluginStarter.class.getName(), plugin.getName())
                .redirectErrorStream(true)
                .start();
        String ausgabe;
        try (InputStream ein = prozess.getInputStream()) {
            ausgabe = new String(ein.readAllBytes(), StandardCharsets.UTF_8).strip();
        }
        if (!prozess.waitFor(ZEITLIMIT_SEKUNDEN, TimeUnit.SECONDS)) {
            prozess.destroyForcibly();
            throw new IllegalStateException("Plugin " + plugin.getName() + " hat das Zeitlimit überschritten");
        }
        if (prozess.exitValue() != 0) {
            throw new PluginBeendetException(prozess.exitValue());
        }
        return ausgabe;
    }

    /**
     * Wird geworfen, wenn ein Plugin seine JVM mit einem Fehlerstatus beendet hat.
     */
    public static class PluginBeendetException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        private final int status;

        PluginBeendetException(int status) {
            super("Plugin hat mit Status " + status + " geendet");
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }
}

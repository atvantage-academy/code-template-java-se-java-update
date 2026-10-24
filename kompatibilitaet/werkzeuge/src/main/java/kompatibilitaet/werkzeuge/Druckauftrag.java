package kompatibilitaet.werkzeuge;

import java.io.File;
import java.io.IOException;

/**
 * Ein Druckauftrag schreibt in eine temporäre Datei. Aufgeräumt wird sie, wenn der
 * Garbage Collector den Auftrag einsammelt – in {@code finalize()}.
 */
public class Druckauftrag {

    private final File datei;

    public Druckauftrag() throws IOException {
        datei = File.createTempFile("druckauftrag", ".pdf");
    }

    public File getDatei() {
        return datei;
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            datei.delete();
        } finally {
            super.finalize();
        }
    }
}

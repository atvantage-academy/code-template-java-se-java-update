package kompatibilitaet.securitymanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PluginAusfuehrerTest {

    public static class GrüßendesPlugin implements Runnable {

        @Override
        public void run() {
            System.out.println("Hallo aus dem Plugin");
        }
    }

    public static class BeendendesPlugin implements Runnable {

        @Override
        public void run() {
            System.exit(3);
        }
    }

    @Test
    void führtEinPluginAus() throws Exception {
        assertEquals("Hallo aus dem Plugin", PluginAusfuehrer.führeAus(GrüßendesPlugin.class));
    }

    @Test
    void verhindertDassEinPluginDieAnwendungBeendet() {
        PluginAusfuehrer.PluginBeendetException e = assertThrows(PluginAusfuehrer.PluginBeendetException.class,
                () -> PluginAusfuehrer.führeAus(BeendendesPlugin.class));
        assertEquals(3, e.getStatus());
    }
}

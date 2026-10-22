package kompatibilitaet.securitymanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PluginAusführerTest {

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
        assertEquals("Hallo aus dem Plugin", PluginAusführer.führeAus(GrüßendesPlugin.class));
    }

    @Test
    void verhindertDassEinPluginDieAnwendungBeendet() {
        PluginAusführer.PluginBeendetException e = assertThrows(PluginAusführer.PluginBeendetException.class,
                () -> PluginAusführer.führeAus(BeendendesPlugin.class));
        assertEquals(3, e.getStatus());
    }
}

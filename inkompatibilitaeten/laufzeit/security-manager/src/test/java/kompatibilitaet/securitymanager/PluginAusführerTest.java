package kompatibilitaet.securitymanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

class PluginAusführerTest {

    @Test
    void führtEinPluginAus() {
        final AtomicBoolean gelaufen = new AtomicBoolean();
        PluginAusführer.führeAus(new Runnable() {
            @Override
            public void run() {
                gelaufen.set(true);
            }
        });
        assertEquals(true, gelaufen.get());
    }

    @Test
    void verhindertDassEinPluginDieAnwendungBeendet() {
        PluginAusführer.PluginBeendetException e = assertThrows(PluginAusführer.PluginBeendetException.class,
                () -> PluginAusführer.führeAus(() -> System.exit(3)));
        assertEquals(3, e.getStatus());
    }
}

package kompatibilitaet.toolchain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ToolchainTest {

    @Test
    void läuftAufJdk8() {
        System.out.println("Die Tests laufen auf Java " + System.getProperty("java.version")
                + " (" + System.getProperty("java.vendor") + ") aus " + System.getProperty("java.home"));

        assertEquals("1.8", System.getProperty("java.specification.version"),
                "Die Tests laufen nicht auf JDK 8 – prüfe jdkHome in ~/.m2/toolchains.xml.");
    }
}

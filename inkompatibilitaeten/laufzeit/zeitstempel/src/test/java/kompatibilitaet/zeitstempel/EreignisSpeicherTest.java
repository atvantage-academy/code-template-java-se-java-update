package kompatibilitaet.zeitstempel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EreignisSpeicherTest {

    @Test
    void geladenesEreignisGleichtDemGespeicherten() {
        EreignisSpeicher speicher = new EreignisSpeicher();
        Ereignis bestellt = Ereignis.jetzt("BESTELLT");

        long schlüssel = speicher.speichere(bestellt);

        assertEquals(bestellt, speicher.lade(schlüssel));
    }
}

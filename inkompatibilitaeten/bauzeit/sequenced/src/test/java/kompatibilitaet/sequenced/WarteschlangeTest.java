package kompatibilitaet.sequenced;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class WarteschlangeTest {

    @Test
    void liefertErstenUndLetztenAuftrag() {
        Warteschlange<String> aufträge = new Warteschlange<String>();
        aufträge.add("B-00001");
        aufträge.add("B-00002");

        assertEquals("B-00001", aufträge.getFirst().get());
        assertEquals("B-00002", aufträge.getLast().get());
    }

    @Test
    void leereWarteschlangeWirftNicht() {
        assertFalse(new Warteschlange<String>().getFirst().isPresent());
    }
}

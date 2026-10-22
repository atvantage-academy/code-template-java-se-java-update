package neuerungen.textbloecke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TexteTest {

    @Test
    void bestätigungBleibtZeichengenauGleich() {
        String erwartet = "{\n"
                + "  \"id\": \"B-00001\",\n"
                + "  \"kunde\": \"Ada Lovelace\",\n"
                + "  \"betrag\": 51.80\n"
                + "}";
        assertEquals(erwartet, Texte.bestätigungAlsJson("B-00001", "Ada Lovelace", new BigDecimal("51.80")));
    }

    @Test
    void berichtBleibtZeichengenauGleich() {
        String[] erwartet = {
                "Bestellbericht",
                "==============",
                "",
                "B-00001       51.80 EUR",
                "B-00002      661.33 EUR",
                "",
                "Fragen? Antworte einfach auf diese E-Mail."
        };
        String bericht = Texte.bericht(Arrays.asList("B-00001", "B-00002"),
                Arrays.asList(new BigDecimal("51.80"), new BigDecimal("661.33")));
        assertEquals(Arrays.asList(erwartet), Arrays.asList(bericht.split("\\R")));
    }
}

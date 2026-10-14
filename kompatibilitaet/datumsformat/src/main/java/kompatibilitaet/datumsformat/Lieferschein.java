package kompatibilitaet.datumsformat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Erzeugt die Kopfzeile eines Lieferscheins – in deutscher Schreibweise.
 */
public final class Lieferschein {

    private static final DateTimeFormatter DATUM = DateTimeFormatter.ofPattern("EEE, d. MMM yyyy", Locale.GERMANY);

    private Lieferschein() {
    }

    public static String kopfzeile(String bestellnummer, LocalDate lieferdatum, BigDecimal betrag) {
        return "Lieferschein " + bestellnummer + " vom " + DATUM.format(lieferdatum)
                + " über " + NumberFormat.getCurrencyInstance(Locale.GERMANY).format(betrag);
    }
}

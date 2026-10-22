package kompatibilitaet.datumsformat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Map;

/**
 * Erzeugt die Kopfzeile eines Lieferscheins – in deutscher Schreibweise.
 * <p>
 * Das Lagersystem liest diese Zeile ein; ihre Form ist also ein Vertrag. Deshalb stehen
 * die Abkürzungen und das Betragsformat hier im Code und kommen nicht aus den
 * Locale-Daten des JDK, die seit Java 9 aus dem Unicode CLDR stammen (JEP 252) und sich
 * mit jedem Update ändern dürfen.
 */
public final class Lieferschein {

    private static final Map<Long, String> WOCHENTAGE = Map.of(
            1L, "Mo", 2L, "Di", 3L, "Mi", 4L, "Do", 5L, "Fr", 6L, "Sa", 7L, "So");

    private static final Map<Long, String> MONATE = Map.ofEntries(
            Map.entry(1L, "Jan"), Map.entry(2L, "Feb"), Map.entry(3L, "Mär"), Map.entry(4L, "Apr"),
            Map.entry(5L, "Mai"), Map.entry(6L, "Jun"), Map.entry(7L, "Jul"), Map.entry(8L, "Aug"),
            Map.entry(9L, "Sep"), Map.entry(10L, "Okt"), Map.entry(11L, "Nov"), Map.entry(12L, "Dez"));

    private static final DateTimeFormatter DATUM = new DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_WEEK, WOCHENTAGE)
            .appendPattern(", d. ")
            .appendText(ChronoField.MONTH_OF_YEAR, MONATE)
            .appendPattern(" yyyy")
            .toFormatter(Locale.GERMANY);

    private Lieferschein() {
    }

    public static String kopfzeile(String bestellnummer, LocalDate lieferdatum, BigDecimal betrag) {
        // DecimalFormat ist nicht threadsicher – deshalb je Aufruf neu.
        DecimalFormat euro = new DecimalFormat("#,##0.00 €", DecimalFormatSymbols.getInstance(Locale.GERMANY));
        return "Lieferschein " + bestellnummer + " vom " + DATUM.format(lieferdatum)
                + " über " + euro.format(betrag);
    }
}

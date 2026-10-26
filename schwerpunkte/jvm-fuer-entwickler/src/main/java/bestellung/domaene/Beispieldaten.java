package bestellung.domaene;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Ein paar Bestellungen, damit REST-Schnittstelle und Bericht etwas zu zeigen haben.
 */
public final class Beispieldaten {

    public static final List<Kunde> KUNDEN = Collections.unmodifiableList(Arrays.asList(
            new Kunde("K-1001", "Jana Müller", "jana.mueller@example.com"),
            new Kunde("K-1002", "Ömer Yılmaz", "oemer.yilmaz@example.com"),
            new Kunde("K-1003", "Grete Weiß", null)));

    private Beispieldaten() {
    }

    public static void befülle(BestellService service) {
        service.anlegen(new Bestellung(KUNDEN.get(0),
                Arrays.asList(
                        new Position("A-100", "Kaffeebohnen, 1 kg", 2, new BigDecimal("18.90")),
                        new Position("A-205", "Milchkännchen", 1, new BigDecimal("12.50"))),
                new Rechnung(14), LocalDate.of(2026, 9, 1)));
        service.anlegen(new Bestellung(KUNDEN.get(1),
                Collections.singletonList(
                        new Position("A-310", "Siebträgermaschine", 1, new BigDecimal("649.00"))),
                new Kreditkarte("4111 1111 1111 1111", YearMonth.of(2028, 3)), LocalDate.of(2026, 9, 3)));
        service.anlegen(new Bestellung(KUNDEN.get(2),
                Arrays.asList(
                        new Position("A-100", "Kaffeebohnen, 1 kg", 5, new BigDecimal("18.90")),
                        new Position("A-412", "Entkalker", 3, new BigDecimal("7.99"))),
                new Lastschrift("DE02 1203 0000 0000 2020 51"), LocalDate.of(2026, 9, 3)));
        service.anlegen(new Bestellung(KUNDEN.get(0),
                Collections.singletonList(
                        new Position("A-520", "Kaffeemühle", 1, new BigDecimal("89.00"))),
                new PayPal("jana.mueller@example.com"), LocalDate.of(2026, 9, 7)));
    }
}

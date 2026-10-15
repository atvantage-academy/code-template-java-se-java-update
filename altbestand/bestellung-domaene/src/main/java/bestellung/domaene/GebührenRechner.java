package bestellung.domaene;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Berechnet die Gebühr, die eine Zahlart für einen Betrag kostet.
 *
 * <p>Ein {@code switch} über die versiegelte {@link Zahlart}: Er braucht keinen
 * {@code default}, und eine neue Zahlart übersetzt erst, wenn sie hier ihren Fall hat.
 */
public final class GebührenRechner {

    private static final BigDecimal RECHNUNGSGEBÜHR = new BigDecimal("1.50");
    private static final BigDecimal KREDITKARTE_PROZENT = new BigDecimal("0.019");
    private static final BigDecimal PAYPAL_FIX = new BigDecimal("0.35");
    private static final BigDecimal PAYPAL_PROZENT = new BigDecimal("0.0249");

    private GebührenRechner() {
    }

    public static BigDecimal gebühr(Zahlart zahlart, BigDecimal betrag) {
        // Die Namen der Muster-Variablen braucht hier niemand. Ab Java 22 (LTS 25) schreibt
        // man dafür „_“: case Rechnung _ -> …; mit --release 21 geht das noch nicht.
        return switch (zahlart) {
            case Rechnung rechnung -> RECHNUNGSGEBÜHR;
            case Lastschrift lastschrift -> BigDecimal.ZERO.setScale(2);
            case Kreditkarte kreditkarte -> betrag.multiply(KREDITKARTE_PROZENT).setScale(2, RoundingMode.HALF_UP);
            case PayPal payPal -> PAYPAL_FIX.add(betrag.multiply(PAYPAL_PROZENT)).setScale(2, RoundingMode.HALF_UP);
        };
    }
}

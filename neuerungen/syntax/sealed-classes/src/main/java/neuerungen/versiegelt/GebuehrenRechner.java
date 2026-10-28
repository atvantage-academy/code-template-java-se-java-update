package neuerungen.versiegelt;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Berechnet die Gebühr, die eine Zahlart für einen Betrag kostet. Der {@code switch}
 * über die versiegelte {@link Zahlart} braucht keinen {@code default}: Fehlt eine
 * Zahlart, übersetzt er nicht.
 */
public final class GebuehrenRechner {

    private static final BigDecimal RECHNUNGSGEBÜHR = new BigDecimal("1.50");
    private static final BigDecimal KREDITKARTE_PROZENT = new BigDecimal("0.019");
    private static final BigDecimal PAYPAL_FIX = new BigDecimal("0.35");
    private static final BigDecimal PAYPAL_PROZENT = new BigDecimal("0.0249");

    private GebuehrenRechner() {
    }

    public static BigDecimal gebühr(Zahlart zahlart, BigDecimal betrag) {
        return switch (zahlart) {
            case Rechnung rechnung -> RECHNUNGSGEBÜHR;
            case Lastschrift lastschrift -> BigDecimal.ZERO.setScale(2);
            case Kreditkarte kreditkarte -> betrag.multiply(KREDITKARTE_PROZENT).setScale(2, RoundingMode.HALF_UP);
            case PayPal payPal -> PAYPAL_FIX.add(betrag.multiply(PAYPAL_PROZENT)).setScale(2, RoundingMode.HALF_UP);
        };
    }
}

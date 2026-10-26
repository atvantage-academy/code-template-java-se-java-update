package bestellung.domaene;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Berechnet die Gebühr, die eine Zahlart für einen Betrag kostet.
 */
public class GebührenRechner implements ZahlartVisitor<BigDecimal> {

    private static final BigDecimal RECHNUNGSGEBÜHR = new BigDecimal("1.50");
    private static final BigDecimal KREDITKARTE_PROZENT = new BigDecimal("0.019");
    private static final BigDecimal PAYPAL_FIX = new BigDecimal("0.35");
    private static final BigDecimal PAYPAL_PROZENT = new BigDecimal("0.0249");

    private final BigDecimal betrag;

    public GebührenRechner(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public static BigDecimal gebühr(Zahlart zahlart, BigDecimal betrag) {
        return zahlart.accept(new GebührenRechner(betrag));
    }

    @Override
    public BigDecimal visit(Rechnung rechnung) {
        return RECHNUNGSGEBÜHR;
    }

    @Override
    public BigDecimal visit(Lastschrift lastschrift) {
        return BigDecimal.ZERO.setScale(2);
    }

    @Override
    public BigDecimal visit(Kreditkarte kreditkarte) {
        return betrag.multiply(KREDITKARTE_PROZENT).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal visit(PayPal payPal) {
        return PAYPAL_FIX.add(betrag.multiply(PAYPAL_PROZENT)).setScale(2, RoundingMode.HALF_UP);
    }
}

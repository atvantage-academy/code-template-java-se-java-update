package neuerungen.versiegelt;

/**
 * Zahlung über ein PayPal-Konto.
 */
public final class PayPal extends Zahlart {

    private final String konto;

    public PayPal(String konto) {
        this.konto = konto;
    }

    public String getKonto() {
        return konto;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

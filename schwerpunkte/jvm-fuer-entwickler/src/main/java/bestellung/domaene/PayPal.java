package bestellung.domaene;

import java.util.Objects;

/**
 * Zahlung über ein PayPal-Konto.
 */
public final class PayPal extends Zahlart {

    private final String konto;

    public PayPal(String konto) {
        this.konto = Objects.requireNonNull(konto, "konto");
    }

    public String getKonto() {
        return konto;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "PayPal{konto=" + konto + "}";
    }
}

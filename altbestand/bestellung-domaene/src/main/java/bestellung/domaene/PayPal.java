package bestellung.domaene;

import java.util.Objects;

/**
 * Zahlung über ein PayPal-Konto.
 */
public record PayPal(String konto) implements Zahlart {

    public PayPal {
        Objects.requireNonNull(konto, "konto");
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

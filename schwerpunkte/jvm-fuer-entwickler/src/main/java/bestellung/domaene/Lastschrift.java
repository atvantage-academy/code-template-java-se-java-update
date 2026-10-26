package bestellung.domaene;

import java.util.Objects;

/**
 * SEPA-Lastschrift von einem Konto.
 */
public final class Lastschrift extends Zahlart {

    private final String iban;

    public Lastschrift(String iban) {
        this.iban = Objects.requireNonNull(iban, "iban");
    }

    public String getIban() {
        return iban;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Lastschrift{iban=" + iban + "}";
    }
}

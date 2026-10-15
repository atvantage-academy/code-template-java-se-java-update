package bestellung.domaene;

import java.util.Objects;

/**
 * SEPA-Lastschrift von einem Konto.
 */
public record Lastschrift(String iban) implements Zahlart {

    public Lastschrift {
        Objects.requireNonNull(iban, "iban");
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

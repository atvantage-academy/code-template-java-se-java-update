package bestellung.domaene;

import java.util.Objects;

/**
 * Zahlung über ein PayPal-Konto.
 */
public record PayPal(String konto) implements Zahlart {

    public PayPal {
        Objects.requireNonNull(konto, "konto");
    }
}

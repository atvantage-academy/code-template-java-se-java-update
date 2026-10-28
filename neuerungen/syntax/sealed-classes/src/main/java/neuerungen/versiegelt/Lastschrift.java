package neuerungen.versiegelt;

/**
 * Zahlung per Lastschrift von einem Konto.
 */
public final class Lastschrift extends Zahlart {

    private final String iban;

    public Lastschrift(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }
}

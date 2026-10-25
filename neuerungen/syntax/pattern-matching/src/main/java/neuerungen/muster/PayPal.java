package neuerungen.muster;

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
}

package neuerungen.versiegelt;

/**
 * Zahlung per Kreditkarte.
 */
public final class Kreditkarte extends Zahlart {

    private final String kartennummer;

    public Kreditkarte(String kartennummer) {
        this.kartennummer = kartennummer;
    }

    public String getKartennummer() {
        return kartennummer;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

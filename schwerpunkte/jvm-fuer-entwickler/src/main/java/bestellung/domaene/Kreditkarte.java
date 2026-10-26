package bestellung.domaene;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Zahlung per Kreditkarte.
 */
public final class Kreditkarte extends Zahlart {

    private final String kartennummer;
    private final YearMonth gültigBis;

    public Kreditkarte(String kartennummer, YearMonth gültigBis) {
        this.kartennummer = Objects.requireNonNull(kartennummer, "kartennummer");
        this.gültigBis = Objects.requireNonNull(gültigBis, "gültigBis");
    }

    public String getKartennummer() {
        return kartennummer;
    }

    public YearMonth getGültigBis() {
        return gültigBis;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Kreditkarte{kartennummer=****" + kartennummer.substring(kartennummer.length() - 4)
                + ", gültigBis=" + gültigBis + "}";
    }
}

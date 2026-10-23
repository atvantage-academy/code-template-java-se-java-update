package neuerungen.muster;

import java.time.YearMonth;

/**
 * Zahlung per Kreditkarte.
 */
public final class Kreditkarte extends Zahlart {

    private final String kartennummer;
    private final YearMonth gültigBis;

    public Kreditkarte(String kartennummer, YearMonth gültigBis) {
        this.kartennummer = kartennummer;
        this.gültigBis = gültigBis;
    }

    public String getKartennummer() {
        return kartennummer;
    }

    public YearMonth getGültigBis() {
        return gültigBis;
    }
}

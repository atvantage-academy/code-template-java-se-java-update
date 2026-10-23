package neuerungen.records;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Die Kreditkarte, mit der eine Bestellung bezahlt wird. Ihre Nummer gehört in keine
 * Log-Datei.
 */
public final class Kreditkarte {

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kreditkarte karte = (Kreditkarte) o;
        return kartennummer.equals(karte.kartennummer) && gültigBis.equals(karte.gültigBis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kartennummer, gültigBis);
    }

    @Override
    public String toString() {
        return "Kreditkarte[kartennummer=****" + kartennummer.substring(kartennummer.length() - 4)
                + ", gültigBis=" + gültigBis + "]";
    }
}

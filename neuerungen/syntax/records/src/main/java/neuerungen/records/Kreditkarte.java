package neuerungen.records;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Die Kreditkarte, mit der eine Bestellung bezahlt wird. Ihre Nummer gehört in keine
 * Log-Datei – deshalb schreibt der Record sein {@code toString} selbst.
 */
public record Kreditkarte(String kartennummer, YearMonth gültigBis) {

    public Kreditkarte {
        Objects.requireNonNull(kartennummer, "kartennummer");
        Objects.requireNonNull(gültigBis, "gültigBis");
    }

    @Override
    public String toString() {
        return "Kreditkarte[kartennummer=****" + kartennummer.substring(kartennummer.length() - 4)
                + ", gültigBis=" + gültigBis + "]";
    }
}

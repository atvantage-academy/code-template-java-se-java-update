package bestellung.domaene;

import java.time.YearMonth;
import java.util.Objects;

/**
 * Zahlung per Kreditkarte.
 */
public record Kreditkarte(String kartennummer, YearMonth gültigBis) implements Zahlart {

    public Kreditkarte {
        Objects.requireNonNull(kartennummer, "kartennummer");
        Objects.requireNonNull(gültigBis, "gültigBis");
    }

    /**
     * Das {@code toString} des Records zeigte die ganze Kartennummer – in jedem Log.
     * Deshalb bleibt es hier selbst geschrieben.
     */
    @Override
    public String toString() {
        return "Kreditkarte[kartennummer=****" + kartennummer.substring(kartennummer.length() - 4)
                + ", gültigBis=" + gültigBis + "]";
    }
}

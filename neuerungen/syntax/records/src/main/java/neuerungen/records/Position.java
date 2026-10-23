package neuerungen.records;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Eine Position einer Bestellung: welcher Artikel, wie oft, zu welchem Preis.
 */
public record Position(String artikelnummer, String bezeichnung, int menge, BigDecimal einzelpreis) {

    public Position {
        if (menge <= 0) {
            throw new IllegalArgumentException("Die Menge muss positiv sein: " + menge);
        }
        Objects.requireNonNull(artikelnummer, "artikelnummer");
        Objects.requireNonNull(bezeichnung, "bezeichnung");
        Objects.requireNonNull(einzelpreis, "einzelpreis");
    }

    public BigDecimal summe() {
        return einzelpreis.multiply(BigDecimal.valueOf(menge));
    }
}

package bestellung.domaene;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Eine Position einer Bestellung: welcher Artikel, wie oft, zu welchem Preis.
 */
public final class Position {

    private final String artikelnummer;
    private final String bezeichnung;
    private final int menge;
    private final BigDecimal einzelpreis;

    public Position(String artikelnummer, String bezeichnung, int menge, BigDecimal einzelpreis) {
        if (menge <= 0) {
            throw new IllegalArgumentException("Die Menge muss positiv sein: " + menge);
        }
        this.artikelnummer = Objects.requireNonNull(artikelnummer, "artikelnummer");
        this.bezeichnung = Objects.requireNonNull(bezeichnung, "bezeichnung");
        this.menge = menge;
        this.einzelpreis = Objects.requireNonNull(einzelpreis, "einzelpreis");
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public int getMenge() {
        return menge;
    }

    public BigDecimal getEinzelpreis() {
        return einzelpreis;
    }

    public BigDecimal getSumme() {
        return einzelpreis.multiply(BigDecimal.valueOf(menge));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return menge == position.menge
                && artikelnummer.equals(position.artikelnummer)
                && bezeichnung.equals(position.bezeichnung)
                && einzelpreis.equals(position.einzelpreis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artikelnummer, bezeichnung, menge, einzelpreis);
    }

    @Override
    public String toString() {
        return "Position{artikelnummer=" + artikelnummer + ", bezeichnung=" + bezeichnung
                + ", menge=" + menge + ", einzelpreis=" + einzelpreis + "}";
    }
}

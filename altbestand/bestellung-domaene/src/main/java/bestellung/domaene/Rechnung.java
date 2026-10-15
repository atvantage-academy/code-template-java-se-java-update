package bestellung.domaene;

/**
 * Kauf auf Rechnung mit einem Zahlungsziel in Tagen.
 */
public record Rechnung(int zahlungszielTage) implements Zahlart {

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

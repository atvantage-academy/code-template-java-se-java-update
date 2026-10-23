package neuerungen.versiegelt;

/**
 * Kauf auf Rechnung mit einem Zahlungsziel in Tagen.
 */
public final class Rechnung extends Zahlart {

    private final int zahlungszielTage;

    public Rechnung(int zahlungszielTage) {
        this.zahlungszielTage = zahlungszielTage;
    }

    public int getZahlungszielTage() {
        return zahlungszielTage;
    }

    @Override
    public <R> R accept(ZahlartVisitor<R> visitor) {
        return visitor.visit(this);
    }
}

package neuerungen.versiegelt;

/**
 * Wie eine Bestellung bezahlt wird.
 *
 * <p>Es gibt genau vier Zahlarten: {@link Rechnung}, {@link Lastschrift},
 * {@link Kreditkarte} und {@link PayPal}. Sicherstellen lässt sich das mit Java 8
 * nicht; der package-private Konstruktor hält nur fremde Pakete ab.
 */
public abstract class Zahlart {

    Zahlart() {
    }

    public abstract <R> R accept(ZahlartVisitor<R> visitor);
}

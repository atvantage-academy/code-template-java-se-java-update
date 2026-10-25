package neuerungen.versiegelt;

/**
 * Wie eine Bestellung bezahlt wird.
 *
 * <p>Es gibt genau vier Zahlarten: {@link Rechnung}, {@link Lastschrift},
 * {@link Kreditkarte} und {@link PayPal}. Sicherstellen lässt sich das mit Java 8
 * nicht; der package-private Konstruktor hält nur fremde Pakete ab.
 */
// Umbauen: versiegeln – dann weiß der Compiler, dass es genau diese vier Zahlarten gibt.
public abstract class Zahlart {

    Zahlart() {
    }

    // Umbauen: entfällt mit dem Visitor.
    public abstract <R> R accept(ZahlartVisitor<R> visitor);
}

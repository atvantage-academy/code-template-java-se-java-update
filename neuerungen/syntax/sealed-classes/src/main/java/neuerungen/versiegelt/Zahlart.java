package neuerungen.versiegelt;

/**
 * Wie eine Bestellung bezahlt wird. Es gibt genau vier Zahlarten – das sagt die
 * {@code permits}-Klausel dem Compiler.
 */
public abstract sealed class Zahlart permits Rechnung, Lastschrift, Kreditkarte, PayPal {

    Zahlart() {
    }
}

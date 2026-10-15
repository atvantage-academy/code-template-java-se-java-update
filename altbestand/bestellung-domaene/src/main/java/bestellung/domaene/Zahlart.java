package bestellung.domaene;

/**
 * Wie eine Bestellung bezahlt wird.
 *
 * <p>Versiegelt: Es gibt genau diese vier Zahlarten, und der Compiler weiß das. Eine
 * fünfte muss hier eingetragen werden – sonst übersetzt sie nicht.
 */
public sealed interface Zahlart permits Rechnung, Lastschrift, Kreditkarte, PayPal {

    <R> R accept(ZahlartVisitor<R> visitor);
}

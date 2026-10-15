package bestellung.domaene;

/**
 * Wie eine Bestellung bezahlt wird.
 *
 * <p>Versiegelt: Es gibt genau diese vier Zahlarten, und der Compiler weiß das. Eine
 * fünfte muss hier eingetragen werden – und danach in jedem {@code switch} über
 * Zahlarten ihren Fall bekommen, sonst übersetzt das Projekt nicht.
 */
public sealed interface Zahlart permits Rechnung, Lastschrift, Kreditkarte, PayPal {
}

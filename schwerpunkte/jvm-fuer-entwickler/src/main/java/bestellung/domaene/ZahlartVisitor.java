package bestellung.domaene;

/**
 * Eine Operation über alle Zahlarten (Visitor-Muster). Jede neue Zahlart braucht
 * hier eine neue Methode – und jede Implementierung muss nachziehen.
 */
public interface ZahlartVisitor<R> {

    R visit(Rechnung rechnung);

    R visit(Lastschrift lastschrift);

    R visit(Kreditkarte kreditkarte);

    R visit(PayPal payPal);
}

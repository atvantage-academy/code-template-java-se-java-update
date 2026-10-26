package bestellung.domaene;

/**
 * Wie eine Bestellung bezahlt wird.
 *
 * <p>Die Unterklassen stehen in diesem Paket – mehr soll es nicht geben. Sicherstellen
 * lässt sich das mit Java 8 nicht; der package-private Konstruktor hält nur
 * fremde Pakete ab.
 */
public abstract class Zahlart {

    Zahlart() {
    }

    public abstract <R> R accept(ZahlartVisitor<R> visitor);
}

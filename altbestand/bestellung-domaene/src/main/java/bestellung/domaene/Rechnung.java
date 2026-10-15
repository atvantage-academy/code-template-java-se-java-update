package bestellung.domaene;

/**
 * Kauf auf Rechnung mit einem Zahlungsziel in Tagen.
 */
public record Rechnung(int zahlungszielTage) implements Zahlart {
}

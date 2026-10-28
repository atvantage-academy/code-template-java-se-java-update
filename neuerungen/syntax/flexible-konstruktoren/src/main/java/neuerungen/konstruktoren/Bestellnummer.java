package neuerungen.konstruktoren;

/**
 * Die Nummer einer Bestellung, eingegeben etwa als „ b-1042 “. Sie wird geprüft und
 * normalisiert, bevor die Oberklasse sie sieht.
 *
 * Seit Java 25 stehen Prüfung und Normalisierung als gewöhnliche Anweisungen vor
 * {@code super(…)} – ohne Hilfsmethode.
 */
public final class Bestellnummer extends Kennung {

    public Bestellnummer(String eingabe) {
        if (eingabe == null || eingabe.isBlank()) {
            throw new IllegalArgumentException("Bestellnummer fehlt");
        }
        String normalisiert = eingabe.strip().toUpperCase();
        super("B", normalisiert);
    }
}

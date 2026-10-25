package neuerungen.konstruktoren;

/**
 * Die Nummer einer Bestellung, eingegeben etwa als „ b-1042 “. Sie wird geprüft und
 * normalisiert, bevor die Oberklasse sie sieht.
 *
 * Mit Java 8 darf vor {@code super(…)} keine Anweisung stehen. Prüfen und Normalisieren
 * wandern deshalb in eine statische Hilfsmethode, die im Aufruf von {@code super(…)}
 * steckt.
 */
public final class Bestellnummer extends Kennung {

    public Bestellnummer(String eingabe) {
        super("B", normalisiere(eingabe));
    }

    private static String normalisiere(String eingabe) {
        if (eingabe == null || eingabe.trim().isEmpty()) {
            throw new IllegalArgumentException("Bestellnummer fehlt");
        }
        return eingabe.trim().toUpperCase();
    }
}

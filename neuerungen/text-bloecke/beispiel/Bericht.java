/**
 * Text-Blöcke (JEP 378, Java 15).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 15:
 *
 *     java neuerungen/text-bloecke/beispiel/Bericht.java
 *
 * Die Ausgabe macht Leerzeichen als „·“ und das Zeilenende als „¶“ sichtbar.
 * Verschiebe danach die schließenden Anführungszeichen eines Blocks nach links oder
 * rechts und starte noch einmal. Was ändert sich?
 */
public class Bericht {

    public static void main(String[] args) {
        zeige("Gewöhnlicher Block", """
                Bestellbericht
                ==============
                """);

        zeige("Schließende Anführungszeichen direkt hinter dem Text", """
                kein Umbruch am Ende""");

        zeige("Schließende Anführungszeichen weiter links", """
                Bestellbericht
            """);

        zeige("Einrückung gegenüber der Zeile ganz links", """
                    eingerückt
                um vier Zeichen
                """);

        zeige("Zeile fortsetzen mit \\", """
                eine lange Zeile, \
                im Quelltext umbrochen
                """);

        zeige("Leerzeichen am Ende erhalten mit \\s", """
                rechtsbündig   \s
                """);

        zeige("Platzhalter füllen mit formatted()", """
                {
                  "id": "%s",
                  "betrag": %s
                }""".formatted("B-00001", "51.80"));
    }

    private static void zeige(String titel, String text) {
        System.out.println("– " + titel + ":");
        System.out.println(text.replace(" ", "·").replace("\n", "¶\n"));
    }
}

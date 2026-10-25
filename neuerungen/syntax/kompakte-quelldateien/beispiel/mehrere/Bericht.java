/**
 * Mehrere Quelldateien starten (JEP 458, Java 22): Bericht nutzt Position und Format
 * aus den Nachbardateien im selben Ordner – ohne Build, ohne vorheriges Übersetzen.
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 22:
 *
 *     java neuerungen/syntax/kompakte-quelldateien/beispiel/mehrere/Bericht.java
 */
public class Bericht {

    public static void main(String[] args) {
        Position[] positionen = {
                new Position("Kaffeebecher", 2, 1290),
                new Position("Teekanne", 1, 3450)
        };
        int summe = 0;
        for (Position position : positionen) {
            System.out.println(Format.zeile(position));
            summe += position.gesamtInCent();
        }
        System.out.println("Summe: " + Format.euro(summe));
    }
}

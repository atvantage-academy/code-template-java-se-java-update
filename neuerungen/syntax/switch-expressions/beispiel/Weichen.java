/**
 * Switch Expressions (JEP 361, Java 14).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 14:
 *
 *     java neuerungen/syntax/switch-expressions/beispiel/Weichen.java
 *
 * Ergänze danach im enum eine Zahlart BAR und starte noch einmal. Welcher switch
 * merkt es, welcher nicht?
 */
public class Weichen {

    enum Zahlart { RECHNUNG, LASTSCHRIFT, KREDITKARTE, PAYPAL }

    public static void main(String[] args) {
        for (Zahlart zahlart : Zahlart.values()) {
            System.out.println(zahlart + ": " + tageAlsAusdruck(zahlart) + " Tage, "
                    + tageAlsAnweisung(zahlart) + " Tage, " + hinweis(zahlart));
        }
    }

    // Neu: ein Ausdruck mit Pfeilen. Kein break, kein Durchfallen, kein default –
    // der Compiler prüft, dass jede Zahlart einen Wert bekommt.
    static int tageAlsAusdruck(Zahlart zahlart) {
        return switch (zahlart) {
            case RECHNUNG -> 14;
            case LASTSCHRIFT -> 3;
            case KREDITKARTE, PAYPAL -> 0;
        };
    }

    // Alt: eine Anweisung. Fehlt eine Zahlart, sagt der Compiler nichts.
    static int tageAlsAnweisung(Zahlart zahlart) {
        int tage = -1;
        switch (zahlart) {
            case RECHNUNG:
                tage = 14;
                break;
            case LASTSCHRIFT:
                tage = 3;
                break;
            case KREDITKARTE:
            case PAYPAL:
                tage = 0;
        }
        return tage;
    }

    // Braucht ein Zweig mehr als einen Ausdruck, liefert yield den Wert.
    static String hinweis(Zahlart zahlart) {
        return switch (zahlart) {
            case RECHNUNG -> {
                int tage = tageAlsAusdruck(zahlart);
                yield "zahlbar in " + tage + " Tagen";
            }
            case LASTSCHRIFT -> "wird abgebucht";
            default -> "bereits bezahlt";
        };
    }
}

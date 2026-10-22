/**
 * Private Methoden in Interfaces (JEP 213, Java 9).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 11:
 *
 *     java neuerungen/private-interface-methoden/beispiel/Hilfsmethoden.java
 *
 * Nimm danach die Kommentarzeichen vor der markierten Zeile in main weg und starte
 * noch einmal. Was meldet der Compiler?
 */
public class Hilfsmethoden {

    public static void main(String[] args) {
        Gruß gruß = () -> "Ada";
        System.out.println(gruß.förmlich());
        System.out.println(gruß.locker());
        // System.out.println(gruß.einleitung("Moin"));
    }
}

interface Gruß {

    String name();

    default String förmlich() {
        return einleitung("Guten Tag");
    }

    default String locker() {
        return einleitung("Hallo");
    }

    // Gemeinsamer Code der Default-Methoden – nach außen unsichtbar.
    private String einleitung(String anrede) {
        return anrede + ", " + mitGroßemAnfang(name());
    }

    // Auch statisch geht es privat.
    private static String mitGroßemAnfang(String text) {
        return text.isEmpty() ? text : Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }
}

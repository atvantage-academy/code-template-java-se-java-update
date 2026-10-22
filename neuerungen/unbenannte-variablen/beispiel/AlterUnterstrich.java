/**
 * Die Vorgeschichte von „_“: ein gewöhnlicher Bezeichner bis Java 8, ein Fehler ab
 * Java 9 (JEP 213).
 *
 * Übersetzen im Wurzelordner des Übungsprojekts, mit jedem JDK ab 11:
 *
 *     javac --release 8 -d neuerungen/unbenannte-variablen/target/alt neuerungen/unbenannte-variablen/beispiel/AlterUnterstrich.java
 *     javac --release 9 -d neuerungen/unbenannte-variablen/target/alt neuerungen/unbenannte-variablen/beispiel/AlterUnterstrich.java
 *
 * Mit Release 8 übersetzt die Datei mit einer Warnung, mit Release 9 nicht mehr.
 */
public class AlterUnterstrich {

    public static void main(String[] args) {
        String _ = "Ich heiße Unterstrich.";
        System.out.println(_);
    }
}

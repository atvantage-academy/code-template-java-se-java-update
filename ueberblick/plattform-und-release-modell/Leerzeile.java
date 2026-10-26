/**
 * Nutzt String.isBlank() – das gibt es erst seit Java 11.
 *
 * Für Java 8 übersetzt, mit --release prüft javac die API und lehnt ab:
 *
 *     javac --release 8 -d target/release Leerzeile.java
 *
 * Mit -source und -target nur die Sprache – es übersetzt, läuft auf Java 8 aber nicht
 * (NoSuchMethodError):
 *
 *     javac -source 8 -target 8 -d target/source-target Leerzeile.java
 */
public class Leerzeile {

    public static void main(String[] args) {
        String zeile = "   ";
        System.out.println("Leer? " + zeile.isBlank());
    }
}

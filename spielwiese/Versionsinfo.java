/**
 * Zeigt, auf welchem Java dieses Programm läuft.
 *
 * Ohne Build und ohne vorheriges javac starten (seit Java 11):
 *
 *     java spielwiese/Versionsinfo.java
 */
public class Versionsinfo {

    public static void main(String[] args) {
        Runtime.Version version = Runtime.version();
        System.out.println("Java " + version.feature() + " (" + version + ")");
        System.out.println("Hersteller: " + System.getProperty("java.vendor"));
        System.out.println("Standard-Zeichensatz: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("Prozessoren: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Max. Heap: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + " MB");
    }
}

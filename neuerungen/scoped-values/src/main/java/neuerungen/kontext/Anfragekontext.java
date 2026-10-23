package neuerungen.kontext;

import java.util.concurrent.Callable;

/**
 * Hält die Kennung der Anfrage, die gerade bearbeitet wird – damit jede Schicht sie ins
 * Protokoll schreiben kann, ohne dass sie als Parameter durch alle Methoden wandert.
 *
 * Seit Java 25 ein {@link ScopedValue}: Die Bindung gilt genau für den Aufruf der
 * Aufgabe und endet mit ihm – kein Zurücksetzen, kein Aufräumen nach einem Fehler.
 */
public final class Anfragekontext {

    private static final ScopedValue<String> ANFRAGE = ScopedValue.newInstance();

    private Anfragekontext() {
    }

    /** Bearbeitet die Aufgabe im Kontext der Anfrage und gibt ihr Ergebnis zurück. */
    public static <T> T bearbeite(String anfrage, Callable<T> aufgabe) throws Exception {
        return ScopedValue.where(ANFRAGE, anfrage).call(aufgabe::call);
    }

    /** Die Kennung der laufenden Anfrage, oder „–“ außerhalb einer Anfrage. */
    public static String aktuelleAnfrage() {
        return ANFRAGE.orElse("–");
    }
}

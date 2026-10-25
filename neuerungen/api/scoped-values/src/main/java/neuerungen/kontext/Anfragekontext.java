package neuerungen.kontext;

import java.util.concurrent.Callable;

/**
 * Hält die Kennung der Anfrage, die gerade bearbeitet wird – damit jede Schicht sie ins
 * Protokoll schreiben kann, ohne dass sie als Parameter durch alle Methoden wandert.
 *
 * Mit Java 8 ist das eine {@link ThreadLocal}: Wer sie setzt, muss sie am Ende wieder
 * zurücksetzen, sonst sieht die nächste Anfrage auf demselben Thread die alte Kennung.
 */
public final class Anfragekontext {

    // Umbauen: ein ScopedValue – die Bindung endet mit dem Aufruf von selbst.
    private static final ThreadLocal<String> ANFRAGE = new ThreadLocal<String>();

    private Anfragekontext() {
    }

    /** Bearbeitet die Aufgabe im Kontext der Anfrage und gibt ihr Ergebnis zurück. */
    public static <T> T bearbeite(String anfrage, Callable<T> aufgabe) throws Exception {
        // Umbauen: Binden und Aufrufen in einer Zeile – Merken und Zurücksetzen entfallen.
        String vorher = ANFRAGE.get();
        ANFRAGE.set(anfrage);
        try {
            return aufgabe.call();
        } finally {
            if (vorher == null) {
                ANFRAGE.remove();
            } else {
                ANFRAGE.set(vorher);
            }
        }
    }

    /** Die Kennung der laufenden Anfrage, oder „–“ außerhalb einer Anfrage. */
    public static String aktuelleAnfrage() {
        // Umbauen: orElse statt der Prüfung auf null.
        String anfrage = ANFRAGE.get();
        return anfrage == null ? "–" : anfrage;
    }
}

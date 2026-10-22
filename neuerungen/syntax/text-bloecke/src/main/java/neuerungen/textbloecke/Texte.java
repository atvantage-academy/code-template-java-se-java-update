package neuerungen.textbloecke;

import java.math.BigDecimal;
import java.util.List;

/**
 * Texte, die der Shop verschickt: eine Bestätigung als JSON und einen Bericht für den
 * Vertrieb. Feste Teile stehen als Text-Block da (JEP 378).
 */
public final class Texte {

    private Texte() {
    }

    /** Die Bestätigung einer Bestellung als JSON, ohne Zeilenumbruch am Ende. */
    public static String bestätigungAlsJson(String id, String kunde, BigDecimal betrag) {
        // Die schließenden Anführungszeichen direkt hinter der Klammer: kein \n am Ende.
        return """
                {
                  "id": "%s",
                  "kunde": "%s",
                  "betrag": %s
                }""".formatted(id, kunde, betrag);
    }

    /** Ein Bericht über Bestellungen, je Bestellung eine Zeile aus Nummer und Betrag. */
    public static String bericht(List<String> nummern, List<BigDecimal> beträge) {
        StringBuilder text = new StringBuilder("""
                Bestellbericht
                ==============

                """);
        for (int i = 0; i < nummern.size(); i++) {
            text.append(String.format("%-8s %10s EUR%n", nummern.get(i), beträge.get(i)));
        }
        text.append("""

                Fragen? Antworte einfach auf diese E-Mail.
                """);
        return text.toString();
    }
}

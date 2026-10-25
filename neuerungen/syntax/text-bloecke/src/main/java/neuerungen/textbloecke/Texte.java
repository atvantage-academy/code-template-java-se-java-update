package neuerungen.textbloecke;

import java.math.BigDecimal;
import java.util.List;

/**
 * Texte, die der Shop verschickt: eine Bestätigung als JSON und einen Bericht für den
 * Vertrieb. Beide entstehen Zeile für Zeile aus Zeichenketten – so, wie man das mit
 * Java 8 schreibt.
 */
public final class Texte {

    private Texte() {
    }

    /** Die Bestätigung einer Bestellung als JSON, ohne Zeilenumbruch am Ende. */
    public static String bestätigungAlsJson(String id, String kunde, BigDecimal betrag) {
        // Umbauen: Ein Text-Block zeigt das JSON, wie es aussieht – ohne \n und \".
        return "{\n"
                + "  \"id\": \"" + id + "\",\n"
                + "  \"kunde\": \"" + kunde + "\",\n"
                + "  \"betrag\": " + betrag + "\n"
                + "}";
    }

    /** Ein Bericht über Bestellungen, je Bestellung eine Zeile aus Nummer und Betrag. */
    public static String bericht(List<String> nummern, List<BigDecimal> beträge) {
        // Umbauen: Der feste Kopf wird ein Text-Block.
        StringBuilder text = new StringBuilder();
        text.append("Bestellbericht\n");
        text.append("==============\n");
        text.append("\n");
        for (int i = 0; i < nummern.size(); i++) {
            text.append(String.format("%-8s %10s EUR%n", nummern.get(i), beträge.get(i)));
        }
        // Umbauen: Der feste Gruß am Ende ebenso.
        text.append("\n");
        text.append("Fragen? Antworte einfach auf diese E-Mail.\n");
        return text.toString();
    }
}

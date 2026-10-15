package bestellung.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import bestellung.domaene.Bestellung;
import bestellung.domaene.Kreditkarte;
import bestellung.domaene.Kunde;
import bestellung.domaene.Lastschrift;
import bestellung.domaene.PayPal;
import bestellung.domaene.Position;
import bestellung.domaene.Rechnung;
import bestellung.domaene.Zahlart;

/**
 * Liest Bestellungen aus einer CSV-Datei (Trennzeichen Semikolon, eine Kopfzeile,
 * eine Bestellung mit einer Position je Zeile).
 */
public class CsvLeser {

    public List<Bestellung> lies(InputStream ein) throws IOException {
        var bestellungen = new ArrayList<Bestellung>();
        try (var leser = new BufferedReader(new InputStreamReader(ein, StandardCharsets.UTF_8))) {
            var zeile = leser.readLine();
            var nummer = 1;
            while ((zeile = leser.readLine()) != null) {
                nummer++;
                if (zeile.isBlank()) {
                    continue;
                }
                bestellungen.add(parse(zeile, nummer));
            }
        }
        return bestellungen;
    }

    private Bestellung parse(String zeile, int nummer) {
        String[] felder = zeile.split(";", -1);
        if (felder.length != 9) {
            throw new IllegalArgumentException("Zeile " + nummer + ": 9 Felder erwartet, " + felder.length + " gefunden");
        }
        var kunde = new Kunde(felder[0], felder[1], null);
        var position = new Position(felder[2], felder[3], Integer.parseInt(felder[4]), new BigDecimal(felder[5]));
        return new Bestellung(kunde, List.of(position), zahlart(felder[6], felder[7], nummer),
                LocalDate.parse(felder[8]));
    }

    private Zahlart zahlart(String art, String detail, int nummer) {
        if ("RECHNUNG".equals(art)) {
            return new Rechnung(Integer.parseInt(detail));
        } else if ("LASTSCHRIFT".equals(art)) {
            return new Lastschrift(detail);
        } else if ("KREDITKARTE".equals(art)) {
            String[] teile = detail.split("\\|");
            return new Kreditkarte(teile[0], YearMonth.parse(teile[1]));
        } else if ("PAYPAL".equals(art)) {
            return new PayPal(detail);
        }
        throw new IllegalArgumentException("Zeile " + nummer + ": unbekannte Zahlart " + art);
    }
}

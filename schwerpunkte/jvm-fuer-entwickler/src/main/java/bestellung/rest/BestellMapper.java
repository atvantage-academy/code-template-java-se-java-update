package bestellung.rest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import bestellung.domaene.Bestellung;
import bestellung.domaene.Kreditkarte;
import bestellung.domaene.Kunde;
import bestellung.domaene.Lastschrift;
import bestellung.domaene.PayPal;
import bestellung.domaene.Position;
import bestellung.domaene.Rechnung;
import bestellung.domaene.Zahlart;
import bestellung.domaene.ZahlartBeschreibung;

/**
 * Übersetzt zwischen JSON und Domäne.
 */
public class BestellMapper {

    public Map<String, Object> alsJson(Bestellung bestellung, BigDecimal gesamtbetrag) {
        Map<String, Object> json = new LinkedHashMap<String, Object>();
        json.put("id", bestellung.getId());
        json.put("kundennummer", bestellung.getKunde().getKundennummer());
        json.put("kunde", bestellung.getKunde().getName());
        json.put("bestelltAm", bestellung.getBestelltAm().toString());
        json.put("status", bestellung.getStatus().name());
        json.put("zahlart", ZahlartBeschreibung.beschreibe(bestellung.getZahlart()));
        List<Map<String, Object>> positionen = new ArrayList<Map<String, Object>>();
        for (Position position : bestellung.getPositionen()) {
            Map<String, Object> p = new LinkedHashMap<String, Object>();
            p.put("artikelnummer", position.getArtikelnummer());
            p.put("bezeichnung", position.getBezeichnung());
            p.put("menge", position.getMenge());
            p.put("einzelpreis", position.getEinzelpreis());
            positionen.add(p);
        }
        json.put("positionen", positionen);
        json.put("gesamtbetrag", gesamtbetrag);
        return json;
    }

    /**
     * Liest eine neue Bestellung, etwa:
     *
     * <pre>
     * {"kunde": {"kundennummer": "K-2001", "name": "Ada Lovelace"},
     *  "zahlart": {"art": "RECHNUNG", "zahlungszielTage": 14},
     *  "positionen": [{"artikelnummer": "A-100", "bezeichnung": "Kaffeebohnen, 1 kg",
     *                  "menge": 2, "einzelpreis": 18.90}]}
     * </pre>
     */
    public Bestellung ausJson(JsonNode json) {
        JsonNode kunde = pflicht(json, "kunde");
        List<Position> positionen = new ArrayList<Position>();
        for (JsonNode p : pflicht(json, "positionen")) {
            positionen.add(new Position(
                    pflicht(p, "artikelnummer").asText(),
                    pflicht(p, "bezeichnung").asText(),
                    pflicht(p, "menge").asInt(),
                    pflicht(p, "einzelpreis").decimalValue()));
        }
        return new Bestellung(
                new Kunde(pflicht(kunde, "kundennummer").asText(), pflicht(kunde, "name").asText(),
                        kunde.hasNonNull("email") ? kunde.get("email").asText() : null),
                positionen,
                zahlart(pflicht(json, "zahlart")),
                LocalDate.now());
    }

    private Zahlart zahlart(JsonNode json) {
        String art = pflicht(json, "art").asText();
        if ("RECHNUNG".equals(art)) {
            return new Rechnung(pflicht(json, "zahlungszielTage").asInt());
        } else if ("LASTSCHRIFT".equals(art)) {
            return new Lastschrift(pflicht(json, "iban").asText());
        } else if ("KREDITKARTE".equals(art)) {
            return new Kreditkarte(pflicht(json, "kartennummer").asText(),
                    YearMonth.parse(pflicht(json, "gueltigBis").asText()));
        } else if ("PAYPAL".equals(art)) {
            return new PayPal(pflicht(json, "konto").asText());
        }
        throw new IllegalArgumentException("Unbekannte Zahlart: " + art);
    }

    private static JsonNode pflicht(JsonNode json, String feld) {
        JsonNode wert = json.get(feld);
        if (wert == null || wert.isNull()) {
            throw new IllegalArgumentException("Pflichtfeld fehlt: " + feld);
        }
        return wert;
    }
}

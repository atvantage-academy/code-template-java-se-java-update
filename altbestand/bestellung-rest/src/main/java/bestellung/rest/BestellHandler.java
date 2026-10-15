package bestellung.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import bestellung.domaene.BestellService;
import bestellung.domaene.Bestellung;
import bestellung.domaene.Position;
import bestellung.extern.Lagerdienst;

/**
 * Die Ressource {@code /bestellungen}: Controller-Schicht über dem {@link BestellService}.
 *
 * <ul>
 * <li>{@code GET /bestellungen} – alle Bestellungen</li>
 * <li>{@code GET /bestellungen/{id}} – eine Bestellung</li>
 * <li>{@code POST /bestellungen} – eine neue Bestellung; jede Position wird beim Lager geprüft</li>
 * <li>{@code DELETE /bestellungen/{id}} – stornieren</li>
 * </ul>
 */
public class BestellHandler implements HttpHandler {

    private static final String BASIS = "/bestellungen";

    private final BestellService service;
    private final Lagerdienst lagerdienst;
    private final BestellMapper mapper;
    private final ObjectMapper json = new ObjectMapper();

    public BestellHandler(BestellService service, Lagerdienst lagerdienst, BestellMapper mapper) {
        this.service = service;
        this.lagerdienst = lagerdienst;
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String methode = exchange.getRequestMethod();
            String id = idAusPfad(exchange.getRequestURI().getPath());
            if ("GET".equals(methode) && id == null) {
                alle(exchange);
            } else if ("GET".equals(methode)) {
                eine(exchange, id);
            } else if ("POST".equals(methode) && id == null) {
                anlegen(exchange);
            } else if ("DELETE".equals(methode) && id != null) {
                stornieren(exchange, id);
            } else {
                antworte(exchange, 405, fehler("Methode nicht erlaubt: " + methode));
            }
        } catch (IllegalArgumentException e) {
            antworte(exchange, 400, fehler(e.getMessage()));
        } catch (IllegalStateException e) {
            antworte(exchange, 409, fehler(e.getMessage()));
        } finally {
            exchange.close();
        }
    }

    private void alle(HttpExchange exchange) throws IOException {
        List<Map<String, Object>> liste = new ArrayList<Map<String, Object>>();
        for (Bestellung bestellung : service.alleNeuesteZuerst()) {
            liste.add(mapper.alsJson(bestellung, service.gesamtbetrag(bestellung)));
        }
        antworte(exchange, 200, liste);
    }

    private void eine(HttpExchange exchange, String id) throws IOException {
        Optional<Bestellung> gefunden = service.finde(id);
        if (gefunden.isPresent()) {
            Bestellung bestellung = gefunden.get();
            antworte(exchange, 200, mapper.alsJson(bestellung, service.gesamtbetrag(bestellung)));
        } else {
            antworte(exchange, 404, fehler("Keine Bestellung mit der ID " + id));
        }
    }

    private void anlegen(HttpExchange exchange) throws IOException {
        JsonNode anfrage = json.readTree(lies(exchange.getRequestBody()));
        Bestellung bestellung = mapper.ausJson(anfrage);
        for (Position position : bestellung.getPositionen()) {
            if (!lagerdienst.istVorrätig(position.artikelnummer())) {
                throw new IllegalStateException("Nicht vorrätig: " + position.artikelnummer());
            }
        }
        Bestellung angelegt = service.anlegen(bestellung);
        exchange.getResponseHeaders().add("Location", BASIS + "/" + angelegt.getId());
        antworte(exchange, 201, mapper.alsJson(angelegt, service.gesamtbetrag(angelegt)));
    }

    private void stornieren(HttpExchange exchange, String id) throws IOException {
        Bestellung storniert = service.stornieren(id);
        antworte(exchange, 200, mapper.alsJson(storniert, service.gesamtbetrag(storniert)));
    }

    private static String idAusPfad(String pfad) {
        String rest = pfad.substring(BASIS.length());
        if (rest.isEmpty() || "/".equals(rest)) {
            return null;
        }
        return rest.substring(1);
    }

    private static Map<String, Object> fehler(String meldung) {
        return java.util.Collections.<String, Object>singletonMap("fehler", meldung);
    }

    private void antworte(HttpExchange exchange, int status, Object körper) throws IOException {
        byte[] bytes = json.writerWithDefaultPrettyPrinter().writeValueAsBytes(körper);
        exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream aus = exchange.getResponseBody();
        try {
            aus.write(bytes);
        } finally {
            aus.close();
        }
    }

    private static String lies(InputStream ein) throws IOException {
        ByteArrayOutputStream puffer = new ByteArrayOutputStream();
        byte[] block = new byte[4096];
        int gelesen;
        while ((gelesen = ein.read(block)) != -1) {
            puffer.write(block, 0, gelesen);
        }
        return new String(puffer.toByteArray(), StandardCharsets.UTF_8);
    }
}

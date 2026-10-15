package bestellung.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestAnwendungTest {

    private RestAnwendung anwendung;

    @BeforeEach
    void starte() throws IOException {
        anwendung = new RestAnwendung(0);
        anwendung.starte();
    }

    @AfterEach
    void stoppe() {
        anwendung.stoppe();
    }

    @Test
    void liefertDieBeispielbestellungen() throws IOException {
        HttpURLConnection verbindung = öffne("/bestellungen");
        assertEquals(200, verbindung.getResponseCode());
        String körper = lies(verbindung.getInputStream());
        assertTrue(körper.contains("\"id\" : \"B-00004\""), körper);
        assertTrue(körper.contains("Jana Müller"), körper);
    }

    @Test
    void unbekannteBestellungGibt404() throws IOException {
        assertEquals(404, öffne("/bestellungen/B-99999").getResponseCode());
    }

    @Test
    void legtEineBestellungAn() throws IOException {
        assertEquals(201, Lasttest.sendeBestellung(anwendung.getPort()));
        HttpURLConnection verbindung = öffne("/bestellungen/B-00005");
        assertEquals(200, verbindung.getResponseCode());
        assertTrue(lies(verbindung.getInputStream()).contains("Ada Lovelace"));
    }

    private HttpURLConnection öffne(String pfad) throws IOException {
        return (HttpURLConnection) URI.create("http://localhost:" + anwendung.getPort() + pfad).toURL().openConnection();
    }

    private static String lies(InputStream ein) {
        Scanner scanner = new Scanner(ein, "UTF-8").useDelimiter("\\A");
        try {
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            scanner.close();
        }
    }
}

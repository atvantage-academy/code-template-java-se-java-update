package neuerungen.switchausdruecke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ZahlartregelnTest {

    @Test
    void liestKürzelOhneRücksichtAufSchreibweise() {
        assertEquals(Zahlart.RECHNUNG, Zahlartregeln.ausKürzel("RE"));
        assertEquals(Zahlart.LASTSCHRIFT, Zahlartregeln.ausKürzel("ls"));
        assertEquals(Zahlart.LASTSCHRIFT, Zahlartregeln.ausKürzel("SEPA"));
        assertEquals(Zahlart.KREDITKARTE, Zahlartregeln.ausKürzel(" kk "));
        assertEquals(Zahlart.PAYPAL, Zahlartregeln.ausKürzel("PP"));
    }

    @Test
    void lehntUnbekannteKürzelAb() {
        assertThrows(IllegalArgumentException.class, () -> Zahlartregeln.ausKürzel("BAR"));
    }

    @Test
    void kenntDasZahlungszielJeZahlart() {
        assertEquals(14, Zahlartregeln.zahlungszielTage(Zahlart.RECHNUNG));
        assertEquals(3, Zahlartregeln.zahlungszielTage(Zahlart.LASTSCHRIFT));
        assertEquals(0, Zahlartregeln.zahlungszielTage(Zahlart.KREDITKARTE));
        assertEquals(0, Zahlartregeln.zahlungszielTage(Zahlart.PAYPAL));
    }

    @Test
    void gibtJeZahlartEinenHinweis() {
        assertEquals("Bitte zahle innerhalb von 14 Tagen.", Zahlartregeln.hinweis(Zahlart.RECHNUNG));
        assertEquals("Wir buchen den Betrag von Deinem Konto ab.", Zahlartregeln.hinweis(Zahlart.LASTSCHRIFT));
        assertEquals("Der Betrag ist bereits bezahlt.", Zahlartregeln.hinweis(Zahlart.KREDITKARTE));
        assertEquals("Der Betrag ist bereits bezahlt.", Zahlartregeln.hinweis(Zahlart.PAYPAL));
    }
}

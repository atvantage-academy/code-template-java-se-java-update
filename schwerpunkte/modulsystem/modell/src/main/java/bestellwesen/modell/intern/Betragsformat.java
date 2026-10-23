package bestellwesen.modell.intern;

/**
 * Schreibt Beträge in Cent als Euro, etwa „1.234,50 €“ – unabhängig von der
 * Spracheinstellung des Rechners.
 *
 * <p><b>Nur für das Modell gedacht</b>, von außen gilt {@link bestellwesen.modell.Betrag}.
 * Das Paket heißt „intern“, damit sich niemand darauf verlässt. Verhindern kann das
 * unter Java 8 nur der Name: Die Klasse ist {@code public}, also kann sie jeder aufrufen.
 */
public final class Betragsformat {

    private Betragsformat() {
    }

    public static String alsEuro(long cent) {
        String vorzeichen = cent < 0 ? "-" : "";
        long betrag = Math.abs(cent);
        String euro = String.valueOf(betrag / 100);
        StringBuilder mitPunkten = new StringBuilder();
        for (int i = 0; i < euro.length(); i++) {
            if (i > 0 && (euro.length() - i) % 3 == 0) {
                mitPunkten.append('.');
            }
            mitPunkten.append(euro.charAt(i));
        }
        long rest = betrag % 100;
        return vorzeichen + mitPunkten + "," + (rest < 10 ? "0" : "") + rest + " €";
    }
}

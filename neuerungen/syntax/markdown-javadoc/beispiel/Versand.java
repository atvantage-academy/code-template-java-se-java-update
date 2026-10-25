/// Berechnet die **Versandkosten** einer Bestellung.
///
/// Die Staffel:
///
/// | Warenwert      | Versand  |
/// |----------------|----------|
/// | unter 50,00 €  | 4,90 €   |
/// | ab 50,00 €     | frei     |
///
/// Beispiel:
///
/// ```java
/// double kosten = Versand.kosten(42.0);   // 4.9
/// ```
///
/// Mehr zu Zahlen mit Nachkommastellen: [java.math.BigDecimal].
public class Versand {

    private Versand() {
    }

    /// Die Versandkosten für den Warenwert, in Euro.
    ///
    /// - unter 50,00 € fallen `4.90` an,
    /// - sonst nichts.
    ///
    /// @param warenwert der Warenwert in Euro
    /// @return die Versandkosten in Euro
    public static double kosten(double warenwert) {
        return warenwert < 50.0 ? 4.90 : 0.0;
    }

    /// Gibt die Versandkosten für zwei Warenwerte aus.
    ///
    /// @param args wird nicht gelesen
    public static void main(String[] args) {
        System.out.println("Für 42,00 €: " + kosten(42.0));
        System.out.println("Für 80,00 €: " + kosten(80.0));
    }
}

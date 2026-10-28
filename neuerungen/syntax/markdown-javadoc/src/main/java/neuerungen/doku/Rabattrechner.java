package neuerungen.doku;

import java.math.BigDecimal;
import java.math.RoundingMode;

/// Berechnet den Rabatt auf eine Bestellung.
///
/// Die Staffel richtet sich nach dem Warenwert:
///
/// - ab 100,00 € gibt es **5 %**,
/// - ab 500,00 € gibt es **10 %**.
///
/// Beispiel:
///
/// ```java
/// BigDecimal rabatt = Rabattrechner.rabatt(new BigDecimal("250.00"));
/// // 12.50
/// ```
///
/// @see java.math.BigDecimal
public final class Rabattrechner {

    private static final BigDecimal STUFE_1 = new BigDecimal("100.00");
    private static final BigDecimal STUFE_2 = new BigDecimal("500.00");

    private Rabattrechner() {
    }

    /// Der Rabatt für den Warenwert, auf Cent gerundet.
    ///
    /// Unter 100,00 € ist der Rabatt `0.00`. Gerundet wird kaufmännisch
    /// ([RoundingMode#HALF_UP]).
    ///
    /// @param warenwert der Warenwert in Euro, nicht negativ
    /// @return der Rabatt in Euro
    /// @throws IllegalArgumentException wenn der Warenwert negativ ist
    public static BigDecimal rabatt(BigDecimal warenwert) {
        if (warenwert.signum() < 0) {
            throw new IllegalArgumentException("Warenwert negativ: " + warenwert);
        }
        return warenwert.multiply(satz(warenwert)).setScale(2, RoundingMode.HALF_UP);
    }

    /// Der Rabattsatz für den Warenwert, etwa `0.05` für 5 %.
    ///
    /// @param warenwert der Warenwert in Euro
    /// @return der Satz als Dezimalzahl
    public static BigDecimal satz(BigDecimal warenwert) {
        if (warenwert.compareTo(STUFE_2) >= 0) {
            return new BigDecimal("0.10");
        }
        if (warenwert.compareTo(STUFE_1) >= 0) {
            return new BigDecimal("0.05");
        }
        return BigDecimal.ZERO;
    }
}

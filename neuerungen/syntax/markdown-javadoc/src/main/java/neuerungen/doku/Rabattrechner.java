package neuerungen.doku;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Umbauen: Markdown mit /// statt HTML – Absätze, Liste, fett, Codeblock.
/**
 * Berechnet den Rabatt auf eine Bestellung.
 *
 * <p>Die Staffel richtet sich nach dem Warenwert:</p>
 * <ul>
 *   <li>ab 100,00 € gibt es <b>5 %</b>,</li>
 *   <li>ab 500,00 € gibt es <b>10 %</b>.</li>
 * </ul>
 *
 * <p>Beispiel:</p>
 * <pre>{@code
 * BigDecimal rabatt = Rabattrechner.rabatt(new BigDecimal("250.00"));
 * // 12.50
 * }</pre>
 *
 * @see java.math.BigDecimal
 */
public final class Rabattrechner {

    private static final BigDecimal STUFE_1 = new BigDecimal("100.00");
    private static final BigDecimal STUFE_2 = new BigDecimal("500.00");

    private Rabattrechner() {
    }

    // Umbauen: Markdown – `0.00` statt <code>, [RoundingMode#HALF_UP] statt {@link}.
    /**
     * Der Rabatt für den Warenwert, auf Cent gerundet.
     *
     * <p>Unter 100,00 € ist der Rabatt <code>0.00</code>. Gerundet wird
     * kaufmännisch ({@link RoundingMode#HALF_UP}).</p>
     *
     * @param warenwert der Warenwert in Euro, nicht negativ
     * @return der Rabatt in Euro
     * @throws IllegalArgumentException wenn der Warenwert negativ ist
     */
    public static BigDecimal rabatt(BigDecimal warenwert) {
        if (warenwert.signum() < 0) {
            throw new IllegalArgumentException("Warenwert negativ: " + warenwert);
        }
        return warenwert.multiply(satz(warenwert)).setScale(2, RoundingMode.HALF_UP);
    }

    // Umbauen: Markdown – `0.05` statt <code>.
    /**
     * Der Rabattsatz für den Warenwert, etwa <code>0.05</code> für 5 %.
     *
     * @param warenwert der Warenwert in Euro
     * @return der Satz als Dezimalzahl
     */
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

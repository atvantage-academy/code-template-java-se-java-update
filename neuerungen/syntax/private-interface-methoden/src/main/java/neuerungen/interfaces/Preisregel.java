package neuerungen.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Rechnet Nettopreise in Bruttopreise um. Wer die Regel umsetzt, nennt nur den
 * Steuersatz; die Rechnung steckt in den Default-Methoden.
 */
public interface Preisregel {

    /** Der Steuersatz als Anteil, etwa 0.19 für 19 %. */
    BigDecimal steuersatz();

    /** Der Bruttopreis zu einem Nettopreis, auf Cent gerundet. */
    default BigDecimal brutto(BigDecimal netto) {
        BigDecimal mitSteuer = netto.multiply(BigDecimal.ONE.add(steuersatz()));
        return Preisregel.aufCentRunden(mitSteuer);
    }

    /** Der Bruttopreis zu einem Nettopreis nach Abzug eines Rabatts in Prozent. */
    default BigDecimal bruttoMitRabatt(BigDecimal netto, int rabattProzent) {
        BigDecimal rabattiert = netto.multiply(BigDecimal.valueOf(100 - rabattProzent))
                .divide(BigDecimal.valueOf(100));
        BigDecimal mitSteuer = rabattiert.multiply(BigDecimal.ONE.add(steuersatz()));
        return Preisregel.aufCentRunden(mitSteuer);
    }

    /**
     * Nur für die beiden Default-Methoden gedacht. Mit Java 8 muss eine statische
     * Methode im Interface öffentlich sein – damit gehört sie zur API, und jeder darf
     * sie aufrufen.
     */
    static BigDecimal aufCentRunden(BigDecimal betrag) {
        return betrag.setScale(2, RoundingMode.HALF_UP);
    }
}

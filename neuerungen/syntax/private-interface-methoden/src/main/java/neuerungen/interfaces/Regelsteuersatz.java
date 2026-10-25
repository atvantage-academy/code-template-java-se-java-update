package neuerungen.interfaces;

import java.math.BigDecimal;

/**
 * Die Preisregel für den Regelsteuersatz von 19 %.
 */
public class Regelsteuersatz implements Preisregel {

    @Override
    public BigDecimal steuersatz() {
        return new BigDecimal("0.19");
    }
}

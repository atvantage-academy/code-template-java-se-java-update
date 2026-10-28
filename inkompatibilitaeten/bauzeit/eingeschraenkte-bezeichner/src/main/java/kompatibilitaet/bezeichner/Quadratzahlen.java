package kompatibilitaet.bezeichner;

/**
 * Die ersten Quadratzahlen.
 */
public class Quadratzahlen extends Erzeuger<Integer> {

    private final int anzahl;

    public Quadratzahlen(int anzahl) {
        this.anzahl = anzahl;
    }

    @Override
    protected void erzeuge() {
        for (int i = 1; i <= anzahl; i++) {
            liefere(i * i);
        }
    }
}

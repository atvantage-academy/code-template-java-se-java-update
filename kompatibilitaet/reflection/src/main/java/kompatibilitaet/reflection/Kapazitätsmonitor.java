package kompatibilitaet.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Misst, wie viel Platz eine {@link ArrayList} reserviert hat – für ein Monitoring,
 * das zu groß angelegte Puffer findet. Eine öffentliche Methode dafür gibt es nicht,
 * also liest der Monitor das interne Feld {@code elementData} per Reflection.
 */
public final class Kapazitätsmonitor {

    private static final Field ELEMENT_DATA;

    static {
        try {
            ELEMENT_DATA = ArrayList.class.getDeclaredField("elementData");
            ELEMENT_DATA.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Kapazitätsmonitor() {
    }

    public static int kapazität(ArrayList<?> liste) {
        try {
            return ((Object[]) ELEMENT_DATA.get(liste)).length;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static double auslastung(ArrayList<?> liste) {
        int kapazität = kapazität(liste);
        return kapazität == 0 ? 1.0 : (double) liste.size() / kapazität;
    }
}

package kompatibilitaet.werkzeuge;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Vergibt fortlaufende Bestellnummern, auch wenn viele Threads gleichzeitig fragen – ohne
 * Sperre, mit {@code compareAndSwapLong} aus {@code sun.misc.Unsafe}. So schrieb man das
 * vor Java 9, wenn es schnell gehen sollte. {@code sun.misc.Unsafe} ist eine interne API
 * des JDK, keine offizielle.
 */
public final class Nummernkreis {

    private static final Unsafe UNSAFE;
    private static final long WERT;

    static {
        try {
            Field feld = Unsafe.class.getDeclaredField("theUnsafe");
            feld.setAccessible(true);
            UNSAFE = (Unsafe) feld.get(null);
            WERT = UNSAFE.objectFieldOffset(Nummernkreis.class.getDeclaredField("wert"));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private volatile long wert;

    public long nächste() {
        long alt;
        do {
            alt = wert;
        } while (!UNSAFE.compareAndSwapLong(this, WERT, alt, alt + 1));
        return alt + 1;
    }
}

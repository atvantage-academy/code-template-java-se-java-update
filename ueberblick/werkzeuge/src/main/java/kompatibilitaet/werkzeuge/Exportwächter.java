package kompatibilitaet.werkzeuge;

/**
 * Startet einen Export in einem eigenen Thread und bricht ihn ab, wenn er das Zeitlimit
 * überschreitet – mit {@code Thread.stop()}.
 */
public final class Exportwächter {

    private Exportwächter() {
    }

    /**
     * @return {@code true}, wenn der Export rechtzeitig fertig wurde
     */
    public static boolean führeAus(Runnable export, long zeitlimitMillis) throws InterruptedException {
        Thread läufer = new Thread(export, "export");
        läufer.start();
        läufer.join(zeitlimitMillis);
        if (läufer.isAlive()) {
            läufer.stop();
            return false;
        }
        return true;
    }
}

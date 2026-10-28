package kompatibilitaet.securitymanager;

/**
 * Startet ein Plugin in einer eigenen JVM – aufgerufen vom {@link PluginAusfuehrer}.
 */
public final class PluginStarter {

    private PluginStarter() {
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        Class<?> plugin = Class.forName(args[0]);
        ((Runnable) plugin.getDeclaredConstructor().newInstance()).run();
    }
}

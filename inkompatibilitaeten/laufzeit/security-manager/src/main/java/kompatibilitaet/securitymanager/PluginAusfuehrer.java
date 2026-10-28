package kompatibilitaet.securitymanager;

import java.security.Permission;

/**
 * Führt Plugins von Drittanbietern aus. Ruft ein Plugin {@code System.exit(…)},
 * soll nicht die ganze Anwendung enden – das verhindert ein Security Manager,
 * der genau diesen Aufruf abfängt und alles andere erlaubt.
 */
public final class PluginAusfuehrer {

    private PluginAusfuehrer() {
    }

    public static void führeAus(Runnable plugin) {
        SecurityManager vorher = System.getSecurityManager();
        System.setSecurityManager(new ExitVerhinderer());
        try {
            plugin.run();
        } finally {
            System.setSecurityManager(vorher);
        }
    }

    /**
     * Wird geworfen, wenn ein Plugin die Anwendung beenden wollte.
     */
    public static class PluginBeendetException extends SecurityException {

        private static final long serialVersionUID = 1L;

        private final int status;

        PluginBeendetException(int status) {
            super("Plugin wollte die Anwendung mit Status " + status + " beenden");
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private static class ExitVerhinderer extends SecurityManager {

        @Override
        public void checkExit(int status) {
            throw new PluginBeendetException(status);
        }

        @Override
        public void checkPermission(Permission perm) {
            // alles andere ist erlaubt
        }
    }
}

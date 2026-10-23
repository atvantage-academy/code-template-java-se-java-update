package bestellwesen.export;

import java.util.List;

import bestellwesen.modell.Bestellung;

/**
 * Der Erweiterungspunkt: ein Format, in das sich Bestellungen exportieren lassen.
 *
 * <p>Die Anwendung kennt kein Format beim Namen. Sie fragt den
 * {@link java.util.ServiceLoader} nach allen, die zur Laufzeit da sind.
 */
public interface Exportformat {

    /** Ein kurzer Name, etwa „csv“. */
    String name();

    /** Die Bestellungen in diesem Format. */
    String exportiere(List<Bestellung> bestellungen);
}

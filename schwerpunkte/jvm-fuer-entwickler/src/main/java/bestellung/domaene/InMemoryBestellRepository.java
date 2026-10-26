package bestellung.domaene;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Hält die Bestellungen im Speicher. Mehrere Threads greifen zu – deshalb
 * {@code synchronized}, wie es in Java 8 üblich war.
 */
public class InMemoryBestellRepository implements BestellRepository {

    private final Map<String, Bestellung> bestellungen = new LinkedHashMap<String, Bestellung>();
    private int letzteNummer;

    @Override
    public synchronized Bestellung speichere(Bestellung bestellung) {
        if (bestellung.getId() == null) {
            letzteNummer++;
            bestellung.setId(String.format("B-%05d", letzteNummer));
        }
        bestellungen.put(bestellung.getId(), bestellung);
        return bestellung;
    }

    @Override
    public synchronized Optional<Bestellung> finde(String id) {
        return Optional.ofNullable(bestellungen.get(id));
    }

    @Override
    public synchronized List<Bestellung> alle() {
        return new ArrayList<Bestellung>(bestellungen.values());
    }
}

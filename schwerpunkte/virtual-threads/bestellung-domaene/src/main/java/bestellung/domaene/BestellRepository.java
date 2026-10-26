package bestellung.domaene;

import java.util.List;
import java.util.Optional;

/**
 * Ablage der Bestellungen.
 */
public interface BestellRepository {

    Bestellung speichere(Bestellung bestellung);

    Optional<Bestellung> finde(String id);

    List<Bestellung> alle();
}

package bestellung.domaene;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Die Geschäftslogik der Bestellabwicklung.
 */
public class BestellService {

    private final BestellRepository repository;

    public BestellService(BestellRepository repository) {
        this.repository = repository;
    }

    public Bestellung anlegen(Bestellung bestellung) {
        if (bestellung.getKunde() == null) {
            throw new IllegalArgumentException("Eine Bestellung braucht einen Kunden.");
        }
        if (bestellung.getPositionen().isEmpty()) {
            throw new IllegalArgumentException("Eine Bestellung braucht mindestens eine Position.");
        }
        if (bestellung.getZahlart() == null) {
            throw new IllegalArgumentException("Eine Bestellung braucht eine Zahlart.");
        }
        bestellung.setStatus(Status.NEU);
        return repository.speichere(bestellung);
    }

    public Optional<Bestellung> finde(String id) {
        return repository.finde(id);
    }

    /**
     * Alle Bestellungen, die neueste zuerst; bei gleichem Datum nach ID.
     */
    public List<Bestellung> alleNeuesteZuerst() {
        return repository.alle().stream()
                .sorted(Comparator.comparing(Bestellung::getBestelltAm).reversed()
                        .thenComparing(Bestellung::getId))
                .toList();
    }

    /**
     * Was die Kundin oder der Kunde insgesamt zahlt: Warenwert plus Gebühr der Zahlart.
     */
    public BigDecimal gesamtbetrag(Bestellung bestellung) {
        BigDecimal warenwert = bestellung.getWarenwert();
        return warenwert.add(GebührenRechner.gebühr(bestellung.getZahlart(), warenwert));
    }

    public Bestellung stornieren(String id) {
        var bestellung = repository.finde(id)
                .orElseThrow(() -> new IllegalArgumentException("Keine Bestellung mit der ID " + id));
        if (bestellung.getStatus() == Status.VERSENDET) {
            throw new IllegalStateException("Eine versendete Bestellung kann nicht storniert werden: " + id);
        }
        bestellung.setStatus(Status.STORNIERT);
        return repository.speichere(bestellung);
    }

    /**
     * Umsatz je Zahlart (Name der Klasse), ohne stornierte Bestellungen.
     */
    public Map<String, BigDecimal> umsatzJeZahlart() {
        return repository.alle().stream()
                .filter(b -> b.getStatus() != Status.STORNIERT)
                .collect(Collectors.groupingBy(
                        b -> b.getZahlart().getClass().getSimpleName(),
                        TreeMap::new,
                        Collectors.reducing(BigDecimal.ZERO, this::gesamtbetrag, BigDecimal::add)));
    }

    /**
     * Ein Bericht als Text, etwa für eine E-Mail an den Vertrieb.
     */
    public String bericht() {
        var bestellungen = alleNeuesteZuerst().stream()
                .map(b -> "%-8s %-10s %-12s %10s EUR".formatted(
                        b.getId(), b.getBestelltAm(), b.getStatus(), gesamtbetrag(b)))
                .collect(Collectors.joining("\n"));
        var umsatz = umsatzJeZahlart().entrySet().stream()
                .map(e -> "  %-12s %10s EUR".formatted(e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
        return """
                Bestellbericht
                ==============

                %s

                Umsatz je Zahlart:
                %s
                """.formatted(bestellungen, umsatz);
    }
}

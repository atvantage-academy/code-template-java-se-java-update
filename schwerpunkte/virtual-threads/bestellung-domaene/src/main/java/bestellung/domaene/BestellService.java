package bestellung.domaene;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
        List<Bestellung> alle = new ArrayList<Bestellung>(repository.alle());
        Collections.sort(alle, new Comparator<Bestellung>() {
            @Override
            public int compare(Bestellung a, Bestellung b) {
                int nachDatum = b.getBestelltAm().compareTo(a.getBestelltAm());
                return nachDatum != 0 ? nachDatum : a.getId().compareTo(b.getId());
            }
        });
        return alle;
    }

    /**
     * Was die Kundin oder der Kunde insgesamt zahlt: Warenwert plus Gebühr der Zahlart.
     */
    public BigDecimal gesamtbetrag(Bestellung bestellung) {
        BigDecimal warenwert = bestellung.getWarenwert();
        return warenwert.add(GebuehrenRechner.gebühr(bestellung.getZahlart(), warenwert));
    }

    public Bestellung stornieren(String id) {
        Optional<Bestellung> gefunden = repository.finde(id);
        if (!gefunden.isPresent()) {
            throw new IllegalArgumentException("Keine Bestellung mit der ID " + id);
        }
        Bestellung bestellung = gefunden.get();
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
        StringBuilder text = new StringBuilder();
        text.append("Bestellbericht\n");
        text.append("==============\n");
        text.append("\n");
        for (Bestellung bestellung : alleNeuesteZuerst()) {
            text.append(String.format("%-8s %-10s %-12s %10s EUR%n",
                    bestellung.getId(),
                    bestellung.getBestelltAm(),
                    bestellung.getStatus(),
                    gesamtbetrag(bestellung)));
        }
        text.append("\n");
        text.append("Umsatz je Zahlart:\n");
        for (Map.Entry<String, BigDecimal> eintrag : umsatzJeZahlart().entrySet()) {
            text.append(String.format("  %-12s %10s EUR%n", eintrag.getKey(), eintrag.getValue()));
        }
        return text.toString();
    }
}

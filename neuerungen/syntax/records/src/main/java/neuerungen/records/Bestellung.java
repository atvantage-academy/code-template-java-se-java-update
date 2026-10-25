package neuerungen.records;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Eine Bestellung. Sie hat eine Nummer, die sie ein Leben lang behält, und einen
 * Status, der sich in der Abwicklung ändert. Zwei Bestellungen sind gleich, wenn sie
 * dieselbe Nummer haben – egal, was gerade in ihnen steht.
 */
public final class Bestellung {

    private final String nummer;
    private final Kunde kunde;
    private final List<Position> positionen;
    private Status status = Status.OFFEN;

    public Bestellung(String nummer, Kunde kunde, List<Position> positionen) {
        this.nummer = Objects.requireNonNull(nummer, "nummer");
        this.kunde = Objects.requireNonNull(kunde, "kunde");
        this.positionen = new ArrayList<Position>(positionen);
    }

    public String getNummer() {
        return nummer;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public List<Position> getPositionen() {
        return Collections.unmodifiableList(positionen);
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getGesamtsumme() {
        BigDecimal summe = BigDecimal.ZERO;
        for (Position position : positionen) {
            summe = summe.add(position.getSumme());
        }
        return summe;
    }

    public void versenden() {
        if (status != Status.OFFEN) {
            throw new IllegalStateException("Nur offene Bestellungen werden versandt: " + nummer);
        }
        status = Status.VERSANDT;
    }

    public void stornieren() {
        if (status == Status.VERSANDT) {
            throw new IllegalStateException("Versandte Bestellungen werden nicht storniert: " + nummer);
        }
        status = Status.STORNIERT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return nummer.equals(((Bestellung) o).nummer);
    }

    @Override
    public int hashCode() {
        return nummer.hashCode();
    }
}

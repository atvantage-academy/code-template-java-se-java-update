package bestellung.domaene;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Eine Bestellung. Veränderlich: Ihr Status wechselt im Lauf der Abwicklung.
 */
public class Bestellung {

    private String id;
    private Kunde kunde;
    private List<Position> positionen = new ArrayList<Position>();
    private Zahlart zahlart;
    private LocalDate bestelltAm;
    private Status status = Status.NEU;

    public Bestellung() {
    }

    public Bestellung(Kunde kunde, List<Position> positionen, Zahlart zahlart, LocalDate bestelltAm) {
        this.kunde = kunde;
        this.positionen = new ArrayList<Position>(positionen);
        this.zahlart = zahlart;
        this.bestelltAm = bestelltAm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public List<Position> getPositionen() {
        return Collections.unmodifiableList(positionen);
    }

    public void setPositionen(List<Position> positionen) {
        this.positionen = new ArrayList<Position>(positionen);
    }

    public Zahlart getZahlart() {
        return zahlart;
    }

    public void setZahlart(Zahlart zahlart) {
        this.zahlart = zahlart;
    }

    public LocalDate getBestelltAm() {
        return bestelltAm;
    }

    public void setBestelltAm(LocalDate bestelltAm) {
        this.bestelltAm = bestelltAm;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Die Summe aller Positionen, ohne Gebühren.
     */
    public BigDecimal getWarenwert() {
        BigDecimal summe = BigDecimal.ZERO;
        for (Position position : positionen) {
            summe = summe.add(position.summe());
        }
        return summe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bestellung that = (Bestellung) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bestellung{id=" + id + ", kunde=" + kunde.kundennummer() + ", positionen="
                + positionen.size() + ", zahlart=" + zahlart + ", status=" + status + "}";
    }
}

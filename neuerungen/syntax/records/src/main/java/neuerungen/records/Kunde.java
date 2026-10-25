package neuerungen.records;

import java.util.Objects;

/**
 * Wer bestellt. Die E-Mail-Adresse ist freiwillig und darf fehlen.
 */
// Umbauen: ein Record – wie Position.
public final class Kunde {

    private final String kundennummer;
    private final String name;
    private final String email;

    public Kunde(String kundennummer, String name, String email) {
        this.kundennummer = Objects.requireNonNull(kundennummer, "kundennummer");
        this.name = Objects.requireNonNull(name, "name");
        this.email = email;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kunde kunde = (Kunde) o;
        return kundennummer.equals(kunde.kundennummer)
                && name.equals(kunde.name)
                && Objects.equals(email, kunde.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kundennummer, name, email);
    }

    @Override
    public String toString() {
        return "Kunde[kundennummer=" + kundennummer + ", name=" + name + ", email=" + email + "]";
    }
}

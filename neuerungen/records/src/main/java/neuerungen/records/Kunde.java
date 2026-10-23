package neuerungen.records;

import java.util.Objects;

/**
 * Wer bestellt. Die E-Mail-Adresse ist freiwillig und darf fehlen.
 */
public record Kunde(String kundennummer, String name, String email) {

    public Kunde {
        Objects.requireNonNull(kundennummer, "kundennummer");
        Objects.requireNonNull(name, "name");
    }
}

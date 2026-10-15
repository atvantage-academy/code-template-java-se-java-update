package bestellung.domaene;

import java.util.Objects;

/**
 * Ein Kunde, der bestellt. Die E-Mail-Adresse darf fehlen ({@code null}).
 */
public record Kunde(String kundennummer, String name, String email) {

    public Kunde {
        Objects.requireNonNull(kundennummer, "kundennummer");
        Objects.requireNonNull(name, "name");
    }
}

/**
 * Kompakte Quelldatei (JEP 512, Java 25): keine Klasse, kein static, kein String[] args,
 * keine Importe aus java.base – und IO statt System.out.
 *
 * Starten im Wurzelordner des Übungsprojekts, mit JDK 25:
 *
 *     java neuerungen/syntax/kompakte-quelldateien/beispiel/Hallo.java
 *
 * Nimm danach die Kommentarzeichen vor der markierten Zeile am Ende von main weg und
 * starte noch einmal. Was meldet der Compiler?
 */

final String GRUSS = "Hallo";

void main() {
    var artikel = List.of("Kaffeebecher", "Teekanne", "Untersetzer");
    IO.println(GRUSS + ", " + artikel.size() + " Artikel im Lager:");
    for (var name : artikel) {
        IO.println("- " + name + " (" + länge(name) + " Zeichen)");
    }
    IO.println("Inventur am Silvester, einem " + LocalDate.of(2026, 12, 31).getDayOfWeek());

    // Hallo zweites = new Hallo();
}

int länge(String text) {
    return text.length();
}


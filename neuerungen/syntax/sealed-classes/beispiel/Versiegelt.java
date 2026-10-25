/**
 * Versiegelte Klassen und Interfaces (JEP 409, Java 17) und der switch darüber
 * (JEP 441, Java 21).
 *
 * Starten im Wurzelordner des Übungsprojekts, ab JDK 21:
 *
 *     java neuerungen/syntax/sealed-classes/beispiel/Versiegelt.java
 *
 * Nimm danach die Kommentarzeichen vor einer der markierten Zeilen weg und starte
 * noch einmal. Was meldet der Compiler? Probiere jede Zeile einzeln.
 */
public class Versiegelt {

    sealed interface Form permits Kreis, Rechteck, Vieleck {
    }

    record Kreis(double radius) implements Form {
    }

    record Rechteck(double breite, double höhe) implements Form {
    }

    // Offen für Erweiterungen: Unter Vieleck darf jede Klasse stehen.
    non-sealed interface Vieleck extends Form {
        double fläche();
    }

    record Dreieck(double grundseite, double höhe) implements Vieleck {
        public double fläche() {
            return grundseite * höhe / 2;
        }
    }

    // record Ellipse(double a, double b) implements Form {}

    static double fläche(Form form) {
        return switch (form) {
            case Kreis k -> Math.PI * k.radius() * k.radius();
            case Rechteck r -> r.breite() * r.höhe();
            case Vieleck v -> v.fläche();
        };
    }

    // static double umfang(Form form) {
    //     return switch (form) {
    //         case Kreis k -> 2 * Math.PI * k.radius();
    //         case Rechteck r -> 2 * (r.breite() + r.höhe());
    //     };
    // }

    public static void main(String[] args) {
        Form[] formen = {new Kreis(1), new Rechteck(2, 3), new Dreieck(4, 3)};
        for (Form form : formen) {
            System.out.printf("%s hat die Fläche %.2f%n", form, fläche(form));
        }
        System.out.println("Form ist versiegelt: " + Form.class.isSealed());
        for (Class<?> erlaubt : Form.class.getPermittedSubclasses()) {
            System.out.println("  erlaubt: " + erlaubt.getSimpleName());
        }
    }
}

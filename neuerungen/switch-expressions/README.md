# Musterlösung · Switch Expressions

**Release:** 17 (JEP 361, Java 14, LTS 17)

- Alle drei Methoden liefern ihr Ergebnis direkt aus einem `switch`-Ausdruck mit
  Pfeilen – ohne `break`, ohne Hilfsvariable, ohne Durchfallen.
- Über das `enum` kommen `zahlungszielTage` und `hinweis` **ohne `default`** aus:
  Fehlt künftig eine Zahlart, übersetzt der Code nicht mehr.
- Über den `String` in `ausKürzel` bleibt `default` Pflicht; `throw` ist dort ein
  gültiger Fall.
- `hinweis` liefert den Wert eines Falls mit zwei Anweisungen über `yield`.

**Worauf achten:** Wird das `enum` neu übersetzt, der `switch` aber nicht, fängt der
Compiler nichts ab – zur Laufzeit wirft der `switch` dann bei der neuen Konstante.

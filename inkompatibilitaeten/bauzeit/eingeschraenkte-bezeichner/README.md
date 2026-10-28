# Lösung · Eingeschränkte Bezeichner (`yield`)

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `yield outside of switch expression` – beim **Aufruf** der eigenen
  Methode `yield(…)`, nicht bei ihrer Deklaration.
- **Ursache:** Seit Java 14 liefert `yield` den Wert einer Switch-Expression. Ein
  unqualifizierter Aufruf `yield(x)` liest sich für den Compiler als diese Anweisung.
- **Behebung:** Methode in `liefere(…)` umbenannt. Kleiner, aber unschöner:
  `this.yield(…)` qualifiziert den Aufruf.
- **Kein Bruch:** `var`, `record` und `yield` als **Variablennamen** in `Protokoll`
  übersetzen weiterhin. Eingeschränkte Bezeichner sind keine Schlüsselwörter; nur als
  **Typname** sind `var` und `record` verboten.

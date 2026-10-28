# Musterlösung · Private Interface-Methoden

**Release:** 11 (JEP 213, Java 9, LTS 11)

- Die Rechnung mit dem Steuersatz steht nur noch in der privaten Methode `mitSteuer`.
- `aufCentRunden` ist `private static` und gehört nicht mehr zur API des Interfaces.

**Worauf achten:** Wer `Preisregel.aufCentRunden` bisher von außen aufgerufen hat,
übersetzt nicht mehr – der Umbau ist für Aufrufer ein Bruch. Klassen, die
`Preisregel` umsetzen, merken nichts.

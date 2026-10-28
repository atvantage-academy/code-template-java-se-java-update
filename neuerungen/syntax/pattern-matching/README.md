# Musterlösung · Pattern Matching

**Release:** 21 (JEP 394, Java 16, LTS 17; JEP 441 und 440, Java 21, LTS 21)

- `ZahlartBeschreibung.beschreibe` ist eine Switch-Expression über Typ-Muster statt
  einer `instanceof`-Kette mit Casts.
- Die Rechnung ohne Zahlungsziel ist ein eigener Fall mit Wächter
  (`case Rechnung rechnung when … == 0`); er steht **vor** dem allgemeinen
  `Rechnung`-Fall, sonst meldet der Compiler „this case label is dominated by a
  preceding case label“.
- `null` ist ein eigener Fall (`case null`) statt eines `if` am Ende der Kette.
- Der `default` bleibt: Die Hierarchie ist nicht versiegelt, jede Klasse darf eine
  weitere Zahlart ableiten. Ohne `default` übersetzt der `switch` nicht – „the switch
  expression does not cover all possible input values“.

**Worauf achten:** Record-Patterns (`case Rechnung(var tage)`) gehen hier nicht, weil
die Zahlarten Klassen sind, keine Records. Das Beispiel unter `beispiel/` zeigt sie an
eigenen Records.

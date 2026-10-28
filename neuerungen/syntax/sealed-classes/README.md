# Musterlösung · Sealed Classes

**Release:** 21 (JEP 409, Java 17, LTS 17; der `switch` darüber: JEP 441, Java 21,
LTS 21)

- `Zahlart` ist eine versiegelte abstrakte Klasse mit
  `permits Rechnung, Lastschrift, Kreditkarte, PayPal`. Die vier Unterklassen waren
  schon `final` und bleiben es.
- `GebuehrenRechner.gebühr` ist ein `switch` über die Zahlart **ohne `default`**. Der
  Compiler kennt alle Unterarten und prüft, dass jede behandelt ist.
- `ZahlartVisitor` und alle `accept`-Methoden sind gelöscht – ihre Aufgabe, keine
  Zahlart zu vergessen, übernimmt jetzt der Compiler.

**Worauf achten:** Eine fünfte Zahlart ohne Eintrag in `permits` übersetzt nicht
(„class is not allowed to extend sealed class“). Mit Eintrag meldet der `switch`, dass
ein Fall fehlt („the switch expression does not cover all possible input values“). Ein
`default` würde genau diese Prüfung abschalten.

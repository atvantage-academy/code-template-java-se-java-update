# Musterlösung · Lokale Typinferenz mit `var`

**Release:** 11 (JEP 286, Java 10; JEP 323, Java 11; LTS 11)

- Lokale Variablen und Schleifenvariablen sind mit `var` deklariert, die Parameter des
  Lambdas in `berichtszeilen` ebenso.
- Rückgabetypen, Methodenparameter und die Variable des Lambdas behalten ihren Typ –
  dort erschließt der Compiler nichts.

**Worauf achten:** `var x = new TreeMap<>()` ergäbe eine `TreeMap<Object, Object>` –
mit `var` steht der Typ rechts, also ohne Diamant. Die Variablen haben jetzt den Typ
`TreeMap` bzw. `ArrayList`, nicht `Map` bzw. `List`.

# Musterlösung · Collection Factories und Sequenced Collections

**Release:** 21 (JEP 269, Java 9, LTS 11; JEP 431, Java 21, LTS 21)

- `List.of`, `Set.of` und `Map.of` ersetzen `Arrays.asList`, `Collections.unmodifiable…`
  und den statischen Block.
- `getFirst()`, `getLast()` und `reversed()` ersetzen Indexrechnung und Kopie mit
  `Collections.reverse`.

**Worauf achten:** Die Fabrikmethoden lehnen `null` ab – auch in `contains(null)`.
`Set.of` und `Map.of` legen keine Reihenfolge fest, und sie wechselt zwischen zwei
Starts. `reversed()` liefert eine Ansicht der Liste, keine Kopie.

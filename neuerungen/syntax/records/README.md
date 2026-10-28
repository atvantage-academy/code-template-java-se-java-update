# Musterlösung · Records

**Release:** 17 (JEP 395, Java 16; LTS 17)

- `Position`, `Kunde` und `Kreditkarte` sind Records. Die Prüfungen stehen im
  kompakten Konstruktor; `getSumme()` ist die gewöhnliche Methode `summe()`.
- Die Aufrufer in `Bestellung` und `Rechnungstext` nutzen die Zugriffsmethoden ohne
  `get`: `menge()`, `bezeichnung()`, `kartennummer()` …
- `Kreditkarte` schreibt ihr `toString` selbst – das erzeugte zeigte die volle
  Kartennummer, und `WertklassenTest` würde rot.
- `Bestellung` bleibt eine Klasse: Sie hat eine Nummer als Identität und einen Status,
  der sich ändert. Ihre Gleichheit hängt an der Nummer, nicht am Inhalt.

**Worauf achten:** Ein Record ist nur flach unveränderlich. `Position` hält nur
unveränderliche Werte; wer eine Liste als Komponente hat, kopiert sie im kompakten
Konstruktor mit `List.copyOf`.

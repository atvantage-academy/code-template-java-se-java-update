# Lösung · Datumsformate mit CLDR

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `expected: <… vom Mo, 14. Sep 2026 über 1.234,50 €> but was:
  <… vom Mo., 14. Sept. 2026 über 1.234,50 €>`.
- **Ursache:** Seit Java 9 stammen die Locale-Daten aus dem Unicode CLDR (JEP 252).
  Abkürzungen haben jetzt einen Punkt, „Sept.“ hat vier Buchstaben – und **vor dem €
  steht ein geschütztes Leerzeichen** (U+00A0) statt eines normalen. Das sieht man in
  der Meldung nicht: Wer nur das Datum repariert, hat noch einen roten Test.
- **Behebung:** Die Kopfzeile wird vom Lagersystem eingelesen, ihre Form ist ein
  Vertrag. Deshalb stehen Abkürzungen und Betragsformat im Code
  (`DateTimeFormatterBuilder.appendText(…, Map)`, `DecimalFormat`). Ist ein Text nur
  für Menschen gedacht, ist die andere Antwort genauso richtig: das neue Format
  übernehmen und den Test anpassen.
- **Notlösung:** `-Djava.locale.providers=COMPAT` – **nur bis Java 22**, seit 23 gibt es
  die alten Daten nicht mehr.

# Lösung · Genauere Zeitstempel

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `expected: <BESTELLT@2026-10-14T13:29:57.120836761Z> but was:
  <BESTELLT@2026-10-14T13:29:57.120Z>`.
- **Ursache:** Seit Java 15 liefert `Instant.now()` je nach Betriebssystem Mikro- oder
  sogar Nanosekunden statt Millisekunden. Gespeichert werden Millisekunden – nach dem
  Laden ist das Ereignis ein anderes.
- **Behebung:** Die Anwendung legt die Genauigkeit fest, nicht das JDK:
  `Instant.now(Clock.tickMillis(ZoneOffset.UTC))`. `Clock.tickMillis` gibt es seit
  Java 9. Dieselbe Falle steckt in jeder `TIMESTAMP(3)`-Spalte einer Datenbank.

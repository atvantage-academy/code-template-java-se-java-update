# Lösung · UTF-8 als Standard-Zeichensatz

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `expected: <Jana Müller> but was: <Jana M�ller>`.
- **Ursache:** Bis Java 17 kam der Standard-Zeichensatz vom Betriebssystem – auf einem
  deutschen Windows `windows-1252`. Seit Java 18 ist er überall UTF-8 (JEP 400). Unter
  Linux und macOS war er schon vorher UTF-8; deshalb stellt ein Maven-Profil im Modul das
  Windows-Altsystem bis JDK 17 nach.
- **Behebung:** Der Zeichensatz der Datei steht im Code – `new FileReader(datei,
  charset)` gibt es seit Java 11. Das Profil darf bleiben: Der Test ist jetzt mit jedem
  Standard-Zeichensatz grün.
- **Notlösung:** `-Dfile.encoding=COMPAT` stellt das alte Verhalten wieder her.

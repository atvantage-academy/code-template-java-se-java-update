# Lösung · `_` als Bezeichner

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `as of release 9, '_' is a keyword, and may not be used as an identifier`;
  beim `catch (NumberFormatException _)` unter JDK 21
  `unnamed variables are a preview feature and are disabled by default`.
- **Ursache:** `_` ist seit Java 9 reserviert. Seit Java 22 (LTS 25) ist es die
  **unbenannte Variable** (JEP 456): Ein `catch (… _)` ist dann wieder gültig, ein
  **Zugriff** auf `_` nie mehr.
- **Behebung:** umbenennen.
- **Notlösung:** keine – außer beim alten Release zu bleiben.

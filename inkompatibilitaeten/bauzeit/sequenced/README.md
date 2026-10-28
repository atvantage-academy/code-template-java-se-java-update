# Lösung · Sequenced Collections gegen eigene Methoden

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `getFirst() in kompatibilitaet.sequenced.Warteschlange cannot implement
  getFirst() in java.util.List`.
- **Ursache:** Seit Java 21 erben alle Listen `getFirst()`, `getLast()`, `reversed()`
  und weitere aus `SequencedCollection` (JEP 431). Die eigenen Methoden gleichen Namens
  liefern `Optional` – ein anderer Rückgabetyp, also kein gültiges Überschreiben.
- **Behebung:** Die Warteschlange **hat** eine Liste, statt eine zu **sein**. Die
  Aufrufer bleiben unverändert. Wer von einer JDK-Klasse erbt, erbt auch alles, was das
  JDK ihr künftig hinzufügt.

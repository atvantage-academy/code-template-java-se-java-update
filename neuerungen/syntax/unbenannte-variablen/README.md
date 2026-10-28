# Musterlösung · `_`: von der Warnung zum Sprachmittel

**Release:** 25 (JEP 456, Java 22, LTS 25) – braucht ein JDK 25.

`_` steht an vier Stellen für eine Variable, die der Code nie liest: im `catch` –
dort stand es schon in der Vorlage –, in der zählenden Schleife, als Parameter von
`computeIfAbsent` und als erster Parameter des Lambdas in `forEach`.

**Vorgeschichte:** Bis Java 8 ist `_` ein Bezeichner mit Warnung, ab Java 9 ein
Fehler (JEP 213), ab Java 22 das unbenannte Sprachmittel. Die Vorlage zeigt es am
`catch` in `anzahlZahlen`: Mit Release 8 baut sie mit Warnung, mit Release 9 nicht.

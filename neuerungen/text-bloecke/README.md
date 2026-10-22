# Musterlösung · Text-Blöcke

**Release:** 17 (JEP 378, Java 15, LTS 17)

- `bestätigungAlsJson` ist ein Text-Block mit `%s`-Platzhaltern, gefüllt mit
  `formatted()`. Escapes für Anführungszeichen braucht er nicht.
- Kopf und Gruß von `bericht` sind Text-Blöcke; die Zeilen je Bestellung hängt die
  Schleife an wie bisher.

**Worauf achten:** Die Lage der schließenden `"""` entscheidet über den letzten
Zeilenumbruch – bei der JSON-Bestätigung stehen sie deshalb direkt hinter der `}`.
Zeilenenden in Text-Blöcken sind immer `\n`, auch unter Windows. `formatted()` nutzt
die Sprache des Systems; für Zahlen in fremden Formaten `String.format(Locale, …)`.

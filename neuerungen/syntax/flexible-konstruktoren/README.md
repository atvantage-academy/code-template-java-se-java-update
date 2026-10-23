# Musterlösung · LTS 25: Flexible Konstruktoren

**Release:** 25 (JEP 513, Java 25, LTS 25) – braucht ein JDK 25.

Im Konstruktor von `Bestellnummer` stehen Prüfung und Normalisierung jetzt als
Anweisungen **vor** `super(…)`; die statische Hilfsmethode `normalisiere` entfällt.
`BestellnummerTest` ist unverändert grün.

**Grenze:** Vor `super(…)` darf der Konstruktor Felder der eigenen Klasse belegen, aber
nicht auf `this` zugreifen – keine Methode aufrufen, kein Feld lesen. Das Beispiel
`beispiel/VorSuper.java` zeigt beides.

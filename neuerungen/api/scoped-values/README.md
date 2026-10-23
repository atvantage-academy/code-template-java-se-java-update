# Musterlösung · LTS 25: Scoped Values

**Release:** 25 (JEP 506, Java 25, LTS 25) – braucht ein JDK 25.

`Anfragekontext` hält die Kennung der Anfrage in einem `ScopedValue` statt in einer
`ThreadLocal`. `ScopedValue.where(ANFRAGE, anfrage).call(…)` bindet sie genau für den
Aufruf der Aufgabe; danach – auch nach einer Ausnahme – ist sie wieder ungebunden, und
eine innere Bindung überdeckt die äußere nur für ihren eigenen Aufruf. Das
`try`/`finally` mit dem Wiederherstellen des vorigen Werts entfällt, `orElse("–")`
ersetzt die Prüfung auf `null`. `AnfragekontextTest` ist unverändert grün.

**Grenze:** Ein Scoped Value ist unveränderlich, es gibt kein `set`. Wer einen Wert
während der Anfrage ändern muss, bleibt bei `ThreadLocal` oder gibt ein veränderliches
Objekt in die Bindung.

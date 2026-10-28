# Musterlösung · LTS 25: Markdown in Javadoc

**JDK:** 23 oder neuer für `javadoc` (JEP 467, Java 23, LTS 25). Das Release des
Moduls bleibt bei 8: Ein `///`-Kommentar ist für jeden Compiler ein gewöhnlicher
Zeilenkommentar; nur das Werkzeug `javadoc` ab JDK 23 liest ihn als Dokumentation –
auch mit `--release 8`.

Die Kommentare von `Rabattrechner` sind von HTML auf Markdown umgestellt: `<p>`
entfällt, `<ul>`/`<li>` werden `-`, `<b>` wird `**…**`, `<code>` wird `` `…` ``,
`<pre>{@code …}</pre>` wird ein Codeblock mit ` ``` `, `{@link RoundingMode#HALF_UP}`
wird `[RoundingMode#HALF_UP]`. Block-Tags wie `@param` bleiben, wie sie sind.

Prüfen: `javadoc -Xdoclint:all` erzeugt die Dokumentation ohne Warnung, und
`RabattrechnerTest` ist unverändert grün.

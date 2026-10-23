# Musterlösung · Schwerpunkt Modulsystem

**Release:** 11 (JEP 261, Java 9, LTS 11) – für alle drei Teile in
`schwerpunkte/modulsystem/pom.xml`.

Jeder Teil hat eine `module-info.java`:

- `bestellwesen.modell` exportiert `bestellwesen.modell` und `bestellwesen.export`,
  **nicht** `bestellwesen.modell.intern`.
- `bestellwesen.csv` liest das Modell und meldet `CsvFormat` mit `provides` als
  `Exportformat` an.
- `bestellwesen.anwendung` liest das Modell und erklärt mit `uses`, dass sie den
  `ServiceLoader` nach `Exportformat` fragt. Das CSV-Format nennt sie nicht.

`Exportlauf` schrieb die Gesamtsumme über `bestellwesen.modell.intern.Betragsformat`;
mit Modulen ist das Paket nicht mehr sichtbar, und der Aufruf geht über die API
`Betrag`. Die Alternative wäre ein qualifizierter Export
(`exports bestellwesen.modell.intern to bestellwesen.anwendung;`) – dann wäre die
Kopplung wenigstens sichtbar.

Die Datei `META-INF/services/bestellwesen.export.Exportformat` bleibt: Liegt das
CSV-JAR auf dem Klassenpfad – so führt Surefire die Tests der Anwendung aus, weil sie
das CSV-Format nicht mit `requires` nennt –, findet der `ServiceLoader` es nur darüber.
Auf dem Modulpfad zählt allein `provides`.

**Starten und linken** (im Wurzelordner, nach `mvn clean verify -f schwerpunkte/modulsystem/pom.xml`):

```shell
java --module-path schwerpunkte/modulsystem/target/module --module bestellwesen.anwendung/bestellwesen.anwendung.Exportlauf
```

```shell
jlink --module-path schwerpunkte/modulsystem/target/module --add-modules bestellwesen.anwendung,bestellwesen.csv --strip-debug --no-header-files --no-man-pages --launcher exportlauf=bestellwesen.anwendung/bestellwesen.anwendung.Exportlauf --output schwerpunkte/modulsystem/target/bild
```

Ohne `bestellwesen.csv` in `--add-modules` fehlt der Anbieter im Image, und
`exportlauf` meldet „Kein Exportformat gefunden.“ `--bind-services` holt alle Anbieter,
auch die des JDK – und scheitert auf einem JDK ohne `jmods` (Temurin 25, JEP 493).

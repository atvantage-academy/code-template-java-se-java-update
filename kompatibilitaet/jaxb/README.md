# Lösung · Entfernte Java-EE-Module (JAXB)

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** beim Übersetzen `package javax.xml.bind does not exist`; zur Laufzeit
  mit altem Release `NoClassDefFoundError: javax/xml/bind/JAXB`.
- **Ursache:** Java 11 hat die Java-EE- und CORBA-Module entfernt (JEP 320): JAXB,
  JAX-WS, JAF, Common Annotations.
- **Behebung:** Jakarta XML Binding 4 als Abhängigkeit (API plus Implementierung zur
  Laufzeit), Importe auf `jakarta.xml.bind`.
- **Gut zu wissen:** Der Wechsel von `javax` auf `jakarta` ist **keine Änderung des
  JDK**, sondern der Umzug der Spezifikation zur Eclipse Foundation. Wer beim alten
  Namensraum bleiben muss, nimmt `javax.xml.bind:jaxb-api` 2.3 – eine Sackgasse. Auf
  einem Jakarta-EE-Server und in Quarkus bringt die Plattform die Implementierung mit.

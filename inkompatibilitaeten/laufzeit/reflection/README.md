# Lösung · Reflection auf JDK-Interna

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `InaccessibleObjectException: Unable to make field transient
  java.lang.Object[] java.util.ArrayList.elementData accessible: module java.base does
  not "opens java.util" to unnamed module`.
- **Ursache:** Seit Java 16 sind die Interna des JDK standardmäßig gekapselt (JEP 396),
  seit 17 gibt es keinen Schalter mehr, der alles auf einmal öffnet (JEP 403).
- **Notlösung in diesem Stand:** `--add-opens java.base/java.util=ALL-UNNAMED` im
  Surefire-`argLine` und als `Add-Opens` im Manifest. Sie muss **überall** stehen, wo
  der Code läuft – Test, Startskript, Container.
- **Behebung an der Ursache:** Es gibt keine öffentliche API für die Kapazität einer
  `ArrayList`. Die Frage „wie viel Speicher ist reserviert?“ beantworten ein
  Heap-Histogramm (`jcmd <pid> GC.class_histogram`) oder Java Flight Recorder – nicht
  der Anwendungscode. In echten Projekten steckt dieser Bruch fast immer in einer
  **Bibliothek** (alte Versionen von Lombok, Mockito, ByteBuddy, Hibernate, XStream);
  dort heißt die Behebung: aktualisieren.

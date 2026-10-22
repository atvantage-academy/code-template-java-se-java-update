# Lösung · Der Security Manager

Stand **nach** der Übung: Das Modul ist auf `--release 21` gehoben und auf JDK 21
und 25 grün. Die Meldungen sind auf Temurin 21 und 25 gemessen.

- **Symptom:** `UnsupportedOperationException: The Security Manager is deprecated and
  will be removed in a future release`.
- **Ursache:** Seit Java 17 zur Entfernung vorgesehen (JEP 411), seit Java 18 nur noch
  mit `-Djava.security.manager=allow` einschaltbar, seit Java 24 dauerhaft abgeschaltet
  (JEP 486). **Einen Ersatz im JDK gibt es nicht.**
- **Behebung:** Das Plugin läuft in einer eigenen JVM (`ProcessBuilder`). Ein
  `System.exit` beendet nur diese; der Status kommt als Exit-Code zurück. Die Abgrenzung
  übernimmt das Betriebssystem – oder ein Container.
- **Notlösung:** `-Djava.security.manager=allow` – **nur bis Java 23**.

# Musterlösung: Virtual Threads – Pinning beheben

Baut auf `loesung/virtual-threads` auf: Die Bestellabwicklung läuft auf **Release 21**
und virtuellen Threads, und `Verbindung` sichert ihren Zustand jetzt mit einem
`ReentrantLock` statt mit `synchronized`.

| Stelle | Vorher | Nachher |
|--------|--------|---------|
| `pom.xml` | `maven.compiler.release` 8 | 21 |
| `BestellImport` | `Executors.newFixedThreadPool(8)` | `Executors.newVirtualThreadPerTaskExecutor()` – nur diese Zeile |
| `RestAnwendung` | `Executors.newFixedThreadPool(10)` | `Executors.newVirtualThreadPerTaskExecutor()` – nur diese Zeile |
| `Verbindung` | `synchronized` um das Warten | `ReentrantLock` – nur unter Java 21 nötig, ab Java 24 pinnt `synchronized` nicht mehr (JEP 491) |

## Messen

Im Wurzelordner, je JDK ein Aufruf (JDK 25 optional; Run Configurations
`schwerpunkte-virtual-threads-batch-messen-jdk…` und `…-rest-messen-jdk…`):

```shell
mvn clean package -pl schwerpunkte/virtual-threads/bestellung-batch -am -Pjdk21,messen
```

```shell
mvn clean package -pl schwerpunkte/virtual-threads/bestellung-rest -am -Pjdk21,messen
```

## Messwerte

200 Bestellungen, je zweiter Lauf, 12 Kerne, macOS. JDK 8: Zulu 8.0.512, JDK 21:
OpenJDK 21.0.7 (Homebrew), JDK 25: OpenJDK 25.0.2.

| JDK | Stand | Batch | REST |
|-----|-------|------:|-----:|
| 8 | fester Pool | 1358 ms | 2186 ms |
| 21 | fester Pool | 1354 ms | 2212 ms |
| 21 | virtuelle Threads | 923 ms | 1889 ms |
| 21 | … mit `ReentrantLock` | 66 ms | 339 ms |
| 25 | fester Pool | 1358 ms | 2219 ms |
| 25 | virtuelle Threads | 67 ms | 332 ms |
| 25 | … mit `ReentrantLock` | 66 ms | 345 ms |

Der Lasttest schickt höchstens 100 Bestellungen gleichzeitig. Mit virtuellen Threads
dauern 200 Bestellungen deshalb mindestens zwei Runden zu 100 ms.

Unter Java 21 bringen virtuelle Threads wenig: Jeder wartet in `Verbindung.sende`
innerhalb von `synchronized` und bleibt an seinen Trägerthread gepinnt. Es laufen dann
nur so viele gleichzeitig, wie es Kerne gibt – im Batch wie in der REST-Schnittstelle,
denn beide fragen über `Verbindung` an. Mit einem `ReentrantLock` gibt der virtuelle
Thread seinen Träger beim Warten frei. Ab Java 24 pinnt auch `synchronized` nicht mehr
(JEP 491) – auf JDK 25 ändert der Umbau deshalb nichts.

## Kür: Anfragen an die Auskunftei begrenzen

Virtuelle Threads begrenzen nichts mehr. Darf die Auskunftei höchstens 20 Anfragen
gleichzeitig bekommen, gehört die Grenze an den Aufruf, nicht in einen kleinen Pool:

```java
private final Semaphore auskunft = new Semaphore(20);

private boolean prüfe(String kundennummer) throws InterruptedException {
    auskunft.acquire();
    try {
        return bonitätsprüfung.istKreditwürdig(kundennummer);
    } finally {
        auskunft.release();
    }
}
```

Mit 200 Bestellungen und 50 ms Latenz dauert der Import dann rechnerisch 500 ms – zehn
Runden zu je 20.

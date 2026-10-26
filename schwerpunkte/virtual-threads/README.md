# Musterlösung: Virtual Threads

Die Bestellabwicklung auf **Release 21**: Batch-Import und REST-Schnittstelle laufen auf
virtuellen Threads, `Verbindung` schützt ihren Zustand mit einem `ReentrantLock` statt
mit `synchronized`.

| Stelle | Vorher | Nachher |
|--------|--------|---------|
| `BestellImport` | `Executors.newFixedThreadPool(8)` | `Executors.newVirtualThreadPerTaskExecutor()` – nur diese Zeile |
| `RestAnwendung` | `Executors.newFixedThreadPool(10)` | `Executors.newVirtualThreadPerTaskExecutor()` – nur diese Zeile |
| `Verbindung` | `synchronized` um das Warten | `ReentrantLock` – nur unter Java 21 nötig, ab Java 24 pinnt `synchronized` nicht mehr (JEP 491) |

## Messwerte

200 Bestellungen, je zweiter Lauf, 12 Kerne. JDK 21: Temurin 21.0.12.1 in Docker
(Linux), JDK 25: Temurin 25.0.2 unter macOS.

| JDK | Stand | Batch | REST |
|-----|-------|------:|-----:|
| 21 | fester Pool | 1345 ms | 2195 ms |
| 21 | virtuelle Threads | 891 ms | 1920 ms |
| 21 | … mit `ReentrantLock` | 62 ms | 346 ms |
| 25 | fester Pool | 1356 ms | 2157 ms |
| 25 | virtuelle Threads | 60 ms | 309 ms |
| 25 | … mit `ReentrantLock` | 59 ms | 311 ms |

Der Lasttest schickt höchstens 100 Bestellungen gleichzeitig. Mit virtuellen Threads
dauern 200 Bestellungen deshalb mindestens zwei Runden zu 100 ms.

Unter Java 21 wartet jeder virtuelle Thread in `Verbindung.sende` innerhalb von
`synchronized` und bleibt an seinen Trägerthread gepinnt. Es laufen dann nur so viele
gleichzeitig, wie es Kerne gibt – im Batch wie in der REST-Schnittstelle, denn beide
fragen über `Verbindung` an.

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

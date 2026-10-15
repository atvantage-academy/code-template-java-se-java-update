package vorlagen;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Setzt eine Vorlage zu einem Text zusammen – wie {@code STR} aus Java 21:
 * Fragment, Wert, Fragment, Wert, … Fragment.
 */
public class TextProzessor implements Vorlagenprozessor<String> {

    @Override
    public String verarbeite(Vorlage vorlage) {
        var fragmente = vorlage.fragmente();
        var werte = vorlage.werte();
        // Das erste Fragment steht vorn, danach folgt auf jeden Wert sein Fragment.
        return IntStream.range(0, werte.size())
                .mapToObj(i -> werte.get(i) + fragmente.get(i + 1))
                .collect(Collectors.joining("", fragmente.getFirst(), ""));
    }
}

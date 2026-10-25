package kompatibilitaet.bezeichner;

import java.util.ArrayList;
import java.util.List;

/**
 * Sammelt Einträge. Variablen heißen hier {@code var}, {@code record} und {@code yield} –
 * ob das nach einem Release-Sprung noch übersetzt?
 */
public class Protokoll {

    private final List<String> einträge = new ArrayList<String>();

    public void notiere(String record) {
        String var = record.trim();
        int yield = einträge.size() + 1;
        einträge.add(yield + ": " + var);
    }

    public List<String> getEinträge() {
        return einträge;
    }
}

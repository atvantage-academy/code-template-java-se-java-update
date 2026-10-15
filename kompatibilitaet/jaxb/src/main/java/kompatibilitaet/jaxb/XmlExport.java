package kompatibilitaet.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import jakarta.xml.bind.JAXB;

/**
 * Schreibt und liest Bestellungen als XML – mit Jakarta XML Binding, das bis Java 10 als JAXB im JDK steckte.
 */
public final class XmlExport {

    private XmlExport() {
    }

    public static String alsXml(BestellungXml bestellung) {
        StringWriter xml = new StringWriter();
        JAXB.marshal(bestellung, xml);
        return xml.toString();
    }

    public static BestellungXml ausXml(String xml) {
        return JAXB.unmarshal(new StringReader(xml), BestellungXml.class);
    }
}

package kompatibilitaet.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

/**
 * Schreibt und liest Bestellungen als XML – mit JAXB, das bis Java 8 im JDK steckte.
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

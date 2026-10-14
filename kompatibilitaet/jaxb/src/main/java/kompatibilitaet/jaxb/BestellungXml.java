package kompatibilitaet.jaxb;

import java.math.BigDecimal;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Eine Bestellung, wie sie an das Warenwirtschaftssystem geht.
 */
@XmlRootElement(name = "bestellung")
public class BestellungXml {

    @XmlAttribute
    public String id;

    @XmlElement
    public String kundennummer;

    @XmlElement
    public BigDecimal betrag;

    public BestellungXml() {
    }

    public BestellungXml(String id, String kundennummer, BigDecimal betrag) {
        this.id = id;
        this.kundennummer = kundennummer;
        this.betrag = betrag;
    }
}

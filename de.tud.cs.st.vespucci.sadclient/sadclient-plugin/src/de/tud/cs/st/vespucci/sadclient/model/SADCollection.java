package de.tud.cs.st.vespucci.sadclient.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "descriptions")
@XmlSeeAlso(SAD.class)
public class SADCollection {
   
    private List<SAD> sads;

    @XmlElement(name = "description")
    public List<SAD> getSads() {
        return sads;
    }

    public void setSads(List<SAD> sads) {
        this.sads = sads;
    }

}

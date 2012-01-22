/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * Parses XML-responses from the SADServer.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class XMLParser {

    private final static String DESCRIPTIONS = "descriptions";
    private final static String DESCRIPTION = "description";
    private final static String NAME = "name";
    private final static String TYPE = "type";
    private final static String ABSTRACT = "abstract";
    private final static String MODEL = "model";
    private final static String DOCUMENTATION = "documentation";
    private final static String SIZE = "size";
    private final static String URL = "url";

    private XMLInputFactory inputFactory;

    public XMLParser() {
	inputFactory = XMLInputFactory.newInstance();
    }

    public SAD[] parseSADCollection(InputStream inputStream) throws XMLStreamException {

	List<SAD> result = new ArrayList<SAD>();
	XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);
	String current = null;
	SAD model = null;
	SAD.Model m = null;
	SAD.Documentation documentation = null;
	while (eventReader.hasNext()) {
	    XMLEvent event = eventReader.nextEvent();
	    if (event.isStartElement()) {
		StartElement startElement = event.asStartElement();
		String localPart = startElement.getName().getLocalPart();
		if (localPart.equals(DESCRIPTIONS)) {
		    continue;
		} else if (localPart.equals(DESCRIPTION)) {
		    model = new SAD();
		    current = DESCRIPTION;
		    continue;
		} else if (localPart.equals(NAME)) {
		    model.setName(eventReader.nextEvent().asCharacters().getData());
		    continue;
		} else if (localPart.equals(TYPE)) {
		    model.setType(eventReader.nextEvent().asCharacters().getData());
		    continue;
		} else if (localPart.equals(ABSTRACT)) {
		    model.setAbstrct(eventReader.nextEvent().asCharacters().getData());
		    continue;
		} else if (localPart.equals(MODEL)) {
		    m = new SAD.Model();
		    Attribute sizeAttribute = startElement.getAttributeByName(new QName(SIZE));
		    m.setSize(Integer.parseInt(sizeAttribute.getValue()));
		    model.setModel(m);
		    current = MODEL;
		    continue;
		} else if (localPart.equals(DOCUMENTATION)) {
		    documentation = new SAD.Documentation();
		    Attribute sizeAttribute = startElement.getAttributeByName(new QName(SIZE));
		    documentation.setSize(Integer.parseInt(sizeAttribute.getValue()));
		    model.setDocumentation(documentation);
		    current = DOCUMENTATION;
		    continue;
		} else if (localPart.equals(URL)) {
		    if (current.equals(MODEL)) {
			m.setUrl(eventReader.nextEvent().asCharacters().getData());
			continue;
		    } else if (current.equals(DOCUMENTATION)) {
			documentation.setUrl(eventReader.nextEvent().asCharacters().getData());
			continue;
		    }
		}
	    } else if (event.isEndElement()) {
		EndElement endElement = event.asEndElement();
		String localPart = endElement.getName().getLocalPart();
		if (localPart.equals(DESCRIPTION)) {
		    result.add(model);
		    System.out.println(model);
		    continue;
		}
	    }
	}
	return result.toArray(new SAD[0]);
    }
}

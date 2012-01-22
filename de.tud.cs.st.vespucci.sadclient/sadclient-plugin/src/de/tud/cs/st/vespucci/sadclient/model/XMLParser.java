/*
   Copyright 2011 Michael Eichberg et al

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
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

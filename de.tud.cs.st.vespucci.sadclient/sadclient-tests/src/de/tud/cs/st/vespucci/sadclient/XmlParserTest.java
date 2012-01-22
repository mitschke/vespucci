package de.tud.cs.st.vespucci.sadclient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;

import de.tud.cs.st.vespucci.sadclient.model.SAD;
import de.tud.cs.st.vespucci.sadclient.model.XMLParser;

public class XmlParserTest {

    @Test
    public void someTest() throws FileNotFoundException, XMLStreamException {

	XMLParser parser = new XMLParser();
	InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(
		"resources/descriptionCollection.xml")));
	
	SAD[] actual = parser.parseSADCollection(inputStream);
	
	SAD[] expected = new SAD[2];
	SAD model = new SAD();
	model.setName("myName");
	model.setType("myType");
	model.setAbstrct("This is the abstract.");
	model.setModel(null);
	SAD.Model m = new SAD.Model();
	m.setSize(173);
	m.setUrl("http://localhost:9000/vespucci/sads/e343c82f-9f3c-4e48-bdc3-faf3a3a99b34/model");
	model.setDocumentation(null);
    }
}

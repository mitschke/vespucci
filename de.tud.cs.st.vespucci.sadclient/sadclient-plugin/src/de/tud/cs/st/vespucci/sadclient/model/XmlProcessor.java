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

import static org.apache.commons.io.IOUtils.toInputStream;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author Mateusz Parzonka
 */
public class XmlProcessor {

    private Unmarshaller transactionUnmarshaller;
    private Marshaller sadMarshaller;
    private Marshaller transactionMarshaller;
    private Unmarshaller sadUnmarshaller;
    private Unmarshaller sadCollectionUnmarshaller;

    public XmlProcessor() {
	JAXBContext context;
	try {
	    context = JAXBContext.newInstance(SAD.class);
	    sadMarshaller = context.createMarshaller();
	    sadUnmarshaller = context.createUnmarshaller();
	    sadMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    context = JAXBContext.newInstance(SADCollection.class);
	    sadCollectionUnmarshaller = context.createUnmarshaller();

	    context = JAXBContext.newInstance(Transaction.class);
	    transactionMarshaller = context.createMarshaller();
	    transactionUnmarshaller = context.createUnmarshaller();
	    transactionMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	} catch (JAXBException e) {
	    e.printStackTrace();
	    throw new RuntimeException(e);
	}
    }

    public Transaction getTransaction(String xml) {
	try {
	    return (Transaction) transactionUnmarshaller.unmarshal(toInputStream(xml));
	} catch (JAXBException e) {
	    return null;
	}
    }

    public Transaction getTransaction(InputStream inputStream) {
	try {
	    return (Transaction) transactionUnmarshaller.unmarshal(inputStream);
	} catch (JAXBException e) {
	    return null;
	}
    }

    public SAD getSAD(String xml) {
	try {
	    return (SAD) sadUnmarshaller.unmarshal(toInputStream(xml));
	} catch (JAXBException e) {
	    return null;
	}
    }

    public SAD getSAD(InputStream inputStream) throws JAXBException {
	    return (SAD) sadUnmarshaller.unmarshal(inputStream);
    }

    public String getXML(SAD sad) {
	StringWriter sw = new StringWriter();
	try {
	    sadMarshaller.marshal(sad, sw);
	    return sw.toString();
	} catch (JAXBException e) {
	    return null;
	}
    }

    public String getXML(Transaction transaction) {
	StringWriter sw = new StringWriter();
	try {
	    transactionMarshaller.marshal(transaction, sw);
	    return sw.toString();
	} catch (JAXBException e) {
	    return null;
	}
    }

    public SAD[] getSADCollection(InputStream inputStream) throws JAXBException {
	    return ((SADCollection) sadCollectionUnmarshaller.unmarshal(inputStream)).getSads().toArray(new SAD[0]);
    }

}

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

import static org.apache.commons.io.IOUtils.toInputStream;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Responsible for (un)marshalling {@link SAD}s, {@link SADCollection}s and
 * {@link Transaction}s.
 * 
 * @author Mateusz Parzonka
 */
public class XmlProcessor {

    private final Marshaller transactionMarshaller;
    private final Marshaller sadMarshaller;
    private final Unmarshaller transactionUnmarshaller;
    private final Unmarshaller sadUnmarshaller;
    private final Unmarshaller sadCollectionUnmarshaller;

    public XmlProcessor() {
	JAXBContext context;
	try {
	    context = JAXBContext.newInstance(SAD.class, SADCollection.class, Transaction.class);
	    sadMarshaller = context.createMarshaller();
	    sadUnmarshaller = context.createUnmarshaller();
	    sadMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    sadCollectionUnmarshaller = context.createUnmarshaller();
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
	List<SAD> listOfSADs = ((SADCollection) sadCollectionUnmarshaller.unmarshal(inputStream)).getSads();
	// when the xml does not contain any SAD, the list is null
	return listOfSADs == null ? new SAD[0] : listOfSADs.toArray(new SAD[0]);
    }

}

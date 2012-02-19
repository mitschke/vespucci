package de.tud.cs.st.vespucci.sadclient.model;

import static org.apache.commons.io.IOUtils.toInputStream;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxBProcessor {

    private Unmarshaller transactionUnmarshaller;
    private Marshaller sadMarshaller;
    private Marshaller transactionMarshaller;
    private Unmarshaller sadUnmarshaller;

    public JaxBProcessor() {
	JAXBContext context;
	try {
	    context = JAXBContext.newInstance(SAD.class);
	    sadMarshaller = context.createMarshaller();
	    sadUnmarshaller = context.createUnmarshaller();
	    sadMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
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
    
    public static void main(String[] args) {
	JaxBProcessor p = new JaxBProcessor();
    }

}

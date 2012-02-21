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

import java.io.File;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import de.tud.cs.st.vespucci.sadclient.Activator;
import de.tud.cs.st.vespucci.sadclient.model.http.MultiThreadedHttpClient;
import de.tud.cs.st.vespucci.sadclient.model.http.RequestException;
import de.tud.cs.st.vespucci.sadclient.preferences.PreferenceConstants;

/**
 * Interacts with the SADServer via a RESTful interface.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class SADClient {

    private MultiThreadedHttpClient client;
    private volatile static String root = null;
    private final static String TRANSACTION = "/transaction";
    private final static String COLLECTION = "/sads";
    private final static String MODEL = "model";
    private final static String DOCUMENTATION = "documentation";
    private final static String XML = "application/xml";
    private final static String PDF = "application/pdf";

    private final XmlProcessor xmlProcessor;

    // public List<SADModel> getDescriptionCollection() {

    public SADClient() {
	super();
	xmlProcessor = new XmlProcessor();
	root = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVER);
	client = new MultiThreadedHttpClient(getUsername(), getPassword());
	registerPreferenceListeners();
    }

    // SAD-collection //

    public SAD[] getSADCollection() throws RequestException {
	SAD[] result = null;
	HttpResponse response = client.get(SADUrl());
	try {
	    result = xmlProcessor.getSADCollection(response.getEntity().getContent());
	    client.consume(response);
	} catch (Exception e) {
	    throw new RequestException(e);
	}
	return result;
    }

    // SAD //

    public SAD getSAD(String id) throws RequestException {
	SAD result = null;
	HttpResponse response = client.get(SADUrl(id));
	try {
	    result = xmlProcessor.getSAD(response.getEntity().getContent());
	    client.consume(response);
	} catch (Exception e) {
	    throw new RequestException(e);
	}
	return result;
    }

    public Transaction startTransaction(String id) throws Exception {
	Transaction transaction = new Transaction("SAD", null, id, null);
	HttpResponse response = client.post(SADTransactionUrl(), xmlProcessor.getXML(transaction), XML);
	transaction = xmlProcessor.getTransaction(response.getEntity().getContent());
	client.consume(response);
	return transaction;
    }

    public void rollbackTransaction(String transactionId) {
	HttpResponse response = null;
	try {
	    response = client.delete(SADTransactionUrl(transactionId));
	    client.consume(response);
	} catch (RequestException e) {
	    // do nothing
	}
    }

    public void commitTransaction(String transactionid) throws RequestException {
	Transaction transaction = new Transaction();
	transaction.setTransactionUrl(SADTransactionUrl(transactionid));
	client.post(transaction.getTransactionUrl(), xmlProcessor.getXML(transaction), XML);
    }

    public void storeSAD(String transactionId, SAD sad) throws RequestException {
	HttpResponse response = client.put(SADTransactionUrl(transactionId), xmlProcessor.getXML(sad), XML);
	client.consume(response);
    }

    // Model //

    public byte[] getModel(String id, File downloadLocation, IProgressMonitor progressMonitor) throws RequestException {
	HttpResponse response = client.get(ModelUrl(id), XML, progressMonitor);
	byte[] bytes;
	try {
	    bytes = IOUtils.toByteArray(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	client.consume(response);
	return bytes;
    }

    public void putModel(String transactionId, File file, IProgressMonitor progressMonitor) throws RequestException {
	HttpResponse response = client.putAsMultipart(SADTransactionUrl(transactionId) + "/" + MODEL, "model", file,
		XML, progressMonitor);
	client.consume(response);
    }

    public void deleteSAD(String id) throws RequestException {
	HttpResponse response = client.delete(SADUrl(id));
	client.consume(response);
    }

    public void deleteModel(String transactionId) throws RequestException {
	HttpResponse response = client.delete(SADTransactionUrl(transactionId) + "/" + MODEL);
	client.consume(response);
    }

    public void deleteDocumentation(String transactionId) throws RequestException {
	HttpResponse response = client.delete(SADTransactionUrl(transactionId) + "/" + DOCUMENTATION);
	client.consume(response);
    }

    // Documentation //

    public byte[] getDocumentation(String id, File downloadLocation, IProgressMonitor progressMonitor) throws Exception {
	HttpResponse response = client.get(DocumentationUrl(id), PDF, progressMonitor);
	byte[] bytes = IOUtils.toByteArray(response.getEntity().getContent());
	client.consume(response);
	return bytes;
    }

    public void putDocumentation(String transactionId, File file, IProgressMonitor progressMonitor)
	    throws RequestException {
	HttpResponse response = client.putAsMultipart(SADTransactionUrl(transactionId) + "/" + DOCUMENTATION,
		"documentation", file, PDF, progressMonitor);
	client.consume(response);
    }

    // URLs //

    private static String SADUrl() {
	return root + COLLECTION;
    }

    private static String SADUrl(String id) {
	return root + COLLECTION + "/" + id;
    }

    private static String SADTransactionUrl() {
	return root + TRANSACTION + COLLECTION;
    }

    private static String SADTransactionUrl(String transactionId) {
	return root + TRANSACTION + COLLECTION + "/" + transactionId;
    }

    private static String ModelUrl(String id) {
	return root + COLLECTION + "/" + id + "/" + MODEL;
    }

    private static String DocumentationUrl(String id) {
	return root + COLLECTION + "/" + id + "/" + DOCUMENTATION;
    }

    // Helper //

    /**
     * Registers listeners for changes of credentials and server.
     */
    private void registerPreferenceListeners() {
	Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
	    @Override
	    public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty() == PreferenceConstants.P_USERNAME
			|| event.getProperty() == PreferenceConstants.P_PASSWORD) {
		    client.shutdown();
		    client = new MultiThreadedHttpClient(getUsername(), getPassword());
		}
	    }
	});
	Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
	    @Override
	    public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty() == PreferenceConstants.P_SERVER) {
		    root = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVER);
		}
	    }
	});
    }

    private static String getUsername() {
	return Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME);
    }

    private static String getPassword() {
	return Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD);
    }

    /**
     * Initiated by the plugin activator.
     */
    public void shutdown() {
	client.shutdown();
    }

}

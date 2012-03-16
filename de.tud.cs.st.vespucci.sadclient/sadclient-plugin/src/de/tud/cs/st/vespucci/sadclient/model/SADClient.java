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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
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

    public SADClient() {
	super();
	xmlProcessor = new XmlProcessor();
	root = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_SERVER);
	client = new MultiThreadedHttpClient();
	client.setCredentials(getUsername(), getPassword());
	registerPreferenceListeners();
    }

    // SAD-collection //

    public SAD[] getSADCollection() throws RequestException {
	SAD[] result = null;
	HttpResponse response = client.get(SADUrl());
	try {
	    result = xmlProcessor.getSADCollection(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new RequestException(e);
	} finally {
	    client.consume(response);
	}
	return result;
    }

    // SAD //

    public SAD getSAD(String id) throws RequestException {
	SAD result = null;
	HttpResponse response = client.get(SADUrl(id));
	try {
	    result = xmlProcessor.getSAD(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new RequestException(e);
	} finally {
	    client.consume(response);
	}
	return result;
    }

    public Transaction startTransaction(String id) throws RequestException {
	Transaction transaction = new Transaction("SAD", null, id, null);
	HttpResponse response = client.post(SADTransactionUrl(), xmlProcessor.getXML(transaction), XML);
	try {
	    transaction = xmlProcessor.getTransaction(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new RequestException(e);
	} finally {
	    client.consume(response);
	}
	return transaction;
    }

    public void rollbackTransaction(String transactionId) {
	HttpResponse response = null;
	try {
	    response = client.delete(SADTransactionUrl(transactionId));
	} catch (RequestException e) {
	    // do nothing
	} finally {
	    client.consume(response);
	}
    }

    public void commitTransaction(String transactionid) throws RequestException {
	Transaction transaction = new Transaction();
	transaction.setTransactionUrl(SADTransactionUrl(transactionid));
	consume(client.post(transaction.getTransactionUrl(), xmlProcessor.getXML(transaction), XML));
    }

    public void storeSAD(String transactionId, SAD sad) throws RequestException {
	HttpResponse response = client.put(SADTransactionUrl(transactionId), xmlProcessor.getXML(sad), XML);
	client.consume(response);
    }

    // Model //

    public byte[] getModel(String id, SubMonitor monitor) throws RequestException {
	return getAttachment(id, "model", ModelUrl(id), XML, monitor);
    }

    private byte[] getAttachment(String id, String type, String url, String acceptedContentType, SubMonitor monitor)
	    throws RequestException {
	monitor.subTask(String.format("Downloading %s %s...", type, id));
	HttpResponse response = client.get(url, acceptedContentType, monitor);
	final int contentLength = safeLongToInt(response.getEntity().getContentLength());
	byte[] bytes;
	try {
	    bytes = getBytesWithProgress(response.getEntity().getContent(), contentLength, monitor);
	} catch (IOException e) {
	    throw new RequestException(e);
	} finally {
	    client.consume(response);
	}
	return bytes;
    }

    public void putModel(String transactionId, File file, IProgressMonitor progressMonitor) throws RequestException {
	consume(client.putAsMultipart(SADTransactionUrl(transactionId) + "/" + MODEL, MODEL, file, XML,
		progressMonitor));
    }

    public void deleteSAD(String id) throws RequestException {
	consume(client.delete(SADUrl(id)));
    }

    public void deleteModel(String transactionId) throws RequestException {
	consume(client.delete(SADTransactionUrl(transactionId) + "/" + MODEL));
    }

    public void deleteDocumentation(String transactionId) throws RequestException {
	consume(client.delete(SADTransactionUrl(transactionId) + "/" + DOCUMENTATION));
    }

    // Documentation //

    public byte[] getDocumentation(String id, SubMonitor monitor) throws RequestException {
	return getAttachment(id, DOCUMENTATION, DocumentationUrl(id), PDF, monitor);
    }

    public void putDocumentation(String transactionId, File file, IProgressMonitor progressMonitor)
	    throws RequestException {
	consume(client.putAsMultipart(SADTransactionUrl(transactionId) + "/" + DOCUMENTATION, DOCUMENTATION, file,
		PDF, progressMonitor));
    }

    private void consume(HttpResponse response) {
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
     * Transforms the input stream to a byte array while notifying the provided
     * monitor. The workload is partitioned into 100 work units (think percent)
     * which must be respected by clients which provide the monitor.
     * 
     * @param input
     * @param inputLength
     * @param monitor
     * @return
     * @throws IOException
     */
    public byte[] getBytesWithProgress(InputStream input, int inputLength, SubMonitor monitor) throws IOException {
	final ByteArrayOutputStream output = new ByteArrayOutputStream();
	final int bufferSize = 4096;
	// when the inputLength is smaller than the buffer size we are finished
	// immediately
	final int workSize = inputLength < bufferSize ? bufferSize : inputLength / 100;
	final byte[] buffer = new byte[bufferSize];
	int currentWorkedBytes = 0;
	int read = 0;
	while (-1 != (read = input.read(buffer))) {
	    output.write(buffer, 0, read);
	    currentWorkedBytes += read;
	    int worked = currentWorkedBytes / workSize;
	    if (worked > 0) {
		if (monitor.isCanceled())
		    throw new OperationCanceledException();
		monitor.worked(worked);
		currentWorkedBytes -= worked * workSize;
	    }
	}
	return output.toByteArray();
    }

    /**
     * Registers listeners for changes of credentials and server.
     */
    private void registerPreferenceListeners() {
	Activator.getDefault().getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
	    @Override
	    public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty() == PreferenceConstants.P_USERNAME
			|| event.getProperty() == PreferenceConstants.P_PASSWORD) {
		    client.setCredentials(getUsername(), getPassword());
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

    private static int safeLongToInt(long l) {
	if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	    throw new IllegalArgumentException(l + " cannot be safely cast to int.");
	}
	return (int) l;
    }

    /**
     * Initiated by the plugin activator.
     */
    public void shutdown() {
	client.shutdown();
    }

}

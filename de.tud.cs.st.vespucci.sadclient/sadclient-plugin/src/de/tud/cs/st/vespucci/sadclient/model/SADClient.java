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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.eclipse.core.runtime.IProgressMonitor;
import org.apache.commons.io.IOUtils;

import de.tud.cs.st.vespucci.sadclient.model.http.MultiThreadedHttpClient;

/**
 * Retrieves SADs from the server via REST.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class SADClient {

    private MultiThreadedHttpClient client;
    private final static String COLLECTION = "sads";
    private final static String MODEL = "model";
    private final static String DOCUMENTATION = "documentation";
    private final static String XML = "application/xml";
    private final static String PDF = "application/pdf";

    // public List<SADModel> getDescriptionCollection() {

    public SADClient() {
	super();
	client = new MultiThreadedHttpClient("http://localhost:9000/vespucci/", "somebody", "password");
    }

    // SAD-collection //

    public SAD[] getSADCollection() throws SADClientException {
	System.out.println("Trying to get SADs");
	SAD[] result = null;
	HttpResponse response = client.get(COLLECTION);
	try {
	    result = new XMLProcessor().parseSADCollection(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new SADClientException(e);
	}
	client.consume(response);
	return result;
    }

    // SAD //

    public SAD getSAD(String id) {
	System.out.println("Calling getSAD in client with id " + id);
	SAD result = null;
	HttpResponse response = client.get(SADUrl(id));
	try {
	    result = new XMLProcessor().parseSAD(response.getEntity().getContent());
	} catch (Exception e) {
	    throw new SADClientException(e);
	}
	client.consume(response);
	return result;
    }

    public void putSAD(String id, String name, String type, String abstrct) throws SADClientException {
	System.out.println("Calling putSAD in client with id " + id);
	String xml = new XMLProcessor().getXMLDescription(id, name, type, abstrct, false);
	HttpResponse response = client.put(SADUrl(id), xml, XML);
	client.consume(response);
    }

    public void deleteSAD(String id) throws SADClientException {
	System.out.println("Calling deleteSAD in client with id " + id);
	HttpResponse response = client.delete(SADUrl(id));
	client.consume(response);
    }

    // Model //

    public void getModel(String id, File downloadLocation, IProgressMonitor progressMonitor) {
	System.out.println("Calling getModel in client with id " + id);
	HttpResponse response = client.get(ModelUrl(id), XML, progressMonitor);
	writeContents(response, downloadLocation);
	client.consume(response);
    }

    public void putModel(String id, File file, IProgressMonitor progressMonitor) {
	System.out.println("Calling putModel in client with id " + id);
	HttpResponse response = client.put(ModelUrl(id), file, XML, progressMonitor);
	client.consume(response);
    }

    public void deleteModel(String id) {
	System.out.println("Calling deleteModel in client with id " + id);
	HttpResponse response = client.delete(ModelUrl(id));
	client.consume(response);
    }

    // Documentation //

    public void getDocumentation(String id, File downloadLocation, IProgressMonitor progressMonitor) {
	System.out.println("Calling getDocumentation in client with id " + id);
	HttpResponse response = client.get(DocumentationUrl(id), PDF, progressMonitor);
	writeContents(response, downloadLocation);
	client.consume(response);
    }

    public void putDocumentation(String id, File file, IProgressMonitor progressMonitor) {
	System.out.println("Calling putDocumentation in client with id " + id);
	HttpResponse response = client.put(DocumentationUrl(id), file, PDF, progressMonitor);
	client.consume(response);
    }

    public void deleteDocumentation(String id) {
	System.out.println("Calling deleteDocumentation in client with id " + id);
	HttpResponse response = client.delete(DocumentationUrl(id));
	client.consume(response);
    }

    // URLs //

    private static String SADUrl(String id) {
	return COLLECTION + "/" + id;
    }

    private static String ModelUrl(String id) {
	return COLLECTION + "/" + id + "/" + MODEL;
    }

    private static String DocumentationUrl(String id) {
	return COLLECTION + "/" + id + "/" + DOCUMENTATION;
    }

    // Helper //
    
    private static void writeContents(HttpResponse response, File file) {
	try {
	    IOUtils.copy(response.getEntity().getContent(), new FileOutputStream(file));
	} catch (Exception e) {
	    throw new SADClientException(e);
	}
    }

}

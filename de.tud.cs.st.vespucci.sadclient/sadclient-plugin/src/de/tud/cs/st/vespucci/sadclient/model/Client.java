package de.tud.cs.st.vespucci.sadclient.model;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;

import de.tud.cs.st.vespucci.sadclient.controller.SADClientException;

/**
 * Retrieves SADs from the server via REST.
 * 
 * @author mateusz
 * 
 */
public class Client {

    private final HttpClient client;
    private final XMLParser xmlParser;

    // public List<SADModel> getDescriptionCollection() {

    public Client() {
	super();
	ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
	client = new DefaultHttpClient(cm);
	xmlParser = new XMLParser();
    }

    public SAD[] getSADCollection() throws SADClientException {
	System.out.println("Trying to get SADs");
	SAD[] result;
	try {
	    final HttpGet get = new HttpGet("http://localhost:9000/vespucci/sads");
	    get.addHeader("accept", "application/xml");
	    final HttpResponse response = client.execute(get);
	    final HttpEntity entity = response.getEntity();
	    System.out.println("entity:" + entity.getContentLength());
	    result = xmlParser.parseSADCollection(entity.getContent());
	    EntityUtils.consume(entity);
	    return result;
	} catch (Exception e) {
	    throw new SADClientException(e);
	}
    }
    
}

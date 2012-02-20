package de.tud.cs.st.vespucci.sadclient.model.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.runtime.IProgressMonitor;

import de.tud.cs.st.vespucci.sadclient.Activator;


/**
 * Thread-safe wrapper around the {@link DefaultHttpClient} using a
 * {@link ThreadSafeClientConnManager} and enables progress. The HTTP-methods
 * expect successful termination of the client call and will throw an
 * RequestException when the server responds non-okay.
 * 
 * TODO  The client is not multithreaded anymore so rename the whole thing when a valid solution found
 * 
 * @author Mateusz Parzonka
 * 
 */
public class MultiThreadedHttpClient {

    private final DefaultHttpClient client;
    private final static String CHARSET = "UTF-8";

    /**
     * The client will use no authentication.
     */
    public MultiThreadedHttpClient() {
	super();
	SchemeRegistry schemeRegistry = new SchemeRegistry();
	schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	// standard client params
	HttpParams params = new BasicHttpParams();
	params.setParameter(CoreProtocolPNames.HTTP_ELEMENT_CHARSET, CHARSET);
	params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, CHARSET);
	params.setParameter(CoreProtocolPNames.USER_AGENT, Activator.PLUGIN_ID);
	// The SADServer's underlying server implementation (sun) does not allow us to use this handshake,
	// since it always returns "continue"
	params.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, true);
	List<Header> defaultHeaders = new ArrayList<Header>();
	defaultHeaders.add(new BasicHeader("accept", "application/xml"));
	params.setParameter(ClientPNames.DEFAULT_HEADERS, defaultHeaders);

	ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);
	cm.setMaxTotal(200);
	cm.setDefaultMaxPerRoute(20);

	client = new DefaultHttpClient(cm, params);
    }

    /**
     * The client will use Http Digest Authentication with the provided
     * credentials.
     * 
     * @param userName
     * @param password
     */
    public MultiThreadedHttpClient(String userName, String password) {
	this();
	CredentialsProvider credsProvider = new BasicCredentialsProvider();
	client.setCredentialsProvider(credsProvider);
	credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
	List<String> authPrefs = new ArrayList<String>(1);
	authPrefs.add(AuthPolicy.DIGEST);
	client.getParams().setParameter(AuthPNames.TARGET_AUTH_PREF, authPrefs);
    }

    public HttpResponse get(String url) {
	HttpResponse response = null;
	try {
	    final HttpGet get = new HttpGet(url);
	    response = client.execute(get);
	} catch (Exception e) {
	    throw new HttpClientException(e);
	}
	expectStatusCode(response, 200);
	return response;
    }

    public HttpResponse get(String url, String acceptedContentType) {
	HttpResponse response = null;
	try {
	    final HttpGet get = new HttpGet(url);
	    get.setHeader("accept", acceptedContentType);
	    response = client.execute(get);
	} catch (Exception e) {
	    throw new HttpClientException(e);
	}
	expectStatusCode(response, 200);
	return response;
    }

    public HttpResponse get(String url, String acceptedContentType, IProgressMonitor progressMonitor) {
	HttpResponse response = null;
	try {
	    final HttpGet get = new HttpGet(url);
	    get.setHeader("accept", acceptedContentType);
	    response = client.execute(get);
	    HttpEntityWithProgress.attachProgressMonitor(response, progressMonitor);
	} catch (Exception e) {
	    throw new HttpClientException(e);
	}
	expectStatusCode(response, 200);
	return response;
    }

    public HttpResponse put(String url, String string, String mimeType) {
	try {
	    return put(url, new StringEntity(string, mimeType, CHARSET));
	} catch (UnsupportedEncodingException e) {
	    throw new HttpClientException(e);
	}
    }

    public HttpResponse put(String url, File file, String mimeType) {
	return put(url, new FileEntity(file, mimeType));
    }

    public HttpResponse put(String url, File file, String mimeType, IProgressMonitor progressMonitor) {
	System.out.println("Putted file " + file + " has size " + file.length() + " bytes.");
	HttpEntity upstreamEntity = new FileEntityWithProgress(file, mimeType, progressMonitor);
	HttpResponse response = put(url, upstreamEntity);
	try {
	    EntityUtils.consume(upstreamEntity);
	} catch (IOException e) {
	    throw new HttpClientException(e);
	}
	// expectStatusCode(response, 200);
	return response;
    }
    
    public HttpResponse putAsMultipart(String url, String fieldName, File file, String mimeType, IProgressMonitor progressMonitor) {
   	MultipartEntity multipartEntity = new MultipartEntity();
   	ContentBody contentBody = new FileBody(file, mimeType, "UTF-8");
   	multipartEntity.addPart(fieldName, contentBody);
//   	HttpEntity upstreamEntity = new FileEntityWithProgress(file, mimeType, progressMonitor);
   	HttpEntity upstreamEntity = multipartEntity;
   	HttpResponse response = put(url, upstreamEntity);
   	try {
   	    EntityUtils.consume(upstreamEntity);
   	} catch (IOException e) {
   	    throw new HttpClientException(e);
   	}
   	// expectStatusCode(response, 200);
   	return response;
       }

    public HttpResponse put(String url, HttpEntity entity) {
	HttpResponse response = null;
	try {
	    // sending a short put to trigger authentication and storing httpcontext.
	    HttpPut put = new HttpPut(url);
//	    HttpContext localContext = new BasicHttpContext();
//	    HttpEntity smallEntity = new StringEntity("someBytes", "application/xml", "UTF-8");
//	    put.setEntity(smallEntity);
//	    response = client.execute(put, localContext);
//	    EntityUtils.consume(smallEntity);
//	    consume(response);
//	    put = new HttpPut(url);
	    put.setEntity(entity);
	    response = client.execute(put);
	    EntityUtils.consume(entity);
	} catch (IOException e) {
	    throw new HttpClientException(e);
	}
	System.out.println("StatusCode received: [" + response.getStatusLine().getStatusCode() + "]");
	 expectStatusCode(response, 200);
	return response;
    }

    public HttpResponse post(String url, String string, String mimeType) {
	try {
	    return post(url, new StringEntity(string, mimeType, CHARSET));
	} catch (UnsupportedEncodingException e) {
	    throw new HttpClientException(e);
	}
    }

    public HttpResponse post(String url, File file, String mimeType) {
	return post(url, new FileEntity(file, mimeType));
    }

    public HttpResponse post(String url, File file, String mimeType, IProgressMonitor progressMonitor) {
	HttpResponse response = post(url, new FileEntity(file, mimeType));
	HttpEntityWithProgress.attachProgressMonitor(response, progressMonitor);
	return response;
    }

    public HttpResponse post(String url, HttpEntity entity) {
	HttpResponse response = null;
	try {
	    final HttpPost post = new HttpPost(url);
	    post.setEntity(entity);
	    response = client.execute(post);
	    EntityUtils.consume(entity);
	} catch (Exception e) {
	    throw new HttpClientException(e);
	}
	expectStatusCode(response, 200, 201);
	return response;
    }

    public HttpResponse delete(String url) {
	HttpResponse response = null;
	try {
	    final HttpDelete delete = new HttpDelete(url);
	    response = client.execute(delete);
	} catch (Exception e) {
	    throw new HttpClientException(e);
	}
	expectStatusCode(response, 200, 204);
	return response;
    }

    /**
     * Releases the connection and has to be called by clients of this class
     * after calling any of the HTTP-methods.
     * 
     * @param httpResponse
     *            its entity will be consumed using {@link EntityUtils}
     */
    public void consume(HttpResponse httpResponse) {
	try {
	    EntityUtils.consume(httpResponse.getEntity());
	} catch (IOException e) {
	    throw new HttpClientException(e);
	}
    }

    private static void expectStatusCode(HttpResponse response, Integer... expectedStatusCodes) {
	int actualStatusCode = response.getStatusLine().getStatusCode();
	for (Integer expectedStatusCode : expectedStatusCodes) {
	    if (actualStatusCode == expectedStatusCode)
		return;
	}
	// we close the connection, and eat any IO exceptions
	try {
	    EntityUtils.consume(response.getEntity());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	throw new RequestException(actualStatusCode);
    }

}

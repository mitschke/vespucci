package de.tud.cs.st.vespucci.sadclient.model.http;

/**
 * Represents an unrecoverable exception in {@link MultiThreadedHttpClient}.
 * 
 * @author Mateusz Parzonka
 *
 */
public class HttpClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpClientException(Throwable arg0) {
	super(arg0);
    }

    
}

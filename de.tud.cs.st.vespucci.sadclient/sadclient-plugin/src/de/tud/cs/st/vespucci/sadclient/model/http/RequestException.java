package de.tud.cs.st.vespucci.sadclient.model.http;

public class RequestException extends RuntimeException {

    public RequestException(int statusCode) {
	super("Server responded unexpectedly with status code: " + statusCode);
    }

}

package de.tud.cs.st.vespucci.sadclient.model.http;

public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 5264958979783944130L;

    public RequestException(int statusCode) {
	super("Server responded unexpectedly with status code: " + statusCode);
    }

}

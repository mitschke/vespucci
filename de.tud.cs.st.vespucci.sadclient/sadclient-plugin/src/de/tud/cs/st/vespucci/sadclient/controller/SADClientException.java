package de.tud.cs.st.vespucci.sadclient.controller;

/**
 * @author Mateusz Parzonka
 */
public class SADClientException extends Exception {

    private static final long serialVersionUID = 1L;

    public SADClientException() {
	super();
    }

    public SADClientException(String message, Throwable cause) {
	super(message, cause);
    }

    public SADClientException(String message) {
	super(message);
    }

    public SADClientException(Throwable cause) {
	super(cause);
    }

}

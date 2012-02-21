package de.tud.cs.st.vespucci.sadclient.model.http;

public class RequestException extends Exception {

    private static final long serialVersionUID = 5264958979783944130L;

    public RequestException(int statusCode) {
   	super("Server responded with status code " + statusCode + ".");
       }
    
    public RequestException(int statusCode, String body) {
	super("Server responded with status code " + statusCode + ".\n" + body);
    }
    
    public RequestException(Throwable e) {
	super(e);
    }

}

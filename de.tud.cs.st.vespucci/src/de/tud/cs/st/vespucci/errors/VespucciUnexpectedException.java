package de.tud.cs.st.vespucci.errors;

public class VespucciUnexpectedException extends VespucciException {
	
	private static final String PREFIX = "VespucciUnexpectedException: ";

	public VespucciUnexpectedException(String message) {
		super(PREFIX + message);
		// TODO Auto-generated constructor stub
	}

	public VespucciUnexpectedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VespucciUnexpectedException(String message, Throwable cause) {
		super(PREFIX + message, cause);
		// TODO Auto-generated constructor stub
	}

}

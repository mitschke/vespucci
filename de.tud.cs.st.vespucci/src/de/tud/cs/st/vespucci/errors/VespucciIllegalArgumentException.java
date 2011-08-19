package de.tud.cs.st.vespucci.errors;

public class VespucciIllegalArgumentException extends VespucciException {

	private static final String PREFIX = "VespucciIllegalArgumentException: ";
	
	public VespucciIllegalArgumentException(String message) {
		super(PREFIX + message);
		// TODO Auto-generated constructor stub
	}

	public VespucciIllegalArgumentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VespucciIllegalArgumentException(String message, Throwable cause) {
		super(PREFIX + message, cause);
		// TODO Auto-generated constructor stub
	}

}

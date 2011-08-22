package de.tud.cs.st.vespucci.errors;

/**
 * Exception which is thrown if there occurs an error during the evalutation of an argument.
 * 
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 * 
 */
public class VespucciIllegalArgumentException extends VespucciException {

	private static final long serialVersionUID = 5961126748527017839L;
	
	private static final String PREFIX = "VespucciIllegalArgumentException: ";

	public VespucciIllegalArgumentException(final String message) {
		super(PREFIX + message);
		// TODO Auto-generated constructor stub
	}

	public VespucciIllegalArgumentException(final Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public VespucciIllegalArgumentException(final String message, final Throwable cause) {
		super(PREFIX + message, cause);
		// TODO Auto-generated constructor stub
	}

}

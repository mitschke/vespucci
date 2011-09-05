package de.tud.cs.st.vespucci.exceptions;

/**
 * Exception which is thrown if there occurs an error during the evaluation of an argument.
 * 
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 * 
 */
public class VespucciIllegalArgumentException extends VespucciException {
	
	private static final String MESSAGE_PREFIX = "VespucciIllegalArgumentException: ";

	public VespucciIllegalArgumentException(final String message) {
		super(MESSAGE_PREFIX + message);
	}

	public VespucciIllegalArgumentException(final Throwable cause) {
		super(cause);
	}

	public VespucciIllegalArgumentException(final String message, final Throwable cause) {
		super(MESSAGE_PREFIX + message, cause);
	}

}

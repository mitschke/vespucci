package de.tud.cs.st.vespucci.exceptions;

/**
 * Exception which is thrown when different behavior is expected.
 * 
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class VespucciUnexpectedException extends VespucciException {

	private static final String MESSAGE_PREFIX = "VespucciUnexpectedException: ";

	public VespucciUnexpectedException(final String message) {
		super(MESSAGE_PREFIX + message);
	}

	public VespucciUnexpectedException(final Throwable cause) {
		super(cause);
	}

	public VespucciUnexpectedException(final String message, final Throwable cause) {
		super(MESSAGE_PREFIX + message, cause);
	}

}

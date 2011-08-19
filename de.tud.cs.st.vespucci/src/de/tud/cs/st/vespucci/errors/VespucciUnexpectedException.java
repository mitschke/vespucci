package de.tud.cs.st.vespucci.errors;

/**
 * Exception which is thrown when different behavior is expected.
 * 
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class VespucciUnexpectedException extends VespucciException {

	private static final long serialVersionUID = 6380086684556093193L;

	private static final String PREFIX = "VespucciUnexpectedException: ";

	public VespucciUnexpectedException(final String message) {
		super(PREFIX + message);
	}

	public VespucciUnexpectedException(final Throwable cause) {
		super(cause);
	}

	public VespucciUnexpectedException(final String message, final Throwable cause) {
		super(PREFIX + message, cause);
	}

}

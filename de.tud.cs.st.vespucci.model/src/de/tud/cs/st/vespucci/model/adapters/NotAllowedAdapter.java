/**
 * 
 */
package de.tud.cs.st.vespucci.model.adapters;

import de.tud.cs.st.vespucci.model.INotAllowed;
import de.tud.cs.st.vespucci.vespucci_model.Connection;

/**
 * @author Robert Cibulla
 *
 */
public class NotAllowedAdapter extends ConstraintAdapter implements INotAllowed {

	/**
	 * @param adaptee
	 */
	public NotAllowedAdapter(Connection adaptee) {
		super(adaptee);
	}

}

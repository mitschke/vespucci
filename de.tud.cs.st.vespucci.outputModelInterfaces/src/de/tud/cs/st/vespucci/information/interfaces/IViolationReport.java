package de.tud.cs.st.vespucci.information.interfaces;

import java.util.LinkedList;

public interface IViolationReport {

	void addViolation(IViolation violation);
	
	boolean removeViolation(IViolation violation);
	
	LinkedList<IViolation> getViolations();
}

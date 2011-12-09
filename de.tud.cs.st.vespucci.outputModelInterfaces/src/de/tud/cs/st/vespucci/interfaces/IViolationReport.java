package de.tud.cs.st.vespucci.interfaces;

import java.util.Set;

public interface IViolationReport {

	void addViolation(IViolation violation);
	
	boolean removeViolation(IViolation violation);
	
	Set<IViolation> getViolations();
}

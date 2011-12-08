package de.tud.cs.st.vespucci.information.interfaces.spi;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.tud.cs.st.vespucci.information.interfaces.IViolation;
import de.tud.cs.st.vespucci.information.interfaces.IViolationReport;

public class ViolationReport implements IViolationReport {

	private Set<IViolation> violations = new HashSet<IViolation>();
	
	@Override
	public Set<IViolation> getViolations() {
		return violations;
	}

	@Override
	public void addViolation(IViolation violation) {
		violations.add(violation);
	}

	@Override
	public boolean removeViolation(IViolation violation) {
		return violations.remove(violation);
	}

}

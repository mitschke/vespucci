package de.tud.cs.st.vespucci.information.interfaces.spi;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.information.interfaces.IViolation;
import de.tud.cs.st.vespucci.information.interfaces.IViolationReport;

public class ViolationReport implements IViolationReport {

	private LinkedList<IViolation> violations = new LinkedList<IViolation>();
	
	@Override
	public LinkedList<IViolation> getViolations() {
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

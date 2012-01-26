package de.tud.cs.st.vespucci.interfaces;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public interface IViolationSummary {
	
	String getDiagramFile();
	
	IEnsemble getSourceEnsemble();

	IEnsemble getTargetEnsemble();

	IConstraint getConstraint();
	
	int numberOfViolations();
	
	
}

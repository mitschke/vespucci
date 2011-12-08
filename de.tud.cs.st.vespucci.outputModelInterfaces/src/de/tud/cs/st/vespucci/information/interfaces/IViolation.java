package de.tud.cs.st.vespucci.information.interfaces;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public interface IViolation {

	String getDescription();

	// Could be null
	ISourceCodeElement getSourceElement();

	// Could be null
	ISourceCodeElement getTargetElement();

	IEnsemble getSourceEnsemble();

	IEnsemble getTargetEnsemble();

	IConstraint getConstraint();

}

package de.tud.cs.st.vespucci.information.interfaces.spi;

import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;

public class Violation implements IViolation{

	String description;
	ISourceCodeElement sourceElement;
	ISourceCodeElement targetElement;
	IEnsemble sourceEnsemble;
	IEnsemble targetEnsemble;
	IConstraint constraint;
	
	public Violation(String description, ISourceCodeElement sourceElement,
			ISourceCodeElement targetElement, IEnsemble sourceEnsemble,
			IEnsemble targetEnsemble, IConstraint constraint) {
		super();
		this.description = description;
		this.sourceElement = sourceElement;
		this.targetElement = targetElement;
		this.sourceEnsemble = sourceEnsemble;
		this.targetEnsemble = targetEnsemble;
		this.constraint = constraint;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public ISourceCodeElement getSourceElement() {
		return sourceElement;
	}

	@Override
	public ISourceCodeElement getTargetElement() {
		return targetElement;
	}

	@Override
	public IEnsemble getSourceEnsemble() {
		return sourceEnsemble;
	}

	@Override
	public IEnsemble getTargetEnsemble() {
		return targetEnsemble;
	}

	@Override
	public IConstraint getConstraint() {
		return constraint;
	}
	
}

package de.tud.cs.st.vespucci.diagram.outputModelImpl;

import de.tud.cs.st.vespucci.diagram.outputModelInterfaces.IDocumentedViolation;

public class DocumentedViolation extends Connection implements IDocumentedViolation {

	public DocumentedViolation(de.tud.cs.st.vespucci.vespucci_model.Violation connection) {
		super(connection);
	}

}

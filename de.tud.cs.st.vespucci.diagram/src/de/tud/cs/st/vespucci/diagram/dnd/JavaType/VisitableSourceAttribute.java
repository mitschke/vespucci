package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.util.ISourceAttribute;

public class VisitableSourceAttribute implements IVisitable {
	private ISourceAttribute project;

	public VisitableSourceAttribute(Object project) {
		this.project = (ISourceAttribute)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

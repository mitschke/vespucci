package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IMethod;

public class VisitableMethod implements IVisitable {
	private IMethod project;

	public VisitableMethod(Object project) {
		this.project = (IMethod)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IType;

public class VisitableType implements IVisitable {
	private IType project;

	public VisitableType(Object project) {
		this.project = (IType)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

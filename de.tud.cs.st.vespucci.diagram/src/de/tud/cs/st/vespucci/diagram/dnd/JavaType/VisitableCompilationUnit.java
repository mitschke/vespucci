package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.ICompilationUnit;

public class VisitableCompilationUnit implements IVisitable {
	private ICompilationUnit project;

	public VisitableCompilationUnit(Object project) {
		this.project = (ICompilationUnit)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

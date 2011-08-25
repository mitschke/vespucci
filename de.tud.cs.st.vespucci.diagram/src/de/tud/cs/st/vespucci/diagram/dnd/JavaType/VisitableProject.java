package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.core.resources.IProject;

public class VisitableProject implements IVisitable {
	private IProject project;

	public VisitableProject(IProject project) {
		this.project = project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

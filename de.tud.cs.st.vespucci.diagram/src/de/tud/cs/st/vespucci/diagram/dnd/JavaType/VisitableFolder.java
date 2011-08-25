package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.core.resources.IFolder;

public class VisitableFolder implements IVisitable {
	private IFolder project;

	public VisitableFolder(IFolder project) {
		this.project = project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IClassFile;

public class VisitableClassFile implements IVisitable {
	private IClassFile project;

	public VisitableClassFile(IClassFile project) {
		this.project = project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

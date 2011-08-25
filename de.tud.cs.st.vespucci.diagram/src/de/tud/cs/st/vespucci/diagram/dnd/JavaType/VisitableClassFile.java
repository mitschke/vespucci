package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IClassFile;

public class VisitableClassFile implements IVisitable {
	private IClassFile project;

	public VisitableClassFile(Object project) {
		this.project = (IClassFile)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

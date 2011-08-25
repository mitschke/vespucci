package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IField;

public class VisitableField implements IVisitable {
	private IField project;

	public VisitableField(IField project) {
		this.project = project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

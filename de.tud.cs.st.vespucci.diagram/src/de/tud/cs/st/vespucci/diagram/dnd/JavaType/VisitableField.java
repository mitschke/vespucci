package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IField;

public class VisitableField implements IVisitable {
	private IField project;

	public VisitableField(Object project) {
		this.project = (IField)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

public interface IVisitable {
	Object accept(IEclipseObjectVisitor visitor);
}

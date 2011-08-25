package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IPackageFragmentRoot;

public class VisitablePackageFragmentRoot implements IVisitable {
	private IPackageFragmentRoot project;

	public VisitablePackageFragmentRoot(Object project) {
		this.project = (IPackageFragmentRoot)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

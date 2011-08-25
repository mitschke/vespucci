package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IPackageFragment;

public class VisitablePackageFragment implements IVisitable {
	private IPackageFragment project;

	public VisitablePackageFragment(Object project) {
		this.project = (IPackageFragment)project;
	}

	@Override
	public Object accept(IEclipseObjectVisitor visitor) {
		return visitor.visit(project);
	}		
}

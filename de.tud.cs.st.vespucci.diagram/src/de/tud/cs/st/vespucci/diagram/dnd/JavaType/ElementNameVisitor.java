package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.util.ISourceAttribute;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;

public class ElementNameVisitor implements IEclipseObjectVisitor {
	public String getElementName(Object object) {
		if (object instanceof IVisitable) {
			return (String)((IVisitable)object).accept(this);
		} else {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", object));
		}
	}
	
	@Override
	public Object visit(IProject project) {
		return project.getName();
	}
	@Override
	public Object visit(IPackageFragment packageFragment) {
		return packageFragment.isDefaultPackage() ? "" : packageFragment.getElementName();
	}
	@Override
	public Object visit(IPackageFragmentRoot packageFragmentRoot) {
		return packageFragmentRoot.getElementName();
	}
	@Override
	public Object visit(ICompilationUnit compilationUnit) {
		return Resolver.resolveFullyQualifiedClassName(compilationUnit);
	}
	@Override
	public Object visit(IType type) {
		return type.getFullyQualifiedName();
	}
	@Override
	public Object visit(IField field) {
		return Resolver.resolveFullyQualifiedClassName(field) + "." + field.getElementName();
	}
	@Override
	public Object visit(IMethod method) {
		return Resolver.resolveFullyQualifiedClassName(method) + "." + method.getElementName();
	}
	@Override
	public Object visit(ISourceAttribute sourceAttribute) {
		return sourceAttribute.getSourceFileName().toString();
	}
	@Override
	public Object visit(IClassFile classFile) {
		return classFile.getElementName();
	}
	@Override
	public Object visit(IFile file) {
		return file.getName();
	}
	@Override
	public Object visit(IFolder folder) {
		return folder.getName();
	}		
}

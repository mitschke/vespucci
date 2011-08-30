package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.util.ISourceAttribute;
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.JavaElement;
import org.eclipse.jdt.internal.core.PackageFragment;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

public class ElementNameVisitor extends AbstractVisitor {
	private static final String DEFAULT_PACKAGE = "Default Package";
	
	public String getElementName(Object object) {
		return (String)super.visit(object);
	}
	
	@Override
	public Object visit(IProject project) {
		return project.getName();
	}
	@Override
	public Object visit(IPackageFragment packageFragment) {
		return packageFragment.isDefaultPackage() ? DEFAULT_PACKAGE : packageFragment.getElementName();
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
	@Override
	public Object visit(ArrayList<IJavaElement> listOfJavaElements) {
		
		final Object firstElement = listOfJavaElements.get(0);

		if (firstElement instanceof IPackageFragmentRoot) {
			return visit((IPackageFragmentRoot)firstElement);
		} else if (firstElement instanceof IPackageFragment) {
			return visit((IPackageFragment)firstElement);
		} else if (firstElement instanceof IClassFile) {
			return visit((IClassFile)firstElement);
		} else {
			getIllegalArgumentException(firstElement);
		}
		throw new VespucciUnexpectedException(String.format("Given argument [%s] is not supported.", firstElement));
	}

	@Override
	public Object getDefaultResultObject() {
		return null;
	}		
}

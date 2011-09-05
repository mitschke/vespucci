package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;

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

import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;

/**
 * This class provides methods to resolve element names.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 *
 */
public class ElementNameVisitor extends AbstractVisitor {
	private static final String DEFAULT_PACKAGE = "Default Package";

	/**
	 * This method invokes the correct method to retrieve the particular element name.
	 * 
	 * @param object
	 * @return Returns the element name.
	 */
	public String getElementName(final Object object) {
		return (String) super.visit(object);
	}

	@Override
	public Object visit(final IProject project) {
		return project.getName();
	}

	@Override
	public Object visit(final IPackageFragment packageFragment) {
		return packageFragment.isDefaultPackage() ? DEFAULT_PACKAGE : packageFragment.getElementName();
	}

	@Override
	public Object visit(final IPackageFragmentRoot packageFragmentRoot) {
		return packageFragmentRoot.getElementName();
	}

	@Override
	public Object visit(final ICompilationUnit compilationUnit) {
		return Resolver.resolveFullyQualifiedClassName(compilationUnit);
	}

	@Override
	public Object visit(final IType type) {
		return type.getFullyQualifiedName();
	}

	@Override
	public Object visit(final IField field) {
		return Resolver.resolveFullyQualifiedClassName(field) + "." + field.getElementName();
	}

	@Override
	public Object visit(final IMethod method) {
		return Resolver.resolveFullyQualifiedClassName(method) + "." + method.getElementName();
	}

	@Override
	public Object visit(final ISourceAttribute sourceAttribute) {
		return sourceAttribute.getSourceFileName().toString();
	}

	@Override
	public Object visit(final IClassFile classFile) {
		return classFile.getElementName();
	}

	@Override
	public Object visit(final IFile file) {
		return file.getName();
	}

	@Override
	public Object visit(final IFolder folder) {
		return folder.getName();
	}

	@Override
	public Object visit(final ArrayList<IJavaElement> listOfJavaElements) {

		final Object firstElement = listOfJavaElements.get(0);

		if (firstElement instanceof IPackageFragmentRoot) {
			return visit((IPackageFragmentRoot) firstElement);
		} else if (firstElement instanceof IPackageFragment) {
			return visit((IPackageFragment) firstElement);
		} else if (firstElement instanceof IClassFile) {
			return visit((IClassFile) firstElement);
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

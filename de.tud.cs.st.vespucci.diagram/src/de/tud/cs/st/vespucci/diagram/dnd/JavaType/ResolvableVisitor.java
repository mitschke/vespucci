package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

/**
 * This class provides methods to decide whether an object can be resolved.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * 
 */
public class ResolvableVisitor extends AbstractVisitor {

	private static final String DOT_JAR = ".jar";

	/**
	 * This method invokes the correct method to retrieve a resolving decision.
	 * 
	 * @param object
	 * @return Returns true only if the given argument can be resolved.
	 */
	public boolean isResolvable(final Object object) {
		return (Boolean) super.visit(object);
	}

	@Override
	public Object visit(final IPackageFragment packageFragment) {
		return true;
	}

	@Override
	public Object visit(final IPackageFragmentRoot packageFragmentRoot) {
		return true;
	}

	@Override
	public Object visit(final ICompilationUnit icu) {
		try {
			return !icu.getUnderlyingResource().toString().toLowerCase().endsWith(DOT_JAR);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of method %s", icu), e);
		}
	}

	@Override
	public Object visit(final IMethod method) {
		try {
			return !method.getUnderlyingResource().toString().toLowerCase().endsWith(DOT_JAR);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of method %s", method), e);
		}
	}

	@Override
	public Object visit(final IField field) {
		try {
			return !field.getUnderlyingResource().toString().toLowerCase().endsWith(DOT_JAR);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of field %s", field), e);
		}
	}

	@Override
	public Object visit(final IType type) {
		try {
			return !type.getUnderlyingResource().toString().toLowerCase().endsWith(DOT_JAR);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of type %s", type), e);
		}
	}

	@Override
	public Object visit(final ArrayList<IJavaElement> listOfJavaElements) {
		final IJavaElement firstElement = listOfJavaElements.get(0);
		return isLocatedInJarFile(firstElement)
				&& ((firstElement instanceof IPackageFragment) || (firstElement instanceof IPackageFragmentRoot) || (firstElement instanceof IClassFile));
	}

	@Override
	public Object getDefaultResultObject() {
		return false;
	}
}

package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaElement;
import org.eclipse.jdt.internal.core.PackageFragment;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

public class ResolvableVisitor extends AbstractVisitor {
	private static final String JAVA_FILE_ENDING = ".java";
	private static final String JAR_ENDING = ".jar";

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
			return !icu.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of method %s", icu), e);
		}
	}

	@Override
	public Object visit(final IMethod method) {
		try {
			return !method.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of method %s", method), e);
		}
	}

	@Override
	public Object visit(final IField field) {
		try {
			return !field.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of field %s", field), e);
		}
	}

	@Override
	public Object visit(final IType type) {
		try {
			return !type.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of type %s", type), e);
		}
	}
	
	@Override
	public Object visit(ArrayList<IJavaElement> listOfJavaElements) {
		return isLocatedInJarFile(listOfJavaElements.get(0));
	}

	@Override
	public Object getDefaultResultObject() {
		return false;
	}
}

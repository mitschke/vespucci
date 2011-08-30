package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.JavaElement;
import org.eclipse.jdt.internal.core.PackageFragment;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

public class PackageNameVisitor extends AbstractVisitor {
	public String getFullyQualifiedPackageName(final Object object) {
		return (String) super.visit(object);
	}

	@Override
	public Object visit(final IPackageFragment packageFragment) {
		return packageFragment.isDefaultPackage() ? "" : packageFragment.getElementName();
	}

	@Override
	public Object visit(final IMethod method) {
		return visit(method.getCompilationUnit());
	}

	@Override
	public Object visit(final IClassFile classFile) {
		return visit(classFile.getParent());
	}
	
	@Override
	public Object visit(final IField field) {
		return visit(field.getCompilationUnit());
	}

	@Override
	public Object visit(final IType type) {
		return visit(type.getCompilationUnit());
	}

	@Override
	public Object visit(final ICompilationUnit compilationUnit) {
		IPackageDeclaration[] declarations;
		try {
			declarations = compilationUnit.getPackageDeclarations();

			return declarations.length > 0 ? declarations[0].getElementName().trim() : "";
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException((String.format("Failed to resolve package of [%s]", compilationUnit)), e);
		}
	}

	@Override
	public Object visit(final ArrayList<IJavaElement> listOfJavaElements) {
		
		final IJavaElement firstElement = listOfJavaElements.get(0);
		
		if (firstElement instanceof IPackageFragment) {
			return visit((IPackageFragment) firstElement);
		} else if (firstElement instanceof IClassFile) {
			return visit((IClassFile) firstElement);
		}
		throw new VespucciUnexpectedException(String.format("Given argument [%s] is not supported.", firstElement));
	}

	@Override
	public Object getDefaultResultObject() {
		return null;
	}
}

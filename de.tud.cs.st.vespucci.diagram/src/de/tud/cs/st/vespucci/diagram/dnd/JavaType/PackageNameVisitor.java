package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;
import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

public class PackageNameVisitor extends ElementNameVisitor {
	public String getFullyQualifiedPackageName(Object object) {
		if (object instanceof IVisitable) {
			return (String)((IVisitable)object).accept(this);
		} else {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", object));
		}
	}
	
	@Override
	public Object visit(IPackageFragment packageFragment) {
		return packageFragment.isDefaultPackage() ?  "" : packageFragment.getElementName();
	}
	
	@Override
	public Object visit(IMethod method) {
		return visit(method.getCompilationUnit());
	}
	
	@Override
	public Object visit(IField field) {
		return visit(field.getCompilationUnit());
	}
	
	@Override
	public Object visit(IType type) {
		return visit(type.getCompilationUnit());
	}
	
	@Override
	public Object visit(ICompilationUnit compilationUnit) {
		IPackageDeclaration[] declarations;
		try {
			declarations = compilationUnit.getPackageDeclarations();
			if (declarations.length > 0) {
				return declarations[0].getElementName().trim();
			} else {
				return "";
			}
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException((String.format("Failed to resolve package of [%s]", compilationUnit)), e);
		}
	}
}

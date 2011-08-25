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

public class ClassNameVisitor extends AbstractVisitor {
	private static final String JAVA_FILE_ENDING = ".java";
	
	public String getFullyQualifiedClassName(Object object) {
		if (object instanceof IVisitable) {
			return (String)((IVisitable)object).accept(this);
		} else {
			throw new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", object));
		}
	}
	
	@Override
	public Object visit(IMethod method) {
		return method.getDeclaringType().getFullyQualifiedName();
	}
	
	@Override
	public Object visit(IField field) {
		return field.getDeclaringType().getFullyQualifiedName();
	}
	
	@Override
	public Object visit(IType type) {
		return type.getFullyQualifiedName();
	}
	
	@Override
	public Object visit(ICompilationUnit compilationUnit) {
		return
			prependPackageName(
					removeJavaFileEnding(compilationUnit.getElementName()),
					compilationUnit);
	}
	
	private String removeJavaFileEnding(String className) {
		if (className.toLowerCase().endsWith(JAVA_FILE_ENDING)) {
			return className.substring(0, className.length() - JAVA_FILE_ENDING.length());
		} else {
			return className;
		}
	}
	
	private String prependPackageName(final String className, final Object javaElement) {
		final String fqPackageName = Resolver.resolveFullyQualifiedPackageName(javaElement);
		return fqPackageName.equals("") ? className : fqPackageName + "." + className;
	}
}

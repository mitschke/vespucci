package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

/**
 * This class provides methods to resolve class names.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 *
 */
public class ClassNameVisitor extends AbstractVisitor {
	private static final String DOT_JAVA = ".java";

	/**
	 * This method invokes the correct method to retrieve the particular class name.
	 * 
	 * @param object
	 * @return Returns the fully qualified class name.
	 */
	public String getFullyQualifiedClassName(final Object object) {
		return (String) super.visit(object);
	}

	@Override
	public Object visit(final IMethod method) {
		return method.getDeclaringType().getFullyQualifiedName();
	}

	@Override
	public Object visit(final IField field) {
		return field.getDeclaringType().getFullyQualifiedName();
	}

	@Override
	public Object visit(final IType type) {
		return type.getFullyQualifiedName();
	}

	@Override
	public Object visit(final ICompilationUnit compilationUnit) {
		return prependPackageName(removeJavaFileEnding(compilationUnit.getElementName()), compilationUnit);
	}

	private String removeJavaFileEnding(final String className) {
		if (className.toLowerCase().endsWith(DOT_JAVA)) {
			return className.substring(0, className.length() - DOT_JAVA.length());
		} else {
			return className;
		}
	}

	private String prependPackageName(final String className, final Object javaElement) {
		final String fqPackageName = Resolver.resolveFullyQualifiedPackageName(javaElement);
		return fqPackageName.equals("") ? className : fqPackageName + "." + className;
	}

	@Override
	public Object getDefaultResultObject() {
		return null;
	}
}

package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

public class ClassNameVisitor extends AbstractVisitor {
	private static final String JAVA_FILE_ENDING = ".java";
	
	public String getFullyQualifiedClassName(Object object) {
		return (String)super.visit(object);
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

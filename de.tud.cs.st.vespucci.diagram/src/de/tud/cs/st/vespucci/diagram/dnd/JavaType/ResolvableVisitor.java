package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

public class ResolvableVisitor extends AbstractVisitor {
	private static final String JAVA_FILE_ENDING = ".java";
	private static final String JAR_ENDING = ".jar";
	
	public boolean isResolvable(Object object) {
		return (Boolean)super.visit(object);
	}
	
	@Override
	public Object visit(IPackageFragment packageFragment) {
		return true;
	}
	
	@Override
	public Object visit(IPackageFragmentRoot packageFragmentRoot) {
		return true;
	}
	
	@Override
	public Object visit(IMethod method) {
		try {
			return !method.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (JavaModelException e) {
			throw new VespucciUnexpectedException(
					String.format("Could not access underlying resource of method %s", method), e);
		}
	}
	
	@Override
	public Object visit(IField field) {
		try {
			return !field.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (JavaModelException e) {
			throw new VespucciUnexpectedException(
					String.format("Could not access underlying resource of field %s", field), e);
		}
	}
	
	@Override
	public Object visit(IType type) {
		try {
			return !type.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING);
		} catch (JavaModelException e) {
			throw new VespucciUnexpectedException(
					String.format("Could not access underlying resource of type %s", type), e);
		}
	}

	@Override
	public Object getDefaultResultObject() {
		return false;
	}
}

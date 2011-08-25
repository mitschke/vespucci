package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.util.ISourceAttribute;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;

public abstract class AbstractVisitor implements IEclipseObjectVisitor {
	
	public abstract Object getDefaultResultObject();
	
	public Object visit(Object element) {
		try {
			final Class[] elementInterfaces = element.getClass().getInterfaces();
			
			if (elementInterfaces.length == 0) {
				throw getIllegalArgumentException(element);
			}
			
			// For the considered classes, the first interface is the public interface
			// of interest. Thus, it is avoided to depend on eclipse internal packages.
			final Class firstPublicInterface = elementInterfaces[0];			
			final Method correctVisitMethod = getClass().getMethod("visit", new Class[] { firstPublicInterface });

			return correctVisitMethod.invoke(this, new Object[] { element });
		} catch (SecurityException exception) {
			throw getIllegalArgumentException(element);
		} catch (NoSuchMethodException exception) {
			throw getIllegalArgumentException(element);
		} catch (IllegalArgumentException exception) {
			throw getIllegalArgumentException(element);
		} catch (IllegalAccessException exception) {
			throw getIllegalArgumentException(element);
		} catch (InvocationTargetException exception) {
			throw getIllegalArgumentException(element);
		}
	}
	
	@Override
	public Object visit(IProject project) {
		return doDefaultAction(project);
	}

	@Override
	public Object visit(IPackageFragment packageFragment) {
		return doDefaultAction(packageFragment);
	}

	@Override
	public Object visit(IPackageFragmentRoot packageFragmentRoot) {
		return doDefaultAction(packageFragmentRoot);
	}

	@Override
	public Object visit(ICompilationUnit compilationUnit) {
		return doDefaultAction(compilationUnit);
	}

	@Override
	public Object visit(IType type) {
		return doDefaultAction(type);
	}

	@Override
	public Object visit(IField field) {
		return doDefaultAction(field);
	}

	@Override
	public Object visit(IMethod method) {
		return doDefaultAction(method);
	}

	@Override
	public Object visit(ISourceAttribute sourceAttribute) {
		return doDefaultAction(sourceAttribute);
	}

	@Override
	public Object visit(IClassFile classFile) {
		return doDefaultAction(classFile);
	}

	@Override
	public Object visit(IFile file) {
		return doDefaultAction(file);
	}

	@Override
	public Object visit(IFolder folder) {
		return doDefaultAction(folder);
	}
	
	private Object doDefaultAction(Object inputObject) {
		if (getDefaultResultObject() != null) {
			return getDefaultResultObject();
		} else {
			throw getIllegalArgumentException(inputObject);
		}
	}
	
	private RuntimeException getIllegalArgumentException(Object argument) {
		return new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", argument));
	}
}

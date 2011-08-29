package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
import org.eclipse.jdt.internal.core.ClassFile;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.internal.core.SourceField;
import org.eclipse.jdt.internal.core.SourceMethod;
import org.eclipse.jdt.internal.core.SourceType;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;
import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

/**
 * This abstract class provides the methods for all its subclasses and uses the reflection API to
 * determine the kind of visitor which is currently calling the visit method.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * 
 */
public abstract class AbstractVisitor implements IEclipseObjectVisitor {

	public abstract Object getDefaultResultObject();

	public Object visit(Object element) {
		try {

			Class current = element.getClass();

			if (element instanceof IJavaElement && isLocatedInJarFile((IJavaElement) element)) {
				// getClass().getInterfaces doesn't work when "element" lies in JAR-file
				
			}

			boolean place = true;

			final Class[] elementInterfaces = element.getClass().getInterfaces();

			if (elementInterfaces.length == 0) {
				throw getIllegalArgumentException(element);
			}

			// For the considered classes, the first interface is the public interface
			// of interest. Thus, it is avoided to depend on eclipse internal packages.
			final Class firstPublicInterface = elementInterfaces[0];
			final Class[] correctClass = new Class[] { firstPublicInterface };
			final Method correctVisitMethod = getClass().getMethod("visit", correctClass);
			final Object invocation = correctVisitMethod.invoke(this, new Object[] { element });
			
			return invocation;
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
		} catch (NullPointerException exception) {
			throw new VespucciUnexpectedException("Method name must not be null.", exception);
		}
	}

	private static boolean isLocatedInJarFile(IJavaElement element) {
		IJavaElement parent = element.getParent();
		while (parent != null) {
			if (element.getParent() instanceof JarPackageFragmentRoot) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

	/**
	 * 
	 * @param o
	 * @return Returns the specific class of the element.
	 */
	private Class getCorrectClass(Object o) {

		return null;
	}

	boolean hasJARParent(Object o) {
		return false;
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

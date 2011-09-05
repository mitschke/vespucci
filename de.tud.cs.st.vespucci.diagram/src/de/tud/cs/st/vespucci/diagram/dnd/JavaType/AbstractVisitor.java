package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;

import de.tud.cs.st.vespucci.exceptions.VespucciIllegalArgumentException;
import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;

/**
 * This abstract class provides the methods for all its subclasses and uses the reflection API to
 * determine the kind of visitor and object which is currently calling the visit method.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * 
 */
public abstract class AbstractVisitor implements IEclipseObjectVisitor {

	public abstract Object getDefaultResultObject();

	public Object visit(final Object element) {
		
		String className = this.getClass().getName();
		
		try {

			if (element instanceof IJavaElement && isLocatedInJarFile((IJavaElement) element)) {
				// getClass().getInterfaces doesn't return any interfaces when element lies in JAR
				final IJavaElement elementInJar = (IJavaElement) element;
				final ArrayList listOfJavaElements = new ArrayList<IJavaElement>();
				listOfJavaElements.add(elementInJar);
				final Class currentClass = listOfJavaElements.getClass();
				final Method correctVisitMethod = getClass().getMethod("visit", currentClass);
				final Object invocation = correctVisitMethod.invoke(this, new Object[] { listOfJavaElements });

				return invocation;
			}

			final Class currentClass = element.getClass();
			final Class[] elementInterfaces = currentClass.getInterfaces();

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
		} catch (final SecurityException exception) {
			throw getIllegalArgumentException(element);
		} catch (final NoSuchMethodException exception) {
			throw getIllegalArgumentException(element);
		} catch (final IllegalArgumentException exception) {
			throw getIllegalArgumentException(element);
		} catch (final IllegalAccessException exception) {
			throw getIllegalArgumentException(element);
		} catch (final InvocationTargetException exception) {
			throw getIllegalArgumentException(element);
		} catch (final NullPointerException exception) {
			throw new VespucciUnexpectedException("Method name must not be null.", exception);
		}
	}

	protected static boolean isLocatedInJarFile(final IJavaElement element) {
		IJavaElement parent = element;
		while (parent != null) {
			if (parent instanceof JarPackageFragmentRoot) {
				return true;
			}
			parent = parent.getParent();
		}
		return false;
	}

	@Override
	public Object visit(final IProject project) {
		return doDefaultAction(project);
	}

	@Override
	public Object visit(final IPackageFragment packageFragment) {
		return doDefaultAction(packageFragment);
	}

	@Override
	public Object visit(final IPackageFragmentRoot packageFragmentRoot) {
		return doDefaultAction(packageFragmentRoot);
	}

	@Override
	public Object visit(final ICompilationUnit compilationUnit) {
		return doDefaultAction(compilationUnit);
	}

	@Override
	public Object visit(final IType type) {
		return doDefaultAction(type);
	}

	@Override
	public Object visit(final IField field) {
		return doDefaultAction(field);
	}

	@Override
	public Object visit(final IMethod method) {
		return doDefaultAction(method);
	}

	@Override
	public Object visit(final ISourceAttribute sourceAttribute) {
		return doDefaultAction(sourceAttribute);
	}

	@Override
	public Object visit(final IClassFile classFile) {
		return doDefaultAction(classFile);
	}

	@Override
	public Object visit(final IFile file) {
		return doDefaultAction(file);
	}

	@Override
	public Object visit(final IFolder folder) {
		return doDefaultAction(folder);
	}

	@Override
	public Object visit(final ArrayList<IJavaElement> listOfJavaElements) {
		return doDefaultAction(listOfJavaElements.get(0));
	}

	private Object doDefaultAction(final Object inputObject) {
		if (getDefaultResultObject() != null) {
			return getDefaultResultObject();
		} else {
			throw getIllegalArgumentException(inputObject);
		}
	}

	protected RuntimeException getIllegalArgumentException(final Object argument) {
		return new VespucciIllegalArgumentException(String.format("Given argument [%s] not supported.", argument));
	}
}

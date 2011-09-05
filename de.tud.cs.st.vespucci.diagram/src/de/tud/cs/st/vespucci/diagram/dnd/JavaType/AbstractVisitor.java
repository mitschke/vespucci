/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
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

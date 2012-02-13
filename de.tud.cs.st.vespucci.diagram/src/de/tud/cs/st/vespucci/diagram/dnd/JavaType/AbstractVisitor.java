/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
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
 *   - Neither the name of the Software Technology Group Group or Technische 
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

import java.util.ArrayList;

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

/**
 * This abstract class is the super class of all visitors. The abstract class
 * provides a the method invokeCorrectMethod to invoke the correct overloaded
 * visit function of the concrete visitor. All visit functions are implemented
 * to call getDefaultResultObject, hence selective overriding is supported.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * @author Ralf Mitschke
 * 
 */
public abstract class AbstractVisitor implements IEclipseObjectVisitor {

	public abstract Object getDefaultResultObject();

	/**
	 * 
	 * @param element
	 * @return The invocation of the correct method (w.r.t. the parameter type)
	 */
	public Object invokeCorrectMethod(final Object element) {

		if (!(element instanceof IJavaElement))
			return false;

		final IJavaElement javaElement = (IJavaElement) element;

		switch (javaElement.getElementType()) {
		case IJavaElement.CLASS_FILE:
			return visit((IClassFile) element);
		case IJavaElement.COMPILATION_UNIT:
			return visit((ICompilationUnit) element);
		case IJavaElement.FIELD:
			return visit((IField) element);
		case IJavaElement.METHOD:
			return visit((IMethod) element);
		case IJavaElement.PACKAGE_FRAGMENT:
			return visit((IPackageFragment) element);
		case IJavaElement.PACKAGE_FRAGMENT_ROOT:
			return visit((IPackageFragmentRoot) element);
		case IJavaElement.JAVA_PROJECT:
			return visit((IProject) element);
		case IJavaElement.TYPE:
			return visit((IType) element);
		}
		throw getIllegalArgumentException(element);
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
	public Object visit(final IClassFile classFile) {
		return doDefaultAction(classFile);
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

	/**
	 * 
	 * @param argument
	 * @return Returns the specific VespucciException.
	 */
	protected RuntimeException getIllegalArgumentException(final Object argument) {
		return new VespucciIllegalArgumentException(String.format(
				"Given argument [%s] not supported.", argument));
	}
}

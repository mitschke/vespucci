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

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;

/**
 * This class provides methods to decide whether an object can be resolved.
 * 
 * The resolving decision determines if an element can be dragged over to the Vespucci workspace.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * 
 */
public class ResolvableVisitor extends AbstractVisitor {

	/**
	 * JAR-File ending
	 */
	private static final String DOT_JAR = ".jar";

	/**
	 * This method invokes the correct method to retrieve a resolving decision.
	 * 
	 * @param object
	 * @return Returns true only if the given argument can be resolved.
	 */
	public boolean isResolvable(final Object object) {
		return (Boolean) super.invokeCorrectMethod(object);
	}

	@Override
	public Object visit(final IPackageFragment packageFragment) {
		return true;
	}

	@Override
	public Object visit(final IPackageFragmentRoot packageFragmentRoot) {
		return true;
	}

	@Override
	public Object visit(final ICompilationUnit icu) {
		try {
			return !icu.getUnderlyingResource().toString().toLowerCase().endsWith(DOT_JAR);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access underlying resource of ICU [%s]", icu), e);
		}
	}

	@Override
	public Object visit(final IClassFile icf) {
		return true;
	}

	@Override
	public Object visit(final IMethod method) {
		return true;
	}

	@Override
	public Object visit(final IField field) {
		return true;
	}

	@Override
	public Object visit(final IType type) {
		return true;
	}

	@Override
	public Object visit(final ArrayList<IJavaElement> listOfJavaElements) {
		final IJavaElement firstElement = listOfJavaElements.get(0);
		return ((firstElement instanceof IPackageFragment) || (firstElement instanceof IPackageFragmentRoot));
	}

	@Override
	public Object getDefaultResultObject() {
		return false;
	}
}

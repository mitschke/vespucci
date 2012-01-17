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

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

/**
 * This class provides methods to resolve class names.
 * 
 * The resolved class names are used to build ensemble queries.
 * 
 * @author Dominic Scheurer
 * @author Thomas Schulz
 * 
 */
public class ClassNameVisitor extends AbstractVisitor {

	/**
	 * This method invokes the correct method to retrieve the particular class name.
	 * 
	 * @param object
	 * @return Returns the fully qualified class name.
	 */
	public String getFullyQualifiedClassName(final Object object) {
		return (String) super.invokeCorrectMethod(object);
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
		return prependPackageName(removeFileEnding(compilationUnit.getElementName(), ".java"), compilationUnit);
	}

	@Override
	public Object visit(final IClassFile classFile) {
		return prependPackageName(removeFileEnding(classFile.getElementName(), ".class"), classFile);
	}

	private static String removeFileEnding(final String potentialClassName, final String ending) {
		if (potentialClassName.toLowerCase().endsWith(ending)) {
			return potentialClassName.substring(0, potentialClassName.length() - ending.length());
		} else {
			return potentialClassName;
		}
	}

	private static String prependPackageName(final String className, final Object javaElement) {
		final String fqPackageName = Resolver.resolveFullyQualifiedPackageName(javaElement);
		return fqPackageName.equals("") ? className : fqPackageName + "." + className;
	}

	@Override
	public Object getDefaultResultObject() {
		return null;
	}
}

/*
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;

import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;

/**
 * This class provides static methods to resolve information from Vespucci diagrams mainly used to
 * create queries in QueryBuilder.java
 * 
 * 
 * @author Benjamin Lück
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 * @author Dominic Scheurer
 */
public class Resolver {

	private static final String CONSTRUCTOR = "<init>";

	/**
	 * This flag is needed in order to handle resolving packages from JAR-files individually
	 * TODO Find a nicer way to handle JAR-files with reflections and the visitor pattern
	 */
	private static boolean resolvingJARFile = false;

	private Resolver() {
	}

	private static String getFullyQualifiedName(final String type, final IType jdtDeclaringType) {
		try {
			return JavaModelUtil.getResolvedTypeName(type, jdtDeclaringType);
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Could not access type [%s]", jdtDeclaringType), e);
		}
	}

	/**
	 * Getting the package name (FQN) from a IMethod, IPackageFragment, ICompilationUnit, IField and
	 * IType.
	 * 
	 * @param element
	 *            The named IJavaElement.
	 * @return Returns the name of the package.
	 */
	public static String resolveFullyQualifiedPackageName(final Object element) {
		final PackageNameVisitor pnv = new PackageNameVisitor();

		if (element instanceof IPackageFragmentRoot) {
			// XXX: hack to support content of JAR-files
			return (String) pnv.visit((IPackageFragmentRoot) element);
		}

		return pnv.getFullyQualifiedPackageName(element);
	}

	/**
	 * @param objects
	 * @return Returns true, only if all given objects can be resolved.
	 */
	public static boolean isResolvable(final Collection<Object> objects) {
		final ResolvableVisitor resolvableVisitor = new ResolvableVisitor();

		for (final Object object : objects) {

			if (object instanceof IPackageFragmentRoot) {
				// XXX: hack to support content of JAR-files
				// Because this method is always called when one drags a JAR-file
				// from the Project-Explorer to the Vespucci-Workspace, the flag
				// can safely be alternated here.
				resolvingJARFile = true;
				continue;
			} else if (!resolvableVisitor.isResolvable(object)) {
				resolvingJARFile = false;
				return false;
			}
		}

		return true;
	}

	static String resolveFullyQualifiedClassName(final Object javaElement) {
		return new ClassNameVisitor().getFullyQualifiedClassName(javaElement);
	}

	/**
	 * @param element
	 *            The java element, whose class name shall be resolved.
	 * @return Returns the class name without preceding "&#60PACKAGENAME>.". Java file ending will
	 *         be removed.
	 */
	public static String resolveClassName(final Object element) {
		String classname = resolveFullyQualifiedClassName(element);
		final String pkg = resolveFullyQualifiedPackageName(element);

		// remove package name
		if (classname.startsWith(pkg)) {
			classname = classname.substring(pkg.length());
		}
		if (classname.startsWith(".")) {
			classname = classname.substring(1);
		}

		return classname;
	}

	/**
	 * @param method
	 * @return The return type of the given IMethod.
	 */
	public static String resolveReturnType(final IMethod method) {
		try {
			return getFullyQualifiedName(method.getReturnType(), method.getDeclaringType());
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Failed to resolve return type of [%s]", method), e);
		}
	}

	/**
	 * @param method
	 * @return Returns the parameters from the given method as a list of strings.
	 */
	public static List<String> getParameterTypesFromMethod(final IMethod method) {
		final List<String> parameters = new ArrayList<String>();

		for (final String type : method.getParameterTypes()) {
			parameters.add(getFullyQualifiedName(type, method.getDeclaringType()));
		}

		return parameters;
	}

	/**
	 * @param method
	 * @return Returns the name of the given method.
	 */
	public static String getMethodName(final IMethod method) {
		// get method name, set as <init> if it is the constructor of the class
		try {
			if (method.isConstructor()) {
				return CONSTRUCTOR;
			} else {
				return method.getElementName();
			}
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Couldn't determine if method [%s] is a constructor.", method), e);
		}
	}

	/**
	 * @param field
	 * @return Returns the name of the field type.
	 */
	public static String getFullyQualifiedFieldTypeName(final IField field) {
		try {
			return getFullyQualifiedName(field.getTypeSignature(), field.getDeclaringType());
		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Failed to resolve field type of [%s].", field), e);
		}

	}

	/**
	 * @param element
	 * @return Returns the name for the given element.
	 */
	public static String getElementNameFromObject(final Object element) {
		final ElementNameVisitor env = new ElementNameVisitor();

		if (element instanceof IPackageFragmentRoot) {
			// XXX: hack to support content of JAR-files
			return (String) env.visit((IPackageFragmentRoot) element);
		}

		return env.getElementName(element);
	}

	/**
	 * Gets all packages from a IPackageFragmentRoot; e.g. a src-folder or a JAR-file which is in
	 * the project referenced libraries.
	 * 
	 * @param packageRoot
	 *            The package root
	 * @return Returns a list of packages contained in given package root.
	 */
	public static List<String> getPackagesFromPFR(final IPackageFragmentRoot packageRoot) {
		final List<String> packages = new ArrayList<String>();

		try {
			final IJavaElement[] children = packageRoot.getChildren();

			for (final IJavaElement childPackage : children) {

				final String fqPackageName;

				if (resolvingJARFile) {
					// XXX: hack to support content of JAR-files
					fqPackageName = getFQPackageNameforJARFiles(childPackage);
				} else {
					fqPackageName = resolveFullyQualifiedPackageName(childPackage).trim();
				}

				if (fqPackageName.length() > 0) {
					packages.add(fqPackageName);
				}
			}
			return packages;

		} catch (final JavaModelException e) {
			throw new VespucciUnexpectedException(String.format("Failed to get child packages from package root [%s].",
					packageRoot), e);
		}
	}

	/**
	 * This is the same source code as in the PackageNameVisitor class.
	 * 
	 * @param childPackage
	 * @return the fully qualified package name
	 */
	private static String getFQPackageNameforJARFiles(IJavaElement childPackage) {
		if (childPackage instanceof IPackageFragment) {
			final IPackageFragment pkg = (IPackageFragment) childPackage;
			if (pkg.isDefaultPackage()) {
				return "";
			} else {
				return ((IPackageFragment) childPackage).getElementName();
			}
		} else {
			throw new VespucciUnexpectedException("CRAP");
		}
	}

}

/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author BenjaminL
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.util.ISourceAttribute;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.errors.VespucciIllegalArgumentException;
import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * TODO correct the subsequent documentation (Static class???)
 * static class to get all information about the units supports e.g. type
 * conversion from java binary type to FQN or resolving all packages from a
 * given project folder
 * 
 * @author Benjamin Lück
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class Resolver {

	private static final String CONSTRUCTOR = "<init>";

	private static final String JAVA = ".java";

	private static final String JAR_ENDING = ".jar";

	private static final String DEFAULT_PACKAGE = "Default Package";

	private Resolver() {
	}

	private static String getFullyQualifiedName(final String type, final IType jdtDeclaringType) {
		try {
			return JavaModelUtil.getResolvedTypeName(type, jdtDeclaringType);
		} catch (final JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve type", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			// TODO document why it makes sense to return null (here)
			return null;
		}
	}

	/**
	 * Getting the package name (FQN) from a IMethod, IPackageFragment,
	 * ICompilationUnit, IField and IType.
	 * 
	 * @param o The named IJavaElement.
	 * @return Returns the name of the package.
	 */
	public static String getFQPackageName(final Object o) {
		// package...
		if (o instanceof IPackageFragment) {
			final IPackageFragment pkg = (IPackageFragment) o;
			if (pkg.isDefaultPackage()) {
				return "";
			} else {
				return ((IPackageFragment) o).getElementName();
			}
		} else {
			// class, method, field, type
			ICompilationUnit cUnit;

			if (o instanceof IMethod) {
				cUnit = ((IMethod) o).getCompilationUnit();
			} else if (o instanceof IField) {
				cUnit = ((IField) o).getCompilationUnit();
			} else if (o instanceof ICompilationUnit) {
				cUnit = (ICompilationUnit) o;
			} else if (o instanceof IType) {
				cUnit = ((IType) o).getCompilationUnit();
			} else {
				throw new VespucciIllegalArgumentException(String.format("Given argument [%s] is not supported.", o));
			}

			IPackageDeclaration[] declarations;
			try {
				declarations = cUnit.getPackageDeclarations();
				if (declarations.length > 0) {
					return declarations[0].getElementName().trim();
				} else {
					return "";
				}
			} catch (final JavaModelException e) {
				// TODO Move status handling to Exception superclass.
				final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
						"JavaModelException: Failed to resolve Package", e);
				StatusManager.getManager().handle(is, StatusManager.SHOW);
				StatusManager.getManager().handle(is, StatusManager.LOG);
				
				throw new VespucciUnexpectedException((String.format("Failed to resolve package of [%s]", cUnit)),e);
			}
		}
	}

	/**
	 * @param map
	 * @return Returns true, only if ALL objects are processable. TODO what does it mean, to be processable?
	 */
	public static boolean isProcessable(final Map<String, Object> map) {

		for (final String key : map.keySet()) { // FIXME the loop statement is useless; it is
												// equivalent
			// to "getFirst"... either correct the name of the
			// method and documentation or the implementation!
			final Object o = map.get(key);
			boolean akt = false;

			try {

				if (o instanceof IPackageFragment) {
					akt = true;
				} else if (o instanceof IPackageFragmentRoot) {
					akt = true;
				} else if (o instanceof ICompilationUnit) {
					final ICompilationUnit cU = (ICompilationUnit) o;
					if (cU.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING)) {
						akt = false;
					} else {
						akt = true;
					}
				} else if (o instanceof IMethod) {
					final IMethod m = (IMethod) o;
					if (m.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING)) {
						akt = false;
					} else {
						akt = true;
					}
				} else if (o instanceof IField) {
					final IField f = (IField) o;
					if (f.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING)) {
						akt = false;
					} else {
						akt = true;
					}
				} else if (o instanceof IType) {
					final IType t = (IType) o;
					if (t.getUnderlyingResource().toString().toLowerCase().endsWith(JAR_ENDING)) {
						akt = false;
					} else {
						akt = true;
					}
				}

			} catch (final JavaModelException e) {
				final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID, "JavaModelException", e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			} catch (final NullPointerException e) {
				// TODO Hack!!! This hack is implemented, to avoid in each type
				// the check if the underlying resource is null. This approach
				// is done because files of a jar files should be (in future)
				// droppable!
				return false;
			}

			return akt;

		}
		return true;
	}

	/**
	 * getting the FQN classname
	 */
	public static String getFQClassnamefromIxxx(final Object o, final String key) { // TODO Rename
		// getFQClassNamefromIxxx
		String classname = "";
		if (o instanceof IMethod) {
			return ((IMethod) o).getDeclaringType().getFullyQualifiedName();
		} else if (o instanceof IField) {
			return ((IField) o).getDeclaringType().getFullyQualifiedName();
		} else if (o instanceof ICompilationUnit) {
			classname = ((ICompilationUnit) o).getElementName();
		} else if (o instanceof IType) {
			return ((IType) o).getFullyQualifiedName();
		}

		if (classname.toLowerCase().endsWith(JAVA)) {
			classname = classname.substring(0, classname.length() - JAVA.length());
		}
		if (classname.equals("")) {
			return "";
		} else {
			final String FQNpkg = getFQPackageName(o);
			if (FQNpkg.equals("")) {
				return classname;
			} else {
				return FQNpkg + "." + classname;
			}
		}
	}

	/**
	 * getting the classname
	 */
	public static String getClassnamefromIxxx(final Object o, final String key) {
		// TODO fix the code duplication problem!
		// method is the same as getFQClassnamefromIxxx only the package
		// name is not resolved - extending getFQClassnamefromIxxx with a
		// boolean to switch between FQN and N?
		// little hack: substring FQN without packagename

		// class, method, field, type
		String classname = "";
		final String pkg = getFQPackageName(o);

		if (o instanceof IMethod) {
			classname = ((IMethod) o).getDeclaringType().getFullyQualifiedName();
		} else if (o instanceof IField) {
			classname = ((IField) o).getDeclaringType().getFullyQualifiedName();
		} else if (o instanceof ICompilationUnit) {
			classname = ((ICompilationUnit) o).getElementName();
		} else if (o instanceof IType) {
			classname = ((IType) o).getFullyQualifiedName();
		}

		// classname without package name
		if (classname.startsWith(pkg)) {
			classname = classname.substring(pkg.length());
		}
		if (classname.startsWith(".")) {
			classname = classname.substring(1);
		}

		if (classname.toLowerCase().endsWith(JAVA)) {
			classname = classname.substring(0, classname.length() - JAVA.length());
		}
		if (classname.equals("")) {
			return "";
		} else {
			return classname;
		}
	}

	/**
	 * returns the return type of the given IMethod (in this moment IMethod is
	 * the only one)
	 */
	public static String getReturnTypeFromIxxx(final Object o, final String key) {
		try {
			if (o instanceof IMethod) {
				final IMethod m = (IMethod) o;
				return getFullyQualifiedName(m.getReturnType(), m.getDeclaringType());
			}
			return null;
		} catch (final JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve returntype", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			// TODO why does it make sense to return null?
			return null;
		}

	}

	/**
	 * getting all parameters from a given IMethod
	 * 
	 * @return returns the parameters as an string list
	 */
	public static List<String> getParameterTypesFromMethod(final IMethod method) {
		final List<String> parameters = new ArrayList<String>();

		for (final String type : method.getParameterTypes()) {
			parameters.add(getFullyQualifiedName(type, method.getDeclaringType()));
		}

		return parameters;
	}

	/**
	 * get the name of a method
	 * 
	 * @param method
	 * @return String of the method name
	 */
	public static String getMethodnameFromMethod(final IMethod method) { // TODO rename
																			// getMethodName...
		// get method name, set as <init> if it is the constructor of the class
		try {
			if (method.isConstructor()) {
				return CONSTRUCTOR;
			} else {
				return method.getElementName();
			}
		} catch (final JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve methodname", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			// TODO why does it make sense to return null?
			return null;
		}
	}

	/**
	 * get the name of an field
	 * 
	 * @param field
	 * @return String of the field name
	 */
	public static String getFQFieldTypeName(final IField field) {
		try {
			return getFullyQualifiedName(field.getTypeSignature(), field.getDeclaringType());
		} catch (final JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve field type", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			// TODO why does it make sense to return null?
			return null;
		}

	}

	/**
	 * try to resolve the element name of the given object
	 * 
	 * @param the
	 *            given object
	 * @return the element name
	 */
	public static String getElementNameFromObject(final Object o) {
		if (o instanceof IProject) {
			return ((IProject) o).getName();
		} else if (o instanceof IPackageFragment) {
			final IPackageFragment pkg = (IPackageFragment) o;
			if (pkg.isDefaultPackage()) {
				return DEFAULT_PACKAGE;
			} else {
				return ((IPackageFragment) o).getElementName();
			}
		} else if (o instanceof IPackageFragmentRoot) {
			return ((IPackageFragmentRoot) o).getElementName();
		} else if (o instanceof ICompilationUnit) {
			final ICompilationUnit cU = (ICompilationUnit) o;
			return Resolver.getFQClassnamefromIxxx(cU, "");
		} else if (o instanceof IType) {
			final IType type = (IType) o;
			return type.getFullyQualifiedName();
		} else if (o instanceof IField) {
			return Resolver.getFQClassnamefromIxxx(o, "") + "." + ((IField) o).getElementName();
		} else if (o instanceof IMethod) {
			return Resolver.getFQClassnamefromIxxx(o, "") + "." + ((IMethod) o).getElementName();
		} else if (o instanceof ISourceAttribute) {
			return ((ISourceAttribute) o).getSourceFileName().toString();
		} else if (o instanceof IClassFile) {
			return ((IClassFile) o).getElementName();
		} else if (o instanceof IFile) {
			return ((IFile) o).getName();
		} else if (o instanceof IFolder) {
			return ((IFolder) o).getName();
		} else {
			// nothing
			return "";
		}
	}

	/**
	 * getting all packages from a IPackageFragmentRoot; e.g. a src-folder or a
	 * JAR-file which is in the project referenced libraries
	 * 
	 * @return list<Strings> of the included package names
	 */
	public static List<String> getPackagesFromPFR(final IPackageFragmentRoot pfr) {
		final List<String> packages = new ArrayList<String>();

		try {
			final IJavaElement[] children = pfr.getChildren();

			for (final IJavaElement aPackage : children) {
				final String packageName = aPackage.getElementName();
				final String p = getFQPackageName(aPackage).trim();

				final IPackageFragment aPKG = (IPackageFragment) aPackage;
				if (aPKG.isDefaultPackage()) {
					packages.add("");
				}
				if (p.length() > 0) {
					packages.add(p);
				}
			}
			return packages;

		} catch (final JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve packages of src-folder/JAR-file", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			// TODO is this behavior meaningfull?
			return Collections.emptyList();
		}
	}

}

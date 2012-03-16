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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;

import de.tud.cs.st.vespucci.diagram.dnd.JavaType.Resolver;

/**
 * A Class which provides static tools for supporting DnD.
 * 
 * @author Malte Viering
 * @author Benjamin Lück
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class QueryBuilder {

	// constants for the querybuilder
	private static final String PACKAGE = "package";
	private static final String CLASS_WITH_MEMBERS = "class_with_members";
	private static final String METHOD = "method";
	private static final String FIELD = "field";
	private static final String QUERY_DELIMITER = " or ";
	private static final String DERIVED = "derived";
	private static final String STANDARD_SHAPENAME = "A dynamic name";
	private static final Object EMPTY = "empty";

	private static String createClassQueryFromClassFile(Object draggedElement) {
		String classQuery;
		final String packagename = Resolver.resolveFullyQualifiedPackageName(draggedElement);
		final String classname = Resolver.resolveClassName(draggedElement);

		classQuery = String.format("%s('%s','%s')", CLASS_WITH_MEMBERS, packagename, classname);

		return classQuery;
	}

	private static String createClassQueryFromCompilationUnit(final Object draggedElement) {
		String classQuery;
		final String packagename = Resolver.resolveFullyQualifiedPackageName(draggedElement);
		final String classname = Resolver.resolveClassName(draggedElement);

		classQuery = String.format("%s('%s','%s')", CLASS_WITH_MEMBERS, packagename, classname);

		return classQuery;
	}

	private static String createFieldQuery(final Object draggedElement) {
		final IField iField = (IField) draggedElement;
		final String packagename = Resolver.resolveFullyQualifiedPackageName(draggedElement);
		final String classname = Resolver.resolveClassName(draggedElement);
		final String fieldname = iField.getElementName();
		final String type = Resolver.getFullyQualifiedFieldTypeName(iField);

		return String.format("%s('%s','%s','%s','%s')", FIELD, packagename, classname, fieldname, type);
	}

	private static List<String> createJARQuery(final Object draggedElement) {
		final LinkedList<String> queryList = new LinkedList<String>();
		final List<String> packages = Resolver.getPackagesFromPFR((IPackageFragmentRoot) draggedElement);
		for (final String s : packages) {
			final String jarQuery = String.format("%s('%s')", PACKAGE, s);
			queryList.add(jarQuery);
		}
		return queryList;
	}

	private static String createMethodQuery(final Object draggedElement) {
		final IMethod iMethod = (IMethod) draggedElement;
		final String packagename = Resolver.resolveFullyQualifiedPackageName(draggedElement);
		final String classname = Resolver.resolveClassName(draggedElement);
		final String methodname = Resolver.getMethodName(iMethod);
		final List<String> para = Resolver.getParameterTypesFromMethod(iMethod);
		final String returntype = Resolver.resolveReturnType(iMethod);

		final StringBuffer sbPara = new StringBuffer();
		sbPara.append("[");
		final Iterator<String> it = para.iterator();
		while (it.hasNext()) {
			final String s = it.next();
			if (it.hasNext()) {
				sbPara.append("'" + s + "'" + ",");
			} else {
				sbPara.append("'" + s + "'");
			}
		}
		sbPara.append("]");

		return String.format("%s('%s','%s','%s','%s',%s)", METHOD, packagename, classname, methodname, returntype,
				sbPara.toString());
	}

	/**
	 * Getting the first known object name, else return {@link #STANDARD_SHAPENAME}.
	 * 
	 * @param extendedData
	 * @return name as string
	 * @author BenjaminL
	 */
	public static String createNameforNewEnsemble(final Map<?, ?> extendedData) {
		// getting the first known object name
		for (final Object key : extendedData.keySet()) {
			final Object o = extendedData.get(key);

			final String name = Resolver.getElementNameFromObject(o);
			if (!name.equals("")) {
				return name;
			}
		}
		return STANDARD_SHAPENAME;
	}

	private static String createPackageQuery(final Object draggedElement) {
		return String.format("%s('%s')", PACKAGE, Resolver.resolveFullyQualifiedPackageName(draggedElement));
	}

	/**
	 * Creates a List that contains for all Java Files in map an entry: e.g.: <LI>package:
	 * package(&#60PACKAGENAME>) <LI>
	 * class: class_with_members(&#60PACKAGENAME>,&#60PACKAGENAME>.&#60CLASSNAME>)<LI>method:
	 * method(&#60PACKAGENAME>,&#60PACKAGENAME>.&#60CLASSNAME>,'&#60init>' OR
	 * &#60METHODNAME>,&#60RETURNTYPES>,&#60PARAMETERTYPES>) <LI>field:
	 * field(&#60PACKAGENAME>,&#60PACKAGENAME>.&#60CLASSNAME>,&#60FIELDNAME>,&#60FIELDTYPE>)
	 * 
	 * @param eventData
	 *            Extended data from the DnD event request.
	 * @return query Returns the query for the dropped files.
	 * @author Benjamin Lück
	 */
	private static List<String> createQueryFromDNDobjects(final Map<String, Object> eventData) {
		final LinkedList<String> list = new LinkedList<String>();
		for (final String key : eventData.keySet()) {
			final Object draggedElement = eventData.get(key);

			if (draggedElement instanceof IPackageFragment) {
				list.add(createPackageQuery(draggedElement));
			} else if (draggedElement instanceof ICompilationUnit) {
				list.add(createClassQueryFromCompilationUnit(draggedElement));
			} else if (draggedElement instanceof IClassFile) {
				list.add(createClassQueryFromClassFile(draggedElement));
			} else if (draggedElement instanceof IMethod) {
				list.add(createMethodQuery(draggedElement));
			} else if (draggedElement instanceof IType) {
				list.add(createTypeQuery(draggedElement));
			} else if (draggedElement instanceof IField) {
				list.add(createFieldQuery(draggedElement));
			} else if (draggedElement instanceof IPackageFragmentRoot) {
				list.addAll(createJARQuery(draggedElement));
			}
		}
		return list;
	}

	/**
	 * 
	 * @param data
	 * @return Returns the Query created from the data of a drop event.
	 * @author BenjaminL
	 */
	public static String createQueryFromRequestData(final Map<String, Object> data) {
		return createQueryFromRequestData(data, "");
	}

	/**
	 * Creates a new Query from the data of the drop event under consideration of the old Query.
	 * 
	 * @param data
	 * @param oldQuery
	 *            Old Query of the model element.
	 * @return Returns the created query.
	 * @author BenjaminL
	 */
	public static String createQueryFromRequestData(final Map<String, Object> data, final String oldQuery) {
		String newQuery = oldQuery;

		if (newQuery == null || (newQuery.equals(EMPTY) && data.size() > 0)) {
			newQuery = "";
		} else if (newQuery.trim().toLowerCase().equals(DERIVED)) {
			return newQuery;
		} else if (newQuery.trim().length() > 0) {
			newQuery += QUERY_DELIMITER;
		}

		final List<String> queries = createQueryFromDNDobjects(data);

		// extending the old Query
		if (queries != null) {
			for (final String query : queries) {
				newQuery += String.format("%s\n%s", query, QUERY_DELIMITER);
			}
		}

		// delete last query delimiter
		if (newQuery.endsWith(QUERY_DELIMITER)) {
			newQuery = newQuery.substring(0, newQuery.length() - QUERY_DELIMITER.length() - 1);
		}

		if (newQuery.equals("")) {
			return newQuery;
		} else {
			return newQuery + "\n";
		}
	}

	private static String createTypeQuery(final Object draggedElement) {
		final IType type = (IType) draggedElement;
		final ICompilationUnit cU = type.getCompilationUnit();
		final IClassFile cF = type.getClassFile();
		if (cU != null) {
			final String packagename = Resolver.resolveFullyQualifiedPackageName(cU);
			final String classname = Resolver.resolveClassName(type);
			return String.format("%s('%s','%s')", CLASS_WITH_MEMBERS, packagename, classname);
		} else {
			final String packagename = Resolver.resolveFullyQualifiedPackageName(cF);
			final String classname = Resolver.resolveClassName(type);
			return String.format("%s('%s','%s')", CLASS_WITH_MEMBERS, packagename, classname);
		}

	}

	private QueryBuilder() {

	}

}

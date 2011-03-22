/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;

import de.tud.cs.st.vespucci.diagram.dnd.JavaType.Resolver;

/**
 * A Class which provides static tools for supporting DnD
 * 
 * @author MalteV
 * @author BenjaminL
 */
public class QueryBuilder {
	// constants for the querybuilder
	public final static String PACKAGE = "package";
	public final static String CLASS_WITH_MEMBERS = "class_with_members";
	public final static String CLASS = "class";
	public final static String METHOD = "method";
	public final static String FIELD = "field";
	private static final String QUERY_DELIMITER = " or ";
	private static final String DERIVED = "derived";
	private static final String STANDARD_SHAPENAME = "A dynamic name";

	/**
	 * creates a new Query from the data of a drop event
	 * 
	 * @param map
	 *            data of the drop event
	 * @return new query
	 * @author BenjaminL
	 */
	public static String createQueryForAMapOfIResource(Map<String, Object> map) {
		return createQueryForAMapOfIResource(map, "");
	}

	/**
	 * creates a new Query from the data of the drop event under consideration
	 * of the old Query
	 * 
	 * @param map
	 *            data of the drop event
	 * @param oldQuery
	 *            old Query of the model element
	 * @return new query
	 * @author BenjaminL
	 */
	public static String createQueryForAMapOfIResource(Map<String, Object> map,
			String oldQuery) {

		if (oldQuery == null || (oldQuery.equals("empty") && map.size() > 0))
			oldQuery = "";
		else if (oldQuery.trim().toLowerCase().equals(DERIVED))
			return oldQuery;
		else if (oldQuery.length() > 0)
			oldQuery += QUERY_DELIMITER;

		String res = oldQuery;

		// translate all DND objects to query
		List<String> queries = createQueryFromDNDobjects(map);

		// extending the old Query
		if (queries != null) {
			for (String s : queries) {
				res = res + s + "\n" + QUERY_DELIMITER;
			}
		} else {
		}
		if (res.endsWith(QUERY_DELIMITER))
											
			res = res.substring(0, res.length() - QUERY_DELIMITER.length() - 1);

		if (res.equals(""))
			return res;
		else
			return res + "\n";
	}

	/**
	 * Creates a List that contains for all Java Files in map an entry: e.g.:
	 * package: package(<PACKAGENAME>) class:
	 * class_with_members(<PACKAGENAME>,<PACKAGENAME>.<CLASSNAME>) method:
	 * method(<PACKAGENAME>,<PACKAGENAME>.<CLASSNAME>,'<init>' OR
	 * <METHODNAME>,<RETURNTYPES>,<PARAMETERTYPES>) field:
	 * field(<PACKAGENAME>,<PACKAGENAME>.<CLASSNAME>,<FIELDNAME>,<FIELDTYPE>)
	 * 
	 * @param map
	 * @return query list
	 * @author BenjaminL
	 */
	private static List<String> createQueryFromDNDobjects(
			Map<String, Object> map) {
		LinkedList<String> list = new LinkedList<String>();

		for (String key : map.keySet()) {
			Object o = map.get(key);

			// package...
			if (o instanceof IPackageFragment) {
				key = PACKAGE + "('"
						+ Resolver.getFQPackageNameFromIxxx(o, key) + "')";
				list.add(key);
			} else if (o instanceof ICompilationUnit) {
				// CLASS
				String packagename = Resolver.getFQPackageNameFromIxxx(o, key);
				String classname = Resolver.getClassnamefromIxxx(o, key);
				if (packagename.equals(""))
					key = CLASS_WITH_MEMBERS + "('','" + classname + "')";
				else
					key = CLASS_WITH_MEMBERS + "('" + packagename + "','"
							+ classname + "')";
				list.add(key);
			} else if (o instanceof IMethod) {
				// METHOD
				IMethod method = (IMethod) o;
				String packagename = Resolver.getFQPackageNameFromIxxx(o, key);
				String classname = Resolver.getClassnamefromIxxx(o, key);
				String methodname = Resolver.getMethodnameFromMethod(method);
				List<String> para = Resolver
						.getParameterTypesFromMethod(method);
				StringBuffer sbPara = new StringBuffer();
				String returntype = Resolver.getReturnTypeFromIxxx(method, key);

				sbPara.append("[");
				Iterator<String> it = para.iterator();
				while (it.hasNext()) {
					String s = it.next();
					if (it.hasNext())
						sbPara.append("'" + s + "'" + ",");
					else
						sbPara.append("'" + s + "'");
				}
				sbPara.append("]");

				key = METHOD + "('" + packagename + "','" + classname + "','"
						+ methodname + "','" + returntype + "',"
						+ sbPara.toString() + ")";
				list.add(key);
			} else if (o instanceof IType) {
				// IType
				IType type = (IType) o;
				ICompilationUnit cU = type.getCompilationUnit();
				String packagename = Resolver.getFQPackageNameFromIxxx(cU, key);
				String classname = Resolver.getClassnamefromIxxx(type, key);
				key = CLASS_WITH_MEMBERS + "('" + packagename + "','"
						+ classname + "')";
				list.add(key);
			} else if (o instanceof IField) {
				// FIELD
				IField field = (IField) o;
				String packagename = Resolver.getFQPackageNameFromIxxx(o, key);
				String classname = Resolver.getClassnamefromIxxx(o, key);
				String fieldname = field.getElementName();
				String type = Resolver.getFQFieldTypeName(field);

				key = FIELD + "('" + packagename + "','" + classname + "','"
						+ fieldname + "','" + type + "')";
				list.add(key);
			} else if (o instanceof IPackageFragmentRoot) {
				List<String> packages = Resolver
						.getPackagesFromPFR((IPackageFragmentRoot) o);
				for (String s : packages) {
					key = PACKAGE + "('" + s + "')";
					list.add(key);
				}
			}
		}
		return list;

	}

	/**
	 * getting the first known object name - else return "A dynamic name"
	 * 
	 * @param extendedData
	 * @return name as string
	 * @author BenjaminL
	 */
	public static Object createNameforNewEnsemble(Map<?, ?> extendedData) {
		// getting the first known object name
		for (Object key : extendedData.keySet()) {
			Object o = extendedData.get(key);

			String tmp = Resolver.getElementNameFromObject(o);
			if (!tmp.equals(""))
				return tmp;
		}
		return STANDARD_SHAPENAME;
	}

	public static boolean isProcessable(Map<String, Object> extendedData) {
		return Resolver.isProcessable(extendedData);
	}

}

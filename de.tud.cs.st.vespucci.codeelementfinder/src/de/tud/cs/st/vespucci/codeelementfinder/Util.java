/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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
 *     Universitiät Darmstadt nor the names of its contributors may be used to
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
package de.tud.cs.st.vespucci.codeelementfinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi.ClassDeclaration;
import de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi.FieldDeclaration;
import de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi.MethodDeclaration;
import de.tud.cs.st.vespucci.codeelementfinder.interfaces.spi.Statement;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;

/**
 * Provide functionalities that is need to find ICodeElemnets
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
@SuppressWarnings("restriction")
public class Util {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	private static Map<String, String> primitiveTypeTable;

	/**
	 * Creates string pattern for an ICodeElement
	 * 
	 * @see org.eclipse.jdt.core.search.SearchPattern.createPattern(String, int,
	 *      int, int)
	 * 
	 * @param codeElement
	 *            ICodeElement string pattern should be created for
	 * @return StringPattern, if it is not possible to create a string pattern
	 *         an empty string is returned
	 */
	public static String createStringPattern(ICodeElement codeElement) {
		String packagePrefix = codeElement.getPackageIdentifier();
		packagePrefix = packagePrefix.replace("/", ".");
		if (!packagePrefix.equals("")) {
			packagePrefix += ".";
		}

		if (codeElement instanceof IClassDeclaration) {
			IClassDeclaration classDeclaration = (IClassDeclaration) codeElement;
			return packagePrefix + classDeclaration.getSimpleClassName();
		}
		if (codeElement instanceof IMethodDeclaration) {
			IMethodDeclaration methodDeclaration = (IMethodDeclaration) codeElement;
			if (methodDeclaration.getMethodName().contains("<")) {
				if (methodDeclaration.getMethodName().contains("<init")) {
					return packagePrefix
							+ methodDeclaration.getSimpleClassName();
				} else {
					return packagePrefix
							+ methodDeclaration.getSimpleClassName();
				}
			}
			return packagePrefix + methodDeclaration.getSimpleClassName() + "."
					+ methodDeclaration.getMethodName();
		}
		if (codeElement instanceof IFieldDeclaration) {
			IFieldDeclaration fieldDeclaration = (IFieldDeclaration) codeElement;
			return packagePrefix + fieldDeclaration.getSimpleClassName() + "."
					+ fieldDeclaration.getFieldName();
		}
		if (codeElement instanceof IStatement) {
			// string pattern for IStatement is the same than for the
			// corresponding IClassDeclaration
			IStatement statement = (IStatement) codeElement;
			IClassDeclaration classDeclaration = new ClassDeclaration(
					statement.getPackageIdentifier(),
					statement.getSimpleClassName());

			return createStringPattern(classDeclaration);
		}
		return "";
	}

	/**
	 * Creates the information what should be searched for out of an given
	 * ICodeelement
	 * 
	 * @see org.eclipse.jdt.core.search.IJavaSearchConstants
	 * 
	 * @param codeElement
	 *            ICodeElement that is looked for
	 * @return SearchFor constant
	 */
	public static int createSearchFor(ICodeElement codeElement) {
		if ((codeElement instanceof IClassDeclaration)
				|| (codeElement instanceof IStatement)) {
			return IJavaSearchConstants.CLASS_AND_INTERFACE;
		}
		if (codeElement instanceof IMethodDeclaration) {
			if (((IMethodDeclaration) codeElement).getMethodName().equals(
					"<init>")) {
				return IJavaSearchConstants.CONSTRUCTOR;
			}
			return IJavaSearchConstants.METHOD;
		}
		if (codeElement instanceof IFieldDeclaration) {
			return IJavaSearchConstants.FIELD;
		}
		return 0;
	}

	/**
	 * Creates stack with the prioritize search items for an ICodeElement
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	public static Stack<ICodeElement> createSearchTryStack(
			ICodeElement codeElement) {
		List<ICodeElement> tempSearchItems = new LinkedList<ICodeElement>();

		if (codeElement instanceof IClassDeclaration) {
			tempSearchItems = createSearchItems((IClassDeclaration) codeElement);
		} else if (codeElement instanceof IStatement) {
			tempSearchItems = createSearchItems((IStatement) codeElement);
			tempSearchItems.addAll(createMinimumSearchItems(codeElement));
		} else if (codeElement instanceof IMethodDeclaration) {
			tempSearchItems = createSearchItems((IMethodDeclaration) codeElement);
			tempSearchItems.addAll(createMinimumSearchItems(codeElement));
		} else if (codeElement instanceof IFieldDeclaration) {
			tempSearchItems = createSearchItems((IFieldDeclaration) codeElement);
			tempSearchItems.addAll(createMinimumSearchItems(codeElement));
		}

		// List --> Stack

		Stack<ICodeElement> searchItems = new Stack<ICodeElement>();
		for (int i = tempSearchItems.size() - 1; i >= 0; i--) {
			searchItems.push(tempSearchItems.get(i));
		}

		return searchItems;
	}

	/**
	 * Creates list with the prioritize search items for an ICodeElement
	 * containing all possible search items to find the contained class
	 * 
	 * The created search items are the worst approach for each ICodeElement.
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return List with the prioritize search items
	 */
	private static List<ICodeElement> createMinimumSearchItems(
			ICodeElement codeElement) {
		List<ICodeElement> result = new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = null;
		String[] t = className.replace("$", ":").split(":");
		for (int i = countDollars(className); i >= 0; i--) {
			String res = t[0];
			for (int j = 1; j <= i; j++) {
				res += "$" + t[j];
			}
			results = createPossibleClassNames(res);
			for (String string : results) {
				result.add(new ClassDeclaration(codeElement
						.getPackageIdentifier(), string));
			}
		}
		return result;
	}

	/**
	 * Creates list with the prioritize search items for an IClassDeclaration
	 * containing all possible search items to find the IClassDeclaration
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return List with the prioritize search items
	 */
	public static List<ICodeElement> createSearchItems(
			IClassDeclaration codeElement) {
		List<ICodeElement> result = new LinkedList<ICodeElement>();

		// Create an IClassDeclaration for each possible class name (variable
		// length)
		String className = codeElement.getSimpleClassName();
		String[] results = null;
		String[] t = className.replace("$", ":").split(":");
		for (int i = countDollars(className); i >= 0; i--) {
			String res = t[0];
			for (int j = 1; j <= i; j++) {
				res += "$" + t[j];
			}
			results = createPossibleClassNames(res);
			for (String string : results) {
				result.add(new ClassDeclaration(codeElement
						.getPackageIdentifier(), string));
			}
		}
		return result;
	}

	/**
	 * Creates list with the prioritize search items for an IStatement
	 * containing all possible search items to find the IStatement
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return List with the prioritize search items
	 */
	public static List<ICodeElement> createSearchItems(IStatement codeElement) {
		List<ICodeElement> result = new LinkedList<ICodeElement>();

		// Create an IMethodDeclaration for each possible class name
		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);
		for (String string : results) {
			result.add(new Statement(codeElement.getPackageIdentifier(),
					string, codeElement.getLineNumber()));
		}

		return result;
	}

	/**
	 * Creates list with the prioritize search items for an IMethodDeclaration
	 * containing all possible search items to find the IMethodDeclaration
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return List with the prioritize search items
	 */
	public static List<ICodeElement> createSearchItems(
			IMethodDeclaration codeElement) {
		List<ICodeElement> result = new LinkedList<ICodeElement>();

		// Create an IMethodDeclaration for each possible class name
		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);
		for (String string : results) {
			result.add(new MethodDeclaration(
					codeElement.getPackageIdentifier(), string, codeElement
							.getMethodName(), codeElement
							.getReturnTypeQualifier(), codeElement
							.getParameterTypeQualifiers()));
		}

		return result;
	}

	/**
	 * Creates list with the prioritize search items for an IFieldDeclaration
	 * containing all possible search items to find the IFieldDeclaration
	 * 
	 * @param codeElement
	 *            ICodeElement stack create for
	 * @return List with the prioritize search items
	 */
	public static List<ICodeElement> createSearchItems(
			IFieldDeclaration codeElement) {
		List<ICodeElement> result = new LinkedList<ICodeElement>();

		// Create an IFieldDeclaration for each possible class name
		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);
		for (String string : results) {
			result.add(new FieldDeclaration(codeElement.getPackageIdentifier(),
					string, codeElement.getFieldName(), codeElement
							.getTypeQualifier()));
		}

		return result;
	}

	/**
	 * Create array of possible ClassNames out of a given name of a class
	 * 
	 * For example:<br>
	 * <code>className: A$B$C</code> <br>
	 * result:<br>
	 * <code>[A$B$C, <br> A$B.C,<br> A.B$C, <br>A.B.C]</code> <br>
	 * <br>
	 * <code>className: A</code> <br>
	 * result:<br>
	 * <code>[A]</code>
	 * 
	 * @param className
	 *            Class name possibilities should be created for
	 * @return Array of all class names
	 */
	private static String[] createPossibleClassNames(String className) {
		int numOfDollars = countDollars(className);
		if (numOfDollars == 0) {
			String[] results = new String[1];
			results[0] = className;
			return results;
		}
		int[][] temp = fillArrayBinary(numOfDollars);
		String[] results = new String[temp.length];

		for (int i = 0; i < results.length; i++) {
			int[] line = temp[i];
			String result = "";
			String[] t = className.replace("$", ":").split(":");
			for (int j = 0; j < line.length; j++) {
				result += t[j];
				if (line[j] == 0) {
					result += ".";
				} else {
					result += "$";
				}
			}
			result += t[t.length - 1];
			results[i] = result;
		}
		return results;
	}

	/**
	 * Count the occurrence of '$' in a string
	 * 
	 * @param string
	 * @return Number of '$'
	 */
	private static int countDollars(String string) {
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '$') {
				count++;
			}
		}
		return count;
	}

	/**
	 * Create a two dimensional array with all possible binary configurations <br>
	 * For example:<br>
	 * <code>count = 2</code> <br>
	 * result:<br>
	 * <code>[[1, 1], <br> [1, 0],<br> [0, 1], <br>[0, 0]]</code>
	 * 
	 * @param size
	 *            Size of the array
	 * @return Array with all possible configurations
	 */
	private static int[][] fillArrayBinary(int size) {
		int[][] array = new int[(int) Math.pow(2, size)][size];

		for (int i = 0; i < Math.pow(2, size); i++) {
			char[] t = Integer.toBinaryString(i).toCharArray();
			for (int j = t.length - 1; j >= 0; j--) {
				array[array.length - 1 - i][size - 1 - j] = Integer
						.parseInt(String.valueOf(t[t.length - 1 - j]));
			}
		}
		return array;
	}

	/**
	 * Returns the type qualifier modified to an version used for visualizations <br>
	 * For example:<br>
	 * Ljava/lang/String; --> String [[I; --> int[][]
	 * 
	 * @param typQualifier
	 *            Other representation of type qualifier
	 * @return Modified type Qualifier
	 */
	public static String createSimpleTypeText(String typQualifier) {
		String label = "";

		int dimensionOfArray = numberOfArraySymbols(typQualifier);

		String innerTypQualifier = typQualifier.substring(dimensionOfArray);
		if (innerTypQualifier.contains("/")) {
			innerTypQualifier = innerTypQualifier.substring(
					innerTypQualifier.lastIndexOf("/") + 1,
					innerTypQualifier.lastIndexOf(";"));
			label = innerTypQualifier;
		} else {
			if (primitiveTypeTable == null) {
				fillPrimitiveTypeTable();
			}
			for (Entry<String, String> entry : primitiveTypeTable.entrySet()) {
				if (entry.getValue().equals(innerTypQualifier)) {
					label = entry.getKey();
					break;
				}
			}
		}
		while (dimensionOfArray > 0) {
			label += "[]";
			dimensionOfArray--;
		}
		return label;
	}

	/**
	 * Returns the type qualifier modified out of a given type qualifier
	 * representation and an IType element <br>
	 * For example:<br>
	 * QString; --> Ljava/lang/String;
	 * 
	 * @param signatur
	 *            Other representation of type qualifier
	 * @param declaringType
	 *            Type which declared the type
	 * @return Type qualifier
	 */
	public static String createTypQualifier(String signatur, IType declaringType) {

		if (primitiveTypeTable == null) {
			fillPrimitiveTypeTable();
		}

		String typeQualifier = "";

		int dimensionOfArray = numberOfArraySymbols(signatur);

		String arraySymbols = signatur.substring(0, dimensionOfArray);
		String innerTypQualifier = signatur.substring(dimensionOfArray);

		try {
			innerTypQualifier = JavaModelUtil.getResolvedTypeName(
					innerTypQualifier, declaringType);

			if (innerTypQualifier.contains(".")) {
				// . --> /
				innerTypQualifier = innerTypQualifier.replace('.', '/');
				// add L at the beginning
				innerTypQualifier = "L" + innerTypQualifier.replace('.', '/')
						+ ";";

			} else {
				innerTypQualifier = primitiveTypeTable.get(innerTypQualifier);
			}

			// add the arraySymbols at the beginning
			typeQualifier = arraySymbols + innerTypQualifier;

		} catch (Exception e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
					e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return typeQualifier;
	}

	/**
	 * Count the occurrence of '[' at the beginning of a given typeQualifier
	 * 
	 * @param typeQualifier
	 * @return Number of '[' at the beginning
	 */
	private static int numberOfArraySymbols(String typeQualifier) {
		// filter '[' we need them later
		int dimensionOfArray = 0;
		for (int i = 0; i < typeQualifier.length(); i++) {
			if (typeQualifier.charAt(i) == '[') {
				dimensionOfArray++;
			} else {
				break;
			}
		}
		return dimensionOfArray;
	}

	private static void fillPrimitiveTypeTable() {
		primitiveTypeTable = new HashMap<String, String>();
		primitiveTypeTable.put("byte", "B");
		primitiveTypeTable.put("char", "C");
		primitiveTypeTable.put("double", "D");
		primitiveTypeTable.put("float", "F");
		primitiveTypeTable.put("int", "I");
		primitiveTypeTable.put("long", "J");
		primitiveTypeTable.put("short", "S");
		primitiveTypeTable.put("boolean", "Z");
		primitiveTypeTable.put("void", "V");
	}

	public static void printICodeElement(ICodeElement codeElement) {
		System.out.println("------------------" + codeElement.getClass()
				+ "----------------------------");
		System.out.println("PackageIdentifier: "
				+ codeElement.getPackageIdentifier());
		System.out.println("SimpleClassName: "
				+ codeElement.getSimpleClassName());
		if (codeElement instanceof IFieldDeclaration) {
			System.out.println("FieldName: "
					+ ((IFieldDeclaration) codeElement).getFieldName());
		}
		if (codeElement instanceof IMethodDeclaration) {
			System.out.println("MethodeName: "
					+ ((IMethodDeclaration) codeElement).getMethodName());
			System.out.println("ReturnQualifier: "
					+ ((IMethodDeclaration) codeElement)
							.getReturnTypeQualifier());
			System.out.println("ParameterQualifier:");
			for (String string : ((IMethodDeclaration) codeElement)
					.getParameterTypeQualifiers()) {
				System.out.println("  - " + string);
			}
		}
		System.out.println("----------------------------------------------");
	}
}

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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.information.interfaces.spi.ClassDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.FieldDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.MethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

/**
 * Provide functionalities that is need to find ICodeElemnets
 * 
 * @author 
 */
@SuppressWarnings("restriction")
public class Util {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	private static Map<String,String> primitiveTypeTable;

	/**
	 * Creates string pattern for an ICodeElement
	 * 
	 * @param sourceElement ICodeElement string pattern should be created for
	 * @return StringPattern
	 */
	public static String createStringPattern(ICodeElement sourceElement) {
		if (sourceElement instanceof IClassDeclaration){
			IClassDeclaration classDeclaration = (IClassDeclaration) sourceElement;
			return classDeclaration.getPackageIdentifier().replace("/", ".") + "." + classDeclaration.getSimpleClassName();
		}else if (sourceElement instanceof IMethodDeclaration){
			IMethodDeclaration methodDeclaration = (IMethodDeclaration) sourceElement;
			if (methodDeclaration.getMethodName().contains("<")){
				if (methodDeclaration.getMethodName().contains("<init>")){
					return methodDeclaration.getPackageIdentifier().replace("/", ".") + "." + methodDeclaration.getSimpleClassName();
				}else{
					// <clinit> case must to be fixed in an other way
					return methodDeclaration.getPackageIdentifier().replace("/", ".") + "." + methodDeclaration.getSimpleClassName();
				}
			}
			
			return methodDeclaration.getPackageIdentifier().replace("/", ".") + "." + methodDeclaration.getSimpleClassName() + "." + methodDeclaration.getMethodName();
		}else if (sourceElement instanceof IFieldDeclaration){
			IFieldDeclaration fieldDeclaration = (IFieldDeclaration) sourceElement;
			return fieldDeclaration.getPackageIdentifier().replace("/", ".") + "." + fieldDeclaration.getSimpleClassName() + "."+ fieldDeclaration.getFieldName();
		}
		return null;
	}

	/**
	 * Creates the information what should be searched for out of an given
	 * ICodeelement
	 * 
	 * @see org.eclipse.jdt.core.search.IJavaSearchConstants
	 * 
	 * @param sourceElement ICodeElement that is looked for
	 * @return SearchFor constant
	 */
	public static int createSearchFor(ICodeElement sourceElement) {
		if (sourceElement instanceof IClassDeclaration){
			return IJavaSearchConstants.CLASS_AND_INTERFACE;
		}else if (sourceElement instanceof IMethodDeclaration){
			if (((IMethodDeclaration) sourceElement).getMethodName().contains("<init")){
				return IJavaSearchConstants.CONSTRUCTOR;
			}
			return IJavaSearchConstants.METHOD;
		}else if (sourceElement instanceof IFieldDeclaration){
			return IJavaSearchConstants.FIELD;
		}
		return 0;
	}

	/**
	 * Creates stack with the prioritize search items for an ICodeElement
	 * 
	 * @param codeElement ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	public static List<ICodeElement> createSearchTryStack(ICodeElement codeElement){
		if (codeElement instanceof IClassDeclaration){
			return createSearchTryStack((IClassDeclaration) codeElement);
		}else if (codeElement instanceof IMethodDeclaration){
			List<ICodeElement> temp = createSearchTryStack((IMethodDeclaration) codeElement);
			temp.addAll(createSearchTryClassStack(codeElement));
			return temp;
		}else if (codeElement instanceof IFieldDeclaration){
			List<ICodeElement> temp = createSearchTryStack((IFieldDeclaration) codeElement);
			temp.addAll(createSearchTryClassStack(codeElement));
			return temp;
		}
		return new LinkedList<ICodeElement>();
	}

	/**
	 * Creates stack with the prioritize search items for an ICodeElement
	 * containing all possible search items to find the contained class
	 * 
	 * @param codeElement ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	private static List<ICodeElement> createSearchTryClassStack(ICodeElement codeElement) {
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = null;
		String[] t = className.replace("$", ":").split(":");		
		for (int i = count$(className); i >= 0; i--){
			String res = t[0];
			for (int j = 1; j <= i; j++){
				res += "$" + t[j];
			}
			results = createPossibleClassNames(res);	
			for (String string : results) {
				result.add(new ClassDeclaration(codeElement.getPackageIdentifier(), string, null));
			}
		}
		return result;
	}

	/**
	 * Creates stack with the prioritize search items for an IClassDeclaration
	 * containing all possible search items to find the IClassDeclaration
	 * 
	 * @param codeElement ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	public static List<ICodeElement> createSearchTryStack(IClassDeclaration codeElement){
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = null;
		String[] t = className.replace("$", ":").split(":");		
		for (int i = count$(className); i >= 0; i--){
			String res = t[0];
			for (int j = 1; j <= i; j++){
				res += "$" + t[j];
			}
			results = createPossibleClassNames(res);	
			for (String string : results) {
				IClassDeclaration c = (IClassDeclaration) codeElement;
				String typeQualifier = c.getTypeQualifier();
				if (i != count$(className)){
					typeQualifier = null;
				}
				result.add(new ClassDeclaration(codeElement.getPackageIdentifier(), string, typeQualifier));
			}
		}
		return result;
	}

	/**
	 * Creates stack with the prioritize search items for an IMethodDeclaration
	 * containing all possible search items to find the IMethodDeclaration
	 * 
	 * @param codeElement ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	public static List<ICodeElement> createSearchTryStack(IMethodDeclaration codeElement){
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);	
		for (String string : results) {
			result.add(new MethodDeclaration(codeElement.getPackageIdentifier(), string, codeElement.getMethodName(), codeElement.getReturnTypeQualifier(), codeElement.getParameterTypeQualifiers()));
		}

		return result;
	}

	/**
	 * Creates stack with the prioritize search items for an IFieldDeclaration
	 * containing all possible search items to find the IFieldDeclaration
	 * 
	 * @param codeElement ICodeElement stack create for
	 * @return Stack with the prioritize search items
	 */
	public static List<ICodeElement> createSearchTryStack(IFieldDeclaration codeElement){
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);	
		for (String string : results) {
			result.add(new FieldDeclaration(codeElement.getPackageIdentifier(), string, codeElement.getFieldName(), codeElement.getTypeQualifier()));
		}

		return result;
	}

	/**
	 * Create array of possible ClassNames out of a given name of a class
	 * 
	 * @param className Class name possibilities should be created for
	 * @return Array of all class names
	 */
	private static String[] createPossibleClassNames(String className) {
		int numOf$ = count$(className);
		if (numOf$ == 0){
			String[] results = new String[1];
			results[0] = className;
			return results; 
		}
		int[][] temp = fillArrayBinary(numOf$);
		String[] results = new String[temp.length];

		for (int i = 0; i < results.length; i++){
			int[] line = temp[i];
			String result = "";
			String[] t = className.replace("$", ":").split(":");
			for (int j = 0; j < line.length; j++){
				result += t[j];
				if (line[j] == 0){
					result+= ".";
				}else{
					result+= "$";
				}
			}
			result += t[t.length-1];	
			results[i] = result;
		}
		return results;
	}

	/**
	 * Count $ in a string
	 * 
	 * @param string String $ should be count in
	 * @return Number of $ in the given string
	 */
	private static int count$(String string) {
		return string.replace("$", ":").split(":").length - 1;
	}

	/**
	 * Create a two dimensional array for all binary configurations
	 * <br>
	 * For example:<br>
	 * <code>count = 2</code>
	 * <br>
	 * return:<br>
	 * <code>1 1</code><br>
	 * <code>1 0</code><br>
	 * <code>0 1</code><br>
	 * <code>0 0</code><br>
	 * 
	 * @param count Width of the array
	 * @return Array with all possible configurations
	 */
	private static int[][] fillArrayBinary(int count){
		int[][] array = new int[(int) Math.pow(2, count)][count];

		for (int i = 0; i < Math.pow(2, count); i++){
			char[] t = Integer.toBinaryString(i).toCharArray();
			for (int j = t.length-1; j >= 0; j--){
				array[array.length -1 -i][count-1-j] = Integer.parseInt(String.valueOf(t[t.length -1 - j]));
			}
		}
		return array;
	}

	/**
	 * METHODE MUSS VERSCHOBEN WERDEN, HAT HIER NIX ZU SUCHEN.
	 * WIRD AUS LABELPROVIDER VON VIEWS AUFGERUFEN	 * 
	 * 
	 * Returns the type qualifier modified out of a given type qualifier representation
	 * <br>
	 * For example:<br>
	 * QString; --> Ljava/lang/String;
	 * 
	 * @param typQualifier Other representation of type qualifier
	 * @return Modified type Qualifier
	 */
	public static String createSimpleTypeText(String typQualifier){
		String label= "";

		int dimensionOfArray = numberOfArraySymbol(typQualifier);

		String innerTypQualifier = typQualifier.substring(dimensionOfArray);
		if (innerTypQualifier.contains("/")){
			innerTypQualifier = innerTypQualifier.substring(innerTypQualifier.lastIndexOf("/") + 1, innerTypQualifier.lastIndexOf(";"));
			label = innerTypQualifier;
		}else{
			if (primitiveTypeTable == null){
				fillPrimitiveTypeTable();
			}

			for (Entry<String, String> entry : primitiveTypeTable.entrySet()) {
				if (entry.getValue().equals(innerTypQualifier)){
					label = entry.getKey();
					break;
				}
			}
		}
		while (dimensionOfArray > 0){
			label += "[]";
			dimensionOfArray--;
		}
		return label;
	}

	/**
	 * Returns the type qualifier modified out of a given type qualifier representation and an IType element
	 * <br>
	 * For example:<br>
	 * QString; --> Ljava/lang/String;
	 * 
	 * @param signatur Other representation of type qualifier
	 * @param declaringType Type which declared the type
	 * @return Type qualifier
	 */
	public static String createTypQualifier(String signatur, IType declaringType) {		

		if (primitiveTypeTable == null){
			fillPrimitiveTypeTable();
		}

		String typeQualifier = "";

		int dimensionOfArray = numberOfArraySymbol(signatur);

		String arraySymbols = signatur.substring(0, dimensionOfArray);
		String innerTypQualifier = signatur.substring(dimensionOfArray);

		try {
			innerTypQualifier = JavaModelUtil.getResolvedTypeName(innerTypQualifier, declaringType);

			if (innerTypQualifier.contains(".")){
				// . --> /
				innerTypQualifier = innerTypQualifier.replace('.', '/');
				// add L at the beginning
				innerTypQualifier = "L" + innerTypQualifier.replace('.', '/') + ";";

			}else{
				innerTypQualifier = primitiveTypeTable.get(innerTypQualifier);
			}			

			// add the arraySymbols at the beginning
			typeQualifier = arraySymbols + innerTypQualifier;

		} catch (Exception e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return typeQualifier;
	}

	private static int numberOfArraySymbol(String signatur) {
		// filter '[' we need them later
		int dimensionOfArray = 0;
		for (int i = 0; i < signatur.length(); i++){
			if (signatur.charAt(i) == '[' ){
				dimensionOfArray++;
			}else{
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

	public static String removeLastDollarSequence(String simpleClassName) {
		return simpleClassName.substring(0, simpleClassName.lastIndexOf("$"));
	}

	public static String getLastDollarSequence(String simpleClassName) {
		return simpleClassName.substring(simpleClassName.lastIndexOf("$") + 1);
	}
	
	public static void printICodeElement(ICodeElement codeElement){
		System.out.println("------------------"+codeElement.getClass()+"----------------------------");
			System.out.println("PackageIdentifier: " + codeElement.getPackageIdentifier());
			System.out.println("SimpleClassName: " + codeElement.getSimpleClassName());
			if (codeElement instanceof IFieldDeclaration){
				System.out.println("FieldName: " + ((IFieldDeclaration) codeElement).getFieldName());
			}
			if (codeElement instanceof IMethodDeclaration){
				System.out.println("MethodeName: " + ((IMethodDeclaration) codeElement).getMethodName());
				System.out.println("ReturnQualifier: " + ((IMethodDeclaration) codeElement).getReturnTypeQualifier());
				System.out.println("ParameterQualifier:");
				for (String string : ((IMethodDeclaration) codeElement).getParameterTypeQualifiers()) {
					System.out.println("  - " + string);
				}
			}
		System.out.println("----------------------------------------------");
	}

}

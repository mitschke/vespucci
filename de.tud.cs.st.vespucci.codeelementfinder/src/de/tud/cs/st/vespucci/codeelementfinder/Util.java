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

@SuppressWarnings("restriction")
public class Util {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";

	private static Map<String,String> primitiveTypeTable;


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

	public static List<ICodeElement> createSearchTryStack(IMethodDeclaration codeElement){
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);	
		for (String string : results) {
			result.add(new MethodDeclaration(codeElement.getPackageIdentifier(), string, codeElement.getMethodName(), codeElement.getReturnTypeQualifier(), codeElement.getParameterTypeQualifiers()));
		}

		return result;
	}

	public static List<ICodeElement> createSearchTryStack(IFieldDeclaration codeElement){
		List<ICodeElement> result= new LinkedList<ICodeElement>();

		String className = codeElement.getSimpleClassName();
		String[] results = createPossibleClassNames(className);	
		for (String string : results) {
			result.add(new FieldDeclaration(codeElement.getPackageIdentifier(), string, codeElement.getFieldName(), codeElement.getTypeQualifier()));
		}

		return result;
	}


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

	private static int count$(String className) {
		return className.replace("$", ":").split(":").length - 1;
	}

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

			// add the arraySymbols at the beginning and ';' at the end
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

}

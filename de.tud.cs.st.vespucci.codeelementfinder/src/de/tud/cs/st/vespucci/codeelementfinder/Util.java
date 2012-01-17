package de.tud.cs.st.vespucci.codeelementfinder;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

public class Util {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.codeelementfinder";
	
	private static Map<String,String> primitiveTypeTable;
	
	public static String createTypQualifier(String signatur, IType declaringType) {
		if (primitiveTypeTable == null){
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
		
		String typeQualifier = "";
		
		// filter '[' we need them later
		int dimensionOfArray = 0;
		for (int i = 0; i < signatur.length(); i++){
			if (signatur.charAt(i) == '[' ){
				dimensionOfArray++;
			}else{
				break;
			}
		}
		
		String arraySymbols = signatur.substring(0, dimensionOfArray);
		String innerTypQualifier = signatur.substring(dimensionOfArray);
				
		try {
			innerTypQualifier = JavaModelUtil.getResolvedTypeName(innerTypQualifier, declaringType);
			
			if (innerTypQualifier.contains(".")){
				// . --> /
				innerTypQualifier = innerTypQualifier.replace('.', '/');
				// add L at the beginning
				innerTypQualifier = "L" + innerTypQualifier.replace('.', '/');
			}else{
				innerTypQualifier = primitiveTypeTable.get(innerTypQualifier);
			}			
			
			// add the arraySymbols at the beginning and ';' at the end
			typeQualifier = arraySymbols + innerTypQualifier + ";";
			
		} catch (Exception e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return typeQualifier;
	}
	
	public static String removeLastDollarSequence(String simpleClassName) {
		return simpleClassName.substring(0, simpleClassName.lastIndexOf("$"));
	}
	
	public static String getLastDollarSequence(String simpleClassName) {
		return simpleClassName.substring(simpleClassName.lastIndexOf("$") + 1);
	}
	
}

package de.tud.cs.st.vespucci.codeelementfinder;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

public class Debug {

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
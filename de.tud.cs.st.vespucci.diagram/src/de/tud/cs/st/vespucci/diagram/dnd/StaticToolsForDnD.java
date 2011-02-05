/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author MalteV
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitï¿½t Darmstadt
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
 *     Universitï¿½t Darmstadt nor the names of its contributors may be used to 
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.EMFPlugin.EclipsePlugin;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.util.ISourceAttribute;
import org.eclipse.ui.internal.Workbench;

/**
 * A Class witch provide static tools for supporting of DnD
 * @author MalteV
 *
 */
public class StaticToolsForDnD {
	//constants for the querybuilder
	public final static String PACKAGE = "package";
	public final static String CLASS_WITH_MEMBERS = "class_with_members";
	public final static String CLASS = "class";
	public final static String METHOD = "method";
	public final static String FIELD = "field";
	private static final String QUERY_DELIMITER = " or ";
	
	
	/**
	 * creates a new Query from the data of a drop event
	 * @param map data of the drop event
	 * @return new query
	 */
	public static String createQueryForAMapOfIResource(Map<String,Object> map){
		return createQueryForAMapOfIResource(map, "");
	}
	
	/**
	 *  creates a new Query from the Data of a drop event
	 *  under consideration of the old Query
	 * @param map data of the drop event
	 * @param oldQuery oldQuery of the model element
	 * @return new query
	 */
	public static String createQueryForAMapOfIResource(Map<String,Object> map, String oldQuery){
		
		if(oldQuery == null)
			oldQuery = "";
		if(oldQuery.equals("empty") && map.size()>0)
			oldQuery = "";
		if(oldQuery.length() > 0)
			oldQuery += QUERY_DELIMITER;
		
		String res = oldQuery;
		//find all Java files 
		List<String> classes = getJavasSourceCodeFiels(map);
		//find all Packages
		//List<String> pack = getJavaPackages(map);
		
		
		//extending the old Query
		if(classes != null)
			for(String s : classes){
				res = res + s + QUERY_DELIMITER;
			}
		
		/*for(String s : pack){
			res = res + s + ",";
		}*/
		if(res.length() >= QUERY_DELIMITER.length())
			res = res.substring(0, res.length()-QUERY_DELIMITER.length());
		return res + "\n";
	}
	
	/**
	 * Creates a List that contains for all Java Fils in map 
	 * an entry:
	 * class('PACKAGE OF JAVA FILE','JAVA FILE NAME')
	 * @param map 
	 * @return
	 */

	private static List<String> getJavasSourceCodeFiels(Map<String,Object> map){
		
		
		System.out.println("............");
		LinkedList<String> list = new LinkedList<String>();
		for(String key : map.keySet()){
			Object o = map.get(key);

			//package...
			if(o instanceof IPackageFragment){
				//System.out.println("Package --> " + key);
				key = key.substring(0,key.indexOf('[')).trim();
				key = PACKAGE + "(" + key + ")";
				list.add(key);
			}
			//class...
			if(o instanceof ICompilationUnit){
				//System.out.println("Class --> " + key);
				String classname = key.substring(0,key.indexOf('[')).trim();
				String packagename = key.substring(key.indexOf(PACKAGE),key.indexOf('\n',key.indexOf(PACKAGE)));
				key = CLASS_WITH_MEMBERS + "(" + packagename + "," + classname + ")";
				list.add(key);
			}//method
			if(o instanceof IMethod){
				System.out.println("Methode --> " + key);
				key = METHOD + "(" + getQueryFromMethod((IMethod)o) + ")";
				list.add(key);
			}
			
			if(o instanceof IField){
				System.out.println("Attribut --> " + key);
				key = FIELD + "(" + getQueryFromField((IField)o) + ")";
				list.add(key);
			}
		}
		return list;
		
		
		/*
		for(String key : map.keySet()){
			Object o = map.get(key);
			//if(o instanceof ISourceAttribute)
				//System.out.println("attribute");
			//if(o instanceof ISourceMethod)
				//System.out.println("methode");
			if(o instanceof IPackageFragment) 
				System.out.println("Package");
			if(o instanceof ICompilationUnit)
				System.out.println("sour code file");
				
			//"oldquery or class('voller packagename', klassenname)"
				
				
			if(o instanceof IField)
				System.out.println("Attribut");
			if(o instanceof IType)
				System.out.println("classe also class ... in einem file");
			if(o instanceof IMethod)
				System.out.println("Methode");
			if(o instanceof IPackageFragmentRoot) 
				System.out.println("jar package / src Ordner");
			if(o instanceof IProject)
				System.out.println("Projekt Ordner");
			if(o instanceof IClassFile)
				System.out.println("ClassFile");
			if(o instanceof IAdaptable) //trieft auch auf viele ander sachen zu
				System.out.println("Libaraycontainer");
			if(o instanceof IFile)
				System.out.println("file"); //je nach ausgewälter ansicht können file auch *.java datein sein
			if(o instanceof IFolder)
				System.out.println("folder");
			System.out.println(o.getClass());
			
	
		}
		return null;
		*/
	}
	
	private static String getQueryFromMethod(IMethod method) {
		//init vars
		String packagename = "";
		String classname = "";
		String methodname = "";
		String returntype = "";
		StringBuffer sbParaTypes = new StringBuffer();
		String types[];
		
		//System.out.println("decl    " + method.getDeclaringType());
		try {
			//getting package information
			IPackageDeclaration[] declarations = method.getCompilationUnit().getPackageDeclarations();
			if(declarations.length > 0)
			{
				packagename = declarations[0].getElementName(); 
			}
			
			//getting the classname
			classname = method.getDeclaringType().getFullyQualifiedName();
			
			//get methodname, set as <init> if it is the constructor
			if(method.isConstructor())
				methodname = "'<init>'";
			else
				methodname = method.getElementName();
			
			//getting returntype of the method
			returntype = method.getReturnType();	//returntype = Signature.toString(returntype);
			returntype = returntype.replaceAll(";", "");
			returntype = Signature.getSignatureSimpleName(method.getReturnType());
			//returntype = Signature.getSignatureQualifier(returntype);
			
			
			//System.out.println("aaaa     " + method.getTypeParameterSignatures());
			//getting parameters of the given method    //para-names unnecessary names = method.getParameterNames();
			sbParaTypes.append("[");
			for(String type : method.getParameterTypes()){
//				System.out.println(Signature.getSimpleName(type));
//				System.out.println(Signature.getSignatureQualifier(type));
//				System.out.println(Signature.getTypeErasure(type));
//				method.getCorrespondingResource();
				//JDT class -  JavaModelUtil.getResolvedTypeName //ausgang ungewiss
//				System.out.println(Signature.toString(Signature.getSimpleName(type)));
//				JavaModelUtil.getResolvedTypeName;
				
				//System.out.println("Try to resolve type: " + Signature.toString(Signature.getSimpleName(type)));
				LinkedList<String> tmpList = resolveElementToFQN(Signature.toString(Signature.getSimpleName(type)));
				for(String s : tmpList){
					//System.out.println("\tResolved Type: " + s);
				}
				if(tmpList.size()>1){
					System.out.println("Critical - do anything - TODO");
				}
				if(tmpList.size()<1)
					sbParaTypes.append("'" + Signature.getSimpleName(type) + "'");
				else
					sbParaTypes.append("'" + tmpList.getFirst() + "'");
				sbParaTypes.append(",");
				
				IJavaElement me = method;
			}
			if(sbParaTypes.charAt(0)==',')
				sbParaTypes.delete(sbParaTypes.length()-1,sbParaTypes.length());
			if(sbParaTypes.charAt(sbParaTypes.length()-1)==',')
				sbParaTypes.delete(sbParaTypes.length()-1,sbParaTypes.length());
			sbParaTypes.append("]");
			
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		//preparing return values
		packagename = "'" + packagename + "'";
		classname = "'" + classname + "'";
		methodname.replaceAll(";","");
		
		//rückgaben
//		System.out.println("Package   \t" +packagename);
//		System.out.println("Class     \t" +classname);
//		System.out.println("Method    \t" +methodname);
//		System.out.println("Returntype\t" +returntype);
//		System.out.println("Parameter \t" +sbParaTypes.toString());
		
		//System.out.println(packagename + "," + classname + "," + methodname + "," + returntype + "," + sbParaTypes.toString());
		return packagename + "," + classname + "," + methodname + "," + returntype + "," + sbParaTypes.toString();
	}

	private static LinkedList<String> resolveElementToFQN(String s){
		Package[] packages = java.lang.Package.getPackages();
		
		LinkedList<String> fqns = new LinkedList<String>();
	    for (Package aPackage : packages) {
	        try {
	            String fqn = aPackage.getName() + "." + s; //simpleName;
	            //System.out.println(fqn);
	            Class.forName(fqn);
	            fqns.add(fqn);
	        } catch (Exception e) {
	            // Ignore
	        }
	    }
	    return fqns;
	}

	
	private static String getQueryFromField(IField field) {
		//init vars
		String packagename = "";
		String classname = "";
		String fieldname = "";
		String type = "";
		
		fieldname = field.getElementName();
		try {
			type = field.getSource();
			String[] tmp = type.split(" ");
			if(tmp.length >=1)
				if(tmp[1].toLowerCase().equals("static"))
					type = tmp[3];
				else
					type = tmp[1];
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		
		//getting package information
		Object o = field.getParent().getParent();
		if(o instanceof ICompilationUnit){
			o = (ICompilationUnit) o;
			classname = ((ICompilationUnit) o).getElementName();
			o = ((ICompilationUnit) o).getParent();
			if(o instanceof IPackageFragment){
				packagename = ((IPackageFragment) o).getElementName();
			}
		}
		
		//getting the classname
		classname = field.getDeclaringType().getFullyQualifiedName();
			
		//preparing return values
		packagename = "'" + packagename + "'";
		classname = "'" + classname + "'";
		fieldname.replaceAll(";","");
		
		//System.out.println(packagename + "," + classname + "," + fieldname + "," + type);
		return packagename + "," + classname + "," + fieldname + "," + type;
	}
	
	/**
	 * Creates a List that contains for all Java Packages in map 
	 * as an entry:
	 * package('PACKAGE OF JAVA FILE')
	 * @param map
	 * @return
	 */
	private static List<String> getJavaPackages(Map<String,Object> map){
		return null;
	}

	public static Object createNameforNewEnsemble(Map extendedData) {
		// TODO Auto-generated method stub
		return "A dynamic name";
	}
}

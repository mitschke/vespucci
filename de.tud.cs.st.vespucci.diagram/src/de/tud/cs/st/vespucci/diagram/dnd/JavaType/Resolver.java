package de.tud.cs.st.vespucci.diagram.dnd.JavaType;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.util.ISourceAttribute;
import org.eclipse.jdt.internal.compiler.env.ISourceMethod;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * static class to get all information about the units
 * supports type conversion from java binary type to FQN
 * @author luecbn01
 */
@SuppressWarnings("restriction")
public class Resolver {
	private static final String CONSTRUCTOR = "<init>";
	private static final String JAVA = ".java";
	
	public static Resolver INSTANCE;

    public Resolver() {
        INSTANCE = this;
    }

    /**
	 * resolving types to FQN declaration
	 * @param type
	 * @param jdtDeclaringType
	 * @return
	 */
	@SuppressWarnings("restriction")
	private static String typeToFQN(String type, IType jdtDeclaringType) {
		String tmp = null;
        try {
			tmp = JavaModelUtil.getResolvedTypeName(type, jdtDeclaringType);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
        return tmp;
    }
    
    /**
     * getting the package name (FQN) from a IMethod, IPackageFragment, ICompilationUnit, IField and IType
     * @param o	one of the named Ixxx
     * @param key
     * @return FQN of the package
     * @author BenjaminL
     */
	public static String getFQPackageNameFromIxxx(Object o, String key){
		//package...
		if(o instanceof IPackageFragment){
			return ((IPackageFragment) o).getElementName();
		}else{
			// class, method, field, type
			IPackageDeclaration[] declarations;
			ICompilationUnit cUnit = null;
			try {	
				if(o instanceof IMethod)
					cUnit = ((IMethod) o).getCompilationUnit();
				else if(o instanceof IField)
					cUnit = ((IField) o).getCompilationUnit();
				else if(o instanceof ICompilationUnit){
					cUnit = (ICompilationUnit) o;
				}else if(o instanceof IType){
					cUnit = ((IType) o).getCompilationUnit();
				}
				declarations = cUnit.getPackageDeclarations();
				if(declarations.length > 0)
					return declarations[0].getElementName().trim(); 

			} catch (JavaModelException e) {
				IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
						"JavaModelException: Failed to resolve Package", e);
				StatusManager.getManager().handle(is, StatusManager.SHOW);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
		return null;
	}
	
	/**
	 * getting the FQN classname
	 * @param o
	 * @param key
	 * @return FQN classname 
	 */
	public static String getFQClassnamefromIxxx(Object o, String key){
		// class, method, field, type
		String classname = "";
		if(o instanceof IMethod)
			return ((IMethod) o).getDeclaringType().getFullyQualifiedName();
		else if(o instanceof IField)
			return ((IField) o).getDeclaringType().getFullyQualifiedName();
		else if(o instanceof ICompilationUnit){
			classname = ((ICompilationUnit) o).getElementName();
		}else if(o instanceof IType){
			classname = ((IType) o).getParent().getElementName();
		}
		
		if(classname.toLowerCase().endsWith(JAVA))
			classname = classname.substring(0,classname.length()-JAVA.length());
		if(classname.equals(""))
			return "";
		else{
			return getFQPackageNameFromIxxx(o, key) + "." + classname;
		}
	}
	
	
	/**
	 * returns the returntype of the given IMethod
	 * @param o
	 * @param key
	 * @return FQN type
	 */
	public static String getReturnTypeFromIxxx(Object o, String key){
			//method
			try {	
				if(o instanceof IMethod){
					IMethod m = (IMethod) o;
					return typeToFQN(m.getReturnType(), m.getDeclaringType());
				}

			} catch (JavaModelException e) {
				IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
						"JavaModelException: Failed to resolve returntype", e);
				StatusManager.getManager().handle(is, StatusManager.SHOW);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		return null;
	}
	
	/**
	 * getting all parameters from a given IMethod
	 * @param field
	 * @return
	 */
	public static List<String> getParameterTypesFromMethod(IMethod method) {
		List<String> parameters = new ArrayList<String>();
	
		for(String type : method.getParameterTypes()){
			parameters.add(typeToFQN(type, method.getDeclaringType()));
		}
		return parameters;
	}

	public static String getMethodnameFromMethod(IMethod method) {
		
		//get methodname, set as <init> if it is the constructor
		try {
			if(method.isConstructor())
				return CONSTRUCTOR;
			else
				return method.getElementName();
		} catch (JavaModelException e) {
			IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve methodname", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}

	public static String getFQFieldTypeName(IField field) {
		try {
			return typeToFQN(field.getTypeSignature(), field.getDeclaringType());
		} catch (JavaModelException e) {
			IStatus is = new Status(Status.ERROR, VespucciDiagramEditorPlugin.ID,
					"JavaModelException: Failed to resolve field type", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}

	/**
	 * getting 
	 */
	public static String getElementNameFromObject(Object o) {
		if(o instanceof IProject) 
			return ((IProject) o).getName();
		else if(o instanceof IPackageFragment) 
			return ((IPackageFragment) o).getElementName();
		else if(o instanceof ICompilationUnit) 
			return ((ICompilationUnit) o).getElementName();
		else if(o instanceof IType) 
			return ((IType) o).getElementName();
		else if(o instanceof IField) 
			return ((IField) o).getElementName();
		else if(o instanceof IMethod) 
			return ((IMethod) o).getElementName();
		else if(o instanceof IPackageFragmentRoot) 
			return ((IPackageFragmentRoot) o).getElementName();
		else if(o instanceof ISourceAttribute)
			return ((ISourceAttribute) o).getSourceFileName().toString();
//		else if(o instanceof ISourceMethod) 
//			return ((ISourceMethod) o).getElementName();
		else if(o instanceof IProject) 
			return ((IProject) o).getName();
		else if(o instanceof IClassFile) 
			return ((IClassFile) o).getElementName();
//		else if(o instanceof IAdaptable) 
//			return ((IAdaptable) o).getElementName();
		else if(o instanceof IFile) 
			return ((IFile) o).getName();
		else if(o instanceof IFolder) 
			return ((IFolder) o).getName();
		else
			//nothing
		
		return "";
	}
	
	
	
//	private void readInformation() {
//		
//		//package...
//		if(o instanceof IPackageFragment){
//			//System.out.println("Package --> " + key);
//			key = key.substring(0,key.indexOf('[')).trim();
//			key = PACKAGE + "('" + key + "')";
//			list.add(key);
//		}else if(o instanceof ICompilationUnit){
//			//CLASS
//			String packagename = "";
//			String classname = "";
//			try {
//				ICompilationUnit cUnit = (ICompilationUnit) o;				
//				IPackageDeclaration[] declarations;
//			
//				declarations = cUnit.getPackageDeclarations();
//				if(declarations.length > 0)
//				{
//					packagename = declarations[0].getElementName(); 
//				}
//				//getting the classname
//				classname = packagename + "." + ((ICompilationUnit) o).getElementName();
//			} catch (JavaModelException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			key = CLASS_WITH_MEMBERS + "('" + packagename + "','" + classname + "')";
//			list.add(key);
//		}else if(o instanceof IMethod){
//			//METHOD
//			key = METHOD + "(" + getQueryFromMethod((IMethod)o) + ")";
//			list.add(key);
//		}else if(o instanceof IField){
//			//FIELD
//			key = FIELD + "(" + getQueryFromField((IField)o) + ")";
//			list.add(key);
//		}
//	}
	
	
}

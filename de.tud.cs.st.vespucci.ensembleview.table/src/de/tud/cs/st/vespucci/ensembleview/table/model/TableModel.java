package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class TableModel implements IDataModel{

	private Set<Triple<IEnsemble, ICodeElement, IMember>> data = new HashSet<Triple<IEnsemble, ICodeElement, IMember>>();
	
	@Override
	public void added(Triple<IEnsemble, ICodeElement, IMember> element) {
		data.add(element);
	}

	@Override
	public void deleted(Triple<IEnsemble, ICodeElement, IMember> element) {
		data.remove(element);
	}
	
	public Object[] getData(){
		return data.toArray();
	}
	
	public static String createText(Triple<IEnsemble, ICodeElement, IMember> value, int column){
		if (value == null){
			return "";
		}
		switch (column){
		case 0:
			return createEnsembleText(value);
		case 1:
			return createCodeElementText(value);
		case 2:
			return createResourceText(value);
		case 3:
			return createPathText(value);
		default:
			return "";
		}
	}

	private static String createPathText(
			Triple<IEnsemble, ICodeElement, IMember> value) {
		return value.getThird().getPath().toOSString();
	}

	private static String createResourceText(
			Triple<IEnsemble, ICodeElement, IMember> value) {
		return value.getThird().getResource().getName();
	}

	private static String createCodeElementText(
			Triple<IEnsemble, ICodeElement, IMember> value) {
		
		ICodeElement codeElement = value.getSecond();
		IMember member = value.getThird();
		
		if ((codeElement instanceof IClassDeclaration)&&(member instanceof IType)){
			return ((IType)member).getElementName()+":" + ((IType)member).getTypeQualifiedName();
		}
		if ((codeElement instanceof IMethodDeclaration)&&(member instanceof IMethod)){
			String returnTyp = "";
			try{
				returnTyp = ((IMethod)member).getReturnType(); 
			} catch (JavaModelException e) {
			}
			return ((IMethod)member).getElementName()+":" + returnTyp;
		}
		if ((codeElement instanceof IFieldDeclaration)&&(member instanceof IField)){
			String typ = "";
			try{
				typ = ((IField)member).getTypeSignature(); 
			} catch (JavaModelException e) {
			}
			return ((IField)member).getElementName()+":" + typ;
		}
		if (codeElement instanceof IClassDeclaration){
			return ((IClassDeclaration)codeElement).getSimpleClassName()+":" + ((IClassDeclaration)codeElement).getTypeQualifier();
		}
		if (codeElement instanceof IMethodDeclaration){
			return ((IMethodDeclaration)codeElement).getMethodName()+":";
		}
		if (codeElement instanceof IFieldDeclaration){
			return ((IFieldDeclaration)codeElement).getFieldName()+":";
		}
		
		// the right part was not found. member is some parent element
		return "";
	}

	private static String createEnsembleText(
			Triple<IEnsemble, ICodeElement, IMember> value) {
		return value.getFirst().getName();
	}

}

package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.codeelementfinder.Util;
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
		if (codeElement instanceof IClassDeclaration){
			return ((IClassDeclaration)codeElement).getSimpleClassName();
		}
		if (codeElement instanceof IMethodDeclaration){
			IMethodDeclaration methodDeclaration = (IMethodDeclaration)codeElement;
			String label = methodDeclaration.getMethodName()+"(";
			for (int i = 0; i < methodDeclaration.getParameterTypeQualifiers().length; i++) {
				label += Util.createSimpleTypeText(methodDeclaration.getParameterTypeQualifiers()[i]);
				if (i < methodDeclaration.getParameterTypeQualifiers().length-1){
					label += ", ";
				}
			}
			label += ")"+" : " + Util.createSimpleTypeText(((IMethodDeclaration)codeElement).getReturnTypeQualifier());;
			return label;
		}
		if (codeElement instanceof IFieldDeclaration){
			return ((IFieldDeclaration)codeElement).getFieldName()+" : " + Util.createSimpleTypeText(((IFieldDeclaration)codeElement).getTypeQualifier());
		}

		return "";
	}

	private static String createEnsembleText(
			Triple<IEnsemble, ICodeElement, IMember> value) {
		
		IEnsemble ensemble = value.getFirst();
		String label =  ensemble.getName();
		
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		
		return label;
	}

}

package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.codeelementfinder.Util;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class TableModel implements IDataModel{

	private Set<IPair<IEnsemble, ICodeElement>> data = new HashSet<IPair<IEnsemble, ICodeElement>>();
	
	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		data.add(element);
	}

	@Override
	public void deleted(Triple<IEnsemble, ICodeElement, IMember> element) {
		data.remove(element);
	}
	
	public Object[] getData(){
		return data.toArray();
	}
	
	public static String createText(IPair<IEnsemble, ICodeElement> value, int column){
		if (value == null){
			return "";
		}
		switch (column){
		case 0:
			return createEnsembleText(value);
		case 1:
			return createClassText(value);
		case 2:
			return createCodeElementText(value);
		default:
			return "";
		}
	}

	private static String createClassText(
			IPair<IEnsemble, ICodeElement> value) {
		return value.getSecond().getSimpleClassName();
	}

	private static String createCodeElementText(
			IPair<IEnsemble, ICodeElement> value) {
		
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
			IPair<IEnsemble, ICodeElement> value) {
		
		IEnsemble ensemble = value.getFirst();
		String label =  ensemble.getName();
		
		while (ensemble.getParent() != null){
			ensemble = ensemble.getParent();
			label = ensemble.getName() + "." + label;
		}
		
		return label;
	}

}

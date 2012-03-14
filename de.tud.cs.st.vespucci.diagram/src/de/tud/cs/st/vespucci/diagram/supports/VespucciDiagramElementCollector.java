package de.tud.cs.st.vespucci.diagram.supports;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

public class VespucciDiagramElementCollector {
	public static List<EObject> collectElements(View diagramView){
		EObject modelElement = diagramView.getElement();
		System.out.println(modelElement);
		return null;
	}
	
	private static void filterConnections(View diagramView, EObject modelElement){
		
	}
	
	private static void filterEnsembles(View diagramView, EObject modelElement){
		
	}
	
	
}

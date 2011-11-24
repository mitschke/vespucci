package de.tud.cs.st.vespucci.testprocessor;

import de.tud.cs.st.vespucci.diagram.interfaces.IConstraint;
import de.tud.cs.st.vespucci.diagram.interfaces.IEnsemble;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class TestProcessor implements IModelProcessor {

	@Override
	public void processModel(Object diagramModel) {
		
		OutputModel model = Util.getAdapted(diagramModel, OutputModel.class);	
		
		System.out.println(model == null ? "model = null" : model.toString());
		System.out.println();
		
		for (IEnsemble ensemble : model.getElements()) {
			
			
			System.out.println("Ensemble Name: " + ensemble.getName());
			System.out.println("SourceConnections:");
			System.out.println("( " + ensemble.getSourceConnections().size() + " )");
			for (IConstraint constraint : ensemble.getSourceConnections()) {
				System.out.println(constraint.toString());
				
			}
			
			
			
			System.out.println("");
			System.out.println("TargetConnections:");
			System.out.println("( " + ensemble.getTargetConnections().size() + " )");
			for (IConstraint constraint : ensemble.getTargetConnections()) {
				System.out.println("-");
				System.out.println("Class: " + constraint.getClass().getName());
				System.out.println("Type: " + constraint.getDependencyKind());
				System.out.println("-");
				
			}
			System.out.println("");
			System.out.println("------------------------------------------------------");
			
		}
	}

}

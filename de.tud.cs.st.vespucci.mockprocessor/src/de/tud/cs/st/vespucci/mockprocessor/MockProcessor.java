package de.tud.cs.st.vespucci.mockprocessor;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class MockProcessor implements IModelProcessor {

	@Override
	public void processModel(Object diagramModel) {
		ShapesDiagram t = Util.getAdapted(diagramModel, ShapesDiagram.class);	
		
		if (t != null){
			System.out.println("Yes");
		}
		
		ModelT284 model = Util.getAdapted(diagramModel, ModelT284.class);	
		
		if (model != null){
			System.out.println("model: Yes");
		}
	}

}

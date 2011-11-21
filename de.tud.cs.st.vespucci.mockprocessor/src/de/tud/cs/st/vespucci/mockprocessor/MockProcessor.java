package de.tud.cs.st.vespucci.mockprocessor;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.information.interfaces.IViolation;

public class MockProcessor implements IModelProcessor {

	@Override
	public void processModel(Object diagramModel) {
		ModelT284 model = Util.getAdapted(diagramModel, ModelT284.class);

		// Violation in Datei DataModel.java in der Zeile 9 der Methodenaufruf von MainController.doSome();
		
		
		
	}

}

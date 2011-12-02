package de.tud.cs.st.vespucci.mockprocessor;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.diagram.interfaces.IConstraint;
import de.tud.cs.st.vespucci.diagram.interfaces.IEnsemble;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.information.interfaces.ISourceCodeElement;
import de.tud.cs.st.vespucci.information.interfaces.IViolation;
import de.tud.cs.st.vespucci.information.interfaces.IViolationReport;
import de.tud.cs.st.vespucci.information.interfaces.spi.SourceCodeElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Violation;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationReport;

public class MockProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		DiagramModel model = Util.getAdapted(diagramModel, DiagramModel.class);

		LinkedList<IEnsemble> elements = model.getElements();
		
		IEnsemble sourceElement = elements.get(0);
		IEnsemble targetElement = elements.get(1);
		IConstraint constraint = sourceElement.getTargetConnections().getFirst();
		
		
		// Violation in Datei DataModel.java in der Zeile 9 der Methodenaufruf von MainController.doSome();
		ISourceCodeElement sourceCodeElement = new SourceCodeElement("model", "DataModel", 9);
		ISourceCodeElement targetCodeElement = new SourceCodeElement("controller", "MainController", 5);
		
		IViolation firstViolation = new Violation("Nicht erlaubter Aufruf", 
													sourceCodeElement, 
													targetCodeElement, 
													sourceElement, 
													targetElement, 
													constraint);
			
		IViolationReport violationReport = new ViolationReport();
		
		violationReport.addViolation(firstViolation);
		
		return violationReport;
	}

	@Override
	public Class<?> getReturnType() {
		return IViolationReport.class;
	}

}

package de.tud.cs.st.vespucci.mockprocessor;

import java.util.LinkedList;
import java.util.Set;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.information.interfaces.spi.MethodElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.SourceClass;
import de.tud.cs.st.vespucci.information.interfaces.spi.SourceCodeElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Violation;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationReport;
import de.tud.cs.st.vespucci.interfaces.IMethodElement;
import de.tud.cs.st.vespucci.interfaces.ISourceClass;
import de.tud.cs.st.vespucci.interfaces.ISourceCodeElement;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class MockProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		
		IArchitectureModel architectureModel = Util.adapt(diagramModel, IArchitectureModel.class);
		Set<IEnsemble> elements = architectureModel.getEnsembles();
		
		IEnsemble controller = null;
		IEnsemble model = null;
		IEnsemble view = null;
		
		int i = 0;
		
		for (IEnsemble ensemble : elements) {
			if (ensemble.getName().equals("Model")){
				model = ensemble;
			}else if (ensemble.getName().equals("Controller")){
				controller = ensemble;
			}else if (ensemble.getName().equals("View")){
				view = ensemble;
			}
		}
		
		IConstraint modelToController = null;
		IConstraint modelToView = null;
	
		for (IConstraint constraint : architectureModel.getConstraints()) {
			
			if (constraint.getTarget() == controller){
				modelToController = constraint;
			}else{
				modelToView = constraint;
			}
			
		}
		ISourceCodeElement dataModel_callController = new SourceCodeElement("model", "DataModel", 29);
		
		ISourceClass mainView = new SourceClass("view", "MainView");
		
		LinkedList<String> paramTypes = new LinkedList<String>();
		IMethodElement dataModel_createView = new MethodElement("model", "DataModel", "createView", "void", paramTypes);
		
		paramTypes = new LinkedList<String>();
		IMethodElement mainController_doSome = new MethodElement("controller", "MainController", "doSome", "void", paramTypes);

		IViolation firstViolation = new Violation(
				"Not allowed call from Model to Controller",
				dataModel_callController, 
				mainController_doSome, 
				model,
				controller, 
				modelToController);
		
		IViolation secondViolation = new Violation(
				"Not allowed construction from Model to View",
				dataModel_createView, 
				mainView,
				model, 
				view, 
				modelToView);
		
		IViolationReport violationReport = new ViolationReport();
		
		violationReport.addViolation(firstViolation);
		violationReport.addViolation(secondViolation);
		
		return violationReport;
	}

	@Override
	public Class<?> resultClass() {
		return IViolationReport.class;
	}

}

/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.mockprocessor;

import java.util.Set;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.information.interfaces.spi.MethodDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.ClassDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.CodeElement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Statement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Violation;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationReport;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IStatement;
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
		
		String[] paramTypes1 = new String[1];
		paramTypes1[0] = "QString;";
		String[] paramTypes2 = new String[0];
		
		
		//------------------------
		
		IStatement dataModel_callController = new Statement("model", "DataModel", 29);
		IClassDeclaration mainView = new ClassDeclaration("view", "MainView", "typeQualifier");
		IMethodDeclaration dataModel_createView = new MethodDeclaration("model", "DataModel", "createView", "I", paramTypes1);	
		IMethodDeclaration mainController_doSome = new MethodDeclaration("controller.test", "MainController", "doSome", "V", paramTypes2);

		//------------------------
		
		
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

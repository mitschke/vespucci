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

import java.util.HashSet;
import java.util.Set;

import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.information.interfaces.spi.ClassDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.FieldDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.MethodDeclaration;
import de.tud.cs.st.vespucci.information.interfaces.spi.Statement;
import de.tud.cs.st.vespucci.information.interfaces.spi.Violation;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationSummary;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationSummaryView;
import de.tud.cs.st.vespucci.information.interfaces.spi.ViolationView;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IStatement;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationSummary;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;

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
		paramTypes1[0] = "Ljava/lang/String;";
		String[] paramTypes2 = new String[1];
		paramTypes2[0] = "[I;";
		
		
		//------------------------
		
		IStatement markStatement = new Statement("model", "DataModel", 26);
		IMethodDeclaration dataModel_createView = new MethodDeclaration("model", "DataModel", "createInnerView", "[Ljava/lang/String;", paramTypes1);
		
		IMethodDeclaration SubView_show = new MethodDeclaration("view", "MainView$SubView", "show", "V;", new String[0]);
		IClassDeclaration mainView = new ClassDeclaration("view", "MainView$SubView", "Lview/MainView$SubView;");

		IFieldDeclaration field_dec = new FieldDeclaration("controller", "MainController$InnerController$InnerInnerConroller", "someString", "Ljava/lang/String;");
		IMethodDeclaration innerInnerController_doSomething = new MethodDeclaration("controller", "MainController$InnerController$InnerInnerConroller$InnerInnerController", "doSomething", "V;", new String[0]);
		
		//------------------------
		
		
		IViolation firstViolation = new Violation(
				"Some Violation 1",
				markStatement, 
				dataModel_createView, 
				model,
				controller, 
				modelToController);
		
		IViolation secondViolation = new Violation(
				"Some Violation 2",
				SubView_show, 
				mainView,
				model, 
				view, 
				modelToView);
		
		IViolation thirdViolation = new Violation(
				"Some Violation 3", 
				field_dec, 
				innerInnerController_doSomething, 
				null, 
				null, 
				null);
		
		
		Set<IViolation> violations = new HashSet<IViolation>();
		
		violations.add(firstViolation);
		violations.add(secondViolation);
		violations.add(thirdViolation);
				
		Set<IViolationSummary> set = new HashSet<IViolationSummary>();
		set.add(new ViolationSummary("/CaseStudy/src/caseStudy.sad"));
		IDataView<IViolationSummary> temp = new ViolationSummaryView(set);
	
		return new ViolationView(violations, temp);
	}

	@Override
	public Class<?> resultClass() {
		return IViolationView.class;
	}

}
/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitï¿½t Darmstadt
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
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universitï¿½t Darmstadt nor the names of its contributors may be used to 
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
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SemanticCreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RefreshConnectionsRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * EditPolicy for creating new shapes (GMF shapes) on a Vespucci-diagram
 * This Policy handles a CreateViewRequest.
 * @author MalteV
 */
final public class CreationNewEnsembleEditPolicy extends CreationEditPolicy {

	/**
	 * This is a modified version of the getCreateElementAndViewcommand of
	 * CreationEditPolicy. This version should only be used in the context of
	 * creating new Ensembles out of a dropped file method.
	 * CompositeCommand that include : - a create view element command
	 * create semantic element command - two setValue commands
	 * 
	 * @param request
	 * @return Command that creates the semantic and the view command for the
	 *         given CreateViewAndElementRequest
	 */
	protected Command getCreateElementAndViewEnsembleCommand(
			CreateViewAndElementRequest request) {
		/**
		 * A modified version of the SetValueCommand
		 * that always return true for canExecute()
		 * that is necessary because this command is used in a CompositeCommand 
		 * which is only executable if all commands in the compositeCommand are executable
		 * and the this command needs data which will be created from an other command in the compositeCommand.
		 * @author MalteV
		 */
		class extendedSetValueCommand extends SetValueCommand{
			private final CreateElementRequest createRequest;
			public extendedSetValueCommand(SetRequest request, CreateElementRequest createRequest) {
				super(request);
				this.createRequest = createRequest;

			}
			

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
					IAdaptable info) throws ExecutionException {
				this.setElementToEdit(createRequest.getNewElement());
				if(super.canExecute()){
				return super.doExecuteWithResult(monitor, info);
				}
				return CommandResult.newErrorCommandResult("Command was not Executeable\n pls see canExecute in extendedSetValueCommand");
			}
			

			/**
			 * @return this Methode returns always true!
			 * BenjaminL: wenn du die änderst pass auch bitte oben den kommentar an!
			 */
			@Override
			public boolean canExecute() {
				return true;
			}
			
		}

		// copied Content
		// get the element descriptor
		CreateElementRequestAdapter requestAdapter = request
				.getViewAndElementDescriptor().getCreateElementRequestAdapter();

		// get the semantic request
		CreateElementRequest createElementRequest = (CreateElementRequest) requestAdapter
				.getAdapter(CreateElementRequest.class);

		if (createElementRequest.getContainer() == null) {
			// complete the semantic request by filling in the host's semantic
			// element as the context
			View view = (View) getHost().getModel();
			EObject hostElement = ViewUtil.resolveSemanticElement(view);

			if (hostElement == null && view.getElement() == null) {
				hostElement = view;
			}

			// Returns null if host is unresolvable so that trying to create a
			// new element in an unresolved shape will not be allowed.
			if (hostElement == null) {
				return null;
			}
			createElementRequest.setContainer(hostElement);
		}

		// get the create element command based on the elements descriptor
		// request
		Command createElementCommand = getHost().getCommand(
				new EditCommandRequestWrapper(
						(CreateElementRequest) requestAdapter
								.getAdapter(CreateElementRequest.class),
						request.getExtendedData()));

		if (createElementCommand == null) {
			return UnexecutableCommand.INSTANCE;
		}
		if (!createElementCommand.canExecute()) {
			return createElementCommand;
		}

		// create the semantic create wrapper command
		SemanticCreateCommand semanticCommand = new SemanticCreateCommand(
				requestAdapter, createElementCommand);
		Command viewCommand = getCreateCommand(request);

		Command refreshConnectionCommand = getHost().getCommand(
				new RefreshConnectionsRequest(((List<?>) request.getNewObject())));
		// form the compound command and return
		CompositeCommand cc = new CompositeCommand(semanticCommand.getLabel());
		cc.compose(semanticCommand);
		cc.compose(new CommandProxy(viewCommand));

		if (refreshConnectionCommand != null) {
			cc.compose(new CommandProxy(refreshConnectionCommand));
		}
		// end copied content
		// create two setValuecomand to set the Query and the name of the New
		// Ensemble
		EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
				.getEPackage("http://vespucci.editor");
		Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;

		@SuppressWarnings("unchecked")
		SetRequest sr = new SetRequest(createElementRequest.getEditingDomain(),
				createElementRequest.getNewElement(),
				vesPackage.getShape_Query(),
				QueryBuilder.createQueryForAMapOfIResource(request
						.getExtendedData()));
		extendedSetValueCommand svc = new extendedSetValueCommand(sr,
				createElementRequest);

		SetRequest sr2 = new SetRequest(
				createElementRequest.getEditingDomain(),
				createElementRequest.getNewElement(),
				vesPackage.getShape_Name(),
				QueryBuilder.createNameforNewEnsemble(request
						.getExtendedData()));
		extendedSetValueCommand svc2 = new extendedSetValueCommand(sr2,
				createElementRequest);
		cc.compose(svc);
		cc.compose(svc2);
		cc.compose(new SelectAndEditNameCommand(request, getHost().getRoot().getViewer()));
		return new ICommandProxy(cc);
	}
	
	
	/**
	 * return a create command if the request is understood
	 */
	@Override	
	public Command getCommand(Request request) {
		if (understandsRequest(request)) {
			if (request.getType().equals(
					CreateEnsembleDropTargetListener.REQ_DROPNEWENSEMBLE)) {

				if (request instanceof CreateViewAndElementRequest) {
					request.setType(REQ_CREATE);
					return getCreateElementAndViewEnsembleCommand((CreateViewAndElementRequest) request);
				}
			}
		}
		return super.getCommand(request);
	}
	
	
	/**
	 * This class understands request of the time REQ_DROPNEWENSEMBLE and all Request that are
	 * understood by CreationEditPolicy
	 */
	@Override
	public boolean understandsRequest(Request request) {
		return request.getType().equals(
				CreateEnsembleDropTargetListener.REQ_DROPNEWENSEMBLE)
				|| super.understandsRequest(request);
	}

}

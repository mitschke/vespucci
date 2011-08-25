/******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
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
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RefreshConnectionsRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * EditPolicy for creating new shapes (GMF shapes) on a Vespucci-diagram This policy handles a CreateViewRequest.
 * 
 * @author Malte Viering
 */
public final class CreationNewEnsembleEditPolicy extends CreationEditPolicy {
	/**
	 * A modified version of the SetValueCommand that always return true for canExecute() that is necessary because this
	 * command is used in a CompositeCommand that is only executable if all commands in the compositeCommand are
	 * executable. This command needs data that will be created from an other command in the compositeCommand.
	 * 
	 * @author MalteV
	 */
	class ExtendedSetValueCommand extends SetValueCommand {
		private final CreateElementRequest createRequest;

		/**
		 * Sets the {@link #createRequest} additionally to {@link SetValueCommand#SetValueCommand(SetRequest)}.
		 * 
		 * @param request
		 * @param createRequest
		 */
		protected ExtendedSetValueCommand(final SetRequest request, final CreateElementRequest createRequest) {
			super(request);
			this.createRequest = createRequest;
		}

		/**
		 * @return <code>true</code>
		 */
		@Override
		public boolean canExecute() {
			return true;
		}

		@Override
		protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info)
				throws ExecutionException {
			this.setElementToEdit(createRequest.getNewElement());
			if (super.canExecute()) {
				return super.doExecuteWithResult(monitor, info);
			}
			return CommandResult
					.newErrorCommandResult("Command was not executeable\n please see canExecute in extendedSetValueCommand");
		}

	}

	private static final String COMMAND_LABEL = "Creates an ensemble for Drag'n'Drop";

	private static Vespucci_modelPackage vesPackage;

	/**
	 * Sets vespucci model package.
	 */
	public CreationNewEnsembleEditPolicy() {
		final String modelNamespace = ResourceBundle.getBundle("plugin").getString("vespucci_modelNamespaceURI");
		final EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage(modelNamespace);
		vesPackage = (Vespucci_modelPackage) epackage;
	}

	private ExtendedSetValueCommand createSetNameCommand(final CreateElementRequest createElementRequest,
			final CreateViewAndElementRequest request) {
		final SetRequest setNameRequest = new SetRequest(createElementRequest.getEditingDomain(),
				createElementRequest.getNewElement(), vesPackage.getShape_Name(), QueryBuilder.createNameforNewEnsemble(request
						.getExtendedData()));
		return new ExtendedSetValueCommand(setNameRequest, createElementRequest);
	}

	private ExtendedSetValueCommand createSetQueryCommand(final CreateElementRequest createElementRequest, final Request request) {
		@SuppressWarnings("unchecked")
		final SetRequest setQueryRequest = new SetRequest(createElementRequest.getEditingDomain(),
				createElementRequest.getNewElement(), vesPackage.getShape_Query(),
				QueryBuilder.createQueryFromRequestData(request.getExtendedData()));
		return new ExtendedSetValueCommand(setQueryRequest, createElementRequest);
	}

	/**
	 * @return Returns a create command if the request is understood i.e. its type is REQ_DROPNEWENSEMBLE.
	 */
	@Override
	public Command getCommand(final Request request) {
		if (understandsRequest(request)) {
			if (request.getType().equals(CreateEnsembleDropTargetListener.REQ_DROPNEWENSEMBLE)) {

				if (request instanceof CreateViewAndElementRequest) {
					request.setType(REQ_CREATE);
					return createElementAndViewEnsembleCommand((CreateViewAndElementRequest) request);
				}
			}
		}
		return super.getCommand(request);
	}

	/**
	 * This is a modified version of {@link CreationEditPolicy#getCreateElementAndViewcommand}. This version should only
	 * be used in this class. This method returns a CompositeCommand that include - a create view element command create
	 * semantic element command - two setValue commands
	 * 
	 * @param request
	 * @return Command that creates the semantic and the view command for the given CreateViewAndElementRequest
	 */
	private Command createElementAndViewEnsembleCommand(final CreateViewAndElementRequest request) {

		// copied content
		// get the element descriptor
		final CreateElementRequestAdapter requestAdapter = request.getViewAndElementDescriptor().getCreateElementRequestAdapter();

		// get the semantic request
		final CreateElementRequest createElementRequest = (CreateElementRequest) requestAdapter
				.getAdapter(CreateElementRequest.class);

		if (createElementRequest.getContainer() == null) {
			// complete the semantic request by filling in the host's semantic
			// element as the context
			final View view = (View) getHost().getModel();
			EObject hostElement = ViewUtil.resolveSemanticElement(view);

			if (hostElement == null && view.getElement() == null) {
				hostElement = view;
			}

			// Returns null if host is not resolvable s.t. trying to create a
			// new element in an unresolved shape will not be allowed.
			if (hostElement == null) {
				return null;
			}
			createElementRequest.setContainer(hostElement);
		}

		// get the create element command based on the elements descriptor
		// request
		final Command createElementCommand = getHost().getCommand(
				new EditCommandRequestWrapper((CreateElementRequest) requestAdapter.getAdapter(CreateElementRequest.class),
						request.getExtendedData()));

		if (createElementCommand == null) {
			return UnexecutableCommand.INSTANCE;
		}
		if (!createElementCommand.canExecute()) {
			return createElementCommand;
		}
		final CompositeCommand cc = new CompositeCommand(COMMAND_LABEL);

		cc.compose(new SemanticCreateCommand(requestAdapter, createElementCommand));

		final Command viewCreateCommand = getCreateCommand(request);
		cc.compose(new CommandProxy(viewCreateCommand));

		final Command refreshConnectionCommand = getHost().getCommand(
				new RefreshConnectionsRequest(((List<?>) request.getNewObject())));
		// form the compound command and return
		if (refreshConnectionCommand != null) {
			cc.compose(new CommandProxy(refreshConnectionCommand));
		}

		// create two SetValueCommand to set the Query and the Name of the new ensemble
		cc.compose(createSetQueryCommand(createElementRequest, request));
		cc.compose(createSetNameCommand(createElementRequest, request));

		cc.compose(new SelectAndEditNameCommand(request, getHost().getRoot().getViewer()));
		
		return new ICommandProxy(cc);
	}

	/**
	 * This class understands request of the type REQ_DROPNEWENSEMBLE and all Request that are understood by
	 * CreationEditPolicy.
	 * 
	 * @return Returns true, only if the request is understandable.
	 */
	@Override
	public boolean understandsRequest(final Request request) {
		return request.getType().equals(CreateEnsembleDropTargetListener.REQ_DROPNEWENSEMBLE)
				|| super.understandsRequest(request);
	}

}

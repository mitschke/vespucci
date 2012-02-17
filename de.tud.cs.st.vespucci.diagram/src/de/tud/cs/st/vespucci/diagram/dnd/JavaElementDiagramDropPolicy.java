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

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;

/**
 * EditPolicy for creating new ensembles in a Vespucci-diagram from java
 * elements.
 * 
 * @author Ralf Mitschke
 * @author Malte Viering
 */
public final class JavaElementDiagramDropPolicy extends CreationEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy#getCommand
	 * (org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (!understandsRequest(request))
			return null;

		if (!(request instanceof CreateViewAndElementRequest))
			return null;
		/*
		 * CreateViewAndElementRequest createRequest =
		 * (CreateViewAndElementRequest) request; if (!(getHost().getModel()
		 * instanceof View)) throw new IllegalStateException(String.format(
		 * "[%s] should be of type View", getHost().getModel()));
		 * 
		 * final View view = (View) getHost().getModel();
		 * 
		 * EObject container = view.getElement();
		 * 
		 * // alternative of getting the vespucci package via registry (not sure
		 * if // needed) // final String modelNamespace = //
		 * ResourceBundle.getBundle
		 * ("plugin").getString("vespucci_modelNamespaceURI"); // final EPackage
		 * epackage = //
		 * org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.getEPackage
		 * (modelNamespace); // final Vespucci_modelPackage vespucciPackage =
		 * (Vespucci_modelPackage) // epackage;
		 * 
		 * IElementType elementType = ElementTypeRegistry.getInstance()
		 * .getElementType(Vespucci_modelPackage.eINSTANCE.getEnsemble());
		 * 
		 * // funny how all the things are called requests in gmf/gef. // so
		 * this createRequest is not related to the gef reqest passed to this //
		 * method. CreateElementRequest elementCreateRequest = new
		 * CreateElementRequest( container, elementType); Command
		 * createElementCommand = new ICommandProxy( new
		 * EnsembleCreateCommand(elementCreateRequest));
		 * 
		 * CreateElementRequestAdapter requestAdapter =
		 * ((CreateViewAndElementRequest) request)
		 * .getViewAndElementDescriptor().getCreateElementRequestAdapter();
		 * SemanticCreateCommand semanticCreateElementCommand = new
		 * SemanticCreateCommand( requestAdapter, createElementCommand);
		 * 
		 * final Command createViewCommand = getCreateCommand(createRequest);
		 * 
		 * @SuppressWarnings("rawtypes") Command refreshConnectionCommand =
		 * getHost().getCommand( new RefreshConnectionsRequest(((List)
		 * createRequest .getNewObject())));
		 * 
		 * // form the compound command and return CompositeCommand cc = new
		 * CompositeCommand( semanticCreateElementCommand.getLabel());
		 * cc.compose(semanticCreateElementCommand); cc.compose(new
		 * CommandProxy(createViewCommand)); if (refreshConnectionCommand !=
		 * null) { cc.compose(new CommandProxy(refreshConnectionCommand)); }
		 * 
		 * return new ICommandProxy(cc);
		 */
		request.setType(REQ_CREATE);
		Command createElementAndViewCommand = getCreateElementAndViewCommand((CreateViewAndElementRequest) request);

		return createElementAndViewCommand;
	}

	/**
	 * This class understands request of the type REQ_DROPNEWENSEMBLE
	 */
	@Override
	public boolean understandsRequest(final Request request) {
		return IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE.equals(request
				.getType());
	}

}

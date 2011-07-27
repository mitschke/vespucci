/**
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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
package de.tud.cs.st.vespucci.diagram.handler;

import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * Handler for "Set Type"-commands in the constraint popup menu.<br>
 * Set Type changes the class of the constraint, e.g. from Incoming to Outgoing. Thus the class of
 * the connection must be changed for the graphical and semantical object. In order to preserve
 * consistency the constraints will be deleted and then recreated via commands (i.e. the same
 * procedure a user initiates).
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 */
public class SetConstraintTypeHandler extends AbstractHandler {
	/**
	 * Label for commands to be created.
	 */
	private static final String CONTEXT_LABEL = "Set type of constraint";

	/**
	 * Creates a command, that deletes given connections. Undo/Redo is supported, when command is
	 * executed on a command stack. This method is a heavily adapted version of
	 * {@link DiagramGlobalActionHandler#getDeleteCommand(IDiagramWorkbenchPart, IGlobalActionContext)}
	 * .
	 * 
	 * @param selectedConnections
	 *            Selected connections to be deleted.
	 * @return The command, that will delete given connection, when executed.
	 */
	private static Command getDeleteCommand(final ConnectionEditPart[] selectedConnections) {
		final GroupRequest deleteReq = new GroupRequest(org.eclipse.gef.RequestConstants.REQ_DELETE);
		// Chosen label has no deeper meaning/functionality
		final CompoundCommand deleteCC = new CompoundCommand(CONTEXT_LABEL);
		final CompositeTransactionalCommand compositeCommand = new CompositeTransactionalCommand(
				selectedConnections[0].getEditingDomain(), CONTEXT_LABEL);

		// Bundle all delete-commands of the selected connections.
		for (final ConnectionEditPart conn : selectedConnections) {
			final Command command = conn.getCommand(deleteReq);
			if (command != null) {
				compositeCommand.compose(new CommandProxy(command));
			}
		}
		if (!compositeCommand.isEmpty()) {
			deleteCC.add(new ICommandProxy(compositeCommand));
		}
		return deleteCC;
	}

	/**
	 * Selected connections.
	 */
	private ConnectionEditPart[] selectedConnections;
	/**
	 * Array containing a map for all connections. The map associates each feature of the
	 * connection with the corresponding value. This array is needed to restore all properties
	 * of the recreated connections.
	 */
	private HashMap<EStructuralFeature, Object>[] featureMapArr;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection currentSelection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		// ensure selection is not empty
		if (currentSelection.size() == 0) {
			// nothing to do
			return null;
		}
		final Object[] currentSelectionArr = currentSelection.toArray();

		// This array will contain the casted selection-objects.
		selectedConnections = new ConnectionEditPart[currentSelection.size()];

		for (int i = 0; i < currentSelection.size(); ++i) {
			if (currentSelectionArr[i] instanceof ConnectionEditPart) {
				selectedConnections[i] = (ConnectionEditPart) currentSelectionArr[i];
			} else {
				// If this exception is reached, then there should be something wrong with the
				// visibleWhen entry of the popUp-menu.
				return new ExecutionException("Selection is not a connection!");
			}
		}

		featureMapArr = new HashMap[selectedConnections.length];

		// Save information of the connections to be destroyed.
		for (int i = 0; i < selectedConnections.length; ++i) {
			final EObject conn = selectedConnections[i].resolveSemanticElement();
			HashMap<EStructuralFeature, Object> connMap = new HashMap<EStructuralFeature, Object>();
			for (final EStructuralFeature feature : conn.eClass().getEAllStructuralFeatures()) {
				connMap.put(feature, conn.eGet(feature));
			}
			featureMapArr[i] = connMap;
		}

		/**
		 * Command, that shall delete and recreate selected connections.
		 */
		final CompoundCommand recreateCC = new CompoundCommand(CONTEXT_LABEL);
		// add delete-command as first part to the recreate-command
		recreateCC.add(getDeleteCommand(selectedConnections));
		// add create-command as second part
		// TODO get correct type from command
		final IElementType type = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005;
		recreateCC.add(getCreateCommand(type));
		// restore properties of connections
		// TODO
		// Execute on gmf command-stack for undo/redo-compatibility.
		selectedConnections[0].getDiagramEditDomain().getDiagramCommandStack().execute(recreateCC);
		return null;
	}

	/**
	 * Creates a command, that creates connections from given parameter. The type of the new
	 * connections depends on the command, that is handled. Undo/Redo is supported, when command is
	 * executed on a command stack. This method is a heavily adapted version of
	 * {@link org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool#createConnection()}.
	 * 
	 * @param type
	 *            The type of the connection to be created.
	 * @return The command, that will create all deleted connections, when executed.
	 */
	private Command getCreateCommand(final IElementType type) {
		final CompoundCommand createCC = new CompoundCommand(CONTEXT_LABEL);
		final CompositeTransactionalCommand compositeCommand = new CompositeTransactionalCommand(
				selectedConnections[0].getEditingDomain(), CONTEXT_LABEL);

		for (int i = 0; i < featureMapArr.length; ++i) {
			final HashMap<EStructuralFeature, Object> map = featureMapArr[i];

			final CreateConnectionRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type,
					getPreferencesHint());

			final EditPart sourceEditPart = selectedConnections[i].getSource();
			final EditPart targetEditPart = selectedConnections[i].getTarget();

			connectionRequest.setTargetEditPart(targetEditPart);
			connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_START);
			// TODO set to previous location
			connectionRequest.setLocation(new Point(0, 0));

			// only if the connection is supported will we get a non null
			// command from the sourceEditPart
			if (sourceEditPart.getCommand(connectionRequest) != null) {

				connectionRequest.setSourceEditPart(sourceEditPart);
				connectionRequest.setTargetEditPart(targetEditPart);
				connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_END);
				// TODO set to previous location
				connectionRequest.setLocation(new Point(0, 0));

				final Command command = targetEditPart.getCommand(connectionRequest);
				compositeCommand.compose(new CommandProxy(command));
			}
		}
		if (!compositeCommand.isEmpty()) {
			createCC.add(new ICommandProxy(compositeCommand));
		}
		return createCC;
	}

	/**
	 * Taken from
	 * {@link org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool#createConnection()}.
	 * Gets the preferences hint that is to be used to find the appropriate
	 * preference store from which to retrieve diagram preference values. The
	 * preference hint is mapped to a preference store in the preference
	 * registry <@link DiagramPreferencesRegistry>.
	 * 
	 * @return the preferences hint
	 */
	private PreferencesHint getPreferencesHint() {
		final EditPartViewer viewer = selectedConnections[0].getViewer();
		if (viewer != null) {
			final RootEditPart rootEP = viewer.getRootEditPart();
			if (rootEP instanceof IDiagramPreferenceSupport) {
				return ((IDiagramPreferenceSupport) rootEP).getPreferencesHint();
			}
		}
		return PreferencesHint.USE_DEFAULTS;
	}

}
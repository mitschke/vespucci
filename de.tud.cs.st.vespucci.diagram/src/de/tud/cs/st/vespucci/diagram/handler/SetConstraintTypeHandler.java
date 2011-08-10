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
import java.util.HashSet;
import java.util.Map.Entry;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
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
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;

/**
 * Handler for "Set Type"-commands in the constraint popup menu.<br>
 * Set Type changes the class of the constraint, e.g. from Incoming to Outgoing. Thus the class of
 * the connection must be changed for the graphical and semantical object. In order to preserve
 * consistency the constraints will be deleted and then recreated via commands (i.e. the same
 * procedure a user initiates).
 * 
 * @author Alexander Weitzmann
 * @version 0.3
 */
public final class SetConstraintTypeHandler extends AbstractHandler {
	/**
	 * Label for commands to be created.
	 */
	private static final String CONTEXT_LABEL = "Set type of constraint";

	/**
	 * Selected connections.
	 */
	private ConnectionEditPart[] selectedConnections;

	/**
	 * Array containing a map for all selected connections (see {@link #selectedConnections}). The
	 * map associates each feature of the connection with the corresponding value. This array is
	 * needed to restore the properties of the recreated connections. The order must correspond to
	 * the order of {@link #selectedConnections}!
	 */
	private HashMap<EStructuralFeature, Object>[] featureMapArr;

	/**
	 * Array containing a map for all selected connections (see {@link #selectedConnections}). The
	 * map associates graphical parameter with the corresponding value. This array is needed to
	 * restore the properties of the recreated connections. The order must correspond to the order
	 * of {@link #selectedConnections}!
	 */
	private HashMap<String, Object>[] graphicMapArr;

	/**
	 * Source of the connection.
	 */
	private static final String SOURCE_ENSEMBLE = "source";
	/**
	 * Target of the connection.
	 */
	private static final String TARGET_ENSEMBLE = "target";

	/**
	 * Set containing all restored or old connections. "Old" means, that it was not newly created by
	 * this handler and thus must not be restored.
	 */
	private HashSet<ConnectionEditPart> finishedConnections;

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

		/**
		 * Command, that shall delete and recreate selected connections.
		 */
		final CompoundCommand recreateCC = new CompoundCommand(CONTEXT_LABEL);
		// add delete-command as first part to the recreate-command
		recreateCC.add(getDeleteCommand());
		// add create-command as second part
		final SetConstraintTypeParameter typeParams = new SetConstraintTypeParameter();
		final IElementType type = typeParams.getParameterValues().get(
				event.getParameter("de.tud.cs.st.vespucci.diagram.SetConstraintTypeParam"));
		recreateCC.add(getCreateCommand(type));
		// save properties of connections to be destroyed
		saveProperties();
		// Execute on gmf command-stack for undo/redo-compatibility.
		selectedConnections[0].getDiagramEditDomain().getDiagramCommandStack().execute(recreateCC);
		// restore properties in newly created connections
		restoreProperties();
		// return must be null
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
		// create a create-command for each destroyed connection
		for (final ConnectionEditPart con : selectedConnections) {
			final CreateConnectionRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type,
					getPreferencesHint());

			final EditPart sourceEditPart = con.getSource();
			final EditPart targetEditPart = con.getTarget();
			// create request
			connectionRequest.setTargetEditPart(targetEditPart);
			connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_START);
			connectionRequest.setLocation(con.getConnectionFigure().getPoints().getFirstPoint());

			// only if the connection is supported will we get a non null
			// command from the sourceEditPart
			if (sourceEditPart.getCommand(connectionRequest) != null) {

				connectionRequest.setSourceEditPart(sourceEditPart);
				connectionRequest.setTargetEditPart(targetEditPart);
				connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_END);
				connectionRequest.setLocation(con.getConnectionFigure().getPoints().getLastPoint());
				// create command
				final Command command = targetEditPart.getCommand(connectionRequest);
				// bundle create-commands
				compositeCommand.compose(new CommandProxy(command));
			}
		}
		// wrap create commands
		if (!compositeCommand.isEmpty()) {
			createCC.add(new ICommandProxy(compositeCommand));
		}
		return createCC;
	}

	/**
	 * Creates a command, that deletes given connections. Undo/Redo is supported, when command is
	 * executed on a command stack. This method is a heavily adapted version of
	 * {@link DiagramGlobalActionHandler#getDeleteCommand(IDiagramWorkbenchPart, IGlobalActionContext)}
	 * .
	 * 
	 * @return The command, that will delete given connection, when executed.
	 */
	private Command getDeleteCommand() {
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
	 * Returns a unrestored connection created by this handler with specified source and target
	 * ensemble. If no such connection exists a runtime error will be thrown.
	 * 
	 * @param source
	 *            The source of the connection.
	 * @param target
	 *            The target of the connection.
	 * @return Returns a unrestored connection created by this handler with specified source and
	 *         target ensemble, if such connection exists.
	 */
	private ConnectionEditPart getNewConnection(final EnsembleEditPart source, final EnsembleEditPart target) {
		for (final Object conObj : source.getSourceConnections()) {
			final ConnectionEditPart con = (ConnectionEditPart) conObj;
			if (con.getTarget().equals(target) && !finishedConnections.contains(conObj)) {
				return con;
			}
		}
		// TODO delete debug
		throw new RuntimeException("source: " + source.toString() + " target: " + target.toString());
	}

	/**
	 * Taken from
	 * {@link org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool#createConnection()}.
	 * Gets the preferences hint that is to be used to find the appropriate preference store from
	 * which to retrieve diagram preference values. The preference hint is mapped to a preference
	 * store in the preference registry <@link DiagramPreferencesRegistry>.
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

	/**
	 * Restores the properties saved via {@link #saveProperties()}. Note, that
	 * {@link #selectedConnections} is obsolete at this point and can not be used anymore. In order
	 * to find the connections, which must be restored, all connections in the diagram will be
	 * traversed.
	 */
	private void restoreProperties() {
		for (int i = 0; i < featureMapArr.length; ++i) {
			final HashMap<String, Object> graphicMap = graphicMapArr[i];
			// find newly created connection
			final ConnectionEditPart con = getNewConnection((EnsembleEditPart) graphicMap.get(SOURCE_ENSEMBLE),
					(EnsembleEditPart) graphicMap.get(TARGET_ENSEMBLE));

			// Restore information from EObject (semantic model)
			for (final Entry<EStructuralFeature, Object> entry : featureMapArr[i].entrySet()) {
				con.resolveSemanticElement().eSet(entry.getKey(), entry.getValue());
			}

			// Restore graphical information
			// TODO
			// con.getFigure().setForegroundColor((Color) graphicMap.get("FontColor example"));
		}
	}

	/**
	 * Saves all relevant properties of the selected connections. If you add more entries to the
	 * map, don't forget to update {@link #restoreProperties()} accordingly.
	 */
	private void saveProperties() {
		// Save information from EObject (semantic model)
		featureMapArr = new HashMap[selectedConnections.length];
		for (int i = 0; i < selectedConnections.length; ++i) {
			final EObject conn = selectedConnections[i].resolveSemanticElement();
			// this map contains the information for one connection
			final HashMap<EStructuralFeature, Object> map = new HashMap<EStructuralFeature, Object>();
			for (final EStructuralFeature feature : conn.eClass().getEAllStructuralFeatures()) {
				map.put(feature, conn.eGet(feature));
			}
			// populate array
			featureMapArr[i] = map;
		}
		// Save graphical information
		graphicMapArr = new HashMap[selectedConnections.length];
		for (int i = 0; i < selectedConnections.length; ++i) {
			// this map contains the information for one connection
			final HashMap<String, Object> map = new HashMap<String, Object>();
			final ConnectionEditPart con = selectedConnections[i];

			// source and target will not be restored (since this will be already correct after
			// initial creation), but is used for finding the created connection
			map.put(SOURCE_ENSEMBLE, con.getSource());
			map.put(TARGET_ENSEMBLE, con.getTarget());
			// the following properties will be actually restored
			// TODO save more

			// populate array
			graphicMapArr[i] = map;
		}
	}

}
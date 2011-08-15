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

package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.DiagramGlobalActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;

/**
 * This command changes the class of the connection, e.g. from Incoming to Outgoing. Thus the class
 * of
 * the connection must be changed for the graphical and semantical object. In order to preserve
 * consistency the constraints will be deleted and then recreated via commands (i.e. the same
 * procedure a user initiates).
 * 
 * @author Alexander Weitzmann
 * @version 0.5
 */
public class SetConnectionTypeCommand implements Command {

	/**
	 * This classes provides all constants used for restoring recreated connections.
	 * 
	 * @author Alexander Weitzmann
	 * @version 0.1
	 */
	private static class RestoreConstant {
		/**
		 * Source of the connection.
		 */
		private static final String SOURCE_ENSEMBLE = "source";
		/**
		 * Target of the connection.
		 */
		private static final String TARGET_ENSEMBLE = "target";
	}

	/**
	 * Label for commands to be created.
	 */
	private static final String CONTEXT_LABEL = "Set type";

	/**
	 * Description used for this command.
	 */
	private static final String DESCRIPTION = "This command is only used when the type of a conenction is changed "
			+ "via the context menu. It deletes and recreates the selected connections.";

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
	 * Set containing all connections, that are not involved. I.e. have not been selected, nor have
	 * been created by this handler.
	 */
	private final HashSet<ConnectionEditPart> otherConnections;

	/**
	 * All connections, that are already visited during the restoring of recreated connections.
	 */
	private HashSet<ConnectionEditPart> restoredConnections;

	/**
	 * Current used diagram view. It contains i.a. all {@link EditPart} shown.
	 */
	private final IDiagramGraphicalViewer diagramViewer;

	/**
	 * Type to be set for selected connections.
	 */
	private IElementType setType;

	/**
	 * EMF edit domain of the edit parts to be manipulated.
	 */
	TransactionalEditingDomain modelEditingDomain;

	private org.eclipse.gef.commands.Command deleteCmd;

	private org.eclipse.gef.commands.Command createCmd;

	/**
	 * Creates a new command, that deletes and recreates given connections with given type.
	 * 
	 * @param connections
	 *            The connections, whose type is to be changed.
	 * @param connectionType
	 *            The new type to be set.
	 */
	public SetConnectionTypeCommand(final ConnectionEditPart[] connections, final IElementType connectionType) {
		selectedConnections = connections.clone();
		setType = connectionType;
		
		// Set diagram viewer
		diagramViewer = (IDiagramGraphicalViewer) selectedConnections[0].getViewer();

		otherConnections = new HashSet<ConnectionEditPart>();
		restoredConnections = new HashSet<ConnectionEditPart>();

		// Set emf editing domain
		modelEditingDomain = selectedConnections[0].getEditingDomain();
		
		deleteCmd = getDeleteCommand();
		createCmd = getCreateCommand(setType);
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public Command chain(final Command command) {
		org.eclipse.emf.common.command.CompoundCommand cmd = new org.eclipse.emf.common.command.CompoundCommand();
		cmd.append(command);
		cmd.append(this);
		return cmd;
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void execute() {
		saveProperties();
		deleteCmd.execute();
		// capture all other connections; these must be ignored
		otherConnections.addAll(getConnectionsWithout(selectedConnections));
		createCmd.execute();
		restoreProperties();
	}

	@Override
	public Collection<?> getAffectedObjects() {
		return getResult();
	}

	/**
	 * Returns all connections in the current diagram.<br>
	 * Note, that {@link #diagramViewer} must have been initialized before calling this method.
	 * 
	 * @return Returns all {@link ConnectionEditPart} of the diagram.
	 */
	private List<ConnectionEditPart> getConnections() {
		// get all parts within the diagram
		final Collection<EditPart> allParts = diagramViewer.getEditPartRegistry().values();
		// filter for connections
		final List<ConnectionEditPart> connections = new LinkedList<ConnectionEditPart>();
		for (final EditPart part : allParts) {
			if (part instanceof ConnectionEditPart) {
				connections.add((ConnectionEditPart) part);
			}
		}
		return connections;
	}

	/**
	 * Returns all connections in the current diagram not selected.<br>
	 * Note, that {@link #diagramViewer} must have been initialized
	 * before calling this method.
	 * 
	 * @param excludeConnections Connections to be excluded.
	 * 
	 * @return Returns all {@link ConnectionEditPart} of the diagram, that are not inculded in given given connections.
	 */
	private List<? extends ConnectionEditPart> getConnectionsWithout(ConnectionEditPart[] excludeConnections) {
		// get all connections within the diagram
		final List<ConnectionEditPart> result = getConnections();

		// delete selected connections
		for (final ConnectionEditPart conn : excludeConnections) {
			result.remove(conn);
		}
		return result;
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
	private org.eclipse.gef.commands.Command getCreateCommand(final IElementType type) {
		final CompoundCommand createCC = new CompoundCommand(CONTEXT_LABEL + " (create)");
		final CompositeTransactionalCommand compositeCommand = new CompositeTransactionalCommand(
				selectedConnections[0].getEditingDomain(), CONTEXT_LABEL + " (create)");
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
				final org.eclipse.gef.commands.Command command = targetEditPart.getCommand(connectionRequest);
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
	private org.eclipse.gef.commands.Command getDeleteCommand() {
		final GroupRequest deleteReq = new GroupRequest(org.eclipse.gef.RequestConstants.REQ_DELETE);
		// Chosen label has no deeper meaning/functionality
		final CompoundCommand deleteCC = new CompoundCommand(CONTEXT_LABEL + " (delete)");
		final CompositeTransactionalCommand compositeCommand = new CompositeTransactionalCommand(
				selectedConnections[0].getEditingDomain(), CONTEXT_LABEL + " (delete)");

		// Bundle all delete-commands of the selected connections.
		for (final ConnectionEditPart conn : selectedConnections) {
			final org.eclipse.gef.commands.Command command = conn.getCommand(deleteReq);
			if (command != null) {
				compositeCommand.compose(new CommandProxy(command));
			}
		}
		if (!compositeCommand.isEmpty()) {
			deleteCC.add(new ICommandProxy(compositeCommand));
		}
		return deleteCC;
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getLabel() {
		return CONTEXT_LABEL;
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
			if (con.getTarget().equals(target) && !otherConnections.contains(conObj) && !restoredConnections.contains(conObj)) {
				return con;
			}
		}
		// TODO exception handling
		throw new RuntimeException("source: " + source.toString() + " target: " + target.toString());
	}

	/**
	 * Taken from
	 * {@link org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool#createConnection()}:
	 * Gets the preferences hint that is to be used to find the appropriate preference store from
	 * which to retrieve diagram preference values. The preference hint is mapped to a preference
	 * store in the preference registry <@link DiagramPreferencesRegistry>.
	 * 
	 * @return the preferences hint
	 */
	private PreferencesHint getPreferencesHint() {
		if (diagramViewer != null) {
			final RootEditPart rootEP = diagramViewer.getRootEditPart();
			if (rootEP instanceof IDiagramPreferenceSupport) {
				return ((IDiagramPreferenceSupport) rootEP).getPreferencesHint();
			}
		}
		return PreferencesHint.USE_DEFAULTS;
	}

	@Override
	public Collection<?> getResult() {
		return new HashSet<ConnectionEditPart>(restoredConnections);
	}

	@Override
	public void redo() {
		execute();
	}

	/**
	 * Restores the properties saved via {@link #saveProperties()}. Note, that
	 * {@link #selectedConnections} is obsolete at this point and can not be used anymore. In order
	 * to find the connections, which must be restored, all connections in the diagram will be
	 * traversed.
	 */
	private void restoreProperties() {
		// This command will restore the features of the recreated connections.
		final org.eclipse.emf.common.command.CompoundCommand restoreEFeatures = 
			new org.eclipse.emf.common.command.CompoundCommand();

		for (int i = 0; i < featureMapArr.length; ++i) {
			final HashMap<String, Object> graphicMap = graphicMapArr[i];
			// find newly created connection
			final ConnectionEditPart con = getNewConnection((EnsembleEditPart) graphicMap.get(RestoreConstant.SOURCE_ENSEMBLE),
					(EnsembleEditPart) graphicMap.get(RestoreConstant.TARGET_ENSEMBLE));
			// update set of restored connections
			restoredConnections.add(con);

			// Build command for EFeature restore
			for (final Entry<EStructuralFeature, Object> entry : featureMapArr[i].entrySet()) {
				final SetCommand setCommand = new SetCommand(modelEditingDomain, con.resolveSemanticElement(), entry.getKey(),
						entry.getValue());

				restoreEFeatures.append(setCommand);
			}

			// Restore graphical information
			// TODO
			// con.getFigure().setForegroundColor((Color) graphicMap.get("FontColor example"));
		}
		// restore all EFeature
		restoreEFeatures.execute();
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
			map.put(RestoreConstant.SOURCE_ENSEMBLE, con.getSource());
			map.put(RestoreConstant.TARGET_ENSEMBLE, con.getTarget());
			// the following properties will be actually restored
			// TODO save more

			// populate array
			graphicMapArr[i] = map;
		}
	}

	@Override
	public void undo() {
		createCmd.undo();
		deleteCmd.undo();
		
		// prepare for redo		
		restoredConnections.clear();
		ConnectionEditPart[] otherConnectionsArr = otherConnections.toArray(new ConnectionEditPart[otherConnections.size()]);
		selectedConnections = getConnectionsWithout(otherConnectionsArr).toArray(selectedConnections);
		deleteCmd = getDeleteCommand();
		createCmd = getCreateCommand(setType);
	}

}

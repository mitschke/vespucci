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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;

import de.tud.cs.st.vespucci.exceptions.VespucciUnexpectedException;
import de.tud.cs.st.vespucci.vespucci_model.Connection;

/**
 * This command changes the class of the connection, e.g. from Incoming to Outgoing. Thus the class
 * of the connection must be changed for the graphical and semantical object. In order to preserve
 * consistency the constraints will be deleted and then recreated via commands (i.e. the same
 * procedure a user initiates).
 * 
 * @author Alexander Weitzmann
 * @version 0.6
 */
public class SetConnectionTypeCommand implements Command {

	/**
	 * Label for commands to be created.
	 */
	private static final String CONTEXT_LABEL = "Set type";

	/**
	 * Description used for this command.
	 */
	private static final String DESCRIPTION = "Changes the dependency kind of a connection.";

	/**
	 * Connection to be changed.
	 */
	private final ConnectionEditPart connectionToChange;

	/**
	 * Connection, that is created during execution.
	 */
	private Connection createdConnection;

	/**
	 * Current used diagram view. It contains i.a. all {@link EditPart} shown.
	 */
	private final IDiagramGraphicalViewer diagramViewer;

	/**
	 * Type to be set for selected connections.
	 */
	private final IElementType setType;

	/**
	 * EMF edit domain of the edit parts to be manipulated.
	 */
	private final TransactionalEditingDomain modelEditingDomain;

	private CommandProxy createCommand;

	private org.eclipse.gef.commands.Command deleteCommand;

	/**
	 * Creates a new command, that deletes and recreates given connection with given type.
	 * 
	 * @param connection
	 *            The connection, whose type is to be changed.
	 * @param connectionType
	 *            The new type to be set.
	 */
	public SetConnectionTypeCommand(final ConnectionEditPart connection, final IElementType connectionType) {
		connectionToChange = connection;
		setType = connectionType;
		diagramViewer = (IDiagramGraphicalViewer) connectionToChange.getViewer();

		modelEditingDomain = connectionToChange.getEditingDomain();
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
		final CompoundCommand cmd = new CompoundCommand();
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
		// features will be needed for restoring later on
		final HashMap<EStructuralFeature, Object> featureMap = getFeatureMapCopy();

		deleteCommand = getDeleteCommand();
		deleteCommand.execute();

		try {
			final CreateConnectionViewRequest createConnReq = CreateViewRequestFactory.getCreateConnectionRequest(setType,
					getPreferencesHint());
			createCommand = new CommandProxy(getCreateCommand(createConnReq));
			createCommand.execute(null, null);

			// find new connection
			final EdgeImpl newEdge = (EdgeImpl) createConnReq.getConnectionViewDescriptor().getAdapter(EdgeImpl.class);
			createdConnection = (Connection) newEdge.getElement();

			getRestoreSemanticCommand(featureMap).execute();
		} catch (final ExecutionException e) {
			throw new VespucciUnexpectedException("Error during create command execution.",e);
		}
	}

	@Override
	public Collection<?> getAffectedObjects() {
		return getResult();
	}

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getLabel() {
		return CONTEXT_LABEL;
	}

	@Override
	public Collection<?> getResult() {
		final List<Connection> result = new ArrayList<Connection>();
		result.add(createdConnection);
		return result;
	}

	@Override
	public void redo() {
		if (!createCommand.canRedo()) {
			throw new VespucciUnexpectedException("Create command can not be redone.");
		}

		deleteCommand.redo();
		try {
			createCommand.redo(null, null);
		} catch (final ExecutionException e) {
			throw new VespucciUnexpectedException("Error during redo execution.",e);
		}
	}

	@Override
	public void undo() {
		createdConnection = null;

		if (!createCommand.canUndo() || !deleteCommand.canUndo()) {
			throw new VespucciUnexpectedException("Create command can not be undone.");
		}

		try {
			createCommand.undo(null, null);
		} catch (final ExecutionException e) {
			throw new VespucciUnexpectedException("Error during undo execution.",e);
		}
		deleteCommand.undo();
	}

	private org.eclipse.gef.commands.Command getDeleteCommand() {
		final GroupRequest deleteReq = new GroupRequest(org.eclipse.gef.RequestConstants.REQ_DELETE);
		return connectionToChange.getCommand(deleteReq);

	}

	/**
	 * Creates a command, that creates a connection identical to deleted connection, but with this
	 * element type. Undo/Redo is supported, when command is executed on a command stack.
	 * 
	 * @param connectionRequest
	 * 
	 * @return The command, that will create a new connection, when executed.
	 */
	private org.eclipse.gef.commands.Command getCreateCommand(final CreateConnectionRequest connectionRequest) {
		final EditPart sourceEditPart = connectionToChange.getSource();
		final EditPart targetEditPart = connectionToChange.getTarget();
		// prepare request
		connectionRequest.setTargetEditPart(targetEditPart);
		connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_START);
		connectionRequest.setLocation(connectionToChange.getConnectionFigure().getPoints().getFirstPoint());

		// only if the connection is supported will we get a non null
		// command from the sourceEditPart
		if (sourceEditPart.getCommand(connectionRequest) != null) {

			connectionRequest.setSourceEditPart(sourceEditPart);
			connectionRequest.setTargetEditPart(targetEditPart);
			connectionRequest.setType(org.eclipse.gef.RequestConstants.REQ_CONNECTION_END);
			connectionRequest.setLocation(connectionToChange.getConnectionFigure().getPoints().getLastPoint());
			// create command
			return targetEditPart.getCommand(connectionRequest);
		}

		return org.eclipse.gef.commands.UnexecutableCommand.INSTANCE;
	}

	private CompoundCommand getRestoreSemanticCommand(final HashMap<EStructuralFeature, Object> featureMap) {

		final CompoundCommand restoreCmd = new CompoundCommand();
		for (final Entry<EStructuralFeature, Object> entry : featureMap.entrySet()) {
			final SetCommand setCommand = new SetCommand(modelEditingDomain, createdConnection, entry.getKey(), entry.getValue());
			restoreCmd.append(setCommand);
		}
		return restoreCmd;
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

	/**
	 * @return Returns a all EFeatures, mapped with their values, of the connection to be changed.
	 */
	private HashMap<EStructuralFeature, Object> getFeatureMapCopy() {
		final HashMap<EStructuralFeature, Object> featureMap = new HashMap<EStructuralFeature, Object>();

		final EObject semanticConn = connectionToChange.resolveSemanticElement();
		//FIXME does not copy values. Current problem: original source/target history is deleted when delete command is executed. 
		for (final EStructuralFeature feature : semanticConn.eClass().getEAllStructuralFeatures()) {
			featureMap.put(feature, semanticConn.eGet(feature));
		}

		return featureMap;
	}
}

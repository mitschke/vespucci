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

import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.Log;
import org.eclipse.gmf.runtime.common.core.util.Trace;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIDebugOptions;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIPlugin;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIStatusCodes;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jdt.core.IJavaElement;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

/**
 * EditPolicy for creating new ensembles in a Vespucci-diagram from java
 * elements.
 * 
 * @author Ralf Mitschke
 * @author Malte Viering
 */
@SuppressWarnings("restriction")
public final class JavaElementDiagramDropPolicy extends CreationEditPolicy {

	/**
	 * This class understands request of the type REQ_DROPNEWENSEMBLE
	 */
	@Override
	public boolean understandsRequest(final Request request) {
		return IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE.equals(request
				.getType());
	}

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
			throw new IllegalStateException(
					"request should be CreateViewAndElementRequest instead of "
							+ request);

		// CreationEditPolicy.getCreateElementAndViewCommand only understands
		// requests of type REQ_CREATE
		request.setType(REQ_CREATE);
		Command createElementAndViewCommand = getCreateElementAndViewCommand((CreateViewAndElementRequest) request);
		if (!(createElementAndViewCommand instanceof ICommandProxy))
			throw new IllegalStateException(
					"gef should return ICommandProxy instead of "
							+ createElementAndViewCommand);

		ICommand createCommand = ((ICommandProxy) createElementAndViewCommand)
				.getICommand();
		Command setQueryCommand = createSetQueryCommand(request, createCommand);
		Command setNameCommand = createSetNameCommand(request, createCommand);

		CompoundCommand compound = new CompoundCommand();
		compound.add(createElementAndViewCommand);
		compound.add(setNameCommand);
		compound.add(setQueryCommand);

		return compound;
	}

	/**
	 * Returns a modified version of the SetValueCommand that is necessary
	 * because this command is used in a CompositeCommand that and needs data
	 * that will be created from previous command in the CompositeCommand.
	 */
	private Command createSetNameCommand(final Request request,
			final ICommand previousCommand) {

		return new DeferredSetValueCommand(previousCommand,
				Vespucci_modelPackage.eINSTANCE.getShape_Name(),
				createNameforNewEnsemble(request.getExtendedData()));
	}

	@SuppressWarnings("unchecked")
	private Command createSetQueryCommand(final Request request,
			final ICommand previousCommand) {
		return new DeferredSetValueCommand(previousCommand,
				Vespucci_modelPackage.eINSTANCE.getShape_Query(),
				QueryBuilder.createQueryFromRequestData(request
						.getExtendedData()));
	}

	private static String createNameforNewEnsemble(final Map<?, ?> data) {
		@SuppressWarnings("unchecked")
		List<ICodeElement> codeElements = (List<ICodeElement>) data
				.get(IJavaElementDropConstants.DROP_DATA_ICODEELEMENT);

		@SuppressWarnings("unchecked")
		List<IJavaElement> javaElements = (List<IJavaElement>) data
				.get(IJavaElementDropConstants.DROP_DATA_IJAVAELEMENT);

		if (!codeElements.isEmpty())
			return codeElements.get(0).getSimpleClassName();
		if (!javaElements.isEmpty())
			return javaElements.get(0).getElementName();

		throw new IllegalStateException(
				"Failed to create a name for droped ensemble due to empty element lists. Drop should have been disabled.");
	}

	/**
	 * Returns a modified version of the SetValueCommand that is necessary
	 * because this command is used in a CompositeCommand that and needs data
	 * that will be created from previous command in the CompositeCommand.
	 */
	private class DeferredSetValueCommand extends Command {

		private ICommand previousCommand;

		private SetValueCommand setValueCommand;

		private EStructuralFeature feature;
		private Object value;

		public DeferredSetValueCommand(ICommand previousCommand,
				EStructuralFeature feature, Object value) {
			super(null);
			this.previousCommand = previousCommand;
			this.feature = feature;
			this.value = value;
		}

		public void dispose() {
			if (setValueCommand != null)
				setValueCommand.dispose();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.commands.Command#canUndo()
		 */
		@Override
		public boolean canUndo() {
			if (setValueCommand != null)
				setValueCommand.canUndo();
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy#canExecute
		 * ()
		 */
		@Override
		public boolean canExecute() {
			if (setValueCommand == null)
				return true;
			return setValueCommand.canExecute();
		}

		/**
		 * gets the warapped <code>ICommand</code>
		 * 
		 * @return the wrapped command
		 */
		public ICommand getICommand() {
			if (setValueCommand != null)
				return setValueCommand;
			EObject returnValue = findNewElement(previousCommand
					.getCommandResult().getReturnValue());
			setValueCommand = new SetValueCommand(new SetRequest(returnValue,
					feature, value));
			return setValueCommand;
		}

		private EObject findNewElement(Object commandResult) {
			if (!(commandResult instanceof List<?>))
				throw new IllegalStateException(
						"Excpected List of command results and got : "
								+ commandResult);
			@SuppressWarnings("unchecked")
			List<Object> results = (List<Object>) commandResult;
			for (Object result : results) {
				if (!(result instanceof CreateElementRequestAdapter))
					continue;
				CreateElementRequestAdapter requestAdapter = (CreateElementRequestAdapter) result;
				return requestAdapter.resolve();
			}
			throw new IllegalStateException(
					"Failed to find a CreateElementRequest in the command results : "
							+ commandResult);
		}

		public void execute() {
			try {
				getICommand().execute(new NullProgressMonitor(), null);
			} catch (ExecutionException e) {
				Trace.catching(DiagramUIPlugin.getInstance(),
						DiagramUIDebugOptions.EXCEPTIONS_CATCHING, getClass(),
						"execute", e); //$NON-NLS-1$
				Log.error(DiagramUIPlugin.getInstance(),
						DiagramUIStatusCodes.COMMAND_FAILURE,
						e.getLocalizedMessage(), e);
			}
		}

		public void redo() {
			try {
				getICommand().redo(new NullProgressMonitor(), null);
			} catch (ExecutionException e) {
				Trace.catching(DiagramUIPlugin.getInstance(),
						DiagramUIDebugOptions.EXCEPTIONS_CATCHING, getClass(),
						"redo", e); //$NON-NLS-1$
				Log.error(DiagramUIPlugin.getInstance(),
						DiagramUIStatusCodes.COMMAND_FAILURE,
						e.getLocalizedMessage(), e);
			}
		}

		public void undo() {
			try {
				getICommand().undo(new NullProgressMonitor(), null);
			} catch (ExecutionException e) {
				Trace.catching(DiagramUIPlugin.getInstance(),
						DiagramUIDebugOptions.EXCEPTIONS_CATCHING, getClass(),
						"undo", e); //$NON-NLS-1$
				Log.error(DiagramUIPlugin.getInstance(),
						DiagramUIStatusCodes.COMMAND_FAILURE,
						e.getLocalizedMessage(), e);
			}
		}
	}
}

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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
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
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIDebugOptions;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIPlugin;
import org.eclipse.gmf.runtime.diagram.ui.internal.DiagramUIStatusCodes;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
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

		CreateViewAndElementRequest createViewAndElementRequest = (CreateViewAndElementRequest) request;

		// CreationEditPolicy.getCreateElementAndViewCommand only understands
		// requests of type REQ_CREATE
		request.setType(REQ_CREATE);
		Command createElementAndViewCommand = getCreateElementAndViewCommand(createViewAndElementRequest);

		if (!(createElementAndViewCommand instanceof ICommandProxy))
			throw new IllegalStateException(
					"gef should return ICommandProxy instead of "
							+ createElementAndViewCommand);

		ICommand createCommand = ((ICommandProxy) createElementAndViewCommand)
				.getICommand();

		Command setBoundsCommand = new ICommandProxy(new SetBoundsCommand(
				((IGraphicalEditPart) getHost()).getEditingDomain(),
				DiagramUIMessages.SetLocationCommand_Label_Resize,
				createViewAndElementRequest.getViewDescriptors().get(0),
				getBounds(createViewAndElementRequest)));

		Command setQueryCommand = createSetQueryCommand(
				createViewAndElementRequest, createCommand);
		Command setNameCommand = createSetNameCommand(
				createViewAndElementRequest, createCommand);

		CompoundCommand compound = new CompoundCommand();
		compound.add(createElementAndViewCommand);
		compound.add(setBoundsCommand);
		compound.add(setNameCommand);
		compound.add(setQueryCommand);
		compound.add(new ICommandProxy(new SelectAndEditNameCommand(
				(CreateViewAndElementRequest) request, getHost().getRoot()
						.getViewer())));
		return compound;
	}

	private Rectangle getBounds(CreateViewRequest request) {
		return new Rectangle(request.getLocation(), new Dimension(-1, -1));

	}

	/**
	 * Returns a modified version of the SetValueCommand that is necessary
	 * because this command is used in a CompositeCommand that and needs data
	 * that will be created from previous command in the CompositeCommand.
	 */
	private Command createSetNameCommand(final Request request,
			final ICommand previousCommand) {
		return new DeferredSetValueCommand(new DefferedValueGetter() {
			@Override
			public EObject getValue() {
				return findReturnElement(previousCommand);
			}
		}, Vespucci_modelPackage.eINSTANCE.getAbstractEnsemble_Name(),
				createNameforNewEnsemble(request.getExtendedData()));
	}

	/**
	 * Returns a modified version of the SetValueCommand that is necessary
	 * because this command is used in a CompositeCommand that and needs data
	 * that will be created from previous command in the CompositeCommand.
	 */
	@SuppressWarnings("unchecked")
	private Command createSetQueryCommand(final Request request,
			final ICommand previousCommand) {
		return new DeferredSetValueCommand(new DefferedValueGetter() {
			@Override
			public EObject getValue() {
				return findReturnElement(previousCommand);
			}
		}, Vespucci_modelPackage.eINSTANCE.getAbstractEnsemble_Query(),
				QueryBuilder.createQueryFromRequestData(request
						.getExtendedData()));
	}

	private static EObject findReturnElement(ICommand previousCommand) {
		if (!(previousCommand.getCommandResult().getReturnValue() instanceof List<?>))
			throw new IllegalStateException(
					"Excpected List of command results and got : "
							+ previousCommand.getCommandResult()
									.getReturnValue());
		@SuppressWarnings("unchecked")
		List<Object> results = (List<Object>) previousCommand
				.getCommandResult().getReturnValue();
		for (Object result : results) {
			if (!(result instanceof CreateElementRequestAdapter))
				continue;
			CreateElementRequestAdapter requestAdapter = (CreateElementRequestAdapter) result;
			return requestAdapter.resolve();
		}
		throw new IllegalStateException(
				"Failed to find a CreateElementRequest in the command results : "
						+ previousCommand.getCommandResult().getReturnValue());
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

	private interface DefferedValueGetter {
		public EObject getValue();
	}

	/**
	 * A modified version of the SetValueCommand that is necessary because this
	 * command is used in a CompositeCommand that and needs data that will be
	 * created from previous command in the CompositeCommand.
	 */
	private class DeferredSetValueCommand extends Command {

		private DefferedValueGetter getter;

		private SetValueCommand setValueCommand;

		private EStructuralFeature feature;

		private Object value;

		public DeferredSetValueCommand(DefferedValueGetter getter,
				EStructuralFeature feature, Object value) {
			super(null);
			this.getter = getter;
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
			EObject returnValue = getter.getValue();
			setValueCommand = new SetValueCommand(new SetRequest(returnValue,
					feature, value));
			return setValueCommand;
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

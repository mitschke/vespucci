package de.tud.cs.st.vespucci.diagram.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.helpers.VespucciBaseEditHelper;

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

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection currentSelection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		final Object[] currentSelectionArr = currentSelection.toArray();

		// This array will contain the casted selection-objects.
		final ConnectionEditPart[] selectedConnections = new ConnectionEditPart[currentSelection.size()];

		for (int i = 0; i < currentSelection.size(); ++i) {
			if (currentSelectionArr[i] instanceof ConnectionEditPart) {
				selectedConnections[i] = (ConnectionEditPart) currentSelectionArr[i];
			} else {
				// If this exception is reached, then there should be something wrong with the
				// visibleWhen entry of the popUp-menu.
				return new ExecutionException("Selection is not a connection!");
			}
		}
		
		// An array containing all EFEatures for all selected connections.
		EList<EStructuralFeature>[] connFeatures = new EList[selectedConnections.length];
		// An array containing all the values associated with the EFeatures in same order
		List<Object>[] connValues = new List[selectedConnections.length];
		
		// Save information of the connections to be destroyed.
		for(int i = 0; i < selectedConnections.length; ++i){
			final EObject conn = selectedConnections[i].resolveSemanticElement();
			connFeatures[i] = conn.eClass().getEAllStructuralFeatures();
			
			List<Object> values = new LinkedList<Object>();
			for(EStructuralFeature feature: connFeatures[i]){
				values.add(conn.eGet(feature));
			}
			connValues[i] = values;
		}

		// destroy connections
		for(ConnectionEditPart conn: selectedConnections){
//			DestroyElementRequest request = new DestroyElementRequest(conn.resolveSemanticElement(), false);
//			EditCommandRequestWrapper request_wrap = new EditCommandRequestWrapper(RequestConstants.REQ_SEMANTIC_WRAPPER, request);
//			org.eclipse.gef.commands.Command command = conn.getCommand(request_wrap);
//			command.execute();
//			conn.getDiagramEditDomain()
//			VespucciBaseEditHelper helper = new VespucciBaseEditHelper();
//			helper.getEditCommand(request);
//			conn.getEditingDomain().getCommandStack().execute(helper.getEditCommand(request));
//			DiagramCommandStack cmdStack = conn.getDiagramEditDomain().getDiagramCommandStack();
//			cmdStack.execute(helper.getEditCommand(request));
		}
		
		// recreate connections
		//TODO
		return null;
	}

}
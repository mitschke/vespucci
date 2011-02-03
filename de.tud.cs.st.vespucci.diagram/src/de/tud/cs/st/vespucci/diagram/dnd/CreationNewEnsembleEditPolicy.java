package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SemanticCreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RefreshConnectionsRequest;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

final public class CreationNewEnsembleEditPolicy extends org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy{

	/**
	 * This a modified version of the getCreateElementAndViewcommand of CreationEditPolicy
	 * 	this version should only be use in the context of 
	 * 		creating new Ensembles out of a droped file
	 * Method creates a CompositeCommand that include :
	 * 	- a create view element command
	 *  - a create semantic element command
	 *  - two setvalue commands
	 *  
	 * @param request
	 * @return Command that creates the sematnic and the view command for the
	 *         given CreateViewAndElementRequest
	 */
	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		// copied Content
		
		// get the element descriptor
		CreateElementRequestAdapter requestAdapter =
			request.getViewAndElementDescriptor().getCreateElementRequestAdapter();
		
		// get the semantic request
		
		 CreateElementRequest createElementRequest = 
			(CreateElementRequest) requestAdapter.getAdapter(
				CreateElementRequest.class);
		
		if (createElementRequest.getContainer() == null) {
			// complete the semantic request by filling in the host's semantic
			// element as the context
			View view = (View)getHost().getModel();
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

		// get the create element command based on the elementdescriptor's
		// request
		Command createElementCommand =
			getHost().getCommand(
				new EditCommandRequestWrapper(
					(CreateElementRequest)requestAdapter.getAdapter(
						CreateElementRequest.class), request.getExtendedData()));

		if (createElementCommand == null) { 
			return UnexecutableCommand.INSTANCE;
		}		
		if(!createElementCommand.canExecute()){
			return createElementCommand;
		}

		// create the semantic create wrapper command
		SemanticCreateCommand semanticCommand =
			new SemanticCreateCommand(requestAdapter, createElementCommand);
		Command viewCommand = getCreateCommand(request);

		Command refreshConnectionCommand =
			getHost().getCommand(
				new RefreshConnectionsRequest(((List)request.getNewObject())));
		// form the compound command and return
        CompositeCommand cc = new CompositeCommand(semanticCommand.getLabel());
		cc.compose(semanticCommand);
		cc.compose(new CommandProxy(viewCommand));
		 
		if ( refreshConnectionCommand != null ) {
			cc.compose(new CommandProxy(refreshConnectionCommand));
		}
		//end copied content
		//create two setValuecomand to set the Query and the name of the New Ensamble 
		EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
		.getEPackage("http://vespucci.editor");
		Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;

		SetRequest sr = new SetRequest(createElementRequest.getEditingDomain(),createElementRequest.getNewElement(), vesPackage.getShape_Query(), "query test");
		extendedSetValueCommand svc = new extendedSetValueCommand(sr,createElementRequest);
		
		SetRequest sr2 = new SetRequest(createElementRequest.getEditingDomain(),createElementRequest.getNewElement(), vesPackage.getShape_Name(), "name test");
		extendedSetValueCommand svc2 = new extendedSetValueCommand(sr2,createElementRequest);
		
		
		cc.compose(svc);
		cc.compose(svc2);
		return new ICommandProxy(cc);
	}


	

}

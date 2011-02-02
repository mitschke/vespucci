package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;

public class CreationNewEnsembleEditPolicy extends org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy{

	@Override
	public Command getCommand(Request request) {
		if (understandsRequest(request)) {
			if (request instanceof CreateUnspecifiedTypeRequest) {
			
			} else if (request instanceof CreateViewAndElementRequest) {
				return getCreateElementAndViewCommand(
					(CreateViewAndElementRequest)request);
			}
			else if (request instanceof CreateViewRequest) {
				return getCreateCommand((CreateViewRequest)request);
			}
			else if (request instanceof ChangeBoundsRequest) {
				return getReparentCommand((ChangeBoundsRequest)request);
			}
			return super.getCommand(request);
		}
		return null;
	}

	@Override
	public boolean understandsRequest(Request request) {
		// TODO Auto-generated method stub
		return super.understandsRequest(request);
	}

	@Override
	protected Command getCreateCommand(CreateViewRequest request) {
		// TODO Auto-generated method stub
		return super.getCreateCommand(request);
	}
	

}

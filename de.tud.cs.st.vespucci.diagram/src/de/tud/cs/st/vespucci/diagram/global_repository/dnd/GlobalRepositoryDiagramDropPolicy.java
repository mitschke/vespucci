package de.tud.cs.st.vespucci.diagram.global_repository.dnd;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;

public final class GlobalRepositoryDiagramDropPolicy extends CreationEditPolicy {

	public boolean understandRequest(final Request request) {
		// TODO: implementMe
		return false;
	}

	public Command getCommand(Request request) {
		if (!understandRequest(request))
			return null;

		if (!(request instanceof CreateViewAndElementRequest))
			throw new IllegalStateException(
					"gef should be CreateViewAndElementRequest instead of "
							+ request);
		
		CreateViewAndElementRequest createViewAndElementRequest = (CreateViewAndElementRequest) request;
		
		request.setType(REQ_CREATE);
		Command createElementAndViewCommand = getCreateElementAndViewCommand(createViewAndElementRequest);
		
		//TODO: implementMe
		
		return null;
	}
	
}

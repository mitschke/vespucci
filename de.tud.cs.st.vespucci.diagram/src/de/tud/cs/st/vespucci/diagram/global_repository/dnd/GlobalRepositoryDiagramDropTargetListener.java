package de.tud.cs.st.vespucci.diagram.global_repository.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.swt.dnd.DND;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;
import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

public class GlobalRepositoryDiagramDropTargetListener extends AbstractGlobalRepositoryDropTargetListener{

	public GlobalRepositoryDiagramDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected Request createTargetRequest(){
		PreferencesHint hint = VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
		
		ViewAndElementDescriptor viewAndElementDescriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(new CreateElementRequestAdapter(new CreateElementRequest(VespucciElementTypes.Ensemble_2001)), Node.class, "2001", hint);
		
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(viewAndElementDescriptor);
		
		//TODO: IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE ?
		
		request.setLocation(getDropLocation());
		
		addTrensferredSelectionToRequest(request);
		return request;
	}
	
	@Override
	protected void handleDragOver(){
		super.handleDragOver();
		if(getCurrentEvent().detail != DND.DROP_NONE)
			getCurrentEvent().detail = DND.DROP_LINK;
	}

	@Override
	protected void updateTargetRequest() {
		if (getTargetRequest() instanceof CreateRequest){
			((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
		}
		
	}

}

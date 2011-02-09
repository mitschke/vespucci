package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.swt.dnd.DropTargetEvent;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

public class CreateEnsembleDropTargetListener extends
		DropVespucciDiagramTargetListener {
	public static final String REQ_DROPNEWENSEMBLE = "create new Ensemble xxx"; 
	public CreateEnsembleDropTargetListener(EditPartViewer viewer) {
		super(viewer);

	}
	@Override
	protected void handleDrop() {
		super.handleDrop();
	}

	@Override
	public boolean isEnabled(DropTargetEvent event) {
		return super.isEnabled(event);
	}

	@Override
	protected void updateTargetRequest() {
		((CreateViewRequest) getTargetRequest()).setLocation(getDropLocation());
		super.updateTargetEditPart();
	}

	@Override
	protected Request createTargetRequest() {
		//DirectEditRequest request = new DirectEditRequest();
		EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
		.getEPackage("http://vespucci.editor");
		Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;
		IElementType elementType = ElementTypeRegistry.getInstance().getElementType(vesPackage.getEnsemble());
		//TODO is there a way to get VespucciElementTypes.Ensemble_2001 over a methode call?
		elementType = VespucciElementTypes.Ensemble_2001;
		// Get the selected edit part
		EditPart epart = getTargetEditPart();
		if(epart == null)
			return new CreateViewRequest(new CreateViewRequest.ViewDescriptor(null,null));
		//GraphicalEditPart p = (GraphicalEditPart) epart;
		DiagramEditPart p = (DiagramEditPart) epart;		
		CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(elementType, p.getDiagramPreferencesHint());
		request.setType(REQ_DROPNEWENSEMBLE);
		return request;
}
}

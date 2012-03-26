package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
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

/**
 * This listener allows dropping java elements on the diagram itself.
 * 
 * An initial check if the drop is allowed is done in
 * {@link AbstractJavaElementDropTargetListener}. Further the
 * {@link AbstractTransferDropTargetListener} creates the {@link Request} of the
 * overrriden this.createTargetRequest() and verifies that an {@link EditPolicy}
 * , in this case {@link JavaElementDiagramDropPolicy}, is registered for the
 * request.
 * 
 * The drag icon is changed to always show the link icon (little arrow sign)
 * over the target.
 * 
 * 
 * @author Ralf Mitschke
 * 
 */
public class JavaElementDiagramDropTargetListener extends
		AbstractJavaElementDropTargetListener {

	public JavaElementDiagramDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	protected Request createTargetRequest() {
		// The hint is required during element creation and may not be null.
		PreferencesHint hint = VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;

		ViewAndElementDescriptor viewAndElementDescriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(
				new CreateElementRequestAdapter(new CreateElementRequest(
						VespucciElementTypes.Ensemble_2003)), Node.class,
				"2003", hint);
		/*
		 * Important Note! The viewAndElementDescriptor is required during the
		 * creation and not correctly filled if other constructors of
		 * CreateViewAndElementRequest are used. Especially the semanticHint =
		 * "2003" must be set for the diagram element factory to know that it is
		 * responsible for providing elements of the chosen type.
		 */
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(
				viewAndElementDescriptor);

		/*
		 * our own creation policy will fill in ensemble name and query, hence
		 * we use our own request type, to ensure that only our policy will
		 * process the request.
		 */
		request.setType(IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE);

		request.setLocation(getDropLocation());
		
		addTransferedSelectionToRequest(request);
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		if (getCurrentEvent().detail != DND.DROP_NONE)
			getCurrentEvent().detail = DND.DROP_LINK;
	}

	@Override
	protected void updateTargetRequest() {
		if (getTargetRequest() instanceof CreateRequest) {
			((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
		}

	}
}

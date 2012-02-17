package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
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
		PreferencesHint hint = VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;

		ViewAndElementDescriptor viewAndElementDescriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(
				new CreateElementRequestAdapter(new CreateElementRequest(
						VespucciElementTypes.Ensemble_2001)), Node.class,
				"2001", hint);
		Request request = new CreateViewAndElementRequest(
				viewAndElementDescriptor);

		request.setType(IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE);
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
}

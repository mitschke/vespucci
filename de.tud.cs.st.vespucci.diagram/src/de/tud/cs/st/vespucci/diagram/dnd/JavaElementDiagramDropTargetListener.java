package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.swt.dnd.DND;

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
		Request request = new Request(
				IJavaElementDropConstants.REQ_DROP_NEW_ENSEMBLE);
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

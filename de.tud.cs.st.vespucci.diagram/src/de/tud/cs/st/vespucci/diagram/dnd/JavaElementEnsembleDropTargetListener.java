package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.swt.dnd.DND;

/**
 * This listener allows dropping java elements on ensembles.
 * 
 * An initial check if the drop is allowed is done in
 * {@link AbstractJavaElementDropTargetListener}. Further the
 * {@link AbstractTransferDropTargetListener} creates the {@link Request} of the
 * overrriden this.createTargetRequest() and verifies that an {@link EditPolicy}
 * , in this case {@link JavaElementEnsembleDropPolicy}, is registered for the
 * request.
 * 
 * The drag icon is changed to always show the copy icon (little plus sign) over
 * the target.
 * 
 * @author Ralf Mitschke
 * 
 */
public class JavaElementEnsembleDropTargetListener extends
		AbstractJavaElementDropTargetListener {

	public JavaElementEnsembleDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}

	/**
	 * Creates a DirectEditRequest of type
	 * IJavaElementDropConstants.REQ_DROP_EXTEND_ENSEMBLE.
	 * 
	 * Since we use a DirectEditPolicy, we need a DirectEditRequest But the
	 * request still has our own defined type and will not be understood by
	 * built-in policies, but only by ourselves.
	 */
	@Override
	protected Request createTargetRequest() {
		Request request = new DirectEditRequest(
				IJavaElementDropConstants.REQ_DROP_EXTEND_ENSEMBLE);
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
			getCurrentEvent().detail = DND.DROP_COPY;
	}

	@Override
	protected void updateTargetRequest() {
		if (getTargetRequest() instanceof LocationRequest) {
			((LocationRequest) getTargetRequest())
					.setLocation(getDropLocation());
		}
	}
}

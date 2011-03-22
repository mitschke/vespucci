/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart;

/**
 * A TransferDropTargetListener for handling the drop of ISelections on the VespucciDiagram view
 * 
 * the drop is enable if it can be interpreted as a query 
 * 
 * The data of the drop is storted in extendedData
 * 
 * @author MalteV
 */
public class DropVespucciDiagramTargetListener extends
		AbstractTransferDropTargetListener {
	/**
	 * Constructor that set the drop type to IRecource
	 * 
	 * @param viewer
	 */
	public DropVespucciDiagramTargetListener(EditPartViewer viewer) {
		super(viewer, LocalSelectionTransfer.getTransfer());
	}

	@Override
	protected void handleDragOver() {
		setData();
		if (enable()) {
			// The drop target can handle the drop
			getCurrentEvent().detail = DND.DROP_COPY;

		} else {
			// The dorp target can't handle the drop
			getCurrentEvent().detail = DND.DROP_NONE;
		}
		super.handleDragOver();

	}

	@Override
	protected void handleDrop() {
		setData();
		if (enable()) {
			getCurrentEvent().detail = DND.DROP_COPY;

		} else {
			getCurrentEvent().detail = DND.DROP_NONE;
		}
		super.handleDrop();
	}

	/**
	 * set the drop
	 */
	protected void setData() {
		ISelection selection = LocalSelectionTransfer.getTransfer()
				.getSelection();
		Map<String, Object> m = new HashMap<String, Object>();
		for (Object o : ((TreeSelection) selection).toList())
			m.put(o.toString(), o);
		getTargetRequest().setExtendedData(m);
	}

	@Override
	public boolean isEnabled(DropTargetEvent event) {
		return super.isEnabled(event);
	}

	/**
	 * check if the drop is processable
	 * @return
	 */
	private boolean enable() {
		if (getTargetEditPart() == null) {
			;
			// alle EditPart fuer die Drop funktionieren soll
		} else if (getTargetEditPart() instanceof EnsembleEditPart
				|| getTargetEditPart() instanceof Ensemble2EditPart
				|| getTargetEditPart() instanceof ShapesDiagramEditPart) {
			return QueryBuilder.isProcessable(getTargetRequest()
					.getExtendedData());
		}
		return false;

	}

	@Override
	protected void updateTargetRequest() {
		if (getTargetRequest() instanceof LocationRequest)
			((LocationRequest) getTargetRequest())
					.setLocation(getDropLocation());
		else if (getTargetRequest() instanceof CreateRequest)
			((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
		if (!enable()) {
			getCurrentEvent().detail = DND.DROP_NONE;
		} else {
			getCurrentEvent().detail = DND.DROP_COPY;
		}

		super.updateTargetEditPart();
	}

	@Override
	protected Request createTargetRequest() {
		DirectEditRequest request = new DirectEditRequest();
		return request;
	}

}

/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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
 *     Universität Darmstadt nor the names of its contributors may be used to
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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.dnd.DND;

import de.tud.cs.st.vespucci.diagram.dnd.JavaType.Resolver;
import de.tud.cs.st.vespucci.diagram.dnd.patterns.DesignPatternRoutine;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart;

/**
 * A TransferDropTargetListener for handling the drop of ISelections on the
 * VespucciDiagram view. The drop is enabled if it can be interpreted as a
 * query. The data of the drop is stored in extendedData.
 * 
 * @author Malte Viering
 */
public class DropVespucciDiagramTargetListener extends
	AbstractTransferDropTargetListener {

    /**
     * @see {@link AbstractTransferDropTargetListener#AbstractTransferDropTargetListener(EditPartViewer, org.eclipse.swt.dnd.Transfer)}
     * @param viewer
     */
    public DropVespucciDiagramTargetListener(final EditPartViewer viewer) {
	super(viewer, LocalSelectionTransfer.getTransfer());
    }

    @Override
    protected Request createTargetRequest() {
	return new DirectEditRequest();
    }

    @Override
    protected void handleDragOver() {
	setDrop();
	setEventOperation();
	super.handleDragOver();
    }

    @Override
    protected void handleDrop() {
	setDrop();
	setEventOperation();
	super.handleDrop();
    }

    /**
     * @return Returns true only if the drop is resolvable.
     */
    private boolean canBeDropped() {
	if (getTargetEditPart() == null) {

	    // all EditPart for which DnD should work
	} else if (getTargetEditPart() instanceof EnsembleEditPart
		|| getTargetEditPart() instanceof Ensemble2EditPart
		|| getTargetEditPart() instanceof ShapesDiagramEditPart) {
	    return Resolver.isResolvable(getTargetRequest().getExtendedData()
		    .values());
	}
	return false;
    }

    /**
     * Sets the drop.
     */
    protected void setDrop() {

	// Caching to check whether a Design Pattern routine shall be performed
	cacheCurrentDiagramAndEnsembles();

	final ISelection selection = LocalSelectionTransfer.getTransfer()
		.getSelection();
	final Map<String, Object> m = new HashMap<String, Object>();
	for (final Object o : ((TreeSelection) selection).toList()) {
	    m.put(o.toString(), o);
	}
	getTargetRequest().setExtendedData(m);
    }

    /**
     * 
     */
    private void cacheCurrentDiagramAndEnsembles() {
	EditPart e = getTargetEditPart();
	if (e instanceof ShapesDiagramEditPart) {
	    DesignPatternRoutine
		    .lazyStoreDiagramAndEnsembles((ShapesDiagramEditPart) e);
	}
    }

    private void setEventOperation() {
	if (canBeDropped()) {
	    getCurrentEvent().detail = DND.DROP_COPY;
	} else {
	    getCurrentEvent().detail = DND.DROP_NONE;
	}

    }

    @Override
    protected void updateTargetRequest() {
	if (getTargetRequest() instanceof LocationRequest) {
	    ((LocationRequest) getTargetRequest())
		    .setLocation(getDropLocation());
	} else if (getTargetRequest() instanceof CreateRequest) {
	    ((CreateRequest) getTargetRequest()).setLocation(getDropLocation());
	}
	setEventOperation();
	super.updateTargetEditPart();
    }

}

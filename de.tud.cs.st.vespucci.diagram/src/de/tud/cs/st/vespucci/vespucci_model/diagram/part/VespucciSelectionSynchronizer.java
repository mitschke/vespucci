/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author ArtemVovk
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * SelectionSynchronizer for view components
 * 
 * @author a_vovk
 * 
 */
public class VespucciSelectionSynchronizer extends SelectionSynchronizer {

	private List<EditPartViewer> viewers = new ArrayList<EditPartViewer>();
	private boolean isDispatching = false;
	private int disabled = 0;
	private EditPartViewer pendingSelection;

	/**
	 * Adds a viewer to the set of synchronized viewers
	 * 
	 * @param viewer
	 *            the viewer
	 */
	public void addViewer(EditPartViewer viewer) {
		viewer.addSelectionChangedListener(this);
		viewers.add(viewer);
	}

	/**
	 * Maps the given editpart from one viewer to an editpart in another viewer.
	 * It returns <code>null</code> if there is no corresponding part. This
	 * method can be overridden to provide custom mapping.
	 * 
	 * @param viewer
	 *            the viewer being mapped to
	 * @param part
	 *            a part from another viewer
	 * @return <code>null</code> or a corresponding editpart
	 */
	protected List<EditPart> converter(EditPartViewer viewer, EditPart part) {
		List<EditPart> parts = new ArrayList<EditPart>();

		EditPart temp = (EditPart) viewer.getEditPartRegistry().get(
				part.getModel());
		if (temp != null) {
			int selectionCounter = 0;
			if (viewer instanceof TreeViewer) {
				List<?> editParts = viewer.getSelectedEditParts();
				for (Object i : editParts) {
					EditPart pp = (EditPart) i;
					if (pp.getModel() == temp.getModel()) {
						selectionCounter++;
						parts.add(pp);
					}
				}
				if (selectionCounter == 1) {
					return parts;
				} else if (selectionCounter > 1) {
					parts.add(temp);
					return parts;
				}
			}
			EditPart newPart = null;

			newPart = (EditPart) temp;
			parts.add(newPart);
		}
		return parts;
	}

	/**
	 * Removes the viewer from the set of synchronized viewers
	 * 
	 * @param viewer
	 *            the viewer to remove
	 */
	public void removeViewer(EditPartViewer viewer) {
		viewer.removeSelectionChangedListener(this);
		viewers.remove(viewer);
		if (pendingSelection == viewer)
			pendingSelection = null;
	}

	/**
	 * Receives notification from one viewer, and maps selection to all other
	 * members.
	 * 
	 * @param event
	 *            the selection event
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (isDispatching)
			return;
		EditPartViewer source = (EditPartViewer) event.getSelectionProvider();
		if (disabled > 0) {
			pendingSelection = source;
		} else {
			ISelection selection = event.getSelection();
			syncSelection(source, selection);
		}
	}

	private void syncSelection(EditPartViewer source, ISelection selection) {
		isDispatching = true;
		for (int i = 0; i < viewers.size(); i++) {
			if (viewers.get(i) != source) {
				EditPartViewer viewer = (EditPartViewer) viewers.get(i);
				setViewerSelection(viewer, selection);
			}
		}
		isDispatching = false;
	}

	/**
	 * Enables or disabled synchronization between viewers.
	 * 
	 * @since 3.1
	 * @param value
	 *            <code>true</code> if synchronization should occur
	 */
	public void setEnabled(boolean value) {
		if (!value)
			disabled++;
		else if (--disabled == 0 && pendingSelection != null) {
			syncSelection(pendingSelection, pendingSelection.getSelection());
			pendingSelection = null;
		}
	}

	private void setViewerSelection(EditPartViewer viewer, ISelection selection) {
		ArrayList<EditPart> result = new ArrayList<EditPart>();
		@SuppressWarnings("unchecked")
		Iterator<EditPart> iter = ((IStructuredSelection) selection).iterator();
		while (iter.hasNext()) {
			List<EditPart> parts = converter(viewer, (EditPart) iter.next());
			if (parts != null)
				result.addAll(parts);
		}
		viewer.setSelection(new StructuredSelection(result));
		if (result.size() > 0)
			viewer.reveal((EditPart) result.get(result.size() - 1));
	}
}

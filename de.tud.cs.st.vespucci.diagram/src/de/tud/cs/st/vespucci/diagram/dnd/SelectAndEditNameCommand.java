/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
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

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Focus ensemble and select its name field.
 * 
 * @author Patrick Jahnke
 * @author Thomas Schulz
 * @author Alexander Weitzmann
 */
public class SelectAndEditNameCommand extends AbstractCommand {

	private static final String COMMANDNAME = "Select ensemble name-field";

	/**
	 * Reveals the newly created EditPart. {@link #request} must be initialized before calling this method.
	 * 
	 * @param editPart
	 */
	private static void revealEditPart(final EditPart editPart) {
		if (editPart != null && editPart.getViewer() != null) {
			editPart.getViewer().reveal(editPart);
		}
	}

	private static void selectDiagramEditor() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().setFocus();
	}

	private final EditPartViewer editPartViewer;

	private final CreateViewAndElementRequest request;

	/**
	 * Initializes fields.
	 * 
	 * @param request
	 *            This request must provide the view descriptors for the EditPart to be selected. If multiple view
	 *            descriptors exist then the first will be used. See also
	 *            {@link CreateViewAndElementRequest#getViewDescriptors()}.
	 * @param diagramViewer
	 *            The graphical viewer of the diagram.
	 */
	public SelectAndEditNameCommand(final CreateViewAndElementRequest request, final EditPartViewer diagramViewer) {
		super(COMMANDNAME);
		this.request = request;
		editPartViewer = diagramViewer;
	}

	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) {

		if (editPartViewer != null && request != null) {
			selectDiagramEditor();

			// set Focus on the added Ensemble and select the Name
			selectAddedObject();

			return CommandResult.newOKCommandResult();
		}

		return CommandResult.newErrorCommandResult("Command was not executeable.\n Please see canExecute in EditNameCommand.");

	}

	@Override
	protected CommandResult doRedoWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) {
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(final IProgressMonitor progressMonitor, final IAdaptable info) {
		return null;
	}

	private EditPart getFirstEditPartFromRequest() {
		final List<? extends ViewDescriptor> editPartViewDescriptors = request.getViewDescriptors();

		for (final ViewDescriptor object : editPartViewDescriptors) {
			final EditPart editPart = (EditPart) editPartViewer.getEditPartRegistry().get(object.getAdapter(View.class));
			if (editPart != null) {
				return editPart;
			}
		}
		return null;
	}

	/**
	 * Select the newly added shape view by default. Adapted from {@link org.eclipse.gef.tools.CreationTool}.
	 */
	protected void selectAddedObject() {
		final EditPart editPart = getFirstEditPartFromRequest();
		if (editPart != null) {
			final EditPart[] editParts = { editPart };
			editPartViewer.setSelection(new StructuredSelection(editParts));

			// automatically put the first shape into edit-mode
			Display.getCurrent().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (editPart.isActive()) {
						editPart.performRequest(new Request(RequestConstants.REQ_DIRECT_EDIT));
						revealEditPart(editPart);
					}
				}
			});
		}
	}
}

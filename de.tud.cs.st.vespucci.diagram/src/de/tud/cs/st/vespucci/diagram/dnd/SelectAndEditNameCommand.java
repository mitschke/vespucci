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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author Patrick
 * 
 */
public class SelectAndEditNameCommand extends AbstractCommand {
	private static final String COMMANDNAME = "SELECTNAMECOMMAND";
	private CreateViewAndElementRequest createViewAndElementRequest = null;
	private EditPartViewer editPartViewer = null;

	public SelectAndEditNameCommand(
			CreateViewAndElementRequest createViewAndElementRequest,
			EditPartViewer editPartViewer) {
		super(COMMANDNAME);
		this.createViewAndElementRequest = createViewAndElementRequest;
		this.editPartViewer = editPartViewer;
	}

	@Override
	protected CommandResult doExecuteWithResult(
			IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {

		if ((editPartViewer != null)
				&& ((Collection) createViewAndElementRequest.getNewObject() != null)) {
			// select the Diagram Editor
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().setFocus();

			// set Focus on the added Ensemble and select the Name
			selectAddedObject(editPartViewer,
					(Collection) createViewAndElementRequest
							.getViewDescriptors());

			return CommandResult.newOKCommandResult();
		}

		return CommandResult
				.newErrorCommandResult("Command was not executeable\n pleas see canExecute in EditNameCommand");

	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		return null;
	}

	/**
	 * Select the newly added shape view by default
	 * 
	 * @param viewer
	 * @param objects
	 */
	protected void selectAddedObject(EditPartViewer viewer, Collection objects) {
		final List editparts = new ArrayList();
		for (Iterator i = objects.iterator(); i.hasNext();) {
			Object object = i.next();
			if (object instanceof IAdaptable) {
				Object editPart = viewer.getEditPartRegistry().get(
						((IAdaptable) object).getAdapter(View.class));
				if (editPart != null)
					editparts.add(editPart);
			}
		}

		if (!editparts.isEmpty()) {
			viewer.setSelection(new StructuredSelection(editparts));

			// automatically put the first shape into edit-mode
			Display.getCurrent().asyncExec(new Runnable() {
				public void run() {
					EditPart editPart = (EditPart) editparts.get(0);
					//
					// add active test since test scripts are failing on this
					// basically, the editpart has been deleted when this
					// code is being executed. (see RATLC00527114)
					if (editPart.isActive()) {
						editPart.performRequest(new Request(
								RequestConstants.REQ_DIRECT_EDIT));
						revealEditPart((EditPart) editparts.get(0));
					}
				}
			});
		}
	}

	/**
	 * Reveals the newly created editpart
	 * 
	 * @param editPart
	 */
	protected void revealEditPart(EditPart editPart) {
		if ((editPart != null) && (editPart.getViewer() != null))
			editPart.getViewer().reveal(editPart);
	}
}

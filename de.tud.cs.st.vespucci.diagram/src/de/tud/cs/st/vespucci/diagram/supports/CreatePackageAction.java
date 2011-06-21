/** License (BSD Style License):
 *  Copyright (c) 2010
 *  Software Engineering
 *  Department of Computer Science
 *  Technische Universitï¿½t Darmstadt
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Engineering Group or Technische 
 *    Universitï¿½t Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.supports;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

/**
 * Responsibility: create new package object called from context menu.
 * 
 * @author Tam-Minh Nguyen
 * 
 */
public class CreatePackageAction implements IObjectActionDelegate {

    public final static String ID = "addPackageActionID";

    private DiagramEditPart selectedElement;

    public void run(IAction action) {
	CreateViewRequest packageRequest = CreateViewRequestFactory.getCreateShapeRequest(
		VespucciElementTypes.Ensemble_2001, selectedElement.getDiagramPreferencesHint());

	packageRequest.setLocation(EPService.RECENT_MOUSE_RIGHT_CLICK_POSITION);

	Command createCommand = selectedElement.getCommand(packageRequest);
	IAdaptable viewAdapter = (IAdaptable) ((List<?>) packageRequest.getNewObject()).get(0);

	selectedElement.getDiagramEditDomain().getDiagramCommandStack().execute(createCommand);

	// put the new topic in edit mode
	final EditPartViewer viewer = selectedElement.getViewer();
	final EditPart elementPart = (EditPart) viewer.getEditPartRegistry().get(
		viewAdapter.getAdapter(View.class));
	if (elementPart != null) {
	    Display.getCurrent().asyncExec(new Runnable() {

		public void run() {
		    viewer.setSelection(new StructuredSelection(elementPart));
		    Request der = new Request(RequestConstants.REQ_DIRECT_EDIT);
		    elementPart.performRequest(der);
		}
	    });
	}

    }

    public void selectionChanged(IAction action, ISelection selection) {
	selectedElement = null;
	if (selection instanceof IStructuredSelection) {
	    IStructuredSelection structuredSelection = (IStructuredSelection) selection;
	    if (structuredSelection.getFirstElement() instanceof DiagramEditPart) {
		selectedElement = (DiagramEditPart) structuredSelection.getFirstElement();
	    }
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     * org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

}

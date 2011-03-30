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

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;
import de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes;

/**
 * TransferDropTargetListener for handling the transfer of ISelections
 * This TranferDropTargetListener creates a CreateViewRequest
 * that should be processed by {@link de.tud.cs.st.vespucci.diagram.dnd.CreationNewEnsembleEditPolicy CreationNewEnsembelEditPolicy}
 * to create a new Ensemble
 * 
 * @author MalteV
 */
public class CreateEnsembleDropTargetListener extends
		DropVespucciDiagramTargetListener {
	
	public static final String REQ_DROPNEWENSEMBLE = "create new Ensemble xxx"; 	
	public CreateEnsembleDropTargetListener(EditPartViewer viewer) {
		super(viewer);

	}
	
	
	@Override
	protected void handleDrop() {
		super.handleDrop();
	}
	

	@Override
	protected void handleDragOver() {
		super.handleDragOver();
		//TODO: find another way to change the mouse icon. 
		//hack!
		if(getCurrentEvent() != null &&
				getCurrentEvent().detail == DND.DROP_COPY)
			getCurrentEvent().detail = DND.DROP_LINK;
	}


	@Override
	public boolean isEnabled(DropTargetEvent event) {
		return super.isEnabled(event);
	}

	
	@Override
	protected void updateTargetRequest() {
		((CreateViewRequest) getTargetRequest()).setLocation(getDropLocation());
		super.updateTargetEditPart();
	}
	
	/**
	 * creates a CreateViewRequest with the IType Ensemble_2001
	 * @see org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory#getCreateShapeRequest
	 */
	@Override
	protected Request createTargetRequest() {
		EPackage epackage = org.eclipse.emf.ecore.EPackage.Registry.INSTANCE
		.getEPackage("http://vespucci.editor");
		Vespucci_modelPackage vesPackage = (Vespucci_modelPackage) epackage;
		IElementType elementType = ElementTypeRegistry.getInstance().getElementType(vesPackage.getEnsemble());
		
		//TODO is there a way to get VespucciElementTypes.Ensemble_2001 over a method call?
		elementType = VespucciElementTypes.Ensemble_2001;
		// Get the selected editpart
		EditPart epart = getTargetEditPart();
		if(epart == null)
			return new CreateViewRequest(new CreateViewRequest.ViewDescriptor(null,null));
		DiagramEditPart p = (DiagramEditPart) epart;		
		CreateViewRequest request = CreateViewRequestFactory.getCreateShapeRequest(elementType, p.getDiagramPreferencesHint());
		request.setType(REQ_DROPNEWENSEMBLE);
		return request;
	}
	
}

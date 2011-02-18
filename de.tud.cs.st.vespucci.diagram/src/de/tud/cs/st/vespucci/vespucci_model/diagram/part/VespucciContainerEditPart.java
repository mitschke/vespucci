/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Artem Vovk
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.BasicCompartmentImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.vespucci_model.impl.EnsembleImpl;

/**
 * 
 * @author a_vovk
 *
 */
public class VespucciContainerEditPart extends TreeEditPart{

	public VespucciContainerEditPart(Object model) {
		super(model);
	}
	
	

	/**
	 * Returns the children of this from the model,
	 * as this is capable enough of holding EditParts.
	 *
	 * @return  List of children.
	 */
	protected List getModelChildren() {
		Object model = getModel();
		if(model instanceof EnsembleImpl) {
			EnsembleImpl shape = (EnsembleImpl) getModel();
			return shape.getShapes();
		}
		if (model instanceof ShapeImpl){
			ShapeImpl shape = (ShapeImpl) getModel();
			
			EList shapes = shape.getPersistedChildren();
			EList edges = shape.getSourceEdges();
			for (Object i : shapes) {
				if(i instanceof BasicCompartmentImpl) {
					BasicCompartmentImpl bci = (BasicCompartmentImpl)i;
					EList<View> out = new BasicEList<View>(); 
					 out.addAll(bci.getPersistedChildren());
					out.addAll(edges);
					return out;
				}
					
			}
//			
//			EList persChild = shape.getPersistedChildren();
//			EList persChild2 = shape.getTransientChildren();
//			EList persChild3 = shape.getVisibleChildren();
//			System.out.println(persChild.toString() +persChild2 +persChild3);
//			//shape.
//		
//			EnsembleImpl ensemble = (EnsembleImpl)shape.getElement();
//			
		//	EnsembleImpl object = (EnsembleImpl)shape.getElement();
//			return ensemble.getShapes();
		}
			
		return Collections.EMPTY_LIST;
	}

	private static final String ENSEMBLE_IMAGE = "icons/obj16/Ensemble.gif";
	
	@Override
	protected Image getImage() {
		ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin
		.getBundledImageDescriptor(ENSEMBLE_IMAGE);
 
		return imageDescriptor.createImage();
	}



	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart#handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
	 */
	protected void handleNotificationEvent(Notification event) {
		Object feature = event.getFeature();
		if (NotationPackage.eINSTANCE.getView_PersistedChildren()==feature||
			NotationPackage.eINSTANCE.getView_TransientChildren()==feature)
			refreshChildren();
		else
			super.handleNotificationEvent(event);
	}

}

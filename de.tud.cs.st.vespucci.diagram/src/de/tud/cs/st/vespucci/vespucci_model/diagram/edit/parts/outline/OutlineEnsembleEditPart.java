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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.BasicCompartmentImpl;
import org.eclipse.gmf.runtime.notation.impl.ConnectorImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * OutlineEditPart for Ensemble Object
 * 
 * @author a_vovk
 * 
 */
public class OutlineEnsembleEditPart extends TreeEditPart {

	private static final String IMAGE = "icons/outline/Ensemble.gif";

	private Set<EObject> objectListenningTo = new HashSet<EObject>();

	public OutlineEnsembleEditPart(Object model) {
		super(model);
	}

	@Override
	protected Image getImage() {
		ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin
				.getBundledImageDescriptor(IMAGE);
		return imageDescriptor.createImage();
	}

	@Override
	public void activate() {
		if (isActive())
			return;
		super.activate();
		View view = (View) getModel();
		// update Compartments
		for (Object i : view.getPersistedChildren()) {
			if (i instanceof BasicCompartmentImpl) {
				getDiagramEventBroker().addNotificationListener(
						(BasicCompartmentImpl) i, this);
				objectListenningTo.add((BasicCompartmentImpl) i);
			}
		}
	}

	@Override
	public void deactivate() {
		if (!isActive())
			return;

		Iterator<EObject> itr = objectListenningTo.iterator();
		while (itr.hasNext()) {
			EObject eObj = itr.next();
			getDiagramEventBroker().removeNotificationListener(eObj, this);
			// itr.remove();
		}
		objectListenningTo = null;

		super.deactivate();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelChildren() {
		Object model = getModel();

		if (model instanceof ShapeImpl) {
			ShapeImpl shape = (ShapeImpl) getModel();

			EList<?> shapes = shape.getPersistedChildren();
			EList<View> out = new BasicEList<View>();
			
			out.addAll(filterConnectionsFromConnectorImpl(shape.getSourceEdges()));
			out.addAll(filterConnectionsFromConnectorImpl(shape.getTargetEdges()));
						
			for (Object i : shapes) {
				if (i instanceof BasicCompartmentImpl) {
					BasicCompartmentImpl bci = (BasicCompartmentImpl) i;
					out.addAll(bci.getPersistedChildren());
					return out;
				}

			}

		}

		return Collections.EMPTY_LIST;
	}

	/**
	 * Filter connections for EdgeImpl: delete ConnectorImpl
	 * @param connections connections to filter
	 * @return filtered connections
	 */
	private EList<View> filterConnectionsFromConnectorImpl(EList<View> connections){
		EList<View> out = new BasicEList<View>();
		for(View i: connections) {
			if(!(i instanceof ConnectorImpl)){
				out.add(i);
			}
		}
		return out;
	}
	
	
	@Override
	protected void handleNotificationEvent(Notification event) {
		refresh();
	}

}

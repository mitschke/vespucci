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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;

import de.tud.cs.st.vespucci.vespucci_model.Shape;

/**
 * OutlineEditPart for TargetConnections(incoming connections)
 * 
 * @author a_vovk
 * 
 */
public abstract class OutlineTargetConnectionEditPart extends
		OutlineConnectionEditPart {

	
	private Set<EObject> objectListenningTo = new HashSet<EObject>();

	@Override
	public void activate() {
		if (isActive())
			return;
		super.activate();
		EdgeImpl edge = (EdgeImpl) getModel();
		// update Compartments
		org.eclipse.gmf.runtime.notation.Shape sourceShape = (org.eclipse.gmf.runtime.notation.Shape) edge.getSource();

		getDiagramEventBroker().addNotificationListener(
				ViewUtil.resolveSemanticElement(sourceShape), this);

		getDiagramEventBroker().addNotificationListener(sourceShape, this);
		objectListenningTo.add(sourceShape);

//		org.eclipse.gmf.runtime.notation.Shape targetShape = (org.eclipse.gmf.runtime.notation.Shape) edge.getTarget();
//		getDiagramEventBroker().addNotificationListener(
//				ViewUtil.resolveSemanticElement(targetShape), this);
//		getDiagramEventBroker().addNotificationListener(targetShape, this);
//		objectListenningTo.add(targetShape);
	}

	@Override
	public void deactivate() {
		if (!isActive())
			return;
		Iterator<EObject> itr = objectListenningTo.iterator();
		while (itr.hasNext()) {
			EObject eObj = itr.next();
			getDiagramEventBroker().removeNotificationListener(eObj, this);
			itr.remove();
		}
		super.deactivate();
	}
	
	public OutlineTargetConnectionEditPart(Object model) {
		super(model);
	}

	@Override
	protected String getText() {
		NodeImpl sourceNode = (NodeImpl) ((EdgeImpl) this.getModel())
				.getSource();
		if (sourceNode != null)
			return ": " + ((Shape) sourceNode.getElement()).getName();
		return super.getText();
	}
}

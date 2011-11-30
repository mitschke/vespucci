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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ConnectorImpl;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * OutlineEditPart for empty object
 * 
 * @author Artem Vovk
 * 
 */
public class OutlineEmptyEditPart extends TreeEditPart {

	private static final String ENSEMBLE_IMAGE = "icons/outline/Empty.gif";

	public OutlineEmptyEditPart(final Object model) {
		super(model);
	}

	@Override
	protected Image getImage() {
		final ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin.getBundledImageDescriptor(ENSEMBLE_IMAGE);

		return imageDescriptor.createImage();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelChildren() {
		final Object model = getModel();

		if (model instanceof NodeImpl) {
			final NodeImpl node = (NodeImpl) getModel();
			final EList<View> out = excludeConnectorImpl(node.getSourceEdges());
			out.addAll(excludeConnectorImpl(node.getTargetEdges()));
			return out;
		}

		return Collections.EMPTY_LIST;
	}

	@Override
	protected void handleNotificationEvent(final Notification event) {
		final Object notifier = event.getNotifier();
		if (NotationPackage.Literals.VIEW__ELEMENT == event.getFeature()
				|| ((NotationPackage.Literals.VIEW__TARGET_EDGES == event.getFeature() || NotationPackage.Literals.VIEW__SOURCE_EDGES == event
						.getFeature()) && event.getEventType() != Notification.REMOVE)) {

			reactivateSemanticElement();
		} else if (event.getNotifier() == getSemanticElement() || notifier instanceof Style) {
			refresh();
		}
	}

	private static EList<View> excludeConnectorImpl(EList<View> connections) {
		EList<View> result = new BasicEList<View>();
		for (View conn : connections) {
			if (!(conn instanceof ConnectorImpl)) {
				result.add(conn);
			}
		}
		return result;
	}

}

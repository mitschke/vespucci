/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
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
 *   - Neither the name of the Software Technology Group Group or Technische 
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
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
 * @author Artem Vovk
 * 
 */
public class OutlineEnsembleEditPart extends TreeEditPart {

	private static final String IMAGE = "icons/outline/Ensemble.gif";

	private final Set<EObject> objectListenningTo = new HashSet<EObject>();

	public OutlineEnsembleEditPart(final Object model) {
		super(model);
	}

	@Override
	protected Image getImage() {
		final ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin.getBundledImageDescriptor(IMAGE);
		return imageDescriptor.createImage();
	}

	@Override
	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		final View view = (View) getModel();
		// update Compartments
		for (final Object i : view.getPersistedChildren()) {
			if (i instanceof BasicCompartmentImpl) {
				getDiagramEventBroker().addNotificationListener((BasicCompartmentImpl) i, this);
				objectListenningTo.add((BasicCompartmentImpl) i);
			}
		}
	}

	@Override
	public void deactivate() {
		if (!isActive()) {
			return;
		}
		final Iterator<EObject> itr = objectListenningTo.iterator();
		while (itr.hasNext()) {
			final EObject eObj = itr.next();
			getDiagramEventBroker().removeNotificationListener(eObj, this);
			itr.remove();
		}
		super.deactivate();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<?> getModelChildren() {
		final Object model = getModel();

		if (model instanceof ShapeImpl) {
			final ShapeImpl shape = (ShapeImpl) getModel();

			final EList<?> shapes = shape.getPersistedChildren();
			final EList<View> out = new BasicEList<View>();

			out.addAll(excludeConnectorImpl(shape.getSourceEdges()));
			out.addAll(excludeConnectorImpl(shape.getTargetEdges()));

			for (final Object i : shapes) {
				if (i instanceof BasicCompartmentImpl) {
					final BasicCompartmentImpl bci = (BasicCompartmentImpl) i;
					out.addAll(bci.getPersistedChildren());
					return out;
				}

			}

		}

		return Collections.EMPTY_LIST;
	}

	@Override
	protected void handleNotificationEvent(final Notification event) {
		final Object notifier = event.getNotifier();
		if (NotationPackage.Literals.VIEW__ELEMENT == event.getFeature()) {
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

/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.edit.policies;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated NOT
 */
public class ShapesDiagramCanonicalEditPolicy extends
		CanonicalConnectionEditPolicy {

	/**
	 * @generated
	 */
	Set myFeaturesToSynchronize;

	/**
	 * @generated
	 */
	@SuppressWarnings("rawtypes")
	protected List getSemanticChildrenList() {
		View viewObject = (View) getHost().getModel();
		List result = new LinkedList();
		for (Iterator it = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
				.getShapesDiagram_1000SemanticChildren(viewObject).iterator(); it
				.hasNext();) {
			result.add(((de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor) it
					.next()).getModelElement());
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected boolean shouldDeleteView(View view) {
		return true;
	}

	/**
	 * @generated
	 */
	protected boolean isOrphaned(Collection semanticChildren, final View view) {
		int visualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view);
		switch (visualID) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
			if (!semanticChildren.contains(view.getElement())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected String getDefaultFactoryHint() {
		return null;
	}

	/**
	 * @generated
	 */
	protected Set getFeaturesToSynchronize() {
		if (myFeaturesToSynchronize == null) {
			myFeaturesToSynchronize = new HashSet();
			myFeaturesToSynchronize
					.add(de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getShapesDiagram_Shapes());
		}
		return myFeaturesToSynchronize;
	}

	/**
	 * @generated
	 */
	protected List getSemanticConnectionsList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	protected EObject getSourceElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected EObject getTargetElement(EObject relationship) {
		return null;
	}

	/**
	 * @generated
	 */
	protected boolean shouldIncludeConnection(Edge connector,
			Collection children) {
		return false;
	}

	/**
	 * @generated
	 */
	protected void refreshSemantic() {
		List createdViews = new LinkedList();
		createdViews.addAll(refreshSemanticChildren());
		List createdConnectionViews = new LinkedList();
		createdConnectionViews.addAll(refreshSemanticConnections());
		createdConnectionViews.addAll(refreshConnections());

		if (createdViews.size() > 1) {
			// perform a layout of the container
			DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host()
					.getEditingDomain(), createdViews, host());
			executeCommand(new ICommandProxy(layoutCmd));
		}

		createdViews.addAll(createdConnectionViews);
		makeViewsImmutable(createdViews);
	}

	/**
	 * @generated
	 */
	private Diagram getDiagram() {
		return ((View) getHost().getModel()).getDiagram();
	}

	/**
	 * @generated
	 */
	private Collection refreshConnections() {
		Map domain2NotationMap = new HashMap();
		Collection linkDescriptors = collectAllLinks(getDiagram(),
				domain2NotationMap);
		Collection existingLinks = new LinkedList(getDiagram().getEdges());
		for (Iterator linksIterator = existingLinks.iterator(); linksIterator
				.hasNext();) {
			Edge nextDiagramLink = (Edge) linksIterator.next();
			int diagramLinkVisualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getVisualID(nextDiagramLink);
			if (diagramLinkVisualID == -1) {
				if (nextDiagramLink.getSource() != null
						&& nextDiagramLink.getTarget() != null) {
					linksIterator.remove();
				}
				continue;
			}
			EObject diagramLinkObject = nextDiagramLink.getElement();
			EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
			EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
			for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
					.hasNext();) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor nextLinkDescriptor = (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor) linkDescriptorsIterator
						.next();
				if (diagramLinkObject == nextLinkDescriptor.getModelElement()
						&& diagramLinkSrc == nextLinkDescriptor.getSource()
						&& diagramLinkDst == nextLinkDescriptor
								.getDestination()
						&& diagramLinkVisualID == nextLinkDescriptor
								.getVisualID()) {
					linksIterator.remove();
					linkDescriptorsIterator.remove();
					break;
				}
			}
		}
		deleteViews(existingLinks.iterator());
		return createConnections(linkDescriptors, domain2NotationMap);
	}

	/**
	 * @generated
	 */
	private Collection collectAllLinks(View view, Map domain2NotationMap) {
		if (!de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID
				.equals(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getModelID(view))) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getShapesDiagram_1000ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEnsemble_2001ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getDummy_2002ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEnsemble_3001ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getDummy_3003ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getIncoming_4005ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getOutgoing_4003ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getInAndOut_4001ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getNotAllowed_4004ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				result.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getExpected_4002ContainedLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement())
					|| view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		}
		for (Iterator children = view.getChildren().iterator(); children
				.hasNext();) {
			result.addAll(collectAllLinks((View) children.next(),
					domain2NotationMap));
		}
		for (Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
			result.addAll(collectAllLinks((View) edges.next(),
					domain2NotationMap));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection createConnections(Collection linkDescriptors,
			Map domain2NotationMap) {
		List adapters = new LinkedList();
		for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
				.hasNext();) {
			final de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor nextLinkDescriptor = (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor) linkDescriptorsIterator
					.next();
			EditPart sourceEditPart = getEditPart(
					nextLinkDescriptor.getSource(), domain2NotationMap);
			EditPart targetEditPart = getEditPart(
					nextLinkDescriptor.getDestination(), domain2NotationMap);
			if (sourceEditPart == null || targetEditPart == null) {
				continue;
			}
			CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
					nextLinkDescriptor.getSemanticAdapter(),
					String.valueOf(nextLinkDescriptor.getVisualID()),
					ViewUtil.APPEND, false,
					((IGraphicalEditPart) getHost())
							.getDiagramPreferencesHint());
			CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(
					descriptor);
			ccr.setType(RequestConstants.REQ_CONNECTION_START);
			ccr.setSourceEditPart(sourceEditPart);
			sourceEditPart.getCommand(ccr);
			ccr.setTargetEditPart(targetEditPart);
			ccr.setType(RequestConstants.REQ_CONNECTION_END);
			Command cmd = targetEditPart.getCommand(ccr);
			if (cmd != null && cmd.canExecute()) {
				executeCommand(cmd);
				IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
				if (viewAdapter != null) {
					adapters.add(viewAdapter);
				}
			}
		}
		return adapters;
	}

	/**
	 * @generated
	 */
	private EditPart getEditPart(EObject domainModelElement,
			Map domain2NotationMap) {
		View view = (View) domain2NotationMap.get(domainModelElement);
		if (view != null) {
			return (EditPart) getHost().getViewer().getEditPartRegistry()
					.get(view);
		}
		return null;
	}
}

/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class VespucciDiagramContentInitializer {

	/**
	 * @generated
	 */
	private Map myDomain2NotationMap = new HashMap();

	/**
	 * @generated
	 */
	private Collection myLinkDescriptors = new LinkedList();

	/**
	 * @generated
	 */
	public void initDiagramContent(Diagram diagram) {
		if (!de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart.MODEL_ID
				.equals(diagram.getType())) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().logError(
							"Incorrect diagram passed as a parameter: "
									+ diagram.getType());
			return;
		}
		if (false == diagram.getElement() instanceof de.tud.cs.st.vespucci.vespucci_model.ArchitectureModel) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().logError(
							"Incorrect diagram element specified: "
									+ diagram.getElement()
									+ " instead of ArchitectureModel");
			return;
		}
		createArchitectureModel_1000Children(diagram);
		createLinks(diagram);
	}

	/**
	 * @generated
	 */
	private void createArchitectureModel_1000Children(View view) {
		Collection childNodeDescriptors = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
				.getArchitectureModel_1000SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(
					view,
					(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor) it
							.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnsemble_2005Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors
				.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEnsemble_2005OutgoingLinks(view));
		createEnsembleEnsembleCompartment_7007Children(getCompartment(
				view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID));
		createEnsembleEnsembleDescriptionCompartment_7008Children(getCompartment(
				view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartmentEditPart.VISUAL_ID));

	}

	/**
	 * @generated
	 */
	private void createEmpty_2006Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors
				.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEmpty_2006OutgoingLinks(view));

	}

	/**
	 * @generated
	 */
	private void createEnsemble_3004Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors
				.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEnsemble_3004OutgoingLinks(view));
		createEnsembleEnsembleCompartment_7009Children(getCompartment(
				view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID));
		createEnsembleEnsembleDescriptionCompartment_7010Children(getCompartment(
				view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart.VISUAL_ID));

	}

	/**
	 * @generated
	 */
	private void createEmpty_3005Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors
				.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
						.getEmpty_3005OutgoingLinks(view));

	}

	/**
	 * @generated
	 */
	private void createEnsembleEnsembleCompartment_7007Children(View view) {
		Collection childNodeDescriptors = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
				.getEnsembleEnsembleCompartment_7007SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(
					view,
					(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor) it
							.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnsembleEnsembleDescriptionCompartment_7008Children(
			View view) {
	}

	/**
	 * @generated
	 */
	private void createEnsembleEnsembleCompartment_7009Children(View view) {
		Collection childNodeDescriptors = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
				.getEnsembleEnsembleCompartment_7009SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(
					view,
					(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor) it
							.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnsembleEnsembleDescriptionCompartment_7010Children(
			View view) {
	}

	/**
	 * @generated
	 */
	private void createNode(
			View parentView,
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor nodeDescriptor) {
		final String nodeType = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getType(nodeDescriptor.getVisualID());
		Node node = ViewService
				.createNode(
						parentView,
						nodeDescriptor.getModelElement(),
						nodeType,
						de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		switch (nodeDescriptor.getVisualID()) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
			createEnsemble_2005Children(node);
			return;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart.VISUAL_ID:
			createEmpty_2006Children(node);
			return;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
			createEnsemble_3004Children(node);
			return;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart.VISUAL_ID:
			createEmpty_3005Children(node);
			return;
		}
	}

	/**
	 * @generated
	 */
	private void createLinks(Diagram diagram) {
		for (boolean continueLinkCreation = true; continueLinkCreation;) {
			continueLinkCreation = false;
			Collection additionalDescriptors = new LinkedList();
			for (Iterator it = myLinkDescriptors.iterator(); it.hasNext();) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor nextLinkDescriptor = (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor) it
						.next();
				if (!myDomain2NotationMap.containsKey(nextLinkDescriptor
						.getSource())
						|| !myDomain2NotationMap.containsKey(nextLinkDescriptor
								.getDestination())) {
					continue;
				}
				final String linkType = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(nextLinkDescriptor.getVisualID());
				Edge edge = ViewService
						.getInstance()
						.createEdge(
								nextLinkDescriptor.getSemanticAdapter(),
								diagram,
								linkType,
								ViewUtil.APPEND,
								true,
								de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (edge != null) {
					edge.setSource((View) myDomain2NotationMap
							.get(nextLinkDescriptor.getSource()));
					edge.setTarget((View) myDomain2NotationMap
							.get(nextLinkDescriptor.getDestination()));
					it.remove();
					if (nextLinkDescriptor.getModelElement() != null) {
						myDomain2NotationMap.put(
								nextLinkDescriptor.getModelElement(), edge);
					}
					continueLinkCreation = true;
					switch (nextLinkDescriptor.getVisualID()) {
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getIncoming_4005OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getOutgoing_4003OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getInAndOut_4001OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getNotAllowed_4004OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getExpected_4002OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getGlobalIncoming_4006OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getGlobalOutgoing_4007OutgoingLinks(edge));
						break;
					case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationEditPart.VISUAL_ID:
						additionalDescriptors
								.addAll(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramUpdater
										.getViolation_4009OutgoingLinks(edge));
						break;
					}
				}
			}
			myLinkDescriptors.addAll(additionalDescriptors);
		}
	}

	/**
	 * @generated
	 */
	private Node getCompartment(View node, int visualID) {
		String type = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getType(visualID);
		for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (nextView instanceof Node && type.equals(nextView.getType())) {
				return (Node) nextView;
			}
		}
		return null;
	}

}

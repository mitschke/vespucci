/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitï¿½t Darmstadt
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
 *     Universitï¿½t Darmstadt nor the names of its contributors may be used to 
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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class VespucciDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> getSemanticChildren(
			View view) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
			return getShapesDiagram_1000SemanticChildren(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID:
			return getEnsembleEnsembleCompartment_7001SemanticChildren(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID:
			return getEnsembleEnsembleCompartment_7002SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> getShapesDiagram_1000SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram modelElement = (de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor>();
		for (Iterator<?> it = modelElement.getShapes().iterator(); it.hasNext();) {
			de.tud.cs.st.vespucci.vespucci_model.Shape childElement = (de.tud.cs.st.vespucci.vespucci_model.Shape) it
					.next();
			int visualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> getEnsembleEnsembleCompartment_7001SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) containerView
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor>();
		for (Iterator<?> it = modelElement.getShapes().iterator(); it.hasNext();) {
			de.tud.cs.st.vespucci.vespucci_model.Shape childElement = (de.tud.cs.st.vespucci.vespucci_model.Shape) it
					.next();
			int visualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> getEnsembleEnsembleCompartment_7002SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) containerView
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor>();
		for (Iterator<?> it = modelElement.getShapes().iterator(); it.hasNext();) {
			de.tud.cs.st.vespucci.vespucci_model.Shape childElement = (de.tud.cs.st.vespucci.vespucci_model.Shape) it
					.next();
			int visualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
			if (visualID == de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID) {
				result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedLinks(
			View view) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
			return getShapesDiagram_1000ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
			return getEnsemble_2001ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
			return getDummy_2002ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
			return getEnsemble_3001ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
			return getDummy_3003ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
			return getIncoming_4005ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
			return getOutgoing_4003ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
			return getInAndOut_4001ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
			return getNotAllowed_4004ContainedLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
			return getExpected_4002ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingLinks(
			View view) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
			return getEnsemble_2001IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
			return getDummy_2002IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
			return getEnsemble_3001IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
			return getDummy_3003IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
			return getIncoming_4005IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
			return getOutgoing_4003IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
			return getInAndOut_4001IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
			return getNotAllowed_4004IncomingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
			return getExpected_4002IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingLinks(
			View view) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
			return getEnsemble_2001OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
			return getDummy_2002OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
			return getEnsemble_3001OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
			return getDummy_3003OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
			return getIncoming_4005OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
			return getOutgoing_4003OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
			return getInAndOut_4001OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
			return getNotAllowed_4004OutgoingLinks(view);
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
			return getExpected_4002OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getShapesDiagram_1000ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_2001ContainedLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_2002ContainedLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_3001ContainedLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_3003ContainedLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncoming_4005ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoing_4003ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getInAndOut_4001ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getNotAllowed_4004ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getExpected_4002ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_2001IncomingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Incoming_4005(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Outgoing_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_InAndOut_4001(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NotAllowed_4004(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Expected_4002(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_2002IncomingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Incoming_4005(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Outgoing_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_InAndOut_4001(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NotAllowed_4004(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Expected_4002(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_3001IncomingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Incoming_4005(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Outgoing_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_InAndOut_4001(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NotAllowed_4004(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Expected_4002(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_3003IncomingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Incoming_4005(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Outgoing_4003(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_InAndOut_4001(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_NotAllowed_4004(
				modelElement, crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Expected_4002(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncoming_4005IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoing_4003IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getInAndOut_4001IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getNotAllowed_4004IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getExpected_4002IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_2001OutgoingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_2002OutgoingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getEnsemble_3001OutgoingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Ensemble modelElement = (de.tud.cs.st.vespucci.vespucci_model.Ensemble) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getDummy_3003OutgoingLinks(
			View view) {
		de.tud.cs.st.vespucci.vespucci_model.Dummy modelElement = (de.tud.cs.st.vespucci.vespucci_model.Dummy) view
				.getElement();
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Incoming_4005(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Outgoing_4003(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InAndOut_4001(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_NotAllowed_4004(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Expected_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncoming_4005OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoing_4003OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getInAndOut_4001OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getNotAllowed_4004OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getExpected_4002OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedTypeModelFacetLinks_Incoming_4005(
			de.tud.cs.st.vespucci.vespucci_model.Shape container) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Incoming link = (de.tud.cs.st.vespucci.vespucci_model.Incoming) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedTypeModelFacetLinks_Outgoing_4003(
			de.tud.cs.st.vespucci.vespucci_model.Shape container) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Outgoing link = (de.tud.cs.st.vespucci.vespucci_model.Outgoing) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedTypeModelFacetLinks_InAndOut_4001(
			de.tud.cs.st.vespucci.vespucci_model.Shape container) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.InAndOut link = (de.tud.cs.st.vespucci.vespucci_model.InAndOut) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedTypeModelFacetLinks_NotAllowed_4004(
			de.tud.cs.st.vespucci.vespucci_model.Shape container) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.NotAllowed link = (de.tud.cs.st.vespucci.vespucci_model.NotAllowed) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getContainedTypeModelFacetLinks_Expected_4002(
			de.tud.cs.st.vespucci.vespucci_model.Shape container) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Expected) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Expected link = (de.tud.cs.st.vespucci.vespucci_model.Expected) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingTypeModelFacetLinks_Incoming_4005(
			de.tud.cs.st.vespucci.vespucci_model.Shape target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Incoming link = (de.tud.cs.st.vespucci.vespucci_model.Incoming) setting
					.getEObject();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					target,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingTypeModelFacetLinks_Outgoing_4003(
			de.tud.cs.st.vespucci.vespucci_model.Shape target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Outgoing link = (de.tud.cs.st.vespucci.vespucci_model.Outgoing) setting
					.getEObject();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					target,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingTypeModelFacetLinks_InAndOut_4001(
			de.tud.cs.st.vespucci.vespucci_model.Shape target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.InAndOut link = (de.tud.cs.st.vespucci.vespucci_model.InAndOut) setting
					.getEObject();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					target,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingTypeModelFacetLinks_NotAllowed_4004(
			de.tud.cs.st.vespucci.vespucci_model.Shape target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.NotAllowed link = (de.tud.cs.st.vespucci.vespucci_model.NotAllowed) setting
					.getEObject();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					target,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getIncomingTypeModelFacetLinks_Expected_4002(
			de.tud.cs.st.vespucci.vespucci_model.Shape target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof de.tud.cs.st.vespucci.vespucci_model.Expected) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Expected link = (de.tud.cs.st.vespucci.vespucci_model.Expected) setting
					.getEObject();
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					target,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingTypeModelFacetLinks_Incoming_4005(
			de.tud.cs.st.vespucci.vespucci_model.Shape source) {
		de.tud.cs.st.vespucci.vespucci_model.Shape container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof de.tud.cs.st.vespucci.vespucci_model.Shape) {
				container = (de.tud.cs.st.vespucci.vespucci_model.Shape) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Incoming) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Incoming link = (de.tud.cs.st.vespucci.vespucci_model.Incoming) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingTypeModelFacetLinks_Outgoing_4003(
			de.tud.cs.st.vespucci.vespucci_model.Shape source) {
		de.tud.cs.st.vespucci.vespucci_model.Shape container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof de.tud.cs.st.vespucci.vespucci_model.Shape) {
				container = (de.tud.cs.st.vespucci.vespucci_model.Shape) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Outgoing) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Outgoing link = (de.tud.cs.st.vespucci.vespucci_model.Outgoing) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingTypeModelFacetLinks_InAndOut_4001(
			de.tud.cs.st.vespucci.vespucci_model.Shape source) {
		de.tud.cs.st.vespucci.vespucci_model.Shape container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof de.tud.cs.st.vespucci.vespucci_model.Shape) {
				container = (de.tud.cs.st.vespucci.vespucci_model.Shape) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.InAndOut) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.InAndOut link = (de.tud.cs.st.vespucci.vespucci_model.InAndOut) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingTypeModelFacetLinks_NotAllowed_4004(
			de.tud.cs.st.vespucci.vespucci_model.Shape source) {
		de.tud.cs.st.vespucci.vespucci_model.Shape container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof de.tud.cs.st.vespucci.vespucci_model.Shape) {
				container = (de.tud.cs.st.vespucci.vespucci_model.Shape) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.NotAllowed) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.NotAllowed link = (de.tud.cs.st.vespucci.vespucci_model.NotAllowed) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> getOutgoingTypeModelFacetLinks_Expected_4002(
			de.tud.cs.st.vespucci.vespucci_model.Shape source) {
		de.tud.cs.st.vespucci.vespucci_model.Shape container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof de.tud.cs.st.vespucci.vespucci_model.Shape) {
				container = (de.tud.cs.st.vespucci.vespucci_model.Shape) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor> result = new LinkedList<de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor>();
		for (Iterator<?> links = container.getTargetConnections().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof de.tud.cs.st.vespucci.vespucci_model.Expected) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Expected link = (de.tud.cs.st.vespucci.vespucci_model.Expected) linkObject;
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID != de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			de.tud.cs.st.vespucci.vespucci_model.Shape dst = link.getTarget();
			de.tud.cs.st.vespucci.vespucci_model.Shape src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciLinkDescriptor(
					src,
					dst,
					link,
					de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002,
					de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
		}
		return result;
	}

}

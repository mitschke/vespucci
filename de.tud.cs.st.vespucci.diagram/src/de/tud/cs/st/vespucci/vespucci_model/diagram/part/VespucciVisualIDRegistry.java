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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class VespucciVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "de.tud.cs.st.vespucci.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID.equals(view.getType())) {
				return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
						"Unable to parse view type as a visualID number: " + type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getShapesDiagram().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram) domainElement)) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getModelID(containerView);
		if (!de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getEnsemble().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getDummy().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getEnsemble().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getDummy().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getEnsemble().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getDummy().isSuperTypeOf(
						domainElement.eClass())) {
					return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID;
				}
				break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getModelID(containerView);
		if (!de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescriptionEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartmentEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleDescription2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleDescriptionCompartment2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyName2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
				if (de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedNameEditPart.VISUAL_ID == nodeVisualID) {
					return true;
				}
				break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getIncoming().isSuperTypeOf(
				domainElement.eClass())) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getOutgoing().isSuperTypeOf(
				domainElement.eClass())) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getInAndOut().isSuperTypeOf(
				domainElement.eClass())) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getNotAllowed().isSuperTypeOf(
				domainElement.eClass())) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID;
		}
		if (de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE.getExpected().isSuperTypeOf(
				domainElement.eClass())) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram element) {
		return true;
	}

}

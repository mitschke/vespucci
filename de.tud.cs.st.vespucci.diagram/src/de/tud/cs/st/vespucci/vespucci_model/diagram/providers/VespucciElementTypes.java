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
package de.tud.cs.st.vespucci.vespucci_model.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class VespucciElementTypes {

	/**
	 * @generated
	 */
	private VespucciElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static ImageRegistry imageRegistry;

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType ArchitectureModel_1000 = getElementType("de.tud.cs.st.vespucci.diagram.ArchitectureModel_1000"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Ensemble_2003 = getElementType("de.tud.cs.st.vespucci.diagram.Ensemble_2003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Empty_2004 = getElementType("de.tud.cs.st.vespucci.diagram.Empty_2004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Ensemble_3001 = getElementType("de.tud.cs.st.vespucci.diagram.Ensemble_3001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Empty_3003 = getElementType("de.tud.cs.st.vespucci.diagram.Empty_3003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Incoming_4005 = getElementType("de.tud.cs.st.vespucci.diagram.Incoming_4005"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Outgoing_4003 = getElementType("de.tud.cs.st.vespucci.diagram.Outgoing_4003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType InAndOut_4001 = getElementType("de.tud.cs.st.vespucci.diagram.InAndOut_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType NotAllowed_4004 = getElementType("de.tud.cs.st.vespucci.diagram.NotAllowed_4004"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Expected_4002 = getElementType("de.tud.cs.st.vespucci.diagram.Expected_4002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType GlobalIncoming_4006 = getElementType("de.tud.cs.st.vespucci.diagram.GlobalIncoming_4006"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType GlobalOutgoing_4007 = getElementType("de.tud.cs.st.vespucci.diagram.GlobalOutgoing_4007"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Violation_4009 = getElementType("de.tud.cs.st.vespucci.diagram.Violation_4009"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	private static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
		}
		return imageRegistry;
	}

	/**
	 * @generated
	 */
	private static String getImageRegistryKey(ENamedElement element) {
		return element.getName();
	}

	/**
	 * @generated
	 */
	private static ImageDescriptor getProvidedImageDescriptor(
			ENamedElement element) {
		if (element instanceof EStructuralFeature) {
			EStructuralFeature feature = ((EStructuralFeature) element);
			EClass eContainingClass = feature.getEContainingClass();
			EClassifier eType = feature.getEType();
			if (eContainingClass != null && !eContainingClass.isAbstract()) {
				element = eContainingClass;
			} else if (eType instanceof EClass
					&& !((EClass) eType).isAbstract()) {
				element = eType;
			}
		}
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			if (!eClass.isAbstract()) {
				return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().getItemImageDescriptor(
								eClass.getEPackage().getEFactoryInstance()
										.create(eClass));
			}
		}
		// TODO : support structural features
		return null;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		String key = getImageRegistryKey(element);
		ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
		if (imageDescriptor == null) {
			imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
		}
		return imageDescriptor;
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		String key = getImageRegistryKey(element);
		Image image = getImageRegistry().get(key);
		if (image == null) {
			ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
			if (imageDescriptor == null) {
				imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, imageDescriptor);
			image = getImageRegistry().get(key);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		ENamedElement element = getElement(hint);
		if (element == null) {
			return null;
		}
		return getImage(element);
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(
					ArchitectureModel_1000,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getArchitectureModel());

			elements.put(
					Ensemble_2003,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getEnsemble());

			elements.put(
					Empty_2004,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getEmpty());

			elements.put(
					Ensemble_3001,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getEnsemble());

			elements.put(
					Empty_3003,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getEmpty());

			elements.put(
					Incoming_4005,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getIncoming());

			elements.put(
					Outgoing_4003,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getOutgoing());

			elements.put(
					InAndOut_4001,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getInAndOut());

			elements.put(
					NotAllowed_4004,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getNotAllowed());

			elements.put(
					Expected_4002,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getExpected());

			elements.put(
					GlobalIncoming_4006,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getGlobalIncoming());

			elements.put(
					GlobalOutgoing_4007,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getGlobalOutgoing());

			elements.put(
					Violation_4009,
					de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage.eINSTANCE
							.getViolation());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(ArchitectureModel_1000);
			KNOWN_ELEMENT_TYPES.add(Ensemble_2003);
			KNOWN_ELEMENT_TYPES.add(Empty_2004);
			KNOWN_ELEMENT_TYPES.add(Ensemble_3001);
			KNOWN_ELEMENT_TYPES.add(Empty_3003);
			KNOWN_ELEMENT_TYPES.add(Incoming_4005);
			KNOWN_ELEMENT_TYPES.add(Outgoing_4003);
			KNOWN_ELEMENT_TYPES.add(InAndOut_4001);
			KNOWN_ELEMENT_TYPES.add(NotAllowed_4004);
			KNOWN_ELEMENT_TYPES.add(Expected_4002);
			KNOWN_ELEMENT_TYPES.add(GlobalIncoming_4006);
			KNOWN_ELEMENT_TYPES.add(GlobalOutgoing_4007);
			KNOWN_ELEMENT_TYPES.add(Violation_4009);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ArchitectureModelEditPart.VISUAL_ID:
			return ArchitectureModel_1000;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
			return Ensemble_2003;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EmptyEditPart.VISUAL_ID:
			return Empty_2004;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
			return Ensemble_3001;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Empty2EditPart.VISUAL_ID:
			return Empty_3003;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
			return Incoming_4005;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
			return Outgoing_4003;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
			return InAndOut_4001;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
			return NotAllowed_4004;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
			return Expected_4002;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID:
			return GlobalIncoming_4006;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID:
			return GlobalOutgoing_4007;
		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ViolationEditPart.VISUAL_ID:
			return Violation_4009;
		}
		return null;
	}

}

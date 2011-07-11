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
package de.tud.cs.st.vespucci.vespucci_model.diagram.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * @generated
 */
public class VespucciModelingAssistantProvider extends
		ModelingAssistantProvider {

	/**
	 * @generated
	 */
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(2);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_2002);
			return types;
		}
		if (editPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(2);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003);
			return types;
		}
		if (editPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart) {
			ArrayList<IElementType> types = new ArrayList<IElementType>(2);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001);
			types.add(de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) sourceEditPart)
					.getMARelTypesOnSource();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) targetEditPart)
					.getMARelTypesOnTarget();
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSourceAndTarget(IAdaptable source,
			IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) sourceEditPart)
					.getMARelTypesOnSourceAndTarget(targetEditPart);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForSource(IAdaptable target,
			IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
				.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		if (targetEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) targetEditPart)
					.getMATypesForSource(relationshipType);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForTarget(IAdaptable source,
			IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
				.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		if (sourceEditPart instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) {
			return ((de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart) sourceEditPart)
					.getMATypesForTarget(relationshipType);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForSource(IAdaptable target,
			IElementType relationshipType) {
		return selectExistingElement(target,
				getTypesForSource(target, relationshipType));
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForTarget(IAdaptable source,
			IElementType relationshipType) {
		return selectExistingElement(source,
				getTypesForTarget(source, relationshipType));
	}

	/**
	 * @generated
	 */
	protected EObject selectExistingElement(IAdaptable host, Collection types) {
		if (types.isEmpty()) {
			return null;
		}
		IGraphicalEditPart editPart = (IGraphicalEditPart) host
				.getAdapter(IGraphicalEditPart.class);
		if (editPart == null) {
			return null;
		}
		Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
		HashSet<EObject> elements = new HashSet<EObject>();
		for (Iterator<EObject> it = diagram.getElement().eAllContents(); it
				.hasNext();) {
			EObject element = it.next();
			if (isApplicableElement(element, types)) {
				elements.add(element);
			}
		}
		if (elements.isEmpty()) {
			return null;
		}
		return selectElement((EObject[]) elements.toArray(new EObject[elements
				.size()]));
	}

	/**
	 * @generated
	 */
	protected boolean isApplicableElement(EObject element, Collection types) {
		IElementType type = ElementTypeRegistry.getInstance().getElementType(
				element);
		return types.contains(type);
	}

	/**
	 * @generated
	 */
	protected EObject selectElement(EObject[] elements) {
		Shell shell = Display.getCurrent().getActiveShell();
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
						.getInstance().getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				shell, labelProvider);
		dialog.setMessage(de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciModelingAssistantProviderMessage);
		dialog.setTitle(de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}
}

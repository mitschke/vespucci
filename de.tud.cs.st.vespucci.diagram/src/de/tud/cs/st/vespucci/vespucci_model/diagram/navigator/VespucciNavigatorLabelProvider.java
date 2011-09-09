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
package de.tud.cs.st.vespucci.vespucci_model.diagram.navigator;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * @generated
 */
public class VespucciNavigatorLabelProvider extends LabelProvider implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().getImageRegistry()
				.put("Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().getImageRegistry()
				.put("Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem
				&& !isOwnView(((de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup group = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) element;
			return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().getBundledImage(
					group.getIcon());
		}

		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem navigatorItem = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(view)) {
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
				return getImage(
						"Navigator?Node?http://vespucci.editor/2011-06-01?Ensemble", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?Incoming", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Diagram?http://vespucci.editor/2011-06-01?ShapesDiagram", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.ShapesDiagram_1000); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
				return getImage(
						"Navigator?TopLevelNode?http://vespucci.editor/2011-06-01?Ensemble", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?GlobalIncoming", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?NotAllowed", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
				return getImage(
						"Navigator?Node?http://vespucci.editor/2011-06-01?Dummy", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?GlobalOutgoing", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
				return getImage(
						"Navigator?TopLevelNode?http://vespucci.editor/2011-06-01?Dummy", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_2002); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?InAndOut", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.WarningEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?Warning", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Warning_4008); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?Expected", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002); //$NON-NLS-1$
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
				return getImage(
						"Navigator?Link?http://vespucci.editor/2011-06-01?Outgoing", de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance()
				.getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null
				&& de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.isKnownElementType(elementType)) {
			image = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup group = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem navigatorItem = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getVisualID(view)) {
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID:
				return getEnsemble_3001Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID:
				return getIncoming_4005Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID:
				return getShapesDiagram_1000Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID:
				return getEnsemble_2001Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingEditPart.VISUAL_ID:
				return getGlobalIncoming_4006Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID:
				return getNotAllowed_4004Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID:
				return getDummy_3003Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingEditPart.VISUAL_ID:
				return getGlobalOutgoing_4007Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID:
				return getDummy_2002Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID:
				return getInAndOut_4001Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.WarningEditPart.VISUAL_ID:
				return getWarning_4008Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID:
				return getExpected_4002Text(view);
			case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID:
				return getOutgoing_4003Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getIncoming_4005Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Incoming_4005,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDummy_2002Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_2002,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getWarning_4008Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Warning_4008,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.WarningNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getGlobalIncoming_4006Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalIncoming_4006, view
						.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalIncomingNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getShapesDiagram_1000Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getGlobalOutgoing_4007Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.GlobalOutgoing_4007, view
						.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.GlobalOutgoingNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getDummy_3003Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Dummy_3003,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getEnsemble_3001Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_3001,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getOutgoing_4003Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Outgoing_4003,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getInAndOut_4001Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.InAndOut_4001,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getNotAllowed_4004Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.NotAllowed_4004,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6004); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getEnsemble_2001Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Ensemble_2001,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getExpected_4002Text(View view) {
		IParser parser = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciParserProvider.getParser(
				de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciElementTypes.Expected_4002,
				view.getElement() != null ? view.getElement() : view,
				de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 6002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID
				.equals(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry.getModelID(view));
	}

}

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

import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.ui.providers.marker.AbstractModelMarkerNavigationProvider;

/**
 * @generated
 */
public class VespucciMarkerNavigationProvider extends
		AbstractModelMarkerNavigationProvider {

	/**
	 * @generated
	 */
	public static final String MARKER_TYPE = de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.ID
			+ ".diagnostic"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	protected void doGotoMarker(IMarker marker) {
		String elementId = marker
				.getAttribute(
						org.eclipse.gmf.runtime.common.core.resources.IMarker.ELEMENT_ID,
						null);
		if (elementId == null || !(getEditor() instanceof DiagramEditor)) {
			return;
		}
		DiagramEditor editor = (DiagramEditor) getEditor();
		Map editPartRegistry = editor.getDiagramGraphicalViewer()
				.getEditPartRegistry();
		EObject targetView = editor.getDiagram().eResource()
				.getEObject(elementId);
		if (targetView == null) {
			return;
		}
		EditPart targetEditPart = (EditPart) editPartRegistry.get(targetView);
		if (targetEditPart != null) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorUtil
					.selectElementsInDiagram(editor,
							Arrays.asList(new EditPart[] { targetEditPart }));
		}
	}

	/**
	 * @generated
	 */
	public static void deleteMarkers(IResource resource) {
		try {
			resource.deleteMarkers(MARKER_TYPE, true, IResource.DEPTH_ZERO);
		} catch (CoreException e) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().logError(
							"Failed to delete validation markers", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public static IMarker addMarker(IFile file, String elementId,
			String location, String message, int statusSeverity) {
		IMarker marker = null;
		try {
			marker = file.createMarker(MARKER_TYPE);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.LOCATION, location);
			marker.setAttribute(
					org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID,
					elementId);
			int markerSeverity = IMarker.SEVERITY_INFO;
			if (statusSeverity == IStatus.WARNING) {
				markerSeverity = IMarker.SEVERITY_WARNING;
			} else if (statusSeverity == IStatus.ERROR
					|| statusSeverity == IStatus.CANCEL) {
				markerSeverity = IMarker.SEVERITY_ERROR;
			}
			marker.setAttribute(IMarker.SEVERITY, markerSeverity);
		} catch (CoreException e) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().logError(
							"Failed to create validation marker", e); //$NON-NLS-1$
		}
		return marker;
	}
}

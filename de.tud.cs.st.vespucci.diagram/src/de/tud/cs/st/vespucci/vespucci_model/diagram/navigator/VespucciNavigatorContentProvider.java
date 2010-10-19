/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import java.util.LinkedList;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

/**
 * @generated
 */
public class VespucciNavigatorContentProvider implements ICommonContentProvider {

	/**
	 * @generated
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * @generated
	 */
	private Viewer myViewer;

	/**
	 * @generated
	 */
	private AdapterFactoryEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	private WorkspaceSynchronizer myWorkspaceSynchronizer;

	/**
	 * @generated
	 */
	private Runnable myViewerRefreshRunnable;

	/**
	 * @generated
	 */
	@SuppressWarnings( { "unchecked", "serial", "rawtypes" })
	public VespucciNavigatorContentProvider() {
		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
				.createEditingDomain();
		myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
		myEditingDomain.setResourceToReadOnlyMap(new HashMap() {
			public Object get(Object key) {
				if (!containsKey(key)) {
					put(key, Boolean.TRUE);
				}
				return super.get(key);
			}
		});
		myViewerRefreshRunnable = new Runnable() {
			public void run() {
				if (myViewer != null) {
					myViewer.refresh();
				}
			}
		};
		myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain,
				new WorkspaceSynchronizer.Delegate() {
					public void dispose() {
					}

					public boolean handleResourceChanged(final Resource resource) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}

					public boolean handleResourceDeleted(Resource resource) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}

					public boolean handleResourceMoved(Resource resource,
							final URI newURI) {
						for (Iterator it = myEditingDomain.getResourceSet()
								.getResources().iterator(); it.hasNext();) {
							Resource nextResource = (Resource) it.next();
							nextResource.unload();
						}
						if (myViewer != null) {
							myViewer.getControl().getDisplay().asyncExec(
									myViewerRefreshRunnable);
						}
						return true;
					}
				});
	}

	/**
	 * @generated
	 */
	public void dispose() {
		myWorkspaceSynchronizer.dispose();
		myWorkspaceSynchronizer = null;
		myViewerRefreshRunnable = null;
		for (Iterator it = myEditingDomain.getResourceSet().getResources()
				.iterator(); it.hasNext();) {
			Resource resource = (Resource) it.next();
			resource.unload();
		}
		((TransactionalEditingDomain) myEditingDomain).dispose();
		myEditingDomain = null;
	}

	/**
	 * @generated
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		myViewer = viewer;
	}

	/**
	 * @generated
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
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
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile) {
			IFile file = (IFile) parentElement;
			URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true);
			Resource resource = myEditingDomain.getResourceSet().getResource(
					fileURI, true);
			Collection result = new ArrayList();
			result
					.addAll(createNavigatorItems(
							selectViewsByType(
									resource.getContents(),
									de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID),
							file, false));
			return result.toArray();
		}

		if (parentElement instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup group = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup) parentElement;
			return group.getChildren();
		}

		if (parentElement instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem navigatorItem = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem) parentElement;
			if (navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView())) {
				return EMPTY_ARRAY;
			}
			return getViewChildren(navigatorItem.getView(), parentElement);
		}

		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Object[] getViewChildren(View view, Object parentElement) {
		switch (de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
				.getVisualID(view)) {

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup links = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_ShapesDiagram_1000_links,
					"icons/linksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getDiagramLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			links
					.addChildren(createNavigatorItems(connectedViews, links,
							false));
			connectedViews = getDiagramLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			links
					.addChildren(createNavigatorItems(connectedViews, links,
							false));
			connectedViews = getDiagramLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			links
					.addChildren(createNavigatorItems(connectedViews, links,
							false));
			connectedViews = getDiagramLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			links
					.addChildren(createNavigatorItems(connectedViews, links,
							false));
			connectedViews = getDiagramLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			links
					.addChildren(createNavigatorItems(connectedViews, links,
							false));
			if (!links.isEmpty()) {
				result.add(links);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup incominglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Ensemble_2001_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup outgoinglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Ensemble_2001_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartmentEditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup incominglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Dummy_2002_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup outgoinglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Dummy_2002_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup incominglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Ensemble_3001_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup outgoinglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Ensemble_3001_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEnsembleCompartment2EditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup incominglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Dummy_3003_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup outgoinglinks = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Dummy_3003_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup target = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Incoming_4005_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup source = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Incoming_4005_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup target = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Outgoing_4003_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup source = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Outgoing_4003_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup target = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_InAndOut_4001_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup source = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_InAndOut_4001_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup target = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_NotAllowed_4004_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup source = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_NotAllowed_4004_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart.VISUAL_ID: {
			Collection result = new ArrayList();
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup target = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Expected_4002_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup source = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorGroup(
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.NavigatorGroupName_Expected_4002_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksTargetByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.EnsembleEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Ensemble2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			connectedViews = getLinksSourceByType(
					Collections.singleton(view),
					de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
							.getType(de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}
		}
		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Collection getLinksSourceByType(Collection edges, String type) {
		Collection result = new ArrayList();
		for (Iterator it = edges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			View nextEdgeSource = nextEdge.getSource();
			if (type.equals(nextEdgeSource.getType())
					&& isOwnView(nextEdgeSource)) {
				result.add(nextEdgeSource);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getLinksTargetByType(Collection edges, String type) {
		Collection result = new ArrayList();
		for (Iterator it = edges.iterator(); it.hasNext();) {
			Edge nextEdge = (Edge) it.next();
			View nextEdgeTarget = nextEdge.getTarget();
			if (type.equals(nextEdgeTarget.getType())
					&& isOwnView(nextEdgeTarget)) {
				result.add(nextEdgeTarget);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getOutgoingLinksByType(Collection nodes, String type) {
		Collection result = new ArrayList();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getIncomingLinksByType(Collection nodes, String type) {
		Collection result = new ArrayList();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getChildrenByType(Collection nodes, String type) {
		Collection result = new ArrayList();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
			View nextNode = (View) it.next();
			result.addAll(selectViewsByType(nextNode.getChildren(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection getDiagramLinksByType(Collection diagrams, String type) {
		Collection result = new ArrayList();
		for (Iterator it = diagrams.iterator(); it.hasNext();) {
			Diagram nextDiagram = (Diagram) it.next();
			result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection selectViewsByType(Collection views, String type) {
		Collection result = new ArrayList();
		for (Iterator it = views.iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (type.equals(nextView.getType()) && isOwnView(nextView)) {
				result.add(nextView);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ShapesDiagramEditPart.MODEL_ID
				.equals(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciVisualIDRegistry
						.getModelID(view));
	}

	/**
	 * @generated
	 */
	private Collection createNavigatorItems(Collection views, Object parent,
			boolean isLeafs) {
		Collection result = new ArrayList();
		for (Iterator it = views.iterator(); it.hasNext();) {
			result
					.add(new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem(
							(View) it.next(), parent, isLeafs));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public Object getParent(Object element) {
		if (element instanceof de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciAbstractNavigatorItem) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciAbstractNavigatorItem abstractNavigatorItem = (de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciAbstractNavigatorItem) element;
			return abstractNavigatorItem.getParent();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean hasChildren(Object element) {
		return element instanceof IFile || getChildren(element).length > 0;
	}

}

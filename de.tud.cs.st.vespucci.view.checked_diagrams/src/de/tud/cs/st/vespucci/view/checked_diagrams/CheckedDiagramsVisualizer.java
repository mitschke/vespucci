/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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
package de.tud.cs.st.vespucci.view.checked_diagrams;

import java.util.LinkedList;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.view.checked_diagrams.views.CheckedDiagramsTableView;
import de.tud.cs.st.vespucci.view.model.Pair;

/**
 * Receive IViolationViews and delegate them to CheckedDiagramsTable for visualization
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
public class CheckedDiagramsVisualizer implements IResultProcessor {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.view.checked_diagrams";
	
	private static final String EXTENSIONPOINT_OBSERVERATTRIBUTE_NAME = "CheckedDiagrams";
	private static final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.view.checked_diagrams.checkedDiagramsObserver";

	protected static void processException(Exception e){
		final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}

	private CheckedDiagramsTableView view;

	@Override
	public void processResult(Object result, IFile diagramFile) {
		System.out.println("processResult");
		IViolationView violationView = Util.adapt(result, IViolationView.class);
		
		if (violationView != null){
			openView();
			if (view != null){
				Set<IFile> fetchCheckedDiagrams = view.fetchCheckedDiagrams();
				
				if (!fetchCheckedDiagrams.contains(diagramFile)){
					IPair<IViolationView, IFile> element = new Pair<IViolationView, IFile>(violationView, diagramFile);
					view.addEntry(element);
				}
	
				updateCheckedDiagrams();
				

			}
		}
	}

	public void updateCheckedDiagrams() {
		for (ICheckedDiagramsObserver checkedDiagramsObserver : getCheckedDiagramsObservers()) {
			checkedDiagramsObserver.updateCheckedDiagrams(view.fetchCheckedDiagrams());	
		}
	}

	private void openView() {
		try {
			IViewReference viewReference = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.findViewReference(PLUGIN_ID);
			view = viewReference != null ? (CheckedDiagramsTableView) viewReference
					.getPart(true) : null;
			if (view == null) {
				view = (CheckedDiagramsTableView) PlatformUI
						.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(PLUGIN_ID);
			}
			view.setVisualizer(this);
		} catch (PartInitException e) {
			processException(e);
		}
	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationView.class.equals(resultClass);
	}
	
	/**
	 * Return all available ICheckedDiagramsObservers
	 * 
	 * @return List of ProcessorItems containing IModelProcessor and corresponding label
	 */
	private LinkedList<ICheckedDiagramsObserver> getCheckedDiagramsObservers() {
		
		LinkedList<ICheckedDiagramsObserver> converterItems = new LinkedList<ICheckedDiagramsObserver>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		try {
			for (IConfigurationElement i : configurationElement) {

				// Get all Observers
				final Object o = i.createExecutableExtension(EXTENSIONPOINT_OBSERVERATTRIBUTE_NAME);

				if (o instanceof ICheckedDiagramsObserver) {
					converterItems.add((ICheckedDiagramsObserver)  o);
				}
			}

		} catch (CoreException ex) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, ex.getMessage(), ex);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

		return converterItems;
	}

}

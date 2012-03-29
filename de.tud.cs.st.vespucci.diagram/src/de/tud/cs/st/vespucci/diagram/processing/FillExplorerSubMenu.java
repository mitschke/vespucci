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
package de.tud.cs.st.vespucci.diagram.processing;

import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Adds the dynamic model processor items in the project explorer context Vespucci menu, 
 * depending on which processor Extensions are registered.
 * See ExtensionPoint de.tud.cs.st.vespucci.diagram.modelProcessors
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class FillExplorerSubMenu extends ContributionItem {
	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.diagram";

	private static final String EXTENSIONPOINT_PROCESSORATTRIBUTE_NAME = "ModelProcessor";
	private static final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.modelProcessors";
	private LinkedList<ProcessorItem<IModelProcessor>> processorItems;
	private LinkedList<IFile> diagramIFiles;
	private LinkedList<IResultProcessor> resultProcessors;

	public FillExplorerSubMenu() {
	}
	
	public FillExplorerSubMenu(String id) {
		super(id);
	}
	
	@Override
	public void fill(Menu menu, int index) {
		
		// Get all Processors for all registered Plug-Ins
		this.processorItems = getProcessorItems();
		this.diagramIFiles = getSelectedDiagramIFiles();
		this.resultProcessors = getResultProcessors();

		if (this.processorItems.size() == 0){
			MenuItem menuItem = new MenuItem(menu, SWT.CHECK);
			menuItem.setText("(No registered model processors)");
			menuItem.setEnabled(false);
		}else{
			for (final ProcessorItem<IModelProcessor> processor : processorItems) {
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
				menuItem.setText(processor.getLabel());
				menuItem.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {

						for (IFile diagramFile : diagramIFiles) {

							Object result = processor.getProcessor().processModel(diagramFile);
							
							for (IResultProcessor resultProcessor : resultProcessors) {
								if (resultProcessor.isInterested(processor.getProcessor().resultClass())){
									resultProcessor.processResult(result, diagramFile);
								}
							}
							
							try {
								refreshPageView(diagramFile);
							} catch (CoreException e1) {
								final IStatus is = new Status(IStatus.ERROR,PLUGIN_ID, e1.getMessage(), e1);
								StatusManager.getManager().handle(is, StatusManager.LOG);
							}
						}
					}
				});

			}}

	}

	/**
	 * Returns a list of IFiles which ends with *.sad and are currently selected
	 * 
	 * @return List of selected diagram files as IFiles
	 */
	private LinkedList<IFile> getSelectedDiagramIFiles() {

		LinkedList<IFile> diagramIFiles = new LinkedList<IFile>();

		IWorkbench workbench = PlatformUI.getWorkbench();
		IStructuredSelection selection = (IStructuredSelection) workbench
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();

		for (Object o : selection.toArray()) {
			if (o instanceof IFile) {
				final IFile file = (IFile) o;
				if (Util.isFileType(file, "sad")) {
					diagramIFiles.add(file);
				}
			}
		}

		return diagramIFiles;
	}

	private static void refreshPageView(final IFile file) throws CoreException {
		file.getProject().refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());
	}

	/**
	 * Return all available IModelProcessor
	 * 
	 * @return List of ProcessorItems containing IModelProcessor and corresponding label
	 */
	private LinkedList<ProcessorItem<IModelProcessor>> getProcessorItems() {
		
		LinkedList<ProcessorItem<IModelProcessor>> converterItems = new LinkedList<ProcessorItem<IModelProcessor>>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		try {
			for (IConfigurationElement i : configurationElement) {

				// Get all Processors
				final Object o = i.createExecutableExtension(EXTENSIONPOINT_PROCESSORATTRIBUTE_NAME);

				if (o instanceof IModelProcessor) {
					converterItems.add(new ProcessorItem<IModelProcessor>((IModelProcessor) o, i.getAttribute("Label")));
				}
			}

		} catch (CoreException ex) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, ex.getMessage(), ex);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

		return converterItems;
	}
	

	/**
	 * Returns a list of all registered resultProcessors
	 * See ExtensionPoint de.tud.cs.st.vespucci.diagram.resultProcessors
	 * @return List of all available IResultProcessors 
	 */
	private LinkedList<IResultProcessor> getResultProcessors() {
		
		LinkedList<IResultProcessor> returnProcessors = new LinkedList<IResultProcessor>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor("de.tud.cs.st.vespucci.diagram.resultProcessors");
		try {
			for (IConfigurationElement i : configurationElement) {

				// Get all Processors
				final Object o = i.createExecutableExtension("ResultProcessor");

				if (o instanceof IResultProcessor) {
					returnProcessors.add((IResultProcessor) o);
				}
			}

		} catch (CoreException ex) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, ex.getMessage(), ex);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		
		return returnProcessors;
	}


}

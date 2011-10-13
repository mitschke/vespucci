/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
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
package de.tud.cs.st.vespucci.diagram.processing;

import java.util.LinkedList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.statushandlers.StatusManager;



/**
 * Adds the dynamic model element processor items in the Vespucci Editor context menu, 
 * depending on which processor Extensions are registered.
 * See ExtensionPoint de.tud.cs.st.vespucci.diagram.modelElementProcessors
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class FillDiagramEditorSubMenu extends ContributionItem {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.diagram";
	private static final String EXTENSIONPOINT_PROCESSORATTRIBUTE_NAME = "ModelElementProcessor";
	private static final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.modelElementProcessors";
	private LinkedList<ProcessorItem<IModelElementProcessor>> processorItems;
	private LinkedList<Object> diagramElements;

	public FillDiagramEditorSubMenu() {
	}

	public FillDiagramEditorSubMenu(String id) {
		super(id);
	}
	
	@Override
	public void fill(Menu menu, int index) {

		// Get all Processors for all registered Plug-Ins
		this.processorItems = getProcessorItems();
		this.diagramElements = getSelectedDiagramElements();

		if (this.processorItems.size() == 0) {
			MenuItem menuItem = new MenuItem(menu, SWT.CHECK);
			menuItem.setText("(No registered model element processors)");
			menuItem.setEnabled(false);
		} else {
			for (final ProcessorItem<IModelElementProcessor> processorItem : processorItems) {
				MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
				menuItem.setText(processorItem.getLabel());
				menuItem.addSelectionListener(new SelectionAdapter() {

					public void widgetSelected(SelectionEvent e) {

						// TODO: ggf. Editorelemente (ensembles, Constraints, Shapes) erst "umwandeln" per Adapter
						processorItem.getProcessor().processElement(diagramElements);
					}
				});

			}
		}

	}

	private LinkedList<Object> getSelectedDiagramElements() {
		// TODO Auto-generated method stub
		return new LinkedList<Object>();
	}

	private LinkedList<ProcessorItem<IModelElementProcessor>> getProcessorItems() {
		
		LinkedList<ProcessorItem<IModelElementProcessor>> processorItems = new LinkedList<ProcessorItem<IModelElementProcessor>>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		try {
			for (IConfigurationElement i : configurationElement) {

				// Get all Processors
				final Object o = i.createExecutableExtension(EXTENSIONPOINT_PROCESSORATTRIBUTE_NAME);

				if (o instanceof IModelElementProcessor) {
					processorItems.add(new ProcessorItem<IModelElementProcessor>((IModelElementProcessor) o, i.getAttribute("Label")));
				}
			}

		} catch (CoreException ex) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, ex.getMessage(), ex);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

		return processorItems;
	}

}

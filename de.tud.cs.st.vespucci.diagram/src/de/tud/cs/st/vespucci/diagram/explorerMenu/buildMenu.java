package de.tud.cs.st.vespucci.diagram.explorerMenu;

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

public class buildMenu extends ContributionItem {

	private String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.diagramProcessors";
	private LinkedList<ProcessorItem> processorItems;
	private LinkedList<IFile> diagramIFiles;

	public buildMenu() {
	}

	public buildMenu(String id) {
		super(id);
	}
	
	@Override
	public void fill(Menu menu, int index) {

		// Get all Processors for all registered Plug-Ins
		this.processorItems = getProcessorItems();
		this.diagramIFiles = getSelectedDiagramIFiles();
		

		for (final ProcessorItem processorItem : processorItems) {
			MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
			menuItem.setText(processorItem.getLabel());
			menuItem.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					for (IFile diagramFile : diagramIFiles) {

						processorItem.getProcessors().process(diagramFile);
						try {
							refreshPageView(diagramFile);
						} catch (CoreException e1) {
							final IStatus is = new Status(IStatus.ERROR,"de.tud.cs.st.vespucci.diagram", e1.getMessage(), e1);
							StatusManager.getManager().handle(is, StatusManager.LOG);
						}
					}
				}
			});

		}

	}

	private LinkedList<IFile> getSelectedDiagramIFiles() {

		LinkedList<IFile> diagramIFiles = new LinkedList<IFile>();

		IWorkbench workbench = PlatformUI.getWorkbench();
		IStructuredSelection selection = (IStructuredSelection) workbench
				.getActiveWorkbenchWindow().getSelectionService()
				.getSelection();

		for (Object o : selection.toArray()) {
			if (o instanceof IFile) {
				final IFile file = (IFile) o;
				if (isDiagramFile(file)) {
					diagramIFiles.add(file);
				}
			}
		}

		return diagramIFiles;
	}

	private static boolean isDiagramFile(final IFile file) {
		
		final String extension = file.getName().substring(
				(file.getName().length() - 3), file.getName().length());
		return (extension.equals("sad"));
	}

	private static void refreshPageView(final IFile file) throws CoreException {
		file.getProject().refreshLocal(IResource.DEPTH_INFINITE,
				new NullProgressMonitor());
	}

	private LinkedList<ProcessorItem> getProcessorItems() {
		
		LinkedList<ProcessorItem> converterItems = new LinkedList<ProcessorItem>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		try {
			for (IConfigurationElement i : configurationElement) {

				// Handler holen
				final Object o = i.createExecutableExtension("DiagramProcessor");

				if (o instanceof IDiagramProcessor) {
					converterItems.add(new ProcessorItem((IDiagramProcessor) o, i.getAttribute("Label")));
				}
			}

		} catch (CoreException ex) {
			final IStatus is = new Status(IStatus.ERROR, "de.tud.cs.st.vespucci.diagram", ex.getMessage(), ex);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

		return converterItems;
	}

}

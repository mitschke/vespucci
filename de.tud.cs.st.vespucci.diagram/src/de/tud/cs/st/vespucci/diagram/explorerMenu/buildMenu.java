package de.tud.cs.st.vespucci.diagram.explorerMenu;

import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class buildMenu extends ContributionItem {

	private String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.converters";
	private LinkedList<ConverterItem> converterItems;
	private LinkedList<IFile> diagramIFiles;

	public buildMenu() {
	}

	public buildMenu(String id) {
		super(id);
	}
	
	@Override
	public void fill(Menu menu, int index) {

		// Get all converters for all registered Plug-Ins
		this.converterItems = getConverterItems();
		this.diagramIFiles = getSelectedDiagramIFiles();
		

		for (final ConverterItem converterItem : converterItems) {
			MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
			menuItem.setText(converterItem.getLabel());
			menuItem.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {

					for (IFile diagramFile : diagramIFiles) {

						converterItem.getConverter().process(diagramFile);
						try {
							refreshPageView(diagramFile);
						} catch (CoreException e1) {
							e1.printStackTrace();
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

	private LinkedList<ConverterItem> getConverterItems() {
		
		LinkedList<ConverterItem> converterItems = new LinkedList<ConverterItem>();
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		try {
			for (IConfigurationElement i : configurationElement) {

				// Handler holen
				final Object o = i.createExecutableExtension("DiagramConverter");

				if (o instanceof IDiagramProcessor) {
					converterItems.add(new ConverterItem((IDiagramProcessor) o, i.getAttribute("Label")));
				}
			}

		} catch (CoreException ex) {
			System.err.print(ex.getMessage());
		}
		
		return converterItems;
	}

}

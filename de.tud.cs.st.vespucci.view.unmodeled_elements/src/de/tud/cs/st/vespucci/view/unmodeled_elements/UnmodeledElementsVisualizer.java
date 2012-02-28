package de.tud.cs.st.vespucci.view.unmodeled_elements;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IUnmodeledElementView;
import de.tud.cs.st.vespucci.utilities.Util;

public class UnmodeledElementsVisualizer implements IResultProcessor {

	public static final String ViewId = "de.tud.cs.st.vespucci.unmodeled_elements.table";

	@Override
	public void processResult(Object result, IFile file) {
		IUnmodeledElementView elements = Util.adapt(result,
				IUnmodeledElementView.class);
		if (elements != null) {
			try {
				IViewReference viewReference = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.findViewReference(ViewId);
				UnmodeledElementsTableView view = viewReference != null ? (UnmodeledElementsTableView) viewReference
						.getPart(true) : null;
				if (view == null) {
					view = (UnmodeledElementsTableView) PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().showView(ViewId);

				}
				view.addEntry(file.getProject(), elements);
				view.displayProject(file.getProject());
			} catch (PartInitException e) {
				final IStatus is = new Status(IStatus.ERROR, ViewId,
						e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IUnmodeledElementView.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		// unused in this ResultProcessor
	}

}

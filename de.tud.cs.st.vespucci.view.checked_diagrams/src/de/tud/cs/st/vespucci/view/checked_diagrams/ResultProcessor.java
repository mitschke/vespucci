package de.tud.cs.st.vespucci.view.checked_diagrams;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.view.checked_diagrams.model.Pair;
import de.tud.cs.st.vespucci.view.checked_diagrams.views.CheckedDiagrams;

public class ResultProcessor implements IResultProcessor {

	private String PLUGIN_ID = "de.tud.cs.st.vespucci.view.currentdiagrams.views.CurrentDiagramsView";
	
	public ResultProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processResult(Object result, IFile diagramFile) {
		
		IViolationView violationView = Util.adapt(result, IViolationView.class);
		
		if (violationView != null){
			
			IPair<IViolationView, IFile> element = new Pair<IViolationView, IFile>(violationView, diagramFile);
			
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(PLUGIN_ID);		
				
				CheckedDiagrams.addElement(element);
				
			} catch (PartInitException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationView.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		// unused in this result processor
	}

}

package de.tud.cs.st.vespucci.ensemblesourcemap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.ensemblesourcemap.views.EnsembleSourceMapView;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleToSourceMapVisualiser implements IResultProcessor {

	public static final String ID = "de.tud.cs.st.vespucci.ensemblesourcemap";
	
	@Override
	public void processResult(Object result, IProject project) {
		IEnsembleElementList ensembleElementList = Util.adapt(result, IEnsembleElementList.class);
		if (ensembleElementList != null){
			EnsembleSourceProject temp = new EnsembleSourceProject(ensembleElementList);
		
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("de.tud.cs.st.vespucci.ensemblesourcemap");
				
				EnsembleSourceMapView.pseudoSingelton.addEnsembleSourceProject(new EnsembleSourceProject(ensembleElementList));

			} catch (PartInitException e) {
				final IStatus is = new Status(IStatus.ERROR, ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			
			//Debug
			temp.print();
		
		}
	}

	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IEnsembleElementList.class.equals(resultClass);
	}

	@Override
	public void cleanUp() {
		//unused in this ResultProcessor
	}

}

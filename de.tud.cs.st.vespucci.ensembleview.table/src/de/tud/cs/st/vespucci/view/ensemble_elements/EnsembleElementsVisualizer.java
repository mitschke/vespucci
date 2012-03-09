package de.tud.cs.st.vespucci.view.ensemble_elements;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.view.ensemble_elements.model.DataManager;
import de.tud.cs.st.vespucci.view.ensemble_elements.model.TableModel;
import de.tud.cs.st.vespucci.view.ensemble_elements.views.EnsembleElementsTableView;

public class EnsembleElementsVisualizer implements IResultProcessor{

	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.view.ensemble_elements";
	
	@Override
	public void processResult(Object result, IFile diagramFile) {
		IEnsembleElementList ensembleElementList = Util.adapt(result, IEnsembleElementList.class);
		if (ensembleElementList != null){	
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(PLUGIN_ID);		
				
				DataManager<TableModel> dataManager = new DataManager<TableModel>(ensembleElementList, diagramFile.getProject(), new TableModel());
				EnsembleElementsTableView.Table.addDataManager(dataManager);
				
			} catch (PartInitException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
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

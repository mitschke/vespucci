package de.tud.cs.st.vespucci.ensembleview.table;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.ensembleview.table.model.TableModel;
import de.tud.cs.st.vespucci.ensembleview.table.views.EnsembleElementsTableView;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleElementsVisualizer implements IResultProcessor{

	public static final String ID = "de.tud.cs.st.vespucci.ensembleview.table";
	
	@Override
	public void processResult(Object result, IProject project) {
		IEnsembleElementList ensembleElementList = Util.adapt(result, IEnsembleElementList.class);
		if (ensembleElementList != null){	
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(ID);		
				
				DataManager<TableModel> dataManager = new DataManager<TableModel>(ensembleElementList, project, new TableModel());
				
				EnsembleElementsTableView.Table.setDataManager(dataManager);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

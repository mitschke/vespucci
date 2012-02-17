package de.tud.cs.st.vespucci.ensembleview;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.ensembleview.model.TreeElement;
import de.tud.cs.st.vespucci.ensembleview.views.EnsembleSourceMapView;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleToSourceMapVisualiser implements IResultProcessor {

	public static final String ID = "de.tud.cs.st.vespucci.ensembleview";
	
	@Override
	public void processResult(Object result, IFile file) {
		IEnsembleElementList ensembleElementList = Util.adapt(result, IEnsembleElementList.class);
		if (ensembleElementList != null){
			EnsembleSourceProject temp = new EnsembleSourceProject(ensembleElementList);
		
			
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage().showView(ID);

				for (TreeElement<IEnsemble> iterable_element : new EnsembleSourceProject(
						ensembleElementList).getElements()) {
					iterable_element.print("");
				}

				EnsembleSourceMapView.treeTable
						.addEnsembleSourceProject(temp);
			
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

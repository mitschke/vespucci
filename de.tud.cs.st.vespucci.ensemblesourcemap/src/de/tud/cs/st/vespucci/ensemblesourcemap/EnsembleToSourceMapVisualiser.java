package de.tud.cs.st.vespucci.ensemblesourcemap;

import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.ensemblesourcemap.views.EnsembleSourceMapView;
import de.tud.cs.st.vespucci.interfaces.IEnsembleElementList;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleToSourceMapVisualiser implements IResultProcessor {

	@Override
	public void processResult(Object result, IProject project) {
		IEnsembleElementList ensembleElementList = Util.adapt(result, IEnsembleElementList.class);
		if (ensembleElementList != null){
			EnsembleSourceMapView.pseudoSingelton.addEnsembleSourceProject(new EnsembleSourceProject(ensembleElementList));
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

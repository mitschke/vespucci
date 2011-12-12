package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import de.tud.cs.st.vespucci.diagram.processing.IModelElementProcessor;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleElementModelElementProcessor implements
		IModelElementProcessor {

	public EnsembleElementModelElementProcessor() {
	}

	@Override
	public void processElement(Object diagramElement) {
		// TODO this should also be statically enforced by the plug-in descriptor
		
		// guard against malformed input
		if(! (diagramElement instanceof IEnsemble) )
			return;

	}

}

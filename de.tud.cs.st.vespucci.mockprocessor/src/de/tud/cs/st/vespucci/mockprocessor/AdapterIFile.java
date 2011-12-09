package de.tud.cs.st.vespucci.mockprocessor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;

import de.tud.cs.st.vespucci.diagram.model.output.spi.Ensemble;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class AdapterIFile implements IAdapterFactory {
	
	private static Class<?>[] adapterList = { DiagramModel.class };

	@Override
	public Object getAdapter(Object adaptableObject, @SuppressWarnings("rawtypes") Class adapterType) {
		
		if (adapterType == DiagramModel.class) {
			return createDiagram((IFile) adaptableObject);
		}

		return null;
	}
	
	private DiagramModel createDiagram(IFile diagramFile) {
		
		ShapesDiagram d = Util.adapt(diagramFile, ShapesDiagram.class);
	
		if (d != null){
			Set<IEnsemble> ensembles = new HashSet<IEnsemble>();
			
			for (Shape shape : d.getShapes()) {
				ensembles.add(new Ensemble(shape));
			}
			
			return new DiagramModel(ensembles);
		}
		
		return null;		
		
	}

	@Override
	public Class<?>[] getAdapterList() {
		return adapterList;
	}
}

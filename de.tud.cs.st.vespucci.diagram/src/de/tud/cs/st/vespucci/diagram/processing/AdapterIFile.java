package de.tud.cs.st.vespucci.diagram.processing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

public class AdapterIFile implements IAdapterFactory {

	private static Class[] adapterList = { IFile.class, ShapesDiagram.class };

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {

		if (adapterType == IFile.class) {
			return ((IFile) adaptableObject);

		} else if (adapterType == ShapesDiagram.class) {
			return createDiagram((IFile) adaptableObject);
		}

		return null;
	}

	private ShapesDiagram createDiagram(IFile diagramFile) {

		final XMIResourceImpl diagramResource = new XMIResourceImpl();
		FileInputStream diagramStream;
		try {
			diagramStream = new FileInputStream(diagramFile.getRawLocation().toFile());
			diagramResource.load(diagramStream, new HashMap<Object, Object>());

		} catch (Exception e) {

		}

		// Find the ShapesDiagram-EObject
		for (int i = 0; i < diagramResource.getContents().size(); i++) {
			if (diagramResource.getContents().get(i) instanceof ShapesDiagram) {
				final EObject eObject = diagramResource.getContents().get(i);
				return (ShapesDiagram) eObject;
			}
		}


			try {
				throw new FileNotFoundException(
						"ShapesDiagram could not be found in Document.");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}

	@Override
	public Class[] getAdapterList() {

		return this.adapterList;
	}

}

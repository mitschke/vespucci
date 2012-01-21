package de.tud.cs.st.vespucci.utilities;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.tud.cs.st.vespucci.vespucci_model.Vespucci_modelPackage;

public class EcoreUtil {

	public static Object loadVespucciModel(String fileName) throws IOException {

		// Create a resource set to hold the resources.
		//
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the appropriate resource factory to handle all file
		// extensions.
		//
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		// Register the package to ensure it is available during loading.
		//
		resourceSet.getPackageRegistry().put(Vespucci_modelPackage.eNS_URI,
				Vespucci_modelPackage.eINSTANCE);

		URI uri = URI.createFileURI(fileName);

		resourceSet.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
		
		// Demand load resource for this file.
		Resource resource = resourceSet.getResource(uri, true);
		
		
		for (EObject eObject : resource.getContents()) {
			if( eObject.eClass().equals(Vespucci_modelPackage.eINSTANCE.getShapesDiagram()))
				return eObject;
		}
		
		throw new IllegalStateException(fileName + " did not contain a vespuci model [ShapesDiagram.class].");
	}

}

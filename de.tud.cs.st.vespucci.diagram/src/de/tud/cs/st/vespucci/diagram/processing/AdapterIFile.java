package de.tud.cs.st.vespucci.diagram.processing;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;

public class AdapterIFile implements IAdapterFactory {
	
	private static Class[] adapterList = {
		IFile.class}; 

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		
		if (adapterType == IFile.class){
			return ((IFile)adaptableObject);
		}
		
		return null;
	}

	@Override
	public Class[] getAdapterList() {

		return this.adapterList;
	}

}

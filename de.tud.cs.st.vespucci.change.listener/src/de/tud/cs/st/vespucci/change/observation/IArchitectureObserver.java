package de.tud.cs.st.vespucci.change.observation;

import org.eclipse.core.resources.IResource;

public interface IArchitectureObserver {

	void architectureDiagramAdded(IResource resource);

	void architectureDiagramRemoved(IResource resource);

	void architectureDiagramChanged(IResource resource);

}

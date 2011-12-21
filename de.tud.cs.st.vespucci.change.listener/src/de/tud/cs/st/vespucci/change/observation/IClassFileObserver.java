package de.tud.cs.st.vespucci.change.observation;

import org.eclipse.core.resources.IResource;

public interface IClassFileObserver {

	void classFileAdded(IResource resource);

	void classFileRemoved(IResource resource);

	void classFileChanged(IResource resource);

}

package de.tud.cs.st.vespucci.unmodeled_elements.view;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.interfaces.IDataView;

public interface IProjectElementView<T> {

	void displayProject(IProject project);
	
	IProject displayedProject();
	
	void addEntry(IProject project, IDataView<T> elements);
	
	Iterator<IProject> getEntryKeys();

}

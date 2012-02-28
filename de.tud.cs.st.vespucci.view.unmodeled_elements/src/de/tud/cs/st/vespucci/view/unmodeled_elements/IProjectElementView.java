package de.tud.cs.st.vespucci.view.unmodeled_elements;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.interfaces.IDataView;

public interface IProjectElementView<T> {

	void displayProject(IProject project);
	
	IProject displayedProject();
	
	void addEntry(IProject project, IDataView<T> elements);
	
	Iterator<IProject> getEntryKeys();

}

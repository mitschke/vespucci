package de.tud.cs.st.vespucci.diagram.processing;

import org.eclipse.core.resources.IProject;

public interface IReturnProcessor {

	// ReturnProcessor wird Ergebniss eines Processors informiert
	// und erhält auch eine Refernz auf das IProject
	void update(Object object, IProject project);
	
	// Gibt an, ob der ReturnProcessor an einem bestimmten RückgabeTyp interessiert ist
	boolean isInterested(Class<?> type);
}
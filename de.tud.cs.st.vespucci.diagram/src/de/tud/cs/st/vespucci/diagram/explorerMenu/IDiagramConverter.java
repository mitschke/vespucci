package de.tud.cs.st.vespucci.diagram.explorerMenu;

import java.io.File;


public interface IDiagramConverter {

	public void convert(File diagramFile);
	
	/*
	 * ToDo: For further Conventions with Ralf
	 */
	
	@Deprecated
	public void convert(Object diagramElement);
	
}

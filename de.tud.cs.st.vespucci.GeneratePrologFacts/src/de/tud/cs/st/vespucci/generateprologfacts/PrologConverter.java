package de.tud.cs.st.vespucci.generateprologfacts;

import java.io.File;

import de.tud.cs.st.vespucci.diagram.explorerMenu.IDiagramConverter;

public class PrologConverter implements IDiagramConverter {

	public PrologConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(File diagramFile) {
		
		System.out.println("HelloFromProloCreator");

	}

	@Override
	public void convert(Object diagramElement) {
		
		System.out.println("HelloFromProloCreatorObject");

	}

}

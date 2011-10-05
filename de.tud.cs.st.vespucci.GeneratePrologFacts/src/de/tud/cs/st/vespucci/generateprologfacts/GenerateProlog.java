package de.tud.cs.st.vespucci.generateprologfacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.tud.cs.st.vespucci.diagram.explorerMenu.IDiagramConverter;
import de.tud.cs.st.vespucci.exceptions.VespucciIOException;
import de.tud.cs.st.vespucci.generateprologfacts.creator.PrologFileCreator;

public class GenerateProlog implements IDiagramConverter {

	public GenerateProlog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convert(File diagramFile) {
		
		final PrologFileCreator prologFileCreator = new PrologFileCreator();

		try {
			prologFileCreator.createPrologFileFromDiagram(diagramFile);
		} catch (final FileNotFoundException e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		} catch (final IOException e) {
			throw new VespucciIOException(String.format("Failed to save Prolog file from [%s].",diagramFile), e);
		} catch (final Exception e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		}

	}

	@Override
	public void convert(Object diagramElement) {
		
		System.out.println("HelloFromProloCreatorObject");

	}

}

package de.tud.cs.st.vespucci.generateprologfacts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import de.tud.cs.st.vespucci.diagram.explorerMenu.IDiagramProcessor;
import de.tud.cs.st.vespucci.exceptions.VespucciIOException;
import de.tud.cs.st.vespucci.generateprologfacts.creator.PrologFileCreator;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.IStorageClient;
import org.eclipse.core.resources.IFile;






public class GenerateProlog implements IDiagramProcessor, IStorageClient {

	public GenerateProlog() {
	}

	@Override
	public void process(IFile diagramFile) {
		
		final PrologFileCreator prologFileCreator = new PrologFileCreator();
	
		try {
			prologFileCreator.createPrologFileFromDiagram(diagramFile.getRawLocation().toFile());
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

	@Override
	public void doSave(String filePathSAD, String fileNameSAD) {
		
		final PrologFileCreator prologFileCreator = new PrologFileCreator();

		try {
			prologFileCreator.createPrologFileFromDiagram(filePathSAD, fileNameSAD);
		} catch (final FileNotFoundException e) {
			throw new VespucciIOException(String.format("File [%s] not found.",filePathSAD, fileNameSAD), e);
		} catch (final IOException e) {
			throw new VespucciIOException(String.format("Failed to save Prolog file from [%s].",filePathSAD, fileNameSAD), e);
		} catch (final Exception e) {
			throw new VespucciIOException(String.format("File [%s] not found.",filePathSAD, fileNameSAD), e);
		}
	}

}

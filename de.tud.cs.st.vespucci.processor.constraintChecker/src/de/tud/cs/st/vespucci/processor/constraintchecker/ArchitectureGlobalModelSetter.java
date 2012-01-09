package de.tud.cs.st.vespucci.processor.constraintchecker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureGlobalModelSetter implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IProject project = diagramFile.getProject();

		ArchitectureDatabaseProvider databaseProvider = ArchitectureDatabaseProvider
				.getInstance();

			
		databaseProvider.initializeDatabase(project);

		databaseProvider.setGlobalModelFileForProject(diagramFile, project);

		return null;
	}

	@Override
	public Class<?> resultClass() {
		return null;
	}

}

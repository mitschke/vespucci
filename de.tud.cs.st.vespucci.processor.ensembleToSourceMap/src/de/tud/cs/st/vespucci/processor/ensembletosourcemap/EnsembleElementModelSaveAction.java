package de.tud.cs.st.vespucci.processor.ensembletosourcemap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelSaveAction;
import de.tud.cs.st.vespucci.utilities.Util;

public class EnsembleElementModelSaveAction implements IModelSaveAction {

	public EnsembleElementModelSaveAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(Object diagramModel) {
		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IProject project = diagramFile.getProject();

		ArchitectureDatabaseProvider provider = ArchitectureDatabaseProvider.getInstance();
		provider.initializeDatabase(project);
		provider.updateModelFileForProject(diagramFile, project);
		
	}

}

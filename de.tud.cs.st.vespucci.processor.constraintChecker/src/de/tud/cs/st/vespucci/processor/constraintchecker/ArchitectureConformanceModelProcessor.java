package de.tud.cs.st.vespucci.processor.constraintchecker;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureConformanceModelProcessor implements IModelProcessor {

	@Override
	public Object processModel(Object diagramModel) {
		return getViolationView(diagramModel);
	}

	@Override
	public Class<?> resultClass() {
		return IViolationView.class;
	}

	public static IViolationView getViolationView(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);
		
		IProject project = diagramFile.getProject();

		UnissonDatabase db = ArchitectureDatabaseProvider.getInstance().getArchitectureDatabase(project);
		
		ViolationView violationView = new ViolationView(db.violations());
		
		ArchitectureDatabaseProvider.getInstance().initializeDatabase(project);
		
		ArchitectureDatabaseProvider.getInstance().addModelFileToProject(diagramFile, project);
		
		return violationView;
	}
	
}

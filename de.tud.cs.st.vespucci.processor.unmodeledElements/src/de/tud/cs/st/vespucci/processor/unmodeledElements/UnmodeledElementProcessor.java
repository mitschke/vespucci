package de.tud.cs.st.vespucci.processor.unmodeledElements;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.database.architecture.provider.ArchitectureDatabaseProvider;
import de.tud.cs.st.vespucci.diagram.processing.IModelProcessor;
import de.tud.cs.st.vespucci.interfaces.IUnmodeledElementView;
import de.tud.cs.st.vespucci.utilities.Util;

public class UnmodeledElementProcessor implements IModelProcessor {

	/**
	 * 
	 */
	@Override
	public Object processModel(Object diagramModel) {

		IFile diagramFile = Util.adapt(diagramModel, IFile.class);

		IProject project = diagramFile.getProject();

		UnissonDatabase database = ArchitectureDatabaseProvider.getInstance()
				.getArchitectureDatabase(project);

		ArchitectureDatabaseProvider.getInstance().initializeDatabase(project);

		return new UnmodeledElementView(database.unmodeled_elements());
	}

	/**
	 * This processor does not return any values
	 */
	@Override
	public Class<?> resultClass() {
		return IUnmodeledElementView.class;
	}



}

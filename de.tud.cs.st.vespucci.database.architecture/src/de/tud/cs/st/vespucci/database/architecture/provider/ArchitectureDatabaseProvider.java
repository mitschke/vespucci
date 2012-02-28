package de.tud.cs.st.vespucci.database.architecture.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import sae.bytecode.Database;
import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.architecture.listener.ArchitectureFileProcessor;
import de.tud.cs.st.vespucci.change.observation.IArchitectureObserver;
import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;
import de.tud.cs.st.vespucci.database.architecture.Activator;
import de.tud.cs.st.vespucci.database.bytecode.provider.BytecodeDatabaseProvider;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;

public class ArchitectureDatabaseProvider {

	private Map<IProject, UnissonDatabase> databases = new HashMap<IProject, UnissonDatabase>();

	private Map<IProject, Boolean> initializations = new HashMap<IProject, Boolean>();

	private Map<IProject, ArchitectureFileProcessor> observers = new HashMap<IProject, ArchitectureFileProcessor>();

	public static ArchitectureDatabaseProvider getInstance() {
		return Activator.getDefault().getDatabaseProvider();
	}

	public ArchitectureDatabaseProvider() {
	}

	/**
	 * Get the bytecode database for a given project. If no database is present
	 * a new one will be created and returned. A listener for changes to
	 * classfiles is installed that automatically updates the database.
	 * 
	 * @param project
	 * @return
	 */
	public UnissonDatabase getArchitectureDatabase(IProject project) {
		if (databases.containsKey(project))
			return databases.get(project);

		UnissonDatabase database = createDatabase(project);

		databases.put(project, database);
		addArchitectureFileListener(project, database);

		return database;
	}

	/**
	 * Initialize the byte code database for a given project. If the database is
	 * not yet created one will be created
	 * 
	 * @param project
	 */
	public void initializeDatabase(IProject project) {
		if (initializations.containsKey(project))
			return;

		if (!databases.containsKey(project))
		{
			UnissonDatabase database = createDatabase(project);
			databases.put(project, database);
			addArchitectureFileListener(project, database);
		}

		BytecodeDatabaseProvider.getInstance().initializeDatabase(project);
		
		// UnissonDatabase database = databases.get(project);

		// currently a no-op

		initializations.put(project, true);
	}

	/**
	 * Destroy the architecture database for a given project.
	 * 
	 * @param project
	 */
	public void disposeDatabase(IProject project) {
		databases.remove(project);
		initializations.remove(project);
		removeArchitectureFileListener(project);
	}

	protected UnissonDatabase createDatabase(IProject project) {
		Database database = BytecodeDatabaseProvider.getInstance()
				.getBytecodeDatabase(project);
		return new UnissonDatabase(database);
	}

	public void addModelFileToProject(IFile modelFile, IProject project) {
		if (!databases.containsKey(project))
			return;

		ArchitectureFileProcessor fileProcessor = observers.get(project);
		
		if(!fileProcessor.isModel(modelFile))
		{
			fileProcessor.addConstraintModel(modelFile);
		}
		else
		{
			fileProcessor.architectureDiagramChanged(modelFile);
		}
	}

	public void removeModelFileFromProject(IFile modelFile, IProject project) {
		if (!databases.containsKey(project))
			return;
		
		ArchitectureFileProcessor fileProcessor = observers.get(project);
		fileProcessor.deleteConstraintModel(modelFile);
	}
	
	
	public void updateModelFileForProject(IFile modelFile, IProject project) {
		if (!databases.containsKey(project))
			return;
		
		ArchitectureFileProcessor fileProcessor = observers.get(project);
		fileProcessor.architectureDiagramChanged(modelFile);
	}
	

	public void setGlobalModelFileForProject(IFile modelFile,
			IProject project) {
		
		ArchitectureFileProcessor fileProcessor = observers.get(project);
		if(!fileProcessor.isGlobalModel(modelFile))
		{
			fileProcessor.setEnsembleRepository(modelFile);
		}
		else {
			fileProcessor.architectureDiagramChanged(modelFile);
		}
	}

	/**
	 * Install the listener that updates the database for a given project.
	 * 
	 * @param project
	 * @param database
	 */
	private void addArchitectureFileListener(IProject project,
			UnissonDatabase database) {
		if (observers.containsKey(project))
			return;

		ArchitectureFileProcessor observer = new ArchitectureFileProcessor(
				database, new StateLocationCopyService(Activator.getDefault()
						.getStateLocation(), ResourcesPlugin.getWorkspace()
						.getRoot()), Activator.PLUGIN_ID);
		VespucciChangeProvider.getInstance().registerArchitectureObserver(
				project, observer);
		observers.put(project, observer);
	}

	/**
	 * Unregisters the listener of the architecture database for a given
	 * project.
	 * 
	 * @param project
	 */
	private void removeArchitectureFileListener(IProject project) {
		if (!observers.containsKey(project))
			return;
		IArchitectureObserver observer = observers.get(project);
		VespucciChangeProvider.getInstance().unregisterArchitectureObserver(
				project, observer);
	}

}

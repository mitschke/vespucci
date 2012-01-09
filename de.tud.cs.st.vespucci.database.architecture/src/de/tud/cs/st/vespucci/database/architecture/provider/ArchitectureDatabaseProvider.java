package de.tud.cs.st.vespucci.database.architecture.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.architecture.listener.ArchitectureFileListener;
import de.tud.cs.st.vespucci.change.observation.IArchitectureObserver;
import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;
import de.tud.cs.st.vespucci.database.architecture.Activator;
import de.tud.cs.st.vespucci.database.bytecode.provider.BytecodeDatabaseProvider;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;
import de.tud.cs.st.vespucci.utilities.Util;

public class ArchitectureDatabaseProvider {

	private Map<IProject, UnissonDatabase> databases = new HashMap<IProject, UnissonDatabase>();

	private Map<IProject, Boolean> initializations = new HashMap<IProject, Boolean>();

	private Map<IProject, ArchitectureFileListener> observers = new HashMap<IProject, ArchitectureFileListener>();

	private StateLocationCopyService copyService = new StateLocationCopyService(
			Activator.getDefault().getStateLocation(), ResourcesPlugin
					.getWorkspace().getRoot());

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

		UnissonDatabase database = databases.get(project);
		IArchitectureModel model = Util.adapt(modelFile,
				IArchitectureModel.class);
		database.addModel(model);
		
		try {
			copyService.makeShadowCopy(modelFile);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to create a shadow copy of resource: "
							+ modelFile.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
		
		ArchitectureFileListener fileListener = observers.get(project);
		fileListener.registerModel(modelFile);

	}

	public void removeModelFileFromProject(IFile modelFile, IProject project) {
		UnissonDatabase database = databases.get(project);
		IArchitectureModel model = Util.adapt(modelFile,
				IArchitectureModel.class);
		
		database.removeModel(model);
		
		try {
			copyService.deleteShadowCopy(modelFile);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to create a shadow copy of resource: "
							+ modelFile.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
		
		ArchitectureFileListener fileListener = observers.get(project);
		fileListener.unregisterModel(modelFile);
	}

	private boolean isGlobalModelSet = false;

	public void setGlobalModelFileForProject(IFile modelFile,
			IProject project) {
		UnissonDatabase database = databases.get(project);
		IArchitectureModel model = Util.adapt(modelFile, IArchitectureModel.class);
		
		if( !isGlobalModelSet ){ 
			database.addGlobalModel(model);
		}	
		else {
			IFile shadowCopyFile = copyService.getShadowCopyFile(modelFile);
			IArchitectureModel oldModel = Util.adapt(shadowCopyFile, IArchitectureModel.class);			
			database.updateGlobalModel(oldModel, model);
		}
		
		try {
			copyService.makeShadowCopy(modelFile);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to create a shadow copy of resource: "
							+ modelFile.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
		isGlobalModelSet = true;
		ArchitectureFileListener fileListener = observers.get(project);
		fileListener.setGlobalModel(modelFile);
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

		ArchitectureFileListener observer = new ArchitectureFileListener(
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

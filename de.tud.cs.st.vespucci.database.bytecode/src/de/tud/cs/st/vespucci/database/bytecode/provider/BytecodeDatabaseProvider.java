package de.tud.cs.st.vespucci.database.bytecode.provider;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import sae.bytecode.MaterializedDatabase;
import de.tud.cs.st.vespucci.bytecode.listener.ClassFileObserver;
import de.tud.cs.st.vespucci.change.observation.IClassFileObserver;
import de.tud.cs.st.vespucci.change.observation.VespucciChangeProvider;
import de.tud.cs.st.vespucci.database.bytecode.Activator;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.utilities.Util.Selection;

public class BytecodeDatabaseProvider {

	private Map<IProject, Database> databases = new HashMap<IProject, Database>();

	private Map<IProject, Boolean> initializations = new HashMap<IProject, Boolean>();

	private Map<IProject, IClassFileObserver> observers = new HashMap<IProject, IClassFileObserver>();

	public static BytecodeDatabaseProvider getInstance() {
		return Activator.getDefault().getDatabaseProvider();
	}

	public BytecodeDatabaseProvider() {
	
	}

	/**
	 * Get the bytecode database for a given project. If no database is present
	 * a new one will be created and returned. A listener for changes to
	 * classfiles is installed that automatically updates the database.
	 * 
	 * @param project
	 * @return
	 */
	public Database getBytecodeDatabase(IProject project) {
		if (databases.containsKey(project))
			return databases.get(project);

		Database database = createDatabase();

		databases.put(project, database);
		addClassFileListener(project, database);

		return database;
	}

	/**
	 * Initialize the byte code database for a given project. If the database is
	 * not yet created this is a no-op.
	 * 
	 * @param project
	 */
	public void initializeDatabase(IProject project) {
		if (initializations.containsKey(project))
			return;

		if (!databases.containsKey(project))
			return;

		Database database = databases.get(project);

		fillDatabase(database, project);

		initializations.put(project, true);
	}

	/**
	 * Destroy the bytecode database for a given project.
	 * 
	 * @param project
	 */
	public void disposeDatabase(IProject project) {
		databases.remove(project);
		initializations.remove(project);
		removeClassFileListener(project);
	}

	protected Database createDatabase() {
		return new MaterializedDatabase();
	}

	/**
	 * Install the listener that updates the database for a given project.
	 * 
	 * @param project
	 * @param database
	 */
	private void addClassFileListener(IProject project, Database database) {
		if (observers.containsKey(project))
			return;

		ClassFileObserver observer = new ClassFileObserver(database, new StateLocationCopyService(Activator.getDefault().getStateLocation(), ResourcesPlugin.getWorkspace().getRoot()));
		VespucciChangeProvider.getInstance().registerClassFileObserver(project,
				observer);
		observers.put(project, observer);
	}

	/**
	 * Unregisters the listener of the bytecode database for a given project.
	 * 
	 * @param project
	 */
	private void removeClassFileListener(IProject project) {
		if (!observers.containsKey(project))
			return;
		IClassFileObserver observer = observers.get(project);
		VespucciChangeProvider.getInstance().unregisterClassFileObserver(
				project, observer);
	}

	/**
	 * Fill the database with all class files of the current project
	 * 
	 * @param database
	 * @param project
	 */
	private void fillDatabase(Database database, IProject project) {

		List<IFile> classFiles = Util.getFilesOfProject(project,
				Selection.CLASS);
		
		StateLocationCopyService service = new StateLocationCopyService(Activator.getDefault().getStateLocation(), ResourcesPlugin.getWorkspace().getRoot());
		
		for (IFile file : classFiles) {
			try {
				IFileStore store = FileBuffers.getFileStoreAtLocation(file
						.getLocation());

				InputStream stream = store.openInputStream(EFS.NONE, null);
				database.addClassFile(stream);
				stream.close();
				service.makeShadowCopy(file);
				
			} catch (CoreException e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"unable to read resource: "
								+ file.getLocation().toString(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			} catch (IOException e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"unable close stream for resource: "
								+ file.getLocation().toString(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			} catch (Error e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"error while reading class file: "
								+ file.getLocation().toString() + " - " + e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}

	}
}

package de.tud.cs.st.vespucci.database.bytecode.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
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

		ClassFileObserver observer = new ClassFileObserver(database,
				new StateLocationCopyService(Activator.getDefault()
						.getStateLocation(), ResourcesPlugin.getWorkspace()
						.getRoot()));
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

		StateLocationCopyService service = new StateLocationCopyService(
				Activator.getDefault().getStateLocation(), ResourcesPlugin
						.getWorkspace().getRoot());

		addClassFiles(database, project, service);

		// if the build path does not mention the jar it is questionable whether
		// this in the scope
		// addJarFiles(database, project);

		addLibraryFiles(database, project, service);

	}

	private void addClassFiles(Database database, IProject project,
			StateLocationCopyService service) {

		List<IFile> classFiles = Util.getFilesOfProject(project,
				Selection.CLASS);

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
								+ file.getLocation().toString() + " - "
								+ e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}
	}

	private void addDirectJarFiles(Database database, IProject project,
			StateLocationCopyService service) {

		List<IFile> jarFiles = Util.getFilesOfProject(project,
				Selection.PROJECT_JAR);

		for (IFile file : jarFiles) {
			try {
				IFileStore store = FileBuffers.getFileStoreAtLocation(file
						.getLocation());

				InputStream stream = store.openInputStream(EFS.NONE, null);

				database.addArchive(stream);

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
								+ file.getLocation().toString() + " - "
								+ e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}
	}

	private void addLibraryFiles(Database database, IProject project,
			StateLocationCopyService service) {

		List<File> linkedLibraries = filterJreLibraries(Util.getLinkedLiberiesFiles(project));

		for (File file : linkedLibraries) {
			try {
				InputStream stream = new FileInputStream(file);

				database.addArchive(stream);

				stream.close();

			} catch (IOException e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"unable close stream for resource: "
								+ file.getAbsolutePath(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			} catch (Error e) {
				IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"error while reading class file: "
								+ file.getAbsolutePath() + " - "
								+ e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}
	}

	private List<File> filterJreLibraries(List<File> libraries) {

		List<File> result = new LinkedList<File>();
		// for now manually filter the libraries, scope of the analysis should
		// be determined later by a configuration
		for (File file : libraries) {
			if (!("resources.jar".equals(file.getName())
					|| "rt.jar".equals(file.getName())
					|| "jsse.jar".equals(file.getName())
					|| "jce.jar".equals(file.getName())
					|| "charsets.jar".equals(file.getName())
					|| "dnsns.jar".equals(file.getName())
					|| "localedata.jar".equals(file.getName()) || "sunjce_provider.jar"
						.equals(file.getName()))) {
				result.add(file);
			}

		}
		return result;
	}
}

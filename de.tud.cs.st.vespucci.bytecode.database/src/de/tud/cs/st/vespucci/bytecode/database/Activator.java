package de.tud.cs.st.vespucci.bytecode.database;

import java.io.InputStream;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;

import de.tud.cs.st.vespucci.bytecode.database.provider.ProjectDatabaseProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.bytecode.database"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ProjectDatabaseProvider databaseProvider;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	public void makeShadowCopy(IResource resource) {
		IPath shadowPath = getStateLocation().append(resource.getProject().getName()).append(resource.getParent().getProjectRelativePath());		
		IPath shadowFile = shadowPath.append(resource.getName());		
		try {
			
			FileBuffers.getFileStoreAtLocation(shadowPath).mkdir(EFS.NONE, null);
			IFileStore shadowStore = FileBuffers.getFileStoreAtLocation(shadowFile);
			IFileStore resourceStore = FileBuffers.getFileStoreAtLocation(resource.getLocation());
			resourceStore.copy(shadowStore, EFS.OVERWRITE, null);
			
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to make a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}
	
	public InputStream getShadowCopyStream(IResource resource) throws CoreException {
		IPath shadowPath = getStateLocation().append(resource.getProject().getName()).append(resource.getParent().getProjectRelativePath());		
		IPath shadowFile = shadowPath.append(resource.getName());		
		return FileBuffers.getFileStoreAtLocation(shadowFile).openInputStream(EFS.NONE, null);
	}

	
	public void deleteShadowCopy(IResource resource) throws CoreException {
		IPath shadowPath = getStateLocation().append(resource.getProject().getName()).append(resource.getParent().getProjectRelativePath());		
		IPath shadowFile = shadowPath.append(resource.getName());		
		FileBuffers.getFileStoreAtLocation(shadowFile).delete(EFS.NONE, null);
	}


	/**
	 * @return the databasePrvider
	 */
	public ProjectDatabaseProvider getDatabaseProvider() {
		return databaseProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		databaseProvider = new ProjectDatabaseProvider(
				ResourcesPlugin.getWorkspace());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		databaseProvider = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}

package de.tud.cs.st.vespucci.bytecode.listener;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import de.tud.cs.st.vespucci.bytecode.database.Activator;
import de.tud.cs.st.vespucci.change.observation.IClassFileObserver;

/**
 * Observer that tracks changes to class files and writes them to the database
 * 
 * TODO refactor error handling
 */
public class ClassFileObserver implements IClassFileObserver {

	private Database database;

	private IWorkspace workspace;

	public ClassFileObserver(Database database, IWorkspace workspace) {
		this.database = database;
		this.workspace = workspace;
	}

	@Override
	public void classFileAdded(IResource resource) {
		System.out.println("classFileAdded " + resource.getLocation().toString());
		
		Activator.getDefault().makeShadowCopy(resource);
		try {
			InputStream stream = Activator.getDefault().getShadowCopyStream(resource);
			database.addClassFile(stream);
			stream.close();
			
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (IOException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to close stream for resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

	}

	@Override
	public void classFileRemoved(IResource resource) {
		System.out.println("classFileRemoved " + resource.getLocation().toString());
		
		try {
			InputStream stream = Activator.getDefault().getShadowCopyStream(resource);
			database.removeClassFile(stream);
			stream.close();
			Activator.getDefault().deleteShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (IOException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to close shadow copy stream for resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	@Override
	public void classFileChanged(IResource resource) {
		System.out.println("classFileChanged " + resource.getLocation().toString());
		// remove old class file
		InputStream oldStream = null;
		try {
			oldStream = Activator.getDefault().getShadowCopyStream(
					resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read shadow copy for resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			return;
		}

		database.removeClassFile(oldStream);
		try {
			oldStream.close();
		} catch (IOException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to close stream for shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			return;
		}
		

		// make a shadow copy of the new file
		Activator.getDefault().makeShadowCopy(resource);
		
		try {
			InputStream stream = Activator.getDefault().getShadowCopyStream(resource);
			database.addClassFile(stream);
			stream.close();
			
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			return;
		} catch (IOException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to close stream for resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
			return;
		}
		
	}
}

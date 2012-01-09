package de.tud.cs.st.vespucci.bytecode.listener;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import de.tud.cs.st.vespucci.change.observation.IClassFileObserver;
import de.tud.cs.st.vespucci.database.bytecode.Activator;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;

/**
 * Observer that tracks changes to class files and writes them to the database
 * 
 * TODO refactor error handling
 */
public class ClassFileObserver implements IClassFileObserver {

	private Database database;
	
	private StateLocationCopyService copyService;
	
	public ClassFileObserver(Database database, StateLocationCopyService copyService) {
		this.database = database;
		this.copyService = copyService;
	}

	@Override
	public void classFileAdded(IResource resource) {
		System.out.println("classFileAdded " + resource.getLocation().toString());
		try {
			copyService.makeShadowCopy(resource);
			InputStream stream = copyService.getShadowCopyStream(resource);
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
			InputStream stream = copyService.getShadowCopyStream(resource);
			database.removeClassFile(stream);
			stream.close();
			copyService.deleteShadowCopy(resource);
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
			oldStream = copyService.getShadowCopyStream(
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
		
	
		try {
			// make a shadow copy of the new file
			copyService.makeShadowCopy(resource);
			InputStream stream = copyService.getShadowCopyStream(resource);
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

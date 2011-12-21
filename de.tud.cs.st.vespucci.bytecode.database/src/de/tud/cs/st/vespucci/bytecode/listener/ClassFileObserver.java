package de.tud.cs.st.vespucci.bytecode.listener;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import sae.bytecode.Database;
import de.tud.cs.st.vespucci.bytecode.database.Activator;
import de.tud.cs.st.vespucci.change.observation.IClassFileObserver;

public class ClassFileObserver implements IClassFileObserver {

	private Database database;

	private IWorkspace workspace;

	public ClassFileObserver(Database database, IWorkspace workspace) {
		this.database = database;
		this.workspace = workspace;
	}

	@Override
	public void classFileAdded(IResource resource) {
		IFile file = workspace.getRoot().getFile(resource.getLocation());
		try {
			InputStream contents = file.getContents();
			database.addClassFile(contents);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
	}

	@Override
	public void classFileRemoved(IResource resource) {
		IFile file = workspace.getRoot().getFile(resource.getLocation());
		try {
			InputStream contents = file.getContents();
			database.removeClassFile(contents);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"unable to read resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
	}

	@Override
	public void classFileChanged(IResource resource) {
		// TODO Auto-generated method stub

	}

}

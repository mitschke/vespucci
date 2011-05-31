package de.tud.cs.st.classfilechangetracker.changeListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.classfilechangetracker.Activator;
import de.tud.cs.st.classfilechangetracker.StartUp;
import de.tud.cs.st.classfilechangetracker.file.FileHandler;

public class ResourceChangeListener implements IResourceChangeListener {
	FileHandler fileHandler;

	public ResourceChangeListener() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// workspace.getRoot().getLocation().toFile()
		fileHandler = new FileHandler(workspace.getRoot().getLocation()
				.toFile());
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		// POST_CHANGE:
		// indicating an after-the-fact report of creations, deletions, and
		// modifications
		// to one or more resources expressed as a hierarchical resource delta
		// as returned by getDelta.
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			IResourceDelta delta = event.getDelta();
			try {
				fileHandler.writeResourceDeltas(delta);
			} catch (FileNotFoundException e) {
				IStatus is = new Status(Status.ERROR, StartUp.PLUGIN_ID,
						Activator.PLUGIN_ID
								+ ": Global output dir does not exist", e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
				StatusManager.getManager().handle(is, StatusManager.SHOW);
				return;

			}
		}

	}

}

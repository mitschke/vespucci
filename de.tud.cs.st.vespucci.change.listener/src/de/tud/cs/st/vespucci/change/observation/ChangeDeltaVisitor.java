package de.tud.cs.st.vespucci.change.observation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

public class ChangeDeltaVisitor implements IResourceDeltaVisitor {

	private VespucciChangeProvider changeProvider;

	public ChangeDeltaVisitor(VespucciChangeProvider changeProvider) {
		this.changeProvider = changeProvider;
	}

	private static boolean isSadFileExtension(String fileExtension) {
		return "sad".equalsIgnoreCase(fileExtension);
	}

	private static boolean isClassFileExtension(String fileExtension) {
		return "class".equalsIgnoreCase(fileExtension);
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {

		IResource resource = delta.getResource();

		
		IProject project = resource.getProject();

		/* 
		 * The root has no project but we wish to visit the children  
		 */
		if(project == null)
			return true;
		
		if (!changeProvider.hasObserver(project)) {
			return false;
		}


		int type = resource.getType();
		if (type == IResource.FOLDER || type == IResource.PROJECT ) {
			return true;
		}

		if (type != IResource.FILE) {
			return false;
		}

		
		if (isClassFileExtension(resource.getFileExtension())) {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				changeProvider.fireClassFileAdded(project, resource);
				break;
			case IResourceDelta.REMOVED:
				changeProvider.fireClassFileRemoved(project, resource);
				break;
			case IResourceDelta.CHANGED:
				changeProvider.fireClassFileChanged(project, resource);
				break;
			default:
				IStatus is = new Status(
						IStatus.ERROR,
						de.tud.cs.st.vespucci.change.listener.Activator.PLUGIN_ID,
						"unknown delta kind for class file: "
								+ Integer.toString(delta.getKind()));
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}
		if (isSadFileExtension(resource.getFileExtension())) {
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				changeProvider.fireArchitectureFileAdded(project, resource);
				break;
			case IResourceDelta.REMOVED:
				changeProvider.fireArchitectureFileRemoved(project, resource);
				break;
			case IResourceDelta.CHANGED:
				changeProvider.fireArchitectureFileChanged(project, resource);
				break;
			default:
				IStatus is = new Status(
						IStatus.ERROR,
						de.tud.cs.st.vespucci.change.listener.Activator.PLUGIN_ID,
						"unknown delta kind for class file: "
								+ Integer.toString(delta.getKind()));
				StatusManager.getManager().handle(is, StatusManager.LOG);

			}
		}

		// we never need to visit children of files
		return false;

	}
}

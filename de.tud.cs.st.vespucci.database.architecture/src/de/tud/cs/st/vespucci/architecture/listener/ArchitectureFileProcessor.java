package de.tud.cs.st.vespucci.architecture.listener;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import unisson.model.UnissonDatabase;
import de.tud.cs.st.vespucci.change.observation.IArchitectureObserver;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;
import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Files may be added and removed from the database via this class.
 * All Files that are currently in the database are marked as registered in this Processor.
 *  
 * @author Ralf Mitschke
 *
 */
public class ArchitectureFileProcessor implements IArchitectureObserver {

	private UnissonDatabase database;

	private StateLocationCopyService copyService;

	private String pluginId;

	private Set<String> registeredModels = new HashSet<String>();

	private String globalModel = null;

	public ArchitectureFileProcessor(UnissonDatabase database,
			StateLocationCopyService copyService, String pluginId) {
		this.database = database;
		this.copyService = copyService;
		this.pluginId = pluginId;
	}

	@Override
	public void architectureDiagramAdded(IResource resource) {
		if (!isRegisteredModel(resource))
			return;

		IArchitectureModel model = Util.adapt(resource,
				IArchitectureModel.class);
		try {
			copyService.makeShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		if (isGlobalModel(resource))
			database.addGlobalModel(model);
		else
			// we already checked that this is a registered model so no need to
			// check this twice
			database.addModel(model);
	}

	@Override
	public void architectureDiagramRemoved(IResource resource) {
		if (!isRegisteredModel(resource))
			return;

		IArchitectureModel model = Util.adapt(resource,
				IArchitectureModel.class);
		if (isGlobalModel(resource))
			database.removeGlobalModel(model);
		else
			// we already checked that this is a registered model so no need to
			// check this twice
			database.removeModel(model);
		try {
			copyService.deleteShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	@Override
	public void architectureDiagramChanged(IResource resource) {
		if (!isRegisteredModel(resource))
			return;

		IFile oldModelFile = copyService.getShadowCopyFile(resource);
		try {
			IArchitectureModel oldArchitectureModel = Util.adapt(oldModelFile,
					IArchitectureModel.class);
			IArchitectureModel newArchitectureModel = Util.adapt(resource,
					IArchitectureModel.class);
			if (isGlobalModel(resource))
				database.updateGlobalModel(oldArchitectureModel,
						newArchitectureModel);
			else
				// we already checked that this is a registered model so no need
				// to check this twice
				database.updateModel(oldArchitectureModel, newArchitectureModel);
			database.updateModel(oldArchitectureModel, newArchitectureModel);
			copyService.makeShadowCopy(resource);

		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

	}

	public void registerModel(IResource resource) {
		registeredModels.add(resource.getFullPath().toString());
	}

	public void unregisterModel(IResource resource) {
		registeredModels.remove(resource.getFullPath().toString());
	}

	public void setGlobalModel(IResource resource) {
		globalModel = resource.getFullPath().toString();
	}

	public boolean isRegisteredModel(IResource resource) {
		return isGlobalModel(resource) || isModel(resource);
	}

	public boolean isGlobalModel(IResource resource) {
		return resource.getFullPath().toString().equals(globalModel);
	}

	public boolean isModel(IResource resource) {
		return registeredModels.contains(resource.getFullPath().toString());
	}
}

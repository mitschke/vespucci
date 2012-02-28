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
import de.tud.cs.st.vespucci.database.architecture.ShadowArchitectureDecorator;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.utilities.StateLocationCopyService;
import de.tud.cs.st.vespucci.utilities.Util;

/**
 * Files may be added and removed from the database via this class. All Files
 * that are currently in the database are marked as registered in this
 * Processor.
 * 
 * @author Ralf Mitschke
 * 
 */
public class ArchitectureFileProcessor implements IArchitectureObserver {

	private UnissonDatabase database;

	private StateLocationCopyService copyService;

	private String pluginId;

	private Set<String> registeredModels = new HashSet<String>();

	private String ensembleRepository = null;

	public ArchitectureFileProcessor(UnissonDatabase database,
			StateLocationCopyService copyService, String pluginId) {
		this.database = database;
		this.copyService = copyService;
		this.pluginId = pluginId;
	}

	@Override
	public void architectureDiagramAdded(IResource resource) {
		// we always add models explicitly via
		// addConstraintModel/setEnsembleRepository
		// not via event from workspace
	}

	@Override
	public void architectureDiagramRemoved(IResource resource) {
		if (!isRegisteredModel(resource))
			return;
		if (isGlobalModel(resource))
			doDatabaseDelete(resource, true);
		if (isRegisteredModel(resource))
			doDatabaseDelete(resource, false);
		doShadowFileDelete(resource);
	}

	@Override
	public void architectureDiagramChanged(IResource resource) {
		if (!isRegisteredModel(resource))
			return;
		// we need to deregister the model during the update in order allow
		// events on the resource

		if (isGlobalModel(resource)) {
			String ensembleRepository = this.ensembleRepository;
			doDatabaseUpdate(resource, true);
			this.ensembleRepository = ensembleRepository;
		}

		if (isModel(resource)) {
			deRegisterModel(resource);
			doDatabaseUpdate(resource, false);
			registerModel(resource);
		}

		doShadowFileUpdate(resource);
	}

	public void addConstraintModel(IResource resource) {
		doDatabaseAddition(resource, false);
		doShadowFileAddition(resource);
		registeredModels.add(resource.getFullPath().toString());
	}

	public void deleteConstraintModel(IResource resource) {
		doDatabaseDelete(resource, false);
		doShadowFileDelete(resource);
		registeredModels.remove(resource.getFullPath().toString());
	}

	public void setEnsembleRepository(IResource resource) {
		doDatabaseAddition(resource, true);
		doShadowFileAddition(resource);
		ensembleRepository = resource.getFullPath().toString();
	}

	public boolean isRegisteredModel(IResource resource) {
		return isGlobalModel(resource) || isModel(resource);
	}

	public boolean isGlobalModel(IResource resource) {
		return resource.getFullPath().toString().equals(ensembleRepository);
	}

	public boolean isModel(IResource resource) {
		return registeredModels.contains(resource.getFullPath().toString());
	}

	/**
	 * perform an update on the database for the given resource. This method
	 * assumes the model was already added once to the database and a shadow
	 * copy is present.
	 * 
	 */
	private void doDatabaseUpdate(IResource resource, boolean asRepository) {

		IFile oldModelFile = copyService.getShadowCopyFile(resource);
		IArchitectureModel shadowArchitectureModel = Util.adapt(oldModelFile,
				IArchitectureModel.class);
		IArchitectureModel oldArchitectureModel = new ShadowArchitectureDecorator(
				shadowArchitectureModel, copyService.getStateLocation());
		IArchitectureModel newArchitectureModel = Util.adapt(resource,
				IArchitectureModel.class);
		if (asRepository) {
			database.updateGlobalModel(oldArchitectureModel,
					newArchitectureModel);
		} else {
			database.updateModel(oldArchitectureModel, newArchitectureModel);
		}
	}

	private void doShadowFileUpdate(IResource resource) {
		try {
			copyService.makeShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	/**
	 * perform an update on the database for the given resource. This method
	 * assumes the resource was never added to the database
	 */
	private void doDatabaseAddition(IResource resource, boolean asRepository) {
		IArchitectureModel model = Util.adapt(resource,
				IArchitectureModel.class);
		if (asRepository) {
			database.addGlobalModel(model);

		} else {
			database.addModel(model);
		}

	}

	private void doShadowFileAddition(IResource resource) {
		try {
			// always make a copy before adding to the database
			copyService.makeShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	/**
	 * perform an update on the database for the given resource. This method
	 * assumes the resource was never added to the database
	 */
	private void doDatabaseDelete(IResource resource, boolean asRepository) {
		IArchitectureModel model = Util.adapt(resource,
				IArchitectureModel.class);
		if (asRepository) {
			database.removeGlobalModel(model);

		} else {
			database.removeModel(model);
		}

	}

	private void doShadowFileDelete(IResource resource) {
		try {
			// always make a copy before adding to the database
			copyService.deleteShadowCopy(resource);
		} catch (CoreException e) {
			IStatus is = new Status(IStatus.ERROR, pluginId,
					"unable to create a shadow copy of resource: "
							+ resource.getLocation().toString(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

	private void registerModel(IResource resource) {
		registeredModels.add(resource.getFullPath().toString());
	}

	private void deRegisterModel(IResource resource) {
		registeredModels.remove(resource.getFullPath().toString());
	}

}

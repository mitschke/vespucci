package de.tud.cs.st.vespucci.utilities;

import java.io.InputStream;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class StateLocationCopyService {

	private IPath stateLocation;
	
	private IWorkspaceRoot workspaceRoot;


	public StateLocationCopyService(IPath stateLocation,
			IWorkspaceRoot workspaceRoot) {
		this.stateLocation = stateLocation;
		this.workspaceRoot = workspaceRoot;
	}

	/**
	 * @return the stateLocation
	 */
	public IPath getStateLocation() {
		return stateLocation;
	}

	public void makeShadowCopy(IResource resource) throws CoreException {
		IPath shadowPath = getStateLocation().append(
				resource.getProject().getName()).append(
				resource.getParent().getProjectRelativePath());
		
		IPath shadowFile = shadowPath.append(resource.getName());
		FileBuffers.getFileStoreAtLocation(shadowPath).mkdir(EFS.NONE, null);
		IFileStore shadowStore = FileBuffers.getFileStoreAtLocation(shadowFile);
		IFileStore resourceStore = FileBuffers.getFileStoreAtLocation(resource
				.getLocation());
		resourceStore.copy(shadowStore, EFS.OVERWRITE, null);
	}

	public InputStream getShadowCopyStream(IResource resource)
			throws CoreException {
		IPath shadowPath = getStateLocation().append(
				resource.getProject().getName()).append(
				resource.getParent().getProjectRelativePath());
		IPath shadowFile = shadowPath.append(resource.getName());
		return FileBuffers.getFileStoreAtLocation(shadowFile).openInputStream(
				EFS.NONE, null);
	}

	public void deleteShadowCopy(IResource resource) throws CoreException {
		IPath shadowPath = getStateLocation().append(
				resource.getProject().getName()).append(
				resource.getParent().getProjectRelativePath());
		IPath shadowFile = shadowPath.append(resource.getName());
		FileBuffers.getFileStoreAtLocation(shadowFile).delete(EFS.NONE, null);
	}
	
	public IPath getShadowCopyPath(IResource resource) {
		IPath shadowPath = getStateLocation().append(
				resource.getProject().getName()).append(
				resource.getParent().getProjectRelativePath());
		IPath shadowFile = shadowPath.append(resource.getName());
		return shadowFile;
	}
	
	public IFile getShadowCopyFile(IResource resource) {
		IPath shadowPath = getStateLocation().append(
				resource.getProject().getName()).append(
				resource.getParent().getProjectRelativePath());
		IPath shadowFile = shadowPath.append(resource.getName());
		return workspaceRoot.getFile(shadowFile);
	}


}

package de.tud.cs.st.vespucci.global_repository.util;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

public class Global_repositoryFileHandler {
	
	
	public Global_repositoryFileHandler(){
		super();
	}
	
	public static boolean checkRepoAvailable(String projectName){
		IPath path = new Path(IPath.SEPARATOR + projectName + IPath.SEPARATOR + projectName + ".vgr");
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IFile file = project.getFile(path);
		return file.exists();
	}
	
	public static void init(){
		System.out.println("CheckRepoAvailable: "+ checkRepoAvailable("BranchRepo+View"));
	}
}

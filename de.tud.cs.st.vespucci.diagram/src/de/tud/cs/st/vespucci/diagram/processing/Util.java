package de.tud.cs.st.vespucci.diagram.processing;

import java.util.LinkedList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

public class Util {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.diagram";
	
	public static enum Selection {CLASS, JAVA, PROJECT_JAR, JRE_JAR, PLUG_IN_DEP_JAR};
	
	/**
	 * @param file File to check
	 * @param extension Extension of the file type to check
	 * @return Return true only if the given file extension is equals to the parameter extension
	 */
	public static boolean isFileType(final IFile file, String extension) {
		int extlength = extension.length();
		final String ext = file.getName().substring((file.getName().length() - extlength), file.getName().length());
		return (ext.equals(extension));
	}
	
	public static LinkedList<IFile> getFilesOfProject(IProject project, Selection selection){
		LinkedList<IFile> ifiles = new LinkedList<IFile>();
		switch(selection){
			case CLASS: ifiles = getFilesList(project, ".class"); break;
			case JAVA: ifiles = getFilesList(project, ".java"); break;
			case PROJECT_JAR : ifiles = getFilesList(project, ".jar"); break;
			case JRE_JAR: throw new UnsupportedOperationException();
			case PLUG_IN_DEP_JAR: throw new UnsupportedOperationException();
		}
		
		return ifiles;
		
	}
		
	private static LinkedList<IFile> getFilesList(IProject project, String extension) {
			LinkedList<IFile> ifiles = new LinkedList<IFile>();
			try {
				ifiles = traversResource(project, extension);
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
						e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
			return ifiles;
	}

	private static LinkedList<IFile> traversResource(IResource resource,
			String extension) throws CoreException {
		LinkedList<IFile> iFiles = new LinkedList<IFile>();
		traversResourceElements(resource, extension, iFiles);
		return iFiles;
	}

	private static void traversResourceElements(IResource resource,
			String extension, LinkedList<IFile> iFiles) throws CoreException {
		if (resource instanceof IContainer) {
			IContainer container = (IContainer) resource;
			IResource[] iResources = container.members(true);
			for (IResource iResource : iResources) {
				traversResourceElements(iResource, extension, iFiles);
			}
		}
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			if (isFileType(file, extension)) {
				iFiles.add(file);
			}
		}
	}
	
}

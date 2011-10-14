package de.tud.cs.st.vespucci.diagram.processing;

import java.io.File;
import java.util.LinkedList;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.statushandlers.StatusManager;

public class Util {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.diagram";
	
	public static enum Selection {CLASS, JAVA, PROJECT_JAR};
	
	/**
	 * This function provides a generic adapter client for adapting an input object to a free selectable targetClass.
	 * The type of the returning object will be the same as the passed targetClass (<b>Typesafe!</b>). 
	 * See the linked article for further notice, this abstract generic adapter client replaces 
	 * the specific <code>getImageProvider</code> in this article.  
	 * 
	 * @param adaptable Input object, a fitting adapter is required for correct converting. Otherwise <code>null</code> will be returned.
	 * @param targetClass The desired class of the returned (adapted) object.
	 * @return Adapted input Object with type <code>targetClass</code>.
	 * A fitting adapter is required for correct converting. Otherwise <code>null</code> will be returned.
	 * @see <a href="http://www.eclipse.org/articles/article.php?file=Article-Adapters/index.html">Eclipse Corner Article: Adapters</a>
	 */
	@SuppressWarnings("unchecked")
	public static <A> A getAdapted(Object adaptable, Class<A> targetClass) {
	
		A target = null;
	
		// Check if input object is of same type as targetClass
		if (targetClass.isInstance(adaptable)) {
			return (A) adaptable;
		}
	
		// Check if input object provides an adapter for targetClass itself
		if (adaptable instanceof IAdaptable) {
			target = (A) ((IAdaptable) adaptable).getAdapter(targetClass);
		}
	
		// Ask platform adapter manager for a correct adapter for targetClass
		if (target == null) {
			IAdapterManager manager = Platform.getAdapterManager();
			target = (A) manager.getAdapter(adaptable, targetClass);
		}
	
		return target;
	}
	
	/**
	 * @param file File to check
	 * @param extension Extension of the file type to check
	 * @return Return true only if the given file extension is equals to the parameter extension
	 */
	public static boolean isFileType(final IFile file, String extension) {
		int extlength = extension.length();
		final String ext = file.getName().substring(
				(file.getName().length() - extlength), file.getName().length());
		return (ext.equals(extension));
	}
	
	public static LinkedList<IFile> getFilesOfProject(IProject project, Selection selection){
		LinkedList<IFile> ifiles = new LinkedList<IFile>();
		switch(selection){
			case CLASS: ifiles = getFilesList(project, ".class"); break;
			case JAVA: ifiles = getFilesList(project, ".java"); break;
			case PROJECT_JAR : ifiles = getFilesList(project, ".jar"); break;
		}
		return ifiles;
	}
	
	public static LinkedList<File> getLinkedLiberiesFiles(IProject project) {
		LinkedList<File> elements = new LinkedList<File>();

		IJavaProject javaProject = JavaCore.create(project);
		try {
			for (IClasspathEntry classpathentry : javaProject
					.getResolvedClasspath(false)) {
				if (classpathentry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
					elements.add(classpathentry.getPath().toFile());
				}
			}
		} catch (JavaModelException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
					e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return elements;
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

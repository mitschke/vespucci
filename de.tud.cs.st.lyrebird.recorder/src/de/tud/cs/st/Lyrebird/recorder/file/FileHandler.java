package de.tud.cs.st.Lyrebird.recorder.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Date;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.Lyrebird.recorder.Activator;
import de.tud.cs.st.Lyrebird.recorder.StartUp;
import de.tud.cs.st.Lyrebird.recorder.nature.LyrebirdNature;
import de.tud.cs.st.Lyrebird.recorder.preferences.PreferenceConstants;


public class FileHandler {
	private boolean saveGlobal = false;
	private String globalPath = "";
	private String projectPath = "";
	private File workspaceLocation;
	long date;

	public FileHandler(File location) {
		this.workspaceLocation = location;

	}

	private void configOutput() throws FileNotFoundException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		saveGlobal = !store.getBoolean(PreferenceConstants.P_SAVE_PAIR_PROJECT);
		globalPath = store.getString(PreferenceConstants.P_ABSOLUTE_PATH);
		projectPath = store
				.getString(PreferenceConstants.P_PROJECT_RELATIV_PATH);

		if (saveGlobal && !new File(globalPath).exists()) {
			throw new FileNotFoundException(Activator.PLUGIN_ID
					+ ": Global output dir does not exist");

		}

	}

	public void writeResourceDeltas(IResourceDelta delta) throws FileNotFoundException{
		
			configOutput();
		

		date = new Date().getTime();
		IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {

			@Override
			public boolean visit(IResourceDelta delta) throws CoreException {

				IResource resource = delta.getResource();

				if (resource != null
						&& resource.getProject() != null
						&& resource.getProject().isNatureEnabled(
								LyrebirdNature.NATURE_ID)
						&& resource.getType() == IResource.FILE
						&& "class"
								.equalsIgnoreCase(resource.getFileExtension())) {
					try {
						if (resource.getFullPath().toOSString()
								.contains(projectPath))
							return false;

						if (saveGlobal) {
							File outputfoler = new File(globalPath, resource
									.getFullPath().toFile().getParent());
							if (!outputfoler.exists())
								outputfoler.mkdirs();

							File des = new File(outputfoler, Path.SEPARATOR
									+ date + "_" + getKind(delta.getKind())
									+ "_" + resource.getName());
							if (!getSourceFile(resource).exists()) {
								des.createNewFile();
							} else {
								copyFile(getSourceFile(resource), des);
							}
						} else {
							File projecFile = new File(workspaceLocation,
									resource.getProject().getFullPath()
											.toOSString());
							File projectOutPutFile = new File(projecFile,
									projectPath);
							if (!projectOutPutFile.exists())
								projectOutPutFile.mkdirs();
							File outputFoler = new File(projectOutPutFile,
									resource.getProjectRelativePath().toFile()
											.getParent());

							if (!outputFoler.exists())
								outputFoler.mkdirs();

							File des = new File(outputFoler, Path.SEPARATOR
									+ date + "_" + getKind(delta.getKind())
									+ "_" + resource.getName());
							if (!getSourceFile(resource).exists()) {
								des.createNewFile();
							} else {
								copyFile(getSourceFile(resource), des);
							}
						}

					} catch (IOException e) {
						IStatus is = new Status(Status.ERROR,
								StartUp.PLUGIN_ID, "IOException", e);
						StatusManager.getManager()
								.handle(is, StatusManager.LOG);
						StatusManager.getManager()
						.handle(is, StatusManager.SHOW);
						return false;
					}
				}
				return true;
			}
		};
		try {
			delta.accept(visitor);
		} catch (CoreException e) {
			IStatus is = new Status(Status.ERROR, StartUp.PLUGIN_ID,
					"CoreException", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
	}

	private File getSourceFile(IResource resource) {
		return new File(workspaceLocation, resource.getFullPath().toOSString());
	}

	private String getKind(int kind) {
		if (kind == IResourceDelta.ADDED)
			return "ADDED";
		if (kind == IResourceDelta.REMOVED)
			return "REMOVED";
		if (kind == IResourceDelta.CHANGED)
			return "CHANGED";
		return "UNKNOWN";
	}

	// from https://gist.github.com/889747
	public static void copyFile(File sourceFile, File destFile)
			throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileInputStream fIn = null;
		FileOutputStream fOut = null;
		FileChannel source = null;
		FileChannel destination = null;
		try {
			fIn = new FileInputStream(sourceFile);
			source = fIn.getChannel();
			fOut = new FileOutputStream(destFile);
			destination = fOut.getChannel();
			long transfered = 0;
			long bytes = source.size();
			while (transfered < bytes) {
				transfered += destination
						.transferFrom(source, 0, source.size());
				destination.position(transfered);
			}
		} finally {
			if (source != null) {
				source.close();
			} else if (fIn != null) {
				fIn.close();
			}
			if (destination != null) {
				destination.close();
			} else if (fOut != null) {
				fOut.close();
			}
		}
	}
}

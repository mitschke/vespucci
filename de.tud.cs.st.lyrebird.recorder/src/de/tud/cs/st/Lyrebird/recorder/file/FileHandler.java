/* License (BSD Style License):
 * Copyright (c) 2011
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Technology Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
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

import de.tud.cs.st.Lyrebird.recorder.lyrebirdnature.*;
import de.tud.cs.st.Lyrebird.recorder.preferences.PreferenceConstants;

/**
 * These class save relevant bytecode changes.
 * A bytecode change is relevant if the project in which the bytecode change occurs has the lyrebird.nature
 * 
 * Saving Process:
 * goes through a IResoucreDelta and copies all classfiels to
 * projectpath\P_PROJECT_RELATIV_PATH\packageName\subpackagename\...\TIME_EVENT_CLASSNAME.class
 * or
 * P_ABSOLUTE_PATH\packageName\subpackagename\...\TIME_EVENT_CLASSNAME.class
 *  
 * 
 * TIME = point in time [in milliseconds (since January 1, 1970)] when writeResourceDeltas was called
 * Event = Changed/Added/Removed 
 * @see PreferenceConstants
 * @author Malte V
 */
public class FileHandler {
	private boolean saveGlobal = false;
	private String globalPath = "";
	private String projectPath = "";
	private File workspaceLocation;
	long date; // TODO either make it private or document why it has package level visibility

	/**
	 * Constructor
	 * @param location set workspace location
	 */
	public FileHandler(File location) {
		this.workspaceLocation = location;

	}

	/**
	 * Set the output behavior as it is configured in the properties page  
	 * @throws FileNotFoundException
	 */
	private void configOutput() throws FileNotFoundException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		saveGlobal = !store.getBoolean(PreferenceConstants.P_SAVE_PER_PROJECT);
		globalPath = store.getString(PreferenceConstants.P_ABSOLUTE_PATH);
		projectPath = store
				.getString(PreferenceConstants.P_PROJECT_RELATIV_PATH);

		if (saveGlobal && !new File(globalPath).exists()) {
			throw new FileNotFoundException(Activator.PLUGIN_ID
					+ ": Global output dir does not exist");

		}

	}
	/**
	 * Visits all IResouces in delta (IResourceDelta) and saves the corresponding file if it is relevant.<br>
	 * A file is relevant if:
	 * 	- it's a class file
	 * 	- it's location is in a project with the lyrebird natur
	 * 
	 * @param delta
	 * @throws FileNotFoundException // TODO when and why?
	 */
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
						
						

						if (saveGlobal) {
							//check that we dont get a change in Lyrebirds outputfolder
							if(resource.getRawLocation().matchingFirstSegments(new Path(globalPath)) == new Path(globalPath).segmentCount())
								return false;
							
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
							//check that we dont get a change in Lyrebirds outputfolder
							if (resource.getProjectRelativePath().segment(0).equals(projectPath))
								return false;

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
								Activator.PLUGIN_ID, "IOException", e);
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
			IStatus is = new Status(Status.ERROR, Activator.PLUGIN_ID,
					"CoreException", e);
			StatusManager.getManager().handle(is, StatusManager.LOG);

		}
	}

	/**
	 * returns the file for a IResoucre
	 */
	private File getSourceFile(IResource resource) {
		return resource.getRawLocation().toFile();
		//return new File(workspaceLocation, resource.getFullPath().toOSString());
	}

	/**
	 * Determine the event type
	 * TODO Do explain why/where I need to have a string based representation of the kind...
	 */
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
	// TODO use Jakarta Commons IO..! This implementation needs to be improved.
	private static void copyFile(File sourceFile, File destFile)
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

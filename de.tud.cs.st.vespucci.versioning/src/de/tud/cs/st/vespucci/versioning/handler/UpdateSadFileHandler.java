/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */

package de.tud.cs.st.vespucci.versioning.handler;

import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import de.tud.cs.st.vespucci.versioning.VespucciVersionChain;
import de.tud.cs.st.vespucci.versioning.versions.VespucciVersionTemplate;

/**
 * Handler for sad file Upgrade action.
 * 
 * @author Dominic Scheurer
 */
public class UpdateSadFileHandler extends AbstractHandler {
	/**
	 * Stores the state of the command (whether enabled or not)
	 */
	private boolean enabled = true;
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void setEnabled(Object o) {
		if (!(o instanceof EvaluationContext)) {
			enabled = false;
			return;
		}
			
		EvaluationContext evaluationContext = (EvaluationContext) o;
		if (evaluationContext.getDefaultVariable() instanceof List) {
			for (Object element : (List)evaluationContext.getDefaultVariable()) {
				if (element instanceof IFile) {
					IFile file = (IFile)element;
					if (!file.getFullPath().getFileExtension().equalsIgnoreCase("sad") ||
						VespucciVersionChain.getInstance().getVersionOfFile(file).isCurrentVersion()) {
						enabled = false;
						return;
					}
				} else {
					enabled = false;
					return;
				}
			}
		} else {
			enabled = false;
			return;
		}
		
		enabled = true;
	}
	
	/**
	 * Convenience method which converts a single file.
	 * 
	 * @param file File to transform.
	 * @return null
	 */
	public Object execute(IFile file) {
		return execute(new IFile[] { file });
	}
	
	/**
	 * Convenience method which converts an array of files.
	 * 
	 * @param files Files to transform.
	 * @return null
	 */
	public Object execute(IFile[] files) {
		IStructuredSelection structuredSelection = new StructuredSelection(files);
		return execute(structuredSelection);
	}
	
	/**
	 * Custom execute method with a IStructuredSelection argument which
	 * is more simple to call programmatically. 
	 * 
	 * @param selection The selection on which to execute the transformation.
	 * @return null
	 */
	public Object execute(IStructuredSelection selection) {
		for (Object o : selection.toArray()) {
			if (o instanceof IFile) {
				final IFile file = (IFile) o;
				
				VespucciVersionChain versionChain = VespucciVersionChain.getInstance();
				VespucciVersionTemplate currentVersion = versionChain.getVersionOfFile(file);
				
				while (!currentVersion.isCurrentVersion()) {
					final VespucciVersionTemplate nextVersion = versionChain.getNextVersion(currentVersion);
					
					if (nextVersion == null) {
						break;
					}
					
					final IPath newPath = getUniquePathForVersion(file, currentVersion);
					
					final URI outputUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					
					Job job = new Job(
							new StringBuilder("Updating Vespucci file ")
								.append(file.toString())
								.append(" from version ")
								.append(currentVersion.getIdentifier())
								.append(" to ")
								.append(nextVersion.getIdentifier()).toString()
					) {

						@Override
						protected IStatus run(IProgressMonitor monitor) {
							return nextVersion.upgradeFileToThisVersion(file, newPath, outputUri, monitor);
						}
						
					};
					
					job.setUser(true);
					job.schedule();
					
					currentVersion = nextVersion;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		return execute(selection);
	}
	
	/**
	 * @param file The original file.
	 * @param version The version to take the identifier from.
	 * @return A path to a non-existing file which extends the original file path
	 * 		   and contains the version identifier and, if necessary, the current timestamp.
	 */
	private static IPath getUniquePathForVersion(IFile file, VespucciVersionTemplate version) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		
		IPath absOrigPath = file.getRawLocation();
		
		IPath newPath = absOrigPath;
		if (absOrigPath.segment(absOrigPath.segmentCount() - 1).indexOf("." + version.getIdentifier() + ".") < 0) {
			newPath = insertSubExtension(absOrigPath, version.getIdentifier());
		}
		
		newPath = removeTimestamp(newPath);
		
		while (workspaceRoot.findFilesForLocationURI(URIUtil.toURI(newPath))[0].exists()) {
			newPath = insertSubExtension(newPath, new Long(new Date().getTime()).toString());
		}
		
		newPath = newPath.makeRelativeTo(workspaceRoot.getRawLocation());
		newPath = newPath.makeAbsolute();
		
		return newPath;
	}
	
	/**
	 * Inserts a file extension before the actual (last) extension segment.
	 * 
	 * @param originalPath The path in which to insert the sub extension.
	 * @param subExtension The sub extension to insert into the path.
	 * @return The new path with subExtension inserted before the last segment.
	 */
	private static IPath insertSubExtension(IPath originalPath, String subExtension) {
		return originalPath.removeFileExtension()
			.addFileExtension(subExtension)
			.addFileExtension(originalPath.getFileExtension());
	}
	
	/**
	 * Checks if the second-last file extension could be timestamp; if so,
	 * it is removed.
	 * 
	 * @param originalPath The path to remove the timestamp from.
	 * @return The original path without timestamp extension.
	 */
	private static IPath removeTimestamp(IPath originalPath) {
		String possibleTimestamp = originalPath.removeFileExtension().getFileExtension();
		
		if (possibleTimestamp.isEmpty()) {
			return originalPath;
		} else if (tryParse(possibleTimestamp) != null) {
			return originalPath.removeFileExtension().removeFileExtension().addFileExtension("sad");
		} else {
			return originalPath;
		}
	}
	
	/**
	 * @param text Possible Long value
	 * @return The parsed string or null if it cannot be parsed.
	 */
	private static Long tryParse(String text) {
		try {
			return Long.parseLong(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}	
}

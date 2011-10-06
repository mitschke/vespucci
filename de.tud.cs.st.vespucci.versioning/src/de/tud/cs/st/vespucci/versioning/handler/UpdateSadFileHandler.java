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

import java.io.File;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tud.cs.st.vespucci.versioning.VespucciVersionChain;
import de.tud.cs.st.vespucci.versioning.versions.VespucciVersionTemplate;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

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
	public void setEnabled(final Object o) {
		if (!(o instanceof EvaluationContext)) {
			enabled = false;
			return;
		}

		final EvaluationContext evaluationContext = (EvaluationContext) o;
		if (evaluationContext.getDefaultVariable() instanceof List) {
			for (final Object element : (List) evaluationContext.getDefaultVariable()) {
				if (element instanceof IFile) {
					final IFile file = (IFile) element;
					if (!isSadFile(file) || isNewestVersion(file)) {
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

	private static boolean isNewestVersion(final IFile file) {
		return VespucciVersionChain.getInstance().getVersionOfFile(file).isNewestVersion();
	}

	private static boolean isSadFile(final IFile file) {
		final URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		List<EObject> fileModelContents;
		//ToDo: Only QuickFix
		try {
			fileModelContents = new ResourceSetImpl().getResource(fileURI, true).getContents();
		} catch (Exception e) {
			return false;
		}
		
		return file.getFullPath().getFileExtension().equalsIgnoreCase("sad") &&
			fileModelContents != null &&
			fileModelContents.size() == 2 &&
			((fileModelContents.get(0) instanceof ShapesDiagramImpl &&
			  fileModelContents.get(1) instanceof DiagramImpl) ||
			 (fileModelContents.get(0) instanceof DiagramImpl &&
			  fileModelContents.get(1) instanceof ShapesDiagramImpl));
	}

	/**
	 * Convenience method which converts a single file.
	 * 
	 * @param file
	 *            File to transform.
	 * @return null
	 */
	public Object execute(final IFile file) {
		return execute(new IFile[] { file });
	}

	/**
	 * Convenience method which update an array of files.
	 * 
	 * @param files
	 *            Files to transform.
	 * @return null
	 */
	public Object execute(final IFile[] files) {
		final IStructuredSelection structuredSelection = new StructuredSelection(files);
		return execute(structuredSelection);
	}

	/**
	 * Custom execute method with a IStructuredSelection argument which is more simple to call programmatically.
	 * 
	 * @param selection
	 *            The selection on which to execute the transformation.
	 * @return null Has to be null.
	 */
	public Object execute(final IStructuredSelection selection) {
		for (final Object o : selection.toArray()) {
			if (o instanceof IFile) {
				final IFile file = (IFile) o;

				final VespucciVersionChain versionChain = VespucciVersionChain.getInstance();
				VespucciVersionTemplate currentVersion = versionChain.getVersionOfFile(file);

				while (!currentVersion.isNewestVersion()) {
					final VespucciVersionTemplate nextVersion = versionChain.getNextVersion(currentVersion);

					if (nextVersion == null) {
						return null;
					}

					final File backupFile = getUniqueFilePointerForVersion(file, currentVersion);

					final URI outputUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

					final Job job = new Job(String.format("Updating Vespucci file %s from version %s to %s.", file,
							currentVersion.getIdentifier(), nextVersion.getIdentifier())) {

						@Override
						protected IStatus run(final IProgressMonitor monitor) {
							return nextVersion.updateFromDirectPredecessorVersion(file, backupFile, outputUri, monitor);
						}

					};

					// Jobs must be blocking in order to prevent conflicts during usage
					// of the static methods in TransformationHelperLibrary
					job.setRule(ResourcesPlugin.getWorkspace().getRoot());
					job.setPriority(Job.SHORT);
					job.setUser(true);
					job.schedule();

					currentVersion = nextVersion;
				}
			}
		}

		return null;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		return execute(selection);
	}

	/**
	 * @param file
	 *            The original file.
	 * @param version
	 *            The version to take the identifier from.
	 * @return A file handle to a non-existing file which extends the original file path and
	 * 		   contains the version identifier and, if necessary, the current time stamp.
	 */
	private static java.io.File getUniqueFilePointerForVersion(final IFile file, final VespucciVersionTemplate version) {
		final IPath absOrigPath = file.getRawLocation();		

		IPath backupPath = absOrigPath;
		if (fileNameHasNoVersionID(absOrigPath, version)) {
			backupPath = insertSubExtension(absOrigPath, version.getIdentifier());
		}

		// prevents stacking of time stamps
		backupPath = removeTimestamp(backupPath);

		while (backupPath.toFile().exists()) {
			backupPath = removeTimestamp(backupPath);
			backupPath = insertSubExtension(backupPath, new Long(new Date().getTime()).toString());
		}

		return backupPath.toFile();
	}

	private static boolean fileNameHasNoVersionID(final IPath path, final VespucciVersionTemplate version) {
		return !path.lastSegment().contains("." + version.getIdentifier() + ".");
	}

	/**
	 * Inserts a file extension before the actual (last) extension segment.
	 * 
	 * @param originalPath
	 *            The path in which to insert the sub extension.
	 * @param subExtension
	 *            The sub extension to insert into the path.
	 * @return The new path with subExtension inserted before the last segment.
	 */
	private static IPath insertSubExtension(final IPath originalPath, final String subExtension) {
		return originalPath.removeFileExtension().addFileExtension(subExtension)
				.addFileExtension(originalPath.getFileExtension());
	}

	/**
	 * Checks if the second-last file extension could be a time stamp; if so, it is removed.
	 * 
	 * @param originalPath
	 *            The path to remove the time stamp from.
	 * @return The original path without time stamp extension.
	 */
	private static IPath removeTimestamp(final IPath originalPath) {
		final String possibleTimestamp = originalPath.removeFileExtension().getFileExtension();

		if (isTimestamp(possibleTimestamp)) {
			return originalPath.removeFileExtension().removeFileExtension().addFileExtension("sad");
		}
		return originalPath;
	}

	private static boolean isTimestamp(final String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}
}

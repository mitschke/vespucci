/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt 
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

package de.tud.cs.st.vespucci.versioning;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.errors.VespucciTransformationFailedException;
import de.tud.cs.st.vespucci.versioning.versions.VespucciVersionTemplate;
import de.tud.cs.st.vespucci.vespucci_model.impl.ShapesDiagramImpl;

/**
 * Template class which supplies various methods for Vespucci file transformations.
 * 
 * @author Dominic Scheurer
 */
public abstract class VespucciTransformationHelper {
	/**
	 * @return The progress monitor stored in the specific converter class.
	 */
	protected abstract IProgressMonitor getProgressMonitor();

	/**
	 * @return The destination version to convert onto.
	 */
	protected abstract VespucciVersionTemplate getVespucciVersion();

	/**
	 * @param file
	 *            The file from which to retrieve the URI
	 * @return The platform resource URI to the given file.
	 */
	protected static URI getUriFromFile(final IFile file) {
		return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	}

	/**
	 * @param fileURI
	 *            URI to the file which contents are to be loaded.
	 * @return Returns true only if resource for the given file URI does exists.
	 */
	protected static boolean resourceExists(final URI fileURI) {
		final ResourceSetImpl rs = new ResourceSetImpl();
		final EObject result = rs.getEObject(fileURI.appendFragment("/"), true);

		return result != null;
	}

	/**
	 * @return The shell of the active workbench window.
	 */
	protected static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	/**
	 * @param file
	 *            The file which is being transformed.
	 * @return The message displayed at the beginning of the transformation.
	 */
	protected String getTransformationMessage(final IFile file) {
		return String.format("Converting Vespucci diagram \"%s\" from version %s to version %s.", file, getVespucciVersion()
				.getPredecessor().getNamespace(), getVespucciVersion().getNamespace());
	}
	
	/**
	 * Saves transformation result contents to a given URI.
	 * 
	 * @param modelTransformationResult
	 *            Transformation result contents for the model part.
	 * @param diagramTransformationResult
	 *            Transformation result contents for the notation part.
	 * @param saveToUri
	 *            Destination URI.
	 */
	protected void saveResults(
			final ModelExtent modelTransformationResult,
			final ModelExtent diagramTransformationResult,
			final URI saveToUri) {
		final List<EObject> outObjectsModel = modelTransformationResult.getContents();
		final List<EObject> outObjectsDiagram = diagramTransformationResult.getContents();

		final Resource outputResource = new ResourceSetImpl().createResource(saveToUri);
		outputResource.getContents().addAll(outObjectsModel);
		outputResource.getContents().addAll(outObjectsDiagram);

		try {
			outputResource.save(Collections.EMPTY_MAP);
		} catch (final IOException e) {
			handleError(e);
		}
	}

	/**
	 * Renames the given file to the new path.
	 * 
	 * @param fileToRename
	 *            The file to rename.
	 * @param newPath
	 *            The new path to the file.
	 * @param monitor
	 *            The progress monitor to use to illustrate the renaming progress.
	 */
	protected void renameFile(final IFile fileToRename, final IPath newPath, final IProgressMonitor monitor) {
		try {
			// copy-delete avoids opening of old file
			final SubProgressMonitor copyDeleteMonitor = new SubProgressMonitor(monitor, 2);
			fileToRename.copy(newPath, true, copyDeleteMonitor);
			fileToRename.delete(true, copyDeleteMonitor);
		} catch (final CoreException coreException) {
			handleError(coreException);
		}
	}

	/**
	 * Error handling method; shows a message box including the error message and throws a new Vespucci runtime
	 * exception - thus, methods which utilize this error handling routing may return null after calling it.
	 * 
	 * @param ex
	 *            The source exception.
	 */
	private void handleError(final Exception ex) {
		getProgressMonitor().done();

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog.openError(getShell(), getDefaultErrorString(), MessageFormat.format("{0}: {1}", ex.getClass()
						.getSimpleName(), ex.getMessage() == null ? "no message" : ex.getMessage()));
			}
		});

		throw new VespucciTransformationFailedException(getDefaultErrorString(), ex);
	}

	/**
	 * @return The default transformation error string for the specific Vespucci version.
	 */
	protected String getDefaultErrorString() {
		return String.format("Conversion of Vespucci diagram to version %s failed", getVespucciVersion().getNamespace());
	}
	
	/**
	 * @param inputObjects The list of model contents of the file to transform.
	 * @return
	 * 	Input objects for the transformation: Vespucci model part first, diagram part at the end.
	 */
	protected VespucciTransformationInput[] getModelTransformationInputs(EList<EObject> inputObjects) {
		if (!(inputObjects.get(0) instanceof ShapesDiagramImpl)) {
			Collections.swap(inputObjects, 0, 1);
		}
		
		return new VespucciTransformationInput[] {
				new VespucciTransformationInput(
						getVespucciVersion().getModelQvtoUri(),
						inputObjects.get(0)),
				new VespucciTransformationInput(
						getVespucciVersion().getDiagramQvtoUri(),
						inputObjects.get(1))
		};
	}

	/**
	 * Simple struct-like class to encapsulate transformation input
	 * information.
	 * 
	 * @author Dominic Scheurer
	 */
	protected static class VespucciTransformationInput {
		/** The URI to the QVTO transformation code */
		private URI qvtoUri = null;
		/** The model content to transform */
		private EObject modelContent = null;
		
		/**
		 * @param qvtoUri The URI to the QVTO transformation code.
		 * @param modelContent The model content to transform.
		 */
		protected VespucciTransformationInput(
				URI qvtoUri, EObject modelContent) {
			this.qvtoUri = qvtoUri;
			this.modelContent = modelContent;
		}
		
		/**
		 * @return The URI to the QVTO transformation code.
		 */
		public URI getQvtoUri() {
			return qvtoUri;
		}
		
		/**
		 * @return The model content to transform.
		 */
		public List<EObject> getModelContentAsList() {
			final List<EObject> result = new ArrayList<EObject>();
			result.add(modelContent);
			return result;
		}
	}
}

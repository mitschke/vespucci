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
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelExtentContents;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.emf.util.ModelContent;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.In;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.Out;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.QvtInterpretedTransformation;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.TransformationUtil;
import org.eclipse.m2m.internal.qvt.oml.trace.Trace;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.errors.VespucciTransformationFailedException;
import de.tud.cs.st.vespucci.versioning.versions.VespucciVersionTemplate;

/**
 * Template class which supplies various methods for Vespucci file
 * transformations.
 * 
 * @author Dominic Scheurer
 */
@SuppressWarnings("restriction")
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
	 * @param file The file from which to retrieve the URI
	 * @return The platform resource URI to the given file.
	 */
	protected static URI getUriFromFile(IFile file) {
		return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	}
	
	/**
	 * Returns the EObject contents of the file defined by the given URI.
	 * 
	 * @param fileURI URI to the file which contents are to be loaded
	 * @return The EObject contents of the file defined by fileURI
	 */
	protected static boolean resourceIsEmpty(URI fileURI) {
		ResourceSetImpl rs = new ResourceSetImpl();
		EObject result = rs.getEObject(fileURI.appendFragment("/"), true);
		
		if (result == null) {
			// No source given => Show error message
			String title = getMessageFromBundle("VespucciTransformationNoFileTitle");
			String message = getMessageFromBundle("VespucciTransformationNoFileMessage");
			MessageDialog.openError(getShell(), title,
					NLS.bind(message, fileURI.toString()));

			return true;
		}
		
		return false;
	}
	
	/**
	 * @param key Key for which the value should be received.
	 * @return The value specified for the given key in the messages resource bundle.
	 */
	private static String getMessageFromBundle(String key) {
		ResourceBundle messagesBundle = ResourceBundle.getBundle("messages");
		return messagesBundle.getString(key);
	}
	
	/**
	 * @return The shell of the active workbench window.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
	
	/**
	 * @param file The file which is being transformed.
	 * @return The message displayed at the beginning of the transformation.
	 */
	protected String getTransformationMessage(IFile file) {
		StringBuilder sb = new StringBuilder("Converting Vespucci diagram \"")
			.append(file.toString())
			.append("\" from version ")
			.append(getVespucciVersion().getPredecessor().getNamespace())
			.append(" to version ")
			.append(getVespucciVersion().getNamespace());
		
		return sb.toString();
	}
	
	/**
	 * @param fileURI File which contains the contents to extract.
	 * @return The EObject contents of the file, whereas the model part is at the first position in the list.
	 */
	protected static List<EObject> getOrderedResourceContents(URI fileURI) {
		Resource inResource = new ResourceSetImpl().getResource(fileURI, true);

		List<EObject> inObjects = inResource.getContents();
		
		if (!inObjects.get(0).getClass().toString().contains("ShapesDiagramImpl")) {
			Collections.swap(inObjects, 0, 1);
		}
		
		return inObjects;
	}
	
	/**
	 * Executes a QVTO transformation, returns outputs.
	 * 
	 * @param context Context in which to execute the transformation.
	 * @param modelContent Transformation input.
	 * @param qvtoUri URI to the QVTO file to execute.
	 * @return The transformed input data.
	 */
	protected Out executeQvtoTransformation(IContext context, ModelContent modelContent, URI qvtoUri) {
		QvtInterpretedTransformation modelQvtoTransformation = createQvtoTransformation(qvtoUri);
		In modelTransformationInput = new TransformationRunner.In(
				new ModelContent[] { modelContent },
				context
			);
		try {
			return modelQvtoTransformation.run(modelTransformationInput);
		} catch (MdaException qvtoTransformationException) {
			handleError(qvtoTransformationException);
			return null;
		}
	}
	
	/**
	 * @param qvtoUri URI to the transformation module.
	 * @return The transformation object for the given module.
	 */
	private QvtInterpretedTransformation createQvtoTransformation(URI qvtoUri) {
		try {
			return new QvtInterpretedTransformation(
					TransformationUtil.getQvtModule(qvtoUri));
		} catch (MdaException qvtoTransformationException) {
			handleError(qvtoTransformationException);
			return null;
		}		
	}
		
	/**
	 * Checks whether a transformation worked, looking at the output.
	 * 
	 * @param transformationOutput Output of a transformation.
	 * @return True if transformation produced no visible errors, else false.
	 */
	protected static boolean outputIsCorrect(Out transformationOutput) {
		List<ModelExtentContents> outputExtends =
			transformationOutput.getExtents();
		Trace outputTrace = transformationOutput.getTrace();
		
		return outputExtends.size() > 0 &&
			   outputTrace != null;
	}
	
	/**
	 * @param transformationResult Output of a transformation.
	 * @return Retrieves the contents of a transformation output object.
	 */
	protected static ModelExtentContents getContentOfTransformationResult(Out transformationResult) {
		return transformationResult.getExtents().get(0);
	}
	
	/**
	 * Saves transformation result contents to a given URI.
	 * 
	 * @param modelTransformationResult Transformation result contents for the model part.
	 * @param diagramTransformationResult Transformation result contents for the notation part.
	 * @param saveToUri Destination URI.
	 */
	protected void saveResults(
			ModelExtentContents modelTransformationResult,
			ModelExtentContents diagramTransformationResult,
			URI saveToUri) {
		List<EObject> outObjectsModel = modelTransformationResult.getAllRootElements();
		List<EObject> outObjectsDiagram = diagramTransformationResult.getAllRootElements();

		Resource outputResource = new ResourceSetImpl()
			.createResource(saveToUri);
		outputResource.getContents().addAll(
			outObjectsModel);
		outputResource.getContents().addAll(
			outObjectsDiagram);
		
		try {
			outputResource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			handleError(e);
		}
	}
	
	/**
	 * Renames the given file to the new path.
	 * 
	 * @param fileToRename The file to rename.
	 * @param newPath The new path to the file.
	 * @param monitor The progress monitor to use to illustrate the renaming progress.
	 */
	protected void renameFile(IFile fileToRename, IPath newPath, IProgressMonitor monitor) {
		try {
			// copy-delete avoids opening of old file
			SubProgressMonitor copyDeleteMonitor = new SubProgressMonitor(monitor, 2);
			fileToRename.copy(newPath, true, copyDeleteMonitor);
			fileToRename.delete(true, copyDeleteMonitor);
		} catch (CoreException coreException) {
			handleError(coreException);
		}
	}
	
	/**
	 * Error handling method; shows a message box including the error message
	 * and throws a new Vespucci runtime exception - thus, methods which utilize
	 * this error handling routing may return null after calling it.
	 * 
	 * @param ex The source exception.
	 */
	private void handleError(final Exception ex) {
		getProgressMonitor().done();
		
		Display.getDefault().asyncExec(new Runnable() {			
			@Override
			public void run() {
				MessageDialog.openError(getShell(), getDefaultErrorString(),
						MessageFormat.format(
								"{0}: {1}",
								ex.getClass().getSimpleName(),
								ex.getMessage() == null ? "no message" : ex
										.getMessage()));
			}
		});
		
		throw new VespucciTransformationFailedException(
				getDefaultErrorString(),
				ex);
	}
	
	/**
	 * @return The default transformation error string for the specific Vespucci version.
	 */
	protected String getDefaultErrorString() {
		return new StringBuilder("Conversion of Vespucci diagram to version ")
			.append(getVespucciVersion().getNamespace())
			.append(" failed.")
			.toString();
	}

}

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

package de.tud.cs.st.vespucci.versioning.versions;

import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelExtentContents;
import org.eclipse.m2m.internal.qvt.oml.emf.util.ModelContent;
import org.eclipse.m2m.internal.qvt.oml.library.Context;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.Out;
import org.eclipse.m2m.qvt.oml.util.IContext;
import de.tud.cs.st.vespucci.errors.VespucciTransformationFailedException;
import de.tud.cs.st.vespucci.versioning.VespucciTransformationHelper;

/**
 * Template class for Vespucci version descriptors.
 * 
 * @author Dominic Scheurer
 */
@SuppressWarnings("restriction")
public abstract class VespucciVersionTemplate
extends VespucciTransformationHelper {
	/**
	 * <p>Pointer to the current version descriptor.</p>
	 * <p><strong>Remember to update this when adding newer versions!</strong></p>
	 */
	public static final VespucciVersionTemplate CURRENT_VERSION = new VespucciVersion_V0();
	
	/**
	 * Number of (single) steps needed to perform the conversion task.
	 */
	private static final int CONVERSION_STEPS = 7;
	
	/**
	 * Here, the current progress monitor is stored. This is reasonable
	 * because only one transformation should be executed per instance.
	 */
	private IProgressMonitor progessMonitor = null;
	
	/**
	 * @return The namespace string of this version, e.g. "http://vespucci.editor"
	 */
	public abstract String getNamespace();
	
	/**
	 * @return The predecessor of this version in the version chain or null if there is none.
	 */
	public abstract VespucciVersionTemplate getPredecessor();
	
	/**
	 * <p>Example for file path:</p>
	 * <pre>
	 * platform:/plugin/de.tud.cs.st.vespucci.versioning/transformations/migrate_v0_to_v1.model.qvto
	 * </pre>
	 * 
	 * @return The URI to the QVTO file which transforms the model part or null if version is first in the chain.
	 */
	public abstract URI getModelQvtoUri();
	
	/**
	 * <p>Example for file path:</p>
	 * <pre>
	 * platform:/plugin/de.tud.cs.st.vespucci.versioning/transformations/migrate_v0_to_v1.notation.qvto
	 * </pre>
	 * 
	 * @return The URI to the QVTO file which transforms the notation (diagram) part or null if version is first in the chain.
	 */
	public abstract URI getDiagramQvtoUri();
	
	@Override
	protected IProgressMonitor getProgressMonitor() {
		return progessMonitor;
	}
	
	@Override
	protected VespucciVersionTemplate getVespucciVersion() {
		return this;
	}
	
	/**
	 * Template method which converts a Vespucci file to the version indicated
	 * by the concrete implementation of this abstract class.
	 * 
	 * @param inFile File to convert.
	 * @param renameToPath New Path of the original file.
	 * @param outFileUri URI to write to.
	 * @param progressMonitor Monitor used to show the progress.
	 * @return Conversion result status.
	 */
	public IStatus convertFile(IFile inFile, IPath renameToPath, URI outFileUri, IProgressMonitor progressMonitor) {
		try {
			this.progessMonitor = progressMonitor;
			
			URI fileURI = getUriFromFile(inFile);
	
			if (resourceIsEmpty(fileURI)) {
				return Status.CANCEL_STATUS;
			}
			
			progressMonitor.beginTask(getTransformationMessage(inFile), CONVERSION_STEPS);
			
			List<EObject> fileModelContents = getOrderedResourceContents(fileURI);
			ModelContent shapesDiagramContent = new ModelContent(fileModelContents.subList(0, 1));
			ModelContent notationDiagramContent = new ModelContent(fileModelContents.subList(1, 2));
			
			IContext context = new Context();
			
			Out modelTransformationOutput = executeQvtoTransformation(context, shapesDiagramContent);
			Out diagramTransformationOutput = executeQvtoTransformation(context, notationDiagramContent);
			
			if (!outputIsCorrect(modelTransformationOutput) ||
				!outputIsCorrect(diagramTransformationOutput)) {
				throw new VespucciTransformationFailedException(getDefaultErrorString());
			}
			
			ModelExtentContents modelTransformationResult =
				getContentOfTransformationResult(modelTransformationOutput);
			
			ModelExtentContents diagramTransformationResult =
				getContentOfTransformationResult(diagramTransformationOutput);
			
			renameFile(inFile, renameToPath, progressMonitor);
			
			saveResults(modelTransformationResult, diagramTransformationResult, outFileUri);	
			
			return Status.OK_STATUS;
		} catch (VespucciTransformationFailedException transfFailedException) {
			return Status.CANCEL_STATUS;
		}
	}

}

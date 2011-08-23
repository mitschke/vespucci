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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.errors.VespucciTransformationFailedException;
import de.tud.cs.st.vespucci.proxy.Activator;
import de.tud.cs.st.vespucci.versioning.VespucciTransformationHelper;

/**
 * Template class for Vespucci version descriptors.
 * 
 * @author Dominic Scheurer
 */
@SuppressWarnings("restriction")
public abstract class VespucciVersionTemplate
extends VespucciTransformationHelper
implements Comparable<VespucciVersionTemplate> {
	/**
	 * <p>Pointer to the newest version descriptor.</p>
	 * <p><strong>Remember to update this when adding newer versions!</strong></p>
	 */
	public static final VespucciVersionTemplate NEWEST_VERSION = new VespucciVersion_20110601();
	
	/**
	 * Number of (single) steps needed to perform the conversion task.
	 */
	private static final int CONVERSION_STEPS = 9;
	
	/**
	 * Here, the current progress monitor is stored. This is reasonable
	 * because only one transformation should be executed per instance.
	 */
	private IProgressMonitor progessMonitor = null;
	
	/**
	 * @return The creation date of this version (for the natural order).
	 */
	public abstract Date getCreationDate();
	
	/**
	 * @return The namespace string of this version, e.g. "http://vespucci.editor".
	 * 	<strong>NOTE</strong>: namespace of a Vespucci version must always be unique!
	 */
	public abstract String getNamespace();
	
	/**
	 * This method returns the default version identifier. May be overridden
	 * if necessary.
	 * 
	 * @return A textual unique identifier for this version.
	 */
	public String getIdentifier() {
		// TODO consistent renaming of old version; version or time stamp?
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		return simpleDateFormat.format(getCreationDate());
	}
	
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
	
	@Override
	public int compareTo(VespucciVersionTemplate other) {
		return getCreationDate().compareTo(other.getCreationDate());
	};
	
	/**
	 * @return True if this version is the newest version, else false.
	 */
	public boolean isNewestVersion() {
		return this.equals(NEWEST_VERSION);
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
	public IStatus upgradeFileToThisVersion(IFile inFile, IPath renameToPath, URI outFileUri, IProgressMonitor progressMonitor) {
		try {
			if (getPredecessor() == null ||
				!getPredecessor().fileIsOfThisVersion(inFile)) {
				return Status.CANCEL_STATUS;
			}
			
			this.progessMonitor = progressMonitor;
			
			URI fileURI = getUriFromFile(inFile);
	
			if (resourceIsEmpty(fileURI)) {
				return Status.CANCEL_STATUS;
			}
			
			progressMonitor.beginTask(getTransformationMessage(inFile), CONVERSION_STEPS);
			
			List<EObject> fileModelContents = getOrderedResourceContents(fileURI);
			ModelContent shapesDiagramContent = new ModelContent(fileModelContents.subList(0, 1));
			ModelContent notationDiagramContent = new ModelContent(fileModelContents.subList(1, 2));
			
			progressMonitor.worked(1);
			
			IContext context = new Context();
			
			Out modelTransformationOutput = executeQvtoTransformation(context, shapesDiagramContent, getModelQvtoUri());
			progressMonitor.worked(3);
			
			Out diagramTransformationOutput = executeQvtoTransformation(context, notationDiagramContent, getDiagramQvtoUri());
			progressMonitor.worked(3);
			
			if (!outputIsCorrect(modelTransformationOutput) ||
				!outputIsCorrect(diagramTransformationOutput)) {
				throw new VespucciTransformationFailedException(getDefaultErrorString());
			}
			
			ModelExtentContents modelTransformationResult =
				getContentOfTransformationResult(modelTransformationOutput);
			
			ModelExtentContents diagramTransformationResult =
				getContentOfTransformationResult(diagramTransformationOutput);
			
			renameFile(inFile, renameToPath, progressMonitor);
			progressMonitor.worked(1);
			
			saveResults(modelTransformationResult, diagramTransformationResult, outFileUri);
			progressMonitor.worked(1);
			
			return Status.OK_STATUS;
		} catch (VespucciTransformationFailedException transfFailedException) {
			return Status.CANCEL_STATUS;
		}
	}

	/**
	 * @param file The file which namespace to check.
	 * @return True if the file has the namespace of this Vespucci version, else false.
	 */
	public boolean fileIsOfThisVersion(IFile file) {
		InputStream fileInputStream = null;
		
		try {
			fileInputStream = file.getContents();
		} catch (CoreException coreException) {
			handleError("Error reading file contents", coreException);
		}
		
		final Scanner scanner = new Scanner(fileInputStream);
		Pattern xmlnsPattern = Pattern.compile("^.*xmlns=\"(.*?)\".*$");

		while (scanner.hasNextLine()) {
			Matcher m = xmlnsPattern.matcher(scanner.nextLine());
			if (m.find() &&
				m.group(1).equalsIgnoreCase(getNamespace())) {	
				
				scanner.close();				
				return true;
				
			}
		}
		
		scanner.close();		
		return false;
	}
	
	/**
	 * Simple error handler.
	 * 
	 * @param message A custom error message.
	 * @param cause Source Exception.
	 */
	private static void handleError(String message, Exception cause) {
		IStatus is = new Status(IStatus.ERROR, Activator.PLUGIN_ID, message, cause);
		StatusManager.getManager().handle(is, StatusManager.SHOW);
		StatusManager.getManager().handle(is, StatusManager.LOG);
	}
}

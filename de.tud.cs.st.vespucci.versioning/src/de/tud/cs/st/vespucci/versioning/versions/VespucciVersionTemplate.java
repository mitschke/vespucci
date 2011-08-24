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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.osgi.util.NLS;
import de.tud.cs.st.vespucci.errors.VespucciIOException;
import de.tud.cs.st.vespucci.versioning.VespucciTransformationHelper;

/**
 * Template class for Vespucci version descriptors.
 * 
 * @author Dominic Scheurer
 */
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		return simpleDateFormat.format(getCreationDate());
	}
	
	/**
	 * @return The predecessor of this version in the version chain or null if there is none.
	 */
	public abstract VespucciVersionTemplate getPredecessor();
	
	/**
	 * 
	 * @return Returns true only if a predecessor exists.
	 */
	public boolean hasPredecessor(){
		return getPredecessor() != null;
	}
	
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
	 * Template method which converts a diagram to the version indicated
	 * by the concrete implementation of this abstract class.
	 * 
	 * @param inputDiagram File to convert. Must be a direct predecessor of this version.
	 * @param backupPath New Path of the original file.
	 * @param outputURI URI to write to.
	 * @param progressMonitor Monitor used to show the progress.
	 * @return Conversion result status.
	 */
	public IStatus updateFromDirectPredecessorVersion(
			IFile inputDiagram, IPath backupPath, URI outputURI, IProgressMonitor progressMonitor) {

		if (!hasPredecessor() ||
			!getPredecessor().fileIsOfThisVersion(inputDiagram)) {
			return Status.CANCEL_STATUS;
		}
		
		this.progessMonitor = progressMonitor;
		progressMonitor.beginTask(getTransformationMessage(inputDiagram), CONVERSION_STEPS);
		
		URI fileURI = getUriFromFile(inputDiagram);

		if (!resourceExists(fileURI)) {
			noResourceErrorMessage(fileURI);
			return Status.CANCEL_STATUS;
		}
		
		final ResourceSet resourceSet = new ResourceSetImpl();
		
		Resource inResource = resourceSet.getResource(fileURI, true);		
		EList<EObject> inObjects = inResource.getContents();
		
		VespucciTransformationInput[] transformationInputs = getModelTransformationInputs(inObjects);
		
		VespucciTransformationOutput transformationOutput = executeQvtTransformations(transformationInputs);
		
		if (!transformationOutput.getReturnCode().equals(Status.OK_STATUS)) {
			return transformationOutput.getReturnCode();
		}
		
		ArrayList<ModelExtent> transformationResults = transformationOutput.getTransformationResults();
		
		renameFile(inputDiagram, backupPath, progressMonitor);
		
		saveResults(transformationResults.get(0), transformationResults.get(1), outputURI);
		
		return Status.OK_STATUS;
	}
	
	/**
	 * @param fileURI File URI for which no resource could be found.
	 */
	private static void noResourceErrorMessage(URI fileURI) {
		ResourceBundle messagesBundle = ResourceBundle.getBundle("messages");
		String title = messagesBundle.getString("VespucciTransformationNoFileTitle");
		String message = messagesBundle.getString("VespucciTransformationNoFileMessage");
		MessageDialog.openError(getShell(), title,
				NLS.bind(message, fileURI.toString()));
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
			throw new VespucciIOException(String.format("Error occured while reading file contents of %s.", file), coreException);
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
}

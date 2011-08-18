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

package de.tud.cs.st.vespucci.diagram.actions;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelExtentContents;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.emf.util.*;
import org.eclipse.m2m.internal.qvt.oml.library.*;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.In;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.Out;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.QvtInterpretedTransformation;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.TransformationUtil;
import org.eclipse.m2m.internal.qvt.oml.trace.*;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.tud.cs.st.vespucci.versioning.versions.VespucciVersion_20110601;
import de.tud.cs.st.vespucci.versioning.versions.VespucciVersion_V0;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages;

/**
 * @author Dominic Scheurer
 * @version 1.0
 * 
 * Action for transforming serialized Vespucci .sad files of versions
 * prior to 2011-06-01 to the version 2011-06-01.
 */
public class TransformVespucciV0ToV1 implements IObjectActionDelegate {
	/** The current target part */
	private IWorkbenchPart targetPart = null;
	
	/** The currently active list of files for transformation */
	private ArrayList<IFile> files = null;
	
	/** Standard constructor; initializes the file list */
	public TransformVespucciV0ToV1() {
		files = new ArrayList<IFile>();
	}

	@Override
	public void run(IAction action) {		
		// Create the job title string
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < files.size(); i++)
		{
			sb.append(URI.createPlatformResourceURI(files.get(i).getFullPath().toString(), true).toString());
			if (i != files.size() - 1) {
				sb.append(", ");
			}
		}
		
		// Create the conversion job
		Job job = new Job("Convert Vespucci diagram " + sb.toString()) {			
			@Override
			protected IStatus run(IProgressMonitor monitor) {				
				for (IFile file : files)
				{
					VespucciVersion_20110601 ver = new VespucciVersion_20110601();
					ver.upgradeFileToThisVersion(
						file,
						file.getFullPath().addFileExtension("old"),
						URI.createPlatformResourceURI(file.getFullPath().toString(), true),
						monitor);
				}
				
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		
		job.setUser(true);
		job.schedule();
	}
	
//	/**
//	 * Renames the given file by adding ".old" before the ".sad" file ending
//	 * as long as the resulting file name is unique.
//	 * 
//	 * @param file The file to rename
//	 * @param monitor The progress monitor to use
//	 * @throws CoreException
//	 */
//	private static void renameOriginalFile(IFile file, IProgressMonitor monitor)
//	throws CoreException {
//		IPath newPath = file.getFullPath().removeFileExtension().addFileExtension("old").addFileExtension("sad");
//		while (new java.io.File(
//				file.getWorkspace().getRoot().getLocation().toFile(),
//				newPath.toFile().toString()).exists()) {
//			newPath = newPath.removeFileExtension().addFileExtension("old").addFileExtension("sad");
//		}
//
//		file.move(newPath, true, new SubProgressMonitor(monitor, 1));
//	}

//	/**
//	 * Error handling method; shows a message box including the error message.
//	 * 
//	 * @param ex The exception to show to the user.
//	 * @param monitor The current progress monitor (will be canceled)
//	 * @return Always returns CANCEL_STATUS because of abnormal abort
//	 */
//	private IStatus handleError(final Exception ex, IProgressMonitor monitor) {
//		monitor.done();
//		
//		Display.getDefault().asyncExec(new Runnable() {			
//			@Override
//			public void run() {
//				MessageDialog.openError(getShell(), "Transformation failed",
//						MessageFormat.format(
//								"{0}: {1}",
//								ex.getClass().getSimpleName(),
//								ex.getMessage() == null ? "no message" : ex
//										.getMessage()));
//			}
//		});
//		
//		return Status.CANCEL_STATUS;
//	}

//	/**
//	 * @return The shell for the current workbench site
//	 */
//	private Shell getShell() {
//		return targetPart.getSite().getShell();
//	}

//	/**
//	 * Returns the EObject contents of the file defined by the given URI.
//	 * 
//	 * @param fileURI URI to the file which contents are to be loaded
//	 * @return The EObject contents of the file defined by fileURI
//	 */
//	private static EObject getInput(URI fileURI) {
//		ResourceSetImpl rs = new ResourceSetImpl();
//		return rs.getEObject(fileURI.appendFragment("/"), true);
//	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		files = new ArrayList<IFile>();
		action.setEnabled(false);
		
		if (!(selection instanceof IStructuredSelection)
			|| selection.isEmpty()) {
			return;
		}
		
		for (Object fileObject : ((IStructuredSelection)selection).toArray())
		{
			IFile file = (IFile)fileObject;
			files.add(file);
		}
		
		action.setEnabled(true);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}
}

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

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Dominic Scheurer
 * @version 1.0
 * 
 *          Action for transforming serialized Vespucci .sad files of versions prior to 2011-06-01 to the version
 *          2011-06-01.
 */
public class TransformVespucciV0ToV1 implements IObjectActionDelegate {

	/** The currently active list of files for transformation */
	private ArrayList<IFile> files = null;

	/** Standard constructor; initializes the file list. */
	public TransformVespucciV0ToV1() {
		files = new ArrayList<IFile>();
	}

	@Override
	public void run(final IAction action) {
		final String jobTitle = createJobTitle();

		// Create the conversion job
		final Job job = new Job("Convert Vespucci diagram " + jobTitle) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.done();
				return Status.OK_STATUS;
			}
		};

		job.setUser(true);
		job.schedule();
	}

	private String createJobTitle() {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < files.size(); i++) {
			sb.append(URI.createPlatformResourceURI(files.get(i).getFullPath().toString(), true).toString());
			if (i != files.size() - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		files = new ArrayList<IFile>();
		action.setEnabled(false);

		if (!(selection instanceof IStructuredSelection) || selection.isEmpty()) {
			return;
		}

		for (final Object fileObject : ((IStructuredSelection) selection).toArray()) {
			final IFile file = (IFile) fileObject;
			files.add(file);
		}

		action.setEnabled(true);
	}

	@Override
	public void setActivePart(final IAction action, final IWorkbenchPart targetPart) {
	}
}

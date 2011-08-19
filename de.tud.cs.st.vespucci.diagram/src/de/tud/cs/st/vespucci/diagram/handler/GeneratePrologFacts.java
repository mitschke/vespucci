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
package de.tud.cs.st.vespucci.diagram.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.converter.DiagramConverter;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * A handler for saving a *.sad file to a *.pl Prolog file.
 *
 * @author Malte Viering
 */
public class GeneratePrologFacts extends AbstractHandler {

	/**
	 * Handels the event, if it is a IStructuredSelection. Should only be used
	 * for Vespucci diagram files. Generates the prolog facts of the given
	 * SAD-files.
	 *
	 * @return null
	 */
	public Object execute(ExecutionEvent event) {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event); // current Package Explorer
												// selection

		for (Object o : selection.toArray()) { // 2011-03-28: BenjaminL iterate
												// over each given file
			if (o instanceof IFile) {
				IFile file = (IFile) o;

				File f = file.getRawLocation().toFile();
				DiagramConverter dc = new DiagramConverter();
				if (dc.isDiagramFile(f)) {
					try {
						dc.convertDiagramToProlog(f); // generating from the
														// *.sad a
														// *.pl
					} catch (FileNotFoundException e) {
						IStatus is = new Status(IStatus.ERROR,
								VespucciDiagramEditorPlugin.ID,
								"FileNotFoundException", e);
						StatusManager.getManager().handle(is,
								StatusManager.SHOW);
						StatusManager.getManager()
								.handle(is, StatusManager.LOG);
					} catch (IOException e) {
						IStatus is = new Status(IStatus.ERROR,
								VespucciDiagramEditorPlugin.ID,
								"Failed to save Prolog file", e);
						StatusManager.getManager().handle(is,
								StatusManager.SHOW);
						StatusManager.getManager()
								.handle(is, StatusManager.LOG);
					} catch (Exception e) {
						IStatus is = new Status(IStatus.ERROR,
								VespucciDiagramEditorPlugin.ID,
								"FileNotFoundException", e);
						StatusManager.getManager().handle(is,
								StatusManager.SHOW);
						StatusManager.getManager()
								.handle(is, StatusManager.LOG);
					}

					// Refresh Pageview
					try {
						file.getProject().refreshLocal(
								IResource.DEPTH_INFINITE,
								new NullProgressMonitor());
					} catch (CoreException e1) {
						StatusManager.getManager().handle(e1,
								VespucciDiagramEditorPlugin.ID);
					}
				}
			}
		}
		return null;
	}

}

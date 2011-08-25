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

import de.tud.cs.st.vespucci.diagram.creator.PrologFileCreator;
import de.tud.cs.st.vespucci.errors.VespucciIOException;
import de.tud.cs.st.vespucci.errors.VespucciUnexpectedException;
import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * A handler for saving a *.sad file to a *.pl Prolog file.
 * 
 * @author Malte Viering
 * @author Alexander Weitzmann
 * @author Thomas Schulz
 */
public class GeneratePrologFacts extends AbstractHandler {

	/**
	 * Handles the event, if it is a IStructuredSelection. Should only be used
	 * for Vespucci diagram files. Generates the prolog facts of the given
	 * SAD-files.
	 * 
	 * @return null
	 */
	@Override
	public Object execute(final ExecutionEvent event) {

		// current Package Explorer selection
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);

		for (final Object o : selection.toArray()) {

			if (o instanceof IFile) {
				final IFile file = (IFile) o;

				final File diagramFile = file.getRawLocation().toFile();

				if (PrologFileCreator.isDiagramFile(diagramFile)) {

					createPrologFileFrom(diagramFile);

					refreshPageView(file);
				}
			}
		}
		return null;
	}

	private static void createPrologFileFrom(File diagramFile) {

		final PrologFileCreator prologFileCreator = new PrologFileCreator();

		try {
			prologFileCreator.createPrologFileFromDiagram(diagramFile);
		} catch (final FileNotFoundException e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		} catch (final IOException e) {
			throw new VespucciIOException(String.format("Failed to save Prolog file from [%s].",diagramFile), e);
		} catch (final Exception e) {
			throw new VespucciIOException(String.format("File [%s] not found.",diagramFile), e);
		}
	}
	
	private static void refreshPageView(final IFile file) {
		try {
			file.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e1) {
			throw new VespucciUnexpectedException(e1);
		}
	}

}

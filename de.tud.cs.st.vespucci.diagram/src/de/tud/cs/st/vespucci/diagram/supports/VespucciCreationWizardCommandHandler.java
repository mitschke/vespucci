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
package de.tud.cs.st.vespucci.diagram.supports;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciCreationWizard;

/**
 * Command handler is used to call up a wizard creating a new Vespucci diagram.
 * 
 * @author Tam-Minh Nguyen
 */
public class VespucciCreationWizardCommandHandler implements IHandler {

	@Override
	public Object execute(final ExecutionEvent event) {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		final VespucciCreationWizard wizard = new VespucciCreationWizard();

		final ISelection selection = window.getSelectionService().getSelection();

		if (selection instanceof IStructuredSelection) {
			wizard.init(window.getWorkbench(), (IStructuredSelection) selection);
		} else {
			wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
		}

		final Shell parent = window.getShell();
		final WizardDialog dialog = new WizardDialog(parent, wizard);
		dialog.create();
		dialog.open();

		return null;
	}

	/**
	 * Always enabled each time the plug-in started up.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Always handled each time the plug-in started up.
	 */
	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(final IHandlerListener handlerListener) {
		// nothing to do
	}

	@Override
	public void addHandlerListener(final IHandlerListener handlerListener) {
		// nothing to do
	}

	@Override
	public void dispose() {
		// nothing to do
	}
}

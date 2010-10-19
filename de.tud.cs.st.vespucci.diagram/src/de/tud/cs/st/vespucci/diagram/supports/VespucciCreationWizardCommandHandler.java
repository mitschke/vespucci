/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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
 * Command handler is used to call up wizard creating new Vespucci diagram.
 * 
 * @author Tam-Minh Nguyen
 */
public class VespucciCreationWizardCommandHandler implements IHandler {

    public Object execute(ExecutionEvent event) /* throws ExecutionException */{
	IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

	VespucciCreationWizard wizard = new VespucciCreationWizard();

	ISelection selection = window.getSelectionService().getSelection();

	if (selection instanceof IStructuredSelection) {
	    wizard.init(window.getWorkbench(), (IStructuredSelection) selection);
	} else {
	    wizard.init(window.getWorkbench(), StructuredSelection.EMPTY);
	}

	Shell parent = window.getShell();
	WizardDialog dialog = new WizardDialog(parent, wizard);
	dialog.create();
	// WorkbenchHelp.setHelp(dialog.getShell(),
	// IWorkbenchHelpContextIds.NEW_WIZARD_SHORTCUT);
	dialog.open();

	return null;
    }

    /**
     * Always enabled each time the plug-in started up.
     */
    public boolean isEnabled() {
	return true;
    }

    /**
     * Always handled each time the plug-in started up.
     */
    public boolean isHandled() {
	return true;
    }

    /**
     * No change will be fired.
     */
    public void removeHandlerListener(IHandlerListener handlerListener) {
    }

    /**
     * No change will be fired.
     */
    public void addHandlerListener(IHandlerListener handlerListener) {
    }

    public void dispose() {
    }
}

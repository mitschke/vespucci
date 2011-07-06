/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
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

import java.text.MessageFormat;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
//import org.eclipse.m2m.qvt.oml.runtime.ui.wizards.RunInterpretedTransformationWizardDelegate;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages;

public class TransformVespucciV0ToV1 implements IObjectActionDelegate {
	
	private IWorkbenchPart targetPart;
	private URI fileURI;

	@Override
	public void run(IAction action) {
//		try {
//			EObject source = getInput();
//			if (source == null) {
//				String title = "asdf"; //TODO Messages.ASDF
//				String message = "asdf";
//				MessageDialog.openInformation(getShell(), title, NLS.bind(message, fileURI.toString()));
//			} else {
//				URI transfUri = URI.createURI(
//					"platform:/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.qvto"
//				); //$NON-NLS-1$
//				ArrayList<URI> paramUris = new ArrayList<URI>();
//				paramUris.add(fileURI);
//				
//				IWizard wizard = (IWizard) new RunInterpretedTransformationWizardDelegate(transfUri, paramUris);
//				WizardDialog wizardDialog = new WizardDialog(getShell(), wizard);
//				wizardDialog.open();
//			}
//		} catch (Exception ex) {
//			handleError(ex);
//		}
	}

	private void handleError(Exception ex) {
		MessageDialog.openError(getShell(), "Transformation failed", MessageFormat.format("{0}: {1}", ex.getClass().getSimpleName(), ex.getMessage() == null ? "no message" : ex.getMessage()));
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	private EObject getInput() {
		ResourceSetImpl rs = new ResourceSetImpl();
		return rs.getEObject(fileURI.appendFragment("/"), true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		fileURI = null;
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false ||
			selection.isEmpty()) {
			return;
		}
		
		IFile file = (IFile) ((IStructuredSelection)
			selection).getFirstElement();
		fileURI = URI.createPlatformResourceURI(
				file.getFullPath().toString(), true
		);
		action.setEnabled(true);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

}

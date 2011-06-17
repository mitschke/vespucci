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
import org.eclipse.m2m.qvt.oml.runtime.ui.wizards.RunInterpretedTransformationWizardDelegate;
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
		try {
			EObject source = getInput();
			if (source == null) {
				String title = "asdf"; //TODO Messages.ASDF
				String message = "asdf";
				MessageDialog.openInformation(getShell(), title, NLS.bind(message, fileURI.toString()));
			} else {
				URI transfUri = URI.createURI(
					"platform:/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.qvto"
				); //$NON-NLS-1$
				ArrayList<URI> paramUris = new ArrayList<URI>();
				paramUris.add(fileURI);
				
				IWizard wizard = (IWizard) new RunInterpretedTransformationWizardDelegate(transfUri, paramUris);
				WizardDialog wizardDialog = new WizardDialog(getShell(), wizard);
				wizardDialog.open();
			}
		} catch (Exception ex) {
			handleError(ex);
		}
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

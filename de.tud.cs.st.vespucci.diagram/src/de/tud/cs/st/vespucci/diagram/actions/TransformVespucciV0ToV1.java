package de.tud.cs.st.vespucci.diagram.actions;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

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
					"platform:/de.tud.cs.st.vespucci.transforms/transformations/migrate_v0_to_v1.qvto"
				); //$NON-NLS-1$
				ArrayList<URI> paramUris = new ArrayList<URI>();
				paramUris.add(fileURI);
				
				//IWizard wizard = (IWizard) new RunI
			}
		} catch (Exception ex) {
			handleError(ex);
		}
	}

	private void handleError(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	private Shell getShell() {
		// TODO Auto-generated method stub
		return null;
	}

	private EObject getInput() {
		// TODO Auto-generated method stub
		return null;
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

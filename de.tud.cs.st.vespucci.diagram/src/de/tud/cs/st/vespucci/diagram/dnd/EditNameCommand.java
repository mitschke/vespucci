package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class EditNameCommand extends AbstractCommand {
	private CreateViewAndElementRequest createViewAndElementRequest = null;
	private EditPartViewer editPartViewer = null;

	public EditNameCommand(CreateViewAndElementRequest createViewAndElementRequest, EditPartViewer editPartViewer) {
		super("ichbineinseleccommand");
		this.createViewAndElementRequest = createViewAndElementRequest;
		this.editPartViewer = editPartViewer;
	}

	@Override
	protected CommandResult doExecuteWithResult(
			IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {

		if ((editPartViewer != null) && ((Collection)createViewAndElementRequest.getNewObject() != null))
		{
			// select the Diagramm Editor
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().setFocus();
			
			//set Focus on the added Ensemble and select the Name
			selectAddedObject(editPartViewer, (Collection)createViewAndElementRequest.getViewDescriptors());
		
			return CommandResult.newOKCommandResult();
		}
		
		return CommandResult.newErrorCommandResult("Command was not Executeable\n pls see canExecute in EditNameCommand");

	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor,
			IAdaptable info) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Select the newly added shape view by default
	 * @param viewer
	 * @param objects
	 */
	protected void selectAddedObject(EditPartViewer viewer, Collection objects) {
		final List editparts = new ArrayList();
		for (Iterator i = objects.iterator(); i.hasNext();) {
			Object object = i.next();
			if (object instanceof IAdaptable) {
				Object editPart =
					viewer.getEditPartRegistry().get(
						((IAdaptable)object).getAdapter(View.class));
				if (editPart != null)
					editparts.add(editPart);
			}
		}

		if (!editparts.isEmpty()) {
			viewer.setSelection(new StructuredSelection(editparts));
		
			// automatically put the first shape into edit-mode
			Display.getCurrent().asyncExec(new Runnable() {
				public void run(){
					EditPart editPart = (EditPart) editparts.get(0);
					//
					// add active test since test scripts are failing on this
					// basically, the editpart has been deleted when this 
					// code is being executed. (see RATLC00527114)
					if ( editPart.isActive() ) {
						editPart.performRequest(new Request(RequestConstants.REQ_DIRECT_EDIT));
						revealEditPart((EditPart)editparts.get(0));
					}
				}
			});
		}
	}

	/**
	 * Reveals the newly created editpart
	 * @param editPart
	 */
	protected void revealEditPart(EditPart editPart){
		if ((editPart != null)&&
				(editPart.getViewer() != null))
				editPart.getViewer().reveal(editPart);
	}
}

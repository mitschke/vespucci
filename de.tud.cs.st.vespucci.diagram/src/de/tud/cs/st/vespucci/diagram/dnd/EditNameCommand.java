package de.tud.cs.st.vespucci.diagram.dnd;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.ui.PlatformUI;

public class EditNameCommand extends AbstractCommand {

	@Override
	protected CommandResult doExecuteWithResult(
			IProgressMonitor progressMonitor, IAdaptable info)
			throws ExecutionException {
		
		// select the Diagramm Editor
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().setFocus();
		
		return null;
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

}

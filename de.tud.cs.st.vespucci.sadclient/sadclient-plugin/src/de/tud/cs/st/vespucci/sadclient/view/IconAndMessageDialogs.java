package de.tud.cs.st.vespucci.sadclient.view;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.tud.cs.st.vespucci.sadclient.Activator;

/**
 * Provides static utility methods for creating and displaying instances of
 * subclasses of {@link IconAndMessageDialog}.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class IconAndMessageDialogs {

    public static void showErrorDialog(final Shell shell, final String message) {
	shell.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		ErrorDialog errorDialog = new ErrorDialog(shell, "Error", message, new Status(IStatus.ERROR,
			Activator.PLUGIN_ID, message), IStatus.ERROR);
		errorDialog.open();
	    }
	});
    }
    
}

/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
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
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.view;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Used getting user assistance to resolve file name collisions during writing.
 * 
 * @author Mateusz Parzonka
 * 
 */
public class WriteFilesDialog extends MessageDialog {

    private final WriteFilesSettings writeFilesSettings;
    private Button btnCheckButton;

    /**
     * Create the dialog.
     * 
     * @param parentShell
     */
    public WriteFilesDialog(Shell parentShell, String fileName, WriteFilesSettings writeFilesSettings) {
	super(parentShell, "Writing File", null, "File \"" + fileName + "\" exists already!", 4, new String[] {
		"Rename", "Stop", "Overwrite" }, 0);
	this.writeFilesSettings = writeFilesSettings;
    }

    /**
     * Create contents of the dialog.
     * 
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent) {
	Composite container = (Composite) super.createDialogArea(parent);
	container.setLayout(new GridLayout(2, false));

	btnCheckButton = new Button(container, SWT.CHECK);
	btnCheckButton.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    }
	});
	btnCheckButton.setText("Apply to all files");
	return container;
    }

    /**
     * Create contents of the button bar.
     * 
     * @param parent
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	createButton(parent, 0, "Rename", false);
	createButton(parent, IDialogConstants.CANCEL_ID, "Stop", false);
	createButton(parent, 2, "Overwrite", true);
    }

    @Override
    protected void buttonPressed(int buttonId) {
	int result = 0;
	switch (buttonId) {
	case IDialogConstants.CANCEL_ID:
	    break;
	case 0:
	    writeFilesSettings.setOverwrite(true);
	    writeFilesSettings.setApplyToAll(btnCheckButton.getSelection());
	    result = IDialogConstants.OK_ID;
	    break;
	case 2:
	    writeFilesSettings.setOverwrite(false);
	    writeFilesSettings.setApplyToAll(btnCheckButton.getSelection());
	    result = IDialogConstants.OK_ID;
	    break;
	}
	super.buttonPressed(result);
    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
	return new Point(450, 160);
    }

    public static void askForSettings(final WriteFilesSettings writeFilesSettings, final String fileName) {
	final int[] result = new int[1];
	PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
	    public void run() {
		WriteFilesDialog dialog = new WriteFilesDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
			fileName, writeFilesSettings);
		dialog.setBlockOnOpen(true);
		result[0] = dialog.open();
	    }
	});
    }

    public static class WriteFilesSettings {

	private boolean applyToAll;

	private boolean overwrite;

	public WriteFilesSettings() {
	    super();
	}

	public void setOverwrite(boolean overwrite) {
	    this.overwrite = overwrite;
	}

	public void setApplyToAll(boolean applyToAll) {
	    this.applyToAll = applyToAll;
	}

	public boolean isApplyToAll() {
	    return applyToAll;
	}

	public boolean isOverwrite() {
	    return overwrite;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("WriteFilesSettings [applyToAll=");
	    builder.append(applyToAll);
	    builder.append(", overwrite=");
	    builder.append(overwrite);
	    builder.append("]");
	    return builder.toString();
	}

    }

}

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

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;

public class OverwriteDialog extends Dialog {

    Button btnCheckButton;

    /**
     * Create the dialog.
     * 
     * @param parentShell
     */
    public OverwriteDialog(Shell parentShell) {
	super(parentShell);
    }

    /**
     * Create contents of the dialog.
     * 
     * @param parent
     */
    @Override
    protected Control createDialogArea(Composite parent) {
	final Composite container = (Composite) super.createDialogArea(parent);
	container.setLayout(new GridLayout(1, false));

	Composite composite = new Composite(container, SWT.NONE);
	GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	gd_composite.heightHint = 50;
	gd_composite.widthHint = 441;
	composite.setLayoutData(gd_composite);

	Label lblNewLabel = new Label(composite, SWT.NONE);
	lblNewLabel.setBounds(10, 10, 396, 14);
	lblNewLabel.setText("File with this name exists already!");

	Composite composite_1 = new Composite(container, SWT.NONE);
	composite_1.setLayout(new GridLayout(4, true));
	GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	gd_composite_1.widthHint = 450;
	gd_composite_1.heightHint = 42;
	composite_1.setLayoutData(gd_composite_1);

	btnCheckButton = new Button(composite_1, SWT.CHECK);
	GridData gd_btnCheckButton = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	gd_btnCheckButton.widthHint = 89;
	btnCheckButton.setLayoutData(gd_btnCheckButton);
	btnCheckButton.setText("Apply to all");

	Button btnStop = new Button(composite_1, SWT.CENTER);
	btnStop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	btnStop.addMouseListener(new ButtonListener(container, -1));

	Button btnRename = new Button(composite_1, SWT.NONE);
	btnRename.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	btnRename.setText("Rename");
	btnRename.addMouseListener(new ButtonListener(container, 1));

	Button btnOverwrite = new Button(composite_1, SWT.CENTER);
	btnOverwrite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	btnOverwrite.setText("Overwrite");
	btnOverwrite.addMouseListener(new ButtonListener(container, 3));

	return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	// no buttons needed
    }

    private class ButtonListener extends MouseAdapter {

	private Widget container;
	private int returnCode;

	public ButtonListener(Widget container, int returnCode) {
	    super();
	    this.container = container;
	    this.returnCode = returnCode;
	}

	@Override
	public void mouseUp(MouseEvent e) {
	    container.getDisplay().asyncExec(new Runnable() {

		@Override
		public void run() {
		    returnCode += btnCheckButton.getSelection() ? 0 : 1;
		    setReturnCode(returnCode);
		    close();
		}
	    });
	}

    }

    /**
     * Return the initial size of the dialog.
     */
    @Override
    protected Point getInitialSize() {
	return new Point(425, 150);
    }

    public static OverwriteSettings askForSettings(OverwriteSettings overwriteSettings) {
	final int[] result = new int[1];
	PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
	    public void run() {
		OverwriteDialog bar = new OverwriteDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		bar.setBlockOnOpen(true);
		result[0] = bar.open();
	    }
	});
	switch (result[0]) {
	case 1:
	    overwriteSettings.setApplyToAll(false);
	    overwriteSettings.setOverwrite(false);
	    return overwriteSettings;
	case 2:
	    overwriteSettings.setApplyToAll(true);
	    overwriteSettings.setOverwrite(false);
	    return overwriteSettings;
	case 3:
	    overwriteSettings.setApplyToAll(false);
	    overwriteSettings.setOverwrite(true);
	    return overwriteSettings;
	case 4:
	    overwriteSettings.setApplyToAll(false);
	    overwriteSettings.setOverwrite(true);
	    return overwriteSettings;
	default:
	    throw new OperationCanceledException();
	}
    }

    public static class OverwriteSettings {

	private boolean applyToAll;

	private boolean option;

	public OverwriteSettings(boolean applyToAll, boolean option) {
	    super();
	    this.applyToAll = applyToAll;
	    this.option = option;
	}

	private void setOverwrite(boolean overwrite) {
	    this.option = overwrite;
	}

	private void setApplyToAll(boolean overwrite) {
	    this.applyToAll = overwrite;
	}

	public boolean isApplyToAll() {
	    return applyToAll;
	}

	public boolean isOverwrite() {
	    return option;
	}

    }

}
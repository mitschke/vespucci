package de.tud.cs.st.vespucci.sadclient.upload;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;

public class UploadDialog extends Dialog {
	private Text text;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public UploadDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		Group grpNewGroup = new Group(container, SWT.NONE);
		grpNewGroup.setText("New Group");
		grpNewGroup.setBounds(5, 5, 440, 150);
		
		Composite composite_1 = new Composite(grpNewGroup, SWT.NONE);
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(20, 30, 20, 20);
		
		Composite composite_2 = new Composite(grpNewGroup, SWT.NONE);
		
		Composite composite = new Composite(grpNewGroup, SWT.NONE);
		
		StyledText styledText = new StyledText(grpNewGroup, SWT.BORDER | SWT.READ_ONLY);
		styledText.setEnabled(false);
		styledText.setDoubleClickEnabled(false);
		styledText.setSelectionForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		styledText.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		styledText.setText("Not set");
		styledText.setBounds(80, 10, 232, 15);
		
		Button btnNewButton = new Button(grpNewGroup, SWT.NONE);
		btnNewButton.setBounds(332, 3, 94, 28);
		btnNewButton.setText("Edit");
		
		Group grpSecondGroup = new Group(container, SWT.NONE);
		grpSecondGroup.setText("Second Group");
		grpSecondGroup.setBounds(5, 155, 440, 70);
		grpSecondGroup.setLayout(new BorderLayout(0, 0));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}

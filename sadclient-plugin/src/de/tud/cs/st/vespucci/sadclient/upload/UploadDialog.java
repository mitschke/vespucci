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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.List;

public class UploadDialog extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

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
		grpNewGroup.setText("Description");
		grpNewGroup.setBounds(5, 10, 440, 216);
		
		Composite composite_1 = new Composite(grpNewGroup, SWT.NONE);
		
		text = new Text(composite_1, SWT.BORDER);
		text.setBounds(20, 30, 20, 20);
		
		Composite composite_2 = new Composite(grpNewGroup, SWT.NONE);
		
		Composite composite = new Composite(grpNewGroup, SWT.NONE);
		
		Button btnNewButton = new Button(grpNewGroup, SWT.NONE);
		btnNewButton.setBounds(332, 161, 94, 28);
		btnNewButton.setText("Save");
		
		text_1 = new Text(grpNewGroup, SWT.BORDER);
		text_1.setBounds(88, 10, 338, 19);
		
		Label lblName = new Label(grpNewGroup, SWT.NONE);
		lblName.setBounds(10, 10, 59, 14);
		lblName.setText("Name");
		
		text_2 = new Text(grpNewGroup, SWT.BORDER);
		text_2.setBounds(88, 35, 338, 19);
		
		Label lblType = new Label(grpNewGroup, SWT.NONE);
		lblType.setText("Type");
		lblType.setBounds(10, 35, 59, 14);
		
		text_3 = new Text(grpNewGroup, SWT.BORDER | SWT.WRAP);
		text_3.setBounds(88, 60, 338, 95);
		
		Label lblAbstract = new Label(grpNewGroup, SWT.NONE);
		lblAbstract.setText("Abstract");
		lblAbstract.setBounds(10, 61, 59, 14);
		
		Group modelGroup = new Group(container, SWT.NONE);
		modelGroup.setText("Model");
		modelGroup.setBounds(5, 232, 440, 83);
		
		StyledText modelStatusText = new StyledText(modelGroup, SWT.BORDER | SWT.READ_ONLY);
		modelStatusText.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		modelStatusText.setBounds(93, 10, 333, 15);
		modelStatusText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		modelStatusText.setEnabled(false);
		modelStatusText.setDoubleClickEnabled(false);
		modelStatusText.setText("Uploaded 48k");
		
		Button modelDeleteButton = new Button(modelGroup, SWT.NONE);
		modelDeleteButton.setText("Delete");
		modelDeleteButton.setBounds(231, 33, 94, 28);
		
		Label modelCurrentFileLabel = new Label(modelGroup, SWT.NONE);
		modelCurrentFileLabel.setText("Current File");
		modelCurrentFileLabel.setBounds(10, 11, 77, 14);
		
		Button modelUploadButton = new Button(modelGroup, SWT.NONE);
		modelUploadButton.setText("Upload");
		modelUploadButton.setBounds(331, 33, 94, 28);
		
		Group grpDocumentation = new Group(container, SWT.NONE);
		grpDocumentation.setText("Documentation");
		grpDocumentation.setBounds(5, 321, 440, 83);
		
		StyledText styledText = new StyledText(grpDocumentation, SWT.BORDER | SWT.READ_ONLY);
		styledText.setText("Nothing uploaded");
		styledText.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND));
		styledText.setEnabled(false);
		styledText.setDoubleClickEnabled(false);
		styledText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		styledText.setBounds(93, 10, 333, 15);
		
		Button button = new Button(grpDocumentation, SWT.NONE);
		button.setText("Delete");
		button.setBounds(231, 33, 94, 28);
		
		Label label = new Label(grpDocumentation, SWT.NONE);
		label.setText("Current File");
		label.setBounds(10, 11, 77, 14);
		
		Button button_1 = new Button(grpDocumentation, SWT.NONE);
		button_1.setText("Upload");
		button_1.setBounds(331, 33, 94, 28);
		
		CLabel lblNewLabel = new CLabel(container, SWT.BORDER | SWT.SHADOW_IN);
		lblNewLabel.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		lblNewLabel.setBounds(17, 411, 421, 19);
		lblNewLabel.setText("Stored description at server.");

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
//		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
//				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CLOSE_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 520);
	}
}

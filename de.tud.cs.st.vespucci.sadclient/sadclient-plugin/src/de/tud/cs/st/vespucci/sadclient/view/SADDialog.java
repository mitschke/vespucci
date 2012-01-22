package de.tud.cs.st.vespucci.sadclient.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.controller.ISADDialog;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class SADDialog extends Dialog implements ISADDialog {

    protected Object result;
    private Composite container;
    private Controller listener;

    private Label lblName;
    private Label lblType;
    private Label lblAbstract;
    private Text txtName;
    private Text txtType;
    private Text txtAbstract;

    private StyledText txtModel;
    private Button btnModelDelete;
    private Button btnModelUpload;
    private Button btnModelDownload;

    private StyledText txtDoc;
    private Button btnDocDelete;
    private Button btnDocUpload;
    private Button btnDocDownload;
    private Button btnSave;

    public SADDialog(Shell parent) {
	super(parent);
    }
    
    @Override
    public void updateDescription(final SAD sad) {
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		synchronized (sad) {
		    txtName.setText(sad.getName());
		    txtType.setText(sad.getType());
		    txtAbstract.setText(sad.getAbstrct());
		}
		btnSave.setEnabled(false);
	    }
	});
    }

    @Override
    public void updateModel(final SAD sad) {
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		SAD.Model model;
		synchronized (sad) {
		    model = sad.getModel();
		}
		if (model != null) {
		    txtModel.setText("" + model.getSize() + "k");
		    btnModelDelete.setEnabled(true);
		    btnModelDownload.setEnabled(true);
		} else {
		    txtModel.setText("None");
		    btnModelDelete.setEnabled(false);
		    btnModelDownload.setEnabled(false);
		}
	    }
	});
    }

    @Override
    public void updateDocumentation(final SAD sad) {
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		SAD.Documentation documentation;
		synchronized (sad) {
		    documentation = sad.getDocumentation();
		}
		if (documentation != null) {
		    txtDoc.setText("" + documentation.getSize() + "k");
		    btnDocDelete.setEnabled(true);
		    btnDocDownload.setEnabled(true);
		} else {
		    txtDoc.setText("None");
		    btnDocDelete.setEnabled(false);
		    btnDocDownload.setEnabled(false);
		}
	    }
	});
    }

    @Override
    protected Point getInitialSize() {
	return new Point(460, 450);
    }

    @Override
    protected Control createDialogArea(Composite parent) {

	final int BORDER_MARGIN = 10;
	final int GROUP_MARGIN = 5;
	
	/**
	 * When one of the text fields of the description is changed, the save-mode is enabled. 
	 */
	final class DescriptionModifyListener implements ModifyListener {
	    public void modifyText(ModifyEvent arg0) {
		container.getDisplay().asyncExec(new Runnable() {
		    public void run() {
			btnSave.setEnabled(true);
		    }
		});
	    }
	}
	
	container = (Composite) super.createDialogArea(parent);
	container.setLayout(new FormLayout());

	// Description //
	Group grpDescription = new Group(container, SWT.SHADOW_IN);
	grpDescription.setText("Description");
	grpDescription.setLayout(new FormLayout());

	FormData fd_grpDescription = new FormData();
	fd_grpDescription.top = new FormAttachment(0, BORDER_MARGIN);
	fd_grpDescription.left = new FormAttachment(0, BORDER_MARGIN);
	fd_grpDescription.right = new FormAttachment(100, -BORDER_MARGIN);
	fd_grpDescription.bottom = new FormAttachment(68);
	grpDescription.setLayoutData(fd_grpDescription);

	lblName = new Label(grpDescription, SWT.NONE);
	FormData fd_lblName = new FormData();
	fd_lblName.top = new FormAttachment(0, BORDER_MARGIN);
	fd_lblName.left = new FormAttachment(0, BORDER_MARGIN);
	lblName.setLayoutData(fd_lblName);
	lblName.setText("Name");

	txtName = new Text(grpDescription, SWT.BORDER);
	FormData fd_txtName = new FormData();
	fd_txtName.bottom = new FormAttachment(lblName, 0, SWT.BOTTOM);
	fd_txtName.left = new FormAttachment(25, BORDER_MARGIN);
	fd_txtName.right = new FormAttachment(100, -BORDER_MARGIN);
	txtName.setLayoutData(fd_txtName);
	txtName.addModifyListener(new DescriptionModifyListener());

	lblType = new Label(grpDescription, SWT.NONE);
	FormData fd_lblType = new FormData();
	fd_lblType.top = new FormAttachment(lblName, BORDER_MARGIN);
	fd_lblType.left = new FormAttachment(0, BORDER_MARGIN);
	lblType.setLayoutData(fd_lblType);
	lblType.setText("Type");

	txtType = new Text(grpDescription, SWT.BORDER);
	FormData fd_txtType = new FormData();
	fd_txtType.bottom = new FormAttachment(lblType, 0, SWT.BOTTOM);
	fd_txtType.left = new FormAttachment(25, BORDER_MARGIN);
	fd_txtType.right = new FormAttachment(100, -BORDER_MARGIN);
	txtType.setLayoutData(fd_txtType);
	txtType.addModifyListener(new DescriptionModifyListener());

	lblAbstract = new Label(grpDescription, SWT.NONE);
	FormData fd_lblAbstract = new FormData();
	fd_lblAbstract.top = new FormAttachment(lblType, BORDER_MARGIN);
	fd_lblAbstract.left = new FormAttachment(0, BORDER_MARGIN);
	lblAbstract.setLayoutData(fd_lblAbstract);
	lblAbstract.setText("Abstract");

	btnSave = new Button(grpDescription, SWT.NONE);
	FormData fd_btnSave = new FormData();
	fd_btnSave.left = new FormAttachment(75, 0);
	fd_btnSave.right = new FormAttachment(100, -10);
	fd_btnSave.bottom = new FormAttachment(100, -5);
	btnSave.setLayoutData(fd_btnSave);
	btnSave.setText("Save");
	btnSave.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.saveDescription(container);
	    }
	});

	txtAbstract = new Text(grpDescription, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	FormData fd_txtAbstract = new FormData();
	fd_txtAbstract.top = new FormAttachment(lblAbstract, -19);
	fd_txtAbstract.left = new FormAttachment(25, BORDER_MARGIN);
	fd_txtAbstract.right = new FormAttachment(100, -BORDER_MARGIN);
	fd_txtAbstract.bottom = new FormAttachment(btnSave, -5);
	txtAbstract.setLayoutData(fd_txtAbstract);
	txtAbstract.addModifyListener(new DescriptionModifyListener());

	// Model //
	Group grpModel = new Group(container, SWT.NONE);
	grpModel.setText("Model");
	grpModel.setLayout(new GridLayout(4, true));
	FormData fd_grpModel = new FormData();
	fd_grpModel.top = new FormAttachment(grpDescription, GROUP_MARGIN);
	fd_grpModel.left = new FormAttachment(grpDescription, 0, SWT.LEFT);
	fd_grpModel.right = new FormAttachment(grpDescription, 0, SWT.RIGHT);
	fd_grpModel.bottom = new FormAttachment(84, 0);
	grpModel.setLayoutData(fd_grpModel);

	txtModel = new StyledText(grpModel, SWT.BORDER);
	txtModel.setText("48k uploaded");
	txtModel.setToolTipText("Shows if a model was uploaded or not.");
	txtModel.setLeftMargin(2);
	txtModel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	txtModel.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
	txtModel.setEditable(false);
	txtModel.setDoubleClickEnabled(false);
	GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	gd.horizontalIndent = BORDER_MARGIN;
	gd.verticalIndent = -1;
	txtModel.setLayoutData(gd);

	btnModelDelete = new Button(grpModel, SWT.NONE);
	btnModelDelete.setText("Delete");
	btnModelDelete.setToolTipText("Deletes the remotely stored model.");
	btnModelDelete.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnModelDelete.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.deleteModel(container);
	    }
	});

	btnModelUpload = new Button(grpModel, SWT.NONE);
	btnModelUpload.setText("Upload");
	btnModelUpload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnModelUpload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.downloadModel(container);
	    }
	});

	btnModelDownload = new Button(grpModel, SWT.NONE);
	btnModelDownload.setText("Download");
	btnModelDownload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnModelDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.downloadModel(container);
	    }
	});

	// Documentation //
	Group grpDocumentation = new Group(container, SWT.NONE);
	grpDocumentation.setText("Documentation");
	grpDocumentation.setLayout(new GridLayout(4, true));
	FormData fd_grpDocumentation = new FormData();
	fd_grpDocumentation.top = new FormAttachment(grpModel, GROUP_MARGIN);
	fd_grpDocumentation.left = new FormAttachment(grpModel, 0, SWT.LEFT);
	fd_grpDocumentation.right = new FormAttachment(grpModel, 0, SWT.RIGHT);
	fd_grpDocumentation.bottom = new FormAttachment(100, 0);
	grpDocumentation.setLayoutData(fd_grpDocumentation);

	txtDoc = new StyledText(grpDocumentation, SWT.BORDER);
	txtDoc.setText("None");
	txtDoc.setToolTipText("Shows if a documentation was uploaded or not.");
	txtDoc.setLeftMargin(2);
	txtDoc.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	txtDoc.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
	txtDoc.setEditable(false);
	txtDoc.setDoubleClickEnabled(false);
	GridData gdDoc = new GridData(GridData.FILL_HORIZONTAL);
	gdDoc.horizontalIndent = BORDER_MARGIN;
	gdDoc.verticalIndent = -1;
	txtDoc.setLayoutData(gdDoc);

	btnDocDelete = new Button(grpDocumentation, SWT.NONE);
	btnDocDelete.setText("Delete");
	btnDocDelete.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnDocDelete.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.deleteDocumentation(container);
	    }
	});

	btnDocUpload = new Button(grpDocumentation, SWT.NONE);
	btnDocUpload.setText("Upload");
	btnDocUpload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnDocUpload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.downloadDocumentation(container);
	    }
	});

	btnDocDownload = new Button(grpDocumentation, SWT.NONE);
	btnDocDownload.setText("Download");
	btnDocDownload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnDocDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		listener.downloadDocumentation(container);
	    }
	});

	return container;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CLOSE_LABEL, false);
    }
}

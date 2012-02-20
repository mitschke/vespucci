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

import java.io.File;
import java.util.concurrent.Future;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.tud.cs.st.vespucci.sadclient.concurrent.Callback;
import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.model.SAD;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * 
 * 
 * @author Mateusz Parzonka
 * 
 */
public class SADDialog extends Dialog {

    private final Viewer parentViewer;

    protected Object result;
    private Composite container;
    final private Controller controller = Controller.getInstance();

    final private String id;
    private Label lblName;
    private Label lblType;
    private Label lblAbstract;
    private Text txtName;
    private Text txtType;
    private Text txtAbstract;

    private Text txtModelLocation;
    private Button btnModelBrowse;
    private Button btnModelDownload;
    private ProgressBar progressModel;
    private Button radioModelKeep;
    private Button radioModelUpload;
    private Button radioModelDelete;

    private StyledText txtDoc;
    private Button btnDocDelete;
    private Button btnDocDownload;
    private boolean textChanged;
    private Composite composite;

    private SAD sad;

    public SADDialog(Viewer parent, String id) {
	super(parent.getControl().getShell());
	this.parentViewer = parent;
	this.id = id;
    }

    public void updateSAD(final SAD sad) {

	container.getDisplay().syncExec(new Runnable() {

	    public void run() {

		System.out.println("Opening SAD: " + sad);

		txtName.setText(sad.getName());
		txtType.setText(sad.getType());
		txtAbstract.setText(sad.getAbstrct());
		SAD.Model model = sad.getModel();

		if (model != null) {
		    // progressModel.setMaximum(model.getSize());
		    // progressModel.setSelection(model.getSize());
		    radioModelKeep.setText("Keep existing (Currently '" + model.getName() + "')");
		    btnModelDownload.setEnabled(true);
		} else {
		    radioModelKeep.setText("Keep existing (Nothing uploaded)");
		    btnModelDownload.setEnabled(false);
		    // progressModel.setSelection(0);
		}

		SAD.Documentation doc = sad.getDocumentation();
		if (doc != null) {
		    // radioModelKeep.setText("Keep existing (Currently '" +
		    // doc.getName() + "')"); TODO
		    // btnDocDownload.setEnabled(true);
		} else {
		    // radioModelKeep.setText("Keep existing (Nothing uploaded)");
		    // TODO
		    // btnDocDownload.setEnabled(false);
		}

		SADDialog.this.sad = sad;
	    }
	});
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		parentViewer.refresh();
	    }
	});
    }

    @Override
    protected Point getInitialSize() {
	return new Point(500, 570);
    }

    @Override
    protected Control createDialogArea(Composite parent) {

	final int BORDER_MARGIN = 10;
	final int GROUP_MARGIN = 5;
	final int LEFT_TAB = 15;
	final int LINE_MARGIN = 12;

	final int DESCRIPTION_SPACE = 50;
	final int MODEL_SPACE = 75;
	final int DOCUMENTATION_SPACE = 100;

	/**
	 * When one of the text fields of the description is changed, the
	 * save-mode is enabled.
	 */
	final class DescriptionModifyListener implements ModifyListener {
	    public void modifyText(ModifyEvent event) {
		container.getDisplay().syncExec(new Runnable() {
		    public void run() {
			textChanged = true;
		    }
		});
	    }
	}

	container = (Composite) super.createDialogArea(parent);
	container.setLayout(new FormLayout());

	// Description //
	Composite grpDescription = new Composite(container, SWT.NONE);
	grpDescription.setLayout(new FormLayout());

	FormData fd_grpDescription = new FormData();
	fd_grpDescription.top = new FormAttachment(0, BORDER_MARGIN);
	fd_grpDescription.left = new FormAttachment(0, BORDER_MARGIN);
	fd_grpDescription.right = new FormAttachment(100, -BORDER_MARGIN);
	fd_grpDescription.bottom = new FormAttachment(DESCRIPTION_SPACE);
	grpDescription.setLayoutData(fd_grpDescription);

	lblName = new Label(grpDescription, SWT.NONE);
	FormData fd_lblName = new FormData();
	fd_lblName.top = new FormAttachment(0, BORDER_MARGIN);
	fd_lblName.left = new FormAttachment(0, BORDER_MARGIN);
	lblName.setLayoutData(fd_lblName);
	lblName.setText("Name:");

	txtName = new Text(grpDescription, SWT.BORDER);
	FormData fd_txtName = new FormData();
	fd_txtName.bottom = new FormAttachment(lblName, 0, SWT.BOTTOM);
	fd_txtName.left = new FormAttachment(LEFT_TAB, BORDER_MARGIN);
	fd_txtName.right = new FormAttachment(100, -BORDER_MARGIN);
	txtName.setLayoutData(fd_txtName);
	txtName.addModifyListener(new DescriptionModifyListener());

	lblType = new Label(grpDescription, SWT.NONE);
	FormData fd_lblType = new FormData();
	fd_lblType.top = new FormAttachment(lblName, LINE_MARGIN);
	fd_lblType.left = new FormAttachment(0, BORDER_MARGIN);
	lblType.setLayoutData(fd_lblType);
	lblType.setText("Type:");

	txtType = new Text(grpDescription, SWT.BORDER);
	FormData fd_txtType = new FormData();
	fd_txtType.bottom = new FormAttachment(lblType, 0, SWT.BOTTOM);
	fd_txtType.left = new FormAttachment(LEFT_TAB, BORDER_MARGIN);
	fd_txtType.right = new FormAttachment(100, -BORDER_MARGIN);
	txtType.setLayoutData(fd_txtType);
	txtType.addModifyListener(new DescriptionModifyListener());

	lblAbstract = new Label(grpDescription, SWT.NONE);
	FormData fd_lblAbstract = new FormData();
	fd_lblAbstract.top = new FormAttachment(lblType, LINE_MARGIN);
	fd_lblAbstract.left = new FormAttachment(0, BORDER_MARGIN);
	lblAbstract.setLayoutData(fd_lblAbstract);
	lblAbstract.setText("Abstract:");

	txtAbstract = new Text(grpDescription, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	FormData fd_txtAbstract = new FormData();
	fd_txtAbstract.top = new FormAttachment(lblAbstract, -19);
	fd_txtAbstract.left = new FormAttachment(LEFT_TAB, BORDER_MARGIN);
	fd_txtAbstract.right = new FormAttachment(100, -BORDER_MARGIN);
	fd_txtAbstract.bottom = new FormAttachment(100, -BORDER_MARGIN);
	txtAbstract.setLayoutData(fd_txtAbstract);
	txtAbstract.addModifyListener(new DescriptionModifyListener());

	// ////////////////////////////////////////// Model
	// ////////////////////////////////////////
	Group grpModel = new Group(container, SWT.NONE);
	grpModel.setText("Model");
	grpModel.setLayout(new FormLayout());

	FormData fd_grpModel = new FormData();
	fd_grpModel.top = new FormAttachment(grpDescription, BORDER_MARGIN);
	fd_grpModel.left = new FormAttachment(0, BORDER_MARGIN);
	fd_grpModel.right = new FormAttachment(100, -BORDER_MARGIN);
	fd_grpModel.bottom = new FormAttachment(MODEL_SPACE);
	grpModel.setLayoutData(fd_grpModel);

	//
	radioModelKeep = new Button(grpModel, SWT.RADIO);
	radioModelKeep.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    }
	});
	FormData fd_radioModel = new FormData();
	fd_radioModel.top = new FormAttachment(0, BORDER_MARGIN);
	fd_radioModel.left = new FormAttachment(0, BORDER_MARGIN);
	radioModelKeep.setLayoutData(fd_radioModel);
	radioModelKeep.setText("Keep existing (currently 'someModel1.sad')");

	btnModelDownload = new Button(grpModel, SWT.NONE);
	btnModelDownload.setText("Download");
	btnModelDownload.setToolTipText("Downloads the file to disk.");
	FormData fd_btnModelDownload = new FormData();
	fd_btnModelDownload.top = new FormAttachment(grpModel, 6);
	fd_btnModelDownload.left = new FormAttachment(75, BORDER_MARGIN);
	fd_btnModelDownload.right = new FormAttachment(100, -BORDER_MARGIN);
	btnModelDownload.setLayoutData(fd_btnModelDownload);
	btnModelDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		container.getDisplay().asyncExec(new Runnable() {
		    @Override
		    public void run() {
			File downloadLocation = selectFileDialog("sad", sad.getModel().getName());
			if (downloadLocation != null)
			    controller.downloadModel(sad.getId(), downloadLocation);
		    }
		});
	    }
	});

	//
	radioModelUpload = new Button(grpModel, SWT.RADIO);
	radioModelUpload.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    }
	});
	fd_radioModel = new FormData();
	fd_radioModel.top = new FormAttachment(radioModelKeep, BORDER_MARGIN);
	fd_radioModel.left = new FormAttachment(0, BORDER_MARGIN);
	radioModelUpload.setLayoutData(fd_radioModel);
	radioModelUpload.setText("Upload new file:");
	radioModelUpload.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		container.getDisplay().asyncExec(new Runnable() {
		    @Override
		    public void run() {
			System.out.println("Selecting file to upload");
			txtModelLocation.setText(openUploadDialog().getAbsolutePath());
		    }
		});
	    }
	});

	//
	radioModelDelete = new Button(grpModel, SWT.RADIO);
	radioModelDelete.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    }
	});
	fd_radioModel = new FormData();
	fd_radioModel.top = new FormAttachment(radioModelUpload, LINE_MARGIN);
	fd_radioModel.left = new FormAttachment(0, BORDER_MARGIN);
	radioModelDelete.setLayoutData(fd_radioModel);
	radioModelDelete.setText("Delete existing");

	// TODO
	txtModelLocation = new Text(grpModel, SWT.BORDER);
	txtModelLocation.setEnabled(false);
	FormData fd_txtModelLocation = new FormData();
	fd_txtModelLocation.top = new FormAttachment(radioModelKeep, 8);
	fd_txtModelLocation.left = new FormAttachment(28);
	fd_txtModelLocation.right = new FormAttachment(75);
	txtModelLocation.setLayoutData(fd_txtModelLocation);
	txtModelLocation.addModifyListener(new DescriptionModifyListener());

	btnModelBrowse = new Button(grpModel, SWT.NONE);
	btnModelBrowse.setEnabled(false);
	btnModelBrowse.setText("Browse...");
	btnModelBrowse.setToolTipText("Choose a file to be uploaded.");
	FormData fd_btnModelBrowse = new FormData();
	fd_btnModelBrowse.top = new FormAttachment(radioModelKeep, 4);
	fd_btnModelBrowse.left = new FormAttachment(75, BORDER_MARGIN);
	fd_btnModelBrowse.right = new FormAttachment(100, -BORDER_MARGIN);
	btnModelBrowse.setLayoutData(fd_btnModelBrowse);
	btnModelBrowse.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
	    }
	});

	controller.getSAD(id, new UpdateCallback());

	return container;
    }

    private File openUploadDialog() {
	FileDialog fileDialog = new FileDialog(getShell());
	fileDialog.setFilterExtensions(new String[] { "*.sad" });
	fileDialog.setFilterNames(new String[] { "*.sad" });
	fileDialog.setText("Please select a software architecture description file!");
	String selectedFile = fileDialog.open();
	if (selectedFile != null) {
	    System.out.println(selectedFile + " was selected.");
	    return new File(selectedFile);
	}
	return null;
    }

    private File selectFileDialog(String fileType, String presetfileName) {
	FileDialog fileDialog = new FileDialog(getShell(), SWT.SAVE);
	fileDialog.setText("Please enter a file name!");
	fileDialog.setFileName(presetfileName);
	fileDialog.setOverwrite(true);
	String selectedFile = fileDialog.open();
	if (selectedFile != null) {
	    return new File(selectedFile);
	}
	return null;
    }

    private File openDownloadDialog(String basePath) {
	DirectoryDialog directoryDialog = new DirectoryDialog(getShell());
	directoryDialog.setText("Please select a download folder!‚");
	String selectedDirectory = directoryDialog.open();
	if (selectedDirectory != null) {
	    System.out.println(selectedDirectory + " was selected.");
	    return new File(selectedDirectory + "/" + basePath);
	}
	return null;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	Button button = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	button.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
	    }
	});
	button = createButton(parent, IDialogConstants.FINISH_ID, IDialogConstants.FINISH_LABEL, false);
	button.addSelectionListener(new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		if (true) {
		    sad.setName(txtName.getText());
		    sad.setType(txtType.getText());
		    sad.setAbstrct(txtAbstract.getText());
		}
		File modelFile = null;
		if (radioModelUpload.getSelection())
		    modelFile = new File(txtModelLocation.getText());

		controller.storeSAD(true, sad, radioModelDelete.getSelection(), modelFile, false, null,
			new UpdateCallback());
	    }
	});
    }

    public class UpdateCallback implements Callback<SAD> {
	public void set(Future<SAD> future) {
	    try {
		System.out.println("Updating SAD");
		SADDialog.this.updateSAD(future.get());
	    } catch (Exception e) {
		e.printStackTrace();
		IconAndMessageDialogs.showErrorDialog(getShell(), "There was a problem updating the view: " + e);
	    }
	}
    }

    public class ProgressMonitor extends NullProgressMonitor {

	@Override
	public void beginTask(String name, int totalWork) {
	    System.out.println("BEGINNING TASK: " + name + " with total work " + totalWork);
	}

	@Override
	public void worked(int work) {
	    System.out.println("Worked: " + work);
	}

	@Override
	public void done() {
	    System.out.println("Done.");
	}

    }

    public class ProgressBarMonitor extends NullProgressMonitor {

	private final ProgressBar progressBar;

	public ProgressBarMonitor(ProgressBar progressBar) {
	    this.progressBar = progressBar;
	}

	@Override
	public void beginTask(final String name, final int totalWork) {
	    progressBar.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    progressBar.setMaximum(totalWork);
		    // progressBar.setVisible(true);
		    System.out.println("BEGINNING TASK: " + name + " with total work " + totalWork);
		}
	    });

	}

	@Override
	public void worked(final int work) {
	    progressBar.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    if (progressBar.isDisposed())
			return;
		    final int sel = progressBar.getSelection();
		    progressBar.setSelection(sel + work);
		    System.out.println("Accum work: " + progressBar.getSelection());
		}
	    });
	}

	@Override
	public void done() {
	    progressBar.getDisplay().asyncExec(new Runnable() {
		@Override
		public void run() {
		    // progressBar.setVisible(false);
		    System.out.println("Done.");
		}
	    });
	}

    }
}

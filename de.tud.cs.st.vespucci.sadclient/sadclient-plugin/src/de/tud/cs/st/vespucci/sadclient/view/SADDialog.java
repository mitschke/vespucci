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

    private StyledText txtModel;
    private Button btnModelDelete;
    private Button btnModelUpload;
    private Button btnModelDownload;
    private ProgressBar progressBar;
    private ProgressBar progressBar_1;

    private StyledText txtDoc;
    private Button btnDocDelete;
    private Button btnDocUpload;
    private Button btnDocDownload;
    private Button btnSave;

    public SADDialog(Viewer parent, String id) {
	super(parent.getControl().getShell());
	this.parentViewer = parent;
	this.id = id;
    }

    public void updateSAD(final SAD sad) {

	container.getDisplay().syncExec(new Runnable() {

	    public void run() {

		txtName.setText(sad.getName());
		txtType.setText(sad.getType());
		txtAbstract.setText(sad.getAbstrct());
		SAD.Model model = sad.getModel();

		if (model != null) {
		    txtModel.setText("" + model.getSize() + "b");
		    btnModelDelete.setEnabled(true);
		    btnModelDownload.setEnabled(true);
		} else {
		    txtModel.setText("None");
		    btnModelDelete.setEnabled(false);
		    btnModelDownload.setEnabled(false);
		}

		SAD.Documentation documentation = sad.getDocumentation();
		if (documentation != null) {
		    txtDoc.setText("" + documentation.getSize() + "b");
		    btnDocDelete.setEnabled(true);
		    btnDocDownload.setEnabled(true);
		} else {
		    txtDoc.setText("None");
		    btnDocDelete.setEnabled(false);
		    btnDocDownload.setEnabled(false);
		}
		System.out.println("SAD updated");
	    }
	});
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		btnSave.setEnabled(false);
		parentViewer.refresh();
	    }
	});
    }

    public void updateDescription(final SAD sad) {
	container.getDisplay().syncExec(new Runnable() {
	    public void run() {
		txtName.setText(sad.getName());
		txtType.setText(sad.getType());
		txtAbstract.setText(sad.getAbstrct());
		btnSave.setEnabled(false);
	    }
	});
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		btnSave.setEnabled(false);
		parentViewer.refresh();
	    }
	});
    }

    public void updateModel(final SAD sad) {
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		SAD.Model model;
		model = sad.getModel();
		if (model != null) {
		    txtModel.setText("" + model.getSize() + "b");
		    btnModelDelete.setEnabled(true);
		    btnModelDownload.setEnabled(true);
		} else {
		    txtModel.setText("None");
		    btnModelDelete.setEnabled(false);
		    btnModelDownload.setEnabled(false);
		}
		parentViewer.refresh();
	    }
	});
    }

    public void updateDocumentation(final SAD sad) {
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		SAD.Documentation documentation;
		documentation = sad.getDocumentation();
		if (documentation != null) {
		    txtDoc.setText("" + documentation.getSize() + "b");
		    btnDocDelete.setEnabled(true);
		    btnDocDownload.setEnabled(true);
		} else {
		    txtDoc.setText("None");
		    btnDocDelete.setEnabled(false);
		    btnDocDownload.setEnabled(false);
		}
		parentViewer.refresh();
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
	 * When one of the text fields of the description is changed, the
	 * save-mode is enabled.
	 */
	final class DescriptionModifyListener implements ModifyListener {
	    public void modifyText(ModifyEvent event) {
		container.getDisplay().syncExec(new Runnable() {
		    public void run() {
			System.out.println("Text modified.");
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
		controller.saveDescription(id, txtName.getText(), txtType.getText(), txtAbstract.getText(),
			new UpdateCallback());
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
	txtModel.setToolTipText("Shows if a model was uploaded or not.");
	txtModel.setLeftMargin(2);
	txtModel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	txtModel.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
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
		controller.deleteModel(id, new UpdateModelCallback());
	    }
	});

	btnModelUpload = new Button(grpModel, SWT.NONE);
	btnModelUpload.setText("Upload");
	btnModelUpload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnModelUpload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		controller.uploadModel(id, openUploadDialog(), new UpdateModelCallback(), new ProgressBarMonitor(
			progressBar_1));
	    }
	});

	btnModelDownload = new Button(grpModel, SWT.NONE);
	btnModelDownload.setText("Download");
	btnModelDownload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnModelDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		controller.downloadModel(id, openDownloadDialog(id), new ProgressBarMonitor(progressBar_1));
	    }
	});

	progressBar = new ProgressBar(container, SWT.SMOOTH);
	FormData fd_progressBar = new FormData();
	fd_progressBar.bottom = new FormAttachment(76, 20);
	fd_progressBar.top = new FormAttachment(76, 0);
	fd_progressBar.left = new FormAttachment(grpDescription, BORDER_MARGIN, SWT.LEFT);

	progressBar_1 = new ProgressBar(grpDescription, SWT.NONE);
	FormData fd_progressBar_1 = new FormData();
	fd_progressBar_1.top = new FormAttachment(btnSave, 0, SWT.TOP);
	fd_progressBar_1.left = new FormAttachment(txtName, 0, SWT.LEFT);
	progressBar_1.setLayoutData(fd_progressBar_1);
	fd_progressBar.right = new FormAttachment(25, BORDER_MARGIN);
	progressBar.setLayoutData(fd_progressBar);
	progressBar.setVisible(true);

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
	txtDoc.setToolTipText("Shows if a documentation was uploaded or not.");
	txtDoc.setLeftMargin(2);
	txtDoc.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
	txtDoc.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
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
		controller.deleteDocumentation(id, new UpdateDocumentationCallback());
	    }
	});

	btnDocUpload = new Button(grpDocumentation, SWT.NONE);
	btnDocUpload.setText("Upload");
	btnDocUpload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnDocUpload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		btnDocUpload.setText("Cancel");
		controller.uploadDocumentation(id, openUploadDialog(), new UpdateDocumentationCallback(),
			new ProgressMonitor());
	    }
	});

	btnDocDownload = new Button(grpDocumentation, SWT.NONE);
	btnDocDownload.setText("Download");
	btnDocDownload.setLayoutData(new GridData(GridData.CENTER | GridData.FILL_HORIZONTAL));
	btnDocDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		controller.downloadDocumentation(id, openDownloadDialog(id), new ProgressMonitor());
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

    private File openDownloadDialog(String basePath) {
	DirectoryDialog directoryDialog = new DirectoryDialog(getShell());
	directoryDialog.setText("Please select a download folder!‚");
	String selectedFile = directoryDialog.open();
	if (selectedFile != null) {
	    System.out.println(selectedFile + " was selected.");
	    return new File(selectedFile + "/" + basePath);
	}
	return null;
    }

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CLOSE_LABEL, false);
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

    public class UpdateDescriptionCallback implements Callback<SAD> {
	public void set(Future<SAD> future) {
	    try {
		SADDialog.this.updateDescription(future.get());
	    } catch (Exception e) {
		e.printStackTrace();
		IconAndMessageDialogs.showErrorDialog(getShell(), "There was a problem updating the view: " + e);
	    }
	}
    }

    public class UpdateModelCallback implements Callback<SAD> {
	public void set(Future<SAD> future) {
	    try {
		SADDialog.this.updateModel(future.get());
	    } catch (Exception e) {
		e.printStackTrace();
		IconAndMessageDialogs.showErrorDialog(getShell(), "There was a problem updating the view: " + e);
	    }
	}
    }

    public class UpdateDocumentationCallback implements Callback<SAD> {
	public void set(Future<SAD> future) {
	    try {
		SADDialog.this.updateDocumentation(future.get());
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
//		    progressBar.setVisible(true);
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
//		    progressBar.setVisible(false);
		    System.out.println("Done.");
		}
	    });
	}

    }
}

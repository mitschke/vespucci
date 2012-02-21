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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * Dialog for managing SADs.
 * 
 * @author Mateusz Parzonka
 * 
 */
@SuppressWarnings("unused")
public class SADDialog extends Dialog {

    private final Viewer parentViewer;

    protected Object result;
    private Composite container;
    final private Controller controller = Controller.getInstance();

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

    private Text txtDocumentationLocation;
    private Button btnDocumentationBrowse;
    private Button btnDocumentationDownload;
    private ProgressBar progressDocumentation;
    private Button radioDocumentationKeep;
    private Button radioDocumentationUpload;
    private Button radioDocumentationDelete;

    private StyledText txtDoc;
    private Button btnDocDelete;
    private Button btnDocDownload;
    private boolean textChanged;
    private Composite composite;

    final int BORDER_MARGIN = 10;
    final int GROUP_MARGIN = 5;
    final int LEFT_TAB = 15;
    final int RIGHT_TAB = 80;
    final int LINE_MARGIN = 12;

    final int DESCRIPTION_SPACE = 50;
    final int MODEL_SPACE = 75;
    final int DOCUMENTATION_SPACE = 100;

    private final SAD sad;

    public SADDialog(Viewer parent, SAD sad) {
	super(parent.getControl().getShell());
	this.parentViewer = parent;
	this.sad = sad;
    }

    @Override
    protected Point getInitialSize() {
	return new Point(650, 570);
    }

    public void updateSAD() {

	container.getDisplay().syncExec(new Runnable() {

	    public void run() {

		txtName.setText(sad.getName());
		txtType.setText(sad.getType());
		txtAbstract.setText(sad.getAbstrct());
		SAD.Model model = sad.getModel();

		if (model != null) {
		    radioModelKeep.setText("Keep existing (Currently '" + model.getName() + "')");
		    btnModelDownload.setEnabled(true);
		} else {
		    radioModelKeep.setText("Keep existing (Nothing uploaded)");
		    btnModelDownload.setEnabled(false);
		}

		SAD.Documentation documentation = sad.getDocumentation();
		if (documentation != null) {
		    radioDocumentationKeep.setText("Keep existing (Currently '" + documentation.getName() + "')");
		    btnDocumentationDownload.setEnabled(true);
		} else {
		    radioDocumentationKeep.setText("Keep existing (Nothing uploaded)");
		    btnDocumentationDownload.setEnabled(false);
		}

	    }
	});
	container.getDisplay().asyncExec(new Runnable() {
	    public void run() {
		parentViewer.refresh();
	    }
	});
    }

    @Override
    protected Control createDialogArea(Composite parent) {

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

	Label lblName = new Label(grpDescription, SWT.NONE);
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

	Label lblType = new Label(grpDescription, SWT.NONE);
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

	Label lblAbstract = new Label(grpDescription, SWT.NONE);
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

	// Model //
	Group grpModel = createGroup("Model", grpDescription, MODEL_SPACE);

	radioModelKeep = createRadioKeep(grpModel);
	btnModelDownload = createDownloadButton(grpModel, new Runnable() {
	    @Override
	    public void run() {
		File downloadLocation = selectFileDialog("sad", sad.getModel().getName());
		if (downloadLocation != null)
		    controller.downloadModel(sad.getId(), downloadLocation);
	    }
	});

	radioModelUpload = createRadioUpload(grpModel, radioModelKeep);
	txtModelLocation = createLocationText(grpModel, radioModelKeep);
	btnModelBrowse = createBrowseButton(grpModel, radioModelKeep, "sad", txtModelLocation);

	radioModelDelete = createDeleteRadio(grpModel, radioModelUpload);

	// Documenation //

	Group grpDocumentation = createGroup("Documentation", grpModel, 100);

	radioDocumentationKeep = createRadioKeep(grpDocumentation);
	btnDocumentationDownload = createDownloadButton(grpDocumentation, new Runnable() {
	    @Override
	    public void run() {
		File downloadLocation = selectFileDialog("pdf", sad.getDocumentation().getName());
		if (downloadLocation != null)
		    controller.downloadDocumentation(sad.getId(), downloadLocation);
	    }
	});

	radioDocumentationUpload = createRadioUpload(grpDocumentation, radioDocumentationKeep);
	txtDocumentationLocation = createLocationText(grpDocumentation, radioDocumentationKeep);
	btnDocumentationBrowse = createBrowseButton(grpDocumentation, radioDocumentationKeep, "pdf",
		txtDocumentationLocation);

	radioDocumentationDelete = createDeleteRadio(grpDocumentation, radioDocumentationUpload);

	updateSAD();

	return container;
    }

    /**
     * Creates a group using the topmost container as parent.
     * 
     * @param groupName
     * @param compositeAtTop
     * @param bottomMargin
     * @return
     */
    private Group createGroup(String groupName, Composite compositeAtTop, int bottomMargin) {
	Group group = new Group(container, SWT.NONE);
	group.setText(groupName);
	group.setLayout(new FormLayout());
	FormData formData = new FormData();
	formData.top = new FormAttachment(compositeAtTop, BORDER_MARGIN);
	formData.left = new FormAttachment(0, BORDER_MARGIN);
	formData.right = new FormAttachment(100, -BORDER_MARGIN);
	formData.bottom = new FormAttachment(bottomMargin);
	group.setLayoutData(formData);
	return group;
    }

    private Text createLocationText(Group parent, Control controlAtTop) {
	Text txtModelLocation = new Text(parent, SWT.BORDER);
	FormData formData = new FormData();
	formData.top = new FormAttachment(controlAtTop, 8);
	formData.left = new FormAttachment(28);
	formData.right = new FormAttachment(RIGHT_TAB);
	txtModelLocation.setLayoutData(formData);
	return txtModelLocation;
    }

    private Button createBrowseButton(Group parent, Control controlAtTop, final String fileType, final Text textForPath) {
	Button button = new Button(parent, SWT.NONE);
	button.setEnabled(true);
	button.setText("Browse...");
	button.setToolTipText("Choose a file to be uploaded.");
	FormData formData = new FormData();
	formData.top = new FormAttachment(controlAtTop, 4);
	formData.left = new FormAttachment(RIGHT_TAB, BORDER_MARGIN);
	formData.right = new FormAttachment(100, -BORDER_MARGIN);
	button.setLayoutData(formData);
	button.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		container.getDisplay().asyncExec(new Runnable() {
		    @Override
		    public void run() {
			File uploadLocation = openUploadDialog(fileType);
			if (uploadLocation != null)
			    textForPath.setText(uploadLocation.getAbsolutePath());
		    }
		});
	    }
	});
	return button;
    }

    private Button createDeleteRadio(Group parent, Control controlAtTop) {
	FormData formData;
	Button radioModelDelete = new Button(parent, SWT.RADIO);
	formData = new FormData();
	formData.top = new FormAttachment(controlAtTop, LINE_MARGIN);
	formData.left = new FormAttachment(0, BORDER_MARGIN);
	radioModelDelete.setLayoutData(formData);
	radioModelDelete.setText("Delete existing");
	return radioModelDelete;
    }

    private Button createRadioUpload(Group parent, Control controlAtTop) {
	FormData formData;
	Button radioModelUpload = new Button(parent, SWT.RADIO);
	formData = new FormData();
	formData.top = new FormAttachment(controlAtTop, BORDER_MARGIN);
	formData.left = new FormAttachment(0, BORDER_MARGIN);
	radioModelUpload.setLayoutData(formData);
	radioModelUpload.setText("Upload new file: ");
	return radioModelUpload;
    }

    private Button createDownloadButton(Group parent, final Runnable runnable) {
	Button btnModelDownload = new Button(parent, SWT.NONE);
	btnModelDownload.setText("Download");
	btnModelDownload.setToolTipText("Downloads the file to disk.");
	FormData fd_btnModelDownload = new FormData();
	fd_btnModelDownload.top = new FormAttachment(0, 6);
	fd_btnModelDownload.left = new FormAttachment(RIGHT_TAB, BORDER_MARGIN);
	fd_btnModelDownload.right = new FormAttachment(100, -BORDER_MARGIN);
	btnModelDownload.setLayoutData(fd_btnModelDownload);
	btnModelDownload.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseUp(MouseEvent e) {
		container.getDisplay().asyncExec(runnable);
	    }
	});
	return btnModelDownload;
    }

    private Button createRadioKeep(Group parent) {
	Button radioButton = new Button(parent, SWT.RADIO);
	radioButton.setSelection(true);
	FormData formData = new FormData();
	formData.top = new FormAttachment(0, BORDER_MARGIN);
	formData.left = new FormAttachment(0, BORDER_MARGIN);
	radioButton.setLayoutData(formData);
	radioButton
		.setText("Keep existing (currently '                                                                             ')");
	return radioButton;
    }

    private File openUploadDialog(String fileType) {
	FileDialog fileDialog = new FileDialog(getShell());
	fileDialog.setFilterExtensions(new String[] { "*." + fileType });
	fileDialog.setFilterNames(new String[] { "*." + fileType });
	fileDialog.setText("Please select a " + fileType + "-file to upload!");
	String selectedFile = fileDialog.open();
	if (selectedFile != null) {
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

    @Override
    protected void createButtonsForButtonBar(Composite parent) {
	Button button = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	button = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }

    @Override
    protected void okPressed() {
	if (textChanged) {
	    sad.setName(txtName.getText());
	    sad.setType(txtType.getText());
	    sad.setAbstrct(txtAbstract.getText());
	    sad.setWip(false); // TODO
	}
	File modelFile = null;
	if (radioModelUpload.getSelection())
	    modelFile = new File(txtModelLocation.getText());
	File documentationFile = null;
	if (radioDocumentationUpload.getSelection())
	    documentationFile = new File(txtDocumentationLocation.getText());

	controller.storeSAD(true, sad, radioModelDelete.getSelection(), modelFile,
		radioDocumentationDelete.getSelection(), documentationFile, parentViewer);
	super.okPressed();
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

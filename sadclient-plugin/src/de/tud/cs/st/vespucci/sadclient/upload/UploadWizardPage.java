package de.tud.cs.st.vespucci.sadclient.upload;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class UploadWizardPage extends WizardPage {

	private Text text1;
	private Composite container;

	public UploadWizardPage() {
		super("First Page");
		setTitle("First Page2");
		setDescription("This wizard does not really do anything. But this is the first page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
	}

	public String getText1() {
		return text1.getText();
	}
}

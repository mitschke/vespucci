package de.tud.cs.st.vespucci.sadclient.upload;

import org.eclipse.jface.wizard.Wizard;

public class UploadWizard extends Wizard {

	private UploadWizardPage one;

	public UploadWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		one = new UploadWizardPage();
		addPage(one);
	}

	@Override
	public boolean performFinish() {

		// just put the result to the console, imagine here much more
		// intelligent stuff.
		System.out.println(one.getText1());

		return true;
	}
}

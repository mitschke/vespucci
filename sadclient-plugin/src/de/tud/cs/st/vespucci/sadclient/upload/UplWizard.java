package de.tud.cs.st.vespucci.sadclient.upload;

import org.eclipse.jface.wizard.Wizard;

public class UplWizard extends Wizard {

	public UplWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}

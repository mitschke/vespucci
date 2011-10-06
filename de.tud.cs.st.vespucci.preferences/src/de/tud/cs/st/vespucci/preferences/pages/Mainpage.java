package de.tud.cs.st.vespucci.preferences.pages;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class Mainpage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public Mainpage() {
		super(GRID);
		setDescription("Vespucci Preferences");
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void createFieldEditors() {
		
	}

}

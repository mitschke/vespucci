package de.tud.cs.st.vespucci.vespucci_model.diagram.preferences;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SaveActions extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public SaveActions() {
		super(GRID);
		setPreferenceStore(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance()
				.getPreferenceStore());
		setDescription("Which Plug-Ins should be executed on save?");
		
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		

		final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.saveActions";

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		
		for (IConfigurationElement i : configurationElement) {

			addField(new BooleanFieldEditor(generateId(i),
					i.getAttribute("Label"), getFieldEditorParent()));
		}
		
		
	}

	private String generateId(IConfigurationElement i) {
		return "saveBooleanOption" + i.getAttribute("id");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}

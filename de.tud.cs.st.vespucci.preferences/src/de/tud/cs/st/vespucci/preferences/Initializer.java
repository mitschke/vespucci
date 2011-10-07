package de.tud.cs.st.vespucci.preferences;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class Initializer extends AbstractPreferenceInitializer {

	public Initializer() {
	}

	@Override
	public void initializeDefaultPreferences() {

		final String EXTENSIONPOINT_ID = "de.tud.cs.st.vespucci.diagram.saveActions";

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		
		IPreferenceStore store = Preferences.getDefault().getPreferenceStore();
		
		for (IConfigurationElement i : configurationElement) {
			store.setDefault(generateId(i), true);
		}
	}
	

	private String generateId(IConfigurationElement i) {
		return "saveBooleanOption" + i.getAttribute("id");
	}

}

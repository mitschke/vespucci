package de.tud.cs.st.Lyrebird.recorder.preferences;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import de.tud.cs.st.Lyrebird.recorder.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_SAVE_PAIR_PROJECT, true);
		store.setDefault(PreferenceConstants.P_PROJECT_RELATIV_PATH, "Outputdir_" + Activator.PLUGIN_ID);
		store.setDefault(PreferenceConstants.P_ABSOLUTE_PATH, "");
	}

}

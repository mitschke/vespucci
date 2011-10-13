/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
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
 */
package de.tud.cs.st.vespucci.vespucci_model.diagram.preferences;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 *
 * @generated NOT
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {
	
	/**
	 * @author Patrick Gottschämmer
	 * @author Olav Lenz
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		de.tud.cs.st.vespucci.vespucci_model.diagram.preferences.DiagramGeneralPreferencePage.initDefaults(store);
		de.tud.cs.st.vespucci.vespucci_model.diagram.preferences.DiagramAppearancePreferencePage.initDefaults(store);
		de.tud.cs.st.vespucci.vespucci_model.diagram.preferences.DiagramConnectionsPreferencePage.initDefaults(store);
		de.tud.cs.st.vespucci.vespucci_model.diagram.preferences.DiagramPrintingPreferencePage.initDefaults(store);
		de.tud.cs.st.vespucci.vespucci_model.diagram.preferences.DiagramRulersAndGridPreferencePage.initDefaults(store);

		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();

		IConfigurationElement[] configurationElement = extensionRegistry
				.getConfigurationElementsFor("de.tud.cs.st.vespucci.diagram.modelSaveActions");
		
		for (IConfigurationElement i : configurationElement) {
			store.setDefault(generateId(i), true);
		}
		
	}
	
	private String generateId(IConfigurationElement i) {
		return "saveBooleanOption" + i.getAttribute("id");
	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.getInstance().getPreferenceStore();
	}
}

/* License (BSD Style License):
 * Copyright (c) 2011
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Technology Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.Lyrebird.recorder.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import de.tud.cs.st.Lyrebird.recorder.Activator;

/**
 * Preference page for the lyrebird recorder
 * @author Malte V
 */
public class PreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {

	private StringFieldEditor prefFoldername;
	private DirectoryFieldEditor prefPath;
	
	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Preference page for " + Activator.PLUGIN_ID);
	}
	
	/**
	 * all property fields
	 */
	public void createFieldEditors() {
		
		addField(
				new BooleanFieldEditor(
					PreferenceConstants.P_SAVE_PER_PROJECT,
					"Save Lyrebird records into Eclipse projectfolder",
					getFieldEditorParent()));
		
		prefFoldername = new StringFieldEditor(PreferenceConstants.P_PROJECT_RELATIV_PATH, "Output foldername\n(Lyrebird needs it's own output folder):", getFieldEditorParent());
		
		addField(prefFoldername);
		prefPath = new DirectoryFieldEditor(PreferenceConstants.P_ABSOLUTE_PATH, 
				"Global outputpath: ", getFieldEditorParent());
		addField(prefPath);
		setEnable();
	}
	
	private void setEnable(){
		boolean saveperproject = Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SAVE_PER_PROJECT);
		prefPath.setEnabled(!saveperproject, getFieldEditorParent());
		prefFoldername.setEnabled(saveperproject, getFieldEditorParent());
	}
	 @Override
	public void propertyChange(PropertyChangeEvent event) {
		 //toggle the enable property of StringFieldEditor and DirectoryFieldEditor if the value of BooleanFieldEditor changes
		 if(event.getSource() instanceof org.eclipse.jface.preference.BooleanFieldEditor){
			 if(((BooleanFieldEditor) event.getSource()).getPreferenceName().equals(PreferenceConstants.P_SAVE_PER_PROJECT)){
				 if(event.getNewValue().equals(true)){
					 prefPath.setEnabled(false, getFieldEditorParent());
					 prefFoldername.setEnabled(true, getFieldEditorParent());
				 }else{
					 prefPath.setEnabled(true, getFieldEditorParent());
					 prefFoldername.setEnabled(false, getFieldEditorParent());
				 }
			 }
		 }
		super.propertyChange(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}
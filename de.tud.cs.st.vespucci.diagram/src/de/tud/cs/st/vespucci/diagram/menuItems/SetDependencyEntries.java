/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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

package de.tud.cs.st.vespucci.diagram.menuItems;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import de.tud.cs.st.vespucci.io.ValidDependenciesReader;

/**
 * This class provides the entries for the
 * "Edit Constraint"/"Set Dependency"-menu. For each entry in the validDependencies-textfile 
 * ({@link de.tud.cs.st.vespucci/resources/validDependencies.txt}) one menu-entry will be generated.
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 */
public class SetDependencyEntries extends CompoundContributionItem {
	
	/**
	 * Valid names for dependencies read from the resource-file.
	 */
	private static final String[] dependencies = new ValidDependenciesReader().getKeywords();
	
	/**
	 * Generated entries.
	 */
	private IContributionItem[] entries = null;

	@Override
	protected IContributionItem[] getContributionItems() {
		if(entries == null && dependencies.length != 0){
			// generate entries
			entries = new CommandContributionItem[dependencies.length];
			
			for(int i = 0; i < dependencies.length; i++){
				final String dependency = dependencies[i];
				
				final CommandContributionItemParameter contributionParameter = (new CommandContributionItemParameter(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
						"de.tud.cs.st.vespucci.diagram.menuItems.SetDependencyContribution_" + dependency, 
						"de.tud.cs.st.vespucci.diagram.setDependenciesCommand",
						CommandContributionItem.STYLE_CHECK));
				contributionParameter.label = dependency;
				
				entries[i] = new CommandContributionItem(contributionParameter);
			}
		}
		
		return entries.clone();
	}
}

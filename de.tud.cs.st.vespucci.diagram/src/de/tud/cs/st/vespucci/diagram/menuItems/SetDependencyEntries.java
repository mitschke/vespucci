package de.tud.cs.st.vespucci.diagram.menuItems;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
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
						"de.tud.cs.st.vespucci.diagram.menuItems.SetDependencyContribution" + dependency, 
						"de.tud.cs.st.vespucci.diagram.setDependenciesCommand",
						SWT.NONE));
				contributionParameter.label = dependency;
				
				entries[i] = new CommandContributionItem(contributionParameter);
			}
		}
		
		return entries.clone();
	}
}

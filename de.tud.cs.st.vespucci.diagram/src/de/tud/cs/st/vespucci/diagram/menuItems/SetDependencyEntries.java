package de.tud.cs.st.vespucci.diagram.menuItems;

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
 * ({@link de.tud.cs.st.vespucci/validDependencies.txt}) one menu-entry will be generated.
 * 
 * @author Alexander Weitzmann
 * @version 0.1
 */
public class SetDependencyEntries extends CompoundContributionItem {
	ValidDependenciesReader nameReader = new ValidDependenciesReader();

	@Override
	protected IContributionItem[] getContributionItems() {
		// TODO Auto-generated method stub
		final CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				"my.project.myCommandContributionItem", "my.project.myCommand",
				SWT.NONE);
		contributionParameter.label = "Dynamic Menu Item ";
		return new IContributionItem[] { new CommandContributionItem(
				contributionParameter) };
	}

}

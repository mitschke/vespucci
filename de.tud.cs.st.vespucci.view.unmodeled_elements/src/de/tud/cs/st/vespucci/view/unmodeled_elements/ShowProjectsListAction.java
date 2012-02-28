package de.tud.cs.st.vespucci.view.unmodeled_elements;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public class ShowProjectsListAction<T> extends Action implements
		IMenuCreator {

	private Menu menu;

	IProjectElementView<T> view;

	public ShowProjectsListAction(IProjectElementView<T> view) {
		super();
		this.view = view;
		setMenuCreator(this);
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
		}
		menu = null;
	}

	@Override
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		for (Iterator<IProject> iterator = view.getEntryKeys(); iterator
				.hasNext();) {
			IProject project = iterator.next();
			ShowProjectResultsAction<T> action = new ShowProjectResultsAction<T>(
					project, view);
			
			addActionToMenu(action, menu);
			if(view.displayedProject() != null && view.displayedProject().getName().equals(project.getName()))
				action.setChecked(true);
		}
		return menu;
	}

	protected void addActionToMenu(IAction action, Menu menu) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(menu, -1);
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

}

package de.tud.cs.st.vespucci.unmodeled_elements.view;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

public class ShowProjectResultsAction<T> extends Action {

	IProject project;

	IProjectElementView<T> view;

	public ShowProjectResultsAction(IProject project,
			IProjectElementView<T> view) {
		super(project.getName(), IAction.AS_CHECK_BOX);
		this.project = project;
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		view.displayProject(project);
	}

}

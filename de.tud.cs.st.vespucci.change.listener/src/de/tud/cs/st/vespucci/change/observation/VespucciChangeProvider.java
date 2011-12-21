package de.tud.cs.st.vespucci.change.observation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import de.tud.cs.st.vespucci.change.listener.Activator;

public class VespucciChangeProvider {

	public static VespucciChangeProvider getInstance() {
		return Activator.getDefault().getChangeProvider();
	}
	
	private Map<IProject, Set<IClassFileObserver>> classFileObservers = new HashMap<IProject, Set<IClassFileObserver>>();

	private Map<IProject, Set<IArchitectureObserver>> diagramObservers = new HashMap<IProject, Set<IArchitectureObserver>>();

	public void registerClassFileObserver(IProject project,
			IClassFileObserver observer) {
		Set<IClassFileObserver> projectObservers = null;
		if (classFileObservers.containsKey(project)) {
			projectObservers = classFileObservers.get(project);
		} else {
			projectObservers = new HashSet<IClassFileObserver>();
			classFileObservers.put(project, projectObservers);
		}
		projectObservers.add(observer);
	}

	public void unregisterClassFileObserver(IProject project,
			IClassFileObserver observer) {
		if (!classFileObservers.containsKey(project))
			return;

		Set<IClassFileObserver> projectObservers = classFileObservers
				.get(project);
		projectObservers.remove(observer);
		if (projectObservers.isEmpty()) {
			classFileObservers.remove(project);
		}

	}

	public void registerArchitectureObserver(IProject project,
			IArchitectureObserver observer) {
		Set<IArchitectureObserver> projectObservers = null;
		if (diagramObservers.containsKey(project)) {
			projectObservers = diagramObservers.get(project);
		} else {
			projectObservers = new HashSet<IArchitectureObserver>();
			diagramObservers.put(project, projectObservers);
		}
		projectObservers.add(observer);
	}

	public void unregisterArchitectureObserver(IProject project,
			IArchitectureObserver observer) {
		if (!diagramObservers.containsKey(project))
			return;

		Set<IArchitectureObserver> projectObservers = diagramObservers
				.get(project);
		projectObservers.remove(observer);
		if (projectObservers.isEmpty()) {
			diagramObservers.remove(project);
		}

	}

	public boolean hasObserver(IProject project) {
		return classFileObservers.containsKey(project);
	}

	protected void fireClassFileAdded(IProject project, IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;
		Set<IClassFileObserver> projectObservers = classFileObservers
				.get(project);
		for (IClassFileObserver observer : projectObservers) {
			observer.classFileAdded(resource);
		}
	}

	protected void fireClassFileRemoved(IProject project, IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;
		Set<IClassFileObserver> projectObservers = classFileObservers
				.get(project);
		for (IClassFileObserver observer : projectObservers) {
			observer.classFileRemoved(resource);
		}
	}

	protected void fireClassFileChanged(IProject project, IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;
		Set<IClassFileObserver> projectObservers = classFileObservers
				.get(project);
		for (IClassFileObserver observer : projectObservers) {
			observer.classFileChanged(resource);
		}
	}

	protected void fireArchitectureFileAdded(IProject project,
			IResource resource) {
		if (!diagramObservers.containsKey(project))
			return;
		Set<IArchitectureObserver> projectObservers = diagramObservers
				.get(project);
		for (IArchitectureObserver observer : projectObservers) {
			observer.architectureDiagramAdded(project);
		}
	}

	protected void fireArchitectureFileRemoved(IProject project,
			IResource resource) {
		if (!diagramObservers.containsKey(project))
			return;
		Set<IArchitectureObserver> projectObservers = diagramObservers
				.get(project);
		for (IArchitectureObserver observer : projectObservers) {
			observer.architectureDiagramRemoved(resource);
		}
	}

	protected void fireArchitectureFileChanged(IProject project,
			IResource resource) {
		if (!diagramObservers.containsKey(project))
			return;
		Set<IArchitectureObserver> projectObservers = diagramObservers
				.get(project);
		for (IArchitectureObserver observer : projectObservers) {
			observer.architectureDiagramChanged(resource);
		}

	}
}

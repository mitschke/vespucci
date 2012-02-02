package de.tud.cs.st.vespucci.change.observation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import de.tud.cs.st.vespucci.change.listener.Activator;

public class VespucciChangeProvider {

	public static VespucciChangeProvider getInstance() {
		return Activator.getDefault().getChangeProvider();
	}

	private Map<IProject, Set<IClassFileObserver>> classFileObservers = new HashMap<IProject, Set<IClassFileObserver>>();

	private Map<IProject, Set<IArchitectureObserver>> diagramObservers = new HashMap<IProject, Set<IArchitectureObserver>>();

	private IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace()
			.getRuleFactory();

	
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

	protected void fireClassFileAdded(final IProject project, final IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;
		
		WorkspaceJob job = new ClassFileWorkspaceJob("classfile added", project) {

			@Override
			protected void notifyObserver(IClassFileObserver observer) {
				observer.classFileAdded(resource);
			}
					
		};
		ISchedulingRule rule = ruleFactory.modifyRule(resource);
		job.setRule(rule);
		job.schedule();

	}

	protected void fireClassFileRemoved(final IProject project, final IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;
		
		WorkspaceJob job = new ClassFileWorkspaceJob("classfile removed", project) {

			@Override
			protected void notifyObserver(IClassFileObserver observer) {
				observer.classFileRemoved(resource);
			}
					
		};
		ISchedulingRule rule = ruleFactory.modifyRule(resource);
		job.setRule(rule);
		job.schedule();

		

	}

	protected void fireClassFileChanged(final IProject project,
			final IResource resource) {
		if (!classFileObservers.containsKey(project))
			return;

		WorkspaceJob job = new ClassFileWorkspaceJob("classfile changed", project) {

			@Override
			protected void notifyObserver(IClassFileObserver observer) {
				observer.classFileChanged(resource);
			}
					
		};
		ISchedulingRule rule = ruleFactory.modifyRule(resource);
		job.setRule(rule);
		job.schedule();

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
	
	
	private abstract class ClassFileWorkspaceJob extends WorkspaceJob {

		private final IProject project;
		
		public ClassFileWorkspaceJob(String name, IProject project) {
			super(name);
			this.project = project;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor)
				throws CoreException {
			monitor.beginTask(getName(), classFileObservers.size());
			Set<IClassFileObserver> projectObservers = classFileObservers
					.get(project);
			for (IClassFileObserver observer : projectObservers) {
				notifyObserver(observer);
				monitor.worked(1);
			}
			monitor.done();
			return Status.OK_STATUS;
		}
		
		protected abstract void notifyObserver(IClassFileObserver observer);
		
	}
}

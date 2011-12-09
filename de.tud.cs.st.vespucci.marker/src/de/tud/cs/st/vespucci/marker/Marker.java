package de.tud.cs.st.vespucci.marker;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.diagram.processing.Util;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;

public class Marker implements IResultProcessor {

	private static String PLUGIN_ID = "de.tud.cs.st.vespucci.mockreturnprocessor";
	
	private IProject project;
	private Set<IViolation> violations;
	
	private static Set<IMarker> markers = new HashSet<IMarker>();

	@Override
	public void processResult(Object object, IProject project) {
		this.project = project;
		this.violations = Util.adapt(object, IViolationReport.class).getViolations();
		if (violations != null){
			markCode();
		}
	}

	@Override
	public void cleanUp(){
		for (IMarker marker : markers){
			try {
				marker.delete();
			} catch (CoreException e) {
				final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
				StatusManager.getManager().handle(is, StatusManager.LOG);
			}
		}
	}
	
	@Override
	public boolean isInterested(Class<?> type) {
		return type.equals(IViolationReport.class);
	}

	private void markCode() {
		for (IViolation violation : violations) {
			String fileName = violation.getSourceElement().getSimpleClassName();
			String packageName = violation.getSourceElement().getPackageIdentifier();
			
			packageName = packageName.replace(".", System.getProperty("file.separator"));
			
			
			// TODO: "src" NOGO
			IFile file = project.getFile("src"+ System.getProperty("file.separator") + packageName  + System.getProperty("file.separator") + fileName + ".java");	
			
			addMarker(file, violation.getDescription(), violation.getSourceElement().getLineNumber(), IMarker.PRIORITY_HIGH);
		}
	}
	
	private void addMarker(IFile file, String message, int lineNumber, int severity) {
		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
			markers.add(marker);
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

}

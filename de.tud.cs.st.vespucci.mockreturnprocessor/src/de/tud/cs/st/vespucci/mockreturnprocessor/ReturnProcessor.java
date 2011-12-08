package de.tud.cs.st.vespucci.mockreturnprocessor;

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
import de.tud.cs.st.vespucci.information.interfaces.IViolation;
import de.tud.cs.st.vespucci.information.interfaces.IViolationReport;

public class ReturnProcessor implements IResultProcessor {

	private IProject project;
	private Set<IViolation> violations;

	@Override
	public void processResult(Object object, IProject project) {
		this.project = project;
		this.violations = Util.adapt(object, IViolationReport.class).getViolations();

		if (violations != null){
			mark();
		}
	}

	private void mark() {
		for (IViolation violation : violations) {
			String fileName = violation.getSourceElement().getClassName();
			String packageName = violation.getSourceElement().getPackageName();
			
			IFile file = project.getFile("src"+ System.getProperty("file.separator") + packageName  + System.getProperty("file.separator") + fileName + ".java");	
			addMarker(file, violation.getDescription(), violation.getSourceElement().getLineNumber(), IMarker.PRIORITY_HIGH);
		}
	}

	@Override
	public boolean isInterested(Class<?> type) {
		return type.equals(IViolationReport.class);
	}

	private void addMarker(IFile file, String message, int lineNumber,int severity) {

		try {
			IMarker marker = file.createMarker("org.eclipse.core.resources.problemmarker");
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
			marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
		}
		catch (CoreException e) {
			final IStatus is = new Status(IStatus.ERROR,"de.tud.cs.st.vespucci.mockreturnprocessor", e.getMessage(), e);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
	}

}

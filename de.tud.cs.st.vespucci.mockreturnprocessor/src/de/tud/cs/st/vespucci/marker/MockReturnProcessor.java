package de.tud.cs.st.vespucci.marker;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.processing.IResultProcessor;
import de.tud.cs.st.vespucci.interfaces.IViolation;
import de.tud.cs.st.vespucci.interfaces.IViolationReport;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;

public class MockReturnProcessor implements IResultProcessor {

	
	private IProject project;
	private Set<IViolation> violations;
	

	@Override
	public void processResult(Object object, IProject project) {
		
		
		this.violations = Util.adapt(object, IViolationReport.class).getViolations();
		
		for (IViolation violation : this.violations) {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("\n");
			sb.append("Violation Description: ");
			sb.append(violation.getDescription());
			sb.append("\n");
			sb.append("SourceEnsemble: ");
			sb.append(violation.getSourceEnsemble().getName());
			sb.append("\n");
			sb.append("TargetEnsemble: ");
			sb.append(violation.getTargetEnsemble().getName());
			sb.append("\n");
			sb.append("SourceCodeSourceElement: ");
			sb.append(violation.getSourceElement().getPackageIdentifier() + "/" + violation.getSourceElement().getSimpleClassName());
			sb.append("\n");
			sb.append("Type :" + violation.getSourceElement().getClass().toString() );

			sb.append("\n");
			sb.append("SourceCodeTargetElement: ");
			sb.append(violation.getTargetElement().getPackageIdentifier() + "/" + violation.getTargetElement().getSimpleClassName());
			sb.append("\n");
			sb.append("Type :" + violation.getTargetElement().getClass().toString());
			
			System.out.println(sb);
			
		}
	}

	@Override
	public void cleanUp(){
	}
	
	@Override
	public boolean isInterested(Class<?> resultClass) {
		return IViolationReport.class.equals(resultClass);
	}


}

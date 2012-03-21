package de.tud.cs.st.vespucci.diagram.supports;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;




public class VespucciCreationSAMHelper {

	

	public static URI createmodelURI(URI diagramURI) {
		
		URI uri = URI.createURI(ResourcesPlugin.getWorkspace().getRoot().getProject(diagramURI.segment(1)).getFullPath()+"/GlobalRepository.sam");

		return uri;

	}

}
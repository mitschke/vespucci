/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Technology Group Group or Technische
 *     Universität Darmstadt nor the names of its contributors may be used to
 *     endorse or promote products derived from this software without specific
 *     prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.processing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.model.output.spi.ArchitectureModel;
import de.tud.cs.st.vespucci.diagram.model.output.spi.ConversionUtils;
import de.tud.cs.st.vespucci.model.IArchitectureModel;
import de.tud.cs.st.vespucci.model.IConstraint;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.utilities.Util;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Shape;
import de.tud.cs.st.vespucci.vespucci_model.ShapesDiagram;

/**
 * 
 * @author Patrick Gottschämmer
 * @author Olav Lenz
 */
public class AdapterIFile implements IAdapterFactory {

	private static final String PLUGIN_ID = "de.tud.cs.st.vespucci.diagram";

	private static Class<?>[] adapterList = { ShapesDiagram.class,
			IArchitectureModel.class };

	@Override
	public Object getAdapter(Object adaptableObject,
			@SuppressWarnings("rawtypes") Class adapterType) {

		if (adapterType == ShapesDiagram.class) {

			return createDiagram((IFile) adaptableObject);

		} else if (adapterType == IArchitectureModel.class) {

			return createArchitectureModel((IFile) adaptableObject);
		}

		return null;
	}

	private static IArchitectureModel createArchitectureModel(IFile diagramFile) {

		ShapesDiagram diagram = Util.adapt(diagramFile, ShapesDiagram.class);

		if (diagram != null) {
			Set<IEnsemble> ensembles = new HashSet<IEnsemble>();

			for (Shape shape : diagram.getShapes()) {
				ensembles.add(ConversionUtils.createEnsemble(shape));

				for (Connection connection : shape.getTargetConnections()) {

					if (connection.getTarget().eIsProxy())
						createWarningForProxiesMarker(diagramFile, connection);

				}
			}
			Set<IConstraint> constraints = new HashSet<IConstraint>();
			for (IEnsemble ensemble : ensembles) {
				Set<IConstraint> ensembleConstraints = getConstraints(ensemble);
				constraints.addAll(ensembleConstraints);
			}
			return new ArchitectureModel(ensembles, constraints, diagramFile
					.getFullPath().makeAbsolute().toPortableString());
		}

		return null;
	}

	private static ShapesDiagram createDiagram(IFile diagramFile) {

		final XMIResourceImpl diagramResource = new XMIResourceImpl();

		IFileStore fileStore = FileBuffers.getFileStoreAtLocation(diagramFile
				.getFullPath());
		try {
			InputStream diagramStream = fileStore.openInputStream(EFS.NONE,
					null);
			diagramResource.load(diagramStream, new HashMap<Object, Object>());

			// Find the ShapesDiagram-EObject
			for (int i = 0; i < diagramResource.getContents().size(); i++) {
				if (diagramResource.getContents().get(i) instanceof ShapesDiagram) {
					final EObject eObject = diagramResource.getContents()
							.get(i);
					diagramStream.close();
					return (ShapesDiagram) eObject;
				}
			}

			throw new FileNotFoundException(
					"ShapesDiagram could not be found in Document.");

		} catch (FileNotFoundException e1) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
					e1.getMessage(), e1);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (IOException e1) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
					e1.getMessage(), e1);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (CoreException e1) {
			final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID,
					e1.getMessage(), e1);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}
		return null;
	}

	private static void createWarningForProxiesMarker(IFile file,
			Connection element) {

		if (!file.exists())
			return;

		if (ResourcesPlugin.getWorkspace().isTreeLocked())
			return;

		String elementId = element.eResource().getURIFragment(element);

		String location = EMFCoreUtil.getQualifiedName(element, true);

		IMarker marker = de.tud.cs.st.vespucci.vespucci_model.diagram.providers.VespucciMarkerNavigationProvider
				.addMarker(
						file,
						elementId,
						location,
						"Diagram contains proxy references. Proxies are omited from procesing",
						IStatus.WARNING);
		try {
			marker.setAttribute(IMarker.TRANSIENT, true);
		} catch (CoreException e) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().logError(
							"Failed to create validation marker", e); //$NON-NLS-1$
		}

	}

	@Override
	public Class<?>[] getAdapterList() {
		return AdapterIFile.adapterList;
	}

	/**
	 * Returns all constraints that start at this ensemble or any inner
	 * ensembles
	 * 
	 * @param ensemble
	 * @return
	 */
	private static Set<IConstraint> getConstraints(IEnsemble ensemble) {
		Set<IConstraint> constraints = new HashSet<IConstraint>();
		for (IConstraint constraint : ensemble.getTargetConnections()) {
			constraints.add(constraint);
		}
		for (IEnsemble inner : ensemble.getInnerEnsembles()) {
			constraints.addAll(getConstraints(inner));
		}
		return constraints;
	}
}

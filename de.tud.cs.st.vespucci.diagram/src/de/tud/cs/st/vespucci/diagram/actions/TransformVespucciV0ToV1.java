/**
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
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
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
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

package de.tud.cs.st.vespucci.diagram.actions;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelExtentContents;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.emf.util.*;
import org.eclipse.m2m.internal.qvt.oml.library.*;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.In;
import org.eclipse.m2m.internal.qvt.oml.runtime.generator.TransformationRunner.Out;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.QvtInterpretedTransformation;
import org.eclipse.m2m.internal.qvt.oml.runtime.project.TransformationUtil;
import org.eclipse.m2m.internal.qvt.oml.trace.*;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages;

/**
 * @author DominicS
 */
@SuppressWarnings("restriction")
public class TransformVespucciV0ToV1 implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;
	private URI fileURI;

	@Override
	public void run(IAction action) {
		// get the resources from the input URI
		final ResourceSet resourceSet = new ResourceSetImpl();
		Job job = new Job("Convert diagram " + fileURI.toString()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				// execute the task ...
				EObject source = getInput();
				URI modelTransfUri = null;
				if (source == null) {
					String title = Messages.VespucciTransformationNoFileTitle;
					String message = Messages.VespucciTransformationNoFileMessage;
					MessageDialog.openInformation(getShell(), title,
							NLS.bind(message, fileURI.toString()));

					return Status.CANCEL_STATUS;
				} else {
					modelTransfUri = URI
							.createURI("platform:/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.qvto"); //$NON-NLS-1$
					URI notationTransfUri = URI
							.createURI("platform:/de.tud.cs.st.vespucci/transformations/migrate_v0_to_v1.notation.qvto"); //$NON-NLS-1$
				}

				try {
					monitor.beginTask("converting...", 4);

					Resource inResource = resourceSet
							.getResource(fileURI, true);

					// create the inputs
					List<EObject> inObjects = inResource.getContents();
					ModelContent input_di2 = new ModelContent(inObjects);
					ModelContent[] inputs = new ModelContent[1];
					inputs[0] = input_di2;

					// setup the execution environment details -> context
					IContext mycontext = new Context();
					Trace trace = null;

					QvtInterpretedTransformation transformation = new QvtInterpretedTransformation(
							TransformationUtil.getQvtModule(modelTransfUri));
					In IntransformationRunner = new TransformationRunner.In(
							inputs, mycontext);
					Out OuttransformationRunner = new TransformationRunner.Out(
							null, null, null);

					monitor.worked(1);
					// running the transformation
					OuttransformationRunner = transformation
							.run(IntransformationRunner);

					// retrieve the outputs
					List<ModelExtentContents> outputs = OuttransformationRunner
							.getExtents();

					// retrieve the trace
					trace = OuttransformationRunner.getTrace();

					if (trace != null && outputs.size() == 2) {
						// processing the trace
						URI Uri_trace = fileURI.trimFileExtension()
								.appendFileExtension("trace");
						EList<TraceRecord> outObjects_trace = trace
								.getTraceRecords();
						Resource outResource_trace = resourceSet
								.createResource(Uri_trace);
						outResource_trace.getContents()
								.addAll(outObjects_trace);

						// processing the outputs
						ModelExtentContents output_notation = outputs.get(0);
						ModelExtentContents output_di = outputs.get(1);
						monitor.worked(1);

						URI Uri_notation = fileURI.trimFileExtension()
								.appendFileExtension("notation");
						URI Uri_di = fileURI.trimFileExtension()
								.appendFileExtension("di");

						// the output objects got captured in the output extent
						List<EObject> outObjects_notation = output_notation
								.getAllRootElements();
						List<EObject> outObjects_di = output_di
								.getAllRootElements();

						// Let's persist them using a resource for notation
						Resource outResource_notation = resourceSet
								.createResource(Uri_notation);
						outResource_notation.getContents().addAll(
								outObjects_notation);

						// let's persist them using a resource for di
						Resource outResource_di = resourceSet
								.createResource(Uri_di);
						outResource_di.getContents().addAll(outObjects_di);

						monitor.worked(1);

						try {
							outResource_notation.save(Collections.emptyMap());
							outResource_di.save(Collections.emptyMap());
							outResource_trace.save(Collections.emptyMap());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						monitor.worked(1);

						// remove trace file
						URIConverter uri = resourceSet.getURIConverter();
						uri.delete(Uri_trace, null);
					}
				} catch (final MdaException e) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							MessageDialog
									.openError(
											new Shell(),
											"Model transformation error during conversion",
											e.toString());
						}
					});
					e.printStackTrace();
				} catch (final IOException e) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(new Shell(),
									"IO error during conversion", e.toString());
						}
					});
					e.printStackTrace();
				} catch (final RuntimeException e) {
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(new Shell(),
									"Error during conversion", e.toString());
						}
					});
					e.printStackTrace();
				} finally {
					// when the transformation succeeds
					// 1- restore the content of Di2 file i.e., di2 namespace
					// PapyrusNamespace.restoreDi2Namespace(inAbsolutepath);
				}

				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private void handleError(Exception ex) {
		MessageDialog.openError(getShell(), "Transformation failed",
				MessageFormat.format(
						"{0}: {1}",
						ex.getClass().getSimpleName(),
						ex.getMessage() == null ? "no message" : ex
								.getMessage()));
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	private EObject getInput() {
		ResourceSetImpl rs = new ResourceSetImpl();
		return rs.getEObject(fileURI.appendFragment("/"), true);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		fileURI = null;
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false
				|| selection.isEmpty()) {
			return;
		}

		IFile file = (IFile) ((IStructuredSelection) selection)
				.getFirstElement();
		fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(),
				true);
		action.setEnabled(true);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

}

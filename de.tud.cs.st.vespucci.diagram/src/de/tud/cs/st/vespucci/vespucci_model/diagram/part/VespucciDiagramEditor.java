/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Tam-Minh Nguyen
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
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
package de.tud.cs.st.vespucci.vespucci_model.diagram.part;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TreeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.diagram.converter.DiagramConverter;
import de.tud.cs.st.vespucci.diagram.dnd.CreateEnsembleDropTargetListener;
import de.tud.cs.st.vespucci.diagram.dnd.DropVespucciDiagramTargetListener;
import de.tud.cs.st.vespucci.diagram.supports.EPService;
import de.tud.cs.st.vespucci.diagram.supports.VespucciMouseListener;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.Dummy;
import de.tud.cs.st.vespucci.vespucci_model.Ensemble;
import de.tud.cs.st.vespucci.vespucci_model.Expected;
import de.tud.cs.st.vespucci.vespucci_model.InAndOut;
import de.tud.cs.st.vespucci.vespucci_model.Incoming;
import de.tud.cs.st.vespucci.vespucci_model.Outgoing;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineDummyEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineEnsembleEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineExpectedSourceConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineExpectedTargetConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineInAndOutSourceConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineInAndOutTargetConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineIncomingSourceConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineIncomingTargetConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineNotAllowedSourceConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineNotAllowedTargetConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineOutgoingSourceConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineOutgoingTargetConnectionEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.outline.OutlineRootEditPart;

/**
 * @generated
 */
public class VespucciDiagramEditor extends DiagramDocumentEditor implements
		IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "de.tud.cs.st.vespucci.vespucci_model.diagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated NOT
	 */
	private SelectionSynchronizer synchronizer;

	/**
	 * @generated
	 */
	public VespucciDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @generated
	 */
	@Override
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciPaletteFactory()
				.fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	@Override
	protected PreferencesHint getPreferencesHint() {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	@Override
	public String getContributorId() {
		return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	@Override
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			return de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider()
				.getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated
	 */
	@Override
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			setDocumentProvider(de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin
					.getInstance().getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * Custom SelectionSynchronizer
	 * 
	 * @author a_vovk
	 * @generated NOT
	 */
	@Override
	protected SelectionSynchronizer getSelectionSynchronizer() {
		if (synchronizer == null)
			synchronizer = new VespucciSelectionSynchronizer();
		return synchronizer;
	}

	/**
	 * EditPartFactory for OutlineView
	 * 
	 * @author a_vovk
	 * @generated NOT
	 */
	@Override
	protected EditPartFactory getOutlineViewEditPartFactory() {
		return new EditPartFactory() {

			/**
			 * Creates EditParts for OutlineView
			 */
			public EditPart createEditPart(EditPart context, Object model) {
				if (model instanceof View) {
					// Diagram is a root edit part
					if (model instanceof Diagram) {
						return new OutlineRootEditPart(model);
					}
					View view = (View) model;
					EObject element = view.getElement();

					if (element instanceof Ensemble) {
						return new OutlineEnsembleEditPart(model);
					} else if (element instanceof Dummy) {
						return new OutlineDummyEditPart(model);
					} else if (element instanceof Connection) {
						Connection conn = (Connection) element;
						NodeImpl shape = (NodeImpl) context.getModel();
						if (shape.getElement() == conn.getSource()) {
							if (conn instanceof Incoming) {
								return new OutlineIncomingSourceConnectionEditPart(
										model);
							} else if (conn instanceof Outgoing) {
								return new OutlineOutgoingSourceConnectionEditPart(
										model);
							} else if (conn instanceof Expected) {
								return new OutlineExpectedSourceConnectionEditPart(
										model);
							} else if (conn instanceof InAndOut) {
								return new OutlineInAndOutSourceConnectionEditPart(
										model);
							} else {
								return new OutlineNotAllowedSourceConnectionEditPart(
										model);
							}

						} else {
							if (conn instanceof Incoming) {
								return new OutlineIncomingTargetConnectionEditPart(
										model);
							} else if (conn instanceof Outgoing) {
								return new OutlineOutgoingTargetConnectionEditPart(
										model);
							} else if (conn instanceof Expected) {
								return new OutlineExpectedTargetConnectionEditPart(
										model);
							} else if (conn instanceof InAndOut) {
								return new OutlineInAndOutTargetConnectionEditPart(
										model);
							} else {
								return new OutlineNotAllowedTargetConnectionEditPart(
										model);
							}
						}

					}
				}
				return new TreeEditPart(model);

			}
		};
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * New methods added to translate diagram to prolog facts.
	 * 
	 * @author Patrick Jahnke, MalteV
	 * @generated NOT
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);

		DiagramConverter converter = new DiagramConverter();
		try {
			converter.ConvertDiagramToProlog(this.getCurrentSelectedFilePath(),
					this.getCurrentSelectedFileName());
		} catch (FileNotFoundException e) {
			IStatus is = new Status(Status.ERROR,
					VespucciDiagramEditorPlugin.ID, "FileNotFoundException", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (IOException e) {
			IStatus is = new Status(Status.ERROR,
					VespucciDiagramEditorPlugin.ID,
					"Failed to save Prolog file", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		} catch (Exception e) {
			IStatus is = new Status(Status.ERROR,
					VespucciDiagramEditorPlugin.ID, "FileNotFoundException", e);
			StatusManager.getManager().handle(is, StatusManager.SHOW);
			StatusManager.getManager().handle(is, StatusManager.LOG);
		}

		// refresh Package View
		IProject activeProject = this.getSelectedFile().getFile().getProject();
		try {
			activeProject.refreshLocal(IResource.DEPTH_INFINITE,
					progressMonitor);
		} catch (CoreException e) {
			IStatus iStat = new Status(Status.ERROR,
					VespucciDiagramEditorPlugin.ID,
					"Failed to refresh package view");
			StatusManager.getManager().handle(iStat, StatusManager.SHOW);
			StatusManager.getManager().handle(iStat, StatusManager.LOG);
		}
		// this.saveDiagramInTextNonRecursive(progressMonitor);
		this.validateDiagramConstraints();

	}

	/**
	 * @generated
	 */
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
				.getFile() : null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS
					.bind(de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciDiagramEditor_SavingDeletedFile,
							original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
				.getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog
						.openWarning(
								shell,
								de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciDiagramEditor_SaveAsErrorTitle,
								de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor,
					newInput,
					getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog
						.openError(
								shell,
								de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciDiagramEditor_SaveErrorTitle,
								de.tud.cs.st.vespucci.vespucci_model.diagram.part.Messages.VespucciDiagramEditor_SaveErrorMessage,
								x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		if (diagram == null || diagram.eResource() == null) {
			return StructuredSelection.EMPTY;
		}
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem item = new de.tud.cs.st.vespucci.vespucci_model.diagram.navigator.VespucciNavigatorItem(
					diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		de.tud.cs.st.vespucci.vespucci_model.diagram.part.DiagramEditorContextMenuProvider provider = new de.tud.cs.st.vespucci.vespucci_model.diagram.part.DiagramEditorContextMenuProvider(
				this, getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
				provider, getDiagramGraphicalViewer());
	}

	/**
	 * @return selected file from editor.
	 * @author Tam-Minh Nguyen
	 */
	private IFileEditorInput getSelectedFile() {
		// get project PATH
		IFileEditorInput fileInput = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IEditorPart editor = page.getActiveEditor();
				if (editor != null) {
					IEditorInput input = editor.getEditorInput();
					if (input instanceof IFileEditorInput) {
						// GET PATH
						fileInput = (IFileEditorInput) input;
					}
				}
			}
		}
		return fileInput;
	}

	/**
	 * @return name of selected file.
	 * @author Tam-Minh Nguyen
	 */
	private String getCurrentSelectedFileName() {
		IFileEditorInput fileInput = this.getSelectedFile();
		if (fileInput == null) {
			// TODO Error handling
			return "";
		} else {
			return fileInput.getFile().getName();
		}
	}

	/**
	 * @return path of selected file, without file name.
	 * @author Tam-Minh Nguyen
	 */
	private String getCurrentSelectedFilePath() {
		IFileEditorInput fileInput = this.getSelectedFile();
		if (fileInput == null) {
			// TODO Error handling
			return "";
		} else {
			return fileInput.getFile().getParent().getLocation().toString();
		}
	}

	/**
	 * put a drop listener to the Vespucci diagram view
	 * 
	 * @author MalteV
	 */
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		// adds 2 TransferDropTargetListener to the diagram view. for handling DnD out of the Package Explorer
		getDiagramGraphicalViewer().addDropTargetListener(
				new DropVespucciDiagramTargetListener(
						getDiagramGraphicalViewer()));
		getDiagramGraphicalViewer().addDropTargetListener(
				new CreateEnsembleDropTargetListener(
						getDiagramGraphicalViewer()));
	}

	/**
	 * Temp connections will be colored in red.
	 * 
	 * @category hook point
	 * @generated NOT
	 * @author Tam-Minh Nguyen
	 */
	@Override
	protected void initializeGraphicalViewerContents() {
		super.initializeGraphicalViewerContents();

		// get all list of connections
		EditPart root = this.getDiagramGraphicalViewer().getRootEditPart();
		root = (EditPart) root.getChildren().get(0);

		// DiagramEditPart r; r.getFigure();
		// help to get postion of right mouse click
		VespucciMouseListener vml = new VespucciMouseListener();
		(((DiagramEditPart) root).getFigure()).addMouseListener(vml);

		List<EditPart> shapeList = EPService
				.getAllShapesInSideCompartment(root);
		Set<ConnectionEditPart> conSet = EPService
				.getAllConnectionsToAndFromShapeList(shapeList);

		// int idx = 1;
		for (Object ee : conSet) {
			//
			if (ee instanceof ConnectionEditPart) {
				ConnectionEditPart con = (ConnectionEditPart) ee;
				Connection ci = (Connection) con.resolveSemanticElement();

				//ci can be null if the connection belong to a note attachment.
				//so we need to handle it  
				if (ci == null)
					continue;

				if (ci.isTemp()) {
					// draw with RED
					con.getFigure().setForegroundColor(
							org.eclipse.draw2d.ColorConstants.red);
					con.getFigure().repaint();

				}
			}
		}
	}

	/**
	 * Call validation on Diagram constraints (unique ID, connection allowed or
	 * not).
	 * 
	 * @author Tam-Minh Nguyen
	 */
	private void validateDiagramConstraints() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IWorkbenchPart workbenchPart = page.getActivePart();
				if (workbenchPart instanceof IDiagramWorkbenchPart) {
					final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) workbenchPart;
					ValidateAction.runValidation(part.getDiagramEditPart(),
							part.getDiagram());
				}
			}
		}
	}

}
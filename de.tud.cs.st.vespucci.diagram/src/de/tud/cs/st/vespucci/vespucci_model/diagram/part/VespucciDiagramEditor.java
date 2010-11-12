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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.NoteEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
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

import de.tud.cs.st.vespucci.diagram.supports.EPService;
import de.tud.cs.st.vespucci.diagram.supports.VespucciMouseListener;
import de.tud.cs.st.vespucci.vespucci_model.Connection;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.Dummy2EditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.DummyEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.ExpectedEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.InAndOutEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.IncomingEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.NotAllowedEditPart;
import de.tud.cs.st.vespucci.vespucci_model.diagram.edit.parts.OutgoingEditPart;

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
	 * @author Tam-Minh Nguyen
	 * @generated NOT
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		this.saveDiagramInTextNonRecursive(progressMonitor);
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
	 * Prolog facts for ensembles and connections are saved with following form:
	 * <li>ensemble(name, query, sub-ensemble list). <li>dependency (id, source,
	 * target, type).
	 * 
	 * @author Tam-Minh Nguyen
	 */
	private void saveDiagramInTextNonRecursive(IProgressMonitor progressMonitor) {
		try {
			String pathOfSelectedFile = this.getCurrentSelectedFilePath();
			String currentSelectedFilename = this.getCurrentSelectedFileName();

			// TODO Error Handling...
			/*
			 * if (currentSelectedFilename.length() == 0) {
			 * System.err.println("No file seleted"); return; }
			 */

			// CREATE FILE
			File f = new File(pathOfSelectedFile + "/"
					+ currentSelectedFilename + ".pl");

			// the file will be overwritten
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			// write Author string
			bos.write(this.getStringSeperator().getBytes());
			bos.write(this.getStringHeader(currentSelectedFilename).getBytes());
			bos.write(this.getStringCurrentDate().getBytes());
			bos.write(this.getStringSeperator().getBytes());
			bos.write("\n".getBytes());

			// write Help predicates
			// bos.write(this.getHelpPredicatesString().getBytes());

			// TRANSLATE ENSEMBLE FACTS
			// visit all elements of the model to produce text-based diagram
			bos.write(this.getStringSeperator().getBytes());
			bos.write(this.getStringDefinitionEnsemble().getBytes());
			bos.write(this.getStringSeperator().getBytes());
			EditPart ep = getDiagramGraphicalViewer().getRootEditPart();
			ep = (EditPart) ep.getChildren().get(0);
			// get all elements of diagram (ensembles,notes,labels)
			List<EditPart> shapes = new ArrayList<EditPart>();
			for (Object o : ep.getChildren()) {
				if (o instanceof GraphicalEditPart) {
					GraphicalEditPart ePart = (GraphicalEditPart) o;
					shapes.add(ePart);
					//
					EditPart compartment = EPService
							.getCommpartmentEditPart(ePart);
					if (compartment != null) {
						shapes.addAll(EPService
								.getAllShapesInSideCompartment(compartment));
					}
				} else {
					System.err.println("What am I: " + o.toString());
				}
			}
			// encode each ensemble (the empty ensemble is encoded only once,
			// since all
			// instances are indistinguishable)
			boolean emptyEnsemble = false;
			for (EditPart elem : shapes) {
				// TODO Is Dummy2EditPart needed?
				if (elem instanceof DummyEditPart
						|| elem instanceof Dummy2EditPart) {
					if (!emptyEnsemble) {
						emptyEnsemble = true;
						bos.write((getEmptyEnsembleString() + "\n").getBytes());
					}
				} else {
					String s = this.translateEnsembleToPrologNonRecursive(elem);
					if (!s.equals("")) {
						bos.write((s + "\n").getBytes());
					}
				}
			}

			// TRANSLATE DEPENDENCY FACTS
			bos.write("\n".getBytes());
			bos.write(this.getStringSeperator().getBytes());
			bos.write(this.getStringDefinitionDependency().getBytes());
			bos.write(this.getStringSeperator().getBytes());

			List<EditPart> shapeList = EPService
					.getAllShapesInSideCompartment(ep);
			Set<ConnectionEditPart> conSet = EPService
					.getAllConnectionsToAndFromShapeList(shapeList);

			// iterate on each dependency to get its prolog facts.
			int idx = 1;
			for (Object ee : conSet) {
				if (ee instanceof ConnectionEditPart) {
					ConnectionEditPart con = (ConnectionEditPart) ee;
					Connection ci = (Connection) con.resolveSemanticElement();
					if (ci.isTemp() == false) {
						String s = this.translateConnectionToProlog(ee, idx++);
						//
						if (s.length() != 0) {
							bos.write(s.getBytes());
						}
					}
				}
			}

			bos.flush();
			fos.flush();
			bos.close();
			fos.close();

			// refresh Package View
			IProject activeProject = this.getSelectedFile().getFile()
					.getProject();
			activeProject.refreshLocal(IResource.DEPTH_INFINITE,
					progressMonitor);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private String getStringHeader(String filename) {
		StringBuilder s = new StringBuilder("");
		s.append("% Prolog based representation of the Vespucci architecture diagram: ");
		s.append(filename);
		// s.append("\n");
		s.append("% Created by Vespucci, Technische Universität Darmstadt, Department of Computer Science\n");
		s.append("% www.opal-project.de\n\n");
		// s.append("% ");
		s.append(":- multifile ensemble/5.\n");
		s.append(":- multifile abstract_ensemble/5.\n");
		// s.append("% ");
		s.append(":- multifile outgoing/7.\n");
		// s.append("% ");
		s.append(":- multifile incoming/7.\n");
		// s.append("% ");
		s.append(":- multifile not_allowed/7.\n");
		// s.append("% ");
		s.append(":- multifile expected/7.\n");
		// s.append("% ");
		s.append(":- discontiguous ensemble/5.\n");
		s.append(":- discontiguous abstract_ensemble/5.\n");
		// s.append("% ");
		s.append(":- discontiguous outgoing/7.\n");
		// s.append("% ");
		s.append(":- discontiguous incoming/7.\n");
		// s.append("% ");
		s.append(":- discontiguous not_allowed/7.\n");
		// s.append("% ");
		s.append(":- discontiguous expected/7.\n\n");

		return s.toString();
	}

	private String getStringCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return "% Date <" + dateFormat.format(date) + ">.\n";
	}

	private String getStringDefinitionEnsemble() {
		String s = "";
		s += "%ensemble(File, Name, Query, SubEnsembles) :- Definition of an ensemble.\n";
		s += "%\tFile - The simple file name in which the ensemble is defined. (e.g., 'Flashcards.sad')\n";
		s += "%\tName - Name of the ensemble\n";
		s += "%\tQuery - Query that determines which source elements belong to the ensemble\n";
		s += "%\tSubEnsembles - List of all sub ensembles of this ensemble.\n";
		return s;
	}

	private String getStringDefinitionDependency() {
		String s = "";
		s += "%DEPENDENCY(File, ID, SourceE, TargetE, Type) :- Definition of a dependency between two ensembles.\n";
		s += "%\tDEPENDENCY - The type of the dependency. Possible values: outgoing, incoming, expected, not_allowed\n";
		s += "%	File - The simple file name in which the dependency is defined. (e.g., 'Flashcards.sad')\n";
		s += "%\tID - An ID identifying the dependency\n";
		s += "%\tSourceE - The source ensemble\n";
		s += "%\tTargetE - The target ensemble\n";
		s += "%\tRelation classifier - Kinds of uses-relation between source and target ensemble (all, field_access, method_call,...)\n";
		return s;
	}

	private String getStringSeperator() {
		return "%------\n";
	}

	private String getEmptyEnsembleString() {
		return "ensemble('" + this.getCurrentSelectedFileName()
				+ "', 'empty', [], (empty), []).";
	}

	// private String getStringHelpPredicates() {
	// String s = "";
	// s += "% Predicate helps to access children of ensemble\n%---\n";
	// s += "sub_ensemble(E,SubE) :- ensemble(E,_,SubEs),member(SubE,SubEs).\n";
	// s +=
	// "sub_ensemble(E,SubE) :- ensemble(E,_,IEs),member(IE,IEs),sub_ensemble(IE,SubE).\n\n";
	// return s;
	// }

	/**
	 * Diagram elements (ensembles, connections) are translated to prolog.
	 * Ensemble contains their sub ensembles in non recursive way.
	 * 
	 * @return the prolog encoding of the ensemble (ensemble/4).
	 * @author Tam-Minh Nguyen
	 */
	private String translateEnsembleToPrologNonRecursive(Object o) {
		StringBuilder s = new StringBuilder("");

		if (o instanceof NoteEditPart) {
			NoteEditPart taep = (NoteEditPart) o;
			System.err.println(((org.eclipse.gmf.runtime.notation.Shape) taep
					.getModel()).getDescription());
			return s.toString();
		} else if (o instanceof ITextAwareEditPart) {
			// System.out.println(o);
			// TODO What is this...?
			return s.toString();
		}/*
		 * else if (o instanceof ShapeNodeEditPart && (((ShapeNodeEditPart)
		 * o).getModel()) instanceof org.eclipse.gmf.runtime.notation.Shape) {
		 * System.out.println(((org.eclipse.gmf.runtime.notation.Shape)
		 * ((ShapeNodeEditPart) o) .getModel()).getDescription()); // TODO What
		 * is this...? return s; }
		 */
		else if (o instanceof ShapeNodeEditPart) {
			// System.out.println(o);
			// ConcernEditPart || Concern2EditPart
			ShapeNodeEditPart node = (ShapeNodeEditPart) o;

			String filename = this.getCurrentSelectedFileName();
			if (isAbstractEnsemble(o))
				s.append("abstract_ensemble");
			else
				s.append("ensemble");
			s.append("(");
			s.append(getArchitectureDescriptor());
			s.append(", ");
			s.append(getEnsembleDescriptor(o));
			s.append(", ");
			s.append(getEnsembleParameters(o));
			s.append(", ");
			s.append(getEnsembleQuery(o));
			s.append(", ");
			EditPart comparment = EPService.getCommpartmentEditPart(node);
			if (comparment != null) {
				s.append(getEnsembleChildrenList(comparment.getChildren()));
			} else {
				s.append("[]");
			}
			s.append(").");
			return s.toString();
		}
		return s.toString();
	}

	/**
	 * 
	 * @return a quoted String that identifies this architecture definition
	 */
	private String getArchitectureDescriptor() {
		StringBuilder s = new StringBuilder("'");
		s.append(this.getCurrentSelectedFileName());
		s.append("'");
		return s.toString();
	}

	/**
	 * 
	 * @return the name of the ensemble. (Without parameters)
	 */
	private static String getEnsembleDescriptor(Object elem) {
		String text = EPService.getEditPartName(elem);
		StringBuilder s = new StringBuilder("'");
		if (text.indexOf('(') > 0) {
			s.append(text.subSequence(0, text.indexOf('(')));
		} else {
			s.append(text);
		}
		s.append("'");
		return s.toString();
	}

	/**
	 * @return a prolog list of the form ['ParamName'=ParamName, ...]
	 */
	private static String getEnsembleParameters(Object elem) {
		String[] parameters = getEnsembleParameterDefinitions(elem);
		if (parameters.length == 0)
			return "[]";
		StringBuilder s = new StringBuilder("[");
		s.append(getEncodedParameter(parameters[0]));
		for (int i = 1; i < parameters.length; i++) {
			s.append(", ");
			s.append(getEncodedParameter(parameters[i]));
		}
		s.append("]");
		return s.toString();
	}

	private static boolean isAbstractEnsemble(Object elem) {
		String[] parameters = getEnsembleParameterDefinitions(elem);
		for (int i = 0; i < parameters.length; i++) {
			if (parameterVariable.matcher(parameters[i]).matches())
				return true;
		}
		return false;
	}

	private static Pattern parameterVariable = Pattern.compile("\\p{Upper}.*");

	private static Pattern parameterNames = Pattern.compile("(.*?)=(.*)");

	private static String getEncodedParameter(String name) {
		StringBuilder s = new StringBuilder();
		if (parameterVariable.matcher(name).matches()) {
			s.append("'");
			s.append(name);
			s.append("'");
			s.append("=");
			s.append(name);
			return s.toString();
		}
		Matcher m = parameterNames.matcher(name);
		if (m.matches()) {
			s.append("'");
			s.append(m.group(1));
			s.append("'");
			s.append("=");
			s.append(m.group(2));
			return s.toString();
		}

		s.append("_");
		s.append("=");
		s.append(name);
		return s.toString();
	}


	private static Pattern parameterList = Pattern
			.compile(	"^.+?" + // match the descriptor
						"\\(" +  // match the first bracket
						"(.*)" + // match anything in between as group
						"\\)$"); // match the last parenthesis by asserting the string ends here
	
	/**
	 * TODO the user should be notified when the parenthesis form an illegal match. Currently everything is 'thrown' into the last parameter, which may result in illegal prolog 
	 * @param elem
	 * @return
	 */
	private static String[] getEnsembleParameterDefinitions(Object elem) {
		String text = EPService.getEditPartName(elem);
		Matcher m = parameterList.matcher(text);
		if (!m.matches())
			return new String[0];
		List<String> parameterDefinitions = new LinkedList<String>();
		String parameters = m.group(1);
		int start = 0;
		int matchParenthesis = 0;
		for (int i = 0; i < parameters.length(); i++) {
			if(parameters.charAt(i) == '(')
				matchParenthesis++;
			if(matchParenthesis > 0 && parameters.charAt(i) == ')')
				matchParenthesis--;
			if(parameters.charAt(i) == ',' && matchParenthesis == 0) {
				parameterDefinitions.add(parameters.substring(start, i).trim());
				start = i + 1;
			}
		}
		parameterDefinitions.add(parameters.substring(start, parameters.length()).trim());
		String[] result = new String[parameterDefinitions.size()];
		return parameterDefinitions.toArray(result);
	}

	/**
	 * @return the query of the ensemble
	 */
	private static String getEnsembleQuery(Object elem) {
		StringBuilder s = new StringBuilder("(");
		s.append(EPService.getEditPartQuery(elem));
		s.append(")");
		return s.toString();
	}

	/**
	 * @return the query of the ensemble
	 */
	private static String getEnsembleChildrenList(List children) {
		if (children.isEmpty())
			return "[]";

		StringBuilder s = new StringBuilder("[");
		if (children.size() >= 1) {
			s.append(getEnsembleDescriptor(children.get(0)));
		}
		if (children.size() >= 2) {
			for (Object child : children.subList(1, children.size())) {
				s.append(", ");
				s.append(getEnsembleDescriptor(child));
			}
		}
		s.append("]");
		return s.toString();
	}

	/**
	 * @param o
	 *            only ConnectionEditPart type will be translated to prolog.
	 * @return string contained prolog fact of connection editpart
	 *         (dependency/7.).
	 * @author Tam-Minh Nguyen
	 */
	private String translateConnectionToProlog(Object o, int idx) {

		if (o instanceof ConnectionEditPart) {
			ConnectionEditPart con = (ConnectionEditPart) o;

			EditPart source = con.getSource();
			EditPart target = con.getTarget();
			String source_name = getEnsembleDescriptor(source);
			String target_name = getEnsembleDescriptor(target);

			String classifier = EPService.getConnectionClassifier(con);

			String filename = this.getCurrentSelectedFileName();

			if (con instanceof IncomingEditPart) {
				StringBuilder s = new StringBuilder("incoming");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				return s.toString();
			} else if (con instanceof OutgoingEditPart) {
				StringBuilder s = new StringBuilder("outgoing");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				return s.toString();
			} else if (con instanceof InAndOutEditPart) {
				StringBuilder s = new StringBuilder("incoming");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				s.append("outgoing");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				return s.toString();
			} else if (con instanceof NotAllowedEditPart) {
				StringBuilder s = new StringBuilder("not_allowed");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				return s.toString();
			} else if (con instanceof ExpectedEditPart) {
				StringBuilder s = new StringBuilder("expected");
				s.append(createDependencyContent(idx, source, target,
						classifier));
				s.append(".\n");
				return s.toString();
			} else {
				// TODO Improve error handling!
				return "% Error while encoding the modeled connections.";
			}

		}
		// TODO Improve error handling!
		return "% Error while encoding the modeled connections.";
	}

	private String createDependencyContent(int idx, Object source,
			Object target, String classifier) {
		StringBuilder s = new StringBuilder("(");
		s.append(getArchitectureDescriptor());
		s.append(", ");
		s.append(idx);
		s.append(", ");
		s.append(getEnsembleDescriptor(source));
		s.append(", ");
		s.append(getEnsembleParameters(source));
		s.append(", ");
		s.append(getEnsembleDescriptor(target));
		s.append(", ");
		s.append(getEnsembleParameters(target));
		s.append(", ");
		s.append(classifier);
		s.append(")");
		return s.toString();
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
	 * Temp connections will be colored in red.
	 * 
	 * @category hook point
	 * @generated NOT
	 * @author Tam-Minh Nguyen
	 */
	@Override
	protected void initializeGraphicalViewerContents() {
		super.initializeGraphicalViewerContents();
		// System.err.println("Hook point");

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

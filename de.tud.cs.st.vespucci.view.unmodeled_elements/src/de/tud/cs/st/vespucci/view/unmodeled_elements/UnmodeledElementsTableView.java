package de.tud.cs.st.vespucci.view.unmodeled_elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.view.table.ColumnComparator;
import de.tud.cs.st.vespucci.view.table.DataViewContentProvider;
import de.tud.cs.st.vespucci.view.table.Filter;
import de.tud.cs.st.vespucci.view.table.TableColumnSorterListener;

public class UnmodeledElementsTableView extends ViewPart implements
		IProjectElementView<ICodeElement> {

	private TableViewer tableViewer;

	private Map<IProject, IDataView<ICodeElement>> entries;

	private IProject displayedProject = null;

	public UnmodeledElementsTableView() {
		entries = new HashMap<IProject, IDataView<ICodeElement>>();
	}

	@Override
	public void displayProject(IProject project) {
		if (!entries.containsKey(project))

		{
			this.displayedProject = null;
			tableViewer.setInput(null);
			return;
		}
		this.displayedProject = project;
		tableViewer.setInput(entries.get(project));
	}

	@Override
	public void addEntry(IProject project, IDataView<ICodeElement> elements) {
		if (entries.containsKey(project)) {
			entries.get(project).dispose();
		}
		entries.put(project, elements);
	}

	@Override
	public Iterator<IProject> getEntryKeys() {
		return entries.keySet().iterator();
	}

	@Override
	public IProject displayedProject() {
		return displayedProject;
	}

	public void createPartControl(Composite parent) {

		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(new DataViewContentProvider<ICodeElement>());
		tableViewer.setLabelProvider(new CodeElementLabelProvider());
		//tableViewer.setSorter(new ViewerSorter());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				if (event.getSelection() instanceof StructuredSelection) {
					StructuredSelection ts = (StructuredSelection) event
							.getSelection();

					ICodeElement element = (ICodeElement) ts.getFirstElement();
					if (element != null) {
						CodeElementFinder.startSearch(element,
								displayedProject,
								new ICodeElementFoundProcessor() {

									@Override
									public void processFoundCodeElement(
											IMember member, int lineNr) {
										// unused in this case
									}

									@Override
									public void processFoundCodeElement(
											IMember member) {
										try {
											JavaUI.openInEditor(member, true,
													true);
										} catch (PartInitException e) {
											final IStatus is = new Status(
													IStatus.ERROR,
													Activator.PLUGIN_ID, e
															.getMessage(), e);
											StatusManager.getManager().handle(
													is, StatusManager.LOG);
										} catch (JavaModelException e) {
											final IStatus is = new Status(
													IStatus.ERROR,
													Activator.PLUGIN_ID, e
															.getMessage(), e);
											StatusManager.getManager().handle(
													is, StatusManager.LOG);
										}
									}

									@Override
									public void noMatchFound(
											ICodeElement codeElement) {
										// unused in this case
									}
								});
					}
				}
			}
		});

		TableColumn packageColumn = new TableColumn(tableViewer.getTable(),
				SWT.NONE);
		packageColumn.setText("Package");
		packageColumn.setWidth(100);
		packageColumn.setMoveable(true);
		//addColumnSortListener(packageColumn);

		TableColumn classColumn = new TableColumn(tableViewer.getTable(),
				SWT.NONE);
		classColumn.setText("Class");
		classColumn.setWidth(100);
		classColumn.setMoveable(true);
		//addColumnSortListener(classColumn);

		TableColumn elementColumn = new TableColumn(tableViewer.getTable(),
				SWT.NONE);
		elementColumn.setText("Element");
		elementColumn.setWidth(200);
		elementColumn.setMoveable(true);
//		addColumnSortListener(elementColumn);

		TableColumnSorterListener.addColumnSortFunctionality(tableViewer, new ColumnComparator() {
			
			@Override
			public int compare(Object e1, Object e2, int column) {
				CodeElementLabelProvider tlp = new CodeElementLabelProvider();
				return tlp.getColumnText(e1, column).compareToIgnoreCase(tlp.getColumnText(e2, column));
			}
		});
		
		IActionBars actionBars = getViewSite().getActionBars();
		fillContextMenu(actionBars.getMenuManager());
		fillToolBar(actionBars.getToolBarManager());

		tableViewer.setComparator(new TableViewComparator());

		final Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer
				.getTransfer() };
		tableViewer.addDragSupport(DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE, transferTypes,
				new DragSourceListener() {

					@Override
					public void dragStart(final DragSourceEvent event) {
						LocalSelectionTransfer.getTransfer().setSelection(
								tableViewer.getSelection());
					}

					@Override
					public void dragSetData(final DragSourceEvent event) {
						 
					}

					@Override
					public void dragFinished(DragSourceEvent event) {
					}

				});
	}

	void fillToolBar(IToolBarManager tbm) {
		ShowProjectsListAction<ICodeElement> listAction = new ShowProjectsListAction<ICodeElement>(
				this);
		listAction.setText("Show project results");
		listAction.setToolTipText("Show project results");
		ImageDescriptor projectImagedesc = PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(IDE.SharedImages.IMG_OBJ_PROJECT);
		listAction.setImageDescriptor(projectImagedesc);
		tbm.add(listAction);
	}

	void fillContextMenu(IMenuManager menu) {
		Action setClassDeclarationsToFiltered = new Action(
				"Show class declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked())
					tableViewer.addFilter(Filter.classDeclarationFilter);
				else
					tableViewer.removeFilter(Filter.classDeclarationFilter);
			}

		};
		setClassDeclarationsToFiltered.setChecked(true);
		menu.add(setClassDeclarationsToFiltered);
		Action setMethodDeclarationsToFiltered = new Action(
				"Show method declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked())
					tableViewer.addFilter(Filter.methodDeclarationFilter);
				else
					tableViewer.removeFilter(Filter.methodDeclarationFilter);
			}

		};
		setMethodDeclarationsToFiltered.setChecked(true);
		menu.add(setMethodDeclarationsToFiltered);
		Action setFieldDeclarationsToFiltered = new Action(
				"Show field declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked())
					tableViewer.addFilter(Filter.fieldDeclarationFilter);
				else
					tableViewer.removeFilter(Filter.fieldDeclarationFilter);
			}

		};
		setFieldDeclarationsToFiltered.setChecked(true);
		menu.add(setFieldDeclarationsToFiltered);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}
}
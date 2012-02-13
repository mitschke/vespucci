package de.tud.cs.st.vespucci.unmodeled_elements.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;

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
		tableViewer.setContentProvider(new CodeElementContentProvider());
		tableViewer.setLabelProvider(new CodeElementLabelProvider());
		tableViewer.setSorter(new ViewerSorter());

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
		addColumnSortListener(packageColumn);

		TableColumn classColumn = new TableColumn(tableViewer.getTable(),
				SWT.NONE);
		classColumn.setText("Class");
		classColumn.setWidth(100);
		classColumn.setMoveable(true);
		addColumnSortListener(classColumn);

		TableColumn elementColumn = new TableColumn(tableViewer.getTable(),
				SWT.NONE);
		elementColumn.setText("Element");
		elementColumn.setWidth(200);
		elementColumn.setMoveable(true);
		addColumnSortListener(elementColumn);

		IActionBars actionBars = getViewSite().getActionBars();
		fillContextMenu(actionBars.getMenuManager());
		fillToolBar(actionBars.getToolBarManager());

		tableViewer.setComparator(new TableViewComparator());

		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer
				.getTransfer() };
		tableViewer.addDragSupport(DND.DROP_COPY, transferTypes,
				new DragSourceListener() {

					@Override
					public void dragStart(DragSourceEvent event) {

					}

					@Override
					public void dragSetData(final DragSourceEvent event) {
						if (!(tableViewer.getSelection() instanceof StructuredSelection)) {
							event.doit = false;
							return;
						}
						StructuredSelection selection = (StructuredSelection) tableViewer
								.getSelection();

						if (selection.isEmpty()) {
							event.doit = false;
							return;
						}

						final List<IMember> elements = new ArrayList<IMember>(
								selection.size());

						for (@SuppressWarnings("rawtypes")
						Iterator iterator = selection.iterator(); iterator
								.hasNext();) {
							ICodeElement element = (ICodeElement) iterator
									.next();

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
											elements.add(member);
										}

										@Override
										public void noMatchFound(
												ICodeElement codeElement) {
											event.doit = false;
										}
									});

						}

						LocalSelectionTransfer.getTransfer().setSelection(
								new StructuredSelection(elements));
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
					tableViewer.addFilter(classDeclarationFilter);
				else
					tableViewer.removeFilter(classDeclarationFilter);
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
					tableViewer.addFilter(methodDeclarationFilter);
				else
					tableViewer.removeFilter(methodDeclarationFilter);
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
					tableViewer.addFilter(fieldDeclarationFilter);
				else
					tableViewer.removeFilter(fieldDeclarationFilter);
			}

		};
		setFieldDeclarationsToFiltered.setChecked(true);
		menu.add(setFieldDeclarationsToFiltered);
	}

	private void addColumnSortListener(final TableColumn tableColumn) {
		final Table table = tableColumn.getParent();

		tableColumn.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (table.getSortDirection()) {
				case SWT.NONE:

				case SWT.DOWN:
					table.setSortColumn(tableColumn);
					table.setSortDirection(SWT.UP);
					tableViewer.refresh();
					break;
				case SWT.UP:
					table.setSortColumn(tableColumn);
					table.setSortDirection(SWT.DOWN);
					tableViewer.refresh();
					break;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	private final ViewerFilter classDeclarationFilter = new ViewerFilter() {

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(element instanceof IClassDeclaration);
		}
	};

	private final ViewerFilter methodDeclarationFilter = new ViewerFilter() {

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(element instanceof IMethodDeclaration);
		}
	};

	private final ViewerFilter fieldDeclarationFilter = new ViewerFilter() {

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(element instanceof IFieldDeclaration);
		}
	};

}
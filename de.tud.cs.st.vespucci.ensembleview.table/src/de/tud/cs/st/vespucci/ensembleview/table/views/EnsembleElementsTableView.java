package de.tud.cs.st.vespucci.ensembleview.table.views;

import java.util.Arrays;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.ensembleview.table.model.IDataManagerObserver;
import de.tud.cs.st.vespucci.ensembleview.table.model.TableModel;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleElementsTableView extends ViewPart implements
		IDataManagerObserver {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.ensembleview.table.views.EnsembleElementsVisualizer";

	public static EnsembleElementsTableView Table;

	private TableViewer tableViewer;
	private TableContentProvider contentProvider;
	private DataManager<TableModel> dataManager;

	public EnsembleElementsTableView() {
		this.contentProvider = new TableContentProvider();
		EnsembleElementsTableView.Table = this;
	}

	public void addDataManager(DataManager<TableModel> dataManager) {
		this.dataManager = dataManager;
		dataManager.register(this);
		contentProvider.setDataModel(dataManager.getDataModel());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.refresh();
	}

	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setSorter(new ViewerSorter());
		tableViewer.setInput(getViewSite());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				if (event.getSelection() instanceof StructuredSelection) {
					StructuredSelection ts = (StructuredSelection) event
							.getSelection();

					IPair<IEnsemble, ICodeElement> pair = DataManager
							.transfer(ts.getFirstElement());
					if (pair != null) {
						CodeElementFinder.startSearch(pair.getSecond(),
								dataManager.getProject(),
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
													IStatus.ERROR, PLUGIN_ID, e
															.getMessage(), e);
											StatusManager.getManager().handle(
													is, StatusManager.LOG);
										} catch (JavaModelException e) {
											final IStatus is = new Status(
													IStatus.ERROR, PLUGIN_ID, e
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

		tableViewer.setComparator(new TableColumnComparator(1, 0));
		
		TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		viewerNameColumn.getColumn().setText("Ensemble");
		viewerNameColumn.getColumn().setWidth(200);
		addColumnListener(viewerNameColumn.getColumn(), 0);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Package");
		viewerNameColumn.getColumn().setWidth(100);
		addColumnListener(viewerNameColumn.getColumn(), 1);
		
		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Class");
		viewerNameColumn.getColumn().setWidth(100);
		addColumnListener(viewerNameColumn.getColumn(), 2);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Element");
		viewerNameColumn.getColumn().setWidth(200);
		addColumnListener(viewerNameColumn.getColumn(), 3);
		
		addActions(tableViewer);

	}

	private void addColumnListener(final TableColumn tableColumn,
			final int column) {
		tableColumn.addSelectionListener(new SelectionListener() {

			int sortDirection = SWT.NONE;

			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((column == 0) && (sortDirection == SWT.NONE)) {
					sortDirection = SWT.UP;
				}

				switch (sortDirection) {
				case SWT.NONE:
				case SWT.DOWN:
					sortDirection = SWT.UP;
					tableColumn.getParent().setSortColumn(tableColumn);
					tableColumn.getParent().setSortDirection(SWT.UP);
					tableViewer.setComparator(new TableColumnComparator(1,
							column));
					break;
				case SWT.UP:
					sortDirection = SWT.DOWN;
					tableColumn.getParent().setSortColumn(tableColumn);
					tableColumn.getParent().setSortDirection(SWT.DOWN);
					tableViewer.setComparator(new TableColumnComparator(-1,
							column));
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

	static boolean idle = true;

	@Override
	public void update() {
		//if (idle) {
			tableViewer.getTable().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					tableViewer.refresh();
					idle = true;
				}
			});
		//	idle = false;
		//}
	}

	private final ViewerFilter classDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if( element == null ) return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IClassDeclaration);
		}
	};

	private final ViewerFilter methodDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if( element == null ) return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IMethodDeclaration);
		}
	};

	private final ViewerFilter fieldDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if( element == null ) return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IFieldDeclaration);
		}
	};

	private void addActions(final TableViewer tableViewer){
		IActionBars actionBars = getViewSite().getActionBars();
		IMenuManager viewMenu = actionBars.getMenuManager();
		Action setClassDeclarationsToFiltered = new Action("Show class declarations", IAction.AS_CHECK_BOX) {

			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if(!isChecked())
					tableViewer.setFilters(addFilter(tableViewer.getFilters(), classDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(tableViewer.getFilters(), classDeclarationFilter));
			}
			

			
		};
		setClassDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setClassDeclarationsToFiltered);
		Action setMethodDeclarationsToFiltered = new Action("Show method declarations", IAction.AS_CHECK_BOX) {

			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if(!isChecked())
					tableViewer.setFilters(addFilter(tableViewer.getFilters(), methodDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(tableViewer.getFilters(), methodDeclarationFilter));
			}
			

			
		};
		setMethodDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setMethodDeclarationsToFiltered);
		Action setFieldDeclarationsToFiltered = new Action("Show field declarations", IAction.AS_CHECK_BOX) {

			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if(!isChecked())
					tableViewer.setFilters(addFilter(tableViewer.getFilters(), fieldDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(tableViewer.getFilters(), fieldDeclarationFilter));
			}
			

			
		};
		setFieldDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setFieldDeclarationsToFiltered);
		
	}

	private static ViewerFilter[] addFilter(ViewerFilter[] oldFilters,
			ViewerFilter filter) {
		ViewerFilter[] newFilters = Arrays.copyOf(oldFilters,
				oldFilters.length + 1);
		newFilters[oldFilters.length] = filter;
		return newFilters;
	}

	private static ViewerFilter[] removeFilter(ViewerFilter[] oldFilters,
			ViewerFilter filter) {
		if( oldFilters.length == 0)
			return oldFilters;
		ViewerFilter[] newFilters = new ViewerFilter[oldFilters.length - 1];
		int offset = 0;
		for (int i = 0; i < oldFilters.length; i++) {
			if (oldFilters[i] == filter) {
				offset = -1;
				continue;
			}
			newFilters[i + offset] = oldFilters[i];
		}
		return newFilters;
	}
}
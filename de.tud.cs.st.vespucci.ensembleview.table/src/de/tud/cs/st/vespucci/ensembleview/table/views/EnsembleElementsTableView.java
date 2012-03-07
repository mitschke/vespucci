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
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
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
import de.tud.cs.st.vespucci.view.table.ColumnComparator;
import de.tud.cs.st.vespucci.view.table.TableColumnSorterListener;

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
		composite = new Composite(parent, SWT.NONE);
		RowLayout layout1 = new RowLayout(SWT.HORIZONTAL);
		layout1.spacing = 0;
		layout1.marginLeft = 0;
		layout1.marginRight = 0;
		composite.setLayout(layout1);
		GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 941;
		gd_composite.heightHint = 26;
		composite.setLayoutData(gd_composite);
		

		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		org.eclipse.swt.layout.GridLayout layout = new org.eclipse.swt.layout.GridLayout(1, true);
		parent.setLayout(layout);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 5;
		table.setLayoutData(gridData);
		table.setBounds(0, 27, 806, 442);

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

		TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		viewerNameColumn.getColumn().setText("Ensemble");
		viewerNameColumn.getColumn().setWidth(176);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Package");
		viewerNameColumn.getColumn().setWidth(180);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Class");
		viewerNameColumn.getColumn().setWidth(188);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Element");
		viewerNameColumn.getColumn().setWidth(200);
		
		TableColumnSorterListener.addAllColumnListener(tableViewer, new ColumnComparator() {
			
			@Override
			public int compare(Object e1, Object e2, int column) {
				int tempOrder = 0;
				if (column == 3){
					IPair<IEnsemble, ICodeElement> element1 = DataManager.transfer(e1);	
					IPair<IEnsemble, ICodeElement> element2 = DataManager.transfer(e2);
					tempOrder =  TableLabelProvider.createElementTypQualifier(element1.getSecond()).compareTo(TableLabelProvider.createElementTypQualifier(element2.getSecond()));
					if (tempOrder != 0){
						return tempOrder;
					}
				}
					TableLabelProvider tlp = new TableLabelProvider();
					return tlp.getColumnText(e1, column).compareTo(tlp.getColumnText(e2, column));	
			}
		});
		
		TableColumn[] columns = tableViewer.getTable().getColumns();
		
		t_Ensemble = new Text(composite, SWT.BORDER);
		t_Ensemble.setLayoutData(new RowData(88, SWT.DEFAULT));
		t_Ensemble.setBounds(0, 0, columns[0].getWidth(), 50);
		
		t_Package = new Text(composite, SWT.BORDER);
		t_Package.setBounds(columns[0].getWidth(), 0, columns[1].getWidth(), 50);
		
		t_Class = new Text(composite, SWT.BORDER);
		t_Class.setBounds(columns[0].getWidth() + columns[1].getWidth(), 0, columns[2].getWidth(), 50);
		
		t_Element = new Text(composite, SWT.BORDER);
		t_Element.setBounds(columns[0].getWidth() + columns[1].getWidth() + columns[2].getWidth(), 0, columns[3].getWidth(), 50);
				
		for (TableColumn column : tableViewer.getTable().getColumns()) {
			column.addControlListener(new ControlListener() {
				
				@Override
				public void controlResized(ControlEvent e) {
					resizeTextFields();
				}
				
				@Override
				public void controlMoved(ControlEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		addActions(tableViewer);
		
		setSerachFieldListener();
		resizeTextFields();

	}
	
	private void setSerachFieldListener() {
		t_Ensemble.addModifyListener(new SearchFieldModifyListener(tableViewer, 0));
		t_Package.addModifyListener(new SearchFieldModifyListener(tableViewer, 1));
		t_Class.addModifyListener(new SearchFieldModifyListener(tableViewer, 2));
		t_Element.addModifyListener(new SearchFieldModifyListener(tableViewer, 3));
	}

	private void resizeTextFields(){
		TableColumn[] columns = tableViewer.getTable().getColumns();
		
		t_Ensemble.setLayoutData(new RowData(columns[0].getWidth()-12, SWT.DEFAULT));
		t_Package.setLayoutData(new RowData(columns[1].getWidth()-12, SWT.DEFAULT));
		t_Class.setLayoutData(new RowData(columns[2].getWidth()-12, SWT.DEFAULT));
		t_Element.setLayoutData(new RowData(columns[3].getWidth()-12, SWT.DEFAULT));
		
		composite.layout();
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
		// if (idle) {
		tableViewer.getTable().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				// dont refresh All

				tableViewer.refresh();
				idle = true;
			}
		});
		// idle = false;
		// }
	}

	private final ViewerFilter classDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IClassDeclaration);
		}
	};

	private final ViewerFilter methodDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IMethodDeclaration);
		}
	};

	private final ViewerFilter fieldDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IFieldDeclaration);
		}
	};
	private Composite composite;
	private Text t_Ensemble;
	private Text t_Package;
	private Text t_Class;
	private Text t_Element;

	private void addActions(final TableViewer tableViewer) {
		IActionBars actionBars = getViewSite().getActionBars();
		IMenuManager viewMenu = actionBars.getMenuManager();
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
					tableViewer.setFilters(addFilter(tableViewer.getFilters(),
							classDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(
							tableViewer.getFilters(), classDeclarationFilter));
			}

		};
		setClassDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setClassDeclarationsToFiltered);
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
					tableViewer.setFilters(addFilter(tableViewer.getFilters(),
							methodDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(
							tableViewer.getFilters(), methodDeclarationFilter));
			}

		};
		setMethodDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setMethodDeclarationsToFiltered);
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
					tableViewer.setFilters(addFilter(tableViewer.getFilters(),
							fieldDeclarationFilter));
				else
					tableViewer.setFilters(removeFilter(
							tableViewer.getFilters(), fieldDeclarationFilter));
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
		if (oldFilters.length == 0)
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
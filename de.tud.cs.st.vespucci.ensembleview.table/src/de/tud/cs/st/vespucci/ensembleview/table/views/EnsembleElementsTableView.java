package de.tud.cs.st.vespucci.ensembleview.table.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import de.tud.cs.st.vespucci.ensembleview.table.Activator;
import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.ensembleview.table.model.IDataManagerObserver;
import de.tud.cs.st.vespucci.ensembleview.table.model.TableModel;
import de.tud.cs.st.vespucci.ensembleview.table.model.Triple;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleElementsTableView extends ViewPart implements IDataManagerObserver {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String PLUGIN_ID = "de.tud.cs.st.vespucci.ensembleview.table.views.EnsembleElementsVisualizer";

	public static EnsembleElementsTableView Table;

	private TableViewer tableViewer;
	private TableContentProvider contentProvider;

	public EnsembleElementsTableView() {
		this.contentProvider = new TableContentProvider();
		EnsembleElementsTableView.Table = this;
	}

	public void addDataManager(DataManager<TableModel> dataManager){
		dataManager.register(this);
		contentProvider.setDataModel(dataManager.getDataModel());
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.refresh();
	}

	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setLabelProvider(new TableLabelProvider());
		tableViewer.setSorter(new ViewerSorter());
		tableViewer.setInput(getViewSite());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		tableViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event){
				if (event.getSelection() instanceof StructuredSelection){
					StructuredSelection ts = (StructuredSelection)event.getSelection();
					Triple<IEnsemble, ICodeElement, IMember> tripel = DataManager.transfer(ts.getFirstElement());
					if (tripel != null){
						
						//IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().getActivePart();
						try {
							JavaUI.openInEditor(tripel.getThird(), true, true);
						} catch (PartInitException e) {
							final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
							StatusManager.getManager().handle(is, StatusManager.LOG);
						} catch (JavaModelException e) {
							final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
							StatusManager.getManager().handle(is, StatusManager.LOG);
						}
						/*
						IWorkbenchPage editorPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						if (editorPage != null) {

							if (tripel.getThird().getResource() != null){
								try {
									IProject project = tripel.getThird().getJavaProject().getProject();
									IFile file = project.getFile(tripel.getThird().getResource().getProjectRelativePath());

									IMarker marker = file.createMarker(IMarker.TEXT);
									marker.setAttribute(IMarker.MESSAGE, "");
									marker.setAttribute(IMarker.SEVERITY, IMarker.PRIORITY_NORMAL);
									marker.setAttribute(IMarker.CHAR_START, tripel.getThird().getNameRange().getOffset());
									marker.setAttribute(IMarker.CHAR_END, tripel.getThird().getNameRange().getOffset());
									marker.setAttribute(IMarker.TRANSIENT, true);

									IDE.openEditor(editorPage, marker, true);

									marker.delete();
								}catch (CoreException e) {
									final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
									StatusManager.getManager().handle(is, StatusManager.LOG);
								}
							}else{
								// TODO: Make possible to jump to code of external jars with attached source code
							}
						}
						*/
					}
				}
			}
		});

		TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Ensemble");
		viewerNameColumn.getColumn().setWidth(200);
		addColumnListener(viewerNameColumn.getColumn(), 0);
		
		tableViewer.setComparator(new TableColumnComparator(1, 0));

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Element");
		viewerNameColumn.getColumn().setWidth(200);
		addColumnListener(viewerNameColumn.getColumn(), 1);
		
		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Resource");
		viewerNameColumn.getColumn().setWidth(100);
		addColumnListener(viewerNameColumn.getColumn(), 2);
		
		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Path");
		viewerNameColumn.getColumn().setWidth(200);
		addColumnListener(viewerNameColumn.getColumn(), 3);
	}
	
	private void addColumnListener(final TableColumn tableColumn, final int column){
		tableColumn.addSelectionListener(new SelectionListener() {
			
			int sortDirection = SWT.NONE;
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if ((column == 0)&&(sortDirection == SWT.NONE)){
					sortDirection = SWT.UP;
				}
				
				switch (sortDirection){
				case SWT.NONE: case SWT.DOWN:
					sortDirection = SWT.UP;
					tableColumn.getParent().setSortColumn(tableColumn);
					tableColumn.getParent().setSortDirection(SWT.UP);
					tableViewer.setComparator(new TableColumnComparator(1, column));
					break;
				case SWT.UP:
					sortDirection = SWT.DOWN;
					tableColumn.getParent().setSortColumn(tableColumn);
					tableColumn.getParent().setSortDirection(SWT.DOWN);
					tableViewer.setComparator(new TableColumnComparator(-1, column));
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
		if (idle){
			tableViewer.getTable().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					tableViewer.refresh();
					idle = true;
				}
			});
			idle = false;
		}
	}

	class TableContentProvider implements IStructuredContentProvider {

		private TableModel tableModel;

		public void setDataModel(TableModel tableModel){
			this.tableModel = tableModel;
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (tableModel != null){
				return tableModel.getData();
			}
			return new String[0];

		}
	}

	class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return TableModel.createText(DataManager.transfer(obj), index);
		}

		private Image loadImage(String fileLocation) {
			ImageDescriptor imageDescriptor = Activator.getImageDescriptor(fileLocation);
			if (imageDescriptor != null){
				return imageDescriptor.createImage();
			}
			return null;
		}

		private Image package_icon_cache = null;
		private Image class_icon_cache = null;
		private Image method_icon_cache = null;
		private Image field_icon_cache = null;
		
		public Image getColumnImage(Object obj, int index) {
			Triple<IEnsemble, ICodeElement, IMember> triple = DataManager.transfer(obj);
			if (triple != null){
				switch (index) {
				case 0:
					if (package_icon_cache == null){
						package_icon_cache = loadImage("icons/newpackfolder_wiz.gif");
					}
					return package_icon_cache; 
				case 1:
					if (triple.getSecond() instanceof IClassDeclaration){
						if (class_icon_cache == null){
							class_icon_cache = loadImage("icons/class.gif");
						}
						return class_icon_cache;
					}
					if (triple.getSecond() instanceof IMethodDeclaration){
						if (method_icon_cache == null){
							method_icon_cache = loadImage("icons/method.gif");
						}
						return method_icon_cache;
					}
					if (triple.getSecond() instanceof IFieldDeclaration){
						if (field_icon_cache == null){
							field_icon_cache = loadImage("icons/field.gif");
						}
						return field_icon_cache;
					}
				default:
				}
			}
			return null;
		}
	}
	
	class TableColumnComparator extends ViewerSorter{
		
		private static final int numOfColumns = 4;
		
		private int sortDirection;
		private int column;
		
		public TableColumnComparator (int sortDirection, int column){
			this.sortDirection = sortDirection;
			this.column = column;
		}
		
		public int compare(Viewer viewer, Object e1, Object e2) {
			
			Triple<IEnsemble, ICodeElement, IMember> element1 = DataManager.transfer(e1);
			Triple<IEnsemble, ICodeElement, IMember> element2 = DataManager.transfer(e2);
			int tempOrder = 0;
			for (int i = column; i < numOfColumns; i++){
				tempOrder = sortDirection * TableModel.createText(element1, i).compareToIgnoreCase(TableModel.createText(element2, i));
				
				if (tempOrder != 0){
					break;
				}
			}
			return tempOrder;
		}
	}

}
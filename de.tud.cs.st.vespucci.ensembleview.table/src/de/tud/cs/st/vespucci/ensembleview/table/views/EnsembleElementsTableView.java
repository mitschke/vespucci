package de.tud.cs.st.vespucci.ensembleview.table.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IMember;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
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
						IWorkbenchPage editorPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						if (editorPage != null) {
							
							try {
								IProject project = tripel.getThird().getJavaProject().getProject();
								IFile file = project.getFile(tripel.getThird().getResource().getProjectRelativePath());
								
								IMarker marker = file.createMarker(IMarker.TEXT);
								marker.setAttribute(IMarker.MESSAGE, "");
								marker.setAttribute(IMarker.SEVERITY, IMarker.PRIORITY_NORMAL);
								marker.setAttribute(IMarker.CHAR_START, tripel.getThird().getSourceRange().getOffset());
								marker.setAttribute(IMarker.CHAR_END, tripel.getThird().getSourceRange().getOffset());
								marker.setAttribute(IMarker.TRANSIENT, true);
								
								IDE.openEditor(editorPage, marker, true);
								
								marker.delete();
							}
							catch (CoreException e) {
								final IStatus is = new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e);
								StatusManager.getManager().handle(is, StatusManager.LOG);
							}
						}
					}
				}
			}
		});

		TableViewerColumn viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Ensemble");
		viewerNameColumn.getColumn().setWidth(100);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Element");
		viewerNameColumn.getColumn().setWidth(100);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Resource");
		viewerNameColumn.getColumn().setWidth(100);

		viewerNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Path");
		viewerNameColumn.getColumn().setWidth(100);
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	@Override
	public void update() {
		tableViewer.getTable().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				tableViewer.refresh();
			}
		});
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

		public Image getColumnImage(Object obj, int index) {
			Triple<IEnsemble, ICodeElement, IMember> triple = DataManager.transfer(obj);
			if (triple != null){
				switch (index) {
				case 0:
					return loadImage("icons/newpackfolder_wiz.gif");
				case 1:
					if (triple.getSecond() instanceof IClassDeclaration){
						return loadImage("icons/class.gif");
					}
					if (triple.getSecond() instanceof IMethodDeclaration){
						return loadImage("icons/method.gif");
					}
					if (triple.getSecond() instanceof IFieldDeclaration){
						return loadImage("icons/field.gif");
					}
				default:
				}
			}
			return null;
		}

	}

}
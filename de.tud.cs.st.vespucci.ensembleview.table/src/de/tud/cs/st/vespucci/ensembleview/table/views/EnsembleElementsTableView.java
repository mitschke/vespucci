package de.tud.cs.st.vespucci.ensembleview.table.views;


import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.ensembleview.table.model.IDataManagerObserver;
import de.tud.cs.st.vespucci.ensembleview.table.model.TableModel;
import de.tud.cs.st.vespucci.ensembleview.table.model.Triple;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class EnsembleElementsTableView extends ViewPart implements IDataManagerObserver {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.tud.cs.st.vespucci.ensembleview.table.views.EnsembleElementsVisualizer";

	public static EnsembleElementsTableView Table;
	
	private TableViewer viewer;
	private ViewContentProvider contentProvider;
	private LabelProvider labelProvider;
	
	public void setDataManager(DataManager<TableModel> dataManager){
		dataManager.register(this);
		contentProvider.setDataModel(dataManager.getDataModel());
		viewer.setLabelProvider(labelProvider);
		viewer.setContentProvider(contentProvider);
		viewer.refresh();
	}
	
	class ViewContentProvider implements IStructuredContentProvider {
		
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
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			if (obj instanceof Triple){
				Object first = ((Triple<?,?,?>) obj).getFirst();
				if (first instanceof IEnsemble){
					return ((IEnsemble) first).getName();
				}
			}
			return getText(obj);
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().
					getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public EnsembleElementsTableView() {
		this.contentProvider = new ViewContentProvider();
		this.labelProvider = new ViewLabelProvider();
		EnsembleElementsTableView.Table = this;
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void update() {
		viewer.setLabelProvider(labelProvider);
		viewer.setContentProvider(contentProvider);
		viewer.refresh();
	}
}
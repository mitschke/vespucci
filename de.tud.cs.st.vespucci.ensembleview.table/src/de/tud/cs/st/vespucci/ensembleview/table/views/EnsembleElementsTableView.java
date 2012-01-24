package de.tud.cs.st.vespucci.ensembleview.table.views;


import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

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
			if (obj instanceof Triple<?,?,?>){
				Triple<?,?,?> temp = (Triple<?, ?, ?>) obj;
				if ((temp.getFirst() instanceof IEnsemble)&&(temp.getSecond() instanceof ICodeElement)&&(temp.getThird() instanceof IMember)){
					IEnsemble ensemble = (IEnsemble) temp.getFirst();
					ICodeElement codeElement = (ICodeElement) temp.getSecond();
					IMember member = (IMember) temp.getThird();
					switch (index) {
					case 0:
						return ensemble.getName();
					case 1:
						return codeElement.getSimpleClassName();
					case 2:
						if (member instanceof IType){
							return ((IType) member).getElementName();
						}
						if (member instanceof IField){
							try {
								return ((IField) member).getElementName() + " : " + ((IField) member).getTypeSignature();
							} catch (JavaModelException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (member instanceof IMethod){
							try {
								return ((IMethod) member).getElementName() + " : " + ((IMethod) member).getSignature();
							} catch (JavaModelException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						return member.getResource().getName();
					case 3:
						return member.getPath().toOSString();
					default:
						return "";
					}
				}
			}

//			if (obj instanceof Triple){
//				Object third = ((Triple<?,?,?>) obj).getThird();
//				if (third instanceof IMember){
//					return ((IMember) third).getElementName();
//				}
//			}
		return getText(obj);
		}
		
		private ImageDescriptor loadImageDescriptor(String fileLocation){
			return Activator.getImageDescriptor(fileLocation);
		}
		
		private Image loadImage(String fileLocation) {
			ImageDescriptor imageDescriptor = loadImageDescriptor(fileLocation);
			
			if (imageDescriptor != null){
				return Activator.getImageDescriptor(fileLocation).createImage();
			}else{
				return null;
			}

		}
		
		public Image getColumnImage(Object obj, int index) {
			Triple<IEnsemble, ICodeElement, IMember> triple = DataManager.transfer(obj);
			if (triple != null){
				switch (index) {
				case 0:
					return loadImage("icons/newpackfolder_wiz.gif");
				case 1:
					if (triple.getSecond() instanceof IClassDeclaration){
						return loadImage("icons/generate_class.gif");
					}
					if (triple.getSecond() instanceof IMethodDeclaration){
						return loadImage("icons/public_co.gif");
					}
					if (triple.getSecond() instanceof IFieldDeclaration){
						return loadImage("icons/field_private_obj.gif");
					}
				default:
					break;
				}
			}
			

			return null;
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
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new ViewerSorter());
		viewer.setInput(getViewSite());
		
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		TableViewerColumn viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Ensemble");
		viewerNameColumn.getColumn().setWidth(100);
		

		
		viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Element");
		viewerNameColumn.getColumn().setWidth(100);


		
		viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Resource");
		viewerNameColumn.getColumn().setWidth(100);

		viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Path");
		viewerNameColumn.getColumn().setWidth(100);
		

		
	}

	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	@Override
	public void update() {
		viewer.getTable().getDisplay().asyncExec(new Runnable() {
		    @Override
		    public void run() {
		        viewer.refresh();
		    }
		});
	}
	

	
	
}
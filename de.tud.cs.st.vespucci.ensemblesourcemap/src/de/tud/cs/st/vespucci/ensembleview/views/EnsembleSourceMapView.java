package de.tud.cs.st.vespucci.ensembleview.views;


import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.ensembleview.Activator;
import de.tud.cs.st.vespucci.ensembleview.EnsembleSourceProject;
import de.tud.cs.st.vespucci.ensembleview.model.TreeElement;
import de.tud.cs.st.vespucci.model.IEnsemble;


public class EnsembleSourceMapView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "ttesd.views.SampleView";

	public static EnsembleSourceMapView treeTable;
	
	private TreeViewer viewer;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		private TreeElement<String> invisibleRoot;

		public ViewContentProvider(){
			invisibleRoot = new TreeElement<String>(null, "Root");
		}
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {
			if (child instanceof TreeElement) {
				return ((TreeElement<?>)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {
			if (parent instanceof TreeElement) {
				return ((TreeElement<?>)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeElement)
				return ((TreeElement<?>)parent).hasChildren();
			return false;
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
//		private void initialize() {
//			invisibleRoot = new TreeElement<String>(null, "Root");
//		}
		
		public void setTree(List<TreeElement<IEnsemble>> Tree){
			invisibleRoot = new TreeElement<String>(null, "Root");
			for (TreeElement<IEnsemble> treeElement : Tree) {
				invisibleRoot.addChild(treeElement);
			}
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			
			//TODO: check if ImageDesriptor != null
			if (columnIndex == 0){
				if (element instanceof TreeElement){
					if (((TreeElement<?>) element).getReference() instanceof IEnsemble){
						return Activator.getImageDescriptor("icons/newpackfolder_wiz.gif").createImage();
					}
					if (((TreeElement<?>) element).getReference() instanceof String){
						
						if (((TreeElement<?>) element).getParent().getReference() instanceof IEnsemble){
							return Activator.getImageDescriptor("icons/package_obj.gif").createImage();
						}else{
							return Activator.getImageDescriptor("icons/generate_class.gif").createImage();
						}
					}
					
					return null;
				}

			}
			return null;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0){
				if (element instanceof TreeElement){
					TreeElement<?> treeElement = (TreeElement<?>) element;
					
					if (treeElement.getReference() instanceof IEnsemble){
						return ((IEnsemble)treeElement.getReference()).getName();
					}
					
					if (treeElement.getReference() instanceof String){
						return (String) treeElement.getReference();
					}
					return element.toString();
				}
				return element.toString();
			}
			return "";
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public EnsembleSourceMapView() {
		EnsembleSourceMapView.treeTable = this;
	}

	private ViewContentProvider viewContentProvider;
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		Tree tree = new Tree(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		viewer = new TreeViewer(tree);
		
	      TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
	      tree.setLinesVisible(true);
	      column1.setAlignment(SWT.LEFT);
	      column1.setText("Ensemble/Source");
	      column1.setWidth(250);
	      TreeColumn column2 = new TreeColumn(tree, SWT.RIGHT);
	      column2.setAlignment(SWT.LEFT);
	      column2.setText("Resource");
	      column2.setWidth(160);
	      TreeColumn column3 = new TreeColumn(tree, SWT.RIGHT);
	      column3.setAlignment(SWT.LEFT);
	      column3.setText("Path");
	      column3.setWidth(160);
	
		//viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewContentProvider =new ViewContentProvider();
		viewer.setContentProvider(viewContentProvider);
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "Ttesd.viewer");
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void addEnsembleSourceProject(EnsembleSourceProject temp) {
		viewContentProvider.setTree(temp.getElements());
		viewer.refresh();
		viewer.expandAll();
	}
}
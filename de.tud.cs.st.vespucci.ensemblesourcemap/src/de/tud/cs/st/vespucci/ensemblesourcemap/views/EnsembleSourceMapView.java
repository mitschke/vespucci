package de.tud.cs.st.vespucci.ensemblesourcemap.views;


import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.ensemblesourcemap.EnsembleSource;
import de.tud.cs.st.vespucci.ensemblesourcemap.EnsembleSourceProject;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;


public class EnsembleSourceMapView extends ViewPart {

	public static EnsembleSourceMapView pseudoSingelton;
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "de.tud.cs.st.vespucci.ensemblesourcemap.views.EnsembleSourceMapView";

	public static TreeViewer tableTree;	 
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
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

	/**
	 * The constructor.
	 */
	public EnsembleSourceMapView() {
		EnsembleSourceMapView.pseudoSingelton = this;
	}
	
	private LinkedList<EnsembleSourceProject> ensembles = new LinkedList<EnsembleSourceProject>();

	public void addEnsembleSourceProject(EnsembleSourceProject project){
		ensembles.add(project);
		
		//Mock
		EnsembleSourceMapView.tableTree.setInput(ensembles.get(0));
		
		//TODO: do something with it
	}
	
	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		Tree ensembleTree = new Tree(parent, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ensembleTree.setHeaderVisible(true);
		EnsembleSourceMapView.tableTree = new TreeViewer(ensembleTree);
		EnsembleSourceMapView.tableTree.setLabelProvider(new TableLabelProvider());
		EnsembleSourceMapView.tableTree.setContentProvider(new EnsembleContentProvider());

		TreeColumn ensemble = new TreeColumn(ensembleTree, SWT.LEFT);
		ensembleTree.setLinesVisible(true);
		ensemble.setAlignment(SWT.LEFT);
		ensemble.setText("Ensemble");
		ensemble.setWidth(160);
		TreeColumn incredits = new TreeColumn(ensembleTree, SWT.RIGHT);
		incredits.setAlignment(SWT.LEFT);
		incredits.setText("Source");
		incredits.setWidth(100);

		EnsembleSourceMapView.tableTree.expandAll();
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(EnsembleSourceMapView.tableTree.getControl(), "de.tud.cs.st.vespucci.ensemblesourcemap.viewer");
	}


	@Override
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		EnsembleSourceMapView.tableTree.getControl().setFocus();
	}
	
	// Source: http://javawiki.sowas.com/doku.php?id=swt-jface:treetableviewer
	
	   class EnsembleContentProvider implements ITreeContentProvider{
		      public Object[] getChildren(Object parentElement){
		         if (parentElement instanceof EnsembleSource)
		            return ((List<ICodeElement>) ((EnsembleSource )parentElement).getCodeElements()).toArray();
		         return new Object[0];
		      }

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public Object getParent(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				// TODO Auto-generated method stub
				return false;
			}
		 
		   }
		 
		 
		   class TableLabelProvider implements ITableLabelProvider{
		 
		      public Image getColumnImage(Object element, int columnIndex){
		         return null;
		      }
		 
		      public String getColumnText(Object element, int columnIndex){
//		         switch (columnIndex){
//		            case 0: return element.toString();
//		            case 1:
//		               if (element instanceof House)
//		                  return ((House)element).getPerson();
//		            case 2: 
//		               if (element instanceof House)
//		                  return ((House)element).getSex();
//		         }
		         return element.toString();
		      }
		 
		      public void dispose(){
		      }
		 
		      public boolean isLabelProperty(Object element, String property){
		         return false;
		      }
		 


			@Override
			public void addListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeListener(ILabelProviderListener listener) {
				// TODO Auto-generated method stub
				
			}
		   }	
	


}
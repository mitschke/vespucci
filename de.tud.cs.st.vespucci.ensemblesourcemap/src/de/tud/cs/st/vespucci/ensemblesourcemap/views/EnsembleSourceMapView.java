package de.tud.cs.st.vespucci.ensemblesourcemap.views;


import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.ensemblesourcemap.EnsembleSourceProject;


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

	public void addEnsembleSourceProject(EnsembleSourceProject project){
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
		EnsembleSourceMapView.tableTree.setLabelProvider(new ViewLabelProvider());

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


}
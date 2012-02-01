package de.tud.cs.st.vespucci.diagram.global_repository.view;



import org.eclipse.jface.action.Action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;

import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.jface.viewers.ListViewer;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.PlatformUI;

import org.eclipse.ui.menus.IMenuService;
import org.eclipse.ui.part.ViewPart;


import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;





/**
 * @author Tanya Harizanova
 * View class makes the Global Repository View
 */

public class GlobalRepositoryView  extends ViewPart{
	
	private static final String ADD_IMAGE = "icons/global_repository/AddEnsemble.gif";
	private static final String DELETE_IMAGE="icons/global_repository/DeleteEnsemble.gif";
	private static final String SELECT_IMAGE="icons/global_repository/SelectAllEnsemble.gif";
	private Label label;
	private Action addItemAction, deleteItemAction, selectAllAction;
	
	@Override
	public void createPartControl(Composite parent) {
		 label = new Label(parent, 0);
		 createActions();
		 createToolbar();
         label.setText("Hello Vespucci!");
         }

	@Override
	public void setFocus() {
		label.setFocus();
		
	}
    /**
     * create actions for the Tool Bar
     */
    public void createActions() {
        addItemAction = new Action("Add...") {
                public void run() { 
                           //addItem();
                   }
           };
           //set image icon for this action
           addItemAction.setImageDescriptor(ImageDescriptor.createFromImage(getImage(ADD_IMAGE)));
           
          
           deleteItemAction = new Action("Delete") {
                   public void run() {
                          // deleteItem();
                   }
           };
           //set image icon for this action
           deleteItemAction.setImageDescriptor(ImageDescriptor.createFromImage(getImage(DELETE_IMAGE)));

           selectAllAction = new Action("Select All") {
                   public void run() {
                           //selectAll();
                   }
           };
           //set image icon for this action
           selectAllAction.setImageDescriptor(ImageDescriptor.createFromImage(getImage(SELECT_IMAGE)));
    }


    /**
     * create a tool bar on the view and add the actions
     */
    private void createToolbar() {
            IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
            mgr.add(addItemAction);
            mgr.add(deleteItemAction);
            mgr.add(selectAllAction);
    }

    /**
     * @param name
     * @return an image on the path
     */
    protected Image getImage(String name) {
		final ImageDescriptor imageDescriptor = VespucciDiagramEditorPlugin.getBundledImageDescriptor(name);

		return imageDescriptor.createImage();
	}
}

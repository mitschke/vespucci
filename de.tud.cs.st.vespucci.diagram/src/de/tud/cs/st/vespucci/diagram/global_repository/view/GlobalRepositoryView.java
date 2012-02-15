/**
 *  License (BSD Style License):
 *   Copyright (c) 2012
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universität Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universität Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */

package de.tud.cs.st.vespucci.diagram.global_repository.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.vespucci_model.diagram.part.VespucciDiagramEditorPlugin;

/**
 * @author Tanya Harizanova
 * @author Tabea Born
 * @author Christian Knapp
 * View class makes the Global Repository View
 */

public class GlobalRepositoryView  extends ViewPart{
	
	private static final String ADD_IMAGE = "icons/global_repository/AddEnsemble.gif";
	private static final String DELETE_IMAGE="icons/global_repository/DeleteEnsemble.gif";
	private static final String EDIT_IMAGE="icons/global_repository/EditEnsemble.gif";
	private TreeViewer viewer;
	private Action addItemAction, deleteItemAction, editAction;
	
	/**
	 * creates a Tree structured List in the current view (Global Repository)
	 */
	@Override
	public void createPartControl(Composite parent) {
		 createActions();
		 createToolbar();
		 viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		 viewer.setContentProvider(new GRContentProvider());
		 viewer.setLabelProvider(new GRLabelProvider());
		 //Expand the tree
		 viewer.setAutoExpandLevel(2);
		 //Provide the input to the ContentProvider
		 viewer.setInput(new Init());
		 
         
         }

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
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

           /**
            * Creates a new window for editing selected Ensembles
            * TODO
            */
           editAction = new Action("Edit") {
                   public void run() {
                           EditWindow edwin = new EditWindow();
                           edwin.setBlockOnOpen(true);
                           edwin.open();
                           edwin.close();
                   }
           };
           //set image icon for this action
           editAction.setImageDescriptor(ImageDescriptor.createFromImage(getImage(EDIT_IMAGE)));
    }


    /**
     * create a tool bar on the view and add the actions
     */
    private void createToolbar() {
            IToolBarManager mgr = getViewSite().getActionBars().getToolBarManager();
            mgr.add(addItemAction);
            mgr.add(deleteItemAction);
            mgr.add(editAction);
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

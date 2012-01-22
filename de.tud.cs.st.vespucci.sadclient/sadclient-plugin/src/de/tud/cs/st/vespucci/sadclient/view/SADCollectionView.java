/*
   Copyright 2011 Michael Eichberg et al

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.tud.cs.st.vespucci.sadclient.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * @author Mateusz Parzonka
 */
public class SADCollectionView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "de.tud.cs.st.vespucci.sadclient.views.SADCollectionView";

    private TableViewer viewer;
    private Action action1;
    private Action action2;
    private Action doubleClickAction;

    // column numbers
    private final static int NAME_COLUMN = 0;
    private final static int TYPE_COLUMN = 1;
    private final static int ABSTRACT_COLUMN = 2;
    private final static int MODEL_COLUMN = 3;
    private final static int DOCUMENTATION_COLUMN = 4;

    /*
     * The content provider class is responsible for providing objects to the
     * view. It can wrap existing objects in adapters or simply return objects
     * as-is. These objects may be sensitive to the current input of the view,
     * or ignore it and always show the same content (like Task List, for
     * example).
     */

    class ViewContentProvider implements IStructuredContentProvider {

	private Shell shell;

	public ViewContentProvider(Shell shell) {
	    this.shell = shell;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
	    return Controller.getInstance().getSADCollection(shell);
	}
    }

    class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public String getColumnText(Object obj, int index) {
	    if (obj instanceof SAD) {
		SAD sad = (SAD) obj;
		switch (index) {
		case NAME_COLUMN:
		    return sad.getName();
		case TYPE_COLUMN:
		    return sad.getType();
		case ABSTRACT_COLUMN:
		    return sad.getAbstrct();
		}
	    }
	    return "";
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
	    if (obj instanceof SAD) {
		SAD sad = (SAD) obj;
		switch (index) {
		case MODEL_COLUMN:
		    if (sad.getModel() != null)
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		case DOCUMENTATION_COLUMN:
		    if (sad.getDocumentation() != null)
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
	    }
	    return null;

	}
    }

    class NameSorter extends ViewerSorter {
    }

    /**
     * The constructor.
     */
    public SADCollectionView() {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {
	viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);
	viewer.getTable().setHeaderVisible(true);
	viewer.getTable().setLinesVisible(true);

	createTableViewerColumn(NAME_COLUMN, "Name", 100);
	createTableViewerColumn(TYPE_COLUMN, "Type", 100);
	createTableViewerColumn(ABSTRACT_COLUMN, "Abstract", 100);
	createTableViewerColumn(MODEL_COLUMN, "Model", 20);
	createTableViewerColumn(DOCUMENTATION_COLUMN, "Documentation", 20);

	viewer.setContentProvider(new ViewContentProvider(viewer.getControl().getShell()));
	viewer.setLabelProvider(new ViewLabelProvider());
	viewer.setSorter(new NameSorter());
	viewer.setInput(getViewSite());

	// Create the help context id for the viewer's control
	PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "java-tycho-view.viewer");
	makeActions();
	hookDoubleClickAction();
    }

    private TableViewerColumn createTableViewerColumn(int colNumber, String title, int bound) {
	final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.VIRTUAL);
	final TableColumn column = viewerColumn.getColumn();
	column.setText(title);
	column.setWidth(bound);
	column.setResizable(true);
	column.setMoveable(true);
	column.addSelectionListener(getSelectionAdapter(column, colNumber));
	return viewerColumn;
    }

    private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
	SelectionAdapter selectionAdapter = new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		// comparator.setColumn(index);
		// int dir = comparator.getDirection();
		// viewer.getTable().setSortDirection(dir);
		viewer.getTable().setSortColumn(column);
		viewer.refresh();
	    }
	};
	return selectionAdapter;
    }

    private void makeActions() {
	
	// action 1
	action1 = new Action() {
	    public void run() {
		showMessage("Action 1 executed");
	    }
	};
	action1.setText("Action 1");
	action1.setToolTipText("Action 1 tooltip");
	action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

	action2 = new Action() {
	    public void run() {
		viewer.refresh();
		showMessage("Refresh executed");
	    }
	};
	
	// action 2
	action2.setText("Action 2");
	action2.setToolTipText("Action 2 tooltip");
	action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
	doubleClickAction = new Action() {
	    public void run() {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection) selection).getFirstElement();
		showMessage("Double-click detected on3 " + obj.toString());
	    }
	};

	// local pulldown menu
	IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	menuManager.add(action1);
	menuManager.add(new Separator());
	menuManager.add(action2);

	// local toolbar 
	IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	toolBarManager.add(action1);
	toolBarManager.add(action2);
	
	// local pulldown menu
	MenuManager menuMgr = new MenuManager("#PopupMenu");
	menuMgr.setRemoveAllWhenShown(true);
	menuMgr.addMenuListener(new IMenuListener() {
	    public void menuAboutToShow(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	    }
	});
	Menu menu = menuMgr.createContextMenu(viewer.getControl());
	viewer.getControl().setMenu(menu);
	getSite().registerContextMenu(menuMgr, viewer);
    }

    private void hookDoubleClickAction() {
	viewer.addDoubleClickListener(new IDoubleClickListener() {
	    public void doubleClick(DoubleClickEvent event) {
		doubleClickAction.run();
	    }
	});
    }

    private void showMessage(String message) {
	// MessageDialog.openInformation(viewer.getControl().getShell(),
	// "SADClient", message);
	// UploadWizard wizard = new UploadWizard();
	// WizardDialog dialog = new
	// WizardDialog(viewer.getControl().getShell(), wizard);
	Dialog dialog = new SADDialog(viewer.getControl().getShell());
	dialog.open();
	System.out.println(dialog.getReturnCode());
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
	viewer.getControl().setFocus();
    }
}
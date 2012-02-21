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
 * 
 *
 * $Id$
 */
package de.tud.cs.st.vespucci.sadclient.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.model.SAD;

/**
 * A collection of SADs is represented as Eclipse View.
 * 
 * @author Mateusz Parzonka
 */
public class SADCollectionView extends ViewPart {

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "de.tud.cs.st.vespucci.sadclient.view.SADCollectionView";

    private TableViewer viewer;
    private TableViewerComparator comparator;
    private SAD selectedSAD = null;

    // column numbers
    private final static int MODEL_COLUMN = 0;
    private final static int DOCUMENTATION_COLUMN = 1;
    private final static int NAME_COLUMN = 2;
    private final static int TYPE_COLUMN = 3;
    private final static int ABSTRACT_COLUMN = 4;

    @Override
    public void createPartControl(Composite parent) {
	
	viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);
	viewer.getTable().setHeaderVisible(true);
	viewer.getTable().setLinesVisible(true);

	createTableViewerColumn(MODEL_COLUMN, "Mdl", 24, false);
	createTableViewerColumn(DOCUMENTATION_COLUMN, "Doc", 24, false);
	createTableViewerColumn(NAME_COLUMN, "Name", 100, true);
	createTableViewerColumn(TYPE_COLUMN, "Type", 100, true);
	createTableViewerColumn(ABSTRACT_COLUMN, "Abstract", 300, true);

	viewer.setContentProvider(new ViewContentProvider());
	viewer.setLabelProvider(new ViewLabelProvider());
	comparator = new TableViewerComparator();
	viewer.setComparator(comparator);
	viewer.setInput(getViewSite());

	createActions();
	addListeners();
    }

    private TableViewerColumn createTableViewerColumn(int colNumber, String title, int bound, boolean resizable) {
	final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.VIRTUAL);
	final TableColumn column = viewerColumn.getColumn();
	column.setText(title);
	column.setWidth(bound);
	column.setResizable(resizable);
	column.setMoveable(true);
	column.addSelectionListener(getSelectionAdapter(column, colNumber));
	return viewerColumn;
    }

    private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
	SelectionAdapter selectionAdapter = new SelectionAdapter() {
	    @Override
	    public void widgetSelected(SelectionEvent e) {
		comparator.setColumn(index);
		int dir = comparator.getDirection();
		viewer.getTable().setSortDirection(dir);
		viewer.getTable().setSortColumn(column);
		viewer.refresh();
	    }
	};
	return selectionAdapter;
    }

    private void createActions() {

	// create
	final Action actionCreate = new Action() {
	    public void run() {
		Dialog dialog = new SADDialog(viewer, new SAD());
		dialog.open();
	    }
	};
	actionCreate.setText("Create SAD");
	actionCreate.setToolTipText("Creates a new SAD.");
	actionCreate.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_OBJ_ADD));

	// delete
	final Action actionDelete = new Action() {
	    public void run() {
		if (MessageDialog.openConfirm(viewer.getControl().getShell(), "Delete SAD",
			"Do you really want to delete the selected SAD?"))
		    Controller.getInstance().deleteSAD(selectedSAD, viewer);
	    }
	};
	actionDelete.setText("Delete");
	actionDelete.setToolTipText("Deletes the selected SAD.");
	actionDelete.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_ETOOL_DELETE));

	// refresh
	final Action actionRefresh = new Action() {
	    public void run() {
		viewer.refresh();
	    }
	};
	actionRefresh.setText("Refresh");
	actionRefresh.setToolTipText("Updates the list of software architectures from the server.");
	actionRefresh.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
		.getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));

	// pulldown menu
	IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	menuManager.add(actionCreate);
	menuManager.add(actionRefresh);

	// toolbar
	IToolBarManager toolBarManager = getViewSite().getActionBars().getToolBarManager();
	toolBarManager.add(actionCreate);
	toolBarManager.add(actionDelete);
	toolBarManager.add(new Separator());
	toolBarManager.add(actionRefresh);

	// popup menu
	MenuManager menuMgr = new MenuManager("#PopupMenu");
	menuMgr.setRemoveAllWhenShown(true);
	menuMgr.addMenuListener(new IMenuListener() {
	    public void menuAboutToShow(IMenuManager manager) {
		manager.add(actionCreate);
		manager.add(actionRefresh);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	    }
	});
	Menu menu = menuMgr.createContextMenu(viewer.getControl());
	viewer.getControl().setMenu(menu);
	getSite().registerContextMenu(menuMgr, viewer);
    }

    /**
     * Listen for selections in the table and for doubeclicks opening the
     * {@link SADDialog}.
     */
    private void addListeners() {
	viewer.addDoubleClickListener(new IDoubleClickListener() {
	    public void doubleClick(DoubleClickEvent event) {
		if (selectedSAD != null) {
		    Dialog dialog = new SADDialog(viewer, selectedSAD);
		    dialog.open();
		}
	    }
	});
	viewer.addSelectionChangedListener(new ISelectionChangedListener() {
	    public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Object object = selection.getFirstElement();
		if (object instanceof SAD) {
		    selectedSAD = (SAD) object;
		} else {
		    System.err.println("SADTableViewer: Selection-type unknown!");
		}
	    }
	});
    }
    
    /**
     * Returns a collection of SADs.
     */
    class ViewContentProvider implements IStructuredContentProvider {

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	    // do nothing
	}

	public void dispose() {
	    // do nothing
	}

	public Object[] getElements(Object parent) {
	    return Controller.getInstance().getSADCollection();
	}
    }

    /**
     * Provides the labels for Model and Documentation, no labels for the other
     * columns.
     */
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
		    break;
		case DOCUMENTATION_COLUMN:
		    if (sad.getDocumentation() != null)
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
	    }
	    return null;
	}
    }
    
    /**
     * Sorts columns lexically. Model and documentation -columns are sorted not-null first. 
     */
    public class TableViewerComparator extends ViewerComparator {
   	private int columnIndex;
   	private static final int DESCENDING = 1;
   	private int direction = DESCENDING;

   	public TableViewerComparator() {
   	    this.columnIndex = 0;
   	    direction = DESCENDING;
   	}

   	public int getDirection() {
   	    return direction == DESCENDING ? SWT.DOWN : SWT.UP;
   	}

   	public void setColumn(int column) {
   	    if (column == this.columnIndex) {
   		// Same column as last sort; toggle the direction
   		direction = 1 - direction;
   	    } else {
   		// New column; do an ascending sort
   		this.columnIndex = column;
   		direction = DESCENDING;
   	    }
   	}

   	@Override
   	public int compare(Viewer viewer, Object e1, Object e2) {
   	    SAD s1 = (SAD) e1;
   	    SAD s2 = (SAD) e2;
   	    int compare = 0;
   	    switch (columnIndex) {
   	    case MODEL_COLUMN:
   		compare = compareNull(s1.getModel(), s2.getModel());
   		break;
   	    case DOCUMENTATION_COLUMN:
   		compare = compareNull(s1.getDocumentation(), s2.getDocumentation());
   		break;
   	    case NAME_COLUMN:
   		compare = s1.getName().compareToIgnoreCase(s2.getName());
   		break;
   	    case TYPE_COLUMN:
   		compare = s1.getType().compareToIgnoreCase(s2.getType());
   		break;
   	    case ABSTRACT_COLUMN:
   		compare = s1.getAbstrct().compareToIgnoreCase(s2.getAbstrct());
   		break;
   	    default:
   		compare = 0;
   	    }
   	    return direction == DESCENDING ? -compare : compare;
   	}

       }

       /**
        * Sorts null before not-null.
        */
       private static int compareNull(Object o1, Object o2) {
   	if (o1 == null && o2 == null) {
   	    return 0;
   	}
   	if (o1 == null)
   	    return -1;
   	if (o2 == null)
   	    return 1;
   	return 0;
       }

    public void setFocus() {
	viewer.getControl().setFocus();
    }

}
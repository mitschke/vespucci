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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.PluginTransfer;
import org.eclipse.ui.part.PluginTransferData;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.sadclient.controller.Controller;
import de.tud.cs.st.vespucci.sadclient.controller.SADUpdate;
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
    private final static List<SAD> selectedSAD = new ArrayList<SAD>();
    private boolean refreshFromCache; // true, when viewer#refresh does not
				      // expect the current state from the
				      // server

    // column numbers
    private final static int MODEL_COLUMN = 0;
    private final static int DOCUMENTATION_COLUMN = 1;
    private final static int NAME_COLUMN = 2;
    private final static int TYPE_COLUMN = 3;
    private final static int MODIFIED_COLUMN = 4;
    private final static int ABSTRACT_COLUMN = 5;

    @Override
    public void createPartControl(Composite parent) {

	viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);
	viewer.getTable().setHeaderVisible(true);
	viewer.getTable().setLinesVisible(true);

	createTableViewerColumn(MODEL_COLUMN, "Mdl", 24, false);
	createTableViewerColumn(DOCUMENTATION_COLUMN, "Doc", 24, false);
	createTableViewerColumn(NAME_COLUMN, "Name", 100, true);
	createTableViewerColumn(TYPE_COLUMN, "Type", 100, true);
	createTableViewerColumn(MODIFIED_COLUMN, "Modified", 100, true);
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
		refreshFromCache = true;
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
		if (!selectedSAD.isEmpty())
		    if (MessageDialog.openConfirm(viewer.getControl().getShell(), "Delete SAD",
			    String.format("Do you really want to delete the selected %d SAD(s)?", selectedSAD.size())))
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
     * Listen for selections in the table and for double-clicks opening the {@link SADDialog}.
     */
    private void addListeners() {
	viewer.addDoubleClickListener(new IDoubleClickListener() {
	    public void doubleClick(DoubleClickEvent event) {
		if (!selectedSAD.isEmpty()) {
		    final String id = selectedSAD.get(0).getId();
		    Dialog dialog = new SADDialog(viewer, Controller.getInstance().getSAD(id));
		    asyncViewerRefresh();
		    dialog.open();
		}
	    }

	    private void asyncViewerRefresh() {
		viewer.getControl().getDisplay().asyncExec(new Runnable() {
		    @Override
		    public void run() {
			viewer.refresh();
		    }
		});
	    }
	    
	});

	viewer.addSelectionChangedListener(new ISelectionChangedListener() {
	    public void selectionChanged(SelectionChangedEvent event) {
		selectedSAD.clear();
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		Iterator<?> iter = selection.iterator();
		while (iter.hasNext()) {
		    Object object = iter.next();
		    if (object instanceof SAD) {
			selectedSAD.add((SAD) object);
		    } else {
			IconAndMessageDialogs.logError("Unknown selection type.", "Type: " + object.getClass());
		    }
		}

	    }
	});

	viewer.addDropSupport(DND.DROP_MOVE, new Transfer[] { FileTransfer.getInstance() }, new DropListener(viewer));

	viewer.addDragSupport(DND.DROP_MOVE, new Transfer[] { PluginTransfer.getInstance() }, new DragListener());

    }

    /**
     * Returns a collection of SADs.
     */
    class ViewContentProvider implements IStructuredContentProvider {

	private Object[] cache = new Object[0];

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	    // do nothing
	}

	public void dispose() {
	    // do nothing
	}

	public Object[] getElements(Object parent) {
	    if (refreshFromCache) {
		refreshFromCache = false;
		return cache;
	    } else {
		cache = Controller.getInstance().getSADCollection();
		return cache;
	    }
	}
    }

    /**
     * Provides the labels for Model and Documentation, no labels for the other columns.
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
		case MODIFIED_COLUMN:
		    return new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy").format(new Timestamp(sad.getModified()));
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
		compare = s1.getName().compareTo(s2.getName());
		break;
	    case TYPE_COLUMN:
		compare = s1.getType().compareTo(s2.getType());
		break;
	    case ABSTRACT_COLUMN:
		compare = s1.getAbstrct().compareTo(s2.getAbstrct());
		break;
	    case MODIFIED_COLUMN:
		compare = s1.getModified().compareTo(s2.getModified());
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

    /**
     * Allows drags from the project explorer to the table viewer.
     */
    private static class DropListener extends ViewerDropAdapter {

	private final Viewer viewer;

	protected DropListener(Viewer viewer) {
	    super(viewer);
	    this.viewer = viewer;
	}

	@Override
	public boolean performDrop(Object data) {

	    String[] drag = (String[]) data;
	    File modelFile = getFileByExtension(drag, "sad");
	    File documentationFile = getFileByExtension(drag, "pdf");

	    if (!validateData(drag.length, modelFile, documentationFile))
		return false;

	    SADUpdate su = getSADUpdate();
	    su.setModelFile(modelFile);
	    su.setDocumentationFile(documentationFile);
	    if (su.isNewSAD()) {
		String newName = modelFile != null ? getBasename(modelFile) : getBasename(documentationFile);
		su.getSAD().setName(newName);
		su.setDescriptionChanged(true);
	    }

	    Controller.getInstance().performUpdate(su);
	    return true;
	}

	private boolean validateData(int length, File modelFile, File documentationFile) {
	    return ((length == 1 || length == 2)
		    && implies(length == 1, modelFile != null || documentationFile != null) && implies(length == 2,
		    modelFile != null && documentationFile != null));
	}

	private static boolean implies(boolean a, boolean b) {
	    return (!a || b);
	}

	private static File getFileByExtension(String[] filePaths, String extension) {
	    for (String filePath : filePaths) {
		if (FilenameUtils.getExtension(filePath).equals(extension))
		    return new File(filePath);
	    }
	    return null;
	}

	private static String getBasename(File file) {
	    return FilenameUtils.getBaseName(file.getAbsolutePath());
	}

	private SADUpdate getSADUpdate() {
	    SADUpdate sadUpdate = new SADUpdate(viewer);
	    if (getCurrentTarget() instanceof SAD && getCurrentLocation() == LOCATION_ON) {
		sadUpdate.setSAD((SAD) getCurrentTarget());
	    } else {
		sadUpdate.setSAD(new SAD());
		sadUpdate.setNewSAD(true);
	    }
	    return sadUpdate;
	}

	/*
	 * We removed LOCATION_BEFORE in this implementation to improve the mouse handling (it got
	 * fiddly when trying to drop on SADs when the mouse snapped AROUND SADs).
	 */
	@Override
	protected int determineLocation(DropTargetEvent event) {
	    if (!(event.item instanceof Item)) {
		return LOCATION_NONE;
	    }
	    Item item = (Item) event.item;
	    Point coordinates = new Point(event.x, event.y);
	    coordinates = viewer.getControl().toControl(coordinates);
	    if (item != null) {
		Rectangle bounds = getBounds(item);
		if (bounds == null) {
		    return LOCATION_NONE;
		}
		if ((bounds.y + bounds.height - coordinates.y) < 5) {
		    return LOCATION_AFTER;
		}
	    }

	    return LOCATION_ON;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
	    switch (getCurrentLocation()) {
	    case LOCATION_ON:
	    case LOCATION_NONE:
	    case LOCATION_AFTER:
		return true;
	    default:
		return false;
	    }
	}

    }

    private static class DragListener implements DragSourceListener {

	public DragListener() {
	    super();
	}

	@Override
	public void dragFinished(DragSourceEvent event) {
	    // nothing to do
	}

	@Override
	public void dragSetData(DragSourceEvent event) {

	    PluginTransferData p;
	    try {
		p = new PluginTransferData("sadclient-plugin.sad.dnd", getBytes(selectedSAD));
		event.data = p;
	    } catch (IOException e) {
		IconAndMessageDialogs.logError("Internal error with drag and drop.", e.getMessage());
	    }
	}

	@Override
	public void dragStart(DragSourceEvent event) {
	    // nothing to do
	}

	private static byte[] getBytes(Object object) throws IOException {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ObjectOutput out = new ObjectOutputStream(bos);
	    out.writeObject(object);
	    byte[] result = bos.toByteArray();
	    out.close();
	    bos.close();
	    return result;
	}
    }

}
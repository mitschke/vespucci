package de.tud.cs.st.vespucci.view.checked_diagrams.views;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.view.ImageManager;
import de.tud.cs.st.vespucci.view.checked_diagrams.model.Pair;

public class CheckedDiagramsTableView extends ViewPart {

	private static final int COLOUMN_PROJECT = 0;
	private static final int COLOUMN_DIAGRAMFILE = 1;
	private static final int COLOUMN_PATH = 2;

	private TableViewer tableViewer;
	private Action disposeAction;

	public void createPartControl(Composite parent) {
		createTable(parent);
		createActions();
		modifyContextMenu();
		modifyActionBars();
	}

	private void createTable(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn.getColumn();
		tableColumn.setText("Project");
		tableColumn.setWidth(200);

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableColumn = tableViewerColumn.getColumn();
		tableColumn.setText("DiagramFile");
		tableColumn.setWidth(200);

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableColumn = tableViewerColumn.getColumn();
		tableColumn.setText("Path");
		tableColumn.setWidth(200);

		tableViewer.setLabelProvider(new ViewLabelProvider());
		tableViewer.setSorter(new ViewerSorter());
		tableViewer.setContentProvider(new ArrayContentProvider());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);
	}

	private void createActions(){
		disposeAction = new Action() {
			public void run() {
				StructuredSelection ts = (StructuredSelection) tableViewer.getSelection();
				for (Iterator<?> iterator = ts.iterator(); iterator.hasNext();) {
					IPair<IViolationView, IFile> entry = Pair.transfer(iterator.next(), IViolationView.class, IFile.class);
					if (entry != null){
						entry.getFirst().dispose();
						tableViewer.remove(entry);
					}
				}
			}
		};
		disposeAction.setText("dispose");
		disposeAction.setToolTipText("dispose");
		disposeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}

	private void modifyContextMenu() {
		MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(disposeAction);
				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu menu = menuManager.createContextMenu(tableViewer.getControl());
		tableViewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, tableViewer);
	}

	private void modifyActionBars() {
		IActionBars actionBars = getViewSite().getActionBars();
		actionBars.getToolBarManager().add(disposeAction);
	}

	public void addEntry(IPair<IViolationView, IFile> element) {
		tableViewer.add(element);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			IPair<IViolationView, IFile> element = Pair.transfer(obj, IViolationView.class, IFile.class);
			if (element != null){
				switch(index){
				case COLOUMN_PROJECT:
					return element.getSecond().getProject().getName();
				case COLOUMN_DIAGRAMFILE:
					return element.getSecond().getName();
				case COLOUMN_PATH:
					return element.getSecond().getFullPath().toPortableString();
				default:
					return "";
				}
			}
			return "";
		}

		public Image getColumnImage(Object obj, int index) {
			IPair<IViolationView, IFile> element = Pair.transfer(obj, IViolationView.class, IFile.class);
			if (element != null){
				switch(index){
				case COLOUMN_PROJECT:
					return ImageManager.getImage(ImageManager.ICON_PROJECT);
				case COLOUMN_DIAGRAMFILE:
					return ImageManager.getImage(ImageManager.VESPUCCI_DIAGRAM_FILE);
				default:
				}
			}
			return null;
		}
	}
}
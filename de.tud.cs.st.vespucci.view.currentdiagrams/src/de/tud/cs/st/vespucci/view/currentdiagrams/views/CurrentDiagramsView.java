package de.tud.cs.st.vespucci.view.currentdiagrams.views;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.interfaces.IViolationView;

public class CurrentDiagramsView extends ViewPart {

	public static final String ID = "de.tud.cs.st.vespucci.view.currentdiagrams.views.CurrentDiagramsView";

	private static CurrentDiagramsView Table;
	
	private ViewContentProvider contentProvider;
	private LabelProvider labelProvider;
	private TableViewer viewer;

	public class ViewContentProvider implements IStructuredContentProvider {
		
		private Set<IPair<IViolationView, IFile>> data = new HashSet<IPair<IViolationView, IFile>>();
		
		public void addData(IPair<IViolationView, IFile> element){
			data.add(element);
		}
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return data.toArray();
		}

		public void removeData(IPair<IViolationView, IFile> temp) {
			data.remove(temp);
		}
	}
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			IPair<IViolationView, IFile> element = transfer(obj);
			switch(index){
			case 0:
				return element.getSecond().getProject().getName();
			case 1:
				return element.getSecond().getName();
			case 2:
				return element.getSecond().getFullPath().toPortableString();
			default:
				return "";
			}
		}
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}
		public Image getImage(Object obj) {
			return null;
		}
	}

	/**
	 * The constructor.
	 */
	public CurrentDiagramsView() {
		Table = this;
	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		contentProvider = new ViewContentProvider();
		viewer.setContentProvider(contentProvider);
		labelProvider = new ViewLabelProvider();
		viewer.setLabelProvider(labelProvider);
		viewer.setInput(getViewSite());
		
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		
		TableViewerColumn viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Project");
		viewerNameColumn.getColumn().setWidth(200);
		
		viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("DiagramFile");
		viewerNameColumn.getColumn().setWidth(200);
		
		viewerNameColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerNameColumn.getColumn().setText("Path");
		viewerNameColumn.getColumn().setWidth(200);
		
		createAction();
		hookContextMenu();
		contributeToActionBars();
	}

	private Action disposeAction;	
	
	private void createAction(){
		disposeAction = new Action() {
			public void run() {
				
				StructuredSelection ts = (StructuredSelection) Table.viewer.getSelection();
				for (Iterator<?> iterator = ts.iterator(); iterator.hasNext();) {
					Object obj = iterator.next();
					IPair<IViolationView, IFile> temp = transfer(obj);
					temp.getFirst().dispose();
					Table.contentProvider.removeData(temp);
					Table.refresh();
				}
			}
		};
		disposeAction.setText("dispose");
		disposeAction.setToolTipText("dispose");
		disposeAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {


			public void menuAboutToShow(IMenuManager manager) {
				manager.add(disposeAction);
				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		//bars.getMenuManager().add(disposeAction);
		bars.getToolBarManager().add(disposeAction);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public static void addElement(IPair<IViolationView, IFile> element) {
		if (Table != null){
			Table.contentProvider.addData(element);
			Table.refresh();
		}
	}
	
	private void refresh() {
		Table.viewer.setLabelProvider(labelProvider);
		Table.viewer.refresh();
	}

	public static IPair<IViolationView, IFile> transfer(Object obj){
		if (obj instanceof IPair){
			IPair<?,?> temp = (IPair<?,?>) obj;
			if (temp.getFirst() instanceof IViolationView){
				if (temp.getSecond() instanceof IFile){
					@SuppressWarnings("unchecked")
					IPair<IViolationView, IFile> result = (IPair<IViolationView, IFile>) temp;
					return result;
				}
			}
		}
		return null;
	}
	
}
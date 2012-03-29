/*
 *  License (BSD Style License):
 *   Copyright (c) 2011
 *   Software Technology Group
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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
 *   - Neither the name of the Software Technology Group Group or Technische
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
package de.tud.cs.st.vespucci.view.checked_diagrams.views;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.interfaces.IViolationView;
import de.tud.cs.st.vespucci.view.ImageManager;
import de.tud.cs.st.vespucci.view.model.Pair;
import de.tud.cs.st.vespucci.view.table.IColumnComparator;
import de.tud.cs.st.vespucci.view.table.TableColumnSorterListener;

/**
 * View which visualize the information of IViolationViews in a table.
 * Show which diagrams are checked at the moment and prevent functionality 
 * to dispose the checking of diagrams if it is wanted.
 * 
 * @author 
 */
public class CheckedDiagramsTableView extends ViewPart {

	private static final int COLOUMN_PROJECT = 0;
	private static final int COLOUMN_DIAGRAMFILE = 1;
	private static final int COLOUMN_PATH = 2;

	private TableViewer tableViewer;
	private Action disposeAction;
	private ContentProvider contentProvider;

	public void addEntry(IPair<IViolationView, IFile> element) {
		contentProvider.addData(element);
		tableViewer.setLabelProvider(new ViewLabelProvider());
		tableViewer.refresh();
	}

	public void createPartControl(Composite parent) {
		createTable(parent);
		createActions();
		modifyContextMenu();
		modifyActionBars();
	}

	private void createTable(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.setLabelProvider(new ViewLabelProvider());
		contentProvider = new ContentProvider();
		tableViewer.setContentProvider(contentProvider);
		tableViewer.setInput(getViewSite());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

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

		TableColumnSorterListener.addColumnSortFunctionality(tableViewer, new IColumnComparator() {
			
			@Override
			public int compare(Object e1, Object e2, int column) {
				ViewLabelProvider vlp = new ViewLabelProvider();
				
				return vlp.getColumnText(e1, column).compareToIgnoreCase(vlp.getColumnText(e2, column));
			}
		});
	}

	private void createActions(){
		disposeAction = new Action() {
			public void run() {
				StructuredSelection ts = (StructuredSelection) tableViewer.getSelection();
				for (Iterator<?> iterator = ts.iterator(); iterator.hasNext();) {
					IPair<IViolationView, IFile> entry = Pair.cast(iterator.next(), IViolationView.class, IFile.class);
					if (entry != null){
						entry.getFirst().dispose();
						contentProvider.removeData(entry);
						tableViewer.remove(entry);
					}
				}
			}
		};
		disposeAction.setText("remove diagram");
		disposeAction.setToolTipText("removes all violation from this dagram an stop checking it");
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

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			IPair<IViolationView, IFile> element = Pair.cast(obj, IViolationView.class, IFile.class);
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
			IPair<IViolationView, IFile> element = Pair.cast(obj, IViolationView.class, IFile.class);
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

	/**
	 * ContentProvider for checked_diagram table
	 * 
	 * @author 
	 */
	class ContentProvider implements IStructuredContentProvider{

		private List<IPair<IViolationView, IFile>> data = new LinkedList<IPair<IViolationView, IFile>>();

		public void addData(IPair<IViolationView, IFile> element){
			data.add(element);
		}
		
		public void removeData(IPair<IViolationView, IFile> element){
			data.remove(element);
		}
		
		@Override
		public void dispose() {
			// unused in this case
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// unused in this case
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return data.toArray();
		}
		
	}
}
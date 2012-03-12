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
package de.tud.cs.st.vespucci.view.ensemble_elements.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.tud.cs.st.vespucci.codeelementfinder.CodeElementFinder;
import de.tud.cs.st.vespucci.codeelementfinder.ICodeElementFoundProcessor;
import de.tud.cs.st.vespucci.interfaces.IClassDeclaration;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IDataView;
import de.tud.cs.st.vespucci.interfaces.IFieldDeclaration;
import de.tud.cs.st.vespucci.interfaces.IMethodDeclaration;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;
import de.tud.cs.st.vespucci.view.ensemble_elements.EnsembleElementsVisualizer;
import de.tud.cs.st.vespucci.view.model.Pair;
import de.tud.cs.st.vespucci.view.table.ColumnComparator;
import de.tud.cs.st.vespucci.view.table.DataViewContentProvider;
import de.tud.cs.st.vespucci.view.table.TableColumnSorterListener;

/**
 * 
 * @author 
 */
public class EnsembleElementsTableView extends ViewPart {

	private IProject project;

	private static final int COLOUMN_ENSEMBLE = 0;
	private static final int COLOUMN_PACKAGE = 1;
	private static final int COLOUMN_CLASS = 2;
	private static final int COLOUMN_ELEMENT = 3;

	private TableViewer tableViewer;

	private Composite composite;

	private Text searchFieldEnsemble;
	private Text searchFieldPackage;
	private Text searchFieldClass;
	private Text searchFieldElement;

	public void setData(IDataView<IPair<IEnsemble, ICodeElement>> dataView, IProject project){
		this.project = project;
		tableViewer.setLabelProvider(new EnsembleElementsTableLabelProvider());
		tableViewer.setInput(dataView);
	}

	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);

		addSearchFields(parent);
		addTable(parent);
		addSearchFieldListener();
		resizeSearchFields(parent);

		addActions();
	}

	private void addTable(final Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 5;

		Table table = tableViewer.getTable();
		table.setLayoutData(gridData);

		tableViewer.setLabelProvider(new EnsembleElementsTableLabelProvider());
		tableViewer.setContentProvider(new DataViewContentProvider<IPair<IEnsemble, ICodeElement>>());
		tableViewer.setInput(getViewSite());

		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		TableViewerColumn tableColumn = new TableViewerColumn(tableViewer,SWT.NONE);
		tableColumn.getColumn().setText("Ensemble");
		tableColumn.getColumn().setWidth(200);

		tableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableColumn.getColumn().setText("Package");
		tableColumn.getColumn().setWidth(200);

		tableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableColumn.getColumn().setText("Class");
		tableColumn.getColumn().setWidth(200);

		tableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableColumn.getColumn().setText("Element");
		tableColumn.getColumn().setWidth(200);

		TableColumnSorterListener.addAllColumnListener(tableViewer, new ColumnComparator() {

			@Override
			public int compare(Object e1, Object e2, int column) {
				int tempOrder = 0;
				if (column == COLOUMN_ELEMENT){
					IPair<IEnsemble, ICodeElement> element1 = Pair.transfer(e1, IEnsemble.class, ICodeElement.class);	
					IPair<IEnsemble, ICodeElement> element2 = Pair.transfer(e2, IEnsemble.class, ICodeElement.class);;
					tempOrder =  EnsembleElementsTableLabelProvider.createElementTypQualifier(element1.getSecond()).compareTo(EnsembleElementsTableLabelProvider.createElementTypQualifier(element2.getSecond()));
					if (tempOrder != 0){
						return tempOrder;
					}
				}
				EnsembleElementsTableLabelProvider ensembleElementsTableLabelProvider = new EnsembleElementsTableLabelProvider();
				return ensembleElementsTableLabelProvider.getColumnText(e1, column).compareToIgnoreCase(ensembleElementsTableLabelProvider.getColumnText(e2, column));	
			}
		});

		for (TableColumn column : tableViewer.getTable().getColumns()) {
			column.addControlListener(new ControlListener() {

				@Override
				public void controlResized(ControlEvent e) {
					resizeSearchFields(parent);
				}

				@Override
				public void controlMoved(ControlEvent e) {
					// unused in this case
				}
			});
		}

		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				if (event.getSelection() instanceof StructuredSelection) {
					StructuredSelection ts = (StructuredSelection) event.getSelection();

					IPair<IEnsemble, ICodeElement> pair = Pair.transfer(ts.getFirstElement(), IEnsemble.class, ICodeElement.class); 
					if (pair != null) {
						CodeElementFinder.startSearch(pair.getSecond(), project, new ICodeElementFoundProcessor() {

							@Override
							public void processFoundCodeElement(IMember member, int lineNr) {
								// unused in this case
							}

							@Override
							public void processFoundCodeElement(IMember member) {
								try {
									JavaUI.openInEditor(member, true, true);
								} catch (PartInitException e) {
									EnsembleElementsVisualizer.processException(e);
								} catch (JavaModelException e) {
									EnsembleElementsVisualizer.processException(e);
								}
							}

							@Override
							public void noMatchFound(ICodeElement codeElement) {
								// unused in this case
							}
						});
					}
				}
			}
		});
	}

	private void addSearchFields(Composite parent) {
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.spacing = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;

		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);

		searchFieldEnsemble = new Text(composite, SWT.BORDER);
		searchFieldPackage = new Text(composite, SWT.BORDER);
		searchFieldClass = new Text(composite, SWT.BORDER);
		searchFieldElement = new Text(composite, SWT.BORDER);
	}

	private void addSearchFieldListener() {
		searchFieldEnsemble.addModifyListener(new SearchFieldModifyListener(tableViewer, COLOUMN_ENSEMBLE));
		searchFieldPackage.addModifyListener(new SearchFieldModifyListener(tableViewer, COLOUMN_PACKAGE));
		searchFieldClass.addModifyListener(new SearchFieldModifyListener(tableViewer, COLOUMN_CLASS));
		searchFieldElement.addModifyListener(new SearchFieldModifyListener(tableViewer, COLOUMN_ELEMENT));
	}

	private void resizeSearchFields(Composite parent){
		TableColumn[] columns = tableViewer.getTable().getColumns();

		searchFieldEnsemble.setLayoutData(new RowData(columns[0].getWidth()-12, SWT.DEFAULT));
		searchFieldPackage.setLayoutData(new RowData(columns[1].getWidth()-12, SWT.DEFAULT));
		searchFieldClass.setLayoutData(new RowData(columns[2].getWidth()-12, SWT.DEFAULT));
		searchFieldElement.setLayoutData(new RowData(columns[3].getWidth()-12, SWT.DEFAULT));

		parent.layout();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	private final ViewerFilter classDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IClassDeclaration);
		}
	};

	private final ViewerFilter methodDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IMethodDeclaration);
		}
	};

	private final ViewerFilter fieldDeclarationFilter = new ViewerFilter() {

		@SuppressWarnings("unchecked")
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element == null)
				return false;
			return !(((IPair<IEnsemble, ICodeElement>) element).getSecond() instanceof IFieldDeclaration);
		}
	};

	private void addActions() {
		IActionBars actionBars = getViewSite().getActionBars();
		IMenuManager viewMenu = actionBars.getMenuManager();
		Action setClassDeclarationsToFiltered = new Action(
				"Show class declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked()){
					tableViewer.addFilter(classDeclarationFilter);
				}else{
					tableViewer.removeFilter(classDeclarationFilter);
				}
			}

		};
		setClassDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setClassDeclarationsToFiltered);
		Action setMethodDeclarationsToFiltered = new Action(
				"Show method declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked()){
					tableViewer.addFilter(methodDeclarationFilter);
				}else{
					tableViewer.removeFilter(methodDeclarationFilter);
				}
			}

		};
		setMethodDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setMethodDeclarationsToFiltered);
		Action setFieldDeclarationsToFiltered = new Action(
				"Show field declarations", IAction.AS_CHECK_BOX) {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.action.Action#run()
			 */
			@Override
			public void run() {
				if (!isChecked()){
					tableViewer.addFilter(fieldDeclarationFilter);
				}else{
					tableViewer.removeFilter(fieldDeclarationFilter);
				}
			}

		};
		setFieldDeclarationsToFiltered.setChecked(true);
		viewMenu.add(setFieldDeclarationsToFiltered);
	}
}
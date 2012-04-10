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
package de.tud.cs.st.vespucci.view.table;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Listener which change the sortedColumn and sortDirection of
 * an TableViewer when notified
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
public final class TableColumnSorterListener implements SelectionListener {

	/**
	 * Add sorting functionality to an given TableViewer
	 * 
	 * @param tableViewer TableViewer where to add the sorting functionality
	 * @param columnComparator Comparator which is used to sort the Table
	 */
	public static void addColumnSortFunctionality(TableViewer tableViewer, IColumnComparator columnComparator){
		Table table = tableViewer.getTable();
		for (int i = 0; i < table.getColumnCount(); i++){
			table.getColumn(i).addSelectionListener(new TableColumnSorterListener(tableViewer, i));
		}
		tableViewer.setComparator(new TableColumnSorter(tableViewer, columnComparator));
	}

	private TableViewer tableViewer;
	private TableColumn tableColumn;

	public TableColumnSorterListener(TableViewer tableViewer, int column) {
		this.tableViewer = tableViewer;
		this.tableColumn = tableViewer.getTable().getColumn(column);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		ISelection selection = tableViewer.getSelection();	
		switch (tableViewer.getTable().getSortDirection()) {
		case SWT.NONE:
		case SWT.DOWN:
			tableViewer.getTable().setSortColumn(tableColumn);
			tableViewer.getTable().setSortDirection(SWT.UP);
			tableViewer.refresh();
			break;
		case SWT.UP:
			tableViewer.getTable().setSortColumn(tableColumn);
			tableViewer.getTable().setSortDirection(SWT.DOWN);
			tableViewer.refresh();
			break;
		}
		tableViewer.setSelection(selection, true);
		tableViewer.getTable().showSelection();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
}

/**
 * Sorter to sort a table depending on selected column.
 * Column specific order of table elements
 * 
 * @author Olav Lenz
 * @author Patrick Gottschaemmer
 */
class TableColumnSorter extends ViewerSorter{
	
	private IColumnComparator columnComparator;
	private TableViewer tableViewer;

	public TableColumnSorter(TableViewer tableViewer, IColumnComparator columnComparator){
		this.columnComparator = columnComparator;
		this.tableViewer = tableViewer;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {
		int column = getOriginialSortColumnIndex(tableViewer.getTable());
		int sortDirection = getSortDirection(); 

		int tempOrder = 0;
		for (int i = column; i < tableViewer.getTable().getColumnCount(); i++){
			tempOrder = sortDirection * columnComparator.compare(e1, e2, i);
			if (tempOrder != 0){
				break;
			}
		}
		return tempOrder;
	}

	private int getSortDirection() {
		int sortDirection = 1;
		
		switch (tableViewer.getTable().getSortDirection()) {
		case SWT.DOWN:
			sortDirection = -1;
			break;
		case SWT.UP:
			sortDirection = 1;
			break;
		}
		return sortDirection;
	}
	
	/**
	 * 
	 * @author Ralf Mitschke
	 */
	private static int getOriginialSortColumnIndex(Table table) {
		TableColumn sortColumn = table.getSortColumn();
		if (sortColumn == null)
			return 0;
		
		TableColumn[] columns = table.getColumns();
		// the current index of the column
		int colIndex = 0;
		for (; colIndex < columns.length; colIndex++) {
			if (columns[colIndex].equals(sortColumn))
				break;
		}

		// order has array indices of the currently displayed order of columns
		// and entries of creation time of columns
		int[] columnOrder = table.getColumnOrder();
		return columnOrder[colIndex];
	}
}

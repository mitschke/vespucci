package de.tud.cs.st.vespucci.view.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public final class TableColumnSorterListener implements SelectionListener {

	private TableViewer tableViewer;
	private TableColumn tableColumn;

	public TableColumnSorterListener(TableViewer tableViewer, int column) {
		this.tableViewer = tableViewer;
		this.tableColumn = tableViewer.getTable().getColumn(column);
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
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
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}

	public static void addAllColumnListener(TableViewer tableViewer, ColumnComparator columnComparator){
		Table table = tableViewer.getTable();
		for (int i = 0; i < table.getColumnCount(); i++){
			table.getColumn(i).addSelectionListener(new TableColumnSorterListener(tableViewer, i));
		}
		tableViewer.setComparator(new TableColumnComparator(tableViewer, columnComparator));
	}
}

class TableColumnComparator extends ViewerSorter{
	
	private ColumnComparator columnComparator;
	private TableViewer tableViewer;

	public TableColumnComparator(TableViewer tableViewer, ColumnComparator columnComparator){
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

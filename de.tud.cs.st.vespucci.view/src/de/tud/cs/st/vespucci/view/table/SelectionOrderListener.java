package de.tud.cs.st.vespucci.view.table;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class SelectionOrderListener implements SelectionListener {

		private int sortDirection = SWT.NONE;
		private TableViewer tableViewer;
		private TableColumn tableColumn;
		private int column;
		private TableColumnComparator tableColumnComparator;

		public SelectionOrderListener(TableViewer tableViewer, int column, ColumnComparator columnComparator){
			this.tableViewer = tableViewer;
			Table table = tableViewer.getTable();
			this.tableColumn = table.getColumn(column);
			this.column = column;
			this.tableColumnComparator = new TableColumnComparator(columnComparator);
			tableColumnComparator.setColumn(column);
			tableColumnComparator.setMaxNumOfColumns(table.getColumnCount());
			tableColumnComparator.setSortDirection(1);
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			if ((column == 0) && (sortDirection == SWT.NONE)) {
				sortDirection = SWT.UP;
			}

			switch (sortDirection) {
			case SWT.NONE:
			case SWT.DOWN:
				sortDirection = SWT.UP;
				tableColumn.getParent().setSortColumn(tableColumn);
				tableColumn.getParent().setSortDirection(SWT.UP);
				tableColumnComparator.setSortDirection(1);
				tableViewer.setComparator(tableColumnComparator);
				break;
			case SWT.UP:
				sortDirection = SWT.DOWN;
				tableColumn.getParent().setSortColumn(tableColumn);
				tableColumn.getParent().setSortDirection(SWT.DOWN);
				tableColumnComparator.setSortDirection(-1);
				tableViewer.setComparator(tableColumnComparator);
				break;
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}
		
		public static void setSortFunctionality(TableViewer tableViewer, ColumnComparator columnComparator){
			Table table = tableViewer.getTable();
			for (int i = 0; i < table.getColumnCount(); i++){
				TableColumn tableColumn = table.getColumn(i);
				tableColumn.addSelectionListener(new SelectionOrderListener(tableViewer, i, columnComparator));
			}
		}
}

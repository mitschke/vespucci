package de.tud.cs.st.vespucci.view.unmodeled_elements;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TableViewComparator extends ViewerSorter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.
	 * viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (!(viewer instanceof TableViewer))
			return 0;

		TableViewer tb = (TableViewer) viewer;

		if (!(tb.getLabelProvider() instanceof ITableLabelProvider))
			return 0;

		ITableLabelProvider labelProvider = (ITableLabelProvider) ((StructuredViewer) viewer)
				.getLabelProvider();

		int sortDirection = tb.getTable().getSortDirection();

		int originialSortColumnIndex = getOriginialSortColumnIndex(tb
				.getTable());
		String text1 = labelProvider
				.getColumnText(e1, originialSortColumnIndex);
		String text2 = labelProvider
				.getColumnText(e2, originialSortColumnIndex);

		int order = text1.compareTo(text2);
		if (order == 0) {
			for (int i = 0; i < tb.getTable().getColumnCount(); i++) {
				if (i == originialSortColumnIndex)
					continue;

				text1 = labelProvider.getColumnText(e1, i);
				text2 = labelProvider.getColumnText(e2, i);

				order = text1.compareTo(text2);
				if (order != 0)
					break;

			}
		}

		switch (sortDirection) {
		case SWT.DOWN:
			return order;
		case SWT.UP:
			return -order;
		}

		return 0;
	}

	private static int getOriginialSortColumnIndex(Table table) {
		TableColumn sortColumn = table.getSortColumn();
		if (sortColumn == null)
			return 0;
		TableColumn[] columns = table.getColumns();
		// the current index of the column
		int colIndex = 0;
		for (; colIndex < columns.length; colIndex++) {
			if (columns[colIndex] == sortColumn)
				break;
		}

		// order has array indices of the currently displayed order of columns
		// and entries of creation time of columns
		int[] columnOrder = table.getColumnOrder();
		return columnOrder[colIndex];
	}
}

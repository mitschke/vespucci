package de.tud.cs.st.vespucci.view.table;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class TableColumnComparator extends ViewerSorter{

	private int sortDirection;
	protected int column;
	private int maxNumOfColumns = 3;
	ColumnComparator columnComparator;

	public TableColumnComparator(ColumnComparator columnComparator){
		this.columnComparator = columnComparator;
	}
	
	public int compare(Viewer viewer, Object e1, Object e2) {
		int tempOrder = 0;
		for (int i = column; i <= maxNumOfColumns; i++){
			
			tempOrder = sortDirection * columnComparator.compare(e1,e2, i);
			
			if (tempOrder != 0){
				break;
			}
		}
		return tempOrder;
	}

	public void setSortDirection(int sortDirection) {
		this.sortDirection = sortDirection;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public void setMaxNumOfColumns(int maxNumOfColumns) {
		this.maxNumOfColumns = maxNumOfColumns;
	}
}
package de.tud.cs.st.vespucci.ensembleview.table.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

class TableColumnComparator extends ViewerSorter{
	
	private static final int numOfColumns = 4;
	
	private int sortDirection;
	private int column;
	
	public TableColumnComparator (int sortDirection, int column){
		this.sortDirection = sortDirection;
		this.column = column;
	}
	
	public int compare(Viewer viewer, Object e1, Object e2) {
		
		IPair<IEnsemble, ICodeElement> element1 = DataManager.transfer(e1);
		IPair<IEnsemble, ICodeElement> element2 = DataManager.transfer(e2);
		int tempOrder = 0;
		for (int i = column; i < numOfColumns; i++){
			// Special case for sorting element column
			// sorted at first by type of element and afterwards by spelling
			if (i == 3){
				tempOrder = TableLabelProvider.createElementTypQualifier(element1.getSecond()).compareTo(TableLabelProvider.createElementTypQualifier(element2.getSecond()));
				
				if (tempOrder != 0){
					break;
				}
			}
			
			tempOrder = sortDirection * TableLabelProvider.createText(element1, i).compareToIgnoreCase(TableLabelProvider.createText(element2, i));
			
			if (tempOrder != 0){
				break;
			}
		}
		return tempOrder;
	}
}
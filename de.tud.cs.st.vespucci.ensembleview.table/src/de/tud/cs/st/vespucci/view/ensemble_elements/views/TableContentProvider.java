package de.tud.cs.st.vespucci.view.ensemble_elements.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import de.tud.cs.st.vespucci.view.ensemble_elements.model.TableModel;

class TableContentProvider implements IStructuredContentProvider {

	private TableModel tableModel;

	public void setDataModel(TableModel tableModel){
		this.tableModel = tableModel;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
	public void dispose() {
	}
	public Object[] getElements(Object parent) {
		if (tableModel != null){
			return tableModel.getData();
		}
		return new String[0];

	}
}
package de.tud.cs.st.vespucci.ensembleview.table.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

public class SearchFieldModifyListener implements ModifyListener {

	private TableViewer tableViewer;
	private SearchFilter filter = null;
	private String text = null;
	private int column;
	
	public SearchFieldModifyListener(TableViewer tableView, int column){
		this.tableViewer = tableView;
		this.column = column;
	}
	
	@Override
	public void modifyText(ModifyEvent e) {
		if (filter != null) {
			tableViewer.removeFilter(filter);
		}

		text = ((Text) e.getSource()).getText();
		filter = new SearchFilter(column, text);
		tableViewer.addFilter(filter);
	}

}

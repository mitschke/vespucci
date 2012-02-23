package de.tud.cs.st.vespucci.ensembleview.table.views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import de.tud.cs.st.vespucci.ensembleview.table.model.DataManager;
import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class SearchFilter extends ViewerFilter {
	
	private int column;
	private String input;
	
	public SearchFilter(int column, String input){
		this.column = column;
		this.input = input;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element != null){
			return select(DataManager.transfer(element), column, input);
		}
		return false;
		
	}
	
	public static boolean select(IPair<IEnsemble, ICodeElement> element, int column, String input){
		if (element != null){
			Pattern pat = Pattern.compile(".*" + input.replace("?", ".").replace("*", ".*"));
			
			String string = TableLabelProvider.createText(element, column);
			
			
			Matcher matcher = pat.matcher(string);
			return matcher.lookingAt();
		}
		return false;
	}

}

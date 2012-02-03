package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class TableModel implements IDataModel{

	private Set<IPair<IEnsemble, ICodeElement>> data = new HashSet<IPair<IEnsemble, ICodeElement>>();
	
	@Override
	public void added(IPair<IEnsemble, ICodeElement> element) {
		data.add(element);
	}

	@Override
	public void deleted(Triple<IEnsemble, ICodeElement, IMember> element) {
		data.remove(element);
	}
	
	public Object[] getData(){
		return data.toArray();
	}

}

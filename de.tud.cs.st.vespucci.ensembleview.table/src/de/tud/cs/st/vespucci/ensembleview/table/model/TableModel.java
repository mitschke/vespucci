package de.tud.cs.st.vespucci.ensembleview.table.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.model.IEnsemble;

public class TableModel implements IDataModel{

	private Set<Triple<IEnsemble, ICodeElement, IMember>> data = new HashSet<Triple<IEnsemble, ICodeElement, IMember>>();
	
	@Override
	public void added(Triple<IEnsemble, ICodeElement, IMember> element) {
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

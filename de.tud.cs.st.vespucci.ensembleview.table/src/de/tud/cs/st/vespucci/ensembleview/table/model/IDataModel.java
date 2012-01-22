package de.tud.cs.st.vespucci.ensembleview.table.model;

import org.eclipse.jdt.core.IMember;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.model.IEnsemble;

public interface IDataModel {

	void added(Triple<IEnsemble, ICodeElement, IMember> element);
	
	void deleted(Triple<IEnsemble, ICodeElement, IMember> element);
	
}

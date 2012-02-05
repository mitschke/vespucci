package de.tud.cs.st.vespucci.ensembleview.table.model;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public interface IDataModel {

	void added(IPair<IEnsemble, ICodeElement> element);

	void deleted(IPair<IEnsemble, ICodeElement> element);
	
}

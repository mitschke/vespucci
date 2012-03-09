package de.tud.cs.st.vespucci.view.ensemble_elements.model;

import de.tud.cs.st.vespucci.interfaces.ICodeElement;
import de.tud.cs.st.vespucci.interfaces.IPair;
import de.tud.cs.st.vespucci.model.IEnsemble;

public interface IDataModel {

	void added(IPair<IEnsemble, ICodeElement> element);

	void deleted(IPair<IEnsemble, ICodeElement> element);
	
}

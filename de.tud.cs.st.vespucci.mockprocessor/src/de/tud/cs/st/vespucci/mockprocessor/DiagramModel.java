package de.tud.cs.st.vespucci.mockprocessor;

import java.util.Set;

import de.tud.cs.st.vespucci.model.IEnsemble;

public class DiagramModel {

	Set<IEnsemble> elements;

	public DiagramModel(Set<IEnsemble> elements) {
		this.elements = elements;
	}

	public Set<IEnsemble> getElements() {
		return elements;
	}
	
}

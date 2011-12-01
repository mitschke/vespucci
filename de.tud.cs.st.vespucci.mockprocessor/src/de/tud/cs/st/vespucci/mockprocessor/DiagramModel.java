package de.tud.cs.st.vespucci.mockprocessor;

import java.util.LinkedList;

import de.tud.cs.st.vespucci.diagram.interfaces.IEnsemble;

public class DiagramModel {

	LinkedList<IEnsemble> elements;

	public DiagramModel(LinkedList<IEnsemble> elements) {
		this.elements = elements;
	}

	public LinkedList<IEnsemble> getElements() {
		return elements;
	}
	
	
	
	
}
